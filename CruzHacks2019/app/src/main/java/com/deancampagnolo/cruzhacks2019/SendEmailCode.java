package com.deancampagnolo.cruzhacks2019;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Toast;
import android.widget.TextView;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import android.util.Log;

import com.sun.mail.smtp.SMTPTransport;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.activation.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.PasswordAuthentication;
import javax.activation.*;

import java.net.*;

public class SendEmailCode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email_code);
    }

    //can't run network stuff on main thread so do this
    private class DoEmail extends AsyncTask<String, Object, Object>{
        protected Void doInBackground(String... params){
            sendEmail(params[0], params[1]);
            return null;
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(Object... result) {
        }
    }

    public void onButtonClicked(View v){
        switch(v.getId()){
            case R.id.Submit:
                TextView emailBox = (TextView)findViewById(R.id.EmailAddress);
                TextView msgBox = (TextView)findViewById(R.id.SecretMessage);

                String message = msgBox.getText().toString(); //THIS IS THE MESSAGE IN THE QR
                String toAddress = emailBox.getText().toString();

                //send to db

                message = removeSpaces(message);

                int[] size = {400, 400};
                String qURL = getQRURLString(message, size);

                //if you want the png directly, use this
                //BufferedImage img = getQRPNG(message, size);

                //sends email on another thread
                new DoEmail().execute(toAddress, qURL);

                Toast.makeText(this, "Email Sent!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //returns png of message encoded as png w/ dimensions size[0]xsize[1]
    public BufferedImage getQRPNG(String message, int[] size) {
        String qrURLString = getQRURLString(message, size);
        URL qrURL = null;

        try {
            qrURL = new URL(qrURLString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            return ImageIO.read(qrURL);
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    //formatted get request that returns the address to get a qr png
    public String getQRURLString(String message, int[] size) {
        return "https://api.qrserver.com/v1/create-qr-code/?data=" + message + "&size=" + size[0] + "x" + size[1];
    }

    //https://javapapers.com/android/android-email-app-with-gmail-smtp-using-javamail/
    public void sendEmail(String toAddress, String imgURL){
        try {
            String fromEmail = "projectlandmarkcruzhacks2019";
            String fromPass = "ADJJpassword!";

            Properties props = System.getProperties();
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getDefaultInstance(props, null);

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail, fromEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
            message.setSubject("Your ProjectLandmark QR Code");
            message.setContent("<h4>Here is your QR code: </h4><br><img src=" + imgURL + ">", "text/html");

            Transport trans = session.getTransport("smtp");
            trans.connect("smtp.gmail.com", fromEmail + "@gmail.com", fromPass);
            trans.sendMessage(message, message.getAllRecipients());
            trans.close();

        } catch (Exception e){
            //Toast.makeText(this, "Error Sending Email", Toast.LENGTH_SHORT).show();
            Log.e("AaronTag", e.getMessage());
            Log.e("AaronTag", "oh no");
        }
    }

    public String removeSpaces(String s){
        s = s.trim();

        if (s == "" || s == null){
            return "empty_message";
        }

        int r = s.indexOf(" ");
        while (r != -1) {
            if (r != -1) {
                s = s.substring(0, r) + "_" + s.substring(r+1);
            }
            r = s.indexOf(" ");
        }

        return s;
    }
}
