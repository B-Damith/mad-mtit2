package com.mtit.gitso;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {
    EditText username;
    Button search;
    HttpURLConnection urlConnection;
    LinearLayout headerProgress;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        headerProgress = (LinearLayout) findViewById(R.id.headerProgress);
        //headerProgress.setVisibility(View.GONE);
        progress = new ProgressDialog(this);
        //progress.setTitle("Loading");
        progress.setCancelable(false);

        search = (Button) findViewById(R.id.search);
        username = (EditText) findViewById(R.id.username);
        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            if(username!=null && username.getText()!=null && !username.getText().toString().equals("")){
                progress.setMessage("Connecting to Server...");
                new Retrieve().execute(username.getText().toString().trim());
            }else{
                username.setError("User Name is required");
            }
            }
        });
    }

    private class Retrieve extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String response = "";
            try{

                URL url = new URL("https://api.github.com/users/"+params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(1500000);
                urlConnection.setConnectTimeout(1500000);
                urlConnection.setRequestMethod("GET");
                try {
                    InputStreamReader is = new InputStreamReader(urlConnection.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(is);
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    response = stringBuilder.toString();
                    return response;
                }
                finally{
                    urlConnection.disconnect();
                }
            }catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
            }
            return response;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //headerProgress.setVisibility(View.VISIBLE);
            progress.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(!s.equals("")){
                Intent intent = new Intent(LoginActivity.this,RepoListActivity.class);
                intent.putExtra("response",s);
                startActivity(intent);
            }else{
                username.setError("User Name does not exists");
            }
            //headerProgress.setVisibility(View.GONE);
            progress.dismiss();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(username!=null) {
            username.setText("");
        }
    }

}
