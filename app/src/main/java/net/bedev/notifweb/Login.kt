package net.bedev.notifweb

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*
import net.bedev.notifweb.helper.Cons
import net.bedev.notifweb.helper.See
import net.bedev.notifweb.model.user
import net.bedev.notifweb.rest.ApiConfig
import net.bedev.notifweb.ui.Balita
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_login)

        Btnlogin.setOnClickListener {
            postLogin()
            PbLogin.visibility = View.VISIBLE
        }

    }

    private fun postLogin() {
        val email = EdUsername.text.toString()
        val pass = EdPassword.text.toString()
        if (email.isEmpty() || pass.isEmpty()) {
            EdUsername.error = "Harus di isi"
            EdPassword.error = "Harus di isi"
            PbLogin.visibility = View.INVISIBLE
            return

        }

        val apiService = ApiConfig.getApiService()
        apiService.login(EdUsername.text.toString().trim(), EdPassword.text.toString().trim())
            .enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    See.log("onFailure")
                    See.toast(this@Login, "Cek Koneksi Intennet anda")
                    PbLogin.visibility = View.GONE
                }

                override fun onResponse(
                    call: Call<ResponseBody>, response: Response<ResponseBody>
                ) {
                    val respon = response.body()?.string()
                    See.log("respon : $respon")
                    val json = JSONObject(respon)
                    val apiStatus = json.getInt(Cons.api_status)

                    PbLogin.visibility = View.GONE


                    if (apiStatus == 1) {
                        val data = Gson().fromJson(respon, user::class.java).data
                        data.map {
                            val id_ibu = it.id_ibu
                            val nama = it.nama
                            val foto = it.foto_ibu
                            val email = it.email
                            val password = it.password

                            See.log("data : $id_ibu ,$foto, $email, $password, $nama")

                            val sharedPreferences =
                                getSharedPreferences("user", Context.MODE_PRIVATE)

                            val editor = sharedPreferences.edit()
                            editor.putString("ID_IBU", id_ibu)
                            editor.putString("NAMA", nama)
                            editor.putString("FOTO", foto)
                            editor.putString("EMAIL", email)
                            editor.putString("PASS", password)
                            editor.apply()

                            val resultId_ibu = sharedPreferences.getString("ID_IBU","")
                            val resultNama = sharedPreferences.getString("NAMA","")
                            val resultFoto = sharedPreferences.getString("FOTO","")
                            val resultEmail = sharedPreferences.getString("EMAIL","")
                            val resultPassword = sharedPreferences.getString("PASS","")
                            See.log("Result Sharepreference : $resultId_ibu, $resultNama , $resultFoto , $resultEmail ,$resultPassword")

                            val intent = Intent(this@Login , Balita::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)

                        }

                    } else {
                        See.toast(this@Login, "Silahkan masukkan email dan password dengan benar")
                        See.log("apiStatus : $apiStatus")
                    }

                }

            })

    }
}
