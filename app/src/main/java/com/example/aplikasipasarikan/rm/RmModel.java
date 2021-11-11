package com.example.aplikasipasarikan.rm;

public class RmModel {
    private String nama_umkm;
    private String alamat;
    private String title_kelurahan;
    private String title_kecamatan;
    private String title_kabkota;
    private String jumlah_anggota;

    public RmModel(String nama_umkm, String alamat, String title_kelurahan, String title_kecamatan, String title_kabkota, String jumlah_anggota) {
        this.nama_umkm = nama_umkm;
        this.alamat = alamat;
        this.title_kelurahan = title_kelurahan;
        this.title_kecamatan = title_kecamatan;
        this.title_kabkota = title_kabkota;
        this.jumlah_anggota = jumlah_anggota;
    }

    public String getNama_umkm() {
        return nama_umkm;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getTitle_kelurahan() {
        return title_kelurahan;
    }

    public String getTitle_kecamatan() {
        return title_kecamatan;
    }

    public String getTitle_kabkota() {
        return title_kabkota;
    }

    public String getJumlah_anggota() {
        return jumlah_anggota;
    }
}
