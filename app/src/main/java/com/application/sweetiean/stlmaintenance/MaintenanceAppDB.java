package com.application.sweetiean.stlmaintenance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.FragmentActivity;

import com.application.sweetiean.stlservicing.ServicingActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sweetiean on 11/21/2015.
 */
public class MaintenanceAppDB {

    private DbHelper maintenanceAppHelper;
    private final Context maintenanceAppContext;
    //private SQLiteDatabase maintenanceAppDatabase;

    public static SQLiteDatabase maintenanceAppDatabase;

    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "maintenance_app_db";

    public static final String TABLE_MAIN_INFO = "Maintenance_Base_Data_Table";
    private static final String TABLE_MAIN_TASK = "Task_Table";
    private static final String TABLE_MAIN_IMAGES = "Images";
    
    public static final String TABLE_SERV_INFO = "Service_Base_Data_Table";
    private static final String TABLE_SERV_SERVICING = "Servicing_Table";
    private static final String TABLE_SERV_REPLACEMENT = "Replacement_Table";

    public static final String TABLE_CUSTOMERS = "Customer_Table";
    public static final String TABLE_EQUIPMENT = "Equipment_Table";

    //COMMON FIELDS
    public static final String ROW_ID = "_id";
    public static final String SYSAID_ID = "Sysaid_Id";
    public static final String TASK_TYPE = "Task_Type";
    public static final String CUSTOMER = "Customer";
    //public static final String SITE_ID = "Site_Id";
    public static final String ADDRESS = "Address";
    public static final String REGION = "Region";
    public static final String LOCATION = "Location_Coordinates";
    public static final String STL_REP_NAME = "STL_Rep_Name";
    public static final String STL_REP_POST = "STL_Rep_Position";
    public static final String STL_REP_SIGN = "STL_Rep_Signature";
    public static final String CLIENT_REP_NAME = "Client_Rep_Name";
    public static final String CLIENT_REP_POST = "Client_Rep_Position";
    public static final String CLIENT_REP_SIGN = "Client_Rep_Signature";
    public static final String REMARKS = "Remarks";
    public static final String DATE = "Date";

    //MAINTENANCE FIELDS
    public static final String PHONE = "Phone";
    public static final String EMAIL = "E_Mail";
    public static final String INVENTORY = "Inventory";
    public static final String GEN_HOURS = "GenSet_Run_Hours";
    public static final String BATTERY_VOLT = "Battery_Voltage";
    public static final String MAINTENANCE_TYPE = "Maintenance_Type";
    public static final String EQUIPMENT = "Equipment";
    public static final String QUANTITY = "Quantity";
    public static final String IMAGE_1 = "Image1";
    public static final String IMAGE_2 = "Image2";
    public static final String IMAGE_3 = "Image3";
    public static final String IMAGE_4 = "Image4";
    public static final String IMAGE_5 = "Image5";
    public static final String IMAGE_6 = "Image6";

    //SERVICING FIELDS
    public static final String DVR_FW_UPDATE = "Update_DVR_Firmware";
    public static final String FW_VERSION = "Firmware_Version";
    public static final String WORKSTATION = "Workstation";
    public static final String TEST_PC_PERFORMANCE = "Test_PC_Performance";
    public static final String CLEAN_PC = "Clean_PC";
    public static final String WORKSTATION_SERIAL = "Workstation_Serial";
    public static final String DVR = "DVR";
    public static final String DVR_SERIAL = "DVR_Serial";
    public static final String CAMERAS = "Cameras";
    public static final String CHECK_UPS = "UPS";
    public static final String UPS = "UPS_Type";
    public static final String UPS_SERIAL = "UPS_Serial";
    public static final String BACKUP_STATUS = "Backup_Status";
    public static final String HDD = "HDD";
    public static final String _500GB = "_500GB";
    public static final String HDD500SERIAL = "HDD_500Serial";
    public static final String _1TB = "_1TB";
    public static final String HDD1TBSERIAL = "HDD_1TBSerial";
    public static final String _2TB = "_2TB";
    public static final String HDD2TBSERIAL = "HDD_2TBSerial";
    public static final String DVR_TYPE = "DVR_Type";
    public static final String FIX_BOX = "Fix_Box";
    public static final String FIX_BOX_QTY = "Fix_Box_Qty";
    public static final String FIX_BOX_SERIAL = "FIx_Box_Serial";
    public static final String DOME_IR = "Dome_IR";
    public static final String DOME_IR_QTY = "Dome_IR_Qty";
    public static final String DOME_IR_SERIAL = "Dome_IR_Serial";
    public static final String BULLET_IR = "Bullet_IR";
    public static final String BULLET_IR_QTY = "Bullet_IR_Qty";
    public static final String BULLET_IR_SERIAL = "Bullet_IR_Serial";
    public static final String HOUSING = "Housing";
    public static final String HOUSING_QTY = "Housing_Qty";
    public static final String HOUSING_SERIAL = "Housing_Serial";
    public static final String POWER_SUPPLY = "Power_Supply";
    public static final String _12V_SUPPLY = "_12v_Supply";
    public static final String _12V_BOX = "_12v_Box";
    public static final String MONITOR = "Monitor";
    public static final String MONITOR_SERIAL = "Monitor_Serial";
    public static final String OTHER_ISSUES = "Other_Issues";
    public static final String ISSUES = "Issues";

