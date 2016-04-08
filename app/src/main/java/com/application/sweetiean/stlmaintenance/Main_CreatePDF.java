package com.application.sweetiean.stlmaintenance;

import android.os.Environment;
import android.util.Log;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by sweetiean on 12/16/2015.
 */
public class Main_CreatePDF {
    public Main_CreatePDF(){}


    public Boolean writepdf_noGen(String sysaid,String itemsarray) {
        try {
            String fpath = Environment.getExternalStorageDirectory().getPath() + "/" + sysaid + ".pdf";
            File file = new File(fpath);
            // If file does not exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }



            // step 1
            Document document = new Document();
            // step 2
            PdfWriter.getInstance(document, new FileOutputStream(file.getAbsoluteFile()));

            Font font1 = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.NORMAL);
            Font font2 = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLDITALIC|Font.UNDERLINE);

            // step 3
            document.open();

            // step 4
            Paragraph paragraph1 = new Paragraph("MAINTENANCE REPORT", font2);
            paragraph1.setSpacingBefore(25);
            paragraph1.setAlignment(Element.ALIGN_CENTER);
            paragraph1.setIndentationLeft(50);
            paragraph1.setIndentationRight(50);

            Paragraph paragraph2 = new Paragraph(Utility.getTodaysDate(), font2);
            paragraph2.setSpacingAfter(25);
            paragraph2.setAlignment(Element.ALIGN_CENTER);
            paragraph2.setIndentationLeft(50);
            paragraph2.setIndentationRight(50);

            Paragraph paragraph3 = new Paragraph("TASK", font2);
            paragraph3.setAlignment(Element.ALIGN_CENTER);
            paragraph3.setIndentationLeft(50);
            paragraph3.setIndentationRight(50);

            document.add(paragraph1);
            document.add(paragraph2);
            document.add(new Paragraph("Customer: " + MaintenanceActivity.customer, font1));
            document.add(new Paragraph("Task Type: " + MaintenanceActivity.taskType, font1));
            document.add(new Paragraph("Sysaid ID: " + MaintenanceActivity.sysaid, font1));
            document.add(new Paragraph("Address: " + MaintenanceActivity.address, font1));
            document.add(new Paragraph("Region: " + MaintenanceActivity.region, font1));
            document.add(new Paragraph("Phone: " + MaintenanceActivity.phone, font1));
            document.add(new Paragraph("E-mail: " + MaintenanceActivity.email, font1));
            document.add(new Paragraph("Location Coordinates: " + MaintenanceActivity.locationCoordinates, font1));

            document.add(new Paragraph(" "));
            document.add(paragraph3);

            document.add(new Paragraph(itemsarray, font1));


            if(BaseDataFragment.count > 1 && TaskFragment.count > 4) {
                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                        MaintenanceActivity.sysaid + MaintenanceActivity.taskType + 1 + ".jpg";

                File img_file = new File(filePath);
                if (img_file.exists()) {
                    Image img = Image.getInstance(filePath);
                    img.scaleToFit(150, 150);
                    document.add(img);
                }else{
                    document.add(new Paragraph("NO IMAGE", font1));
                }


                String filePath1 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                        MaintenanceActivity.sysaid + MaintenanceActivity.taskType + 2 + ".jpg";

                File img_file1 = new File(filePath1);
                if (img_file1.exists()) {
                    Image img1 = Image.getInstance(filePath1);
                    img1.scaleToFit(150, 150);
                    document.add(img1);
                }else{
                    document.add(new Paragraph("NO IMAGE", font1));
                }


                String filePath2 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                        MaintenanceActivity.sysaid + MaintenanceActivity.taskType + 3 + ".jpg";

                File img_file2 = new File(filePath2);
                if (img_file2.exists()) {
                    Image img2 = Image.getInstance(filePath2);
                    img2.scaleToFit(150, 150);
                    document.add(img2);
                }else{
                    document.add(new Paragraph("IMAGE 3 NOT AVAILABLE", font1));
                }


                String filePath3 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                        MaintenanceActivity.sysaid + MaintenanceActivity.taskType + 4 + ".jpg";

                File img_file3 = new File(filePath3);
                if (img_file3.exists()) {
                    Image img3 = Image.getInstance(filePath3);
                    img3.scaleToFit(150, 150);
                    document.add(img3);
                }else{
                    document.add(new Paragraph("NO IMAGE", font1));
                }


                String filePath4 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                        MaintenanceActivity.sysaid + MaintenanceActivity.taskType + 5 + ".jpg";

                File img_file4 = new File(filePath4);
                if (img_file4.exists()) {
                    Image img4 = Image.getInstance(filePath4);
                    img4.scaleToFit(150, 150);
                    document.add(img4);
                }else{
                    document.add(new Paragraph("NO IMAGE", font1));
                }


                String filePath5 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                        MaintenanceActivity.sysaid + MaintenanceActivity.taskType + 6 + ".jpg";

                File img_file5 = new File(filePath5);
                if (img_file5.exists()) {
                    Image img5 = Image.getInstance(filePath5);
                    img5.scaleToFit(150, 150);
                    document.add(img5);
                }else{
                    document.add(new Paragraph("IMAGE 6 NOT AVAILABLE", font1));
                }

            }



