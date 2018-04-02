package com.example.salinda.salseforseautomation.activity;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.salinda.salseforseautomation.Other.ConnectionDetector;
import com.example.salinda.salseforseautomation.Other.RecyclerViewClickListener;
import com.example.salinda.salseforseautomation.Other.RecyclerViewTouchListener;
import com.example.salinda.salseforseautomation.R;
import com.example.salinda.salseforseautomation.SQLite.SQLiteOutletHandler;
import com.example.salinda.salseforseautomation.adapter.OutletAdapter;
import com.example.salinda.salseforseautomation.mapActivity.RouteAdapter;
import com.example.salinda.salseforseautomation.mapActivity.CurrentLocation;
import com.example.salinda.salseforseautomation.mapActivity.Direction;
import com.example.salinda.salseforseautomation.mapActivity.LocationModel;
import com.example.salinda.salseforseautomation.model.OutletModel;
import com.example.salinda.salseforseautomation.rest.ApiClient;
import com.example.salinda.salseforseautomation.rest.ApiInterface;
import com.example.salinda.salseforseautomation.session.OutletSession;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.salinda.salseforseautomation.mapActivity.RouteAdapter.KEY_OUTLETS;

public class ShowOutletActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ApiInterface apiInterface;
    private OutletAdapter adepter;
    private SearchView searchView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outlet_ui);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // toolbar fancy stuff
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.outlet_toolbar_title);

        recyclerView = (RecyclerView)findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        fetchOutlet();

        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getApplicationContext(), recyclerView, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                List<LocationModel> locationList = new ArrayList<LocationModel>();
                LocationModel locationModel = new LocationModel();
                List<OutletModel> outletModels = new OutletSession(getApplicationContext()).getAllOutlet();
                locationModel.setLongitude(outletModels.get(position).getLongitude());
                locationModel.setLatitude(outletModels.get(position).getLatitude());
                locationModel.setName(outletModels.get(position).getName());
                locationList.add(locationModel);

                Intent intent = new Intent(getApplicationContext(), Direction.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle bundle = new Bundle();
                bundle.putSerializable(KEY_OUTLETS, (Serializable) locationList);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<LocationModel> locationList = new ArrayList<LocationModel>();
                List<OutletModel> outletModels = new OutletSession(getApplicationContext()).getAllOutlet();
                for(int i = 0; i<outletModels.size(); i++){
                    LocationModel locationModel = new LocationModel();
                    locationModel.setName(outletModels.get(i).getName());
                    locationModel.setLatitude(outletModels.get(i).getLatitude());
                    locationModel.setLongitude(outletModels.get(i).getLongitude());
                    locationList.add(locationModel);
                }

                Intent intent = new Intent(getApplicationContext(), CurrentLocation.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle bundle = new Bundle();
                bundle.putSerializable(RouteAdapter.KEY_OUTLETS, (Serializable) locationList);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void fetchOutlet() {
        if(new ConnectionDetector(this).isConnected()){
            progressDialog = new ProgressDialog(ShowOutletActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            Call<List<OutletModel>> call = apiInterface.getOutlet();
            call.enqueue(new Callback<List<OutletModel>>() {
                @Override
                public void onResponse(Call<List<OutletModel>> call, Response<List<OutletModel>> response) {
                    if(response.code()==200){
                        progressDialog.dismiss();
                        adepter = new OutletAdapter(getApplicationContext(), response.body());      //create adapter object with our data
                        recyclerView.setAdapter(adepter);
                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"System Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<OutletModel>> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Request fail", Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            try{
                adepter = new OutletAdapter(getApplicationContext(), new SQLiteOutletHandler(getApplicationContext()).getAllOutlets());
                recyclerView.setAdapter(adepter);
            }catch (Exception e){

            }
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