    //CUSTOMER FIELDS
    public static final String CUSTOMER_NAME = "Customer_Name";
    public static final String CUSTOMER_ACCOUNT = "Customer_Account";

    //ITEMS FIELDS
    public static final String ITEM_NAME = "Item_Name";
    public static final String ITEM_NUMBER = "Item_ID";


    public long createBaseDataRecord(String sql_date, String sql_sysaid, String sql_taskType, String sql_customer,
                                     String sql_custAccount, String sql_address, String sql_region, String sql_phone,
                                     String sql_email, String sql_location, String sql_stlRepName, String sql_stlRepPost,
                                     String sql_stlRepSign, String sql_clientRepName, String sql_clientRepPost,
                                     String sql_clientRepSign) {

        ContentValues cv = new ContentValues();

        cv.put(DATE, sql_date);
        cv.put(SYSAID_ID, sql_sysaid);
        cv.put(TASK_TYPE, sql_taskType);
        cv.put(CUSTOMER, sql_customer);
        cv.put(CUSTOMER_ACCOUNT, sql_custAccount);
        cv.put(ADDRESS, sql_address);
        cv.put(REGION, sql_region);
        cv.put(PHONE, sql_phone);
        cv.put(EMAIL, sql_email);
        cv.put(LOCATION, sql_location);
        cv.put(STL_REP_NAME, sql_stlRepName);
        cv.put(STL_REP_POST, sql_stlRepPost);
        cv.put(STL_REP_SIGN, sql_stlRepSign);
        cv.put(CLIENT_REP_NAME, sql_clientRepName);
        cv.put(CLIENT_REP_POST, sql_clientRepPost);
        cv.put(CLIENT_REP_SIGN, sql_clientRepSign);

        return maintenanceAppDatabase.insert(TABLE_MAIN_INFO, null, cv);
    }

    public long createTaskRecord(String sql_sysaid, String sql_inventory, String sql_genHour, String sql_battVolt,
                                 String sql_maintenanceType, String sql_equipment, String sql_quantity, String sql_remarks) {

        ContentValues cv = new ContentValues();

        cv.put(SYSAID_ID, sql_sysaid);
        cv.put(INVENTORY, sql_inventory);
        cv.put(GEN_HOURS, sql_genHour);
        cv.put(BATTERY_VOLT, sql_battVolt);
        cv.put(MAINTENANCE_TYPE, sql_maintenanceType);
        cv.put(EQUIPMENT, sql_equipment);
        cv.put(QUANTITY, sql_quantity);
        cv.put(REMARKS, sql_remarks);


        return maintenanceAppDatabase.insert(TABLE_MAIN_TASK, null, cv);

    }

    public long createImageRecord(String sql_sysaid, byte[] sql_img_1, byte[] sql_img_2, byte[] sql_img_3, byte[] sql_img_4,
                                  byte[] sql_img_5, byte[] sql_img_6) {

        ContentValues cv = new ContentValues();

        cv.put(SYSAID_ID, sql_sysaid);
        cv.put(IMAGE_1, sql_img_1);
        cv.put(IMAGE_2, sql_img_2);
        cv.put(IMAGE_3, sql_img_3);
        cv.put(IMAGE_4, sql_img_4);
        cv.put(IMAGE_5, sql_img_5);
        cv.put(IMAGE_6, sql_img_6);


        return maintenanceAppDatabase.insert(TABLE_MAIN_IMAGES, null, cv);
    }

    public long createServBaseDataRecord(String sql_date, String sql_sysaid, String sql_taskType, String sql_customer,
                                         String sql_siteId, String sql_address, String sql_region, String sql_location,
                                         String sql_stlRepName, String sql_stlRepPost, String sql_stlRepSign,
                                         String sql_clientRepName, String sql_clientRepPost, String sql_clientRepSign) {

        ContentValues cv = new ContentValues();

        cv.put(DATE, sql_date);
        cv.put(SYSAID_ID, sql_sysaid);
        cv.put(TASK_TYPE, sql_taskType);
        cv.put(CUSTOMER, sql_customer);
        cv.put(CUSTOMER_ACCOUNT, sql_siteId);
        cv.put(ADDRESS, sql_address);
        cv.put(REGION, sql_region);
        cv.put(LOCATION, sql_location);
        cv.put(STL_REP_NAME, sql_stlRepName);
        cv.put(STL_REP_POST, sql_stlRepPost);
        cv.put(STL_REP_SIGN, sql_stlRepSign);
        cv.put(CLIENT_REP_NAME, sql_clientRepName);
        cv.put(CLIENT_REP_POST, sql_clientRepPost);
        cv.put(CLIENT_REP_SIGN, sql_clientRepSign);

        return maintenanceAppDatabase.insert(TABLE_SERV_INFO, null, cv);
    }

