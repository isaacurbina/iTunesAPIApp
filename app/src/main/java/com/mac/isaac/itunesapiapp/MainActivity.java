package com.mac.isaac.itunesapiapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.mac.isaac.itunesapiapp.adapters.RVAdapter;
import com.mac.isaac.itunesapiapp.pojos.Result;
import com.mac.isaac.itunesapiapp.pojos.Results;
import com.mac.isaac.itunesapiapp.retrofit.RFInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RVAdapter adapter;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    List<Result> results;
    EditText etSearch;
    boolean loading = false;
    int columns = 1;
    int page = 1;
    String search_value;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("search_value", search_value);
        Log.i("SAVESTATE", "Storing "+search_value);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etSearch = (EditText) this.findViewById(R.id.et_search);
        recyclerView = (RecyclerView) this.findViewById(R.id.recycler_list_results);
        recyclerView.setHasFixedSize(true);
        columns = this.getSupportedColumns();
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(columns, 1);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        if (savedInstanceState != null) {
            search_value = savedInstanceState.getString("search_value");
            Log.i("SAVEDSTATE", "variables " +
                            search_value
            );
            runTask(search_value);
        }
    }

    // Dynamically display different columns according to the device width
    public int getSupportedColumns() {
        if (this.findViewById(R.id.orientation_portrait) != null)
            return 2;
        else if (this.findViewById(R.id.orientation_landscape) != null)
            return 4;
        else
            return 1;
    }

    public void doSearch(View view) {
        String q = etSearch.getText().toString();
        search_value = q;
        runTask(q);
    }

    public void runTask(String q) {
        if (q.length()>0) {
            GrabResultsTask task = new GrabResultsTask();
            task.execute(q);
            loading = true;
        }
    }

    private class GrabResultsTask extends AsyncTask<String, Integer, List<Result>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<Result> results) {
            super.onPostExecute(results);
            loading = false;
            if (results.size()>0) {
                adapter = new RVAdapter(MainActivity.this, results);
                recyclerView.setAdapter(adapter);
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected List<Result> doInBackground(String... params) {

            List<Result> response;
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://itunes.apple.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RFInterface rfInterface = retrofit.create(RFInterface.class);

            // TEST CASE FOR MEDIA BY MOVIE ID
            Call<Results> request = rfInterface.search(
                    params[0],
                    25
            );

            Results results = null;

            try {
                results = request.execute().body();
                if (results.getResults().size()>0)
                    response = results.getResults();
                else
                    response = new ArrayList<>();
            } catch (Exception e) {
                System.out.println("Error performing Retrofit Request: " + e.getMessage());
                response = new ArrayList<>();
            }
            return response;
        }
    }
}
