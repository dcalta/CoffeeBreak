package com.example.drewcaltagirone.coffeebreak;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.util.ArrayList;

import java.io.InputStream;
import java.sql.Time;
import java.util.Date;

public class EventDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        Intent i = getIntent();
        String title = i.getStringExtra("title");
        String time = i.getStringExtra("time");
        String date = i.getStringExtra("date");
        String gps = i.getStringExtra("gps");
        String description = i.getStringExtra("description");

        TextView t = (TextView)findViewById(R.id.textView);
        TextView ti = (TextView)findViewById(R.id.textView2);
        TextView da = (TextView)findViewById(R.id.textView3);
        TextView g = (TextView)findViewById(R.id.textView4);
        TextView d = (TextView)findViewById(R.id.textView5);

        t.setText("Title:   " + title);
        ti.setText("Time:   " + time);
        da.setText("Date:   " + date);
        g.setText("GPS:   " + gps);
        d.setText("Description:   " + description);
    }

    protected void onResume(Bundle savedInstanceState){
        //
    }
}
