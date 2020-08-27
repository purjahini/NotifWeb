package net.bedev.notifweb.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_balita.*
import net.bedev.notifweb.Login
import net.bedev.notifweb.R
import net.bedev.notifweb.adapter.BalitaAdapter
import net.bedev.notifweb.helper.Cons
import net.bedev.notifweb.helper.See
import net.bedev.notifweb.model.ModelBalita
import net.bedev.notifweb.model.balita
import net.bedev.notifweb.rest.ApiConfig
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Balita : AppCompatActivity() {
    var id_ibu = ""
    var nama = " "
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(net.bedev.notifweb.R.layout.activity_balita)

        getdataPeriksa()


        val sharedPreferences =
            getSharedPreferences("user", Context.MODE_PRIVATE)

        id_ibu = sharedPreferences.getString("ID_IBU", "").toString()
        var resultNama = sharedPreferences.getString("NAMA", "")
        val resultFoto = sharedPreferences.getString("FOTO", "")
        val resultEmail = sharedPreferences.getString("EMAIL", "")
        val resultPassword = sharedPreferences.getString("PASS", "")
        See.log("Result Sharepreference PeriksaBalitaa  : $id_ibu, $resultNama , $resultFoto , $resultEmail ,$resultPassword")
        val sp = supportActionBar
        sp?.title = "Selamat datang ibu " + resultNama
        sp?.subtitle = "Data Balita kesayangan anda"
        getBalita()
    }

    private fun getdataPeriksa() {
      val apiService = ApiConfig.getApiService()
        apiService.periksabalita(9.toString(), 8.toString())
            .enqueue(object : Callback<ResponseBody>{
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    See.toast(this@Balita, "Cek Koneksi internet anda")
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    val respon = response.body()?.string()
                    See.log("responBalita : $respon")
                    val json = JSONObject(respon)
                    val apiStatus = json.getInt(Cons.api_status)

                    if (apiStatus == 1){
                        val data = Gson().fromJson(respon, balita::class.java).data
                      data.map {
                            it.berat
                          it.umur
                        }

                    }
                }

            })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(net.bedev.notifweb.R.menu.menu_user, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        val id = item.getItemId()

//        if (id == R.id.menuUbahPassword) {
//            Toast.makeText(this, "Fitur masih Dalam Pengembangan", Toast.LENGTH_LONG).show()
//            return true
//        }
        if (id == R.id.menuLogout) {
            val sharedPreferences =
                getSharedPreferences("user", Context.MODE_PRIVATE)

            val editor = sharedPreferences.edit()
            editor.remove("ID_IBU")
            editor.remove("NAMA")
            editor.remove("FOTO")
            editor.remove("EMAIL")
            editor.remove("PASS")
            editor.apply()


            val intent = Intent(this , Login::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)

            Toast.makeText(this, "LogOut Berhasil", Toast.LENGTH_LONG).show()
            return true
        }

        return super.onOptionsItemSelected(item)

    }

    private fun getBalita() {
        RvGetBalita.layoutManager = LinearLayoutManager(applicationContext)
        val apiService = ApiConfig.getApiService()
        apiService.getbalita(id_ibu)
            .enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    PbBalita.visibility = View.GONE
                    See.toast(this@Balita, "Cek Koneksi internet anda")
                }

                override fun onResponse(
                    call: Call<ResponseBody>, response: Response<ResponseBody>
                ) {
                    val respon = response.body()?.string()
                    See.log("responBalita : $respon")
                    val json = JSONObject(respon)
                    val apiStatus = json.getInt(Cons.api_status)

                    if (apiStatus == 1) {
                        PbBalita.visibility = View.GONE
                        val data = Gson().fromJson(respon, ModelBalita::class.java)
                        val list = data.data

                        See.log("listBalita: $list")

                        if (list != null) {
                            TvDataNull.visibility = View.GONE
                            RvGetBalita.adapter = BalitaAdapter(list)
                        } else {
                            See.log("listBalita Adapter kosong")
                        }


                    } else {
                        See.log(apiStatus.toString())
                        PbBalita.visibility = View.GONE
                        See.toast(
                            this@Balita,
                            "Silahkan Daftarkan Bayi anda ke Kader Posyandu Terdekat"
                        )

                    }
                }

            })

    }
}