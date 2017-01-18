package com.application.sweetiean.stlmaintenance;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;


/**
 * Created by sweetiean on 1/15/2016.
 */
public class uploadMainToERP extends AsyncTask<String, Integer, Void> {

    Context context;
    String file;
    String Maintenance_Info;
    String Task;

    public uploadMainToERP(Context c){
        context = c;
    }

    @Override
    protected Void doInBackground(String... params) {

        Boolean doNothing = false;

        try{


            if(OverviewFragment.itemClicked.equals("uploadMain_file")) {
                file = MaintenanceActivity.Main_axcall.SendMainFile(OverviewFragment.getBytesFromFile(), OverviewFragment.sysaidIdFileName + ".pdf");
                doNothing = true;
            }



            if(!doNothing) {
                //check info because you will be receiving from both info data
                MaintenanceAppDB getRecord = new MaintenanceAppDB(context);


                if(OverviewFragment.itemClicked.equals("uploadMain_data")) {
                    Maintenance_Info = getRecord.getMaintenanceInfoRecord(OverviewFragment.sysaidIdFileName);

                    Task = getRecord.getTaskRecord(OverviewFragment.sysaidIdFileName);
                }
            }//end insert



        }catch (Exception e){


            e.printStackTrace();


        }

        return null;
    }



    @Override
    protected void onPostExecute(Void aVoid) {

        if(OverviewFragment.itemClicked.equals("uploadMain_file")) {

            Toast.makeText(context, file, Toast.LENGTH_SHORT).show();
            //Toast.makeText(context, "Upload to ERP complete!!!", Toast.LENGTH_LONG).show();

        }else if(OverviewFragment.itemClicked.equals("uploadMain_data")) {

            Toast.makeText(context, Maintenance_Info, Toast.LENGTH_SHORT).show();
            Toast.makeText(context, Task, Toast.LENGTH_SHORT).show();
            //Toast.makeText(context, "Upload to ERP complete!!!", Toast.LENGTH_LONG).show();

           /* if(Maintenance_Info.equals("OK")&& Task.equals("OK")){
                //set listitem background to some colour
            }*/

        }

        /*if(Maintenance_Info.equals("OK")&& Task.equals("OK")&&file.equals("OK")){
            //set listitem background to green
        }*/





    }



}
