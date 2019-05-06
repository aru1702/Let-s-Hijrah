/**
 * Pada bagian ini berfungsi untuk membuat sebuah produk atau hasil database
 * yang diimpor dari sqlite, sama seperti proses pembuatan database dengan
 * CREATE namun kali ini mendefinisikan sehingga bisa dipanggil untuk impor
 * database.
 *
 * CREATE tb_daily (
 *  id integer,
 *  judul text
 *  selesai integer,
 *  total integer
 * );
 */

package com.timdakwah.letshijrah.model;

public class Product_Challenges {
    /**
     * Deklarasikan setiap atribut yang ada ke dalam variabel
     * Lebih baik semuanya dimasukkan ke dalam String kecuali id karena dibutuhkan
     * untuk count() pada tahap selanjutnya.
     */

    private Integer id;
    private String judul;
    private Integer selesai;
    private Integer total;

    public Product_Challenges(Integer id, String judul, Integer selesai, Integer total) {
        this.id = id;
        this.judul = judul;
        this.selesai = selesai;
        this.total = total;
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

    public Integer getSelesai() {
        return selesai;
    }

    public void setSelesai(Integer selesai) {
        this.selesai = selesai;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
