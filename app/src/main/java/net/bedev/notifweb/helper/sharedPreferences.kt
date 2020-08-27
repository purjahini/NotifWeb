package net.bedev.notifweb.helper

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class sharedPreferences : AppCompatActivity() {

    fun getidIbu (id : String){
        val sharedPreferences =
            getSharedPreferences("user", Context.MODE_PRIVATE)
       var id_ibu = sharedPreferences.getString("ID_IBU", "").toString()

    }

}