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

public class Product_Daily {
    /**
     * Deklarasikan setiap atribut yang ada ke dalam variabel
     * Lebih baik semuanya dimasukkan ke dalam String kecuali id karena dibutuhkan
     * untuk count() pada tahap selanjutnya.
     */

    private Integer id;
    private String judul;
    private String deskripsi;
    private Integer selesai;

    public Product_Daily(Integer id, String judul, String deskripsi, Integer selesai) {
        this.id = id;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.selesai = selesai;
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

    public Integer getSelesai() {
        return selesai;
    }

    public void setSelesai(Integer selesai) {
        this.selesai = selesai;
    }
}
