package com.example.salinda.salseforseautomation.Fragment.OutletSelectorTabs;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.salinda.salseforseautomation.Other.ConnectionDetector;
import com.example.salinda.salseforseautomation.Other.RecyclerViewClickListener;
import com.example.salinda.salseforseautomation.Other.RecyclerViewTouchListener;
import com.example.salinda.salseforseautomation.R;
import com.example.salinda.salseforseautomation.SQLite.SQLiteOutletHandler;
import com.example.salinda.salseforseautomation.activity.BillingTabViewActivity;
import com.example.salinda.salseforseautomation.adapter.OutletAdapter;
import com.example.salinda.salseforseautomation.model.OutletModel;
import com.example.salinda.salseforseautomation.rest.ApiClient;
import com.example.salinda.salseforseautomation.rest.ApiInterface;
import com.example.salinda.salseforseautomation.session.ItemSession;
import com.example.salinda.salseforseautomation.session.OutletSession;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllOutletsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ApiInterface apiInterface;
    private OutletAdapter adepter;
    private SearchView searchView;
    private OutletModel outlet;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myFragmentView =  inflater.inflate(R.layout.recyclerview_with_search, container, false);

        recyclerView = (RecyclerView)myFragmentView.findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setHasFixedSize(true);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        fetchProduct();

        searchView = (SearchView) myFragmentView.findViewById(R.id.search);
        searchView.setQueryHint("  Search  ");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                try{
                    adepter.getFilter().filter(query);
                }catch (Exception e){

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                try{
                    adepter.getFilter().filter(query);
                }catch (Exception e){}
                return false;
            }
        });

        /*searchView = (EditText) myFragmentView.findViewById(R.id.search);
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = searchView.getText().toString().toLowerCase(Locale.getDefault());
                adepter.getFilter().filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }
        });*/

        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getActivity(), recyclerView, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                ItemSession itemSession = new ItemSession(getActivity());
                OutletSession outletSession = new OutletSession(getActivity());
                itemSession.clearItemSession();
                itemSession.starItemCount();
                try{
                    outlet = outletSession.getAllOutlet().get(position);
                    itemSession.setOutletId(outlet.getId());
                }catch (Exception e){

                }
                Intent intent = new Intent(getActivity(), BillingTabViewActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finish();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        return myFragmentView;
    }

    private void fetchProduct(){
        if(new ConnectionDetector(getActivity()).isConnected()){
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            Call<List<OutletModel>> call = apiInterface.getOutlet();
            call.enqueue(new Callback<List<OutletModel>>() {
                @Override
                public void onResponse(Call<List<OutletModel>> call, Response<List<OutletModel>> response) {
                    progressDialog.dismiss();
                    if(response.code()==200){
                        adepter = new OutletAdapter(getActivity(), response.body());
                        recyclerView.setAdapter(adepter);
                    }else {
                        Toast.makeText(getActivity(),"System Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<OutletModel>> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(),"Request fail", Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            try{
                adepter = new OutletAdapter(getActivity(), new SQLiteOutletHandler(getActivity()).getAllOutlets());
                recyclerView.setAdapter(adepter);
            }catch (Exception e){

            }
            Toast.makeText(getActivity(),"Waiting for Network", Toast.LENGTH_SHORT).show();
        }
    }
}
