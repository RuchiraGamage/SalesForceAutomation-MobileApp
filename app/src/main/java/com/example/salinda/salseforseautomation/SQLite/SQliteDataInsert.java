package com.example.salinda.salseforseautomation.SQLite;

import android.content.Context;
import android.os.SystemClock;

import com.example.salinda.salseforseautomation.model.LoginModel;
import com.example.salinda.salseforseautomation.model.OutletModel;
import com.example.salinda.salseforseautomation.model.OutstandingModel;
import com.example.salinda.salseforseautomation.model.ProductsModel;
import com.example.salinda.salseforseautomation.model.VanProductModel;
import com.example.salinda.salseforseautomation.rest.ApiClient;
import com.example.salinda.salseforseautomation.rest.ApiInterface;
import com.example.salinda.salseforseautomation.session.SessionHandler;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SQliteDataInsert {

    private ApiInterface apiInterface;
    Context context;

    public SQliteDataInsert(Context context){
        this.context = context;
    }

    public void InsertDatabase(){
        new SQLiteDatabaseHandler(context);
//        new SQLiteOutletHandler(context).deleteAll();
        InsertOutlets();
//        new SQLiteTodayOutletHandler(context).deleteAll();
        InsertTodayOutlets();
//        new SQLiteProductHandler(context).deleteAll();
        InsertProducts();
        InsertVanProducts();
//        new SQLiteOutstandingHandler(context).deleteAll();
        InsertOutstanding();
    }

    public void InsertTodayOutlets(){
        SessionHandler sessionHandler = new SessionHandler(context);
        LoginModel loginModel = sessionHandler.getUserDetails();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<OutletModel>> call = apiInterface.getOutletsByUserId(loginModel.getId());
        call.enqueue(new Callback<List<OutletModel>>() {
            @Override
            public void onResponse(Call<List<OutletModel>> call, Response<List<OutletModel>> response) {
                new SQLiteTodayOutletHandler(context).deleteAll();
                SystemClock.sleep(100);
                new SQLiteTodayOutletHandler(context).addOutletList(response.body());
            }

            @Override
            public void onFailure(Call<List<OutletModel>> call, Throwable t) {

            }
        });
    }

    public void InsertOutlets(){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<OutletModel>> call = apiInterface.getOutlet();
        call.enqueue(new Callback<List<OutletModel>>() {
            @Override
            public void onResponse(Call<List<OutletModel>> call, Response<List<OutletModel>> response) {
                new SQLiteOutletHandler(context).deleteAll();
                SystemClock.sleep(100);
                new SQLiteOutletHandler(context).addOutletList(response.body());
            }

            @Override
            public void onFailure(Call<List<OutletModel>> call, Throwable t) {

            }
        });
    }

    public void InsertProducts(){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<ProductsModel>> call = apiInterface.getProduct();
        call.enqueue(new Callback<List<ProductsModel>>() {
            @Override
            public void onResponse(Call<List<ProductsModel>> call, Response<List<ProductsModel>> response) {
                new SQLiteProductHandler(context).deleteAll();
                SystemClock.sleep(100);
                new SQLiteProductHandler(context).addProductList(response.body());
            }

            @Override
            public void onFailure(Call<List<ProductsModel>> call, Throwable t) {

            }
        });
    }

    public void InsertVanProducts(){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<VanProductModel>> call = apiInterface.getVanProduct(new SessionHandler(context).getUserDetails().getId());
        call.enqueue(new Callback<List<VanProductModel>>() {
            @Override
            public void onResponse(Call<List<VanProductModel>> call, Response<List<VanProductModel>> response) {
                try{
                    new SQLiteVanProductHandler(context).deleteAll();
                    SystemClock.sleep(100);
                    new SQLiteVanProductHandler(context).addProductList(response.body());
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<List<VanProductModel>> call, Throwable t) {

            }
        });
    }

    public void InsertOutstanding(){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<OutstandingModel>> call = apiInterface.getOutstanding();
        call.enqueue(new Callback<List<OutstandingModel>>() {
            @Override
            public void onResponse(Call<List<OutstandingModel>> call, Response<List<OutstandingModel>> response) {
                new SQLiteOutstandingHandler(context).deleteAll();
                SystemClock.sleep(100);
                new SQLiteOutstandingHandler(context).addOutstandingList(response.body());
            }

            @Override
            public void onFailure(Call<List<OutstandingModel>> call, Throwable t) {

            }
        });
    }
}
