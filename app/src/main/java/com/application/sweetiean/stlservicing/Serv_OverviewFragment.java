package com.application.sweetiean.stlservicing;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.application.sweetiean.stlmaintenance.MaintenanceAppDB;
import com.application.sweetiean.stlmaintenance.R;
import com.application.sweetiean.stlmaintenance.uploadMainToERP;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Serv_OverviewFragment extends Fragment {

    View view;
    ListView overview_display;
    // Arraylist for overviews
    private ArrayList<ArrayList<String>> overList;
    private ArrayList<String> sysid;
    private ArrayList<String> engName;
    private ArrayList<String> taskType;
    private ArrayList<String> date;
    private MaintenanceAppDB db;
    private SQLiteDatabase sqldb;
    private OverviewAdapter adapter;

    public static String sysaidIdFileName, itemClicked;
    public String sysaididContext;



    public Serv_OverviewFragment() {
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
        view = inflater.inflate(R.layout.fragment_serv__overview, container, false);
        init();
        return view;
    }

    public void init(){

        overview_display = (ListView) view.findViewById(android.R.id.list);

        db = new MaintenanceAppDB(this.getActivity());
        sqldb = db.openForRead();
        if (sqldb.isOpen()) {
            Cursor cursor = sqldb.query(MaintenanceAppDB.TABLE_SERV_INFO,
                    new String[]{MaintenanceAppDB.SYSAID_ID, MaintenanceAppDB.TASK_TYPE, MaintenanceAppDB.STL_REP_NAME, MaintenanceAppDB.DATE}, null, null, null, null, null);

            overList = new ArrayList<ArrayList<String>>();
            sysid = new ArrayList<String>();
            date = new ArrayList<String>();
            engName = new ArrayList<String>();
            taskType = new ArrayList<String>();

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

        if (overList.size() != 0) {
            adapter = new OverviewAdapter(this.getActivity(), overList);
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
        menu.add(0, v.getId(), 0, "Send as attachment");
        menu.add(0, v.getId(), 0, "Upload to ERP");
        menu.add(0, v.getId(), 0, "Upload report via webservice");
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

        if(item.getTitle()=="Upload report via webservice")
        {
            itemClicked = "uploadServ_file";

            uploadServToERP uploadServToERP = new uploadServToERP(this.getActivity());
            uploadServToERP.execute();

        }


        if(item.getTitle()=="Send as attachment")
        {

            this.sendMail(this.getPDFFileName());

        }
        if(item.getTitle()=="Upload to ERP")
        {
            itemClicked = "uploadServ_data";
            uploadServToERP uploadServToERP = new uploadServToERP(this.getActivity());
            uploadServToERP.execute();

        }

        return true;
    }


    public void sendMail(String _sysAid)
    {
        //The easiest way to send an e-mail is to create an Intent of type ACTION_SEND.

        Intent sendEmail = new Intent(Intent.ACTION_SEND);
        sendEmail.putExtra(Intent.EXTRA_SUBJECT, "Service Report Automatically Sent From Tablet");
        sendEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{"perja@stlghana.com", "GavrielKa@stlghana.com", "valeryro@stlghana.com", "frankow@stlghana.com", "elad@stlghana.com"});
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
