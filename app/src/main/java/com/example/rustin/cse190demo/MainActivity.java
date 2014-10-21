package com.example.rustin.cse190demo;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class MainActivity extends Activity {

    private TextView timeText;
    private TextView dateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeText = (TextView) findViewById(R.id.timeText);
        dateText = (TextView) findViewById(R.id.dateText);

        new DownloadJSONTask().execute(new String[] {"http://time.jsontest.com"});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class DownloadJSONTask extends AsyncTask<String,Integer,JSONObject> {
        protected JSONObject doInBackground(String... urls) {
            JSONObject result = null;
            DefaultHttpClient client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(urls[0]); // Don't do this
            Log.d("JSON Thing","lets get started");
            try {
                HttpResponse execute = client.execute(httpGet);
                InputStream content = execute.getEntity().getContent();

                BufferedReader buffer = new BufferedReader(new InputStreamReader(content));

                String response = "",s = "";

                while ((s = buffer.readLine()) != null) {
                    response += s;
                }

                result = new JSONObject(response);
            } catch (Exception e) {} // Don't do this

            return result;
        }

        protected void onPostExecute(JSONObject result) {
            try {
                timeText.setText(result.getString("time"));
                dateText.setText(result.getString("date"));
            } catch (Exception e) {} // Again don't do this
        }

    }
}
