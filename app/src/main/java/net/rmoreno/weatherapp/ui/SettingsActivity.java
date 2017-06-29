package net.rmoreno.weatherapp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.rmoreno.weatherapp.R;
import net.rmoreno.weatherapp.SettingsInteractor;
import net.rmoreno.weatherapp.presenters.SettingsPresenter;
import net.rmoreno.weatherapp.presenters.SettingsPresenterImpl;
import net.rmoreno.weatherapp.repositories.WeatherRepository;


public class SettingsActivity extends ActionBarActivity implements  SettingsView{

    Button mDone;
    EditText mTemperature;
    String SHARED_PREFERENCES = "MyPrefs";
    SettingsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mDone = (Button) findViewById(R.id.done);
        mTemperature = (EditText) findViewById(R.id.temperature);

        SharedPreferences preferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();

        new SettingsPresenterImpl(
                new SettingsInteractor(new WeatherRepository(preferences))
        );


        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidInput((mTemperature.getText().toString()))) {
                    presenter.updateSweather(Integer.parseInt(mTemperature.getText().toString()));
                    displayToast("Sweater Weather Updated!").show();
                    Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                    startActivity(intent);
                }

                else {
                    mTemperature.setText("");
                    displayToast("Invalid input").show();
                }
            }
        });
    }

    @Override
    public void displaySweather() {

    }

    private boolean isValidInput(String input) {
        try {
            Integer.parseInt(input);
            if(input.length() > 0) {
                return true;
            }

        } catch(NumberFormatException e) {
            Log.e("Invalid input", e.getMessage());
            return false;
        }

        return true;
    }

    private Toast displayToast(String display) {
        return Toast.makeText(SettingsActivity.this, display, Toast.LENGTH_SHORT);
    }

}
