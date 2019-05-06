package com.timdakwah.letshijrah;

import android.app.AlarmManager;
import android.app.AlertDialog;
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

import com.timdakwah.letshijrah.adapter.Adapter_Daily;
import com.timdakwah.letshijrah.database.dbHelper_Daily;
import com.timdakwah.letshijrah.model.Product_Daily;

import java.text.SimpleDateFormat;
import java.util.List;

import static com.timdakwah.letshijrah.database.dbHelper_Daily.TB_NAME_DAILY;

public class DailyActivity extends AppCompatActivity {
    private ListView myLVProduct;
    private Adapter_Daily myAdapter;
    private List<Product_Daily> myProductList;
    private dbHelper_Daily myDBHelper;

    private String dataID[];
    private Integer countData = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);

        Thread t = new Thread() {
            @Override
            public void run () {
                try {
                    while (!isInterrupted()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView tv = (TextView) findViewById(R.id.textViewClock);
                                long date = System.currentTimeMillis();
                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                                String dateS = sdf.format(date);
                                tv.setText("Hari ini " + dateS);
                            }
                        });
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e){ }
            }
        };
        t.start();
    }

    @Override
    protected void onResume() {
        super.onResume();

        myLVProduct = (ListView) findViewById(R.id.daily_list_listview);
        myDBHelper = new dbHelper_Daily(this);

        /**
         * Bagian di bawah ini mengambil data dari myDBHelper dengan membuka database
         * secara Readable, yang berarti read only databas. Gunakan Writeable untuk
         * menggunakan database dan bisa mengubahnya
         *
         * Database yang digunakan dipanggil dengan menggunakan variabel String yang sudah
         * didefinisikan sebelumnya pada class dbHelper masing-masing database
         */

        SQLiteDatabase myDB = myDBHelper.getReadableDatabase();
        Cursor myCursor = myDB.rawQuery("SELECT * FROM " + TB_NAME_DAILY, null);
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
         */

        myProductList = myDBHelper.getListProduct();
        myAdapter = new Adapter_Daily(this, myProductList);
        myLVProduct.setAdapter(myAdapter);
        myLVProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selection = dataID[position];
                Intent i = new Intent(DailyActivity.this, DailyContentActivity.class);
                i.putExtra("id", selection);
                startActivity(i);
            }
        });

        Button resetbutton = (Button) findViewById(R.id.buttonResetD);
        resetbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(DailyActivity.this);
                myBuilder.setTitle("Mereset seluruh proses dari tantangan harian?");

                myBuilder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ResetDaily();
                        Toast.makeText(DailyActivity.this, "Success!", Toast.LENGTH_SHORT).show();
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

    private void ResetDaily () {
        dbHelper_Daily myDBHelper = new dbHelper_Daily(this);
        SQLiteDatabase mySQL = myDBHelper.getWritableDatabase();
        mySQL.execSQL("UPDATE " + TB_NAME_DAILY + " SET selesai = 0");
    }
}
