package com.example.salinda.salseforseautomation.Other;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import com.example.salinda.salseforseautomation.SQLite.SQLiteOrderHandler;
import com.example.salinda.salseforseautomation.SQLite.SQLiteOrderItemHandler;
import com.example.salinda.salseforseautomation.model.OrderArrayModel;
import com.example.salinda.salseforseautomation.model.PreOrderPostModel;
import com.example.salinda.salseforseautomation.model.SQLiteOrderModel;
import com.example.salinda.salseforseautomation.model.VanOrderPostModel;
import com.example.salinda.salseforseautomation.rest.ApiClient;
import com.example.salinda.salseforseautomation.rest.ApiInterface;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BackgroundService extends IntentService {
    ApiInterface apiInterface;

    public BackgroundService() {
        super("BackgroundService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i("Tag ", "Start Sleep");
        SystemClock.sleep(5000);
        Log.i("Tag ", "End Sleep");

        while (true){
            Log.i("Tag ", "Sleep");
            SystemClock.sleep(10000);
            int size = new SQLiteOrderHandler(getApplicationContext()).getAllOrder().size();
            if(new ConnectionDetector(getApplicationContext()).isConnected() && size!=0){
                List<SQLiteOrderModel> orderModels = new SQLiteOrderHandler(getApplicationContext()).getAllOrder();
                Log.i("Tag ", "Background Service ");
                Log.i("Tag ", "Before Delete"+String.valueOf(new SQLiteOrderHandler(getApplicationContext()).getAllOrder().size()));
                for(int i = 0; i<orderModels.size(); i++){
                    List<OrderArrayModel> orderItemArray = new SQLiteOrderItemHandler(getApplicationContext()).getProductItemByOrderId(orderModels.get(i).getId());

                    if(orderModels.get(i).getOrderType().equals("PreOrder")){
                        PreOrderPostModel preOrderPostModel = new PreOrderPostModel(orderModels.get(i).getDate(), orderModels.get(i).getDescription(),
                                orderModels.get(i).getDeliveryDate(), orderModels.get(i).getUserId(), orderModels.get(i).getOutletId(),
                                orderModels.get(i).getLongitude(), orderModels.get(i).getLatitude(), orderItemArray);

                        preOrderPostMethod(preOrderPostModel, orderModels.get(i).getId());
                    }else {
                        VanOrderPostModel vanOrderPostModel = new VanOrderPostModel(orderModels.get(i).getDate(), orderModels.get(i).getDescription(),
                                orderModels.get(i).getUserId(), orderModels.get(i).getOutletId(), orderModels.get(i).getLongitude(),
                                orderModels.get(i).getLatitude(), orderModels.get(i).getPaymentType(), orderModels.get(i).getAmount(), orderItemArray);

                        vanOrderPostMethod(vanOrderPostModel, orderModels.get(i).getId());
                    }
                }
                Log.i("Tag ", "After Delete"+String.valueOf(new SQLiteOrderHandler(getApplicationContext()).getAllOrder().size()));
            }
        }
    }

    public void preOrderPostMethod(PreOrderPostModel postModel, final int id){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiInterface.postPreOrderPayment(postModel);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code()==204){
                    new SQLiteOrderHandler(getApplicationContext()).deleteById(id);
                    new SQLiteOrderItemHandler(getApplicationContext()).deleteByOrderId(id);
                    Log.i("Tag ", "Deleted");
                }
                Log.i("Tag ", String.valueOf(response.code()));
                Log.i("Tag ", "Just Delete"+String.valueOf(new SQLiteOrderHandler(getApplicationContext()).getAllOrder().size()));
                //Toast.makeText(getApplicationContext(),response.message() , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //Toast.makeText(getApplicationContext(),"Request fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void vanOrderPostMethod(VanOrderPostModel postModel, final int id){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiInterface.postVanOrderPayment(postModel);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code()==204){
                    new SQLiteOrderHandler(getApplicationContext()).deleteById(id);
                    new SQLiteOrderItemHandler(getApplicationContext()).deleteByOrderId(id);
                }
                //Toast.makeText(getApplicationContext(),response.message() , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //Toast.makeText(getApplicationContext(),"Request fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