    public long createServicingRecord(String sql_sysaid, String sql_firmwareUpdate, String sql_firmwareVersion, String sql_workstation,
                                      String sql_testCP, String sql_cleanPC, String sql_workstationSerial, String sql_remarks,
                                      String sql_dvr, String sql_dvrSerial, String sql_cleanCam, String sql_checkUPS,
                                      String sql_ups, String sql_upsSerial, String sql_upsStatus) {

        ContentValues cv = new ContentValues();

        cv.put(SYSAID_ID, sql_sysaid);
        cv.put(DVR_FW_UPDATE, sql_firmwareUpdate);
        cv.put(FW_VERSION, sql_firmwareVersion);
        cv.put(WORKSTATION, sql_workstation);
        cv.put(TEST_PC_PERFORMANCE, sql_testCP);
        cv.put(CLEAN_PC, sql_cleanPC);
        cv.put(WORKSTATION_SERIAL, sql_workstationSerial);
        cv.put(REMARKS, sql_remarks);
        cv.put(DVR, sql_dvr);
        cv.put(DVR_SERIAL, sql_dvrSerial);
        cv.put(CAMERAS, sql_cleanCam);
        cv.put(CHECK_UPS, sql_checkUPS);
        cv.put(UPS, sql_ups);
        cv.put(UPS_SERIAL, sql_upsSerial);
        cv.put(BACKUP_STATUS, sql_upsStatus);


        return maintenanceAppDatabase.insert(TABLE_SERV_SERVICING, null, cv);

    }

    public long createReplacementRecord(String sql_sysaid, String sql_upsCheck, String sql_ups, String sql_upsSerial,
                                        String sql_workstation, String sql_workstationSerial, String sql_hdd, String sql_500,
                                        String sql_1TB, String sql_2TB, String sql_hdd500Serial, String sql_hdd1TBSerial, String sql_hdd2TBSerial, String sql_dvr, String sql_dvrType,
                                        String sql_dvrSerial, String sql_cameras, String sql_fixBox, String sql_fixBoxQty,
                                        String sql_fixBoxSerial, String sql_dome, String sql_domeQty, String sql_domeSerial,
                                        String sql_bullet, String sql_bulletQty, String sql_bulletSerial, String sql_housing,
                                        String sql_housingQty, String sql_housingSerial, String sql_powerSupp, String sql_12vSupp,
                                        String sql_12vBox, String sql_monitor, String sql_monitorSerial, String sql_issuesBox,
                                        String sql_issuesText) {

        ContentValues cv = new ContentValues();

        cv.put(SYSAID_ID, sql_sysaid);
        cv.put(CHECK_UPS, sql_upsCheck);
        cv.put(UPS, sql_ups);
        cv.put(UPS_SERIAL, sql_upsSerial);
        cv.put(WORKSTATION, sql_workstation);
        cv.put(WORKSTATION_SERIAL, sql_workstationSerial);
        cv.put(HDD, sql_hdd);
        cv.put(_500GB, sql_500);
        cv.put(HDD500SERIAL, sql_hdd500Serial);
        cv.put(_1TB, sql_1TB);
        cv.put(HDD1TBSERIAL, sql_hdd1TBSerial);
        cv.put(_2TB, sql_2TB);
        cv.put(HDD2TBSERIAL, sql_hdd2TBSerial);
        cv.put(DVR, sql_dvr);
        cv.put(DVR_TYPE, sql_dvrType);
        cv.put(DVR_SERIAL, sql_dvrSerial);
        cv.put(CAMERAS, sql_cameras);
        cv.put(FIX_BOX, sql_fixBox);
        cv.put(FIX_BOX_QTY, sql_fixBoxQty);
        cv.put(FIX_BOX_SERIAL, sql_fixBoxSerial);
        cv.put(DOME_IR, sql_dome);
        cv.put(DOME_IR_QTY, sql_domeQty);
        cv.put(DOME_IR_SERIAL, sql_domeSerial);
        cv.put(BULLET_IR, sql_bullet);
        cv.put(BULLET_IR_QTY, sql_bulletQty);
        cv.put(BULLET_IR_SERIAL, sql_bulletSerial);
        cv.put(HOUSING, sql_housing);
        cv.put(HOUSING_QTY, sql_housingQty);
        cv.put(HOUSING_SERIAL, sql_housingSerial);
        cv.put(POWER_SUPPLY, sql_powerSupp);
        cv.put(_12V_SUPPLY, sql_12vSupp);
        cv.put(_12V_BOX, sql_12vBox);
        cv.put(MONITOR, sql_monitor);
        cv.put(MONITOR_SERIAL, sql_monitorSerial);
        cv.put(OTHER_ISSUES, sql_issuesBox);
        cv.put(ISSUES, sql_issuesText);


        return maintenanceAppDatabase.insert(TABLE_SERV_REPLACEMENT, null, cv);
    }


    public int getCustomersCount() {

        maintenanceAppDatabase = openForRead();

        String countQuery = "SELECT " + CUSTOMER_NAME + " FROM " + TABLE_CUSTOMERS;
        Cursor cursor = maintenanceAppDatabase.rawQuery(countQuery, null);
        int count = cursor.getCount();

        if (cursor != null && !cursor.isClosed())
        {
            cursor.close();
        }
        this.close();

        // return count
        return count;
    }

    public void deleteAllCustomers(){
        maintenanceAppDatabase = openForRead();

        String deleteQuery = "DELETE FROM " + TABLE_CUSTOMERS;
        maintenanceAppDatabase.rawQuery(deleteQuery, null).moveToFirst();
        /*if (cursor != null && !cursor.isClosed())
        {
            cursor.close();
        }*/
        this.close();
    }