            document.add(new Paragraph(" "));
            document.add(new Paragraph("STL Rep Name: " + MaintenanceActivity.stl_rep_name, font1));
            document.add(new Paragraph("STL Rep Position: " + MaintenanceActivity.stl_rep_post, font1));
            document.add(new Paragraph("STL Rep Signature: ", font1));
            String stl_filePathSign = ImageSignFragment.tempDir +
                    ImageSignFragment.stl_rep_sign;
            Image stl_imgSign = Image.getInstance(stl_filePathSign);
            stl_imgSign.scaleToFit(150, 150);
            document.add(stl_imgSign);

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Client Rep Name: " + MaintenanceActivity.client_rep_name, font1));
            document.add(new Paragraph("Client Rep Position: " + MaintenanceActivity.client_rep_post, font1));
            document.add(new Paragraph("Client Rep Signature: ", font1));
            String client_filePathSign = ImageSignFragment.tempDir +
                    ImageSignFragment.client_rep_sign;
            Image client_imgSign = Image.getInstance(client_filePathSign);
            client_imgSign.scaleToFit(150, 150);
            document.add(client_imgSign);

            // step 5
            document.close();

            Log.d("Success", "Success");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }


    public Boolean writepdf_gen(String sysaid, String itemsArray) {
        try {
            String fpath = Environment.getExternalStorageDirectory().getPath() + "/" + sysaid + ".pdf";
            File file = new File(fpath);
            // If file does not exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }


            // step 1
            Document document = new Document();
            // step 2
            PdfWriter.getInstance(document, new FileOutputStream(file.getAbsoluteFile()));

            Font font1 = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL);
            Font font2 = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLDITALIC|Font.UNDERLINE);

            // step 3
            document.open();


            // step 4
            Paragraph paragraph1 = new Paragraph("MAINTENANCE REPORT", font2);
            paragraph1.setSpacingBefore(25);
            paragraph1.setAlignment(Element.ALIGN_CENTER);
            paragraph1.setIndentationLeft(50);
            paragraph1.setIndentationRight(50);

            Paragraph paragraph2 = new Paragraph(Utility.getTodaysDate(), font2);
            paragraph2.setSpacingAfter(25);
            paragraph2.setAlignment(Element.ALIGN_CENTER);
            paragraph2.setIndentationLeft(50);
            paragraph2.setIndentationRight(50);

            Paragraph paragraph3 = new Paragraph("TASK", font2);
            paragraph3.setAlignment(Element.ALIGN_CENTER);
            paragraph3.setIndentationLeft(50);
            paragraph3.setIndentationRight(50);

            document.add(paragraph1);
            document.add(paragraph2);
            document.add(new Paragraph("Customer: " + MaintenanceActivity.customer, font1));
            document.add(new Paragraph("Task Type: " + MaintenanceActivity.taskType, font1));
            document.add(new Paragraph("Sysaid ID: " + MaintenanceActivity.sysaid, font1));
            document.add(new Paragraph("Address: " + MaintenanceActivity.address, font1));
            document.add(new Paragraph("Region: " + MaintenanceActivity.region, font1));
            document.add(new Paragraph("Phone: " + MaintenanceActivity.phone, font1));
            document.add(new Paragraph("E-mail: " + MaintenanceActivity.email, font1));
            document.add(new Paragraph("Location Coordinates: " + MaintenanceActivity.locationCoordinates, font1));

            document.add(new Paragraph(" "));
            document.add(paragraph3);


            document.add(new Paragraph(itemsArray, font1));

            //adding images
            if(BaseDataFragment.count > 1 && TaskFragment.count > 4) {
                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                        MaintenanceActivity.sysaid + MaintenanceActivity.taskType + 1 + ".jpg";

                File img_file = new File(filePath);
                if (img_file.exists()) {
                    Image img = Image.getInstance(filePath);
                    img.scaleToFit(150, 150);
                    document.add(img);
                }else{
                    document.add(new Paragraph("NO IMAGE", font1));
                }


                String filePath1 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                        MaintenanceActivity.sysaid + MaintenanceActivity.taskType + 2 + ".jpg";

                File img_file1 = new File(filePath1);
                if (img_file1.exists()) {
                    Image img1 = Image.getInstance(filePath1);
                    img1.scaleToFit(150, 150);
                    document.add(img1);
                }else{
                    document.add(new Paragraph("NO IMAGE", font1));
                }


                String filePath2 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                        MaintenanceActivity.sysaid + MaintenanceActivity.taskType + 3 + ".jpg";

                File img_file2 = new File(filePath2);
                if (img_file2.exists()) {
                    Image img2 = Image.getInstance(filePath2);
                    img2.scaleToFit(150, 150);
                    document.add(img2);
                }else{
                    document.add(new Paragraph("IMAGE 3 NOT AVAILABLE", font1));
                }


                String filePath3 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                        MaintenanceActivity.sysaid + MaintenanceActivity.taskType + 4 + ".jpg";

                File img_file3 = new File(filePath3);
                if (img_file3.exists()) {
                    Image img3 = Image.getInstance(filePath3);
                    img3.scaleToFit(150, 150);
                    document.add(img3);
                }else{
                    document.add(new Paragraph("NO IMAGE", font1));
                }


                String filePath4 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                        MaintenanceActivity.sysaid + MaintenanceActivity.taskType + 5 + ".jpg";

                File img_file4 = new File(filePath4);
                if (img_file4.exists()) {
                    Image img4 = Image.getInstance(filePath4);
                    img4.scaleToFit(150, 150);
                    document.add(img4);
                }else{
                    document.add(new Paragraph("NO IMAGE", font1));
                }


                String filePath5 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                        MaintenanceActivity.sysaid + MaintenanceActivity.taskType + 6 + ".jpg";

                File img_file5 = new File(filePath5);
                if (img_file5.exists()) {
                    Image img5 = Image.getInstance(filePath5);
                    img5.scaleToFit(150, 150);
                    document.add(img5);
                }else{
                    document.add(new Paragraph("IMAGE 6 NOT AVAILABLE", font1));
                }

            }


            document.add(new Paragraph(" "));
            document.add(new Paragraph("STL Rep Name: " + MaintenanceActivity.stl_rep_name, font1));
            document.add(new Paragraph("STL Rep Position: " + MaintenanceActivity.stl_rep_post, font1));
            document.add(new Paragraph("STL Rep Signature: ", font1));
            String stl_filePathSign = ImageSignFragment.tempDir +
                    ImageSignFragment.stl_rep_sign;
            Image stl_imgSign = Image.getInstance(stl_filePathSign);
            stl_imgSign.scaleToFit(150, 150);
            document.add(stl_imgSign);

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Client Rep Name: " + MaintenanceActivity.client_rep_name, font1));
            document.add(new Paragraph("Client Rep Position: " + MaintenanceActivity.client_rep_post, font1));
            document.add(new Paragraph("Client Rep Signature: ", font1));
            String client_filePathSign = ImageSignFragment.tempDir +
                    ImageSignFragment.client_rep_sign;
            Image client_imgSign = Image.getInstance(client_filePathSign);
            client_imgSign.scaleToFit(150, 150);
            document.add(client_imgSign);


            // step 5
            document.close();

            Log.d("Success", "Success");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

}
