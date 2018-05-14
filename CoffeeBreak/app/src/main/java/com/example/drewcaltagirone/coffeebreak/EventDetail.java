package com.example.drewcaltagirone.coffeebreak;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import java.io.InputStream;
import java.sql.Time;
import java.util.Date;

public class EventDetail extends AppCompatActivity {
    public JSONObject jos = null;
    public JSONArray ja = null;
    public JSONObject jos2 = null;
    public JSONArray ja2 = null;
    public JSONArray newArr = null;
    public JSONArray list = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        Button deleteButton = findViewById(R.id.delBut);
        Button goingButton = findViewById(R.id.addBut);

        Intent i = getIntent();
        final String title = i.getStringExtra("title");
        final String time = i.getStringExtra("time");
        final String date = i.getStringExtra("date");
        final String location = i.getStringExtra("location");
        final String description = i.getStringExtra("description");
        final String host = i.getStringExtra("host");
        final String pos = i.getStringExtra("position");



        TextView t = (TextView)findViewById(R.id.textView);
        TextView ti = (TextView)findViewById(R.id.textView2);
        TextView da = (TextView)findViewById(R.id.textView3);
        TextView g = (TextView)findViewById(R.id.textView4);
        TextView d = (TextView)findViewById(R.id.textView5);
        TextView h = (TextView)findViewById(R.id.textView6);

        t.setText(title);
        ti.setText(time);
        da.setText(date);
        g.setText(location);
        d.setText(description);
        h.setText(host);


        final Context context = this;
        deleteButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                jos = null;
                try {
                    // Reading a file that already exists
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
                        jos = new JSONObject(j);
                        ja = jos.getJSONArray("data");
                        ja.remove(Integer.parseInt(pos));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                catch(IOException e){
                    Log.d("No file:", "JSON Error");
                }

                // WRITE FILE
                //ja.remove(Integer.parseInt(pos));
                //Log.d("Position", pos);

                // write the file
                JSONObject temp = null;
                try{

                    File f = new File(getFilesDir(), "file.ser");
                    FileOutputStream fo = new FileOutputStream(f);
                    ObjectOutputStream o = new ObjectOutputStream(fo);
                    String j = jos.toString();
                    //Log.d("after", j);
                    o.writeObject(j);
                    // remove the file entirely if there are no more objects
                    // to show "No items"
                    if(ja.length() == 0) {
                        f.delete();
                    }
                    o.close();
                    fo.close();
                }
                catch(IOException e){
                    Log.d("Error", "Write Error.");
                }


                Intent i = new Intent(EventDetail.this, EventList.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        goingButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                jos2 = null;

                // Create the name of the file based off the email
                Intent i = getIntent();
                String emailfile = null;
                String email = i.getStringExtra("email");
                emailfile = email + ".ser";

                if(emailfile.length() < 5) {
                    emailfile = "guest@guest.com.ser";
                }

                // Read the file


                try {
                    File f = new File(getFilesDir(), emailfile);
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
                        jos2 = new JSONObject(j);
                        ja2 = jos2.getJSONArray("data");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    // Here, initialize a new JSONObject
                    jos2 = new JSONObject();
                    ja2 = new JSONArray();
                    try {
                        jos2.put("data", ja2);
                    } catch (JSONException j) {
                        j.printStackTrace();
                    }
                }

                JSONObject temp = new JSONObject();
                try {
                    temp.put("title", title);
                    temp.put("time", time);
                    temp.put("date", date);
                    temp.put("location", location);
                    temp.put("description", description);
                    temp.put("host", host);

                } catch (JSONException j) {
                    j.printStackTrace();
                }

                ja2.put(temp);

                // write the file
                try {
                    File f = new File(getFilesDir(), emailfile);
                    FileOutputStream fo = new FileOutputStream(f);
                    ObjectOutputStream o = new ObjectOutputStream(fo);
                    String j = jos2.toString();
                    o.writeObject(j);
                    o.close();
                    fo.close();
                } catch (IOException e) {

                }

            }
        });

    }


}
