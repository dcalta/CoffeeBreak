package com.example.drewcaltagirone.coffeebreak;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.EditText;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Writer;
import java.util.ArrayList;

import java.io.InputStream;
import java.sql.Time;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;


public class NewEvent extends AppCompatActivity {
    private final String TAG = "TESTGPS";
    public JSONObject jo = null;
    public JSONArray ja = null;
    LocationManager l;
    LocationListener li;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Start up the Location Service
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);


        final EditText first = findViewById(R.id.titleInput);
        final EditText second = findViewById(R.id.timeInput);
        final EditText third = findViewById(R.id.titleInput);
        final EditText fourth = findViewById(R.id.titleInput);
        final EditText fifth = findViewById(R.id.timeInput);

        Button b = findViewById(R.id.enterBut);

        // Read the file


        try {
            File f = new File(getFilesDir(), "file.ser");
            FileInputStream fi = new FileInputStream(f);
            ObjectInputStream o = new ObjectInputStream(fi);
            // Notice here that we are de-serializing a String object (instead of
            // a JSONObject object) and passing the String to the JSONObject’s
            // constructor. That’s because String is serializable and
            // JSONObject is not. To convert a JSONObject back to a String, simply
            // call the JSONObject’s toString method.
            String j = null;
            try {
                j = (String) o.readObject();
            } catch (ClassNotFoundException c) {
                c.printStackTrace();
            }
            try {
                jo = new JSONObject(j);
                ja = jo.getJSONArray("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            // Here, initialize a new JSONObject
            jo = new JSONObject();
            ja = new JSONArray();
            try {
                jo.put("data", ja);
            } catch (JSONException j) {
                j.printStackTrace();
            }
        }
        Intent i = getIntent();
//        Log.d("Check", hostText);
        final String hostText = i.getStringExtra("host");
        //Log.d("Check", hostText);

        b.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                String firstText = first.getText().toString();
                String secondText = second.getText().toString();
                String thirdText = third.getText().toString();
                String fourthText = fourth.getText().toString();
                String fifthText = fifth.getText().toString();



                JSONObject temp = new JSONObject();
                try {
                    temp.put("title", firstText);
                    temp.put("time", secondText);
                    temp.put("date", thirdText);
                    temp.put("location", fourthText);
                    temp.put("description", fifthText);
                    temp.put("host", hostText);

                } catch (JSONException j) {
                    j.printStackTrace();
                }

                ja.put(temp);

                // write the file
                try {
                    File f = new File(getFilesDir(), "file.ser");
                    FileOutputStream fo = new FileOutputStream(f);
                    ObjectOutputStream o = new ObjectOutputStream(fo);
                    String j = jo.toString();
                    o.writeObject(j);
                    o.close();
                    fo.close();
                } catch (IOException e) {

                }

                //pop the activity off the stack
                Intent i = new Intent(NewEvent.this, EventList.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

    }
}

