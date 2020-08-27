package net.bedev.notifweb.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_getbalita.view.*
import net.bedev.notifweb.R
import net.bedev.notifweb.model.ModelBalita
import net.bedev.notifweb.ui.PeriksaBalitaa


class BalitaAdapter  (val listgetBalita : List<ModelBalita.Data>) : RecyclerView.Adapter<MvpHolder>(){
    private  lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MvpHolder {
        context = parent.context
        return MvpHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_getbalita,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = listgetBalita.size

    override fun onBindViewHolder(holder: MvpHolder, position: Int) {
        val data = listgetBalita[position]
        var id = data.id_balita
        var balita = data.nama_balita

        var url = Uri.parse("http://posyandu.bimar.io/upload/balita/"+data.foto_balita)


        val name = holder.itemView.TvNamaBalita
        name.text = "Nama : "+data.nama_balita
        val foto = holder.itemView.IvBalita
        Glide.with(context)
            .load(url)
            .into(foto)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, PeriksaBalitaa::class.java)
            intent.putExtra("id_balita", id)
            intent.putExtra("namabalita", balita)
            context.startActivity(intent)
        }
    }
}