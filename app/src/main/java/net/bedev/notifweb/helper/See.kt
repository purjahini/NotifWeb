package net.bedev.notifweb.helper

import android.content.Context
import android.util.Log
import android.widget.Toast

class See {

    companion object {

        var TAG: String = "log_posyandu_core"

        fun log(message: String) {

            try {
                Log.d(TAG, message)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }


        fun log(key: String, message: String) {
            try {
                Log.d(TAG, key + " -> " + message);
            } catch (e: NullPointerException) {
                e.printStackTrace()
            }
        }

        fun logE(message: String) {
            try {
                Log.e(TAG, message);
            } catch (e: NullPointerException) {
                e.printStackTrace()
            }
        }


        fun logE(key: String, message: String) {
            try {
                Log.e(TAG, key + " -> " + message);
            } catch (e: NullPointerException) {
                e.printStackTrace()
            }
        }


        fun toast(context: Context, message: String) {
            try {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            }
        }
    }


}