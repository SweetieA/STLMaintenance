package com.application.sweetiean.stlmaintenance;


import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.application.sweetiean.stlservicing.Serv_BaseDataFragment;

import java.io.File;
import java.io.FileOutputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class ImageSignFragment extends Fragment {

    View view, sView, cView;
    LinearLayout cContent, sContent;
    signature_client cSignature;
    signature_stl sSignature;
    Button cClear, cSign, sClear, sSign;
    EditText clientName, clientPost;
    EditText stlName, stlPost;

    public static String tempDir;
    public int count = 1;
    public String client_current = null;
    public String stl_current = null;
    private Bitmap mBitmap;
    File stl_path;
    File client_path;

    private String uniqueId;
    public static String client_rep_name, client_rep_post, client_rep_sign;
    public static String stl_rep_name, stl_rep_post, stl_rep_sign;



    public ImageSignFragment() {
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
        view = inflater.inflate(R.layout.fragment_image_sign, container, false);
        init();
        return view;
    }


    

    private void init() {

        tempDir = Environment.getExternalStorageDirectory() + "/" + getResources().getString(R.string.external_dir) + "/";
        ContextWrapper cw = new ContextWrapper(getActivity());
        File directory = cw.getDir(getResources().getString(R.string.external_dir), Context.MODE_PRIVATE);

        prepareDirectory();
        uniqueId = Utility.getTodaysDate() + "_" + Utility.getCurrentTime() + "_" + Math.random();
        stl_current = "STL" + uniqueId + ".png";
        client_current = "Client" + uniqueId + ".png";

        client_path = new File(tempDir, client_current);
        stl_path = new File(tempDir, stl_current);

        initClient();
        initStl();


    }

    private void initClient(){
        cContent = (LinearLayout) view.findViewById(R.id.clientSign);
        cSignature = new signature_client(this.getActivity(), null);
        cSignature.setBackgroundColor(Color.WHITE);
        cContent.addView(cSignature, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        cClear = (Button) view.findViewById(R.id.cClear);
        cSign = (Button) view.findViewById(R.id.cSign);
        cSign.setEnabled(false);
        cView = cContent;

        clientName = (EditText) view.findViewById(R.id.clientNameEditText);
        clientPost = (EditText) view.findViewById(R.id.ClientPostEditText);

        cClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("log_tag", "Panel Cleared");
                cSignature.clear();
                cSign.setEnabled(false);
            }
        });

        cSign.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("log_tag", "Panel Saved");
                boolean error = captureClientSignature();
                if (!error) {
                    cView.setDrawingCacheEnabled(true);
                    cSignature.saveClient(cView);
                    client_rep_name = clientName.getText().toString();
                    client_rep_post = clientPost.getText().toString();
                    client_rep_sign = client_current;//REPLACE THIS WITH ACTUAL FILE NAME
                    Toast toast = Toast.makeText(getActivity(), "Client signature captured successfully", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 150, 20);
                    toast.show();
                }
                else
                {
                    captureClientSignature();
                }

            }
        });
    }

    private void initStl(){
        sContent = (LinearLayout) view.findViewById(R.id.STLSign);
        sSignature = new signature_stl(this.getActivity(), null);
        sSignature.setBackgroundColor(Color.WHITE);
        sContent.addView(sSignature, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        sClear = (Button) view.findViewById(R.id.mClear);
        sSign = (Button) view.findViewById(R.id.mSign);
        sSign.setEnabled(false);
        sView = sContent;

        stlName = (EditText) view.findViewById(R.id.STLNameEditText);
        stlPost = (EditText) view.findViewById(R.id.STLPostEditText);

        sClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("log_tag", "Panel Cleared");
                sSignature.clear();
                sSign.setEnabled(false);
            }
        });

        sSign.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("log_tag", "Panel Saved");
                boolean error = captureSTLSignature();
                if (!error) {
                    sView.setDrawingCacheEnabled(true);
                    sSignature.saveSTL(sView);
                    stl_rep_name = stlName.getText().toString();
                    stl_rep_post = stlPost.getText().toString();
                    stl_rep_sign = stl_current;//REPLACE THIS WITH ACTUAL FILE NAME
                    Toast toast = Toast.makeText(getActivity(), "STL signature captured successfully", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 150, 20);
                    toast.show();
                }
                else
                {
                    captureSTLSignature();
                }
            }
        });
    }

    public void onDestroy() {
        Log.w("GetSignature", "onDestory");
        super.onDestroy();
    }

    private boolean captureSTLSignature() {

        boolean error = false;
        String errorMessage = "";


        if(stlName.getText().toString().equalsIgnoreCase("")){
            errorMessage = errorMessage + "Please enter STL Rep Name\n";
            error = true;
        }

        if(error){
            Toast toast = Toast.makeText(this.getActivity(), errorMessage, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 105, 20);
            toast.show();
        }

        return error;
    }

    private boolean captureClientSignature() {

        boolean error = false;
        String errorMessage = "";


        if(clientName.getText().toString().equalsIgnoreCase("")){
            errorMessage = errorMessage + "Please enter Client Rep Name\n";
            error = true;
        }

        if(error){
            Toast toast = Toast.makeText(this.getActivity(), errorMessage, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL, 105, 20);
            toast.show();
        }

        return error;
    }

    private boolean prepareDirectory()
    {
        try
        {
            if (makedirs())
            {
                return true;
            } else {
                return false;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(this.getActivity(), "Could not initiate File System.. Is Sdcard mounted properly?", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private boolean makedirs()
    {
        File tempdir = new File(tempDir);
        if (!tempdir.exists())
            tempdir.mkdirs();

        if (tempdir.isDirectory())
        {
            File[] files = tempdir.listFiles();
            for (File file : files)
            {
                if (!file.delete())
                {
                    System.out.println("Failed to delete " + file);
                }
            }
        }
        return (tempdir.isDirectory());
    }

    public class signature_client extends View
    {
        private static final float STROKE_WIDTH = 5f;
        private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
        private Paint paint = new Paint();
        private Path path = new Path();

        private float lastTouchX;
        private float lastTouchY;
        private final RectF dirtyRect = new RectF();

        public signature_client(Context context, AttributeSet attrs)
        {
            super(context, attrs);
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(STROKE_WIDTH);
        }


        public void saveClient(View v)
        {
            //TODO CAPTURE IMAGE FILE NAME
            Log.v("log_tag", "Width: " + v.getWidth());
            Log.v("log_tag", "Height: " + v.getHeight());
            if(mBitmap == null)
            {
                mBitmap =  Bitmap.createBitmap (cContent.getWidth(), cContent.getHeight(), Bitmap.Config.RGB_565);;
            }
            Canvas canvas = new Canvas(mBitmap);
            try
            {
                FileOutputStream mFileOutStream = new FileOutputStream(client_path);

                v.draw(canvas);
                mBitmap.compress(Bitmap.CompressFormat.PNG, 90, mFileOutStream);
                mFileOutStream.flush();
                mFileOutStream.close();
                String url = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), mBitmap, "title", null);
                Log.v("log_tag","url: " + url);
                //In case you want to delete the file
                //boolean deleted = mypath.delete();
                //Log.v("log_tag","deleted: " + mypath.toString() + deleted);
                //If you want to convert the image to string use base64 converter

            }
            catch(Exception e)
            {
                Log.v("log_tag", e.toString());
            }
        }

        public void clear()
        {
            path.reset();
            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas)
        {
            canvas.drawPath(path, paint);
        }

        @Override
        public boolean onTouchEvent( MotionEvent event)
        {
            float eventX = event.getX();
            float eventY = event.getY();

            cSign.setEnabled(true);

            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(eventX, eventY);
                    lastTouchX = eventX;
                    lastTouchY = eventY;
                    return true;

                case MotionEvent.ACTION_MOVE:

                case MotionEvent.ACTION_UP:

                    resetDirtyRect(eventX, eventY);
                    int historySize = event.getHistorySize();
                    for (int i = 0; i < historySize; i++)
                    {
                        float historicalX = event.getHistoricalX(i);
                        float historicalY = event.getHistoricalY(i);
                        expandDirtyRect(historicalX, historicalY);
                        path.lineTo(historicalX, historicalY);
                    }
                    path.lineTo(eventX, eventY);
                    break;

                default:
                    debug("Ignored touch event: " + event.toString());
                    return false;
            }

            invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                    (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

            lastTouchX = eventX;
            lastTouchY = eventY;

            return true;
        }

        private void debug(String string){
        }

        private void expandDirtyRect(float historicalX, float historicalY)
        {
            if (historicalX < dirtyRect.left)
            {
                dirtyRect.left = historicalX;
            }
            else if (historicalX > dirtyRect.right)
            {
                dirtyRect.right = historicalX;
            }

            if (historicalY < dirtyRect.top)
            {
                dirtyRect.top = historicalY;
            }
            else if (historicalY > dirtyRect.bottom)
            {
                dirtyRect.bottom = historicalY;
            }
        }

        private void resetDirtyRect(float eventX, float eventY)
        {
            dirtyRect.left = Math.min(lastTouchX, eventX);
            dirtyRect.right = Math.max(lastTouchX, eventX);
            dirtyRect.top = Math.min(lastTouchY, eventY);
            dirtyRect.bottom = Math.max(lastTouchY, eventY);
        }
    }

    public class signature_stl extends View
    {
        private static final float STROKE_WIDTH = 5f;
        private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
        private Paint paint = new Paint();
        private Path path = new Path();

        private float lastTouchX;
        private float lastTouchY;
        private final RectF dirtyRect = new RectF();

        public signature_stl(Context context, AttributeSet attrs)
        {
            super(context, attrs);
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(STROKE_WIDTH);
        }

        public void saveSTL(View v)
        {
            //TODO CAPTURE IMAGE FILE NAME
            Log.v("log_tag", "Width: " + v.getWidth());
            Log.v("log_tag", "Height: " + v.getHeight());
            if(mBitmap == null)
            {
                mBitmap =  Bitmap.createBitmap (sContent.getWidth(), sContent.getHeight(), Bitmap.Config.RGB_565);;
            }
            Canvas canvas = new Canvas(mBitmap);
            try
            {
                FileOutputStream mFileOutStream = new FileOutputStream(stl_path);

                v.draw(canvas);
                mBitmap.compress(Bitmap.CompressFormat.PNG, 90, mFileOutStream);
                mFileOutStream.flush();
                mFileOutStream.close();
                String url = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), mBitmap, "title", null);
                Log.v("log_tag","url: " + url);
                //In case you want to delete the file
                //boolean deleted = mypath.delete();
                //Log.v("log_tag","deleted: " + mypath.toString() + deleted);
                //If you want to convert the image to string use base64 converter

            }
            catch(Exception e)
            {
                Log.v("log_tag", e.toString());
            }
        }


        public void clear()
        {
            path.reset();
            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas)
        {
            canvas.drawPath(path, paint);
        }

        @Override
        public boolean onTouchEvent( MotionEvent event)
        {
            float eventX = event.getX();
            float eventY = event.getY();

            sSign.setEnabled(true);

            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(eventX, eventY);
                    lastTouchX = eventX;
                    lastTouchY = eventY;
                    return true;

                case MotionEvent.ACTION_MOVE:

                case MotionEvent.ACTION_UP:

                    resetDirtyRect(eventX, eventY);
                    int historySize = event.getHistorySize();
                    for (int i = 0; i < historySize; i++)
                    {
                        float historicalX = event.getHistoricalX(i);
                        float historicalY = event.getHistoricalY(i);
                        expandDirtyRect(historicalX, historicalY);
                        path.lineTo(historicalX, historicalY);
                    }
                    path.lineTo(eventX, eventY);
                    break;

                default:
                    debug("Ignored touch event: " + event.toString());
                    return false;
            }

            invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                    (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

            lastTouchX = eventX;
            lastTouchY = eventY;

            return true;
        }

        private void debug(String string){
        }

        private void expandDirtyRect(float historicalX, float historicalY)
        {
            if (historicalX < dirtyRect.left)
            {
                dirtyRect.left = historicalX;
            }
            else if (historicalX > dirtyRect.right)
            {
                dirtyRect.right = historicalX;
            }

            if (historicalY < dirtyRect.top)
            {
                dirtyRect.top = historicalY;
            }
            else if (historicalY > dirtyRect.bottom)
            {
                dirtyRect.bottom = historicalY;
            }
        }

        private void resetDirtyRect(float eventX, float eventY)
        {
            dirtyRect.left = Math.min(lastTouchX, eventX);
            dirtyRect.right = Math.max(lastTouchX, eventX);
            dirtyRect.top = Math.min(lastTouchY, eventY);
            dirtyRect.bottom = Math.max(lastTouchY, eventY);
        }
    }

}
