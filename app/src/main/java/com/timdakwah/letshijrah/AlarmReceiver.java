package com.timdakwah.letshijrah;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import com.timdakwah.letshijrah.database.dbHelper_Daily;

import java.util.Calendar;

import static com.timdakwah.letshijrah.database.dbHelper_Daily.TB_NAME_DAILY;

public class AlarmReceiver extends BroadcastReceiver {

    private dbHelper_Daily myDBHelper;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            return;
        }

        SQLiteDatabase sql = myDBHelper.getWritableDatabase();
        sql.execSQL("UPDATE " + TB_NAME_DAILY + " SET selesai = 0");
    }
}
