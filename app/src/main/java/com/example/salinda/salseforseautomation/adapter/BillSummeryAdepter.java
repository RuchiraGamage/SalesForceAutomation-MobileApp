package com.example.salinda.salseforseautomation.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.salinda.salseforseautomation.Fragment.BillingProcessTabs.SummeryFragment;
import com.example.salinda.salseforseautomation.Other.Animation;
import com.example.salinda.salseforseautomation.R;
import com.example.salinda.salseforseautomation.model.PQModel;

import java.util.List;

public class BillSummeryAdepter extends RecyclerView.Adapter<BillSummeryAdepter.MyViewHolder>{
    private List<PQModel> pqModels;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, brand, price,orderType;
        EditText quantity;
        CardView card;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.Product);
            brand = (TextView) itemView.findViewById(R.id.BrandName);
            price = (TextView) itemView.findViewById(R.id.Price);
            orderType = (TextView) itemView.findViewById(R.id.OrderType);
            quantity = (EditText) itemView.findViewById(R.id.Quantity);
            card = (CardView) itemView.findViewById(R.id.summery_card);
        }

        public String getDiplayedQuantity(){
            return quantity.getText().toString();
        }
    }

    public BillSummeryAdepter(Context context, List<PQModel> pqModels) {
        this.context = context;
        this.pqModels = pqModels;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_summery_row_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final PQModel pqModel = pqModels.get(position);
        holder.name.setText(pqModel.getName());
        holder.brand.setText(pqModel.getBrand());
        holder.price.setText(Float.toString(pqModel.getPrice()*pqModel.getQuantity()));
        holder.orderType.setText(pqModel.getOrderType());
        holder.quantity.setText(Integer.toString(pqModel.getQuantity()));
        holder.quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try{
                    pqModels.get(position).setQuantity(Integer.parseInt(String.valueOf(s)));
                    holder.price.setText(Float.toString(pqModel.getPrice()*pqModel.getQuantity()));
                }catch (Exception e){

                }
                new SummeryFragment.Iner().setQuantity(position);
            }
        });
        holder.quantity.setInputType(InputType.TYPE_CLASS_PHONE);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Animation().onClickAnimation(v);
            }
        });
        holder.card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new Animation().onLongClickAnimation(v);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {return pqModels.size();}

}
