package com.example.salinda.salseforseautomation.Fragment.LeaveTab;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.salinda.salseforseautomation.Other.ConnectionDetector;
import com.example.salinda.salseforseautomation.R;
import com.example.salinda.salseforseautomation.adapter.MyLeavesAdapter;
import com.example.salinda.salseforseautomation.model.LeaveHistryModel;
import com.example.salinda.salseforseautomation.rest.ApiClient;
import com.example.salinda.salseforseautomation.rest.ApiInterface;
import com.example.salinda.salseforseautomation.session.SessionHandler;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyLeaves extends Fragment {
    RecyclerView recyclerView;
    MyLeavesAdapter adapter;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View myFragmentView =  inflater.inflate(R.layout.recycler_view, container, false);

        recyclerView = (RecyclerView)myFragmentView.findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setHasFixedSize(true);

        fetchData();

        return myFragmentView;
    }

    private void fetchData(){
        if(new ConnectionDetector(getActivity()).isConnected()){
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Call<List<LeaveHistryModel>> call = apiInterface.getMyLeaves(new SessionHandler(getActivity()).getUserDetails().getId());
            call.enqueue(new Callback<List<LeaveHistryModel>>() {
                @Override
                public void onResponse(Call<List<LeaveHistryModel>> call, Response<List<LeaveHistryModel>> response) {
                    if(response.code()==200){
                        progressDialog.dismiss();
                        adapter = new MyLeavesAdapter(getActivity(), response.body());
                        recyclerView.setAdapter(adapter);
                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(),"System Error.. Try Again Later", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<LeaveHistryModel>> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(),"Request Fail.. Try Again Later", Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            Toast.makeText(getActivity(),"Waiting for Network", Toast.LENGTH_SHORT).show();
        }
    }
}
