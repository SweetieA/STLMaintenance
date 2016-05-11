package com.application.sweetiean.stlmaintenance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.application.sweetiean.stlservicing.ServicingActivity;

public class MainActivity extends AppCompatActivity {

    Button openMaintenanceForm, openServicingForm;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        init();

    }

    public void init(){

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        openMaintenanceForm = (Button) findViewById(R.id.maintenanceButton);
        openServicingForm = (Button) findViewById(R.id.servicingButton);
    }

    public void startMaintenanceForm(View v){

        Intent startMaintenance = new Intent(MainActivity.this, MaintenanceActivity.class);
        startActivity(startMaintenance);
    }

    public void startServicingForm(View v){

        Intent startServicing = new Intent(MainActivity.this, ServicingActivity.class);
        startActivity(startServicing);
    }

    @Override
    public void onBackPressed(){

    }

}
