package com.example.ozcan.heartrate;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import noman.weekcalendar.WeekCalendar;
import noman.weekcalendar.listener.OnDateClickListener;


public class MainEkranActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Button btn_bigcal,btn_profile;
    TextView takvim_date;
    LinearLayout linearLayout;
    String sendedDate="";

    int big_year;
    int big_ay;
    int big_gun;
    DateTime time;

    DS_Katmanı ds;


    private WeekCalendar weekCalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ana_ekran_layout);

        btn_bigcal=findViewById(R.id.btn_frgtoday_tarih);
        btn_profile=findViewById(R.id.btn_frgtoday_profile);
        linearLayout=(LinearLayout) findViewById(R.id.ly_calendar);
        takvim_date=(TextView) findViewById(R.id.txt_calendar_date);
        bottomNavigationView=findViewById(R.id.navigation);
        weekCalendar = (WeekCalendar) findViewById(R.id.weekCalendar);


        ds=new DS_Katmanı(this);
        ds.open();


        String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());

        if(Sqlcommand(timeStamp)!=0){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Fragment_Today(timeStamp)).commit();

        }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Fragment_noData()).commit();
        }

        String split_date[]=timeStamp.split("-");
        try {
            weekCalendar.setSelectedDate(new DateTime().withDate(Integer.valueOf(split_date[0]),Integer.valueOf(split_date[1])-1,
                    Integer.valueOf(split_date[2])));


            String[] mounths=getResources().getStringArray(R.array.aylar);
            btn_bigcal.setText(""+split_date[2]+"  "+mounths[Integer.parseInt(split_date[1])-1]);
            takvim_date.setText(""+split_date[0]+" "+mounths[Integer.parseInt(split_date[1])-1]+" "+split_date[0]);
            bottomNavigationView.setOnNavigationItemReselectedListener(navlistener);
             }
        catch (Exception e){}


        HorizontalCalenadar();
    }

    public void Tiklandi(View view) {

        switch (view.getId())
        {
            case R.id.btn_frgtoday_tarih:
                BigCalendar();
                break;
            case R.id.btn_frgtoday_profile:

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainEkranActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.profil_layout, null);


                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();









                break;
        }
    }
    //navigation bar
    private  BottomNavigationView.OnNavigationItemReselectedListener navlistener=
            new BottomNavigationView.OnNavigationItemReselectedListener() {
                @Override
                public void onNavigationItemReselected(@NonNull MenuItem item) {

                    Fragment selectedFragment= null;

                    switch (item.getItemId()){
                        case R.id.nav_today:
                            btn_bigcal.setVisibility(View.VISIBLE);
                            linearLayout.setVisibility(View.VISIBLE);
                            takvim_date.setVisibility(View.VISIBLE);

                            String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
                            String split_date[]=timeStamp.split("-");
                            try {
                                weekCalendar.setSelectedDate(new DateTime().withDate(Integer.valueOf(split_date[0]),Integer.valueOf(split_date[1])-1,
                                        Integer.valueOf(split_date[2])));

                                String[] mounths=getResources().getStringArray(R.array.aylar);
                                btn_bigcal.setText(""+split_date[2]+"  "+mounths[Integer.parseInt(split_date[1])-1]);
                                takvim_date.setText(""+split_date[2]+" "+mounths[Integer.parseInt(split_date[1])-1]+" "+split_date[0]);
                                bottomNavigationView.setOnNavigationItemReselectedListener(navlistener);
                            }
                            catch (Exception e){}

                            if(Sqlcommand(timeStamp)!=0){
                                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Fragment_Today(timeStamp)).commit();

                            }else{
                                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Fragment_noData()).commit();
                            }
                            break;
                        case R.id.nav_pulse:
                            btn_bigcal.setVisibility(View.GONE);
                           // btn_profile.setVisibility(View.GONE);
                            takvim_date.setVisibility(View.GONE);
                            linearLayout.setVisibility(View.GONE);

                            selectedFragment=new Fragment_Pulse();
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                            break;
                           case R.id.nav_statistic:
                               btn_bigcal.setVisibility(View.GONE);
                               takvim_date.setVisibility(View.GONE);
                               //btn_profile.setVisibility(View.GONE);
                               linearLayout.setVisibility(View.GONE);

                               selectedFragment=new Fragment_Statistics();
                               getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                            break;
                    }
                    //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                }
            };
