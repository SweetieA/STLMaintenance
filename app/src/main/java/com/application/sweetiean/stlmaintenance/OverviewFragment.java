package com.application.sweetiean.stlmaintenance;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class OverviewFragment extends ListFragment {

    View view;
    ListView overview_display;
    // Arraylist for overviews
    private ArrayList<ArrayList<String>> overList = new ArrayList<ArrayList<String>>();
    private ArrayList<String> sysid = new ArrayList<String>();
    private ArrayList<String> engName = new ArrayList<String>();
    private ArrayList<String> taskType = new ArrayList<String>();
    private ArrayList<String> date = new ArrayList<String>();
    public static ArrayList<Integer> tagList = new ArrayList<Integer>();
    //private ArrayList<String> tag;
    private MaintenanceAppDB db;
    private SQLiteDatabase sqldb;
    private OverviewAdapter adapter;

    public static String sysaidIdFileName, itemClicked;
    public String sysaididContext;


    public OverviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_overview, container, false);
        init();
        return view;
    }

    private void init() {
        overview_display = (ListView) view.findViewById(android.R.id.list);
        overview_display.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });

        db = new MaintenanceAppDB(this.getActivity());
        sqldb = db.openForRead();
        if (sqldb.isOpen()) {
            Cursor cursor = sqldb.query(MaintenanceAppDB.TABLE_MAIN_INFO,
                    new String[]{MaintenanceAppDB.SYSAID_ID, MaintenanceAppDB.TASK_TYPE, MaintenanceAppDB.STL_REP_NAME, MaintenanceAppDB.DATE}, null, null, null, null, null);

            /*overList = new ArrayList<ArrayList<String>>();
            sysid = new ArrayList<String>();
            date = new ArrayList<String>();
            engName = new ArrayList<String>();
            taskType = new ArrayList<String>();*/

            if (cursor.moveToFirst()) {
                do {

                    sysid.add(cursor.getString(0));
                    taskType.add(cursor.getString(1));
                    engName.add(cursor.getString(2));
                    date.add(cursor.getString(3));

                } while (cursor.moveToNext());
                overList.add(sysid);
                overList.add(taskType);
                overList.add(engName);
                overList.add(date);

            }
            cursor.close();

        }



        /*adapter = new OverviewAdapter(this.getActivity(), overList);

        //adding the various indexes sequentially to our UI
        for(int j=0; j<sysid.size();j++){
            tagList.add(j);
        }
        overview_display.setAdapter(adapter);*/

        if (overList.size() != 0) {
            adapter = new OverviewAdapter(this.getActivity(), overList);
            for(int j=0; j<sysid.size();j++){
            tagList.add(j);
        }
            overview_display.setAdapter(adapter);
        }

        //listview context menu
        registerForContextMenu(overview_display);//takes listview as argument. can take any view tho
    }


    public String getPDFFileName(){
        return sysaidIdFileName;
    }

    public void setPDFFileName(String _param){
        sysaidIdFileName = _param;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        int i = info.position;
        ArrayList StringArray = adapter.getsysaidId();
        this.setPDFFileName((StringArray.get(i).toString()));
        menu.setHeaderTitle("CASE " + StringArray.get(i).toString());
        //TODO clicked sysaidid
        sysaididContext = StringArray.get(i).toString();

        menu.add(0, v.getId(), 0, "Show Report");
        menu.add(0, v.getId(), 0, "Send as Attachment");
        menu.add(0, v.getId(), 0, "Upload to ERP");
        menu.add(0, v.getId(), 0, "Upload Report to ERP");
        //menu.add(0, v.getId(), 0, "Delete Record");
    }


    @Override
    public boolean onContextItemSelected(MenuItem item){
        //AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        //int position = info.position;

        super.onContextItemSelected(item);
        if(item.getTitle() == "Show Report"){

            Intent intent = new Intent();
            intent.setAction(android.content.Intent.ACTION_VIEW);
            File file = new File(Environment.getExternalStorageDirectory().getPath() + "/"  + this.getPDFFileName() + ".pdf");
            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
            startActivity(intent);
        }

        if(item.getTitle()=="Upload Report to ERP")
        {
            itemClicked = "uploadMain_file";

            uploadMainToERP uploadMainToERP = new uploadMainToERP(this.getActivity());
            uploadMainToERP.execute();

        }


        if(item.getTitle()=="Send as Attachment")
        {

            this.sendMail(this.getPDFFileName());

        }
        if(item.getTitle()=="Upload to ERP")
        {

            itemClicked = "uploadMain_data";
            uploadMainToERP uploadMainToERP = new uploadMainToERP(getActivity());
            uploadMainToERP.execute();

        }
        /*if(item.getTitle()=="Delete Record")
        {
            if (overList.size() != 0) {
                String stringVal = String.valueOf(OverviewAdapter.placeHolder.tag.getText());
                int val = Integer.parseInt(stringVal);
                deleteSingleListItem(val);
            }
        }*/

        return true;
    }


    public void deleteSingleListItem(int pos){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this.getActivity());
        alertDialog.setTitle("Delete "+ this.getPDFFileName());
        alertDialog.setMessage("Do you want to delete?");

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //delete the row from the database
                MaintenanceAppDB db = new MaintenanceAppDB(getActivity());
                db.deleteInfoRecord_bySysaid(getPDFFileName());
                db.deleteTaskRecord_bySysaid(getPDFFileName());

                //delete the row from the records ArrayList

                /*ArrayList<String> templist=new ArrayList<String>();
                for (int i = 0; i < overList.size(); i++)
                    templist.add(adapter.list.get(overList.get(i)));

                adapter.list.removeAll(templist);*/


                //notify listview of dataset changed
                adapter.notifyDataSetChanged();
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();



    }


    public void sendMail(String _sysAid)
    {
        //The easiest way to send an e-mail is to create an Intent of type ACTION_SEND.

        Intent sendEmail = new Intent(Intent.ACTION_SEND);
        sendEmail.putExtra(Intent.EXTRA_SUBJECT, "Maintenance Report Automatically Sent From Tablet, CASE NUMBER " + _sysAid);
        sendEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{"matanwe@stlghana.com", "alexanderam@stlghana.com", "kennethda@stlghana.com", "valeryro@stlghana.com", "frankow@stlghana.com", "elad@stlghana.com"});
        sendEmail.putExtra(Intent.EXTRA_TEXT, "Mail with an attachment CASE NUMBER: " + _sysAid);
        //to attach a single file, we add some extended data to our intent:
        File attachment = new File(Environment.getExternalStorageDirectory().getPath() + "/" +this.getPDFFileName()+".pdf");
        sendEmail.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(attachment));
        sendEmail.setType("application/pdf");
        startActivity(Intent.createChooser(sendEmail, "Send mail"));

    }

    // Returns the contents of the file in a byte array.
    public static byte[] getBytesFromFile() throws IOException {

        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/" + sysaidIdFileName + ".pdf");
        InputStream is = new FileInputStream(file);

        // Get the size of the file
        long length = file.length();

        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }

        // Close the input stream and return bytes
        is.close();
        return bytes;
    }
}
