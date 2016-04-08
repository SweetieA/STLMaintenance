package com.application.sweetiean.stlmaintenance;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    public static EditText Url;
    Button saveUrl;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String URL = "ipKey";
    public static SharedPreferences sharedpreferences, settings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        
        init();
        Url.setText(getValue());
    }

    private void init() {

        Url = (EditText)findViewById(R.id.URLEditText);
        saveUrl = (Button)findViewById(R.id.URLSaveButton);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }


    public void save(View v){

        MaintenanceActivity.url = Url.getText().toString();


        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString(URL, MaintenanceActivity.url);

        editor.commit();
        Toast.makeText(this, "Maintenance URL Saved", Toast.LENGTH_LONG).show();

    }

    public String getValue(){
        settings = this.getSharedPreferences(SettingsActivity.MyPREFERENCES, Context.MODE_PRIVATE);;
        MaintenanceActivity.url = settings.getString(SettingsActivity.URL, null);
        return MaintenanceActivity.url;
    }
}