//navigation son



    //BU metod horizontalcalendar seçildiğindeki işlemleri yapar
    public void HorizontalCalenadar()
    {
        weekCalendar.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(DateTime dateTime) {

                try {

                    // Toast.makeText(getApplicationContext(), "You Selected " + dateTime.toString(), Toast.LENGTH_LONG).show();
                    String tarih_=dateTime.toString("yyyy-MM-dd");
                    String dt = tarih_;  // Start date
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar c = Calendar.getInstance();
                    try {
                        c.setTime(sdf.parse(dt));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    c.add(Calendar.MONTH, 1);  // number of days to add
                    dt = sdf.format(c.getTime());  // dt is now the new date



                    String[] mounths=getResources().getStringArray(R.array.aylar);
                    String[] date=tarih_.split("-");
                    btn_bigcal.setText(""+date[0]+"  "+mounths[Integer.parseInt(date[1])]);
                    takvim_date.setText(""+date[2]+" "+mounths[Integer.parseInt(date[1])]+" "+date[0]);

                    if(Sqlcommand(dt)!=0){
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Fragment_Today(dt)).commit();

                    }else{
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Fragment_noData()).commit();
                    }
                    }
               catch (Exception e){

               }

            } });
    }
    //horizontal calendar metod sonu

    //Big calendar (İşlemleri Tamamlandı)
    public void BigCalendar()
    {
        LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout ll= (LinearLayout)inflater.inflate(R.layout.fragment_bigcalendar_layout, null, false);
        CalendarView cv = (CalendarView) ll.getChildAt(0);
        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // TODO Auto-generated method stub
                view.setFocusedMonthDateColor(Color.parseColor("#FF286F"));

                try {
                    weekCalendar.setSelectedDate(new DateTime().withDate(year,month,dayOfMonth));
                    big_year=year;
                    big_ay=month;
                    big_gun=dayOfMonth;
                }
                catch (Exception e){}
            }
        });
        final AlertDialog show = new AlertDialog.Builder(this)
                .setTitle("Takvim")
                .setView(ll)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        big_ay+=1;
                        String date_="";
                        if(big_ay<10){
                            date_=""+big_year+"-0"+big_ay+"-"+big_gun;
                        }
                        if(big_gun<10){
                            date_=""+big_year+"-"+big_ay+"-0"+big_gun;
                        }
                        if(big_gun<10 && big_ay<10){
                            date_=""+big_year+"-0"+big_ay+"-0"+big_gun;
                        }
                        else{
                            date_=""+big_year+"-"+big_ay+"-"+big_gun;
                        }
                        try {
                        String[] mounths=getResources().getStringArray(R.array.aylar);
                        String[] date=date_.split("-");
                        btn_bigcal.setText(""+date[0]+"  "+mounths[Integer.parseInt(date[1])-1]);
                        takvim_date.setText(""+date[2]+" "+mounths[Integer.parseInt(date[1])-1]+" "+date[0]);

                        String xx=""+date[0]+"-"+date[1]+"-"+date[2];

                            if(Sqlcommand(date_)!=0){
                                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Fragment_Today(date_)).commit();
                            }else{
                                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Fragment_noData()).commit();
                            }
                        }
                        catch (Exception e){}

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // Do nothing.
                            }
                        }
                ).show();
    }



    //big calendar son


    public String tariharaligi(String s)
    {
        String dt = s;  // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, 1);  // number of days to add
        dt = sdf.format(c.getTime());
        return dt;
    }

    public int Sqlcommand(String s)
    {
        String s1=tariharaligi(s);
        String sql="SELECT data FROM Pulse where date >= '"+s+"' and date <'"+s1+"'";
        DS_Katmanı ds=new DS_Katmanı(getApplicationContext());
        ds.open();
        int deger=ds.findAverageofToday(sql);
        ds.close();
        return deger;
    }


}
