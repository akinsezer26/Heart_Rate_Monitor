package com.example.ozcan.heartrate;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ozcan on 15.05.2018.
 */

public class SatirAdaptor_MY  extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<MounthStaticsClass> mPulseListem;
    private String date;


    public SatirAdaptor_MY(Activity activity, List<MounthStaticsClass>  pulse_degers,String tarih) {
        //XML'i alıp View'a çevirecek inflater'ı örnekleyelim
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        //gösterilecek listeyi de alalım
        mPulseListem = pulse_degers;
        date=tarih;

    }

    @Override
    public int getCount() {

        return mPulseListem.size();
    }

    @Override
    public MounthStaticsClass getItem(int position) {
        //şöyle de olabilir: public Object getItem(int position)
        return mPulseListem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View satirView;

        satirView = mInflater.inflate(R.layout.satir_layout, null);
        TextView texttarih =
                (TextView) satirView.findViewById(R.id.txt_satir_date);
        TextView textnabız =
                (TextView) satirView.findViewById(R.id.txt_satir_data);

        MounthStaticsClass pulse = mPulseListem.get(position);


        String[] s={"Ocak","Şubat","Mart","Nisan","Mayıs","Haziran","Temmuz","Ağustos","Eylül","Ekim","Kasım","Aralık"};
        String ss=""+pulse.getAvg();


        texttarih.setText(""+s[position]+date);/////////

        if(pulse.getAvg()==0){
            textnabız.setText("kayıt yok");
            textnabız.setTextSize(15.f);
        }
        else{
        textnabız.setText(ss);////////////////

        }

        return satirView;
    }
}
