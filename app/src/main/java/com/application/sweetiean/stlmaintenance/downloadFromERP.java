package com.application.sweetiean.stlmaintenance;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Created by sweetiean on 2/29/2016.
 */
public class downloadFromERP extends AsyncTask<String, Integer, Void> {

    Context context;
    String result;


    public downloadFromERP(Context c){
        context = c;
    }

    @Override
    protected Void doInBackground(String... params) {

        try {
            if(MaintenanceActivity.itemClicked.equals("customers")) {
                result = MaintenanceActivity.Main_axcall.customers();
                MaintenanceActivity.jsonString = result;
            }
            if(MaintenanceActivity.itemClicked.equals("items")){
                result = MaintenanceActivity.Main_axcall.equipment();
                MaintenanceActivity.jsonString = result;
            }
        }
        catch (Exception e){

            e.printStackTrace();

        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        if(MaintenanceActivity.itemClicked.equals("customers")) {
            MaintenanceActivity.jsonString = result;
            MaintenanceActivity.InsertCustIntoDB();
        }
        if(MaintenanceActivity.itemClicked.equals("items")){
            MaintenanceActivity.jsonString = result;
            MaintenanceActivity.InsertItemsIntoDB();
        }


    }


}
