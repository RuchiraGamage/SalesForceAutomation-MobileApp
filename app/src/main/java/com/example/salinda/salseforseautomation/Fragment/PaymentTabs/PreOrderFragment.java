package com.example.salinda.salseforseautomation.Fragment.PaymentTabs;


import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.salinda.salseforseautomation.Fragment.BillingProcessTabs.SummeryFragment;
import com.example.salinda.salseforseautomation.Other.ConnectionDetector;
import com.example.salinda.salseforseautomation.Other.ShowAlert;
import com.example.salinda.salseforseautomation.R;
import com.example.salinda.salseforseautomation.SQLite.SQLiteOrderHandler;
import com.example.salinda.salseforseautomation.SQLite.SQLiteOrderItemHandler;
import com.example.salinda.salseforseautomation.activity.BillingTabViewActivity;
import com.example.salinda.salseforseautomation.activity.PaymentTabViewActivity;
import com.example.salinda.salseforseautomation.model.OrderArrayModel;
import com.example.salinda.salseforseautomation.model.PQModel;
import com.example.salinda.salseforseautomation.model.PreOrderPostModel;
import com.example.salinda.salseforseautomation.model.SQLiteOrderItemModel;
import com.example.salinda.salseforseautomation.model.SQLiteOrderModel;
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

public class PreOrderFragment extends Fragment {
    Date dateTime;
    EditText discription,delivery_Date;
    TextView amount;
    Button accept,cancle;
    String discriptions,deliveryDate;
    ArrayList<PQModel> preOrder;
    List<OrderArrayModel> paymentProductModel;
    PreOrderPostModel preOrderPostModel;
    private ApiInterface apiInterface;
    ProgressDialog progressDialog;
    ShowAlert alert;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View myFragmentView =  inflater.inflate(R.layout.preorder_payment, container, false);

        Bundle bundle = getActivity().getIntent().getExtras();
        preOrder = (ArrayList<PQModel>)bundle.getSerializable(SummeryFragment.KEY_PREORDER);
        //Toast.makeText(getActivity(),preOrder.get(5).getName(), Toast.LENGTH_SHORT).show();

        amount = (TextView)myFragmentView.findViewById(R.id.amount);
        amount.setText("Rs. "+String.valueOf(bundle.getFloat(SummeryFragment.KEY_PREORDER_AMOUNT)));

        paymentProductModel = new ArrayList<>();
        for(int i=0; i<preOrder.size(); i++){
            OrderArrayModel paymentModel = new OrderArrayModel(preOrder.get(i).getProductId(), preOrder.get(i).getQuantity(), preOrder.get(i).getOrderType());
            paymentProductModel.add(i, paymentModel);
        }

        discription = (EditText) myFragmentView.findViewById(R.id.discription);
        delivery_Date = (EditText)myFragmentView.findViewById(R.id.date);
        delivery_Date.setInputType(InputType.TYPE_CLASS_DATETIME);
        final SessionHandler sessionHandler = new SessionHandler(getActivity());
        final ItemSession itemSession = new ItemSession(getActivity());

        accept = (Button)myFragmentView.findViewById(R.id.accept);
        accept.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                dateTime = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd/HH-mm-ss");
                String formattedDate = df.format(dateTime);

                try{
                    discriptions = discription.getText().toString();
                }catch (Exception e){

                }

                try{
                    deliveryDate = delivery_Date.getText().toString();
                }catch (Exception e){

                }

                int userId = sessionHandler.getUserDetails().getId();
                int outletId = itemSession.getOutletId();
//                Location location = ((Direction)getActivity()).getCurrentLocation();
                preOrderPostModel = new PreOrderPostModel(formattedDate, discriptions, deliveryDate, userId, outletId, 0.00f, 0.00f, paymentProductModel);

                if(paymentProductModel.isEmpty()){
                    Toast.makeText(getActivity(),"Empty Bill...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), BillingTabViewActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    getActivity().finish();
                } else {
                    postOrder(preOrderPostModel);
                    paymentProductModel.clear();
                    amount.setText("Rs. 0.00");
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



    public void postOrder(final PreOrderPostModel postModel){
        alert = new ShowAlert(getActivity());
        if(new ConnectionDetector(getActivity()).isConnected()){
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Waiting...");
            progressDialog.show();
            apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Call<ResponseBody> call = apiInterface.postPreOrderPayment(postModel);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    progressDialog.dismiss();
                    if(response.code()==204){
                        //Toast.makeText(getActivity(),"Order Successful" , Toast.LENGTH_SHORT).show();
                        alert.ShowLoginAlert("Order Successful", "Order Successfully Placed", true);
                    }else {
                        //Toast.makeText(getActivity(),"Order Fail... Place Try Again" , Toast.LENGTH_SHORT).show();
                        alert.ShowLoginAlert("Order Fail...", "Order Fail Place Try Again", false);
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    progressDialog.dismiss();
                    insertSQLite(postModel);
                    //Toast.makeText(getActivity(),"Request fail.. Order Will be Send", Toast.LENGTH_SHORT).show();
                    alert.ShowLoginAlert("Request fail..", "Order Will be Send", false);
//                    SystemClock.sleep(3000);
//                    new ItemSession(getActivity()).clearItemSession();
//                    paymentProductModel.clear();
//                    discription.setText("");
//                    amount.setText("Rs. 0.00");
//                    delivery_Date.setText("");
//                    Intent intent = new Intent(getActivity(), OutletSelectorActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(intent);
//                    getActivity().finish();
                }
            });
        }else {
            insertSQLite(postModel);
            //Toast.makeText(getActivity(),"Order Will be Send", Toast.LENGTH_SHORT).show();
            alert.ShowLoginAlert("You are in Offline Condition", "Order Will be Send", false);
//            new ItemSession(getActivity()).clearItemSession();
//            paymentProductModel.clear();
//            discription.setText("");
//            amount.setText("Rs. 0.00");
//            delivery_Date.setText("");
//            Intent intent = new Intent(getActivity(), OutletSelectorActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);
//            getActivity().finish();
        }
    }

    private void insertSQLite(PreOrderPostModel postModel){
        SQLiteOrderHandler orderHandler = new SQLiteOrderHandler(getActivity());
        SQLiteOrderModel orderModel = new SQLiteOrderModel(0, postModel.getDate(), postModel.getRemarks(), postModel.getUserId(),
                postModel.getOutletId(), postModel.getLongitude(), postModel.getLatitude(), null, 0.00f, postModel.getDeliveryDate(), "PreOrder");

        orderHandler.addOrder(orderModel);
        int id = orderHandler.getLastId(postModel.getDate());
        //Toast.makeText(getActivity(),String.valueOf(id), Toast.LENGTH_SHORT).show();

        List<SQLiteOrderItemModel> orderItemModels = new ArrayList<SQLiteOrderItemModel>();
        for(int i=0; i<postModel.getArray().size(); i++){
            SQLiteOrderItemModel itemModel = new SQLiteOrderItemModel();
            itemModel.setId(0);
            itemModel.setOrderId(id);
            itemModel.setProductId(postModel.getArray().get(i).getProductId());
            itemModel.setProductQuantity(postModel.getArray().get(i).getProductQuantity());
            itemModel.setOrderType(postModel.getArray().get(i).getOrderType());
            orderItemModels.add(itemModel);
        }
        //Toast.makeText(getActivity(),String.valueOf(orderItemModels.size()), Toast.LENGTH_SHORT).show();

        SQLiteOrderItemHandler itemHandler = new SQLiteOrderItemHandler(getActivity());
        itemHandler.addOrderItemList(orderItemModels);

    }

}
