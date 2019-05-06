package com.timdakwah.letshijrah;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.timdakwah.letshijrah.database.dbHelper_Challenges;
import com.timdakwah.letshijrah.database.dbHelper_ChallengesList;
import com.timdakwah.letshijrah.database.dbHelper_Daily;
import com.timdakwah.letshijrah.database.dbHelper_References;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

import static com.timdakwah.letshijrah.ImportParameters.DB_NAME;
import static com.timdakwah.letshijrah.ImportParameters.DB_PATH;

public class SplashScreen extends AppCompatActivity {
    TextView tvSplash;
    private dbHelper_Challenges myDBH_Challenges;
    private dbHelper_ChallengesList myDBH_ChallengesList;
    private dbHelper_Daily myDBH_Daily;
    private dbHelper_References myDBH_References;
    File database;

    private AlarmManager alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        /**
         * TEST ALARM PER DAY
         */

        AlarmManager myAM = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent myIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent myPI = PendingIntent.getBroadcast(this, 0, myIntent, 0);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, 00);

        myAM.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, myPI);

        /**
         * Calling AlarmReceiver class
         */

        tvSplash = (TextView)findViewById(R.id.splash_text);
        Handler dbHandler = new Handler();

        myDBH_Challenges = new dbHelper_Challenges(this);
        database = getApplicationContext().getDatabasePath(ImportParameters.DB_NAME);
        if (!database.exists()) {
            myDBH_Challenges.getReadableDatabase();
            if (copyDatabase(this)) {
                dbHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // just wait until 0.5 s
                    }
                }, 500);
                Toast.makeText(this, "Data has been copied successfully!", Toast.LENGTH_SHORT).show();
            } else {
                dbHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // just wait until 0.5 s
                    }
                }, 500);
                Toast.makeText(this, "Data failed to loaded!", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        myDBH_ChallengesList = new dbHelper_ChallengesList(this);
        database = getApplicationContext().getDatabasePath(ImportParameters.DB_NAME);
        if (!database.exists()) {
            myDBH_ChallengesList.getReadableDatabase();
            if (copyDatabase(this)) {
                dbHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // just wait until 0.5 s
                    }
                }, 500);
                Toast.makeText(this, "Data has been copied successfully!", Toast.LENGTH_SHORT).show();
            } else {
                dbHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // just wait until 0.5 s
                    }
                }, 500);
                Toast.makeText(this, "Data failed to loaded!", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        myDBH_Daily = new dbHelper_Daily(this);
        database = getApplicationContext().getDatabasePath(ImportParameters.DB_NAME);
        if (!database.exists()) {
            myDBH_Daily.getReadableDatabase();
            if (copyDatabase(this)) {
                dbHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // just wait until 0.5 s
                    }
                }, 500);
                Toast.makeText(this, "Data has been copied successfully!", Toast.LENGTH_SHORT).show();
            } else {
                dbHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // just wait until 0.5 s
                    }
                }, 500);
                Toast.makeText(this, "Data failed to loaded!", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        myDBH_References = new dbHelper_References(this);
        database = getApplicationContext().getDatabasePath(ImportParameters.DB_NAME);
        if (!database.exists()) {
            myDBH_References.getReadableDatabase();
            if (copyDatabase(this)) {
                dbHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // just wait until 0.5 s
                    }
                }, 500);
                Toast.makeText(this, "Data has been copied successfully!", Toast.LENGTH_SHORT).show();
            } else {
                dbHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // just wait until 0.5 s
                    }
                }, 500);
                Toast.makeText(this, "Data failed to loaded!", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        }, 3000L); //3000 L = 3 detik

    }

    /**
     * Fungsi copyDatabase digunakan untuk mengkopi seluruh database di ekstenal ke internal
     *
     * Fungsi ini dipanggil setelah membuka isi database ekstenal pada file sqlite, lalu dikopi
     * seluruhnya ke dalam sqlite yang berada di dalam aplikasi dan menjadi database lokal.
     *
     * @param context
     * @return boolean: true/false
     */

    private boolean copyDatabase (Context context) {
        try {
            InputStream inputStream = context.getAssets().open(DB_NAME);
            String outFileName = DB_PATH + DB_NAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[] buff = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}