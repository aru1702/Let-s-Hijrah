/**
 * Pada bagian ini akan mengambil data dari database untuk challenge_list yang mana berisikan
 * tugas-tugas yang harus dijalankan saat memilih salah satu pilihan challenge. Setelah itu
 * akan ditampilkan nama challenge, banyaknya penyelesaiain, dan list dari tugas-tugasnya.
 *
 * Database yang digunakan  : db_lets_hijrah.sqlite
 * Tabel yang digunakan     : tb_challenge
 */

package com.timdakwah.letshijrah;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.timdakwah.letshijrah.adapter.Adapter_Challenges;
import com.timdakwah.letshijrah.database.dbHelper_Challenges;
import com.timdakwah.letshijrah.database.dbHelper_ChallengesList;
import com.timdakwah.letshijrah.model.Product_Challenges;

import java.util.List;

import static com.timdakwah.letshijrah.database.dbHelper_Challenges.TB_NAME_CHALLENGES;
import static com.timdakwah.letshijrah.database.dbHelper_ChallengesList.TB_NAME_CHALLENGESLIST;

public class ChallengeActivity extends AppCompatActivity {

    /**
     * Definisikan variabel yang akan digunakan
     *
     * Variabel dataTitle(1/2)[] digunakan untuk mendapatkan hasil database list tugas
     * Variabel countData digunakan untuk mendapatkan banyaknya row pada database
     */

    private ListView myLVProduct;
    private Adapter_Challenges myAdapter;
    private List<Product_Challenges> myProductList;
    private dbHelper_Challenges myDBHelper;

    private dbHelper_ChallengesList myDBHelperList;

    private String dataID[] = null;
    private String dataTitle1[] = null;
    private String dataTitle2[] = null;
    private String dataTitle3[] = null;
    private Integer countData = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);
    }

    @Override
    protected void onResume() {
        super.onResume();

        myLVProduct = (ListView) findViewById(R.id.Challenge_ListView);
        myDBHelper = new dbHelper_Challenges(this);

        /**
         * Bagian di bawah ini mengambil data dari myDBHelper dengan membuka database
         * secara Readable, yang berarti read only databas. Gunakan Writeable untuk
         * menggunakan database dan bisa mengubahnya
         *
         * Database yang digunakan dipanggil dengan menggunakan variabel String yang sudah
         * didefinisikan sebelumnya pada class dbHelper masing-masing database
         */

        SQLiteDatabase myDB = myDBHelper.getReadableDatabase();
        Cursor myCursor = myDB.rawQuery("SELECT * FROM " + TB_NAME_CHALLENGES, null);
        myCursor.moveToFirst();

        countData = myCursor.getCount();
        dataID = new String[countData];
        dataTitle1 = new String[countData];
        dataTitle2 = new String[countData];
        dataTitle3 = new String[countData];

        for (int i = 0 ; i < countData ; i++) {
            myCursor.moveToPosition(i);
            dataID[i] = myCursor.getString(0).toString();
            dataTitle1[i] = myCursor.getString(1).toString();
            dataTitle2[i] = myCursor.getString(2).toString();
            dataTitle3[i] = myCursor.getString(3).toString();
        }

        /**
         * Hasilnya untuk mendapatkan seluruh data dari database lalu dimasukkan ke dalam
         * variabel array untuk memuat suatu data.
         *
         * -> myCursor mendapatkan hasil tabel dari query yang ada
         * -> countData berisi banyaknya row
         * -> dataID[] berisi isi "id" dari database
         * -> dataTitle1[] berisi isi "judul" dari data, lihat bagian FOR STATEMENT
         * -> dataTitle2[] berisi isi "banyakselesai" dari data, lihat bagian FOR STATEMENT
         */

        myProductList = myDBHelper.getListProduct();
        myAdapter = new Adapter_Challenges(this, myProductList);
        myLVProduct.setAdapter(myAdapter);
        myLVProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            /**
             * @params parent       : ...
             * @params view         : ...
             * @params position     : posisi dari data ke berapa per yang ditampilkan, dari 0
             * @params id           : id dari tiap-tiap link yang dibuat, berawal dari 1
             *
             */

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectionID = dataID[position];
                String selection1 = dataTitle1[position];
                String selection2 = dataTitle2[position];
                String selection3 = dataTitle3[position];

                /**
                 * Bagian di bawah ini mengambil data pada masing-masing variabel array dataTitle(1/2)[]
                 * untuk di passing ke class java selanjutnya.
                 */

                Intent myIntent = new Intent(ChallengeActivity.this, ChallengeListActivity.class);
                myIntent.putExtra("challenge_id", selectionID);
                myIntent.putExtra("challenge_title", selection1);
                myIntent.putExtra("challenge_progress", selection2);
                myIntent.putExtra("challenge_maxprogress", selection3);
                startActivity(myIntent);
            }
        });

        Button resetbutton = (Button) findViewById(R.id.buttonResetC);
        resetbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(ChallengeActivity.this);
                myBuilder.setTitle("Mereset seluruh proses dari tantangan");
                myBuilder.setMessage("Apakah anda yakin ingin mereset seluruh proses dari seluruh tantangan?\n\n" +
                                    "HINT: Anda bisa mereset salah satu level tantangan dari pilihan menu level tersebut.");

                myBuilder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ResetAll();
                        Toast.makeText(ChallengeActivity.this, "Success!", Toast.LENGTH_SHORT).show();
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

    private void ResetAll () {
        myDBHelper = new dbHelper_Challenges(this);
        SQLiteDatabase mySQL1 = myDBHelper.getWritableDatabase();

        myDBHelperList = new dbHelper_ChallengesList(this);
        SQLiteDatabase mySQL2 = myDBHelperList.getWritableDatabase();

        try {
            mySQL1.execSQL("UPDATE " + TB_NAME_CHALLENGES + " SET selesai = 0");
            mySQL2.execSQL("UPDATE " + TB_NAME_CHALLENGESLIST + " SET selesai = 0");
        } catch (Exception e) { }
    }
}
