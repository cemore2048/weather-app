package net.rmoreno.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

                SharedPreferences preferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = preferences.edit();
                
                int updateSweater = Integer.parseInt(mTemperature.getText().toString());
                edit.remove("sweater");
                edit.putInt("sweater", updateSweater);
                edit.commit();
                preferences.getInt("sweater", 0);

                Toast.makeText(SettingsActivity.this, "Sweater Weather Updated", Toast.LENGTH_SHORT).show();

                Map<String, ?> map = preferences.getAll();
                for(Map.Entry<String,?> entry : map.entrySet()){
                    Log.d("map values",entry.getKey() + ": " +
                            entry.getValue().toString());
                }
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
