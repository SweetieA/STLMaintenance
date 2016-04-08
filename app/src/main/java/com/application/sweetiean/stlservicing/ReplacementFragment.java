package com.application.sweetiean.stlservicing;


import android.os.Bundle;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class ReplacementFragment extends Fragment {

    View view;
    public static Spinner UPSSpinner;
    public static CheckBox ups, workstation, hdd, _500GB, _1TB, _2TB, dvr, cameras, fixBox, domeIR, bulletIR,
            housing, powerSupply, _12vPwrSupply, _12vPwrBox, monitor, otherIssues;
    public static EditText UPSSerial, workstationSerial, hdd500Serial, hdd1TBSerial, hdd2TBSerial, dvrType, dvrSerial, fixBoxQty, domeQty,
            bulletQty, housingQty, fixBoxSerial, domeSerial, bulletSerial, housingSerial, monitorSerial, issues;


    public ReplacementFragment() {
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
        view = inflater.inflate(R.layout.fragment_replacement, container, false);
        init();
        return view;
    }


    public void init(){

        UPSSpinner = (Spinner)view.findViewById(R.id.UPSSpinner);
        ArrayAdapter<CharSequence> UPSAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.UPSArray, android.R.layout.simple_spinner_item);
        UPSAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        UPSSpinner.setAdapter(UPSAdapter);

        ups = (CheckBox)view.findViewById(R.id.UPSCheckbox);
        workstation = (CheckBox)view.findViewById(R.id.workstationCheckbox);
        hdd = (CheckBox)view.findViewById(R.id.HDDCheckbox);
        _500GB = (CheckBox)view.findViewById(R.id.GB500Checkbox);
        _1TB = (CheckBox)view.findViewById(R.id.TB1Checkbox);
        _2TB = (CheckBox)view.findViewById(R.id.TB2Checkbox);
        dvr = (CheckBox)view.findViewById(R.id.DVRCheckbox);
        cameras = (CheckBox)view.findViewById(R.id.cameraCheckbox);
        fixBox = (CheckBox)view.findViewById(R.id.fixboxCheckbox);
        domeIR = (CheckBox)view.findViewById(R.id.domeCheckbox);
        bulletIR = (CheckBox)view.findViewById(R.id.bulletCheckbox);
        housing = (CheckBox)view.findViewById(R.id.housingCheckbox);
        powerSupply = (CheckBox)view.findViewById(R.id.powerCheckbox);
        _12vPwrSupply = (CheckBox)view.findViewById(R.id.powerSupplyCheckbox);
        _12vPwrBox = (CheckBox)view.findViewById(R.id.powerBoxCheckbox);
        monitor = (CheckBox)view.findViewById(R.id.monitorCheckbox);
        otherIssues = (CheckBox)view.findViewById(R.id.issuesCheckbox);

        UPSSerial = (EditText)view.findViewById(R.id.UPSSerialEditText);
        workstationSerial = (EditText)view.findViewById(R.id.workstationSerialEditText);
        hdd500Serial = (EditText)view.findViewById(R.id._500SerialEditText);
        hdd1TBSerial = (EditText)view.findViewById(R.id._1TBSerialEditText);
        hdd2TBSerial = (EditText)view.findViewById(R.id._2TBSerialEditText);
        dvrType = (EditText)view.findViewById(R.id.typeEditText);
        dvrSerial = (EditText)view.findViewById(R.id.DVRSerialEditText);
        fixBoxQty = (EditText)view.findViewById(R.id.fixboxQtyEditText);
        domeQty = (EditText)view.findViewById(R.id.domeQtyEditText);
        bulletQty = (EditText)view.findViewById(R.id.bulletQtyEditText);
        housingQty = (EditText)view.findViewById(R.id.housingQtyEditText);
        fixBoxSerial = (EditText)view.findViewById(R.id.fixboxSerialEditText);
        domeSerial = (EditText)view.findViewById(R.id.domeSerialEditText);
        bulletSerial = (EditText)view.findViewById(R.id.bulletSerialEditText);
        housingSerial = (EditText)view.findViewById(R.id.housingSerialEditText);
        monitorSerial = (EditText)view.findViewById(R.id.monitorSerialEditText);
        issues = (EditText)view.findViewById(R.id.issuesEditText);

    }


}