    public int getItemsCount() {

        maintenanceAppDatabase = openForRead();

        String countQuery = "SELECT " + ITEM_NAME + " FROM " + TABLE_EQUIPMENT;
        Cursor cursor = maintenanceAppDatabase.rawQuery(countQuery, null);
        int count = cursor.getCount();

        if (cursor != null && !cursor.isClosed())
        {
            cursor.close();
        }
        this.close();

        // return count
        return count;
    }

    public void deleteAllItems(){
        maintenanceAppDatabase = openForRead();

        String deleteQuery = "DELETE FROM " + TABLE_EQUIPMENT;
        maintenanceAppDatabase.rawQuery(deleteQuery, null).moveToFirst();
        /*if (cursor != null && !cursor.isClosed())
        {
            cursor.close();
        }*/
        this.close();
    }

    public String[] getAllCustNames()
    {
        maintenanceAppDatabase = openForRead();
        Cursor c = maintenanceAppDatabase.query(TABLE_CUSTOMERS, new String[] {CUSTOMER_NAME}, null, null, null, null, null);

        if(c.getCount() >0)
        {
            String[] str = new String[c.getCount()];
            int i = 0;

            while (c.moveToNext())
            {
                str[i] = c.getString(c.getColumnIndex(CUSTOMER_NAME));
                i++;
            }
            if (c != null && !c.isClosed())
            {
                c.close();
            }
            this.close();
            return str;
        }
        else
        {
            if (c != null && !c.isClosed())
            {
                c.close();
            }
            this.close();
            return new String[] {};
        }
    }
    public String[] getAllItemNames()
    {
        maintenanceAppDatabase = openForRead();
        Cursor c = maintenanceAppDatabase.query(TABLE_EQUIPMENT, new String[] {ITEM_NAME}, null, null, null, null, null);

        if(c.getCount() >0)
        {
            String[] str = new String[c.getCount()];
            int i = 0;

            while (c.moveToNext())
            {
                str[i] = c.getString(c.getColumnIndex(ITEM_NAME));
                i++;
            }

            if (c != null && !c.isClosed())
            {
                c.close();
            }
            this.close();
            return str;
        }
        else
        {
            if (c != null && !c.isClosed())
            {
                c.close();
            }
            this.close();
            return new String[] {};
        }
    }


    public String getTaskDataArray_noGen(String _sysAidId) {


        String[] columns = new String[]{ROW_ID, SYSAID_ID, INVENTORY, MAINTENANCE_TYPE, EQUIPMENT, QUANTITY, REMARKS};
        maintenanceAppDatabase = openForRead();

        Cursor c = maintenanceAppDatabase.query(TABLE_MAIN_TASK, columns, SYSAID_ID + "=?", new String[]{_sysAidId}, null, null, null);

        String result = "";

        int rowCount = 0;


        int iInventory = c.getColumnIndex(INVENTORY);
        int iMaintenanceType = c.getColumnIndex(MAINTENANCE_TYPE);
        int iEquipment = c.getColumnIndex(EQUIPMENT);
        int iQuantity = c.getColumnIndex(QUANTITY);
        int iRemarks = c.getColumnIndex(REMARKS);



        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

            rowCount = rowCount +1;
            String Inventory;
            String MaintenanceType;
            String Equipment;
            String Quantity;
            String Remark;


            Inventory =  "Inventory: " +c.getString(iInventory);
            MaintenanceType = "Maintenance Type: " + c.getString(iMaintenanceType);
            Equipment = "Equipment: " + c.getString(iEquipment);
            Quantity = "Quantity: " + c.getString(iQuantity);
            Remark =  "Remarks: " + c.getString(iRemarks);
            result = result + Inventory +", "+ MaintenanceType +", "+ Equipment +", "+ Quantity +", "+ Remark + "\n\n";


        }
        if (c != null && !c.isClosed())
        {
            c.close();
        }
        this.close();


