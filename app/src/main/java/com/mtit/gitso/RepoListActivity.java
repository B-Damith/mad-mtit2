package com.mtit.gitso;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.mtit.gitso.adaptor.CollectionAdapter;
import com.mtit.gitso.model.RepoListModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class RepoListActivity extends AppCompatActivity {
    RecyclerView rv_collection;
    ArrayList<RepoListModel> data;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progress = new ProgressDialog(this);
        progress.setMessage("Syncronizing...");
        progress.setCancelable(false);
        String repository = getIntent().getExtras()!=null?getIntent().getExtras().getString("response"):"";

        String repositoryUrl = "";
        try {
            JSONObject jObj = new JSONObject(repository);
            repositoryUrl = (String) jObj.get("repos_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        rv_collection = (RecyclerView)findViewById(R.id.rv_collection);
        rv_collection.setHasFixedSize(true);
        rv_collection.setLayoutManager(new LinearLayoutManager(this));
        rv_collection.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        rv_collection.setItemAnimator(new DefaultItemAnimator());
        new RetrieveFeedTask().execute(repositoryUrl);

    }

    public class RetrieveFeedTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try{
                URL url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
                finally{
                    urlConnection.disconnect();
                }
            }catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            data = new ArrayList<RepoListModel>();
            try {
                JSONArray repoList = new JSONArray(s);
                for(int i=0;i<repoList.length();i++){
                    JSONObject obj = repoList.getJSONObject(i);
                    RepoListModel repo = new RepoListModel(obj.getString("name"),obj.getString("description"),obj.getInt("open_issues_count"),obj.getString("issues_url"),obj.getString("language"));
                    data.add(repo);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            rv_collection.setAdapter(new CollectionAdapter(data,new CollectionAdapter.OnItemClickListener() {
                @Override public void onItemClick(RepoListModel item) {
                    if(item.getIssueCount()>0){
                        Intent intent = new Intent(RepoListActivity.this,IssueListActivity.class);
                        intent.putExtra("url",item.getIssuesURL());
                        startActivity(intent);
                    }else{
                        Toast.makeText(RepoListActivity.this, "No Issues are reported", Toast.LENGTH_SHORT).show();
                    }
                }
            }));

            progress.dismiss();
        }

    }
}
