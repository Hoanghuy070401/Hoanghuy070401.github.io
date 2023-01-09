package com.example.qlnv1;

public class NhanVien {
    public  int ID;
    public  String Name;
    public  int SDT;
    public  String Diachi;
    public  byte[] picture;

    public NhanVien(int ID, String name, int SDT, String diachi, byte[] picture) {
        this.ID = ID;
        Name = name;
        this.SDT = SDT;
        Diachi = diachi;
        this.picture = picture;
    }
}
