package net.bedev.notifweb.rest

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("login_user.php")
    fun login(
        @Field("email") email : String,
        @Field("password") password : String
    ) : Call<ResponseBody>

    @FormUrlEncoded
    @POST ("periksa_balita.php")
    fun periksabalita(
        @Field("nama") nama : String,
        @Field("id_ibu") id_ibu : String
    ) : Call<ResponseBody>

    @FormUrlEncoded
    @POST ("balita.php")
    fun getbalita(
        @Field("ibu") ibu : String
    ) : Call<ResponseBody>
}