package com.example.ozcan.heartrate;


import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by ozcan on 12.05.2018.
 */

public class Fragment_Statistics extends Fragment implements View.OnClickListener{

    DS_Katmanı ds;
    ValueLineChart mCubicValueLineChart_day,mCubicValueLineChart_month,getmCubicValueLineChart_year;

    List<Pulse_Deger> ps;
    List<MounthStaticsClass> msc;
    List<MounthStaticsClass> ysc;

    Button buton_day,buton_month,buton_year;
    TextView text_avg,text_tarih;
    ListView lss;
    String sql_sorgu_grafik,sql_max,sql_min,sql_avg;
    RelativeLayout rl_day,rl_month,rl_year;

    String hangilist;
    int max,min,avg,position;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_statistic_layout,container,false);
        ds=new DS_Katmanı(getActivity());
        ds.open();

        text_avg=(TextView) view.findViewById(R.id.txtS_ort);
        text_tarih=(TextView) view.findViewById(R.id.txtS_tarih);
        lss=(ListView) view.findViewById(R.id.list_statics) ;



        buton_day=(Button)view.findViewById(R.id.btnS_day);
        buton_month=(Button)view.findViewById(R.id.btnS_month);
        buton_year=(Button)view.findViewById(R.id.btnS_year);
        rl_day=(RelativeLayout) view.findViewById(R.id.graph_day);
        rl_month=(RelativeLayout) view.findViewById(R.id.graph_month);
        rl_year=(RelativeLayout) view.findViewById(R.id.graph_year);

        buton_day.setOnClickListener(this);
        buton_month.setOnClickListener(this);
        buton_year.setOnClickListener(this);



        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        TodayData();
        populateGraphView1(view);
        ds.close();
        return view;
    }

    //day
    private void populateGraphView1(View view) {

        mCubicValueLineChart_day= (ValueLineChart)view.findViewById(R.id.cubiclinechart);
        ValueLineSeries series = new ValueLineSeries();
        series.setColor(0xffff286f);

        for (int i=0;i<ps.size();i++) {
            float a=ps.get(i).getNabiz_deger();
            series.addPoint(new ValueLinePoint(""+ps.get(i).getNabiz_deger(), (float)a));
        }
        mCubicValueLineChart_day.getMaxZoomX();
        mCubicValueLineChart_day.addSeries(series);
        mCubicValueLineChart_day.showContextMenu();
        mCubicValueLineChart_day.startAnimation();
    }

    //month
    private void populateGraphView2(View view) {

        mCubicValueLineChart_month= (ValueLineChart)view.findViewById(R.id.cubiclinechart1);
        ValueLineSeries series = new ValueLineSeries();
        series.setColor(0xffff286f);
        String[] ayy=getResources().getStringArray(R.array.months);
        for (int i=0;i<msc.size();i++) {
            series.addPoint(new ValueLinePoint(""+ayy[i]+"", (float)msc.get(i).getAvg()));
        }
        mCubicValueLineChart_month.addSeries(series);
        mCubicValueLineChart_month.startAnimation();
    }
    //year
    private void populateGraphView3(View view) {

        getmCubicValueLineChart_year= (ValueLineChart)view.findViewById(R.id.cubiclinechart3);
        ValueLineSeries series = new ValueLineSeries();
        series.setColor(0xffff286f);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        int listsize=ysc.size();
        year-=listsize;

        for (int i=listsize-1;i>=0;i--) {
            series.addPoint(new ValueLinePoint(""+year, (float)ysc.get(i).getAvg()));
            year++;
        }
        series.addPoint(new ValueLinePoint(""+(year+1), 0.f));
       // series.addPoint(new ValueLinePoint(""+(year+2), 0.f));
        getmCubicValueLineChart_year.addSeries(series);
        getmCubicValueLineChart_year.startAnimation();
    }



    @Override
    public void onClick(View v) {

        Calendar cal = Calendar.getInstance();
        cal.setTime( Calendar.getInstance().getTime());
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String[] s=getResources().getStringArray(R.array.aylar);

        switch (v.getId()){
            case R.id.btnS_day:
                rl_day.setVisibility(View.VISIBLE);
                rl_month.setVisibility(View.INVISIBLE);
                rl_year.setVisibility(View.INVISIBLE);
                buton_month.setBackgroundColor(Color.parseColor("#FF286F"));
                buton_month.setTextColor(Color.parseColor("#FFFFFF"));
                buton_year.setBackgroundColor(Color.parseColor("#FF286F"));
                buton_year.setTextColor(Color.parseColor("#FFFFFF"));
                buton_day.setBackgroundColor(Color.parseColor("#FFFFFF"));
                buton_day.setTextColor(Color.parseColor("#000000"));

                ds=new DS_Katmanı(getActivity());
                ds.open();
                TodayData();
                text_tarih.setText(""+s[month]+" "+year);
                ds.close();
                populateGraphView1(getView());

                break;
            case R.id.btnS_month:
                hangilist="month";
                rl_month.setVisibility(View.VISIBLE);
                rl_day.setVisibility(View.INVISIBLE);
                rl_year.setVisibility(View.INVISIBLE);
                buton_day.setBackgroundColor(Color.parseColor("#FF286F"));
                buton_day.setTextColor(Color.parseColor("#FFFFFF"));
                buton_year.setBackgroundColor(Color.parseColor("#FF286F"));
                buton_year.setTextColor(Color.parseColor("#FFFFFF"));
                buton_month.setBackgroundColor(Color.parseColor("#FFFFFF"));
                buton_month.setTextColor(Color.parseColor("#000000"));

                ds=new DS_Katmanı(getActivity());
                ds.open();
                msc=MonthDataList();
                text_tarih.setText(""+s[month]+" "+year);
                text_avg.setText(""+msc.get(month).getAvg());
                SatirAdaptor_MY sa=new SatirAdaptor_MY(getActivity(),msc,""+year);
                lss.setAdapter(sa);
                ds.close();
                populateGraphView2(getView());

                break;
            case R.id.btnS_year:
                hangilist="year";
                rl_month.setVisibility(View.INVISIBLE);
                rl_day.setVisibility(View.INVISIBLE);
                rl_year.setVisibility(View.VISIBLE);
                buton_day.setBackgroundColor(Color.parseColor("#FF286F"));
                buton_day.setTextColor(Color.parseColor("#FFFFFF"));
                buton_month.setBackgroundColor(Color.parseColor("#FF286F"));
                buton_month.setTextColor(Color.parseColor("#FFFFFF"));
                buton_year.setBackgroundColor(Color.parseColor("#FFFFFF"));
                buton_year.setTextColor(Color.parseColor("#000000"));
                ds=new DS_Katmanı(getActivity());
                ds.open();
                ysc=YearDataList();
                text_tarih.setText(""+year);
                text_avg.setText(""+ysc.get(0).getAvg());
                SatirAdaptor_year saa=new SatirAdaptor_year(getActivity(),ysc,year);
                lss.setAdapter(saa);
                ds.close();
                populateGraphView3(getView());

                break;
        }

    }

    public void TodayData(){
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());

        String dt = timeStamp;  // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, 1);  // number of days to add
        dt = sdf.format(c.getTime());  // dt is now the new date

        sql_sorgu_grafik=" select * from Pulse where date >= '"+timeStamp+"' and date <'"+dt+"'";
        sql_max="SELECT MAX(data) as data FROM Pulse where date >= '"+timeStamp+"' and date <'"+dt+"' ";
        sql_min="SELECT MIN(data) as data FROM Pulse where date >= '"+timeStamp+"' and date <'"+dt+"'";
        sql_avg="SELECT data FROM Pulse where date >= '"+timeStamp+"' and date <'"+dt+"'";

        ps=ds.listele(sql_sorgu_grafik);
        SatirAdaptor sa=new SatirAdaptor(getActivity(),ps);
        lss.setAdapter(sa);
        min=ds.findMin(sql_min);
        text_avg.setText(""+ds.findAverageofToday(sql_avg));
        max=ds.findMax(sql_max);

    }

    public List<MounthStaticsClass> MonthDataList()
    {
        ArrayList<MounthStaticsClass> liste=new ArrayList<MounthStaticsClass>();

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        for(int i=0;i<12;i++){
            ds=new DS_Katmanı(getActivity());
            ds.open();
        calendar.set(year,i,1); //------>
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
        String tarih=sdf.format(calendar.getTime()).toString();

            String startdate="";
        if(i<9){
             startdate= ""+year+"-"+"0"+(i+1);
        }
        else{
            startdate= ""+year+"-"+(i+1);
        }
        sql_max="SELECT MAX(data) as data FROM Pulse where date >= '"+startdate+"-01' and date <'"+tarih+"' ";
        sql_min="SELECT MIN(data) as data FROM Pulse where date >= '"+startdate+"-01' and date <'"+tarih+"'";
        sql_avg="SELECT data FROM Pulse where date >= '"+startdate+"-01' and date <'"+tarih+"'";
        int minn=ds.findMin(sql_min);
        int avgn=ds.findAverageofToday(sql_avg);
        int maxn=ds.findMax(sql_max);
        ds.close();
        MounthStaticsClass mounth=new MounthStaticsClass(minn,avgn,maxn);
        liste.add(mounth);
        }

        return liste;
    }
    public List<MounthStaticsClass> YearDataList()
    {
        ArrayList<MounthStaticsClass> liste=new ArrayList<MounthStaticsClass>();

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        for(int j=3;j>0;j--){

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.DAY_OF_YEAR, 1);
        Date start = cal.getTime();

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyy-MM-dd");
        String start_dateofyear=sdf2.format(cal.getTime()).toString();

//set date to last day of 2014
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, 11); // 11 = december
        cal.set(Calendar.DAY_OF_MONTH, 31); // new years eve
        Date end = cal.getTime();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyy-MM-dd");
        String end_dateofyear=sdf1.format(cal.getTime()).toString();

            ds=new DS_Katmanı(getActivity());
            ds.open();
        sql_max="SELECT MAX(data) as data FROM Pulse where date >= '"+start_dateofyear+"' and date <'"+end_dateofyear+"' ";
        sql_min="SELECT MIN(data) as data FROM Pulse where date >= '"+start_dateofyear+"' and date <'"+end_dateofyear+"'";
        sql_avg="SELECT data FROM Pulse where date >= '"+start_dateofyear+"' and date <'"+end_dateofyear+"'";
        int minn=ds.findMin(sql_min);
        int avgn=ds.findAverageofToday(sql_avg);
        int maxn=ds.findMax(sql_max);
        ds.close();
        MounthStaticsClass mounth=new MounthStaticsClass(minn,avgn,maxn);
        liste.add(mounth);
        year--;
        }

        return liste;
    }
}
