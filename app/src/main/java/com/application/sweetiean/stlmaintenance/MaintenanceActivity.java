package com.application.sweetiean.stlmaintenance;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.application.sweetiean.stlservicing.Serv_BaseDataFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MaintenanceActivity extends AppCompatActivity {

    public static String url, DeviceId;
    public static webService Main_axcall;
    public static String jsonString, itemClicked;

    public static Context con;




    static String sysaid, taskType, customer, site, address, region, phone, email, locationCoordinates, stl_rep_name,
            stl_rep_post, stl_rep_sign, client_rep_name, client_rep_post, client_rep_sign, inventory,
            genRunHours, batteryVoltage, maintenanceType, quantity, remarks;

    static byte[] beforeImg1, beforeImg2, beforeImg3, afterImg1, afterImg2, afterImg3;

    public static String custName, custAccount, customer_full, itemName, itemID, equipment_full;
    private static final String TAG_NAME_CUSTOMER = "AccountName";
    private static final String TAG_NUMBER_CUSTOMER = "AccountNumber";
    private static final String TAG_NAME_ITEM = "ItemName";
    private static final String TAG_NUMBER_ITEM = "ItemID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);

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
        viewPager.setOffscreenPageLimit(3);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        url = prefs.getString("ipKey", "null");

        TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        DeviceId = tm.getDeviceId();

        Main_axcall = new webService();

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OverviewFragment(), "Overview");
        adapter.addFragment(new BaseDataFragment(), "Base Data");
        adapter.addFragment(new TaskFragment(), "Task");
        adapter.addFragment(new ImageSignFragment(), "Sign");
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
        sysaid = BaseDataFragment.sysaid.getText().toString();
        taskType = BaseDataFragment.taskSpinner.getSelectedItem().toString();
        customer = BaseDataFragment.customer.getText().toString();
        site = BaseDataFragment.site.getText().toString();
        address = BaseDataFragment.address.getText().toString();
        region = BaseDataFragment.regionSpinner.getSelectedItem().toString();
        phone = BaseDataFragment.phone.getText().toString();
        email = BaseDataFragment.email.getText().toString();
        locationCoordinates = BaseDataFragment.locationCoordinates.getText().toString();
        stl_rep_name = ImageSignFragment.stl_rep_name;
        stl_rep_post = ImageSignFragment.stl_rep_post;
        stl_rep_sign = ImageSignFragment.stl_rep_sign;
        client_rep_name = ImageSignFragment.client_rep_name;
        client_rep_post = ImageSignFragment.client_rep_post;
        client_rep_sign = ImageSignFragment.client_rep_sign;

    }

    public void initTaskFragVar(){
        //task
        inventory = TaskFragment.inventory.getText().toString();
        genRunHours = TaskFragment.genHrs.getText().toString();
        batteryVoltage = TaskFragment.batVolt.getText().toString();
        maintenanceType = TaskFragment.maintenanceType.getSelectedItem().toString();
        quantity = TaskFragment.quantity.getText().toString();
        remarks = TaskFragment.remarks.getText().toString();

    }

    /*public void initImageVar(){
        //images
        beforeImg1 = Utility.getBytes(((BitmapDrawable) BaseDataFragment.bPic1.getDrawable()).getBitmap());
        beforeImg2 = Utility.getBytes(((BitmapDrawable) BaseDataFragment.bPic2.getDrawable()).getBitmap());
        beforeImg3 = Utility.getBytes(((BitmapDrawable) BaseDataFragment.bPic3.getDrawable()).getBitmap());
        afterImg1 = Utility.getBytes(((BitmapDrawable) TaskFragment.aPic1.getDrawable()).getBitmap());
        afterImg2 = Utility.getBytes(((BitmapDrawable) TaskFragment.aPic2.getDrawable()).getBitmap());
        afterImg3 = Utility.getBytes(((BitmapDrawable) TaskFragment.aPic3.getDrawable()).getBitmap());
    }*/

    public void main_insertToBaseData_db(){
        initBaseFragVar();
        String sql_date = Utility.getTodaysDate();
        String sql_sysaid = sysaid;
        String sql_taskType = taskType;
        String sql_customer = customer;
        String sql_site = site;
        String sql_address = address;
        String sql_region = region;
        String sql_phone = phone;
        String sql_email = email;
        String sql_location = locationCoordinates;
        String sql_stlRepName = stl_rep_name;
        String sql_stlRepPost = stl_rep_post;
        String sql_stlRepSign = stl_rep_sign;
        String sql_clientRepName = client_rep_name;
        String sql_clientRepPost = client_rep_post;
        String sql_clientRepSign = client_rep_sign;

        MaintenanceAppDB base_data_record = new MaintenanceAppDB(this);
        base_data_record.openForRead();
        base_data_record.createBaseDataRecord(sql_date, sql_sysaid, sql_taskType, sql_customer, sql_site,
                sql_address, sql_region, sql_phone, sql_email, sql_location, sql_stlRepName, sql_stlRepPost,
                sql_stlRepSign, sql_clientRepName, sql_clientRepPost, sql_clientRepSign);
        base_data_record.close();

    }


    /*public void main_insertToImage_db(){
        initImageVar();
        String sql_sysaid = sysaid;
        byte[] sql_img_1 = beforeImg1;
        byte[] sql_img_2 = beforeImg2;
        byte[] sql_img_3 = beforeImg3;
        byte[] sql_img_4 = afterImg1;
        byte[] sql_img_5 = afterImg2;
        byte[] sql_img_6 = afterImg3;

        MaintenanceAppDB image_record = new MaintenanceAppDB(this);
        image_record.openForRead();
        image_record.createImageRecord(sql_sysaid, sql_img_1, sql_img_2, sql_img_3,
                sql_img_4, sql_img_5, sql_img_6);
        image_record.close();

    }*/
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
                MaintenanceAppDB.maintenanceAppDatabase.insert(MaintenanceAppDB.TABLE_CUSTOMERS, null, cv);
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

    public static void InsertItemsIntoDB()
    {


        try{
            JSONArray jarray =new JSONArray(jsonString);

            MaintenanceAppDB sqlitedb = new MaintenanceAppDB(con);
            sqlitedb.deleteAllItems();
            sqlitedb.openForRead();



            for (int count = 0; count < jarray.length(); count++) {
                JSONObject obj = jarray.getJSONObject(count);
                itemName = obj.getString(TAG_NAME_ITEM);
                itemID = obj.getString(TAG_NUMBER_ITEM);

                equipment_full = itemName +" : " + itemID;

                ContentValues cv = new ContentValues();
                cv.put(MaintenanceAppDB.ITEM_NAME, equipment_full);
                cv.put(MaintenanceAppDB.ITEM_NUMBER, itemID);
                sqlitedb.maintenanceAppDatabase.insert(MaintenanceAppDB.TABLE_EQUIPMENT, null, cv);
            }

            int counter = sqlitedb.getItemsCount();
            Log.i("Items", String.valueOf(counter));

            if(counter == jarray.length()){
                Toast.makeText(con, String.valueOf(counter)+" Equipments Saved to Database Successfully!", Toast.LENGTH_SHORT).show();

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

    public void writeTask_noGen(){
        initBaseFragVar();
        String filename = sysaid;

        Main_CreatePDF fop = new Main_CreatePDF();
        MaintenanceAppDB findtask_noGen = new MaintenanceAppDB(this);
        String itemsArray;
        itemsArray = findtask_noGen.getTaskDataArray_noGen(filename);


        if (fop.writepdf_noGen(filename, itemsArray)) {
            Toast.makeText(getApplicationContext(), filename +  ".pdf created", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "I/O error", Toast.LENGTH_SHORT).show();
        }
    }

    public void writeTask_Gen(){
        initBaseFragVar();
        String filename = sysaid;

        Main_CreatePDF fop = new Main_CreatePDF();
        MaintenanceAppDB findtask_gen = new MaintenanceAppDB(this);
        String itemsArray;
        itemsArray = findtask_gen.getTaskDataArray_Gen(filename);


        if (fop.writepdf_gen(filename, itemsArray)) {
            Toast.makeText(getApplicationContext(), filename +  ".pdf created", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "I/O error", Toast.LENGTH_SHORT).show();
        }
    }

    public void save_final(){

        boolean insert = true;

        if(BaseDataFragment.sysaid.getText().toString().length() == 0){
            insert = false;
            Toast.makeText(this, "Sysaid Id is required", Toast.LENGTH_SHORT).show();
        }

        if(BaseDataFragment.customer.getText().toString().length() == 0){
            insert = false;
            Toast.makeText(this, "Customer Name is required", Toast.LENGTH_SHORT).show();

        }

        if(BaseDataFragment.site.getText().toString().length() == 0){
            insert = false;
            Toast.makeText(this, "Site Id is required", Toast.LENGTH_SHORT).show();

        }

        if(BaseDataFragment.count < 2 && TaskFragment.count <5){
            insert = false;
            Toast.makeText(this, "You should have at least 2 Images before and 2 Images after", Toast.LENGTH_SHORT).show();

        } else if(insert) {
            String selection = BaseDataFragment.taskSpinner.getSelectedItem().toString();

            main_insertToBaseData_db();
            //main_insertToImage_db();

            if (selection.equals("Generator Maintenance")) {
                writeTask_Gen();
            }
            else {
                writeTask_noGen();
            }

            Toast.makeText(this, "ALL DATA STORED SUCCESSFULLY", Toast.LENGTH_LONG).show();
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_maintenance, menu);
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
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.save_tickets) {
            save_final();
            return true;
        }
        if (id == R.id.get_customers) {
            itemClicked = "customers";
            downloadFromERP download = new downloadFromERP(getParent());
            download.execute();

            //Toast.makeText(this, "Customers successfully downloaded.", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.get_equipment) {
            itemClicked = "items";
            downloadFromERP download = new downloadFromERP(getParent());
            download.execute();

            //Toast.makeText(this, "Items successfully downloaded.", Toast.LENGTH_SHORT).show();
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
