package com.example.ozcan.heartrate;

/**
 * Created by ozcan on 4.05.2018.
 */

public class Pulse_Deger {

    String nabiz_tarih;
    int  nabiz_deger;

    public Pulse_Deger(String tarih,int deger){
        nabiz_deger=deger;
        nabiz_tarih=tarih;
    }

    public String getNabiz_tarih() {return nabiz_tarih;}

    public int getNabiz_deger() {return nabiz_deger;}

    public void setNabiz_tarih(String nabiz_tarih) {this.nabiz_tarih = nabiz_tarih;}

    public void setNabiz_deger(int nabiz_deger) {this.nabiz_deger = nabiz_deger;}

    public String toString()
    {
        return ""+nabiz_tarih+" "+nabiz_deger;
    }

    public String DateInverse(String ss){
         String[] parca1=nabiz_tarih.split(" ");
         String[] parca2=parca1[0].split("-");
         String[] parça_saat=parca1[1].split(":");

         return ""+parca2[2]+"-"+parca2[1]+"-"+parca2[0]+" "+parça_saat[0]+":"+parça_saat[1];
    }

}
