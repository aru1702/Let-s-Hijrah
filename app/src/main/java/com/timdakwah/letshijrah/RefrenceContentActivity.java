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
import com.timdakwah.letshijrah.database.dbHelper_References;

import static com.timdakwah.letshijrah.database.dbHelper_Challenges.TB_NAME_CHALLENGES;
import static com.timdakwah.letshijrah.database.dbHelper_ChallengesList.TB_NAME_CHALLENGESLIST;
import static com.timdakwah.letshijrah.database.dbHelper_References.TB_NAME_REFERENCES;

public class RefrenceContentActivity extends Activity {
    /**
     * Membuat variabel untuk parameter yang dipassing dari class java sebelumnya
     */
    private String passedVar = null;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refrence_content);

        /**
         * -> definisikan setiap textview pada XML
         * -> definisikan dbHelper yang digunakan
         * -> mengambil hasil passing parameter dengan getIntent dan getStringExtra
         */

        dbHelper_References dbHelper = new dbHelper_References(this);
        TextView TV_Title = (TextView) findViewById(R.id.refrence_content_title);
        TextView TV_Desc = (TextView) findViewById(R.id.refrence_content_descripton_subheading);
        TextView TV_Sub1 = (TextView) findViewById(R.id.refrence_content_doa_subheading);
        TextView TV_Sub2 = (TextView) findViewById(R.id.refrence_content_arti_subheading);

        /**
         * Untuk query dengan parameter gunakan argument, jangan langsung
         */

        passedVar = getIntent().getStringExtra("id");
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] selectionArgs = {passedVar};
        Cursor myCursor = db.rawQuery("SELECT * FROM " + TB_NAME_REFERENCES + " WHERE id = ?", selectionArgs);

        myCursor.moveToFirst();
        if (myCursor.getCount() > 0) {
            myCursor.moveToPosition(0);
            TV_Title.setText(myCursor.getString(myCursor.getColumnIndex("judul")));
            TV_Desc.setText(myCursor.getString(myCursor.getColumnIndex("deskripsi")));
            TV_Sub1.setText(myCursor.getString(myCursor.getColumnIndex("doa")));
            TV_Sub2.setText(myCursor.getString(myCursor.getColumnIndex("arti")));
        }
    }
}