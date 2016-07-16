package net.rmoreno.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Map;


public class SettingsActivity extends ActionBarActivity {

    Button mDone;
    EditText mTemperature;
    String SHARED_PREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mDone = (Button) findViewById(R.id.done);
        mTemperature = (EditText) findViewById(R.id.temperature);

        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidInput((mTemperature.getText().toString()))) {
                    SharedPreferences preferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = preferences.edit();

                    int updateSweater = Integer.parseInt(mTemperature.getText().toString());
                    edit.remove("sweater");
                    edit.putInt("sweater", updateSweater);
                    edit.commit();
                    preferences.getInt("sweater", 0);

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
