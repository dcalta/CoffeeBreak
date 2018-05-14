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

public class NewUser extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        Button b = findViewById(R.id.createAccBut);
        final Context context = this;
        b.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                EditText getName = findViewById(R.id.nameCField);
                EditText getEmail = findViewById(R.id.emailCField);
                EditText getComp = findViewById(R.id.compCField);
                EditText getPass = findViewById(R.id.passCField);
                String  Name = getName.getText().toString();
                String  Email = getEmail.getText().toString();
                String  Comp = getComp.getText().toString();
                String Password = getPass.getText().toString();
                // pass some key value pairs to the next Activity (via the Intent)
                Intent i = new Intent(NewUser.this, EventList.class);
                i.putExtra("name", Name);
                i.putExtra("email", Email);
                i.putExtra("comp", Comp);
                i.putExtra("pass", Password);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

    }

    protected void onResume(Bundle savedInstanceState){
        //
    }
}
