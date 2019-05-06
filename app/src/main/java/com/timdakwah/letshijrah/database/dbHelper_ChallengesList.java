/**
 * Pada bagian ini kita akan membuat struktur database dan mengimpor
 * dari database yang sudah kita masukkan dalam format sqlite.
 *
 * Database yang digunakan  : db_lets_hijrah.sqlite
 * Tabel yang digunakan     : tb_daily
 */

package com.timdakwah.letshijrah.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.timdakwah.letshijrah.model.Product_ChallengesList;

import java.util.ArrayList;
import java.util.List;

import static com.timdakwah.letshijrah.ImportParameters.DB_NAME;
import static com.timdakwah.letshijrah.ImportParameters.DB_VER;

public class dbHelper_ChallengesList extends SQLiteOpenHelper {
    /**
     * Deklarasi nama, path/lokasi, versi, dan tabel yang digunakan
     * Deklarasi variabel Context dan SQLiteDatabase
     */
    public static final String TB_NAME_CHALLENGESLIST = "tb_challenges_list";

    private Context dbHelper_Context_ChallengesList;
    private SQLiteDatabase dbHelper_SQLiteDatabase_ChallengesList;

    /**
     * Pada fungsi di bawah ini akan membuat fungsi untuk super dengan parameter
     * context, nama database, factory = null, dan versi database.
     * @param context
     */

    public dbHelper_ChallengesList(Context context) {
        super(context, DB_NAME, null, DB_VER);
        this.dbHelper_Context_ChallengesList = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /**
         * bagian ini kosongkan saja
         */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /**
         * bagian ini kosongkan saja
         */
    }

    /**
     * Pada fungsi openDatabase akan membuka lokasi/path dari database
     * lalu jika database tidak kosong dan bisa dibuka maka akan membuka database
     * tersebut.
     */

    public void openDatabase () {
        String dbPath = dbHelper_Context_ChallengesList.getDatabasePath(DB_NAME).getPath();
        if (dbHelper_SQLiteDatabase_ChallengesList != null && dbHelper_SQLiteDatabase_ChallengesList.isOpen()) {
            return;
        }
        dbHelper_SQLiteDatabase_ChallengesList = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    /**
     * Selanjutnya pada fungsi closeDatabase akan menutup kembali database
     * yang telah diimport ke dalam aplikasi karena sudah tersimpan di dalam
     * SQLite pada aplikasi
     */

    public void closeDatabase () {
        if (dbHelper_SQLiteDatabase_ChallengesList != null) {
            dbHelper_SQLiteDatabase_ChallengesList.close();
        }
    }

    /**
     * Fungsi di bawah ini mengambil database dengan cursor dan query.
     * Cursor   : sebuah penunjuk untuk SQLiteDatabase
     * Query    : bahasa pemrograman untuk database
     *
     * Perlu dibuat sebuah ArrayList untuk mendapatkan data dari database yang telah diimpor
     * Lalu menggunakan cursor untuk menunjukkan data yang diambil dari database
     *
     * Setiap data yang diambil pada variabel "product" akan dimasukkan ke dalam variabel
     * array "product_list", cursor akan menuju ke row berikutnya.
     *
     * Kerja cursor:
     *  -> cursor.moveToFirst() : menuju row ke-1
     *  -> cursor.moveToNext()  : menuju row berikutnya
     *  -> cursor.isAfterLast() : mengecek ke row setelah yang terakhir
     *
     *  Pada penggunaan while, mengecek seluruh row sampai terakhir dan memasukkan ke
     *  dalam variabel "product" dan mendapatkan setiap data pada atribut di database.
     *
     * @return : hasil dari product_list
     */

    public List<Product_ChallengesList> getListProduct (String begin, String end) {
        Product_ChallengesList product = null;
        List<Product_ChallengesList> product_list = new ArrayList<>();
        openDatabase();

        String[] passingArgs = {begin, end};
        Cursor cursor = dbHelper_SQLiteDatabase_ChallengesList.rawQuery("SELECT * FROM " + TB_NAME_CHALLENGESLIST + " WHERE id BETWEEN ? AND ?", passingArgs);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            product = new Product_ChallengesList(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4)
            );
            product_list.add(product);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return product_list;
    }
}
