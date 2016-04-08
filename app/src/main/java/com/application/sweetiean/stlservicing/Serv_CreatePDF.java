package com.application.sweetiean.stlservicing;

import android.os.Environment;
import android.util.Log;

import com.application.sweetiean.stlmaintenance.Utility;
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
 * Created by sweetiean on 12/1/2015.
 */
public class Serv_CreatePDF {
    public Serv_CreatePDF(){}

    public Boolean writepdf(String sysaid) {
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
            PdfWriter.getInstance(document,new FileOutputStream(file.getAbsoluteFile()));

            Font font1 = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.NORMAL);
            Font font2 = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLDITALIC|Font.UNDERLINE);

            // step 3
            document.open();

            // step 4
            Paragraph paragraph1 = new Paragraph("SERVICE REPORT", font2);
            paragraph1.setSpacingBefore(25);
            paragraph1.setAlignment(Element.ALIGN_CENTER);
            paragraph1.setIndentationLeft(50);
            paragraph1.setIndentationRight(50);

            Paragraph paragraph2 = new Paragraph(Utility.getTodaysDate(), font2);
            paragraph2.setSpacingAfter(25);
            paragraph2.setAlignment(Element.ALIGN_CENTER);
            paragraph2.setIndentationLeft(50);
            paragraph2.setIndentationRight(50);

            Paragraph paragraph3 = new Paragraph("SERVICING", font2);
            paragraph1.setSpacingBefore(25);
            paragraph1.setAlignment(Element.ALIGN_CENTER);
            paragraph1.setIndentationLeft(50);
            paragraph1.setIndentationRight(50);

            Paragraph paragraph4 = new Paragraph("REPLACEMENT", font2);
            paragraph1.setSpacingBefore(25);
            paragraph1.setAlignment(Element.ALIGN_CENTER);
            paragraph1.setIndentationLeft(50);
            paragraph1.setIndentationRight(50);

            document.add(paragraph1);
            document.add(paragraph2);
            document.add(new Paragraph("Sysaid ID: " + ServicingActivity.sysaid, font1));
            document.add(new Paragraph("Task Type: " + ServicingActivity.taskType, font1));
            document.add(new Paragraph("Customer: " + ServicingActivity.customer, font1));
            document.add(new Paragraph("Site ID: " + ServicingActivity.siteId, font1));
            document.add(new Paragraph("Address: " + ServicingActivity.address, font1));
            document.add(new Paragraph("Region: " + ServicingActivity.region, font1));
            document.add(new Paragraph("Location Coordinates: " + ServicingActivity.locationCoordinates, font1));

            document.add(new Paragraph(" "));
            document.add(paragraph3);

            document.add(new Paragraph("DVR Firmware Update: " + ServicingActivity.dvrFirmware, font1));
            document.add(new Paragraph("Firmware Version: " + ServicingActivity.firmwareVersion, font1));
            document.add(new Paragraph("Workstation: " + ServicingActivity.serv_workstation, font1));
            document.add(new Paragraph("Test Computer Performance: " + ServicingActivity.testCP, font1));
            document.add(new Paragraph("Clean Computer: " + ServicingActivity.cleanPC, font1));
            document.add(new Paragraph("Serial Number: " + ServicingActivity.serv_workstationSerial, font1));
            document.add(new Paragraph("Remarks: " + ServicingActivity.remarks, font1));
            document.add(new Paragraph("DVR: " + ServicingActivity.serv_dvr, font1));
            document.add(new Paragraph("Serial Number: " + ServicingActivity.serv_dvrSerial, font1));
            document.add(new Paragraph("Service and Clean Cameras: " + ServicingActivity.cleanCam, font1));
            document.add(new Paragraph("Check UPS Backup Status: " + ServicingActivity.serv_checkUPS, font1));
            document.add(new Paragraph("UPS: " + ServicingActivity.serv_UPS, font1));
            document.add(new Paragraph("Serial Number: " + ServicingActivity.serv_UPSSerial, font1));
            document.add(new Paragraph("Status: " + ServicingActivity.status, font1));

            document.add(new Paragraph(" "));
            document.add(paragraph4);

            document.add(new Paragraph("UPS: " + ServicingActivity.repl_checkUPS, font1));
            document.add(new Paragraph("UPS Type: " + ServicingActivity.repl_UPS, font1));
            document.add(new Paragraph("Serial Number: " + ServicingActivity.repl_UPSSerial, font1));
            document.add(new Paragraph("Workstation: " + ServicingActivity.repl_workstation, font1));
            document.add(new Paragraph("Serial Number: " + ServicingActivity.repl_workstationSerial, font1));
            document.add(new Paragraph("Hard Disk Drive: " + ServicingActivity.hdd, font1));
            document.add(new Paragraph("500GB: " + ServicingActivity._500GB, font1));
            document.add(new Paragraph("Serial Number: " + ServicingActivity.hdd500Serial, font1));
            document.add(new Paragraph("1TB: " + ServicingActivity._1TB, font1));
            document.add(new Paragraph("Serial Number: " + ServicingActivity.hdd1TBSerial, font1));
            document.add(new Paragraph("2TB: " + ServicingActivity._2TB, font1));
            document.add(new Paragraph("Serial Number: " + ServicingActivity.hdd2TBSerial, font1));
            document.add(new Paragraph("DVR: " + ServicingActivity.repl_dvr, font1));
            document.add(new Paragraph("DVR Type: " + ServicingActivity.dvrType, font1));
            document.add(new Paragraph("Serial Number: " + ServicingActivity.repl_dvrSerial, font1));
            document.add(new Paragraph("Cameras: " + ServicingActivity.cameras, font1));
            document.add(new Paragraph("Fix Box: " + ServicingActivity.fixBox, font1));
            document.add(new Paragraph("Quantity: " + ServicingActivity.fixBoxQty, font1));
            document.add(new Paragraph("Serial Number: " + ServicingActivity.fixBoxSerial, font1));
            document.add(new Paragraph("Dome IR: " + ServicingActivity.domeIR, font1));
            document.add(new Paragraph("Quantity: " + ServicingActivity.domeQty, font1));
            document.add(new Paragraph("Serial Number: " + ServicingActivity.domeSerial, font1));
            document.add(new Paragraph("Bullet IR: " + ServicingActivity.bulletIR, font1));
            document.add(new Paragraph("Quantity: " + ServicingActivity.bulletQty, font1));
            document.add(new Paragraph("Serial Number: " + ServicingActivity.bulletSerial, font1));
            document.add(new Paragraph("Housing: " + ServicingActivity.housing, font1));
            document.add(new Paragraph("Quantity: " + ServicingActivity.housingQty, font1));
            document.add(new Paragraph("Serial Number: " + ServicingActivity.housingSerial, font1));
            document.add(new Paragraph("Power Supply: " + ServicingActivity.powerSupply, font1));
            document.add(new Paragraph("12V DC Power Supply: " + ServicingActivity._12vPwrSupply, font1));
            document.add(new Paragraph("12V DC Power Box: " + ServicingActivity._12vPwrBox, font1));
            document.add(new Paragraph("Monitor: " + ServicingActivity.monitor, font1));
            document.add(new Paragraph("Serial Number: " + ServicingActivity.monitorSerial, font1));
            document.add(new Paragraph("Other Issues: " + ServicingActivity.otherIssues, font1));
            document.add(new Paragraph("Issues: " + ServicingActivity.issues, font1));

            document.add(new Paragraph(" "));
            document.add(new Paragraph("STL Rep Name: " + ServicingActivity.stl_rep_name, font1));
            document.add(new Paragraph("STL Rep Position: " + ServicingActivity.stl_rep_post, font1));
            document.add(new Paragraph("STL Rep Signature: ", font1));
            String stl_filePathSign = STLSignatureActivity.tempDir +
                    Serv_BaseDataFragment.stl_rep_sign;
            Image stl_imgSign = Image.getInstance(stl_filePathSign);
            stl_imgSign.scaleToFit(150, 150);
            document.add(stl_imgSign);

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Client Rep Name: " + ServicingActivity.client_rep_name, font1));
            document.add(new Paragraph("Client Rep Position: " + ServicingActivity.client_rep_post, font1));
            document.add(new Paragraph("Client Rep Signature: ", font1));
            String client_filePathSign = ClientSignatureActivity.tempDir +
                    Serv_BaseDataFragment.client_rep_sign;
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
