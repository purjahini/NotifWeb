package net.bedev.notifweb.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_periksa_balita.view.*
import net.bedev.notifweb.R
import net.bedev.notifweb.model.balita


class BalitaPeriksaAdapter (val listBalita : List<balita.Data>) : RecyclerView.Adapter<MvpHolder>() {
    private  lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MvpHolder {
        context = parent.context
        return MvpHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_periksa_balita,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = listBalita.size

    override fun onBindViewHolder(holder: MvpHolder, position: Int) {
        val data = listBalita[position]

//        val name = holder.itemView.TvNamaBalita
//        name.text = "Nama : "+data.nama
        val jk = holder.itemView.TvjenisKelamin
        if (data.jenis_kelamin.toInt() == 1 ){
            jk.text = "Jenis kelamin : Laki-laki"
        } else {
            jk.text = "Jenis kelamin : Perempuan"
        }
        val umur = holder.itemView.TvUmur
        umur.text ="Umur Balita : "+ data.umur +" Bulan"
        val berat = holder.itemView.TvBeratBadan
        berat.text = "Berat Badan : "+data.berat + " Gram"
        val tinggi = holder.itemView.TvTinggiBadan
        tinggi.text ="Tinggi Badan : "+ data.tinggi + " Cm"
        val rekom = holder.itemView.TvRekom
        rekom.text = "Rekom dari kader : \n"+data.rekom
        val tgl = holder.itemView.TvTanggal
        tgl.text = "Tanggal Periksa : "+ data.tgl

        val TvResult = holder.itemView.TvResult


    val tinggiBmi = data.tinggi.toDouble() * 0.01
        val beratBmi = data.berat.toDouble() / 1000

        val bmi = beratBmi / (tinggiBmi *tinggiBmi)

        when (bmi) {
            in 0..17 -> {
                TvResult.text = "BMI anak anda =" + bmi + "\n Anak Anda  Kategori Kurus"
//                TvResult.resources.getColor(R.color.red)
                TvResult.setTextColor(Color.parseColor("#E71B0C"))

            }
            in 18..23 ->{
                TvResult.text= "BMI anak anda ="+ bmi +"\n Anak Anda  Kategori Normal"

                TvResult.setTextColor(Color.parseColor("#2DE70C"))

            }
            in 24..27 ->{
                TvResult.text= "BMI anak anda ="+ bmi +"\n" +
                        " Anak Anda  Kategori Gemuk"
                TvResult.setTextColor(Color.parseColor("#EAC711"))
            }
            in 28..99 -> {
                TvResult.text= "BMI anak anda ="+ bmi +"\n" +
                        " Anak Anda  Kategori Obesitas"
                TvResult.setTextColor(Color.parseColor("#0C0C0C"))
            }
        }

    }

}