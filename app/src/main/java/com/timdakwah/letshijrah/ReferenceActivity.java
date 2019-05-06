/**
 * Pada bagian ini akan mengambil data dari database untuk challenge_list yang mana berisikan
 * tugas-tugas yang harus dijalankan saat memilih salah satu pilihan challenge. Setelah itu
 * akan ditampilkan nama challenge, banyaknya penyelesaiain, dan list dari tugas-tugasnya.
 *
 * Database yang digunakan  : db_lets_hijrah.sqlite
 * Tabel yang digunakan     : tb_challenge
 */

package com.timdakwah.letshijrah;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.timdakwah.letshijrah.adapter.Adapter_Challenges;
import com.timdakwah.letshijrah.adapter.Adapter_References;
import com.timdakwah.letshijrah.database.dbHelper_Challenges;
import com.timdakwah.letshijrah.database.dbHelper_References;
import com.timdakwah.letshijrah.model.Product_Challenges;
import com.timdakwah.letshijrah.model.Product_References;

import java.util.List;

import static com.timdakwah.letshijrah.database.dbHelper_Challenges.TB_NAME_CHALLENGES;
import static com.timdakwah.letshijrah.database.dbHelper_References.TB_NAME_REFERENCES;

public class ReferenceActivity extends AppCompatActivity {

    /**
     * Definisikan variabel yang akan digunakan
     *
     * Variabel dataTitle(1/2)[] digunakan untuk mendapatkan hasil database list tugas
     * Variabel countData digunakan untuk mendapatkan banyaknya row pada database
     */

    private ListView myLVProduct;
    private Adapter_References myAdapter;
    private List<Product_References> myProductList;
    private dbHelper_References myDBHelper;

    private String dataID[] = null;
    private Integer countData = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reference);

        myLVProduct = (ListView) findViewById(R.id.list_doa_dzikir);
        myDBHelper = new dbHelper_References(this);

        /**
         * Bagian di bawah ini mengambil data dari myDBHelper dengan membuka database
         * secara Readable, yang berarti read only databas. Gunakan Writeable untuk
         * menggunakan database dan bisa mengubahnya
         *
         * Database yang digunakan dipanggil dengan menggunakan variabel String yang sudah
         * didefinisikan sebelumnya pada class dbHelper masing-masing database
         */

        SQLiteDatabase myDB = myDBHelper.getReadableDatabase();
        Cursor myCursor = myDB.rawQuery("SELECT * FROM " + TB_NAME_REFERENCES, null);
        myCursor.moveToFirst();

        countData = myCursor.getCount();
        dataID = new String[countData];

        for (int i = 0 ; i < countData ; i++) {
            myCursor.moveToPosition(i);
            dataID[i] = myCursor.getString(0).toString();
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
        myAdapter = new Adapter_References(this, myProductList);
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

                /**
                 * Bagian di bawah ini mengambil data pada masing-masing variabel array dataTitle(1/2)[]
                 * untuk di passing ke class java selanjutnya.
                 *
                 * Adapun pemakaian switch case untuk mengarahkan berdasarkan ID_ENTITY apakah masuk ke
                 * class java ke-1, ke-2, dst.
                 */

                Intent myIntent = new Intent(ReferenceActivity.this, RefrenceContentActivity.class);
                myIntent.putExtra("id", selectionID);
                startActivity(myIntent);
            }
        });
    }
}
