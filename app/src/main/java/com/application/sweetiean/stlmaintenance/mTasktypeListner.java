package com.application.sweetiean.stlmaintenance;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

/**
 * Created by sweetiean on 11/12/2015.
 */
public class mTasktypeListner implements AdapterView.OnItemSelectedListener {

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        parent.getContext();
        if (parent.getSelectedItem().toString().equals("AC Maintenance")) {

            TaskFragment.task.setText(parent.getItemAtPosition(position).toString());

            ArrayAdapter<CharSequence> taskAdapter = ArrayAdapter.createFromResource(TaskFragment.view.getContext(), R.array.ACMaintenanceArray, android.R.layout.simple_spinner_item);
            taskAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            TaskFragment.maintenanceType.setAdapter(taskAdapter);

        }
        else if (parent.getSelectedItem().toString().equals("UPS Maintenance")) {

            TaskFragment.task.setText(parent.getItemAtPosition(position).toString());

            ArrayAdapter<CharSequence> taskAdapter = ArrayAdapter.createFromResource(TaskFragment.view.getContext(), R.array.UPSMaintenanceArray, android.R.layout.simple_spinner_item);
            taskAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            TaskFragment.maintenanceType.setAdapter(taskAdapter);

        }
        else if (parent.getSelectedItem().toString().equals("Generator Maintenance")) {

            TaskFragment.task.setText(parent.getItemAtPosition(position).toString());
            //TaskFragment.genHours.setVisibility(View.VISIBLE);
            //TaskFragment.battVolt.setVisibility(View.VISIBLE);

            ArrayAdapter<CharSequence> taskAdapter = ArrayAdapter.createFromResource(TaskFragment.view.getContext(), R.array.genMaintenanceArray, android.R.layout.simple_spinner_item);
            taskAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            TaskFragment.maintenanceType.setAdapter(taskAdapter);

        }
        else if (parent.getSelectedItem().toString().equals("AVR Maintenance")) {

            TaskFragment.task.setText(parent.getItemAtPosition(position).toString());

            ArrayAdapter<CharSequence> taskAdapter = ArrayAdapter.createFromResource(TaskFragment.view.getContext(), R.array.AVRMaintenanceArray, android.R.layout.simple_spinner_item);
            taskAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            TaskFragment.maintenanceType.setAdapter(taskAdapter);

        }else if (parent.getSelectedItem().toString().equals("Fumigation")) {

            TaskFragment.task.setText(parent.getItemAtPosition(position).toString());

            ArrayAdapter<CharSequence> taskAdapter = ArrayAdapter.createFromResource(TaskFragment.view.getContext(), R.array.fumigationArray, android.R.layout.simple_spinner_item);
            taskAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            TaskFragment.maintenanceType.setAdapter(taskAdapter);

        }
        else if (parent.getSelectedItem().toString().equals("Vacuum Cleaning")) {

            TaskFragment.task.setText(parent.getItemAtPosition(position).toString());

            ArrayAdapter<CharSequence> taskAdapter = ArrayAdapter.createFromResource(TaskFragment.view.getContext(), R.array.vacuumCleaningArray, android.R.layout.simple_spinner_item);
            taskAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            TaskFragment.maintenanceType.setAdapter(taskAdapter);

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
