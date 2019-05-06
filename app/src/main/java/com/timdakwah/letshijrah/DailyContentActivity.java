package com.timdakwah.letshijrah;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.timdakwah.letshijrah.database.dbHelper_Daily;

import java.lang.ref.SoftReference;

import static com.timdakwah.letshijrah.database.dbHelper_Daily.TB_NAME_DAILY;

public class DailyContentActivity extends AppCompatActivity {
    private String passedVar = null;
    private String needText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_content);

        dbHelper_Daily dbHelper = new dbHelper_Daily(this);
        TextView TV_dailytitle = (TextView) findViewById(R.id.dailyTitle);
        TextView TV_dailytask = (TextView) findViewById(R.id.dailyTask2);
        TextView TV_dailydone = (TextView) findViewById(R.id.dailyDone2);

        passedVar = getIntent().getStringExtra("id");
        SQLiteDatabase myDB = dbHelper.getReadableDatabase();
        String[] passArg = {passedVar};
        Cursor myCursor = myDB.rawQuery("SELECT * FROM " + TB_NAME_DAILY + " WHERE id = ?", passArg);
        myCursor.moveToFirst();

        if (myCursor.getCount() > 0) {
            myCursor.moveToPosition(0);
            TV_dailytitle.setText(myCursor.getString(myCursor.getColumnIndex("judul")));
            TV_dailytask.setText(myCursor.getString(myCursor.getColumnIndex("deskripsi")));
            Integer getDone = myCursor.getInt(myCursor.getColumnIndex("selesai"));
            if (getDone == 0) {
                TV_dailydone.setText("Belum selesai!");
            } else if (getDone == 1) {
                TV_dailydone.setText("Sudah selesai!");
            } else {
                TV_dailydone.setText("Error");
            }
        }

        Button myDoneButton = (Button) findViewById(R.id.buttondailyDone);

        myCursor.moveToFirst();
        Integer valueselesai = myCursor.getInt(myCursor.getColumnIndex("selesai"));

        if (valueselesai > 0) {
            myDoneButton.setEnabled(false);
            myDoneButton.setBackgroundColor(getResources().getColor(R.color.tealLightBlueSecondary_text));
            myDoneButton.setText("TUGAS SELESAI!");
        } else {
            myDoneButton.setText("SELESAIKAN TUGAS INI!");
            myDoneButton.setBackgroundColor(getResources().getColor(R.color.tealLightBlueAccent));
            myDoneButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder myBuilder = new AlertDialog.Builder(DailyContentActivity.this);
                    myBuilder.setTitle("Apakah anda yakin?");
                    myBuilder.setMessage("Ketiklah tulisan \"Bismillah\" tanpa kutip lalu pilih Accept untuk melanjutkan proses ini.");

                    final EditText myInput = new EditText(DailyContentActivity.this);
                    myInput.setInputType(InputType.TYPE_CLASS_TEXT);
                    myBuilder.setView(myInput);
                    myBuilder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            needText = myInput.getText().toString();
                            if (writeDatabase(needText)) {
                                Toast.makeText(DailyContentActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(getIntent());
                            } else {
                                Toast.makeText(DailyContentActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    myBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    myBuilder.show();
                }
            });
        }
    }

    private boolean writeDatabase(String needText) {
        dbHelper_Daily myDBH_D = new dbHelper_Daily(this);
        SQLiteDatabase mySQL_D = myDBH_D.getWritableDatabase();
        String passArg = getIntent().getStringExtra("id");
        String[] passArg2 = {passArg};
        Cursor myC = mySQL_D.rawQuery("SELECT selesai FROM " + TB_NAME_DAILY + " WHERE id = ?", passArg2);
        myC.moveToFirst();
        String getDoneR = myC.getString(myC.getColumnIndex("selesai"));

        Boolean result;
        try {
            if (needText.equals("Bismillah") && !getDoneR.equals("1")){
                mySQL_D.execSQL("UPDATE " + TB_NAME_DAILY + " SET selesai = 1 WHERE id = ?", passArg2);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
