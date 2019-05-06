/**
 * Pada bagian ini akan mengambil data dari database untuk challenge_list yang mana berisikan
 * tugas-tugas yang harus dijalankan saat memilih salah satu pilihan challenge. Setelah itu
 * akan ditampilkan nama challenge, banyaknya penyelesaiain, dan list dari tugas-tugasnya.
 *
 * Database yang digunakan  : db_lets_hijrah.sqlite
 * Tabel yang digunakan     : tb_challenge_list
 */

package com.timdakwah.letshijrah;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.timdakwah.letshijrah.adapter.Adapter_ChallengesList;
import com.timdakwah.letshijrah.database.dbHelper_Challenges;
import com.timdakwah.letshijrah.database.dbHelper_ChallengesList;
import com.timdakwah.letshijrah.model.Product_ChallengesList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import static com.timdakwah.letshijrah.ImportParameters.DB_NAME;
import static com.timdakwah.letshijrah.ImportParameters.DB_PATH;
import static com.timdakwah.letshijrah.database.dbHelper_Challenges.TB_NAME_CHALLENGES;
import static com.timdakwah.letshijrah.database.dbHelper_ChallengesList.TB_NAME_CHALLENGESLIST;

public class ChallengeListActivity extends AppCompatActivity {

    /**
     * Definisikan variabel yang akan digunakan
     *
     * Variabel data[] digunakan untuk mendapatkan hasil database list tugas
     * Variabel countData digunakan untuk mendapatkan banyaknya row pada database
     */

    private ListView myLVProduct;
    private Adapter_ChallengesList myAdapter;
    private List<Product_ChallengesList> myProductList;
    private dbHelper_ChallengesList myDBHelper;
    private dbHelper_Challenges myDBHC;

