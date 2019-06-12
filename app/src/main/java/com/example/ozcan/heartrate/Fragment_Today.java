package com.example.ozcan.heartrate;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;
import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import noman.weekcalendar.WeekCalendar;
import noman.weekcalendar.listener.OnDateClickListener;

/**
 * Created by ozcan on 12.05.2018.
 */

public class Fragment_Today extends Fragment {

    DS_Katmanı ds;
    List<Pulse_Deger> ps;
    TextView Tdeger,text_min,text_max;
    String sql_sorgu_grafik,sql_max,sql_min,sql_avg;


    BarChart mBarChart;


    String mParam1;
    int max,min;

    @SuppressLint("ValidFragment")
    public Fragment_Today(String mParam1) {
        this.mParam1 = mParam1;
    }
    public Fragment_Today() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_today,container,false);

        Tdeger=(TextView) view.findViewById(R.id.txt_satir_data);
        text_min=(TextView) view.findViewById(R.id.txt_min);
        text_max=(TextView) view.findViewById(R.id.txt_max);

        ds=new DS_Katmanı(getActivity());
        ds.open();
        //sqlcommand

        String dt = mParam1;  // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, 1);  // number of days to add
        dt = sdf.format(c.getTime());  // dt is now the new date

        sql_sorgu_grafik=" select * from Pulse where date >= '"+mParam1+"' and date <'"+dt+"'";
        sql_max="SELECT MAX(data) as data FROM Pulse where date >= '"+mParam1+"' and date <'"+dt+"' ";
        sql_min="SELECT MIN(data) as data FROM Pulse where date >= '"+mParam1+"' and date <'"+dt+"'";
        sql_avg="SELECT data FROM Pulse where date >= '"+mParam1+"' and date <'"+dt+"'";
        ps=ds.listele(sql_sorgu_grafik);
        min=ds.findMin(sql_min);
        Tdeger.setText(""+ds.findAverageofToday(sql_avg));
        max=ds.findMax(sql_max);
        text_min.setText(""+min);
        text_max.setText(""+max);



        populateGraphView(view);


        ds.close();
        return view;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void populateGraphView(View view) {
        mBarChart = (BarChart) view.findViewById(R.id.barchart);
        for (int i=0;i<ps.size();i++) {
            float a=ps.get(i).getNabiz_deger();
            mBarChart.addBar(new BarModel((float)a, 0xffff286f));
            mBarChart.startAnimation();
        }
    }


}
