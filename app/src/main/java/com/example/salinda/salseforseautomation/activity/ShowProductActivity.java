package com.example.salinda.salseforseautomation.activity;

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
import android.view.View;
import android.widget.Toast;

import com.example.salinda.salseforseautomation.Other.ConnectionDetector;
import com.example.salinda.salseforseautomation.Other.RecyclerViewClickListener;
import com.example.salinda.salseforseautomation.Other.RecyclerViewTouchListener;
import com.example.salinda.salseforseautomation.R;
import com.example.salinda.salseforseautomation.SQLite.SQLiteProductHandler;
import com.example.salinda.salseforseautomation.adapter.ProductAdapter;
import com.example.salinda.salseforseautomation.model.ProductsModel;
import com.example.salinda.salseforseautomation.rest.ApiClient;
import com.example.salinda.salseforseautomation.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowProductActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ApiInterface apiInterface;
    private ProductAdapter adepter;
    private SearchView searchView;
    private SQLiteProductHandler dataHandler;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_ui);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // toolbar fancy stuff
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.product_toolbar_title);

        recyclerView = (RecyclerView)findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        fetchProduct();
        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getApplicationContext(), recyclerView, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {

                /*Intent intent = new Intent(getApplicationContext() , ShowSingleProductActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);*/
                adepter.selectSingleProduct(position);
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(getApplicationContext()," is long pressed!", Toast.LENGTH_SHORT).show();

            }
        }));
    }

    private void fetchProduct(){

        if(new ConnectionDetector(this).isConnected()){
            progressDialog = new ProgressDialog(ShowProductActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            Call<List<ProductsModel>> call = apiInterface.getProduct();
            call.enqueue(new Callback<List<ProductsModel>>() {
                @Override
                public void onResponse(Call<List<ProductsModel>> call, Response<List<ProductsModel>> response) {
                    if(response.code()==200){
                        progressDialog.dismiss();
                        adepter = new ProductAdapter(getApplicationContext(), response.body());
                        recyclerView.setAdapter(adepter);
                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"System Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<ProductsModel>> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Request fail", Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            try{
                dataHandler = new SQLiteProductHandler(getApplicationContext());
                adepter = new ProductAdapter(getApplicationContext(), dataHandler.getAllProduct());
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
