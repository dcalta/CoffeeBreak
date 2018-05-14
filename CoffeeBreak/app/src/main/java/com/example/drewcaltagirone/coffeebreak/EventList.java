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
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import java.io.InputStream;
import java.sql.Time;
import java.util.Date;

public class EventList extends AppCompatActivity {
    String name;
    String email;
    String comp;

    public JSONObject jos = null;
    public JSONArray ja = null;
    private ListView list;
    private final String TAG = "ListView";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
    }

    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_event_list);
        list = (ListView) findViewById(R.id.event_list_view);
        TextView text = findViewById(R.id.noItemText);
        text.setVisibility(View.INVISIBLE);

        Intent in = getIntent();
        name = in.getStringExtra("name");
        email = in.getStringExtra("email");
        comp = in.getStringExtra("comp");
        // Put the json data into the String json

        jos = null;
        try{
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
            try{
                j = (String) o.readObject();
            }
            catch(ClassNotFoundException c){
                c.printStackTrace();
            }
            try {
                jos = new JSONObject(j);
                ja = jos.getJSONArray("data");
            }
            catch(JSONException e){
                e.printStackTrace();
            }

            // Show the list
            final ArrayList<EventData> aList = new ArrayList<EventData>();
            for(int i = 0; i < ja.length(); i++){

                EventData ld = new EventData();
                try {
                    ld.title = ja.getJSONObject(i).getString("title");
                    ld.time = ja.getJSONObject(i).getString("time");
                    ld.date = ja.getJSONObject(i).getString("date");
                    ld.location = ja.getJSONObject(i).getString("location");
                    ld.description = ja.getJSONObject(i).getString("description");
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

                aList.add(ld);
            }

            // Create an array and assign each element to be the title
            // field of each of the ListData objects (from the array list)
            String[] listItems = new String[aList.size()];

            for(int i = 0; i < aList.size(); i++){
                EventData listD = aList.get(i);
                listItems[i] = listD.title;
            }

            // Show the list view with the each list item an element from listItems
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems);
            list.setAdapter(adapter);

            // Set an OnItemClickListener for each of the list items
            final Context context = this;
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    EventData selected = aList.get(position);

                    // Create an Intent to reference our new activity, then call startActivity
                    // to transition into the new Activity.
                    Intent detailIntent = new Intent(context, EventDetail.class);

                    // pass some key value pairs to the next Activity (via the Intent)
                    detailIntent.putExtra("title", selected.title);
                    detailIntent.putExtra("time", selected.time);
                    detailIntent.putExtra("date", selected.date);
                    detailIntent.putExtra("location", selected.location);
                    detailIntent.putExtra("description", selected.description);
                    detailIntent.putExtra("position", Integer.toString(position));

                    startActivity(detailIntent);
                }

            });
        }
        catch(IOException e){
            // There's no JSON file that exists, so don't
            // show the list. But also don't worry about creating
            // the file just yet, that takes place in AddText.

            //Here, disable the list view
            list.setEnabled(false);
            list.setVisibility(View.INVISIBLE);

            //show the text view
            text.setVisibility(View.VISIBLE);
        }
    }


    // This method will just show the menu item (which is our button "ADD")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        // the menu being referenced here is the menu.xml from res/menu/menu.xml
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    /* Here is the event handler for the menu button that I forgot in class.
    The value returned by item.getItemID() is
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, String.format("" + item.getItemId()));
        // Handle item selection
        Intent i;

        switch (item.getItemId()) {
            case R.id.action_favorite:
                /*the R.id.action_favorite is the ID of our button (defined in strings.xml).
                Change Activity here (if that's what you're intending to do, which is probably is).
                 */
                i = new Intent(this, NewEvent.class);
                startActivity(i);
                break;
            case R.id.action_account:
                /*the R.id.action_favorite is the ID of our button (defined in strings.xml).
                Change Activity here (if that's what you're intending to do, which is probably is).
                 */

                i = new Intent(EventList.this, Account.class);
                i.putExtra("name", name);
                i.putExtra("email", email);
                i.putExtra("comp", comp);
                startActivity(i);
                break;
            case R.id.action_logout:
                /*the R.id.action_favorite is the ID of our button (defined in strings.xml).
                Change Activity here (if that's what you're intending to do, which is probably is).
                 */
                i = new Intent(this, CoffeeBreak.class);
                startActivity(i);
                break;
            default:
                super.onOptionsItemSelected(item);
                break;
        }
        return true;
    }
}
