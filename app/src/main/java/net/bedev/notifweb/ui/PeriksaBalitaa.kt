package net.bedev.notifweb.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_periksa_balita.*
import net.bedev.notifweb.R
import net.bedev.notifweb.adapter.BalitaPeriksaAdapter
import net.bedev.notifweb.helper.Cons
import net.bedev.notifweb.helper.See
import net.bedev.notifweb.model.balita
import net.bedev.notifweb.rest.ApiConfig
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PeriksaBalitaa : AppCompatActivity() {

    var id_ibu = ""
    var id_balita = ""
    var name_balita = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_periksa_balita)
        id_balita = intent.getStringExtra("id_balita")
        name_balita = intent.getStringExtra("namabalita")

        supportActionBar?.title = "Detail Kesehatan " + name_balita
        See.log("nama balita : $name_balita")

        //    TvNamaBalita.text = name_balita
        val sharedPreferences =
            getSharedPreferences("user", Context.MODE_PRIVATE)

        id_ibu = sharedPreferences.getString("ID_IBU", "").toString()
        val resultNama = sharedPreferences.getString("NAMA", "")
        val resultFoto = sharedPreferences.getString("FOTO", "")
        val resultEmail = sharedPreferences.getString("EMAIL", "")
        val resultPassword = sharedPreferences.getString("PASS", "")
        See.log("Result Sharepreference PeriksaBalitaa  : $id_ibu, $resultNama , $resultFoto , $resultEmail ,$resultPassword")
        See.log("Result Id_balita : $id_balita")
        getBalita()
    }

    private fun getBalita() {
        RvBalita.layoutManager = LinearLayoutManager(applicationContext)
        val apiService = ApiConfig.getApiService()
        apiService.periksabalita(id_balita, id_ibu)
            .enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    PbBalita.visibility = View.GONE

                    See.toast(this@PeriksaBalitaa, "Cek Koneksi internet anda")
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

                        val data = Gson().fromJson(respon, balita::class.java)
//                        val databalita = Gson().fromJson(respon, balita::class.java).data
//                        databalita.map {
//
//                        }


                        val list = data.data

                        See.log("listBalita: $list")

                        if (list != null) {
                            TvDataNull.visibility = View.GONE
                            RvBalita.adapter = BalitaPeriksaAdapter(list)
                        } else {
                            PbBalita.visibility = View.GONE
                            See.log("listBalita Adapter kosong")

                        }


                    } else {
                        See.log(apiStatus.toString())
                        PbBalita.visibility = View.GONE
                        See.toast(
                            this@PeriksaBalitaa,
                            "Silahkan Daftarkan Bayi anda ke Kader Posyandu Terdekat"
                        )

                    }
                }

            })

    }
}
