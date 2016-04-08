package com.application.sweetiean.stlservicing;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.application.sweetiean.stlmaintenance.R;
import com.application.sweetiean.stlservicing.ServicingActivity;

public class Serv_SettingsActivity extends AppCompatActivity {

    public static EditText Url;
    Button saveUrl;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String URL = "ipKey";
    public static SharedPreferences sharedpreferences, settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serv__settings);

        init();
        Url.setText(getValue());
    }

    private void init() {
        Url = (EditText)findViewById(R.id.URLEditText);
        saveUrl = (Button)findViewById(R.id.URLSaveButton);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }

    public void save(View v){

        ServicingActivity.url = Url.getText().toString();


        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString(URL, ServicingActivity.url);

        editor.commit();
        Toast.makeText(this, "Servicing URL Saved", Toast.LENGTH_LONG).show();

    }

    public String getValue(){
        settings = this.getSharedPreferences(Serv_SettingsActivity.MyPREFERENCES, Context.MODE_PRIVATE);;
        ServicingActivity.url = settings.getString(Serv_SettingsActivity.URL, null);
        return ServicingActivity.url;
    }
}
