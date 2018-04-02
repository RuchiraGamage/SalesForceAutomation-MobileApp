package com.example.salinda.salseforseautomation.Fragment.BillingProcessTabs;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.salinda.salseforseautomation.Other.RecyclerViewClickListener;
import com.example.salinda.salseforseautomation.Other.RecyclerViewTouchListener;
import com.example.salinda.salseforseautomation.Other.ShowAlert;
import com.example.salinda.salseforseautomation.R;
import com.example.salinda.salseforseautomation.activity.BillingTabViewActivity;
import com.example.salinda.salseforseautomation.activity.OutletSelectorActivity;
import com.example.salinda.salseforseautomation.activity.PaymentTabViewActivity;
import com.example.salinda.salseforseautomation.adapter.BillSummeryAdepter;
import com.example.salinda.salseforseautomation.model.PQModel;
import com.example.salinda.salseforseautomation.session.ItemSession;
import com.example.salinda.salseforseautomation.session.SessionHandler;

import java.util.ArrayList;

public class SummeryFragment extends Fragment {
    public static final String KEY_PREORDER = "Pre Order";
    public static final String KEY_ORDER = "Order";
    public static final String KEY_PREORDER_AMOUNT = "Pre Order Amount";
    public static final String KEY_ORDER_AMOUNT = "Order Amount";
    private LinearLayout linerLayout;
    private static RecyclerView preOrder_RecyclerView;
    private static RecyclerView order_RecyclerView;
    private ShowAlert alert;
    BillSummeryAdepter adepter;
    ArrayList<PQModel> pqModels;
    static ArrayList<PQModel> preOrder;
    static ArrayList<PQModel> order;
    static ItemSession itemSession;
    static TextView preOrderAmount;
    static TextView orderAmount;
    TextView price;
    EditText edit_Quantity;
    Button accept,cancle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View myFragmentView =  inflater.inflate(R.layout.bill_summery, container, false);

        itemSession = new ItemSession(getActivity());
        linerLayout = (LinearLayout) myFragmentView.findViewById(R.id.linerLayout);

        int preOredrCount=0,orderCount=0;
        pqModels = (ArrayList<PQModel>) itemSession.getAllItem();
        preOrder = new ArrayList<>();
        order = new ArrayList<>();
        for(int i=0; i<itemSession.getItemCount(); i++){
            PQModel pqModel = new PQModel(pqModels.get(i).getProductId(), pqModels.get(i).getQuantity(), pqModels.get(i).getOrderType(),
                    pqModels.get(i).getName(), pqModels.get(i).getBrand(), pqModels.get(i).getPrice());
            if(pqModels.get(i).getOrderType().equals(BillingTabViewActivity.KEY_PREORDER)){
                preOrder.add(preOredrCount, pqModel);
                preOredrCount++;
            }else {
                order.add(orderCount, pqModel);
                orderCount++;
            }
        }

        preOrderAmount = (TextView) myFragmentView.findViewById(R.id.preorder_total);
        preOrderAmount.setText("Rs. "+String.valueOf(totalAmount(preOrder)));

        orderAmount = (TextView) myFragmentView.findViewById(R.id.van_order_total);
        orderAmount.setText("Rs. "+String.valueOf(totalAmount(order)));

        preOrder_RecyclerView = (RecyclerView)myFragmentView.findViewById(R.id.preorder_bill);
        preOrder_RecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        preOrder_RecyclerView.setHasFixedSize(true);

