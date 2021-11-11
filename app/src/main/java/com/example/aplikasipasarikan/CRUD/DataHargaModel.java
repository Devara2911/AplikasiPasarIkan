package com.example.aplikasipasarikan.CRUD;

public class DataHargaModel {
    private String title_jenis_ikan;
    private String title_instansi;
    private String eceran;
    private String created_modified;
    private String gambar;
    private String nm_status;

    public DataHargaModel(String title_jenis_ikan, String title_instansi, String eceran, String created_modified, String gambar, String nm_status) {
        this.title_jenis_ikan = title_jenis_ikan;
        this.title_instansi = title_instansi;
        this.eceran = eceran;
        this.created_modified = created_modified;
        this.gambar = gambar;
        this.nm_status = nm_status;
    }

    public String getTitle_jenis_ikan() {
        return title_jenis_ikan;
    }

    public void setTitle_jenis_ikan(String title_jenis_ikan) {
        this.title_jenis_ikan = title_jenis_ikan;
    }

    public String getTitle_instansi() {
        return title_instansi;
    }

    public void setTitle_instansi(String title_instansi) {
        this.title_instansi = title_instansi;
    }

    public String getEceran() {
        return eceran;
    }

    public void setEceran(String eceran) {
        this.eceran = eceran;
    }

    public String getCreated_modified() {
        return created_modified;
    }

    public void setCreated_modified(String created_modified) {
        this.created_modified = created_modified;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getNm_status() {
        return nm_status;
    }

    public void setNm_status(String nm_status) {
        this.nm_status = nm_status;
    }
}
