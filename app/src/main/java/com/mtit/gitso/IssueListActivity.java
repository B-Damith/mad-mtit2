package com.mtit.gitso;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.LinearLayout;

import com.mtit.gitso.adaptor.CollectionAdaptorIssues;
import com.mtit.gitso.model.IssueListModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class IssueListActivity extends AppCompatActivity {
    RecyclerView rv_collection;
    ArrayList<IssueListModel> data;
    LinearLayout headerProgress;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        headerProgress = (LinearLayout) findViewById(R.id.headerProgress);

        progress = new ProgressDialog(this);
        //progress.setTitle("Loading");
        progress.setMessage("Syncronizing...");
        progress.setCancelable(false);

        String issueUrl = getIntent().getExtras()!=null?getIntent().getExtras().getString("url"):"";
        issueUrl = issueUrl.replace("{/number}","");

        rv_collection = (RecyclerView)findViewById(R.id.rv_collection_issues);
        rv_collection.setHasFixedSize(true);
        rv_collection.setLayoutManager(new LinearLayoutManager(this));
        rv_collection.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        rv_collection.setItemAnimator(new DefaultItemAnimator());
        new RetrieveFeedTask().execute(issueUrl);
    }

    public class RetrieveFeedTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //headerProgress.setVisibility(View.VISIBLE);
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
            data = new ArrayList<IssueListModel>();
            try {
                JSONArray repoList = new JSONArray(s);
                for(int i=0;i<repoList.length();i++){
                    JSONObject obj = repoList.getJSONObject(i);
                    IssueListModel repo = new IssueListModel(obj.getString("title"),obj.getString("state"),obj.getString("created_at"),obj.getString("body"));
                    data.add(repo);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //headerProgress.setVisibility(View.GONE);
            progress.dismiss();
            rv_collection.setAdapter(new CollectionAdaptorIssues(data,new CollectionAdaptorIssues.OnItemClickListener() {
                @Override public void onItemClick(IssueListModel item) {

                    //Toast.makeText(IssueListActivity.this, "Unreadable...", Toast.LENGTH_SHORT).show();
                    /*if(item.getIssueCount()>0){
                        Intent intent = new Intent(RepoListActivity.this,IssueListActivity.class);
                        intent.putExtra("url",item.getIssuesURL());
                        startActivity(intent);
                    }else{
                        Toast.makeText(RepoListActivity.this, "No Issues are reported", Toast.LENGTH_SHORT).show();
                    }*/
                }
            }));
        }

    }
}