    private String data[] = null;
    private Integer countData = 0;
    private String passedID_C = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_list);
    }

    @Override
    protected void onResume () {
        super.onResume();
        String passed1 = getIntent().getStringExtra("challenge_title");
        String passed2 = getIntent().getStringExtra("challenge_progress");
        String passed3 = getIntent().getStringExtra("challenge_maxprogress");

        passedID_C = getIntent().getStringExtra("challenge_id");
        myLVProduct = findViewById(R.id.challenge_list_listview);
        myDBHelper = new dbHelper_ChallengesList(this);
        myDBHC = new dbHelper_Challenges(this);

        /**
         * Bagian di bawah ini mengambil data dari myDBHelper dengan membuka database
         * secara Readable, yang berarti read only databas. Gunakan Writeable untuk
         * menggunakan database dan bisa mengubahnya
         *
         * Database yang digunakan dipanggil dengan menggunakan variabel String yang sudah
         * didefinisikan sebelumnya pada class dbHelper masing-masing database
         */

        /**
         * =================== UJI COBA ===================
         *
         * Hasil keluaran:
         * -> myCursor mendapatkan hasil tabel dari query yang ada
         * -> countData berisi banyaknya row
         * -> data[] berisi isi "id" dari data, lihat bagian FOR STATEMENT
         */

        Integer beginRowInt = Integer.parseInt(passedID_C);
        Integer endRowInt = beginRowInt * 100 + 99;
        beginRowInt = beginRowInt * 100 + 1;

        String beginRowStr = new Integer(beginRowInt).toString();
        String endRowStr = new Integer(endRowInt).toString();

        /**
         * beginRowStr  : 101, 201, 301, ...
         * endRowStr    : 199, 299, 399, ...
         */

        String[] myCursorArg = {beginRowStr, endRowStr};

        SQLiteDatabase myDB = myDBHelper.getReadableDatabase();
        Cursor myCursor = myDB.rawQuery("SELECT id FROM " + TB_NAME_CHALLENGESLIST + " WHERE id BETWEEN ? AND ?", myCursorArg);
        myCursor.moveToFirst();
        countData = myCursor.getCount();
        data = new String[countData];

        for (int i = 0 ; i < countData ; i++){
            myCursor.moveToPosition(i);
            data[i] = myCursor.getString(0).toString();
        }

        /**
         * ================================================
         * Status   : Sukses
         * Pada     : Senin 17 Desember 2018, pukul 14:59 WIB
         * ================================================
         */


        /**
         * =================== UJI COBA ===================
         * Hasil keluaran:
         * -> memanggil tabel TB_CHALLENGES untuk mendapatkan nilai {selesai, total}
         * -> nilai dipakai untuk menampilkan " Progres : __ % "
         */

        Integer idC_i = Integer.parseInt(passedID_C);
        String[] idC_s = {idC_i.toString()};
        SQLiteDatabase myDB2 = myDBHC.getReadableDatabase();
        Cursor myC2 = myDB2.rawQuery("SELECT * FROM " + TB_NAME_CHALLENGES + " WHERE id = ?", idC_s);
        myC2.moveToFirst();

        Integer prog = myC2.getInt(myC2.getColumnIndex("selesai"));
        Integer max = myC2.getInt(myC2.getColumnIndex("total"));

        /**
         * ================================================
         * Status   : Sukses
         * Pada     : Rabu 09 Januari 2019, pukul 23:16 WIB
         * ================================================
         */


        /**
         * =================== UJI COBA ===================
         * Hasil keluaran:
         * -> mendapatkan passed parameter dari class java sebelumnya
         * -> menampilkan String pada XML yang sesuai
         */

        //Integer prog = Integer.parseInt(passed2);
        //Integer max =  Integer.parseInt(passed3);
        prog = (prog * 100) / max;
        passed2 = new Integer(prog).toString();
        TextView TV_C_Title1 = (TextView) findViewById(R.id.daily_list_title);
        TextView TV_C_Title2 = (TextView) findViewById(R.id.daily_list_subtitle);

        TV_C_Title1.setText(passed1);
        TV_C_Title2.setText("Progres: " + passed2 + "%");

        /**
         * ================================================
         * Status   : Sukses
         * Pada     : Senin 17 Desember 2018 pukul 16:22 WIB
         * ================================================
         */

        /**
         * Pada bagian d bawah ini menampilkan seluruh hasil dari database ke dalam XML
         * -> myProductList akan berisikan seluruh isi database, dengan getListProduct()
         * -> myAdapter akan berisikan fungsi Adapter keseluruhan dari banyaknya data di myProductList
         * -> myLVProduct akan menampilkan hasil dari myAdapter secara sekuensial, berurut
         * -> myLVProduct juga membuat sebuah link (Intent) ke masing-masing class selanjutnya
         */

        myProductList = myDBHelper.getListProduct(beginRowStr, endRowStr);
        myAdapter = new Adapter_ChallengesList(this, myProductList);
        myLVProduct.setAdapter(myAdapter);

        myLVProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            /**
             * @params parent       : ...
             * @params view         : ...
             * @params position     : posisi dari data ke berapa per yang ditampilkan, dari 0
             * @params id           : id dari tiap-tiap link yang dibuat, berawal dari 1
             *
             * selection menggunakan data[] untuk mendapatkan hasil array-nya untuk di passing
             * ke class selanjutnya
             */

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String selection = data[position];
                Intent myIntent = new Intent(ChallengeListActivity.this, ChallengeContentActivity.class);
                myIntent.putExtra("sub_challenge_id", selection);
                myIntent.putExtra("challenge_id", passedID_C);
                startActivity(myIntent);
            }
        });

        myCursor.close();

        Button resetbutton = (Button) findViewById(R.id.buttonResetCS);
        resetbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(ChallengeListActivity.this);
                myBuilder.setTitle("Mereset seluruh proses dari tantangan");
                myBuilder.setMessage("Apakah anda yakin ingin mereset seluruh proses dari tantangan ini?");

                myBuilder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ResetChallenge();
                        Toast.makeText(ChallengeListActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(getIntent());
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

    private void ResetChallenge () {
        passedID_C = getIntent().getStringExtra("challenge_id");
        Integer passedID_C_i = Integer.parseInt(passedID_C);

        Integer first = passedID_C_i * 100 + 1;
        Integer last = passedID_C_i * 100 + 99;

        String[] args_myDBHC = {passedID_C_i.toString()};
        String[] args_myDBHelper = {first.toString(), last.toString()};

        myDBHC = new dbHelper_Challenges(this);
        SQLiteDatabase mySQL1 = myDBHelper.getWritableDatabase();

        myDBHelper = new dbHelper_ChallengesList(this);
        SQLiteDatabase mySQL2 = myDBHelper.getWritableDatabase();

        mySQL1.execSQL("UPDATE " + TB_NAME_CHALLENGES + " SET selesai = 0 WHERE id = ?", args_myDBHC);
        mySQL2.execSQL("UPDATE " + TB_NAME_CHALLENGESLIST + " SET selesai = 0 WHERE id BETWEEN ? AND ?", args_myDBHelper);
    }
}