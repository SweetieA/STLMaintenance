package com.application.sweetiean.stlmaintenance;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.application.sweetiean.stlservicing.ServicingActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Created by sweetiean on 1/13/2016.
 */
public class webService {

    static Context c;
    //Namespace of the Webservice - It is http://tempuri.org for .NET webservice
    private static String NAMESPACE = "http://tempuri.org/";
    //Webservice URL - It is asmx file location hosted in the server in case of .Net
    //Change the IP address to your machine IP address
    //private static String URL = "http://192.168.251.209/taskservice/uploaddata.asmx"; // new address added
    private static String URL_Main = MaintenanceActivity.url;
    private static String URL_Serv = ServicingActivity.url;
    //SOAP Action URI again http://tempuri.org
    private static String SOAP_ACTION = "http://tempuri.org/";



    public static String SendMainFile(byte[] _data, String _fileName) {
        String resTxt = null;

        String webMethName="UploadFile";
        // Create request


        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        MarshalBase64 marshal = new MarshalBase64();


        PropertyInfo P1 = new PropertyInfo();
        P1.setName("fileName");
        P1.setValue(_fileName);
        P1.setType(String.class);
        request.addProperty(P1);

        PropertyInfo p2=new PropertyInfo();
        p2.setName("f");
        p2.setType(_data);
        p2.setValue(_data);
        request.addProperty(p2);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        //Set envelope as dotNet
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        marshal.register(envelope);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL_Main);

        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to resTxt variable static variable
            resTxt = response.toString();

        } catch (Exception e) {
            //Print error
            e.printStackTrace();
            resTxt = e.toString();
            //Assign error message to resTxt
            //resTxt = "Error occured";
        }
        //Return resTxt to calling object
        return resTxt;
    }

    public static String SendServFile(byte[] _data, String _fileName) {
        String resTxt = null;

        String webMethName="UploadFile";
        // Create request


        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        MarshalBase64 marshal = new MarshalBase64();


        PropertyInfo P1 = new PropertyInfo();
        P1.setName("fileName");
        P1.setValue(_fileName);
        P1.setType(String.class);
        request.addProperty(P1);

        PropertyInfo p2=new PropertyInfo();
        p2.setName("f");
        p2.setType(_data);
        p2.setValue(_data);
        request.addProperty(p2);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        //Set envelope as dotNet
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        marshal.register(envelope);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL_Serv);

        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to resTxt variable static variable
            resTxt = response.toString();

        } catch (Exception e) {
            //Print error
            e.printStackTrace();
            resTxt = e.toString();
            //Assign error message to resTxt
            //resTxt = "Error occured";
        }
        //Return resTxt to calling object
        return resTxt;
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String customers() {
        String resTxt = null;
        String webMethName="downloadCustomerInfo";
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        // Property which holds input parameters

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        //Set envelope as dotNet
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL_Main);

        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
            // Get the response
            Object response = envelope.getResponse();
            resTxt = response.toString();

        } catch (Exception e) {
            //Print error
            e.printStackTrace();
            resTxt = e.toString();
            //Assign error message to resTxt
            //resTxt = "Error occured";
        }
        //Return resTxt to calling object
        return resTxt;

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String equipment() {
        String resTxt = null;
        String webMethName="downloadEquipmentInfo";
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        // Property which holds input parameters

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        //Set envelope as dotNet
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL_Main);

        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
            // Get the response
            Object response = envelope.getResponse();
            resTxt = response.toString();

        } catch (Exception e) {
            //Print error
            e.printStackTrace();
            resTxt = e.toString();
        }
        //Return resTxt to calling object
        return resTxt;

    }



    public static String pushMaintenanceInfoToAx(String _date, String _sysaid, String _taskType, String _customer, String _siteId, String _address,
                                                 String _region, String _phone, String _email, String _location, String _stlRepName, String _stlRepPost,
                                                 String _clientRepName, String _clientRepPost, String _Imei) {
        String resTxt = null;
        String webMethName="pushMaintenanceInfoToAx";
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        // Property which holds input parameters
        PropertyInfo P1 = new PropertyInfo();
        PropertyInfo P2 = new PropertyInfo();
        PropertyInfo P3 = new PropertyInfo();
        PropertyInfo P4 = new PropertyInfo();
        PropertyInfo P5 = new PropertyInfo();
        PropertyInfo P6 = new PropertyInfo();
        PropertyInfo P7 = new PropertyInfo();
        PropertyInfo P8 = new PropertyInfo();
        PropertyInfo P9 = new PropertyInfo();
        PropertyInfo P10 = new PropertyInfo();
        PropertyInfo P11 = new PropertyInfo();
        PropertyInfo P12 = new PropertyInfo();
        PropertyInfo P13 = new PropertyInfo();
        PropertyInfo P14 = new PropertyInfo();
        PropertyInfo P15 = new PropertyInfo();



        P1.setName("_date");
        P1.setValue(_date);
        P1.setType(String.class);
        request.addProperty(P1);


        P2.setName("_sysaid");
        P2.setValue(_sysaid);
        P2.setType(String.class);
        request.addProperty(P2);


        P3.setName("_taskType");
        P3.setValue(_taskType);
        P3.setType(String.class);
        request.addProperty(P3);


        P4.setName("_customer");
        P4.setValue(_customer);
        P4.setType(String.class);
        request.addProperty(P4);


        P5.setName("_siteId");
        P5.setValue(_siteId);
        P5.setType(String.class);
        request.addProperty(P5);


        P6.setName("_address");
        P6.setValue(_address);
        P6.setType(String.class);
        request.addProperty(P6);


        P7.setName("_region");
        P7.setValue(_region);
        P7.setType(String.class);
        request.addProperty(P7);


        P8.setName("_phone");
        P8.setValue(_phone);
        P8.setType(String.class);
        request.addProperty(P8);


        P9.setName("_email");
        P9.setValue(_email);
        P9.setType(String.class);
        request.addProperty(P9);


        P10.setName("_location");
        P10.setValue(_location);
        P10.setType(String.class);
        request.addProperty(P10);


        P11.setName("_stlRepName");
        P11.setValue(_stlRepName);
        P11.setType(String.class);
        request.addProperty(P11);


        P12.setName("_stlRepPost");
        P12.setValue(_stlRepPost);
        P12.setType(String.class);
        request.addProperty(P12);


        P13.setName("_clientRepName");
        P13.setValue(_clientRepName);
        P13.setType(String.class);
        request.addProperty(P13);


        P14.setName("_clientRepPost");
        P14.setValue(_clientRepPost);
        P14.setType(String.class);
        request.addProperty(P14);


        P15.setName("_Imei");
        P15.setValue(_Imei);
        P15.setType(String.class);
        request.addProperty(P15);



        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        //Set envelope as dotNet
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL_Main);

        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to resTxt variable static variable
            resTxt = response.toString();

        } catch (Exception e) {
            //Print error
            e.printStackTrace();
            resTxt = e.toString();
            //Assign error message to resTxt
            //resTxt = "Error occured";
        }
        //Return resTxt to calling object
        return resTxt;
    }


    public static String pushMaintenanceTaskToAx(String _sysaid, String _inventory, String _genHours, String _batteryVolt, String _maintenanceType,
                                                 String _equipment, String _quantity, String _remarks) {
        String resTxt = null;
        String webMethName="pushMaintenanceTaskToAx";
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        // Property which holds input parameters
        PropertyInfo P1 = new PropertyInfo();
        PropertyInfo P2 = new PropertyInfo();
        PropertyInfo P3 = new PropertyInfo();
        PropertyInfo P4 = new PropertyInfo();
        PropertyInfo P5 = new PropertyInfo();
        PropertyInfo P6 = new PropertyInfo();
        PropertyInfo P7 = new PropertyInfo();
        PropertyInfo P8 = new PropertyInfo();


        // Set Name
        P1.setName("_sysaid");
        // Set Value
        P1.setValue(_sysaid);
        P1.setType(String.class);
        request.addProperty(P1);

        // Set Name
        P2.setName("_inventory");
        // Set Value
        P2.setValue(_inventory);
        P2.setType(String.class);
        request.addProperty(P2);

        // Set Name
        P3.setName("_genHours");
        // Set Value
        P3.setValue(_genHours);
        P3.setType(String.class);
        request.addProperty(P3);

        // Set Name
        P4.setName("_batteryVolt");
        // Set Value
        P4.setValue(_batteryVolt);
        P4.setType(String.class);
        request.addProperty(P4);

        // Set Name
        P5.setName("_maintenanceType");
        // Set Value
        P5.setValue(_maintenanceType);
        P5.setType(String.class);
        request.addProperty(P5);

        // Set Name
        P6.setName("_equipment");
        // Set Value
        P6.setValue(_equipment);
        P6.setType(String.class);
        request.addProperty(P6);

        // Set Name
        P7.setName("_quantity");
        // Set Value
        P7.setValue(_quantity);
        P7.setType(String.class);
        request.addProperty(P7);

        // Set Name
        P8.setName("_remarks");
        // Set Value
        P8.setValue(_remarks);
        P8.setType(String.class);
        request.addProperty(P8);



        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        //Set envelope as dotNet
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL_Main);

        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to resTxt variable static variable
            resTxt = response.toString();

        } catch (Exception e) {
            //Print error
            e.printStackTrace();
            resTxt = e.toString();
            //Assign error message to resTxt
            //resTxt = "Error occured";
        }
        //Return resTxt to calling object
        return resTxt;
    }

    public static String pushServiceInfoToAx(String _date, String _sysaid, String _taskType, String _customer, String _siteId, String _address,
                                                 String _region, String _location, String _stlRepName, String _stlRepPost,
                                                 String _clientRepName, String _clientRepPost, String _Imei) {
        String resTxt = null;
        String webMethName="pushServiceInfoToAx";
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        // Property which holds input parameters
        PropertyInfo P1 = new PropertyInfo();
        PropertyInfo P2 = new PropertyInfo();
        PropertyInfo P3 = new PropertyInfo();
        PropertyInfo P4 = new PropertyInfo();
        PropertyInfo P5 = new PropertyInfo();
        PropertyInfo P6 = new PropertyInfo();
        PropertyInfo P7 = new PropertyInfo();
        PropertyInfo P8 = new PropertyInfo();
        PropertyInfo P9 = new PropertyInfo();
        PropertyInfo P10 = new PropertyInfo();
        PropertyInfo P11 = new PropertyInfo();
        PropertyInfo P12 = new PropertyInfo();
        PropertyInfo P13 = new PropertyInfo();



        P1.setName("_date");
        P1.setValue(_date);
        P1.setType(String.class);
        request.addProperty(P1);


        P2.setName("_sysaid");
        P2.setValue(_sysaid);
        P2.setType(String.class);
        request.addProperty(P2);


        P3.setName("_taskType");
        P3.setValue(_taskType);
        P3.setType(String.class);
        request.addProperty(P3);


        P4.setName("_customer");
        P4.setValue(_customer);
        P4.setType(String.class);
        request.addProperty(P4);


        P5.setName("_siteId");
        P5.setValue(_siteId);
        P5.setType(String.class);
        request.addProperty(P5);


        P6.setName("_address");
        P6.setValue(_address);
        P6.setType(String.class);
        request.addProperty(P6);


        P7.setName("_region");
        P7.setValue(_region);
        P7.setType(String.class);
        request.addProperty(P7);


        P8.setName("_location");
        P8.setValue(_location);
        P8.setType(String.class);
        request.addProperty(P8);


        P9.setName("_stlRepName");
        P9.setValue(_stlRepName);
        P9.setType(String.class);
        request.addProperty(P9);


        P10.setName("_stlRepPost");
        P10.setValue(_stlRepPost);
        P10.setType(String.class);
        request.addProperty(P10);


        P11.setName("_clientRepName");
        P11.setValue(_clientRepName);
        P11.setType(String.class);
        request.addProperty(P11);


        P12.setName("_clientRepPost");
        P12.setValue(_clientRepPost);
        P12.setType(String.class);
        request.addProperty(P12);


        P13.setName("_Imei");
        P13.setValue(_Imei);
        P13.setType(String.class);
        request.addProperty(P13);



        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        //Set envelope as dotNet
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL_Serv);

        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to resTxt variable static variable
            resTxt = response.toString();

        } catch (Exception e) {
            //Print error
            e.printStackTrace();
            resTxt = e.toString();
            //Assign error message to resTxt
            //resTxt = "Error occured";
        }
        //Return resTxt to calling object
        return resTxt;
    }

    public static String pushServicingToAx(String _sysaid, String _firmwareUpdate, String _firmwareVersion, String _workstation,
                                      String _testCP, String _cleanPC, String _workstationSerial, String _remarks,
                                      String _dvr, String _dvrSerial, String _cleanCam, String _checkUPS,
                                      String _ups, String _upsSerial, String _upsStatus) {
        String resTxt = null;
        String webMethName="pushServicingToAx";
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        // Property which holds input parameters
        PropertyInfo P1 = new PropertyInfo();
        PropertyInfo P2 = new PropertyInfo();
        PropertyInfo P3 = new PropertyInfo();
        PropertyInfo P4 = new PropertyInfo();
        PropertyInfo P5 = new PropertyInfo();
        PropertyInfo P6 = new PropertyInfo();
        PropertyInfo P7 = new PropertyInfo();
        PropertyInfo P8 = new PropertyInfo();
        PropertyInfo P9 = new PropertyInfo();
        PropertyInfo P10 = new PropertyInfo();
        PropertyInfo P11 = new PropertyInfo();
        PropertyInfo P12 = new PropertyInfo();
        PropertyInfo P13 = new PropertyInfo();
        PropertyInfo P14 = new PropertyInfo();
        PropertyInfo P15 = new PropertyInfo();


        // Set Name
        P1.setName("_sysaid");
        // Set Value
        P1.setValue(_sysaid);
        P1.setType(String.class);
        request.addProperty(P1);

        // Set Name
        P2.setName("_firmwareUpdate");
        // Set Value
        P2.setValue(_firmwareUpdate);
        P2.setType(String.class);
        request.addProperty(P2);

        // Set Name
        P3.setName("_firmwareVersion");
        // Set Value
        P3.setValue(_firmwareVersion);
        P3.setType(String.class);
        request.addProperty(P3);

        // Set Name
        P4.setName("_workstation");
        // Set Value
        P4.setValue(_workstation);
        P4.setType(String.class);
        request.addProperty(P4);

        // Set Name
        P5.setName("_testCP");
        // Set Value
        P5.setValue(_testCP);
        P5.setType(String.class);
        request.addProperty(P5);

        // Set Name
        P6.setName("_cleanPC");
        // Set Value
        P6.setValue(_cleanPC);
        P6.setType(String.class);
        request.addProperty(P6);

        // Set Name
        P7.setName("_workstationSerial");
        // Set Value
        P7.setValue(_workstationSerial);
        P7.setType(String.class);
        request.addProperty(P7);

        // Set Name
        P8.setName("_remarks");
        // Set Value
        P8.setValue(_remarks);
        P8.setType(String.class);
        request.addProperty(P8);

        P9.setName("_dvr");
        P9.setValue(_dvr);
        P9.setType(String.class);
        request.addProperty(P9);

        P10.setName("_dvrSerial");
        P10.setValue(_dvrSerial);
        P10.setType(String.class);
        request.addProperty(P10);

        P11.setName("_cleanCam");
        P11.setValue(_cleanCam);
        P11.setType(String.class);
        request.addProperty(P11);

        P12.setName("_checkUPS");
        P12.setValue(_checkUPS);
        P12.setType(String.class);
        request.addProperty(P12);

        P13.setName("_ups");
        P13.setValue(_ups);
        P13.setType(String.class);
        request.addProperty(P13);

        P14.setName("_upsSerial");
        P14.setValue(_upsSerial);
        P14.setType(String.class);
        request.addProperty(P14);

        P15.setName("_upsStatus");
        P15.setValue(_upsStatus);
        P15.setType(String.class);
        request.addProperty(P15);



        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        //Set envelope as dotNet
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL_Serv);

        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to resTxt variable static variable
            resTxt = response.toString();

        } catch (Exception e) {
            //Print error
            e.printStackTrace();
            resTxt = e.toString();
            //Assign error message to resTxt
            //resTxt = "Error occured";
        }
        //Return resTxt to calling object
        return resTxt;
    }

    public static String pushReplacementToAx(String _sysaid, String _upsCheck, String _ups, String _upsSerial,
                                             String _workstation, String _workstationSerial, String _hdd, String _500,
                                             String _1TB, String _2TB, String _hdd500Serial, String _hdd1TBSerial,
                                             String _hdd2TBSerial, String _dvr, String _dvrType,
                                             String _dvrSerial, String _cameras, String _fixBox, String _fixBoxQty,
                                             String _fixBoxSerial, String _dome, String _domeQty, String _domeSerial,
                                             String _bullet, String _bulletQty, String _bulletSerial, String _housing,
                                             String _housingQty, String _housingSerial, String _powerSupp, String _12vSupp,
                                             String _12vBox, String _monitor, String _monitorSerial, String _issuesBox,
                                             String _issuesText) {
        String resTxt = null;
        String webMethName="pushReplacementToAx";
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        // Property which holds input parameters
        PropertyInfo P1 = new PropertyInfo();
        PropertyInfo P2 = new PropertyInfo();
        PropertyInfo P3 = new PropertyInfo();
        PropertyInfo P4 = new PropertyInfo();
        PropertyInfo P5 = new PropertyInfo();
        PropertyInfo P6 = new PropertyInfo();
        PropertyInfo P7 = new PropertyInfo();
        PropertyInfo P8 = new PropertyInfo();
        PropertyInfo P9 = new PropertyInfo();
        PropertyInfo P10 = new PropertyInfo();
        PropertyInfo P11 = new PropertyInfo();
        PropertyInfo P12 = new PropertyInfo();
        PropertyInfo P13 = new PropertyInfo();
        PropertyInfo P14 = new PropertyInfo();
        PropertyInfo P15 = new PropertyInfo();
        PropertyInfo P16 = new PropertyInfo();
        PropertyInfo P17 = new PropertyInfo();
        PropertyInfo P18 = new PropertyInfo();
        PropertyInfo P19 = new PropertyInfo();
        PropertyInfo P20 = new PropertyInfo();
        PropertyInfo P21 = new PropertyInfo();
        PropertyInfo P22 = new PropertyInfo();
        PropertyInfo P23 = new PropertyInfo();
        PropertyInfo P24 = new PropertyInfo();
        PropertyInfo P25 = new PropertyInfo();
        PropertyInfo P26 = new PropertyInfo();
        PropertyInfo P27 = new PropertyInfo();
        PropertyInfo P28 = new PropertyInfo();
        PropertyInfo P29 = new PropertyInfo();
        PropertyInfo P30 = new PropertyInfo();
        PropertyInfo P31 = new PropertyInfo();
        PropertyInfo P32 = new PropertyInfo();
        PropertyInfo P33 = new PropertyInfo();
        PropertyInfo P34 = new PropertyInfo();
        PropertyInfo P35 = new PropertyInfo();
        PropertyInfo P36 = new PropertyInfo();


        // Set Name
        P1.setName("_sysaid");
        // Set Value
        P1.setValue(_sysaid);
        P1.setType(String.class);
        request.addProperty(P1);

        // Set Name
        P2.setName("_upsCheck");
        // Set Value
        P2.setValue(_upsCheck);
        P2.setType(String.class);
        request.addProperty(P2);

        // Set Name
        P3.setName("_ups");
        // Set Value
        P3.setValue(_ups);
        P3.setType(String.class);
        request.addProperty(P3);

        // Set Name
        P4.setName("_upsSerial");
        // Set Value
        P4.setValue(_upsSerial);
        P4.setType(String.class);
        request.addProperty(P4);

        // Set Name
        P5.setName("_workstation");
        // Set Value
        P5.setValue(_workstation);
        P5.setType(String.class);
        request.addProperty(P5);

        // Set Name
        P6.setName("_workstationSerial");
        // Set Value
        P6.setValue(_workstationSerial);
        P6.setType(String.class);
        request.addProperty(P6);

        // Set Name
        P7.setName("_hdd");
        // Set Value
        P7.setValue(_hdd);
        P7.setType(String.class);
        request.addProperty(P7);

        // Set Name
        P8.setName("_500");
        // Set Value
        P8.setValue(_500);
        P8.setType(String.class);
        request.addProperty(P8);

        P9.setName("_1TB");
        P9.setValue(_1TB);
        P9.setType(String.class);
        request.addProperty(P9);

        P10.setName("_2TB");
        P10.setValue(_2TB);
        P10.setType(String.class);
        request.addProperty(P10);

        P11.setName("_hdd500Serial");
        P11.setValue(_hdd500Serial);
        P11.setType(String.class);
        request.addProperty(P11);

        P12.setName("_hdd1TBSerial");
        P12.setValue(_hdd1TBSerial);
        P12.setType(String.class);
        request.addProperty(P12);

        P13.setName("_hdd2TBSerial");
        P13.setValue(_hdd2TBSerial);
        P13.setType(String.class);
        request.addProperty(P13);

        P14.setName("_dvr");
        P14.setValue(_dvr);
        P14.setType(String.class);
        request.addProperty(P14);

        P15.setName("_dvrType");
        P15.setValue(_dvrType);
        P15.setType(String.class);
        request.addProperty(P15);

        P16.setName("_dvrSerial");
        P16.setValue(_dvrSerial);
        P16.setType(String.class);
        request.addProperty(P16);

        P17.setName("_cameras");
        P17.setValue(_cameras);
        P17.setType(String.class);
        request.addProperty(P17);

        P18.setName("_fixBox");
        P18.setValue(_fixBox);
        P18.setType(String.class);
        request.addProperty(P18);

        P19.setName("_fixBoxQty");
        P19.setValue(_fixBoxQty);
        P19.setType(String.class);
        request.addProperty(P19);

        P20.setName("_fixBoxSerial");
        P20.setValue(_fixBoxSerial);
        P20.setType(String.class);
        request.addProperty(P20);

        P21.setName("_dome");
        P21.setValue(_dome);
        P21.setType(String.class);
        request.addProperty(P21);

        P22.setName("_domeQty");
        P22.setValue(_domeQty);
        P22.setType(String.class);
        request.addProperty(P22);

        P23.setName("_domeSerial");
        P23.setValue(_domeSerial);
        P23.setType(String.class);
        request.addProperty(P23);

        P24.setName("_bullet");
        P24.setValue(_bullet);
        P24.setType(String.class);
        request.addProperty(P24);

        P25.setName("_bulletQty");
        P25.setValue(_bulletQty);
        P25.setType(String.class);
        request.addProperty(P25);

        P26.setName("_bulletSerial");
        P26.setValue(_bulletSerial);
        P26.setType(String.class);
        request.addProperty(P26);

        P27.setName("_housing");
        P27.setValue(_housing);
        P27.setType(String.class);
        request.addProperty(P27);

        P28.setName("_housingQty");
        P28.setValue(_housingQty);
        P28.setType(String.class);
        request.addProperty(P28);

        P29.setName("_housingSerial");
        P29.setValue(_housingSerial);
        P29.setType(String.class);
        request.addProperty(P29);

        P30.setName("_powerSupp");
        P30.setValue(_powerSupp);
        P30.setType(String.class);
        request.addProperty(P30);

        P31.setName("_12vSupp");
        P31.setValue(_12vSupp);
        P31.setType(String.class);
        request.addProperty(P31);

        P32.setName("_12vBox");
        P32.setValue(_12vBox);
        P32.setType(String.class);
        request.addProperty(P32);

        P33.setName("_monitor");
        P33.setValue(_monitor);
        P33.setType(String.class);
        request.addProperty(P33);

        P34.setName("_monitorSerial");
        P34.setValue(_monitorSerial);
        P34.setType(String.class);
        request.addProperty(P34);

        P35.setName("_issuesBox");
        P35.setValue(_issuesBox);
        P35.setType(String.class);
        request.addProperty(P35);

        P36.setName("_issuesText");
        P36.setValue(_issuesText);
        P36.setType(String.class);
        request.addProperty(P36);



        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        //Set envelope as dotNet
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL_Serv);

        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to resTxt variable static variable
            resTxt = response.toString();

        } catch (Exception e) {
            //Print error
            e.printStackTrace();
            resTxt = e.toString();
            //Assign error message to resTxt
            //resTxt = "Error occured";
        }
        //Return resTxt to calling object
        return resTxt;
    }



}
