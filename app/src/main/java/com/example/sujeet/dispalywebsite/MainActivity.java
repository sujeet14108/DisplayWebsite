package com.example.sujeet.dispalywebsite;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView uiUpdate;
    static final private String nmber = "prime";
    static final private String Isprime = "isprime";

    String x="";
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(nmber, x);
        super.onSaveInstanceState(savedInstanceState);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        uiUpdate = (TextView) findViewById(R.id.textView);
       if (savedInstanceState != null) {
            uiUpdate.setText(savedInstanceState.getString(nmber));
    }
        setSupportActionBar(toolbar);
        final Button GetServerData = (Button) findViewById(R.id.fetch);

        GetServerData.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                new LongOperation().execute();

            }
        });

    }

    private class LongOperation  extends AsyncTask<String, Void, Void> {

        private final HttpClient Client = new DefaultHttpClient();
        private String Content;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(MainActivity.this);



        protected void onPreExecute() {

            uiUpdate.setText("Output : ");
            Dialog.setMessage("Downloading source..");
            Dialog.show();
        }


        protected Void doInBackground(String... urls) {



try {
    HttpClient client = new DefaultHttpClient();
    HttpGet request = new HttpGet("https://www.iiitd.ac.in/about");
    HttpResponse response = client.execute(request);

    Content = "";
    InputStream in = response.getEntity().getContent();
    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    StringBuilder str = new StringBuilder();
    String line = null;
    while ((line = reader.readLine()) != null) {
        str.append(line);
    }
    in.close();
    Content = str.toString();
}   catch (IOException e) {
            Error = e.getMessage();
            cancel(true);
        }
            return null;
        }

        protected void onPostExecute(Void unused) {

            Dialog.dismiss();

            if (Error != null) {

                uiUpdate.setText("Output : "+Error);

            } else {

                uiUpdate.setText("Output : \n"+Content);
                System.out.println(Content);
                x=Content;

            }
        }

    }
}
