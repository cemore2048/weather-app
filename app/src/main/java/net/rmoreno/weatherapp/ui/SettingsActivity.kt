package net.rmoreno.weatherapp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_settings.*
import net.rmoreno.weatherapp.R
import net.rmoreno.weatherapp.SettingsInteractor
import net.rmoreno.weatherapp.presenters.SettingsPresenter
import net.rmoreno.weatherapp.presenters.SettingsPresenterImpl
import net.rmoreno.weatherapp.repositories.WeatherRepository


class SettingsActivity : AppCompatActivity(), SettingsView {

    internal var SHARED_PREFERENCES = "MyPrefs"
    internal lateinit var presenter: SettingsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val preferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)

        presenter = SettingsPresenterImpl(
                SettingsInteractor(WeatherRepository(preferences))
        )

        done.setOnClickListener {
            if (isValidInput(temperature.text.toString())) {
                presenter!!.updateSweather(Integer.parseInt(temperature.text.toString()))
                displayToast("Sweater Weather Updated!")
                val intent = Intent(this@SettingsActivity, MainActivity::class.java)
                startActivity(intent)
            } else {
                temperature.setText("")
                displayToast("Invalid input")
            }
        }
    }

    override fun displaySweather() {
        //TODO show the sweater weather
    }

    private fun isValidInput(input: String): Boolean {
        try {
            Integer.parseInt(input)
            if (input.length > 0) {
                return true
            }

        } catch (e: NumberFormatException) {
            Log.e("Invalid input", e.message)
            return false
        }

        return true
    }

    private fun displayToast(display: String): Unit {
        Toast.makeText(this@SettingsActivity, display, Toast.LENGTH_SHORT).show()
    }

}
