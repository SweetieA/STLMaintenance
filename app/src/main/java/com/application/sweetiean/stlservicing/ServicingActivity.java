package com.application.sweetiean.stlservicing;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.application.sweetiean.stlmaintenance.LoginActivity;
import com.application.sweetiean.stlmaintenance.MaintenanceAppDB;
import com.application.sweetiean.stlmaintenance.R;
import com.application.sweetiean.stlmaintenance.Utility;
import com.application.sweetiean.stlmaintenance.downloadFromERP;
import com.application.sweetiean.stlmaintenance.webService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ServicingActivity extends AppCompatActivity{

    public static String url, DeviceId;
    public static webService Serv_axcall;
    public static String itemClicked;
    public static String jsonString;
    SharedPreferences settings;

    static String sysaid, taskType, customer, customer_account, address, region, locationCoordinates, stl_rep_name,
            stl_rep_post, stl_rep_sign, client_rep_name, client_rep_post, client_rep_sign, dvrFirmware,
            firmwareVersion, serv_workstation, testCP, cleanPC, serv_workstationSerial, remarks, serv_dvr,
            serv_dvrSerial, cleanCam, serv_checkUPS, serv_UPS, serv_UPSSerial, status, repl_checkUPS,
            repl_UPS, repl_UPSSerial, repl_workstation, repl_workstationSerial, hdd, _500GB, _1TB, _2TB,
            hdd500Serial, hdd1TBSerial, hdd2TBSerial, repl_dvr, dvrType, repl_dvrSerial, cameras, fixBox, fixBoxQty, fixBoxSerial,
            domeIR, domeQty, domeSerial, bulletIR, bulletQty, bulletSerial, housing, housingQty, housingSerial,
            powerSupply, _12vPwrSupply, _12vPwrBox, monitor, monitorSerial, otherIssues, issues;


    public static String custName, custAccount, customer_full;
    private static final String TAG_NAME_CUSTOMER = "AccountName";
    private static final String TAG_NUMBER_CUSTOMER = "AccountNumber";


    public static Context con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicing);

        init();
        con = this;

    }

    public void init(){

        Toolbar toolbar;
        TabLayout tabLayout;
        ViewPager viewPager;
        SharedPreferences prefs;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        viewPager.setOffscreenPageLimit(4);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        url = prefs.getString("ipKey", "null");

        TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        DeviceId = tm.getDeviceId();

        Serv_axcall = new webService();

    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Serv_OverviewFragment(), "Overview");
        adapter.addFragment(new Serv_BaseDataFragment(), "Base Data");
        adapter.addFragment(new ServicingFragment(), "Servicing");
        adapter.addFragment(new ReplacementFragment(), "Replacement");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public void initBaseFragVar(){
        //base
        sysaid = Serv_BaseDataFragment.sysaid.getText().toString();
        taskType = Serv_BaseDataFragment.taskSpinner.getSelectedItem().toString();
        customer = Serv_BaseDataFragment.customer.getText().toString();
        address = Serv_BaseDataFragment.address.getText().toString();
        region = Serv_BaseDataFragment.regionSpinner.getSelectedItem().toString();
        locationCoordinates = Serv_BaseDataFragment.locationCoordinates.getText().toString();
        stl_rep_name = Serv_BaseDataFragment.stl_rep_name;
        stl_rep_post = Serv_BaseDataFragment.stl_rep_post;
        stl_rep_sign = Serv_BaseDataFragment.stl_rep_sign;
        client_rep_name = Serv_BaseDataFragment.client_rep_name;
        client_rep_post = Serv_BaseDataFragment.client_rep_post;
        client_rep_sign = Serv_BaseDataFragment.client_rep_sign;

    }

    public void initServFragVar(){

        //servicing
        dvrFirmware = (ServicingFragment.dvrFirmware.isChecked()) ? "Checked" : "Not Checked";
        firmwareVersion = ServicingFragment.firmwareVersion.getText().toString();
        serv_workstation = (ServicingFragment.workstation.isChecked()) ? "Checked" : "Not Checked";
        testCP = (ServicingFragment.testCP.isChecked()) ? "Checked" : "Not Checked";
        cleanPC = (ServicingFragment.cleanPC.isChecked()) ? "Checked" : "Not Checked";
        serv_workstationSerial = ServicingFragment.workstationSerial.getText().toString();
        remarks = ServicingFragment.remarks.getText().toString();
        serv_dvr =(ServicingFragment.dvr.isChecked()) ? "Checked" : "Not Checked";
        serv_dvrSerial = ServicingFragment.dvrSerial.getText().toString();
        cleanCam = (ServicingFragment.cleanCam.isChecked()) ? "Checked" : "Not Checked";
        serv_checkUPS = (ServicingFragment.checkUPS.isChecked()) ? "Checked" : "Not Checked";
        serv_UPS = ServicingFragment.UPSSpinner.getSelectedItem().toString();
        serv_UPSSerial = ServicingFragment.UPSSerial.getText().toString();
        status = ServicingFragment.statusSpinner.getSelectedItem().toString();

    }

    public void initReplFragVar(){

        //replacement

        repl_checkUPS = (ReplacementFragment.ups.isChecked()) ? "Checked" : "Not Checked";
        repl_UPS = ReplacementFragment.UPSSpinner.getSelectedItem().toString();
        repl_UPSSerial = ReplacementFragment.UPSSerial.getText().toString();
        repl_workstation = (ReplacementFragment.workstation.isChecked()) ? "Checked" : "Not Checked";
        repl_workstationSerial = ReplacementFragment.workstationSerial.getText().toString();
        hdd = (ReplacementFragment.hdd.isChecked()) ? "Checked" : "Not Checked";
        _500GB = (ReplacementFragment._500GB.isChecked()) ? "Checked" : "Not Checked";
        _1TB = (ReplacementFragment._1TB.isChecked()) ? "Checked" : "Not Checked";
        _2TB = (ReplacementFragment._2TB.isChecked()) ? "Checked" : "Not Checked";
        hdd500Serial = ReplacementFragment.hdd500Serial.getText().toString();
        hdd1TBSerial = ReplacementFragment.hdd1TBSerial.getText().toString();
        hdd2TBSerial = ReplacementFragment.hdd2TBSerial.getText().toString();
        repl_dvr =(ReplacementFragment.dvr.isChecked()) ? "Checked" : "Not Checked";
        dvrType = ReplacementFragment.dvrType.getText().toString();
        repl_dvrSerial = ReplacementFragment.dvrSerial.getText().toString();
        cameras = (ReplacementFragment.cameras.isChecked()) ? "Checked" : "Not Checked";
        fixBox =(ReplacementFragment.fixBox.isChecked()) ? "Checked" : "Not Checked";
        fixBoxQty = ReplacementFragment.fixBoxQty.getText().toString();
        fixBoxSerial = ReplacementFragment.fixBoxSerial.getText().toString();
        domeIR =(ReplacementFragment.domeIR.isChecked()) ? "Checked" : "Not Checked";
        domeQty = ReplacementFragment.domeQty.getText().toString();
        domeSerial = ReplacementFragment.domeSerial.getText().toString();
        bulletIR =(ReplacementFragment.bulletIR.isChecked()) ? "Checked" : "Not Checked";
        bulletQty = ReplacementFragment.bulletQty.getText().toString();
        bulletSerial = ReplacementFragment.bulletSerial.getText().toString();
        housing =(ReplacementFragment.housing.isChecked()) ? "Checked" : "Not Checked";
        housingQty = ReplacementFragment.housingQty.getText().toString();
        housingSerial = ReplacementFragment.housingSerial.getText().toString();
        powerSupply = (ReplacementFragment.powerSupply.isChecked()) ? "Checked" : "Not Checked";
        _12vPwrSupply = (ReplacementFragment._12vPwrSupply.isChecked()) ? "Checked" : "Not Checked";
        _12vPwrBox = (ReplacementFragment._12vPwrBox.isChecked()) ? "Checked" : "Not Checked";
        monitor =(ReplacementFragment.monitor.isChecked()) ? "Checked" : "Not Checked";
        monitorSerial = ReplacementFragment.monitorSerial.getText().toString();
        otherIssues =(ReplacementFragment.otherIssues.isChecked()) ? "Yes" : "No";
        issues = ReplacementFragment.issues.getText().toString();
    }


    public void serv_insertToBaseData_db(){
        initBaseFragVar();
        String sql_date = Utility.getTodaysDate();
        String sql_sysaid = sysaid;
        String sql_taskType = taskType;
        String sql_customer = customer;
        String sql_site = customer_account;
        String sql_address = address;
        String sql_region = region;
        String sql_location = locationCoordinates;
        String sql_stlRepName = stl_rep_name;
        String sql_stlRepPost = stl_rep_post;
        String sql_stlRepSign = stl_rep_sign;
        String sql_clientRepName = client_rep_name;
        String sql_clientRepPost = client_rep_post;
        String sql_clientRepSign = client_rep_sign;

        MaintenanceAppDB base_data_record = new MaintenanceAppDB(this);
        base_data_record.openForRead();

        base_data_record.createServBaseDataRecord(sql_date, sql_sysaid, sql_taskType, sql_customer,sql_site,
                sql_address, sql_region, sql_location, sql_stlRepName, sql_stlRepPost,
                sql_stlRepSign, sql_clientRepName, sql_clientRepPost, sql_clientRepSign);
        base_data_record.close();

    }

    public void insertToServicing_db(){
        initServFragVar();
        String sql_sysaid = sysaid;
        String sql_firmwareUpdate = dvrFirmware;
        String sql_firmwareVersion = firmwareVersion;
        String sql_workstation = serv_workstation;
        String sql_testCP = testCP;
        String sql_cleanPC = cleanPC;
        String sql_workstationSerial = serv_workstationSerial;
        String sql_remarks = remarks;
        String sql_dvr = serv_dvr;
        String sql_dvrSerial = serv_dvrSerial;
        String sql_cleanCam = cleanCam;
        String sql_checkUPS = serv_checkUPS;
        String sql_UPS = serv_UPS;
        String sql_UPSSerial = serv_UPSSerial;
        String sql_UPSStatus = status;

        MaintenanceAppDB servicing_record = new MaintenanceAppDB(this);
        servicing_record.openForRead();
        servicing_record.createServicingRecord(sql_sysaid, sql_firmwareUpdate, sql_firmwareVersion, sql_workstation,
                sql_testCP, sql_cleanPC, sql_workstationSerial, sql_remarks, sql_dvr,
                sql_dvrSerial, sql_cleanCam, sql_checkUPS, sql_UPS, sql_UPSSerial, sql_UPSStatus);
        servicing_record.close();

    }

    public void insertToReplacement_db(){
        initReplFragVar();
        String sql_sysaid = sysaid;
        String sql_UPSCheck = repl_checkUPS;
        String sql_UPS = repl_UPS;
        String sql_UPSSerial = repl_UPSSerial;
        String sql_workstation = repl_workstation;
        String sql_workstationSerial = repl_workstationSerial;
        String sql_hdd = hdd;
        String sql_500 = _500GB;
        String sql_1TB = _1TB;
        String sql_2TB = _2TB;
        String sql_hdd500Serial = hdd500Serial;
        String sql_hdd1TBSerial = hdd1TBSerial;
        String sql_hdd2TBSerial = hdd2TBSerial;
        String sql_dvr = repl_dvr;
        String sql_dvrType = dvrType;
        String sql_dvrSerial = repl_dvrSerial;
        String sql_cameras = cameras;
        String sql_fixBox = fixBox;
        String sql_fixBoxQty = fixBoxQty;
        String sql_fixBoxSerial = fixBoxSerial;
        String sql_dome = domeIR;
        String sql_domeQty = domeQty;
        String sql_domeSerial = domeSerial;
        String sql_bullet = bulletIR;
        String sql_bulletQty = bulletQty;
        String sql_bulletSerial = bulletSerial;
        String sql_housing = housing;
        String sql_housingQty = housingQty;
        String sql_housingSerial = housingSerial;
        String sql_powerSupp = powerSupply;
        String sql_12vSupp = _12vPwrSupply;
        String sql_12vBox = _12vPwrBox;
        String sql_monitor = monitor;
        String sql_monitorSerial = monitorSerial;
        String sql_issuesBox = otherIssues;
        String sql_issuesText = issues;

        MaintenanceAppDB replacement_record = new MaintenanceAppDB(this);
        replacement_record.openForRead();
        replacement_record.createReplacementRecord(sql_sysaid, sql_UPSCheck, sql_UPS, sql_UPSSerial, sql_workstation,
                sql_workstationSerial, sql_hdd, sql_500, sql_1TB, sql_2TB, sql_hdd500Serial, sql_hdd1TBSerial, sql_hdd2TBSerial, sql_dvr, sql_dvrType,
                sql_dvrSerial, sql_cameras, sql_fixBox, sql_fixBoxQty, sql_fixBoxSerial, sql_dome, sql_domeQty,
                sql_domeSerial, sql_bullet, sql_bulletQty, sql_bulletSerial, sql_housing, sql_housingQty, sql_housingSerial,
                sql_powerSupp, sql_12vSupp, sql_12vBox, sql_monitor, sql_monitorSerial, sql_issuesBox, sql_issuesText);
        replacement_record.close();

    }


    public static void InsertCustIntoDB()
    {


        try{
            JSONArray jarray =new JSONArray(jsonString);

            MaintenanceAppDB sqlitedb = new MaintenanceAppDB(con);
            sqlitedb.deleteAllCustomers();
            sqlitedb.openForRead();



            for (int count = 0; count < jarray.length(); count++) {
                JSONObject obj = jarray.getJSONObject(count);
                custName = obj.getString(TAG_NAME_CUSTOMER);
                custAccount = obj.getString(TAG_NUMBER_CUSTOMER);

                customer_full = custName +" : " + custAccount;

                ContentValues cv = new ContentValues();
                cv.put(MaintenanceAppDB.CUSTOMER_NAME, customer_full);
                cv.put(MaintenanceAppDB.CUSTOMER_ACCOUNT, custAccount);
                sqlitedb.maintenanceAppDatabase.insert(MaintenanceAppDB.TABLE_CUSTOMERS, null, cv);
            }

            int counter = sqlitedb.getCustomersCount();
            Log.i("Customers", String.valueOf(counter));

            if(counter == jarray.length()){
                Toast.makeText(con, String.valueOf(counter)+" Customers Saved to Database Successfully!", Toast.LENGTH_SHORT).show();

            }
            else {
                Toast.makeText(con, "FAILED!!!", Toast.LENGTH_SHORT).show();

            }

            sqlitedb.close();
        }
        catch (JSONException e){

            e.printStackTrace();
        }



    }



    public void save_final(){

        boolean insert = true;

        if(Serv_BaseDataFragment.sysaid.getText().toString().length() == 0){
            insert = false;
            Toast.makeText(this, "Sysaid Id is required", Toast.LENGTH_SHORT).show();
        }

        else if(Serv_BaseDataFragment.customer.getText().toString().length() == 0){
            insert = false;
            Toast.makeText(this, "Customer Name is required", Toast.LENGTH_SHORT).show();

        } else if(insert) {

            serv_insertToBaseData_db();
            insertToServicing_db();
            insertToReplacement_db();

            writePdf();

            Toast.makeText(this, "ALL DATA STORED SUCCESSFULLY", Toast.LENGTH_LONG).show();
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }


    }

    public void writePdf(){
        initBaseFragVar();
        String filename = sysaid;

        Serv_CreatePDF fop = new Serv_CreatePDF();

        if (fop.writepdf(filename)) {
            Toast.makeText(getApplicationContext(), filename +  ".pdf created", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "I/O error", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_servicing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, Serv_SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.save_tickets) {
            save_final();
            return true;
        }
        if (id == R.id.get_customers) {
            itemClicked = "customers";
            Serv_downloadFromERP download = new Serv_downloadFromERP(getParent());
            download.execute();

            Toast.makeText(this, "Customers successfully downloaded.", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.logout) {
            finish();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
