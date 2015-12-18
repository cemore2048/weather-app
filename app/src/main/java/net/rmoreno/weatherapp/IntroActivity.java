package net.rmoreno.weatherapp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class IntroActivity extends Activity {

    String PREF = "MyPrefs";
    Button mDone;
    EditText mTemperature;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);



        mDone = (Button) findViewById(R.id.done);
        mTemperature = (EditText) findViewById(R.id.temperature);

        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temperature = Integer.parseInt(mTemperature.getText().toString());
                SharedPreferences preferences = getSharedPreferences(PREF, Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = preferences.edit();
                edit.putInt("sweater", temperature);
                edit.commit();

                Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });



    }



}
