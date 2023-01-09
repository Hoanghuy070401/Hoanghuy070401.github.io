package com.manager.quanlybanhang.retrofit;




import com.manager.quanlybanhang.model.DonHangModel;
import com.manager.quanlybanhang.model.LoaiSpModel;
import com.manager.quanlybanhang.model.MessageModel;
import com.manager.quanlybanhang.model.SanPhamMoiModel;
import com.manager.quanlybanhang.model.UserModel;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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
    @POST("insert.php")
    @FormUrlEncoded
    Observable<MessageModel> insertSanpham(
            @Field("ten") String ten,
            @Field("gia") int gia,
            @Field("mota") String mota,
            @Field("hinhanh") String hinhanh,
            @Field("loai") int id

    );
    @Multipart
    @POST("upload.php")
    Call<MessageModel> uploadFile(@Part MultipartBody.Part file);

}
