package com.example.salinda.salseforseautomation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.salinda.salseforseautomation.R;
import com.example.salinda.salseforseautomation.model.MonthReportModel;

import java.util.ArrayList;
import java.util.List;

public class MonthReportAdapter extends RecyclerView.Adapter<MonthReportAdapter.MyViewHolder> implements Filterable {
    private List<MonthReportModel> monthReportModels;
    private List<MonthReportModel> monthReportModelsFiltered;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, brand, price, quantity;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.Product);
            brand = (TextView) itemView.findViewById(R.id.BrandName);
            price = (TextView) itemView.findViewById(R.id.Price);
            quantity = (TextView) itemView.findViewById(R.id.Quantity);
        }
    }

    public MonthReportAdapter(Context context, List<MonthReportModel> monthReportModels) {
        this.context = context;
        this.monthReportModels = monthReportModels;
        this.monthReportModelsFiltered = monthReportModels;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.month_report_row_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.name.setText(monthReportModelsFiltered.get(position).getName());
        holder.brand.setText(monthReportModelsFiltered.get(position).getBrand());
        holder.price.setText("Rs. "+Float.toString(monthReportModelsFiltered.get(position).getAmount()));
        holder.quantity.setText("Qt. "+Integer.toString(monthReportModelsFiltered.get(position).getTotalQuantity()));
    }

    @Override
    public int getItemCount() {
        return monthReportModelsFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    monthReportModelsFiltered = monthReportModels;
                } else {
                    List<MonthReportModel> filteredList = new ArrayList<>();
                    for (MonthReportModel row : monthReportModels) {

                        // name match condition. this might differ depending on your requirement
                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getBrand().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    monthReportModelsFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = monthReportModelsFiltered;
                //Toast.makeText(context,productsModelFiltered.size(), Toast.LENGTH_SHORT).show();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                //productsModelFiltered = (ArrayList<ProductsModel>) filterResults.values;
                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }

}
