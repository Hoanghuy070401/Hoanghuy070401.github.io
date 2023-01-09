package com.example.quanlybanhang;

public class NhanVien {
    public int Id;
    public String Name;
    public String SDT;
    public String Tuoi;
    public int CMND;
    public String DiaChi;
    public byte[] priture;


    public NhanVien(int id, String name, String SDT, String tuoi, int CMND, String diaChi, byte[] priture) {
        Id = id;
        Name = name;
        this.SDT = SDT;
        Tuoi = tuoi;
        this.CMND = CMND;
        DiaChi = diaChi;
        this.priture = priture;
    }
}
