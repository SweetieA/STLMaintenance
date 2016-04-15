package com.application.sweetiean.stlservicing;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.application.sweetiean.stlmaintenance.MaintenanceAppDB;

/**
 * Created by sweetiean on 1/29/2016.
 */
public class uploadServToERP extends AsyncTask<String, Integer, Void> {

    Context context;
    String file;
    String Service_Info;
    String Servicing;
    String Replacement;

    public uploadServToERP(Context c){
        context = c;
    }


    @Override
    protected Void doInBackground(String... params) {

        Boolean doNothing = false;

        try{



            if(Serv_OverviewFragment.itemClicked.equals("uploadServ_file")) {
                file = ServicingActivity.Serv_axcall.SendServFile(Serv_OverviewFragment.getBytesFromFile(),Serv_OverviewFragment.sysaidIdFileName+".pdf");
                doNothing = true;
            }



            if(!doNothing) {
                //check info because you will be receiving from both info data
                MaintenanceAppDB getRecord = new MaintenanceAppDB(context);

                if(Serv_OverviewFragment.itemClicked.equals("uploadServ_data")) {
                    Service_Info = getRecord.getServiceInfoRecord(Serv_OverviewFragment.sysaidIdFileName);
                    Servicing = getRecord.getServicingRecord(Serv_OverviewFragment.sysaidIdFileName);
                    Replacement = getRecord.getReplacementRecord(Serv_OverviewFragment.sysaidIdFileName);

                }
            }//end insert



        }catch (Exception e){


            e.printStackTrace();


        }
        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid) {

        if(Serv_OverviewFragment.itemClicked.equals("uploadServ_file")) {

            Toast.makeText(context, file, Toast.LENGTH_SHORT).show();
            Toast.makeText(context, "Upload to ERP complete!!!", Toast.LENGTH_LONG).show();

        }else if(Serv_OverviewFragment.itemClicked.equals("uploadServ_data")) {

            Toast.makeText(context, Service_Info, Toast.LENGTH_SHORT).show();
            Toast.makeText(context, Servicing, Toast.LENGTH_SHORT).show();
            Toast.makeText(context, Replacement, Toast.LENGTH_SHORT).show();
            Toast.makeText(context, "Upload to ERP complete!!!", Toast.LENGTH_LONG).show();

        }



    }

}
