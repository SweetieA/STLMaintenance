package com.application.sweetiean.stlmaintenance;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskFragment extends Fragment {

    public static View view;
    public static Spinner maintenanceType;
    public static TextView task;
    public static LinearLayout genHours, battVolt;
    public static EditText inventory, genHrs, batVolt, quantity, remarks;
    public static AutoCompleteTextView equipment;

    public static ImageView bPic1, bPic2, bPic3,aPic1, aPic2, aPic3;
    Button captureAPic, saveTask;

    Uri FileUri;
    Bitmap bmp;
    static int count = 4;
    public static final int CAMERADATA = 1;


    public TaskFragment() {
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
        view = inflater.inflate(R.layout.fragment_task, container, false);
        init();
        return view;
    }

    private void init() {
        task = (TextView)view.findViewById(R.id.taskTextView);
        maintenanceType = (Spinner) view.findViewById(R.id.maintenanceSpinner);
        genHours = (LinearLayout)view.findViewById(R.id.genRunHrsLinearLayout);
        battVolt = (LinearLayout)view.findViewById(R.id.batteryVoltLinearLayout);
        inventory = (EditText)view.findViewById(R.id.inventoryEditText);
        genHrs = (EditText)view.findViewById(R.id.genRunHrsEditText);
        batVolt = (EditText)view.findViewById(R.id.batteryVoltEditText);

        equipment = (AutoCompleteTextView) view.findViewById(R.id.equipmentAutoCompleteTextView);
        MaintenanceAppDB sqlitedb = new MaintenanceAppDB(this.getActivity());
        sqlitedb.openForRead();

        String[] items = sqlitedb.getAllItemNames();

        for(int i = 0; i < items.length; i++)
        {
            Log.i(this.toString(), items[i]);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_dropdown_item_1line, items);
        equipment.setAdapter(adapter);
        equipment.setThreshold(1);

        quantity = (EditText)view.findViewById(R.id.quantityEditText);
        remarks = (EditText)view.findViewById(R.id.remarksEditText);

        bPic1 = (ImageView)view.findViewById(R.id.Img1);
        bPic2 = (ImageView)view.findViewById(R.id.Img2);
        bPic3 = (ImageView)view.findViewById(R.id.Img3);

        aPic1 = (ImageView)view.findViewById(R.id.Img4);
        aPic2 = (ImageView)view.findViewById(R.id.Img5);
        aPic3 = (ImageView)view.findViewById(R.id.Img6);

        saveTask = (Button)view.findViewById(R.id.save_task);
        saveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_insertToTask_db();
                Toast.makeText(getActivity(), "Item Added", Toast.LENGTH_SHORT).show();
                inventory.setText("");
                genHrs.setText("");
                batVolt.setText("");
                equipment.setText("");
                quantity.setText("");
                remarks.setText("");
            }
        });

        captureAPic = (Button)view.findViewById(R.id.takePhotoButton);
        captureAPic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (count <= 6) {
                    Intent m = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    String imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                            BaseDataFragment.sysaid.getText().toString() + BaseDataFragment.taskSpinner.getSelectedItem().toString() + count + ".jpg";
                    File _file = new File(imageFilePath);
                    FileUri = Uri.fromFile(_file);//it was declared globally
                    m.putExtra(MediaStore.EXTRA_OUTPUT, FileUri);
                    startActivityForResult(m, CAMERADATA);
                } else {

                    captureAPic.setEnabled(false);
                }
            }
        });

    }


    public void main_insertToTask_db(){
        //initTaskFragVar();
        String sql_sysaid = BaseDataFragment.sysaid.getText().toString();
        String sql_inventory = inventory.getText().toString();
        String sql_genHour = genHrs.getText().toString();
        String sql_battVolt = batVolt.getText().toString();
        String sql_maintenanceType = maintenanceType.getSelectedItem().toString();
        String sql_equipment = equipment.getText().toString();
        String sql_quantity = quantity.getText().toString();
        String sql_remarks = remarks.getText().toString();

        MaintenanceAppDB task_record = new MaintenanceAppDB(this.getActivity());
        task_record.openForRead();
        task_record.createTaskRecord(sql_sysaid, sql_inventory, sql_genHour, sql_battVolt,
                sql_maintenanceType, sql_equipment, sql_quantity, sql_remarks);
        task_record.close();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == CAMERADATA) {
            switch (count) {
                case 4:
                    if (resultCode == getActivity().RESULT_OK) {
                       /* Bundle extra = data.getExtras();
                        bmp1 = (Bitmap) extra.get("data");
                        instImg1.setImageBitmap(bmp1);*/
                        Uri uri = FileUri;
                        bmp = BitmapFactory.decodeFile(uri.getPath());
                        aPic1.setImageBitmap(bmp);
                        count++;
                    }
                    break;

                case 5:
                    if (resultCode == getActivity().RESULT_OK) {
                        /*Bundle extra = data.getExtras();
                        bmp1 = (Bitmap) extra.get("data");*/
                        Uri uri = FileUri;
                        bmp = BitmapFactory.decodeFile(uri.getPath());
                        aPic2.setImageBitmap(bmp);
                        count++;
                    }
                    break;

                case 6:
                    if (resultCode == getActivity().RESULT_OK) {
                       /* Bundle extra = data.getExtras();
                        bmp1 = (Bitmap) extra.get("data");*/
                        Uri uri = FileUri;
                        bmp = BitmapFactory.decodeFile(uri.getPath());
                        aPic3.setImageBitmap(bmp);
                        count++;
                    }
                    break;
            }

        }
    }

}
