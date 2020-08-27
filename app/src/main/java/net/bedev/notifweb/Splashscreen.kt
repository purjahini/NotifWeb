package net.bedev.notifweb

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import net.bedev.notifweb.helper.See
import net.bedev.notifweb.ui.Balita

class Splashscreen : AppCompatActivity() {
    private val TIME_OUT_SPLASH: Long = 3000
    var id_ibu = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_splashscreen)

        val sharedPreferences =
            getSharedPreferences("user", Context.MODE_PRIVATE)
        id_ibu = sharedPreferences.getString("ID_IBU", "").toString()
        See.log("id_ibu : $id_ibu")

        Handler().postDelayed({
            if (id_ibu.isNotEmpty()){
                val intent = Intent(this, Balita::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            } else {
                val intent = Intent(this, Login::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)

            }
        }, TIME_OUT_SPLASH)
    }
}
