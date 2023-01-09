package com.example.quanlybanhang.model;

public class LoaiSp {
    int id;
    String ten;
    String Hinhanh;

    public LoaiSp( String ten, String hinhanh) {
        this.ten = ten;
        Hinhanh = hinhanh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getHinhanh() {
        return Hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        Hinhanh = hinhanh;
    }
}
