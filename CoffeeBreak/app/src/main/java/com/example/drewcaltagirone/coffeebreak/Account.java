package com.example.drewcaltagirone.coffeebreak;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.widget.TextView;

import java.util.ArrayList;

import java.io.InputStream;
import java.sql.Time;
import java.util.Date;

public class Account extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Button b = findViewById(R.id.save);
        Button plannerButton = findViewById(R.id.planner);

        Intent i = getIntent();
        final String name = i.getStringExtra("name");
        final String email = i.getStringExtra("email");
        final String comp = i.getStringExtra("comp");

        EditText n = (EditText) findViewById(R.id.Name);
        EditText e = (EditText) findViewById(R.id.Email);
        EditText c = (EditText) findViewById(R.id.Company);

        n.setText(name);
        e.setText(email);
        c.setText(comp);

        b.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                // pass some key value pairs to the next Activity (via the Intent)
                String NAME, EMAIL, COMP;
                EditText getName = findViewById(R.id.Name);
                EditText getEmail = findViewById(R.id.Email);
                EditText getComp = findViewById(R.id.Company);
                NAME = getName.getText().toString();
                EMAIL = getEmail.getText().toString();
                COMP = getComp.getText().toString();

                Intent i = new Intent(Account.this, EventList.class);
                i.putExtra("name", NAME);
                i.putExtra("email", EMAIL);
                i.putExtra("comp", COMP);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(i);
            }
        });

        plannerButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Account.this, UserPlanner.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });

    }

    protected void onResume(Bundle savedInstanceState){
        //
    }
}
