package com.example.ozcan.heartrate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SignUp extends AppCompatActivity {

    TextView tvisim,tvsoyisim,tvsifre,tvtelefon,tvemail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        tvisim=findViewById(R.id.SU_Isim);
        tvsoyisim=findViewById(R.id.SU_Soyisim);
        tvsifre=findViewById(R.id.SU_Sifre);
        tvtelefon=findViewById(R.id.SU_Telefon);
        tvemail=findViewById(R.id.SU_Email);
    }

    public void KayitOl(View view) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("isim", tvisim.getText().toString());
        editor.putString("soyisim", tvsoyisim.getText().toString());
        editor.putString("sifre", tvsifre.getText().toString());
        editor.putString("telefon", tvtelefon.getText().toString());
        editor.putString("email", tvemail.getText().toString());

        editor.commit();

        Intent startActivity = new Intent(SignUp.this, MainEkranActivity.class);
        startActivity(startActivity);
        finish();
    }
}