        try{
            adepter = new BillSummeryAdepter(getActivity(), preOrder);
            preOrder_RecyclerView.setAdapter(adepter);
        }catch (Exception e){

        }
        preOrder_RecyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getActivity(), preOrder_RecyclerView, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, final int position) {

            }

            @Override
            public void onLongClick(View view, final int position) {
                Snackbar snackbar = Snackbar.make(linerLayout, preOrder.get(position).getName(), Snackbar.LENGTH_LONG);
                snackbar.setAction("DELETE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(getActivity(),"Item found", Toast.LENGTH_SHORT).show();
                        try{
                            preOrder.remove(position);
                            adepter = new BillSummeryAdepter(getActivity(), preOrder);
                            preOrder_RecyclerView.setAdapter(adepter);
                            preOrderAmount.setText("Rs. "+String.valueOf(totalAmount(preOrder)));
                            itemSession.editItemSession(preOrder, order);
                        }catch (Exception e){

                        }
                    }
                });
                snackbar.setActionTextColor(Color.RED);          // Changing message text color
                View sbView = snackbar.getView();           // Changing action button text color
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.YELLOW);
                snackbar.show();            //show snackbar
            }
        }));

        order_RecyclerView = (RecyclerView)myFragmentView.findViewById(R.id.van_order_bill);
        order_RecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        order_RecyclerView.setHasFixedSize(true);

        try{
            adepter = new BillSummeryAdepter(getActivity(), order);
            order_RecyclerView.setAdapter(adepter);
        }catch (Exception e){

        }
        order_RecyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getActivity(), order_RecyclerView, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {}

            @Override
            public void onLongClick(View view, final int position) {
                Snackbar snackbar = Snackbar.make(linerLayout, order.get(position).getName() , Snackbar.LENGTH_LONG);
                snackbar.setAction("DELETE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(getActivity(),"Item found", Toast.LENGTH_SHORT).show();
                        order.remove(position);
                        adepter = new BillSummeryAdepter(getActivity(), order);
                        order_RecyclerView.setAdapter(adepter);
                        orderAmount.setText("Rs. "+String.valueOf(totalAmount(order)));
                        itemSession.editItemSession(preOrder, order);

                    }
                });
                snackbar.setActionTextColor(Color.RED);          // Changing message text color
                View sbView = snackbar.getView();           // Changing action button text color
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.YELLOW);
                snackbar.show();            //show snackbar
            }
        }));
        return myFragmentView;
    }

    public static class Iner{
        public void setQuantity(int position) {
            BillSummeryAdepter.MyViewHolder preOrderChildHolder = (BillSummeryAdepter.MyViewHolder) preOrder_RecyclerView.findViewHolderForLayoutPosition(position);
            BillSummeryAdepter.MyViewHolder orderChildHolder = (BillSummeryAdepter.MyViewHolder) order_RecyclerView.findViewHolderForLayoutPosition(position);
           try{
               order.get(position).setQuantity(Integer.parseInt(orderChildHolder.getDiplayedQuantity()));
               orderAmount.setText("Rs. "+String.valueOf(totalAmount(order)));
           }catch (Exception e){

           }
           try{
               preOrder.get(position).setQuantity(Integer.parseInt(preOrderChildHolder.getDiplayedQuantity()));
               preOrderAmount.setText("Rs. "+String.valueOf(totalAmount(preOrder)));
           }catch (Exception e){

           }
            itemSession.editItemSession(preOrder, order);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        accept = (Button) getView().findViewById(R.id.accept);
        accept.setClickable(true);
        cancle = (Button) getView().findViewById(R.id.cancel);
        cancle.setClickable(true);

        accept.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                new SessionHandler(getActivity()).createOnclickSession(false);
                Intent intent = new Intent(getActivity(), PaymentTabViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(KEY_PREORDER, preOrder);
                bundle.putSerializable(KEY_ORDER, order);
                intent.putExtras(bundle);
                intent.putExtra(KEY_PREORDER_AMOUNT, totalAmount(preOrder));
                intent.putExtra(KEY_ORDER_AMOUNT, totalAmount(order));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finish();
            }
        });
        cancle.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                ItemSession itemSession = new ItemSession(getActivity());
                itemSession.clearItemSession();
                Intent intent = new Intent(getActivity(), OutletSelectorActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finish();

            }
        });
    }

    public static float totalAmount(ArrayList<PQModel> pqModels){
        float amount=0;
        for(int i=0; i<pqModels.size(); i++){
            if(pqModels.get(i).getOrderType().equals("Return")){
                amount = amount - (pqModels.get(i).getPrice())*(pqModels.get(i).getQuantity());
            }else {
                amount = amount + (pqModels.get(i).getPrice())*(pqModels.get(i).getQuantity());
            }
        }
        return amount;
    }
}