        return result;
    }

    public String getTaskDataArray_Gen(String _sysAidId) {


        String[] columns = new String[]{ROW_ID, SYSAID_ID, INVENTORY, GEN_HOURS, BATTERY_VOLT, MAINTENANCE_TYPE, EQUIPMENT, QUANTITY, REMARKS};
        maintenanceAppDatabase = openForRead();

        Cursor c = maintenanceAppDatabase.query(TABLE_MAIN_TASK, columns, SYSAID_ID + "=?", new String[]{_sysAidId}, null, null, null);

        String result = "";

        int rowCount = 0;


        int iInventory = c.getColumnIndex(INVENTORY);
        int iGen = c.getColumnIndex(GEN_HOURS);
        int iVolt = c.getColumnIndex(BATTERY_VOLT);
        int iMaintenanceType = c.getColumnIndex(MAINTENANCE_TYPE);
        int iEquipment = c.getColumnIndex(EQUIPMENT);
        int iQuantity = c.getColumnIndex(QUANTITY);
        int iRemarks = c.getColumnIndex(REMARKS);




        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

            rowCount = rowCount +1;
            String Inventory;
            String GenHrs;
            String BattVolt;
            String MaintenanceType;
            String Equipment;
            String Quantity;
            String Remark;


            Inventory =  "Inventory: " +c.getString(iInventory);
            GenHrs = "GenSet Run Hours: " + c.getString(iGen);
            BattVolt =  "Battery Voltage: " + c.getString(iVolt);
            MaintenanceType =  "Maintenance Type: " + c.getString(iMaintenanceType);
            Equipment = "Equipment: " + c.getString(iEquipment);
            Quantity = "Quantity: " + c.getString(iQuantity);
            Remark =  "Remarks: " + c.getString(iRemarks);

            result = result + Inventory +", "+ GenHrs +", "+ BattVolt +", "+ MaintenanceType +", "+ Equipment +", "+ Quantity +", "+ Remark +"\n\n";


        }
        if (c != null && !c.isClosed())
        {
            c.close();
        }
        this.close();


        return result;
    }


    /*Following methods are used to retrieve
    **data from the sqlite database and send over to the ERP
     */

    //Maintenance
    public String getMaintenanceInfoRecord(String _sysAidId)  {
        String[] columns = new String[]{DATE, SYSAID_ID, TASK_TYPE, CUSTOMER, CUSTOMER_ACCOUNT, ADDRESS, REGION,
                PHONE, EMAIL, LOCATION, STL_REP_NAME, STL_REP_POST, CLIENT_REP_NAME, CLIENT_REP_POST};

        Cursor c;
        String result = "";
        maintenanceAppDatabase = openForRead();

        c = maintenanceAppDatabase.query(TABLE_MAIN_INFO, columns, SYSAID_ID + "=?", new String[]{_sysAidId}, null, null, null);



        int iDate = c.getColumnIndex(DATE);
        int iSysaid = c.getColumnIndex(SYSAID_ID);
        int iTask = c.getColumnIndex(TASK_TYPE);
        int iCustName = c.getColumnIndex(CUSTOMER);
        int iSite = c.getColumnIndex(CUSTOMER_ACCOUNT);
        int iAddress = c.getColumnIndex(ADDRESS);
        int iRegion = c.getColumnIndex(REGION);
        int iPhone = c.getColumnIndex(PHONE);
        int iMail = c.getColumnIndex(EMAIL);
        int iLocation = c.getColumnIndex(LOCATION);
        int iSTLName = c.getColumnIndex(STL_REP_NAME);
        int iSTLPost = c.getColumnIndex(STL_REP_POST);
        int iClientName = c.getColumnIndex(CLIENT_REP_NAME);
        int iClientPost = c.getColumnIndex(CLIENT_REP_POST);



        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){


            result = MaintenanceActivity.Main_axcall.pushMaintenanceInfoToAx(c.getString(iDate), c.getString(iSysaid), c.getString(iTask),
                    c.getString(iCustName), c.getString(iSite), c.getString(iAddress), c.getString(iRegion),
                    c.getString(iPhone), c.getString(iMail), c.getString(iLocation), c.getString(iSTLName),
                    c.getString(iSTLPost), c.getString(iClientName), c.getString(iClientPost), MaintenanceActivity.DeviceId);



        }
        if (c != null && !c.isClosed())
        {
            c.close();
        }

        return result;
    }


    public String getTaskRecord(String _sysAidId) {

        String[] columns = new String[]{SYSAID_ID, INVENTORY, GEN_HOURS, BATTERY_VOLT, MAINTENANCE_TYPE, EQUIPMENT, QUANTITY, REMARKS};

        Cursor c = maintenanceAppDatabase.query(TABLE_MAIN_TASK, columns, SYSAID_ID + "=?", new String[]{_sysAidId}, null, null, null);
        maintenanceAppDatabase = openForRead();

        String result = "";


        int iSysaid = c.getColumnIndex(SYSAID_ID);
        int iInventory = c.getColumnIndex(INVENTORY);
        int iGen = c.getColumnIndex(GEN_HOURS);
        int iVolt = c.getColumnIndex(BATTERY_VOLT);
        int iMaintenanceType = c.getColumnIndex(MAINTENANCE_TYPE);
        int iEquipment = c.getColumnIndex(EQUIPMENT);
        int iQty = c.getColumnIndex(QUANTITY);
        int iRemarks = c.getColumnIndex(REMARKS);



        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

            result = MaintenanceActivity.Main_axcall.pushMaintenanceTaskToAx(c.getString(iSysaid), c.getString(iInventory), c.getString(iGen), c.getString(iVolt),
                    c.getString(iMaintenanceType), c.getString(iEquipment), c.getString(iQty), c.getString(iRemarks));


        }
        if (c != null && !c.isClosed())
        {
            c.close();
        }


        return result;
    }


    //Servicing
    public String getServiceInfoRecord(String _sysAidId)  {
        String[] columns = new String[]{DATE, SYSAID_ID, TASK_TYPE, CUSTOMER, CUSTOMER_ACCOUNT, ADDRESS, REGION,
                LOCATION, STL_REP_NAME, STL_REP_POST, CLIENT_REP_NAME, CLIENT_REP_POST};

        Cursor c;
        String result = "";
        maintenanceAppDatabase = openForRead();

        c = maintenanceAppDatabase.query(TABLE_SERV_INFO, columns, SYSAID_ID + "=?", new String[]{_sysAidId}, null, null, null);



        int iDate = c.getColumnIndex(DATE);
        int iSysaid = c.getColumnIndex(SYSAID_ID);
        int iTask = c.getColumnIndex(TASK_TYPE);
        int iCustName = c.getColumnIndex(CUSTOMER);
        int iSite = c.getColumnIndex(CUSTOMER_ACCOUNT);
        int iAddress = c.getColumnIndex(ADDRESS);
        int iRegion = c.getColumnIndex(REGION);
        int iLocation = c.getColumnIndex(LOCATION);
        int iSTLName = c.getColumnIndex(STL_REP_NAME);
        int iSTLPost = c.getColumnIndex(STL_REP_POST);
        int iClientName = c.getColumnIndex(CLIENT_REP_NAME);
        int iClientPost = c.getColumnIndex(CLIENT_REP_POST);



        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){


            result = ServicingActivity.Serv_axcall.pushServiceInfoToAx(c.getString(iDate), c.getString(iSysaid), c.getString(iTask),
                    c.getString(iCustName), c.getString(iSite), c.getString(iAddress), c.getString(iRegion),
                    c.getString(iLocation), c.getString(iSTLName), c.getString(iSTLPost), c.getString(iClientName),
                    c.getString(iClientPost), ServicingActivity.DeviceId);



        }
        if (c != null && !c.isClosed())
        {
            c.close();
        }

        return result;
    }


    public String getServicingRecord(String _sysAidId)  {
        String[] columns = new String[]{SYSAID_ID, DVR_FW_UPDATE, FW_VERSION, WORKSTATION, TEST_PC_PERFORMANCE, CLEAN_PC,
                WORKSTATION_SERIAL, REMARKS, DVR, DVR_SERIAL, CAMERAS, CHECK_UPS, UPS, UPS_SERIAL, BACKUP_STATUS};

        Cursor c;
        String result = "";
        maintenanceAppDatabase = openForRead();

        c = maintenanceAppDatabase.query(TABLE_SERV_SERVICING, columns, SYSAID_ID + "=?", new String[]{_sysAidId}, null, null, null);



        int iSysaid = c.getColumnIndex(SYSAID_ID);
        int iFwUpdate = c.getColumnIndex(DVR_FW_UPDATE);
        int iFwVersion = c.getColumnIndex(FW_VERSION);
        int iWorkstation = c.getColumnIndex(WORKSTATION);
        int iTestPC = c.getColumnIndex(TEST_PC_PERFORMANCE);
        int iCleanPC = c.getColumnIndex(CLEAN_PC);
        int iWorkstationSerial = c.getColumnIndex(WORKSTATION_SERIAL);
        int iRemarks = c.getColumnIndex(REMARKS);
        int iDVR = c.getColumnIndex(DVR);
        int iDVRSerial = c.getColumnIndex(DVR_SERIAL);
        int iCameras = c.getColumnIndex(CAMERAS);
        int iCheckUPS = c.getColumnIndex(CHECK_UPS);
        int iUPS = c.getColumnIndex(UPS);
        int iUPSSerial = c.getColumnIndex(UPS_SERIAL);
        int iBackupStatus = c.getColumnIndex(BACKUP_STATUS);



        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){


            result = ServicingActivity.Serv_axcall.pushServicingToAx(c.getString(iSysaid), c.getString(iFwUpdate), c.getString(iFwVersion),
                    c.getString(iWorkstation), c.getString(iTestPC), c.getString(iCleanPC), c.getString(iWorkstationSerial),
                    c.getString(iRemarks), c.getString(iDVR), c.getString(iDVRSerial), c.getString(iCameras),
                    c.getString(iCheckUPS), c.getString(iUPS), c.getString(iUPSSerial), c.getString(iBackupStatus));



        }
        if (c != null && !c.isClosed())
        {
            c.close();
        }

        return result;
    }


    public String getReplacementRecord(String _sysAidId)  {
        String[] columns = new String[]{SYSAID_ID, CHECK_UPS, UPS, UPS_SERIAL, WORKSTATION, WORKSTATION_SERIAL,
                HDD, _500GB, HDD500SERIAL, _1TB, HDD1TBSERIAL, _2TB, HDD2TBSERIAL, DVR, DVR_TYPE, DVR_SERIAL,
                CAMERAS, FIX_BOX, FIX_BOX_QTY, FIX_BOX_SERIAL, DOME_IR, DOME_IR_QTY, DOME_IR_SERIAL, BULLET_IR,
                BULLET_IR_QTY, BULLET_IR_SERIAL, HOUSING, HOUSING_QTY, HOUSING_SERIAL, POWER_SUPPLY, _12V_SUPPLY,
                _12V_BOX, MONITOR, MONITOR_SERIAL, OTHER_ISSUES, ISSUES};

        Cursor c;
        String result = "";
        maintenanceAppDatabase = openForRead();

        c = maintenanceAppDatabase.query(TABLE_SERV_REPLACEMENT, columns, SYSAID_ID + "=?", new String[]{_sysAidId}, null, null, null);



        int iSysaid = c.getColumnIndex(SYSAID_ID);
        int iCheckUPS = c.getColumnIndex(CHECK_UPS);
        int iUPS = c.getColumnIndex(UPS);
        int iUPSSerial = c.getColumnIndex(UPS_SERIAL);
        int iWorkstation = c.getColumnIndex(WORKSTATION);
        int iWorkstationSerial = c.getColumnIndex(WORKSTATION_SERIAL);
        int iHDD = c.getColumnIndex(HDD);
        int i_500GB = c.getColumnIndex(_500GB);
        int i500Serial = c.getColumnIndex(HDD500SERIAL);
        int i_1TB = c.getColumnIndex(_1TB);
        int i1Serial = c.getColumnIndex(HDD1TBSERIAL);
        int i_2TB = c.getColumnIndex(_2TB);
        int i2Serial = c.getColumnIndex(HDD2TBSERIAL);
        int iDVR = c.getColumnIndex(DVR);
        int iDVRType = c.getColumnIndex(DVR_TYPE);
        int iDVRSerial = c.getColumnIndex(DVR_SERIAL);
        int iCameras = c.getColumnIndex(CAMERAS);
        int iFixbox = c.getColumnIndex(FIX_BOX);
        int iFixboxQty = c.getColumnIndex(FIX_BOX_QTY);
        int iFixboxSerial = c.getColumnIndex(FIX_BOX_SERIAL);
        int iDome = c.getColumnIndex(DOME_IR);
        int iDomeQty = c.getColumnIndex(DOME_IR_QTY);
        int iDomeSerial = c.getColumnIndex(DOME_IR_SERIAL);
        int iBullet = c.getColumnIndex(BULLET_IR);
        int iBulletQty = c.getColumnIndex(BULLET_IR_QTY);
        int iBulletSerial = c.getColumnIndex(BULLET_IR_SERIAL);
        int iHousing = c.getColumnIndex(HOUSING);
        int iHousingQty = c.getColumnIndex(HOUSING_QTY);
        int iHousingSerial = c.getColumnIndex(HOUSING_SERIAL);
        int iPower = c.getColumnIndex(POWER_SUPPLY);
        int iSupply = c.getColumnIndex(_12V_SUPPLY);
        int iBox = c.getColumnIndex(_12V_BOX);
        int iMonitor = c.getColumnIndex(MONITOR);
        int iMonitorSerial = c.getColumnIndex(MONITOR_SERIAL);
        int iOtherISsues = c.getColumnIndex(OTHER_ISSUES);
        int iIssues = c.getColumnIndex(ISSUES);



        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){


            result = ServicingActivity.Serv_axcall.pushReplacementToAx(c.getString(iSysaid), c.getString(iCheckUPS), c.getString(iUPS),
                    c.getString(iUPSSerial), c.getString(iWorkstation), c.getString(iWorkstationSerial), c.getString(iHDD),
                    c.getString(i_500GB), c.getString(i500Serial), c.getString(i_1TB), c.getString(i1Serial),
                    c.getString(i_2TB), c.getString(i2Serial), c.getString(iDVR), c.getString(iDVRType),
                    c.getString(iDVRSerial), c.getString(iCameras), c.getString(iFixbox), c.getString(iFixboxQty),
                    c.getString(iFixboxSerial), c.getString(iDome), c.getString(iDomeQty), c.getString(iDomeSerial),
                    c.getString(iBullet), c.getString(iBulletQty), c.getString(iBulletSerial), c.getString(iHousing),
                    c.getString(iHousingQty), c.getString(iHousingSerial), c.getString(iPower), c.getString(iSupply),
                    c.getString(iBox), c.getString(iMonitor), c.getString(iMonitorSerial), c.getString(iOtherISsues),
                    c.getString(iIssues));



        }
        if (c != null && !c.isClosed())
        {
            c.close();
        }

        return result;
    }



    private class DbHelper extends SQLiteOpenHelper{


        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            //MAINTENANCE TABLES
            db.execSQL("CREATE TABLE " + TABLE_MAIN_INFO + " ("
                    + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + DATE + " TEXT, "
                    + SYSAID_ID + " TEXT, "
                    + TASK_TYPE + " TEXT, "
                    + CUSTOMER + " TEXT, "
                    + CUSTOMER_ACCOUNT + " TEXT, "
                    + ADDRESS + " TEXT, "
                    + REGION + " TEXT, "
                    + PHONE + " TEXT, "
                    + EMAIL + " TEXT, "
                    + LOCATION + " TEXT, "
                    + STL_REP_NAME + " TEXT, "
                    + STL_REP_POST + " TEXT, "
                    + STL_REP_SIGN + " TEXT, "
                    + CLIENT_REP_NAME + " TEXT, "
                    + CLIENT_REP_POST + " TEXT, "
                    + CLIENT_REP_SIGN + " TEXT);"

            );

            db.execSQL("CREATE TABLE " + TABLE_MAIN_TASK + " ("
                    + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + SYSAID_ID + " TEXT, "
                    + INVENTORY + " TEXT, "
                    + GEN_HOURS + " TEXT, "
                    + BATTERY_VOLT + " TEXT, "
                    + MAINTENANCE_TYPE + " TEXT, "
                    + EQUIPMENT + " TEXT, "
                    + QUANTITY + " TEXT, "
                    + REMARKS + " TEXT);"

            );

            db.execSQL("CREATE TABLE " + TABLE_MAIN_IMAGES + " ("
                    + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + SYSAID_ID + " TEXT, "
                    + IMAGE_1 + " BLOB, "
                    + IMAGE_2 + " BLOB, "
                    + IMAGE_3 + " BLOB, "
                    + IMAGE_4 + " BLOB, "
                    + IMAGE_5 + " BLOB, "
                    + IMAGE_6 + " BLOB);"

            );

            //SERVICE REPORT TABLES
            db.execSQL("CREATE TABLE " + TABLE_SERV_INFO + " ("
                    + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + DATE + " TEXT, "
                    + SYSAID_ID + " TEXT, "
                    + TASK_TYPE + " TEXT, "
                    + CUSTOMER + " TEXT, "
                    + CUSTOMER_ACCOUNT + " TEXT, "
                    + ADDRESS + " TEXT, "
                    + REGION + " TEXT, "
                    + LOCATION + " TEXT, "
                    + STL_REP_NAME + " TEXT, "
                    + STL_REP_POST + " TEXT, "
                    + STL_REP_SIGN + " TEXT, "
                    + CLIENT_REP_NAME + " TEXT, "
                    + CLIENT_REP_POST + " TEXT, "
                    + CLIENT_REP_SIGN + " TEXT);"

            );

            db.execSQL("CREATE TABLE " + TABLE_SERV_SERVICING + " ("
                    + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + SYSAID_ID + " TEXT, "
                    + DVR_FW_UPDATE + " TEXT, "
                    + FW_VERSION + " TEXT, "
                    + WORKSTATION + " TEXT, "
                    + TEST_PC_PERFORMANCE + " TEXT, "
                    + CLEAN_PC + " TEXT, "
                    + WORKSTATION_SERIAL + " TEXT, "
                    + REMARKS + " TEXT, "
                    + DVR + " TEXT, "
                    + DVR_SERIAL + " TEXT, "
                    + CAMERAS + " TEXT, "
                    + CHECK_UPS + " TEXT, "
                    + UPS + " TEXT, "
                    + UPS_SERIAL + " TEXT, "
                    + BACKUP_STATUS + " TEXT);"

            );

            db.execSQL("CREATE TABLE " + TABLE_SERV_REPLACEMENT + " ("
                    + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + SYSAID_ID + " TEXT, "
                    + CHECK_UPS + " TEXT, "
                    + UPS + " TEXT, "
                    + UPS_SERIAL + " TEXT, "
                    + WORKSTATION + " TEXT, "
                    + WORKSTATION_SERIAL + " TEXT, "
                    + HDD + " TEXT, "
                    + _500GB + " TEXT, "
                    + HDD500SERIAL + " TEXT, "
                    + _1TB + " TEXT, "
                    + HDD1TBSERIAL + " TEXT, "
                    + _2TB + " TEXT, "
                    + HDD2TBSERIAL + " TEXT, "
                    + DVR + " TEXT, "
                    + DVR_TYPE + " TEXT, "
                    + DVR_SERIAL + " TEXT, "
                    + CAMERAS + " TEXT, "
                    + FIX_BOX + " TEXT, "
                    + FIX_BOX_QTY + " TEXT, "
                    + FIX_BOX_SERIAL + " TEXT, "
                    + DOME_IR + " TEXT, "
                    + DOME_IR_QTY + " TEXT, "
                    + DOME_IR_SERIAL + " TEXT, "
                    + BULLET_IR + " TEXT, "
                    + BULLET_IR_QTY + " TEXT, "
                    + BULLET_IR_SERIAL + " TEXT, "
                    + HOUSING + " TEXT, "
                    + HOUSING_QTY + " TEXT, "
                    + HOUSING_SERIAL + " TEXT, "
                    + POWER_SUPPLY + " TEXT, "
                    + _12V_SUPPLY + " TEXT, "
                    + _12V_BOX + " TEXT, "
                    + MONITOR + " TEXT, "
                    + MONITOR_SERIAL + " TEXT, "
                    + OTHER_ISSUES + " TEXT, "
                    + ISSUES + " TEXT);"

            );

            db.execSQL("CREATE TABLE " + TABLE_CUSTOMERS + " ("
                    + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + CUSTOMER_NAME + " TEXT, "
                    + CUSTOMER_ACCOUNT + " TEXT);"

            );

            db.execSQL("CREATE TABLE " + TABLE_EQUIPMENT + " ("
                            + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + ITEM_NAME + " TEXT, "
                            + ITEM_NUMBER + " TEXT);"

            );

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    public MaintenanceAppDB(Context c){
        maintenanceAppContext = c;
    }

    public SQLiteDatabase openForWrite(){
        maintenanceAppHelper = new DbHelper(maintenanceAppContext);
        maintenanceAppDatabase = maintenanceAppHelper.getWritableDatabase();
        return maintenanceAppDatabase;
    }
    public SQLiteDatabase openForRead(){
        maintenanceAppHelper = new DbHelper(maintenanceAppContext);
        maintenanceAppDatabase = maintenanceAppHelper.getReadableDatabase();
        return maintenanceAppDatabase;
    }

    public void close(){
        maintenanceAppHelper.close();
    }




}
