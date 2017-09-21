package net.rmoreno.weatherapp.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_intro.*
import net.rmoreno.weatherapp.R


class IntroActivity : Activity() {

    internal var PREF = "MyPrefs"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        done.setOnClickListener {
            val temperature = Integer.parseInt(temperature.text.toString())
            val preferences = getSharedPreferences(PREF, Context.MODE_PRIVATE)
            val edit = preferences.edit()
            edit.putInt("sweater", temperature)
            edit.commit()

            val intent = Intent(this@IntroActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
