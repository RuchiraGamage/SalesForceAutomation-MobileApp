package com.example.salinda.salseforseautomation.Fragment.BillingProcessTabs;

import android.app.ProgressDialog;
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
import com.example.salinda.salseforseautomation.Other.ShowAlert;
import com.example.salinda.salseforseautomation.R;
import com.example.salinda.salseforseautomation.SQLite.SQLiteVanProductHandler;
import com.example.salinda.salseforseautomation.activity.BillingTabViewActivity;
import com.example.salinda.salseforseautomation.adapter.ProductAdapter;
import com.example.salinda.salseforseautomation.model.ProductsModel;
import com.example.salinda.salseforseautomation.model.VanProductModel;
import com.example.salinda.salseforseautomation.rest.ApiClient;
import com.example.salinda.salseforseautomation.rest.ApiInterface;
import com.example.salinda.salseforseautomation.session.SessionHandler;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VanOrderFragment extends Fragment {
    private RecyclerView recyclerView;
    private ApiInterface apiInterface;
    private ProductAdapter adepter;
    private SearchView searchView;
    private ShowAlert alert;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View myFragmentView =  inflater.inflate(R.layout.recyclerview_with_search, container, false);

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
                alert = new ShowAlert(getActivity());
                alert.inputDialogAlert(adepter.getProductByPosition(position), BillingTabViewActivity.KEY_VANORDER);
            }

            @Override
            public void onLongClick(View view, int position) {
                adepter.selectSingleProduct(position);
            }
        }));
        return myFragmentView;
    }

    private void fetchProduct(){
        if(new ConnectionDetector(getActivity()).isConnected()){
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            Call<List<VanProductModel>> call = apiInterface.getVanProduct(new SessionHandler(getActivity()).getUserDetails().getId());
            call.enqueue(new Callback<List<VanProductModel>>() {
                @Override
                public void onResponse(Call<List<VanProductModel>> call, Response<List<VanProductModel>> response) {
                    progressDialog.dismiss();
                    List<ProductsModel> models = new ArrayList<ProductsModel>();
                    if(response.code()==200){
                        for(int i = 0; i<response.body().size(); i++){
                            ProductsModel productsModel = new ProductsModel();
                            productsModel.setId(response.body().get(i).getId());
                            productsModel.setName(response.body().get(i).getName());
                            productsModel.setBrand(response.body().get(i).getBrand());
                            productsModel.setCategory(response.body().get(i).getCategory());
                            productsModel.setManufacturePrice(response.body().get(i).getManufacturePrice());
                            productsModel.setPrice(response.body().get(i).getPrice());
                            productsModel.setQuantity(response.body().get(i).getQuantity());
                            productsModel.setDiscountType(response.body().get(i).getDiscountType());
                            productsModel.setImage(response.body().get(i).getImage());
                            models.add(productsModel);
                        }
                        adepter = new ProductAdapter(getActivity(), models);
                        recyclerView.setAdapter(adepter);
                    }else {
                        Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<VanProductModel>> call, Throwable t) {
                    try{
                        Toast.makeText(getActivity(),"Request fail", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){

                    }
                }
            });
        }else {
            try{
                adepter = new ProductAdapter(getActivity(), new SQLiteVanProductHandler(getActivity()).getAllProduct());
                recyclerView.setAdapter(adepter);
            }catch (Exception e){

            }
            Toast.makeText(getActivity(),"Waiting for Network", Toast.LENGTH_SHORT).show();
        }
    }
}
