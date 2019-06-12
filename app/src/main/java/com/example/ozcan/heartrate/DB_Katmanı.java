package com.example.ozcan.heartrate;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ozcan on 30.04.2018.
 */

public class DB_Katmanı extends SQLiteOpenHelper {


    public DB_Katmanı(Context context) {
        super(context, "Pulse", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_command="create table Pulse ( id integer primary key autoincrement , date DATETIME CURRENT_TIMESTAMP not null , data integer not null )";
        db.execSQL(sql_command);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists Pulse");
    }
}
