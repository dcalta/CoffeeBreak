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
import java.util.ArrayList;

import java.io.InputStream;

public class CoffeeBreak extends AppCompatActivity implements View.OnClickListener{
    Button login;
    Button createAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee_break);
        login = findViewById(R.id.loginBut);
        createAcc = findViewById(R.id.createAccBut);
        login.setOnClickListener(this);
        createAcc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // if signing on with existing account
        if(v.getId() == R.id.loginBut) {
            EditText getEmail = findViewById(R.id.emailField);
            EditText getPass = findViewById(R.id.passField);
            String  Email = getEmail.getText().toString();
            String Password = getPass.getText().toString();
            Intent eventListIntent = new Intent(this, EventList.class);
            // pass some key value pairs to the next Activity (via the Intent)
            eventListIntent.putExtra("email", Email);
            eventListIntent.putExtra("pass", Password);
            startActivity(eventListIntent);

        }
        // if making new account
        else if(v.getId() == R.id.createAccBut) {
            Intent newUserIntent = new Intent(this, NewUser.class);
            startActivity(newUserIntent);
        }
        else {
            // Nothing
        }
    }
}
