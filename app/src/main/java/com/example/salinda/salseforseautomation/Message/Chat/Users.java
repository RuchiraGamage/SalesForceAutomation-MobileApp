package com.example.salinda.salseforseautomation.Message.Chat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.salinda.salseforseautomation.R;
import com.example.salinda.salseforseautomation.model.LoginModel;
import com.example.salinda.salseforseautomation.session.SessionHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Users extends AppCompatActivity {

    private static final String url_data="http://salesforcenew20180208102258.azurewebsites.net/api/Users";



    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<UserModel> listitems;

    private SessionHandler sessionHandler;
    private LoginModel loginModel;
    private int current_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        sessionHandler=new SessionHandler(this);
        loginModel=sessionHandler.getUserDetails();
        current_user_id=loginModel.getId();

        recyclerView=(RecyclerView)findViewById(R.id.user_recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        listitems=new ArrayList<>();

        loadRecycleViewData();




    }

    private void loadRecycleViewData() {



        StringRequest stringRequest=new StringRequest(Request.Method.GET, url_data, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //  JSONObject jsonObject=new JSONObject(response);
                    //  JSONArray array=jsonObject.getJSONArray("UserDetails_Result");

                    JSONArray array=new JSONArray(response);

                    for (int i=0;i<array.length();i++){
                        JSONObject o=array.getJSONObject(i);

                        if (o.getInt("Id")!=current_user_id) {
                            UserModel u = new UserModel(o.getInt("Id"), o.getString("FirstName"), o.getString("UserType"), o.getString("City"),o.getString("Image"));
                            listitems.add(u);
                        }
                    }
                    adapter=new UserAdapter(listitems,getApplicationContext());
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}
