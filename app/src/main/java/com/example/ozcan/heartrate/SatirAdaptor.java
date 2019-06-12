package com.example.ozcan.heartrate;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ozcan on 4.05.2018.
 */

public class SatirAdaptor extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Pulse_Deger> mPulseListem;

    public SatirAdaptor(Activity activity, List<Pulse_Deger>  pulse_degers) {
        //XML'i alıp View'a çevirecek inflater'ı örnekleyelim
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        //gösterilecek listeyi de alalım
        mPulseListem = pulse_degers;
    }

    @Override
    public int getCount() {
        return mPulseListem.size();
    }

    @Override
    public Pulse_Deger getItem(int position) {
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

        Pulse_Deger pulse = mPulseListem.get(position);

        String ss=pulse.DateInverse(pulse.getNabiz_tarih());

        texttarih.setText(ss);
        textnabız.setText(""+pulse.getNabiz_deger());
        return satirView;
    }
}
