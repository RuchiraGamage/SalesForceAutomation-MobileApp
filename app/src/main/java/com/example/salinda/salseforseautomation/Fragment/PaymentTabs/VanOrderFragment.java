package com.example.salinda.salseforseautomation.Fragment.PaymentTabs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.salinda.salseforseautomation.Fragment.BillingProcessTabs.SummeryFragment;
import com.example.salinda.salseforseautomation.Other.ConnectionDetector;
import com.example.salinda.salseforseautomation.Other.ShowAlert;
import com.example.salinda.salseforseautomation.R;
import com.example.salinda.salseforseautomation.SQLite.SQLiteOutstandingHandler;
import com.example.salinda.salseforseautomation.activity.BillingTabViewActivity;
import com.example.salinda.salseforseautomation.activity.PaymentTabViewActivity;
import com.example.salinda.salseforseautomation.model.OrderArrayModel;
import com.example.salinda.salseforseautomation.model.PQModel;
import com.example.salinda.salseforseautomation.model.VanOrderPostModel;
import com.example.salinda.salseforseautomation.rest.ApiClient;
import com.example.salinda.salseforseautomation.rest.ApiInterface;
import com.example.salinda.salseforseautomation.session.ItemSession;
import com.example.salinda.salseforseautomation.session.SessionHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VanOrderFragment extends Fragment {
    View myFragmentView;
    Date dateTime;
    String discriptions, paymentType = "cash";
    float Longitude, Latitude;
    float billAmount, paymentAmount;
    TextView amount, outstanding, totalAmount;
    List<OrderArrayModel> paymentProductModel;
    ArrayList<PQModel> order;
    Button accept, cancle;
    private ApiInterface apiInterface;
    ProgressDialog progressDialog;
    EditText discription,payment;
    ShowAlert alert;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myFragmentView =  inflater.inflate(R.layout.vanorder_payment, container, false);

        RadioGroup radioGroup = (RadioGroup) myFragmentView .findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                switch(checkedId) {
                    case R.id.cash:
                        paymentType = "cash";
                        break;
                    case R.id.cheque:
                        paymentType = "cheque";
                        break;
                }
            }
        });

        Bundle bundle = getActivity().getIntent().getExtras();
        order = (ArrayList<PQModel>)bundle.getSerializable(SummeryFragment.KEY_ORDER);

        paymentProductModel = new ArrayList<>();
        for(int i=0; i<order.size(); i++){
            OrderArrayModel paymentModel = new OrderArrayModel(order.get(i).getProductId(), order.get(i).getQuantity(), order.get(i).getOrderType());
            paymentProductModel.add(i, paymentModel);
        }
        outstanding = (TextView) getActivity().findViewById(R.id.outstanding);
        totalAmount = (TextView) getActivity().findViewById(R.id.total_amount);

        billAmount = bundle.getFloat(SummeryFragment.KEY_ORDER_AMOUNT);
        amount = (TextView)myFragmentView.findViewById(R.id.amount);
        amount.setText("Rs. "+String.valueOf(billAmount));

        setOutstanding();

        discription = (EditText) myFragmentView.findViewById(R.id.discription);
        payment = (EditText) myFragmentView.findViewById(R.id.payment);
        payment.setInputType(InputType.TYPE_CLASS_PHONE);
        final SessionHandler sessionHandler = new SessionHandler(getActivity());
        final ItemSession itemSession = new ItemSession(getActivity());

        accept = (Button) myFragmentView.findViewById(R.id.accept);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateTime = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = df.format(dateTime);

                try{
                    discriptions = discription.getText().toString();
                }catch (Exception e){

                }

                try{
                    paymentAmount = Float.valueOf(payment.getText().toString());
                }catch (Exception e){
                    if(paymentProductModel.isEmpty() && !(new SessionHandler(getActivity()).isOnclicked())){
                        Toast.makeText(getActivity(),"Empty Bill...", Toast.LENGTH_SHORT).show();
                        SystemClock.sleep(1000);
                        Intent intent = new Intent(getActivity(), BillingTabViewActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        getActivity().finish();
                    }else {
                        if(new SessionHandler(getActivity()).isOnclicked()){
                            Toast.makeText(getActivity(),"Swipe Pre Order Tab or Click Cancel", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getActivity(),"Place Enter Valid Payment Amount", Toast.LENGTH_SHORT).show();
                        }
                        return;
                    }
                }

                int userId = sessionHandler.getUserDetails().getId();
                int outletId = itemSession.getOutletId();

                VanOrderPostModel postModel = new VanOrderPostModel(formattedDate, discriptions, userId, outletId, 0.00f, 0.00f, paymentType, paymentAmount, paymentProductModel);

//                if(buttonClick == true){
//                    Toast.makeText(getActivity(),"Move PreOrderTab or Click Cancel", Toast.LENGTH_SHORT).show();
//                }

                if(paymentProductModel.isEmpty() && !(new SessionHandler(getActivity()).isOnclicked())){
                    Toast.makeText(getActivity(),"Empty Bill...", Toast.LENGTH_SHORT).show();
                    SystemClock.sleep(1000);
                    Intent intent = new Intent(getActivity(), BillingTabViewActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    getActivity().finish();
                }else {
                    postOrder(postModel);
                    paymentProductModel.clear();
                    payment.setText("");
                    amount.setText("");
                    discription.setText("");
                    outstanding.setText("");
                    totalAmount.setText("");
                    new SessionHandler(getActivity()).createOnclickSession(true);

                }

            }
        });
        cancle = (Button) myFragmentView.findViewById(R.id.cancel);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!paymentProductModel.isEmpty()){
                    ShowAlert alert = new ShowAlert(getActivity());
                    alert.BillCancelAlert(true);
                }else {
                    ((PaymentTabViewActivity)getActivity()).BillCancel(getActivity());
                }
            }
        });

        return myFragmentView;
    }

    public void setOutstanding(){
        ItemSession itemSession = new ItemSession(getActivity());
        if(new ConnectionDetector(getActivity()).isConnected()){
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Log.i("Tag ", String.valueOf(itemSession.getOutletId()));
            Call<Float> call = apiInterface.getLatestOutstanding(itemSession.getOutletId() );
            call.enqueue(new Callback<Float>() {
                @Override
                public void onResponse(Call<Float> call, Response<Float> response) {
                    Toast.makeText(getActivity(), String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                    if(response.code()==200){
                        progressDialog.dismiss();
                        fetchData(response.body());
                    }else {
                        progressDialog.dismiss();
                        fetchData(new SQLiteOutstandingHandler(getActivity()).getOutstandingByOutletId(new ItemSession(getActivity()).getOutletId()));
                    }
                }

                @Override
                public void onFailure(Call<Float> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(),"Outstanding Fail...", Toast.LENGTH_SHORT).show();
                    fetchData(new SQLiteOutstandingHandler(getActivity()).getOutstandingByOutletId(new ItemSession(getActivity()).getOutletId()));
                }
            });
        }else {
            Toast.makeText(getActivity(),"Waiting for Network", Toast.LENGTH_SHORT).show();
            fetchData(new SQLiteOutstandingHandler(getActivity()).getOutstandingByOutletId(new ItemSession(getActivity()).getOutletId()));
        }
    }

    public void fetchData(float out){
        Log.i("Tag ", String.valueOf(out));
        totalAmount = (TextView) getActivity().findViewById(R.id.total_amount);
        outstanding = (TextView) getActivity().findViewById(R.id.outstanding);
        outstanding.setText("Rs. "+String.valueOf(out));
        if(billAmount != 0.0f){
            totalAmount.setText("Rs. "+String.valueOf(billAmount + out));
        }
    }

    public void postOrder(VanOrderPostModel postModel){
//        Log.i("Tag Date", String.valueOf(postModel.getDate()));
//        Log.i("Tag Description", String.valueOf(postModel.getDescription()));
//        Log.i("Tag UserId", String.valueOf(postModel.getUserId()));
//        Log.i("Tag OutletId", String.valueOf(postModel.getOutletId()));
//        Log.i("Tag Longitude", String.valueOf(postModel.getLongitude()));
//        Log.i("Tag Latitude", String.valueOf(postModel.getLatitude()));
//        Log.i("Tag PaymentType", String.valueOf(postModel.getPaymentType()));
//        Log.i("Tag Amount", String.valueOf(postModel.getAmount()));
//        Log.i("Tag Array Size", String.valueOf(postModel.getArray().size()));
//        Log.i("Tag OrderType", String.valueOf(postModel.getArray().get(0).getOrderType()));
//        Log.i("Tag ProductId", String.valueOf(postModel.getArray().get(0).getProductId()));
//        Log.i("Tag ProductQuantity", String.valueOf(postModel.getArray().get(0).getProductQuantity()));
        alert = new ShowAlert(getActivity());
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Waiting...");
        progressDialog.show();
        if(new ConnectionDetector(getActivity()).isConnected()){
            apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Log.i("Tag ", String.valueOf(postModel.getArray().size()));
            Call<ResponseBody> call = apiInterface.postVanOrderPayment(postModel);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    progressDialog.dismiss();
                    if(response.code()==204){
                        //Toast.makeText(getActivity(),"Order Successful" , Toast.LENGTH_SHORT).show();
                        alert.ShowLoginAlert("Order Successful", "Order Successfully Placed", true);
                    }else {
                        //Toast.makeText(getActivity(),"Order Successful..." , Toast.LENGTH_SHORT).show();
                        alert.ShowLoginAlert("Order Fail...", "Order Fail Place Try Again", false);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    progressDialog.dismiss();
                    //Toast.makeText(getActivity(),"Order Fail", Toast.LENGTH_SHORT).show();
                    alert.ShowLoginAlert("Request fail..", "Order Will be Send", false);
                }
            });
        }else {
            //Toast.makeText(getActivity(),"Waiting for Network", Toast.LENGTH_SHORT).show();
            alert.ShowLoginAlert("You are in Offline Condition", "Order Will be Send", false);
        }
    }
}
