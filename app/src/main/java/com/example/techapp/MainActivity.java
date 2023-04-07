package com.example.techapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;


public class MainActivity extends AppCompatActivity {
    RecyclerView lv;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private int totalCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (RecyclerView) findViewById(R.id.listview);

        prefs = getPreferences(Context.MODE_PRIVATE);
        editor = prefs.edit();

        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED)
        {
            new Information().execute("");
        }else
        {
            Toast.makeText(MainActivity.this,"YOU ARE OFFLINE",Toast.LENGTH_SHORT).show();
        }

        BroadcastReceiver connectionBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent == null || intent.getExtras() == null) {
                    return;
                } else {
                    ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = cm.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED)
                    {
                        //Toast.makeText(MainActivity.this,"YOU ARE ONLINE",Toast.LENGTH_LONG).show();
                        new Information().execute("");
                    } else
                    {
                        Toast.makeText(MainActivity.this,"YOU ARE OFFLINE",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(connectionBroadcastReceiver, filter);

        totalCount = prefs.getInt("counter", 0);
        totalCount++;
        editor.putInt("counter", totalCount);
        editor.commit();

        Log.e("counter", String.valueOf(totalCount));
        Toast.makeText(MainActivity.this,"counter "+totalCount,Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_master, menu);

        MenuItem menuItem1= menu.findItem(R.id.counter);
        menuItem1.setTitle(totalCount+" ");

        return true;
    }

    public void onResume() {

        super.onResume();


    }

    public class Information extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            try {
                URL url = new URL("https://reqres.in/api/users");

                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestMethod("GET");   //POST or GET
                urlConnection.connect();

                int statusCode = urlConnection.getResponseCode();
                if (statusCode == 200 )
                {
                    InputStream it = new BufferedInputStream(urlConnection.getInputStream());
                    InputStreamReader read = new InputStreamReader(it);
                    BufferedReader buff = new BufferedReader(read);
                    StringBuilder dta = new StringBuilder();
                    String chunks;
                    while ((chunks = buff.readLine()) != null)
                    {
                        dta.append(chunks);
                    }
                    String returndata = dta.toString();
                    return returndata;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String resultData) {
            super.onPostExecute(resultData);
            if(resultData != null) {
                JSONObject json = null;
                try {
                    json = new JSONObject(resultData);
                    JSONArray arr = json.getJSONArray("data");
                    ArrayList<users> arrlist = new ArrayList<>();
                    for (int i=0; i < arr.length(); i++)
                    {
                        arrlist.add(new users(arr.getJSONObject(i).getInt("id"),arr.getJSONObject(i).getString("email"),arr.getJSONObject(i).getString("first_name"),arr.getJSONObject(i).getString("last_name"),arr.getJSONObject(i).getString("avatar")));
                    }

                    userlistadapter adapter = new userlistadapter(arrlist,getApplicationContext());
                    lv.setAdapter(adapter);
                    lv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}