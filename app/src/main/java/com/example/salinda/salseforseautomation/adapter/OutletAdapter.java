package com.example.salinda.salseforseautomation.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.salinda.salseforseautomation.Other.Animation;
import com.example.salinda.salseforseautomation.R;
import com.example.salinda.salseforseautomation.model.OutletModel;
import com.example.salinda.salseforseautomation.session.OutletSession;

import java.util.ArrayList;
import java.util.List;

public class OutletAdapter extends RecyclerView.Adapter<OutletAdapter.MyViewHolder> implements Filterable {
    private List<OutletModel> outletModels;
    private List<OutletModel> outletModelFiltered;
    private OutletSession outletSession;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,ownerName,contactNo;
        CardView card;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.Name);
            ownerName = (TextView) itemView.findViewById(R.id.OwnerName);
            contactNo = (TextView) itemView.findViewById(R.id.ContactNo);
            card = (CardView) itemView.findViewById(R.id.outlet_card);
        }
    }

    public OutletAdapter(Context context, List<OutletModel> outletModels){
        this.context = context;
        this.outletModels = outletModels;
        this.outletModelFiltered = outletModels;
        outletSession = new OutletSession(context);
        outletSession.clearOutletSession();
        outletSession.createOutletSession(outletModels);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.outlet_row_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final OutletModel outletModel = outletModelFiltered.get(position);
        holder.name.setText(outletModel.getName());
        holder.ownerName.setText(outletModel.getOwnerName());
        holder.contactNo.setText(outletModel.getContactNo());
        holder.card.setOnClickListener(new CardView.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Animation().onClickAnimation(v);
            }
        });
        holder.card.setOnLongClickListener(new CardView.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new Animation().onClickAnimation(v);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return outletModelFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    outletModelFiltered = outletModels;
                } else {
                    List<OutletModel> filteredList = new ArrayList<>();
                    for (OutletModel row : outletModels) {

                        // name match condition. this might differ depending on your requirement
                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getOwnerName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    outletModelFiltered = filteredList;
                }
                outletSession = new OutletSession(context);
                outletSession.clearOutletSession();
                outletSession.createOutletSession(outletModelFiltered);
                FilterResults filterResults = new FilterResults();
                filterResults.values = outletModelFiltered;
                //Toast.makeText(context,outletModelFiltered.size(), Toast.LENGTH_SHORT).show();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                //productsModelFiltered = (ArrayList<ProductsModel>) filterResults.values
                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }

}
