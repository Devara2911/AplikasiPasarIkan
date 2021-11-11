package com.example.aplikasipasarikan;

public class DashboardModel {
    private String title_jenis_ikan;
    private String title_instansi;
    private String eceran;
    private String created_modified;
    private String gambar;

    public DashboardModel(String title_jenis_ikan, String title_instansi, String eceran, String created_modified, String gambar) {
        this.title_jenis_ikan = title_jenis_ikan;
        this.title_instansi = title_instansi;
        this.eceran = eceran;
        this.created_modified = created_modified;
        this.gambar = gambar;
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
}
