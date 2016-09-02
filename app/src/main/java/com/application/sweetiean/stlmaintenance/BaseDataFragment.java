package com.application.sweetiean.stlmaintenance;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;


/**
 * A simple {@link Fragment} subclass.
 */
public class BaseDataFragment extends Fragment implements OnMapReadyCallback {

    View view;
    public static ImageView bPic1, bPic2, bPic3;
    public static EditText sysaid, address, phone, email, site;
    public static AutoCompleteTextView customer;
    public static Spinner taskSpinner, regionSpinner;
    public static TextView locationCoordinates;
    Button location, captureBPic;
    public GoogleMap map;
    public LocationManager locationManager;
    public LocationListener locationListener;

    Uri FileUri;
    Bitmap bmp;
    static int count = 1;
    public static final int CAMERADATA = 1;


    public BaseDataFragment() {
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

        view = inflater.inflate(R.layout.fragment_base_data, container, false);
        init();
        return view;
    }

    public void init(){

        initMap();

        sysaid = (EditText)view.findViewById(R.id.sysaidIdEditText);
        if(sysaid.getText().toString().length() == 0){
            sysaid.setError("Sysaid Id is required!");
        }

        site = (EditText)view.findViewById(R.id.siteIdEditText);
        if(site.getText().toString().length() == 0){
            site.setError("Site Id is required!");
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
        phone = (EditText)view.findViewById(R.id.phoneEditText);
        email = (EditText)view.findViewById(R.id.emailEditText);
        locationCoordinates = (TextView)view.findViewById(R.id.locationTextView);

        taskSpinner = (Spinner)view.findViewById(R.id.taskTypeSpinner);
        ArrayAdapter<CharSequence> taskAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.mtaskTypeArray, android.R.layout.simple_spinner_item);
        taskAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        taskSpinner.setAdapter(taskAdapter);
        taskSpinner.setOnItemSelectedListener(new mTasktypeListner());

        regionSpinner = (Spinner)view.findViewById(R.id.regionSpinner);
        ArrayAdapter<CharSequence> regionAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.regionArray, android.R.layout.simple_spinner_item);
        regionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regionSpinner.setAdapter(regionAdapter);

        location = (Button)view.findViewById(R.id.getLocationButton);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getLocationWithMarker();

            }
        });

        /*bPic1 = (ImageView)view.findViewById(R.id.Img1);
        bPic2 = (ImageView)view.findViewById(R.id.Img2);
        bPic3 = (ImageView)view.findViewById(R.id.Img3);*/

        captureBPic = (Button)view.findViewById(R.id.takePhotoButton);
        captureBPic.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (count <= 3) {
                    Intent m = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    String imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+
                            sysaid.getText().toString()+ taskSpinner.getSelectedItem().toString() + count + ".jpg";
                    File _file = new File(imageFilePath);
                    FileUri = Uri.fromFile(_file);//it was declared globally
                    m.putExtra(MediaStore.EXTRA_OUTPUT, FileUri);
                    startActivityForResult(m, CAMERADATA);
                } else {

                    captureBPic.setEnabled(false);
                }
            }
        });


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
    public void onMapReady(GoogleMap googleMap) {
        //boolean isGPSEnabled;
        //Location location;

        //TODO

        // Enabling MyLocation Layer of Google Map
        googleMap.setMyLocationEnabled(true);



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



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == CAMERADATA) {
            switch (count) {
                case 1:
                    if (resultCode == getActivity().RESULT_OK) {
                       /* Bundle extra = data.getExtras();
                        bmp1 = (Bitmap) extra.get("data");
                        instImg1.setImageBitmap(bmp1);*/
                        Uri uri = FileUri;
                        bmp = BitmapFactory.decodeFile(uri.getPath());
                        TaskFragment.bPic1.setImageBitmap(bmp);
                        count++;
                    }
                    break;

                case 2:
                    if (resultCode == getActivity().RESULT_OK) {
                        /*Bundle extra = data.getExtras();
                        bmp1 = (Bitmap) extra.get("data");*/
                        Uri uri = FileUri;
                        bmp = BitmapFactory.decodeFile(uri.getPath());
                        TaskFragment.bPic2.setImageBitmap(bmp);
                        count++;
                    }
                    break;

                case 3:
                    if (resultCode == getActivity().RESULT_OK) {
                       /* Bundle extra = data.getExtras();
                        bmp1 = (Bitmap) extra.get("data");*/
                        Uri uri = FileUri;
                        bmp = BitmapFactory.decodeFile(uri.getPath());
                        TaskFragment.bPic3.setImageBitmap(bmp);
                        count++;
                    }
                    break;
            }

        }
    }
}
