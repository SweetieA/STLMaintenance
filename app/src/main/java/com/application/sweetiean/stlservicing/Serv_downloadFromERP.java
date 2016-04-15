package com.application.sweetiean.stlservicing;

import android.content.Context;
import android.os.AsyncTask;

import com.application.sweetiean.stlmaintenance.MaintenanceActivity;

/**
 * Created by sweetiean on 2/29/2016.
 */
public class Serv_downloadFromERP extends AsyncTask<String, Integer, Void> {

    Context context;
    String result;


    public Serv_downloadFromERP(Context c){
        context = c;
    }

    @Override
    protected Void doInBackground(String... params) {

        try {
            if(ServicingActivity.itemClicked.equals("customers")){
                result = ServicingActivity.Serv_axcall.customers();
                ServicingActivity.jsonString = result;
            }
        }
        catch (Exception e){

            e.printStackTrace();

        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        if(ServicingActivity.itemClicked.equals("customers")) {
            ServicingActivity.jsonString = result;
            ServicingActivity.InsertCustIntoDB();
        }


    }


}
