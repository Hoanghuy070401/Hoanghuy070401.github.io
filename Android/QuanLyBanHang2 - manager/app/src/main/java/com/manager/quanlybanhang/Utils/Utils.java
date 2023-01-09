package com.manager.quanlybanhang.Utils;

import com.manager.quanlybanhang.model.GioHang;
import com.manager.quanlybanhang.model.User;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static final String BASE_URL = "http://192.168.56.1:8080/banhangonline/";
     public static List<GioHang> manggiohang;
     public static List<GioHang> mangmuahang= new ArrayList<>();
     public static User user_current = new User();
}
