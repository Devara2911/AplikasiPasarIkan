package com.example.aplikasipasarikan.rm;

public class RmDetailModel {
    private String nama_umkm;
    private String bahan_baku;
    private String nama_hasil_produk;
    private String produk_hasil_olahan;
    private String asal_bahan_baku;

    public RmDetailModel(String nama_umkm, String bahan_baku, String nama_hasil_produk, String produk_hasil_olahan, String asal_bahan_baku) {
        this.nama_umkm = nama_umkm;
        this.bahan_baku = bahan_baku;
        this.nama_hasil_produk = nama_hasil_produk;
        this.produk_hasil_olahan = produk_hasil_olahan;
        this.asal_bahan_baku = asal_bahan_baku;
    }

    public String getNama_umkm() {
        return nama_umkm;
    }

    public String getBahan_baku() {
        return bahan_baku;
    }

    public String getNama_hasil_produk() {
        return nama_hasil_produk;
    }

    public String getProduk_hasil_olahan() {
        return produk_hasil_olahan;
    }

    public String getAsal_bahan_baku() {
        return asal_bahan_baku;
    }
}
