package com.example.salinda.salseforseautomation.ReportActivities;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.salinda.salseforseautomation.Other.ConnectionDetector;
import com.example.salinda.salseforseautomation.R;
import com.example.salinda.salseforseautomation.adapter.MonthReportAdapter;
import com.example.salinda.salseforseautomation.model.MonthReportModel;
import com.example.salinda.salseforseautomation.rest.ApiClient;
import com.example.salinda.salseforseautomation.rest.ApiInterface;
import com.example.salinda.salseforseautomation.session.SessionHandler;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserMonthSales extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ApiInterface apiInterface;
    private MonthReportAdapter adepter;
    private SearchView searchView;
    int month;
    int year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.month_report_ui);

        Bundle extras = getIntent().getExtras();
        String selectedMonth = extras.getString(ReportActivity.KEY_MONTH);
        year = Integer.parseInt(extras.getString(ReportActivity.KEY_YEAR));
        switch (selectedMonth){
            case "January": month = 1; break;
            case "February": month = 2; break;
            case "March": month = 3; break;
            case "April": month = 4; break;
            case "May": month = 5; break;
            case "Jun": month = 6; break;
            case "Jul": month = 7; break;
            case "August": month = 8; break;
            case "September": month = 9; break;
            case "October": month = 10; break;
            case "November": month = 10; break;
            case "December": month = 12; break;
        }

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // toolbar fancy stuff
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My "+selectedMonth+" Sales");

        recyclerView = (RecyclerView)findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        fetchData();

    }

    private void fetchData(){

        if(new ConnectionDetector(this).isConnected()){
            apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Call<List<MonthReportModel>> call = apiInterface.getUserMonthSales(new SessionHandler(getApplicationContext()).getUserDetails().getId(), month, year);
            call.enqueue(new Callback<List<MonthReportModel>>() {
                @Override
                public void onResponse(Call<List<MonthReportModel>> call, Response<List<MonthReportModel>> response) {
                    if(response.code()==200){
                        adepter = new MonthReportAdapter(getApplicationContext(), response.body());
                        recyclerView.setAdapter(adepter);
                        float total = 0;
                        for(int i = 0; i<response.body().size(); i++){
                            total = total + response.body().get(i).getAmount();
                        }
                        TextView totalAmount = (TextView) findViewById(R.id.total_amount);
                        totalAmount.setText("Rs. "+String.valueOf(total));
                        if(response.body().size()==0){
                            Toast.makeText(getApplicationContext(),"Empty", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(),"System Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<MonthReportModel>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Request Fail", Toast.LENGTH_SHORT).show();
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

    /*@Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }*/
}
