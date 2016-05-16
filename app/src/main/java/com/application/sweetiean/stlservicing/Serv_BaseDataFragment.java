package com.application.sweetiean.stlservicing;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.application.sweetiean.stlmaintenance.ImageSignFragment;
import com.application.sweetiean.stlmaintenance.MaintenanceAppDB;
import com.application.sweetiean.stlmaintenance.R;
import com.application.sweetiean.stlmaintenance.Utility;
import com.application.sweetiean.stlmaintenance.myLocationListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;


/**
 * A simple {@link Fragment} subclass.
 */
public class Serv_BaseDataFragment extends Fragment implements OnMapReadyCallback {

    View view;
    public static EditText sysaid, address;
    public static AutoCompleteTextView customer;
    public static TextView locationCoordinates;
    public static Spinner taskSpinner, regionSpinner;
    Button sign_STL, sign_client, location;
    public GoogleMap map;
    public LocationManager locationManager;
    public LocationListener locationListener;

    public static final int STL_SIGNATURE_ACTIVITY = 1;
    public static final int CLIENT_SIGNATURE_ACTIVITY = 2;

    public static String stl_rep_name, stl_rep_post, stl_rep_sign;
    public static String client_rep_name, client_rep_post, client_rep_sign;


    public Serv_BaseDataFragment() {
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

        view = inflater.inflate(R.layout.fragment_serv__base_data, container, false);
        init();
        return view;

    }


    public void init(){

        initMap();

        sysaid = (EditText)view.findViewById(R.id.sysaidIdEditText);
        if(sysaid.getText().toString().length() == 0){
            sysaid.setError("Sysaid Id is required!");
        }


        customer = (AutoCompleteTextView)view.findViewById(R.id.accntAutoCompleteTextView);
        if(customer.getText().toString().length() == 0){
            customer.setError("Customer is required!");
        }
        MaintenanceAppDB sqlitedb = new MaintenanceAppDB(this.getActivity());
        sqlitedb.openForRead();

        String[] accounts = sqlitedb.getAllCustNames();

        for(int i = 0; i < accounts.length; i++)
        {
            Log.i(this.toString(), accounts[i]);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_dropdown_item_1line, accounts);
        customer.setAdapter(adapter);
        customer.setThreshold(1);


        address = (EditText)view.findViewById(R.id.addressEditText);
        locationCoordinates = (TextView)view.findViewById(R.id.locationTextView);

        taskSpinner = (Spinner)view.findViewById(R.id.taskTypeSpinner);
        ArrayAdapter<CharSequence> taskAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.taskTypeArray, android.R.layout.simple_spinner_item);
        taskAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        taskSpinner.setAdapter(taskAdapter);

        taskSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(parent.getSelectedItem().toString().equals("Preventive Maintenance")){
                    sysaid.setText(Utility.getTodaysDatenoformat()+Utility.getCurrentTime()+ServicingActivity.DeviceId);
                }else if(parent.getSelectedItem().toString().equals("On Call")){
                    sysaid.getText().clear();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        regionSpinner = (Spinner)view.findViewById(R.id.regionSpinner);
        ArrayAdapter<CharSequence> regionAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.regionArray, android.R.layout.simple_spinner_item);
        regionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regionSpinner.setAdapter(regionAdapter);

        sign_client = (Button)view.findViewById(R.id.clientSignatureButton);
        sign_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ClientSignatureActivity.class);
                startActivityForResult(intent, CLIENT_SIGNATURE_ACTIVITY);
            }
        });

        sign_STL = (Button)view.findViewById(R.id.STLSignatureButton);
        sign_STL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), STLSignatureActivity.class);
                startActivityForResult(intent, STL_SIGNATURE_ACTIVITY);
            }
        });

        location = (Button)view.findViewById(R.id.getLocationButton);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getLocationWithMarker();

            }
        });


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        // Enabling MyLocation Layer of Google Map
        googleMap.setMyLocationEnabled(true);

    }

    public void initMap(){

        // Get the LocationManager object from the System Service LOCATION_SERVICE
        locationListener = new myLocationListener();
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10, this.locationListener);

        // Getting Google Play availability status
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());

        // Showing status
        if(status!= ConnectionResult.SUCCESS){ // Google Play Services are not available

            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this.getActivity(), requestCode);
            dialog.show();

        }
        else {

            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            SupportMapFragment fragment = new SupportMapFragment();
            transaction.add(R.id.map, fragment);
            transaction.commit();
            fragment.getMapAsync(this);

        }
    }

    private void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this.getActivity());
        alertDialog.setTitle("GPS Settings");
        alertDialog.setMessage("GPS is not enabled. Do you want to enable it?");

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();

    }

    private void getLocationWithMarker() {
        boolean isGPSEnabled;
        Location location;

        // Create a criteria object needed to retrieve the provider
        Criteria criteria = new Criteria();

        // Get the name of the best available provider
        String provider = locationManager.getBestProvider(criteria, true);

        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (isGPSEnabled) {
            // We can use the provider immediately to get the last known location
            location = locationManager.getLastKnownLocation(provider);
            if (location != null) {
                //map.clear();
                //  convert the location object to a LatLng object that can be used by the map API
                LatLng currentPosition = new LatLng(location.getLatitude(), location.getLongitude());

                locationCoordinates.setText(currentPosition.toString());

                // zoom to the current location
                //map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 15));

                // add a marker to the map indicating our current position
                //map.addMarker(new MarkerOptions().position(currentPosition).snippet("Lat:" + location.getLatitude() + "Lng:" + location.getLongitude()));
                //isGPSEnabled = true;
            } else {
                Toast.makeText(this.getActivity(), "Could not get location", Toast.LENGTH_LONG).show();
                locationCoordinates.setText("Location not available");

            }
        } else {

            showSettingsAlert();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == STL_SIGNATURE_ACTIVITY) {

            if (resultCode == STLSignatureActivity.RESULT_CANCELED) {
                Toast toast = Toast.makeText(this.getActivity(), "STL Signature not captured!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 105, 50);
                toast.show();
            } else {
                Bundle bundle = data.getExtras();
                String status = bundle.getString("status");
                if (status.equalsIgnoreCase("cancel")) {
                    Toast toast = Toast.makeText(this.getActivity(), "STL Signature not captured!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 105, 50);
                    toast.show();
                }
                if (status.equalsIgnoreCase("done")) {
                    Toast toast = Toast.makeText(this.getActivity(), "STL Signature capture successful!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 105, 50);
                    toast.show();
                }

            }
        }

        if (requestCode == CLIENT_SIGNATURE_ACTIVITY) {

            if (resultCode == STLSignatureActivity.RESULT_CANCELED) {
                Toast toast = Toast.makeText(this.getActivity(), "Client Signature not captured!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 105, 50);
                toast.show();
            } else {
                Bundle bundle = data.getExtras();
                String status = bundle.getString("status");
                if (status.equalsIgnoreCase("cancel")){
                    Toast toast = Toast.makeText(this.getActivity(), "Client Signature not captured!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 105, 50);
                    toast.show();
                }
                if (status.equalsIgnoreCase("done")) {
                    Toast toast = Toast.makeText(this.getActivity(), "Client Signature capture successful!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 105, 50);
                    toast.show();
                }

            }

        }

    }




}
