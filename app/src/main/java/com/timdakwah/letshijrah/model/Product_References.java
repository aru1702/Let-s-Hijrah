/**
 * Pada bagian ini berfungsi untuk membuat sebuah produk atau hasil database
 * yang diimpor dari sqlite, sama seperti proses pembuatan database dengan
 * CREATE namun kali ini mendefinisikan sehingga bisa dipanggil untuk impor
 * database.
 *
 * CREATE tb_daily (
 *  id integer,
 *  judul text
 *  deskripsi text
 *  selesai integer,
 *  kapan text
 * );
 */

package com.timdakwah.letshijrah.model;

public class Product_References {
    /**
     * Deklarasikan setiap atribut yang ada ke dalam variabel
     * Lebih baik semuanya dimasukkan ke dalam String kecuali id karena dibutuhkan
     * untuk count() pada tahap selanjutnya.
     */

    private Integer id;
    private String judul;
    private String deskripsi;
    private String doa;
    private String arti;

    public Product_References(Integer id, String judul, String deskripsi, String doa, String arti) {
        this.id = id;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.doa = doa;
        this.arti = arti;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getDoa() {
        return doa;
    }

    public void setDoa(String doa) {
        this.doa = doa;
    }

    public String getArti() {
        return arti;
    }

    public void setArti(String arti) {
        this.arti = arti;
    }
}
