package com.example.quanlybanhang.retrofit;




import com.example.quanlybanhang.model.DonHangModel;
import com.example.quanlybanhang.model.LoaiSpModel;
import com.example.quanlybanhang.model.SanPhamMoiModel;
import com.example.quanlybanhang.model.UserModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiBanHang {
    @GET("getloadsp.php")
    Observable<LoaiSpModel> getLoaisp();

    @GET("getspmoi.php")
    Observable<SanPhamMoiModel> getSpMoi();

    @POST("chitiet.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> getSanpham(
       @Field("page") int page,
       @Field("loai") int loai
    );
    @POST("dangki.php")
    @FormUrlEncoded
    Observable<UserModel> dangki(
            @Field("email") String email,
            @Field("password") String password,
            @Field("username") String username,
            @Field("mobile") String mobile,
            @Field("address") String address,
            @Field("brithday") String brithday

    );
    @POST("dangnhap.php")
    @FormUrlEncoded
    Observable<UserModel> dangnhap(
            @Field("email") String email,
            @Field("password") String password

    );

    @POST("resetpass.php")
    @FormUrlEncoded
    Observable<UserModel> resetpas(
            @Field("email") String email

    );
    @POST("donhang.php")
    @FormUrlEncoded
    Observable<UserModel> createDonHang(
            @Field("email") String email,
            @Field("mobile") String mobile,
            @Field("iduser") int iduser,
            @Field("username") String username,
            @Field("soluong") int soluong,
            @Field("tongtien") String tongtien,
            @Field("diachi") String diachi,
            @Field("chitiet") String chitiet

    );
    @POST("xemdonhang.php")
    @FormUrlEncoded
    Observable<DonHangModel> xemDonHang(
            @Field("iduser") int id

    );
    @POST("timkiem.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> timkiem(
            @Field("search") String search

    );

}
