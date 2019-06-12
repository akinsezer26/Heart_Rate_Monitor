package com.example.ozcan.heartrate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    boolean isLoged;
    String email,sifre;
    TextView tvSifre,tvEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView tvHesapOlustur=findViewById(R.id.TVHesapOlustur);
        SharedPreferences isLogin= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        isLoged=isLogin.getBoolean("isLogin",false);
        email=isLogin.getString("email","");
        sifre=isLogin.getString("sifre","");

        tvEmail=findViewById(R.id.Login_Email);
        tvSifre=findViewById(R.id.LoginSifre);

        if(isLoged){
            Intent startActivity = new Intent(Login.this, MainEkranActivity.class);
            startActivity(startActivity);
            finish();
        }

        tvHesapOlustur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startActivity = new Intent(Login.this, SignUp.class);
                startActivity(startActivity);
                finish();
            }
        });
    }

    public void GirisYap(View view) {
        if(tvEmail.getText().toString().equals(email) && tvSifre.getText().toString().equals(sifre)) {
            Intent startActivity = new Intent(Login.this, MainEkranActivity.class);
            startActivity(startActivity);
            finish();
        }

        else
            Toast.makeText(this, "Email veya Sifre Hatalı", Toast.LENGTH_SHORT).show();
    }
}
