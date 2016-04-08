package com.application.sweetiean.stlservicing;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.application.sweetiean.stlmaintenance.MaintenanceAppDB;
import com.application.sweetiean.stlmaintenance.R;
import com.application.sweetiean.stlmaintenance.Utility;


/**
 * A simple {@link Fragment} subclass.
 */
public class ServicingFragment extends Fragment {

    View view;
    public static Spinner UPSSpinner, statusSpinner;
    public static CheckBox dvrFirmware, workstation, testCP, cleanPC, dvr, cleanCam, checkUPS;
    public static EditText firmwareVersion, workstationSerial, remarks, dvrSerial, UPSSerial;


    public ServicingFragment() {
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
        view = inflater.inflate(R.layout.fragment_servicing, container, false);
        init();
        return view;
    }


    public void init(){

        dvrFirmware = (CheckBox)view.findViewById(R.id.firmwareUpdateCheckbox);
        workstation = (CheckBox)view.findViewById(R.id.workstationCheckbox);
        testCP = (CheckBox)view.findViewById(R.id.testPCPerformanceCheckbox);
        cleanPC = (CheckBox)view.findViewById(R.id.cleanPCCheckbox);
        dvr = (CheckBox)view.findViewById(R.id.serviceDVRCheckbox);
        cleanCam = (CheckBox)view.findViewById(R.id.cleanCameraCheckbox);
        checkUPS = (CheckBox)view.findViewById(R.id.UPSCheckbox);

        firmwareVersion = (EditText)view.findViewById(R.id.firmwareVersionEditText);
        workstationSerial = (EditText)view.findViewById(R.id.workstationSerialEditText);
        remarks = (EditText)view.findViewById(R.id.remarksEditText);
        dvrSerial = (EditText)view.findViewById(R.id.DVRSerialEditText);
        UPSSerial = (EditText)view.findViewById(R.id.UPSSerialEditText);

        UPSSpinner = (Spinner)view.findViewById(R.id.UPSSpinner);
        ArrayAdapter<CharSequence> UPSAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.UPSArray, android.R.layout.simple_spinner_item);
        UPSAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        UPSSpinner.setAdapter(UPSAdapter);

        statusSpinner = (Spinner)view.findViewById(R.id.statusSpinner);
        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.statusArray, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(statusAdapter);


    }



}
