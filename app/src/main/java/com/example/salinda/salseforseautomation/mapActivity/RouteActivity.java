package com.example.salinda.salseforseautomation.mapActivity;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.salinda.salseforseautomation.Other.ConnectionDetector;
import com.example.salinda.salseforseautomation.R;
import com.example.salinda.salseforseautomation.model.RouteModel;
import com.example.salinda.salseforseautomation.rest.ApiClient;
import com.example.salinda.salseforseautomation.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RouteActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ApiInterface apiInterface;
    private RouteAdapter adepter;
    private SearchView searchView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_ui);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // toolbar fancy stuff
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Route List");

        recyclerView = (RecyclerView)findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        fetchRoute();

//        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getApplicationContext(), recyclerView, new RecyclerViewClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                Toast.makeText(getApplicationContext()," is pressed!", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//                Toast.makeText(getApplicationContext()," is long pressed!", Toast.LENGTH_SHORT).show();
//            }
//        }));
    }

    private void fetchRoute() {
        if(new ConnectionDetector(this).isConnected()){
            progressDialog = new ProgressDialog(RouteActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            Call<List<RouteModel>> call = apiInterface.getAllRoute();
            call.enqueue(new Callback<List<RouteModel>>() {
                @Override
                public void onResponse(Call<List<RouteModel>> call, Response<List<RouteModel>> response) {
                    progressDialog.dismiss();
                    if(response.code()==200){
                        adepter = new RouteAdapter(getApplicationContext(), response.body());
                        recyclerView.setAdapter(adepter);
                    }else {
                        Toast.makeText(getApplicationContext(),"System Error.. Try Again Later", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<RouteModel>> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Request fail", Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            Toast.makeText(getApplicationContext(),"Waiting for Network", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                try{
                    adepter.getFilter().filter(query);
                }catch (Exception e){

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                try{
                    adepter.getFilter().filter(query);
                }catch (Exception e){

                }
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }
}
