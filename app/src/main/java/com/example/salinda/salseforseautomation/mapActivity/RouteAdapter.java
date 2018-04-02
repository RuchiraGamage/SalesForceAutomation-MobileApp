package com.example.salinda.salseforseautomation.mapActivity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.salinda.salseforseautomation.Other.Animation;
import com.example.salinda.salseforseautomation.Other.ConnectionDetector;
import com.example.salinda.salseforseautomation.R;
import com.example.salinda.salseforseautomation.model.RouteModel;
import com.example.salinda.salseforseautomation.rest.ApiClient;
import com.example.salinda.salseforseautomation.rest.ApiInterface;
import com.example.salinda.salseforseautomation.session.SessionHandler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.MyViewHolder> implements Filterable{
    public static final String KEY_OUTLETS = "Outlets";

    Context context;
    List<RouteModel> routeModels;
    List<RouteModel> filteredRouteModel;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView route;
        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            route = (TextView) itemView.findViewById(R.id.route_name);
            image = (ImageView) itemView.findViewById(R.id.map_icon);
        }
    }

    public RouteAdapter(Context context, List<RouteModel> routeModels){
        this.context = context;
        this.routeModels = routeModels;
        this.filteredRouteModel = routeModels;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.route_row_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.route.setText(filteredRouteModel.get(position).getName());
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Animation().onClickAnimation(v);
                if(!(new SessionHandler(context).isOnclicked())){
                    startMap(filteredRouteModel.get(position).getId());
                    new SessionHandler(context).createOnclickSession(true);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredRouteModel.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredRouteModel = routeModels;
                } else {
                    List<RouteModel> filteredList = new ArrayList<>();
                    for (RouteModel row : routeModels) {

                        // name match condition. this might differ depending on your requirement
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    filteredRouteModel = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredRouteModel;
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

    private void startMap(int routeId){
        if(new ConnectionDetector(context).isConnected()){
            ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Call<List<LocationModel>> call = apiInterface.getOutletOfTheRoute(routeId);
            call.enqueue(new Callback<List<LocationModel>>() {
                @Override
                public void onResponse(Call<List<LocationModel>> call, Response<List<LocationModel>> response) {
                    if(response.code()==200){
                        Log.i("Tag ", "Data Loaded");
                        List<LocationModel> locationModel = response.body();
                        Intent intent = new Intent(context, Direction.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(KEY_OUTLETS, (Serializable) locationModel);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }else {
                        Toast.makeText(context,"System Error", Toast.LENGTH_SHORT).show();
                    }
                    new SessionHandler(context).createOnclickSession(false);
                }

                @Override
                public void onFailure(Call<List<LocationModel>> call, Throwable t) {
                    Toast.makeText(context,"Request fail", Toast.LENGTH_SHORT).show();
                    new SessionHandler(context).createOnclickSession(false);
                }
            });
        }else {
            Toast.makeText(context,"Waiting for Network", Toast.LENGTH_SHORT).show();
            new SessionHandler(context).createOnclickSession(false);
        }
    }
}
