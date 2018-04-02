package com.example.salinda.salseforseautomation.adapter;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.example.salinda.salseforseautomation.Other.Animation;
import com.example.salinda.salseforseautomation.Other.ConnectionDetector;
import com.example.salinda.salseforseautomation.R;
import com.example.salinda.salseforseautomation.ReportActivities.OutletLastYearSales;
import com.example.salinda.salseforseautomation.ReportActivities.OutletMonthSales;
import com.example.salinda.salseforseautomation.ReportActivities.ReportActivity;
import com.example.salinda.salseforseautomation.model.MonthlyReportModel;
import com.example.salinda.salseforseautomation.model.OutletModel;
import com.example.salinda.salseforseautomation.rest.ApiClient;
import com.example.salinda.salseforseautomation.rest.ApiInterface;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportOutletSelectorAdaptor extends RecyclerView.Adapter<ReportOutletSelectorAdaptor.MyViewHolder> implements Filterable {
    private List<OutletModel> outletModels;
    private List<OutletModel> outletModelFiltered;
    Context context;
    int id;
    String month,year;
    public static final String KEY_OUTLETID = "outletId";
    public static final String KEY_OUTLET_NAME = "outletName";
    public static final String KEY_OUTLET_GRAPH = "OutletData";
    ProgressDialog progressDialog;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,ownerName,contactNo;
        CardView card;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.Name);
            ownerName = (TextView) itemView.findViewById(R.id.OwnerName);
            card = (CardView) itemView.findViewById(R.id.outlet_card);

        }
    }

    public ReportOutletSelectorAdaptor(Context context, List<OutletModel> outletModels, int id, String month, String year){
        this.context = context;
        this.outletModels = outletModels;
        this.outletModelFiltered = outletModels;
        this.id = id;
        this.month = month;
        this.year = year;

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
        holder.card.setOnClickListener(new CardView.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Animation().onClickAnimation(v);
                if(id==1){
                    outletMonthlyData(outletModels.get(position).getId(), outletModels.get(position).getName());
                }
                if(id==2){
                    Intent intent = new Intent(context, OutletMonthSales.class);
                    intent.putExtra(KEY_OUTLETID, outletModels.get(position).getId());
                    intent.putExtra(ReportActivity.KEY_MONTH, month);
                    intent.putExtra(ReportActivity.KEY_YEAR, year);
                    intent.putExtra(KEY_OUTLET_NAME, outletModels.get(position).getName());
                    context.startActivity(intent);
                }
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

    private void outletMonthlyData(int outletId, final String outletName){
        if(new ConnectionDetector(context).isConnected()){
//            progressDialog = new ProgressDialog(Activity(ReportOutletSelectorActivity));
//            progressDialog.setMessage("Loading...");
//            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Call<List<MonthlyReportModel>> call = apiInterface.getOutletsMonthlyReport(outletId);
            call.enqueue(new Callback<List<MonthlyReportModel>>() {
                @Override
                public void onResponse(Call<List<MonthlyReportModel>> call, Response<List<MonthlyReportModel>> response) {
                    if(response.code()==200){
                        //progressDialog.dismiss();
                        Intent intent = new Intent(context, OutletLastYearSales.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(KEY_OUTLET_GRAPH, (Serializable) response.body());
                        intent.putExtra(KEY_OUTLET_NAME, outletName);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }else {
                       // progressDialog.dismiss();
                        Toast.makeText(context,"System Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<MonthlyReportModel>> call, Throwable t) {
                    //progressDialog.dismiss();
                    Toast.makeText(context,"Request fail.. Try Again", Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            Toast.makeText(context,"Waiting for Network", Toast.LENGTH_SHORT).show();
        }
    }

}
