package com.example.aplikasipasarikan.produksi;

public class ProduksiModel {
    private String title_jenis_ikan;
    private String title_instansi;
    private String gambar;
    private String volume_kg;
    private String title_perikanan;
    private String created_modified;

    public ProduksiModel(String title_jenis_ikan, String title_instansi, String gambar, String volume_kg, String title_perikanan, String created_modified) {
        this.title_jenis_ikan = title_jenis_ikan;
        this.title_instansi = title_instansi;
        this.gambar = gambar;
        this.volume_kg = volume_kg;
        this.title_perikanan = title_perikanan;
        this.created_modified = created_modified;
    }

    public String getTitle_jenis_ikan() {
        return title_jenis_ikan;
    }

    public String getTitle_instansi() {
        return title_instansi;
    }

    public String getGambar() {
        return gambar;
    }

    public String getVolume_kg() {
        return volume_kg;
    }

    public String getTitle_perikanan() {
        return title_perikanan;
    }

    public String getCreated_modified() {
        return created_modified;
    }
}
