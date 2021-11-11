package com.example.aplikasipasarikan.nonkonsumsi;

public class NonKonsumsiDetailModel {
    private String nama_non_konsumsi;
    private String bahan_baku;
    private String nama_hasil_produk;
    private String asal_bahan_baku;

    public NonKonsumsiDetailModel(String nama_non_konsumsi, String bahan_baku, String nama_hasil_produk, String asal_bahan_baku) {
        this.nama_non_konsumsi = nama_non_konsumsi;
        this.bahan_baku = bahan_baku;
        this.nama_hasil_produk = nama_hasil_produk;
        this.asal_bahan_baku = asal_bahan_baku;
    }

    public String getNama_non_konsumsi() {
        return nama_non_konsumsi;
    }

    public String getBahan_baku() {
        return bahan_baku;
    }

    public String getNama_hasil_produk() {
        return nama_hasil_produk;
    }

    public String getAsal_bahan_baku() {
        return asal_bahan_baku;
    }
}
