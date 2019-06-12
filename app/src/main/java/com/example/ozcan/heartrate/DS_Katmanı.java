package com.example.ozcan.heartrate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ozcan on 30.04.2018.
 */

public class DS_Katmanı {
    SQLiteDatabase db;
    DB_Katmanı bdb;
    private String sqlcommand;

    public DS_Katmanı(Context context){
        bdb=new DB_Katmanı(context);
    }
    public void open()
    {
        db=bdb.getWritableDatabase();
    }
    public void close()
    {
        bdb.close();
    }

    public void addPulse(Pulse_Deger p) {
        ContentValues val=new ContentValues();
        val.put("date",p.getNabiz_tarih());
        val.put("data",p.getNabiz_deger());
        db.insert("Pulse",null,val);
    }

    public List<Pulse_Deger> listele(String _date){


        ArrayList<Pulse_Deger> liste=new ArrayList<Pulse_Deger>();
        Cursor c = db.rawQuery(""+_date,null);
                c.moveToFirst();
                if (c.moveToFirst()) {
                    while (!c.isAfterLast()) {
                        String tarih=c.getString(1);
                Integer deger=c.getInt(2);
                Pulse_Deger k=new Pulse_Deger(tarih,deger);
                liste.add(k);
                c.moveToNext();
            }
        }
        Log.e("list size",""+liste.size());
        Log.e("list :",""+liste.toString());

        return liste;
    }

    public Integer findMax(String _date){
        int max=0;
        Cursor c=db.rawQuery(""+_date,null);
        if (c != null) {
            c.moveToFirst();
             max = c.getInt(0);
            return max;
        }
         else
        return 0;
    }

    public Integer findMin(String _date){
        int min=0;
        Cursor c=db.rawQuery(""+_date,null);
        if (c != null) {
            c.moveToFirst();
            min = c.getInt(0);
            return min;
        }
        else
            return 0;

    }

    public Integer findAverageofToday(String _data){
        int avg=0;
        int count=0;
        Cursor c = db.rawQuery(""+_data,null);
        c.moveToFirst();
        if (c.moveToFirst()) {
            while (!c.isAfterLast()){
                Integer deger=c.getInt(0);
                avg+=deger;
                c.moveToNext();
                count++;
            }
            return avg/count;
        }
        return 0;
    }

    public boolean delete_selected_item(){
        String sglcommand="delete from Pulse";
        db.execSQL(sglcommand);
        Cursor c=db.rawQuery(sglcommand,null);
        c.moveToFirst();
        if(c.getColumnCount()<=0){
            return  false;
        }
        return true;
    }

    public String getSqlcommand() {
        return sqlcommand;
    }

    public void setSqlcommand(String sqlcommand) {
        this.sqlcommand = sqlcommand;
    }
}
