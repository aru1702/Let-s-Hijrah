/**
 * Pada bagian ini akan menampilkan hasil dari masing-masing detil dengan data dari database
 * yang bersangkutan. Adanya passing parameter dari class java sebelumnya yaitu berupa id
 * dari database sehingga bisa digunakan QUERY untuk mengambil data pada row yang sesuai.
 *
 * Database yang digunakan  : db_lets_hijrah.sqlite
 * Table yang digunakan     : TB_NAME_CHALLENGESLIST
 */

package com.timdakwah.letshijrah;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.timdakwah.letshijrah.database.dbHelper_Challenges;
import com.timdakwah.letshijrah.database.dbHelper_ChallengesList;

import static com.timdakwah.letshijrah.database.dbHelper_Challenges.TB_NAME_CHALLENGES;
import static com.timdakwah.letshijrah.database.dbHelper_ChallengesList.TB_NAME_CHALLENGESLIST;

public class ChallengeContentActivity extends Activity {
    /**
     * Membuat variabel untuk parameter yang dipassing dari class java sebelumnya
     */
    private String passedVar = null;
    private String needText = null;
    private String passedID_C = null;
    private boolean reloadNeeded = true;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_content);

        /**
         * -> definisikan setiap textview pada XML
         * -> definisikan dbHelper yang digunakan
         * -> mengambil hasil passing parameter dengan getIntent dan getStringExtra
         */

        dbHelper_ChallengesList dbHelper = new dbHelper_ChallengesList(this);
        TextView TV_D_Judul = (TextView) findViewById(R.id.Descjudul);
        TextView TV_D_Desc = (TextView) findViewById(R.id.Descdeskripsi);
        TextView TV_D_Tugas = (TextView) findViewById(R.id.Desctugas);
        TextView TV_D_Done = (TextView) findViewById(R.id.Descpenyelesaian);

        /**
         * Untuk query dengan parameter gunakan argument, jangan langsung
         */

        passedVar = getIntent().getStringExtra("sub_challenge_id");
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] selectionArgs = {passedVar};
        Cursor myCursor = db.rawQuery("SELECT * FROM " + TB_NAME_CHALLENGESLIST + " WHERE id = ?", selectionArgs);

        myCursor.moveToFirst();
        if (myCursor.getCount() > 0) {
            myCursor.moveToPosition(0);
            TV_D_Judul.setText(myCursor.getString(myCursor.getColumnIndex("judul")).toString());
            TV_D_Desc.setText(myCursor.getString(myCursor.getColumnIndex("deskripsi")).toString());
            TV_D_Tugas.setText(myCursor.getString(myCursor.getColumnIndex("tugas")).toString());
            Integer getSelesai = myCursor.getInt(myCursor.getColumnIndex("selesai"));
            if (getSelesai == 0) {
                TV_D_Done.setText("Belum selesai!");
            } else if (getSelesai == 1){
                TV_D_Done.setText("Sudah selesai!");
            } else {
                TV_D_Done.setText("Error data received!");
            }
        }

        Button myDoneButton = (Button) findViewById(R.id.buttonAcceptDone);

        myCursor.moveToFirst();
        Integer valueselesai = myCursor.getInt(myCursor.getColumnIndex("selesai"));
        if (valueselesai > 0) {
            myDoneButton.setEnabled(false);
            myDoneButton.setBackgroundColor(getResources().getColor(R.color.tealLightBlueSecondary_text));
            myDoneButton.setText("TANTANGAN SELESAI!");
        } else {
            myDoneButton.setText("SELESAIKAN TANTANGAN INI!");
            myDoneButton.setBackgroundColor(getResources().getColor(R.color.tealLightBlueAccent));
            myDoneButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder myBuilder = new AlertDialog.Builder(ChallengeContentActivity.this);
                    myBuilder.setTitle("Apakah anda yakin?");
                    myBuilder.setMessage("Ketiklah tulisan \"Bismillah\" tanpa kutip lalu pilih Accept untuk melanjutkan proses ini.");

                    final EditText myInput = new EditText(ChallengeContentActivity.this);
                    myInput.setInputType(InputType.TYPE_CLASS_TEXT);
                    myBuilder.setView(myInput);
                    myBuilder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            needText = myInput.getText().toString();
                            if (writeDatabase(needText)) {
                                Toast.makeText(ChallengeContentActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(getIntent());
                            } else {
                                Toast.makeText(ChallengeContentActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
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
        dbHelper_Challenges myDBH1 = new dbHelper_Challenges(this);
        SQLiteDatabase mySQLDB1 = myDBH1.getWritableDatabase();
        passedID_C = getIntent().getStringExtra("challenge_id");
        String[] argDB1 = {passedID_C};

        dbHelper_ChallengesList myDBH2 = new dbHelper_ChallengesList(this);
        SQLiteDatabase mySQLDB2 = myDBH2.getWritableDatabase();
        passedVar = getIntent().getStringExtra("sub_challenge_id");
        String[] argDB2 = {passedVar};

        Cursor myC = mySQLDB2.rawQuery("SELECT selesai FROM " + TB_NAME_CHALLENGESLIST + " WHERE id = ?", argDB2);
        myC.moveToFirst();
        String getResult = myC.getString(myC.getColumnIndex("selesai")).toString();

        Boolean result;
        try {
            if (needText.equals("Bismillah") && !getResult.equals("1")) {
                mySQLDB1.execSQL("UPDATE " + TB_NAME_CHALLENGES + " SET selesai = selesai + 1 WHERE id = ?", argDB1);
                mySQLDB2.execSQL("UPDATE " + TB_NAME_CHALLENGESLIST + " SET selesai = 1 WHERE id = ?", argDB2);
                result = true;
            } else {
                result = false;
            }
        } catch (Exception e) {
            result = false;
        }
        return result;
    }
}