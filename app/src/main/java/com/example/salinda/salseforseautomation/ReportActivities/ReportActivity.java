package com.example.salinda.salseforseautomation.ReportActivities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.salinda.salseforseautomation.Other.Animation;
import com.example.salinda.salseforseautomation.Other.ConnectionDetector;
import com.example.salinda.salseforseautomation.R;
import com.example.salinda.salseforseautomation.model.MonthlyReportModel;
import com.example.salinda.salseforseautomation.rest.ApiClient;
import com.example.salinda.salseforseautomation.rest.ApiInterface;
import com.example.salinda.salseforseautomation.session.SessionHandler;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportActivity extends AppCompatActivity {
    CardView userMonth, userYear, outletMonth, outletYear, routeMonth, routeYear;
    public static final String KEY_MONTH = "Month";
    public static final String KEY_YEAR = "Year";
    public static final String KEY_USER_GRAPH = "UserData";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        userMonth = (CardView) findViewById(R.id.user_monthly);
        userMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Animation().onClickAnimation(v);
                Spinner monthSpinner = (Spinner) findViewById(R.id.user_mm);
                String month = monthSpinner.getSelectedItem().toString();
                Spinner yearSpinner = (Spinner) findViewById(R.id.user_yyyy);
                String year = yearSpinner.getSelectedItem().toString();
                Intent intent = new Intent(getApplicationContext(), UserMonthSales.class);
                intent.putExtra(KEY_MONTH, month);
                intent.putExtra(KEY_YEAR, year);
                startActivity(intent);
            }
        });
        userYear = (CardView) findViewById(R.id.user_year);
        userYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Animation().onClickAnimation(v);
                userMonthlyData();
            }
        });
        outletYear = (CardView) findViewById(R.id.outlet_year);
        outletYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Animation().onClickAnimation(v);
                Intent intent = new Intent(getApplicationContext(), ReportOutletSelectorActivity.class);
                intent.putExtra("Id", 1);
                startActivity(intent);
            }
        });
        outletMonth = (CardView) findViewById(R.id.outlet_monthly);
        outletMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Animation().onClickAnimation(v);
                Spinner monthSpinner = (Spinner) findViewById(R.id.outlet_mm);
                String month = monthSpinner.getSelectedItem().toString();
                Spinner yearSpinner = (Spinner) findViewById(R.id.outlet_yyyy);
                String year = yearSpinner.getSelectedItem().toString();
                Intent intent = new Intent(getApplicationContext(), ReportOutletSelectorActivity.class);
                intent.putExtra(KEY_MONTH, month);
                intent.putExtra(KEY_YEAR, year);
                intent.putExtra("Id", 2);
                startActivity(intent);
            }
        });
    }

    private void userMonthlyData(){
        if(new ConnectionDetector(this).isConnected()){
            progressDialog = new ProgressDialog(ReportActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Call<List<MonthlyReportModel>> call = apiInterface.getUsersMonthlyReport(new SessionHandler(getApplicationContext()).getUserDetails().getId());
            call.enqueue(new Callback<List<MonthlyReportModel>>() {
                @Override
                public void onResponse(Call<List<MonthlyReportModel>> call, Response<List<MonthlyReportModel>> response) {
                    if(response.code()==200){
                        progressDialog.dismiss();
                        Intent intent = new Intent(getApplicationContext(), UserLastYearSales.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(KEY_USER_GRAPH, (Serializable) response.body());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"System Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<MonthlyReportModel>> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Request fail.. Try Again", Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            Toast.makeText(getApplicationContext(),"Waiting for Network", Toast.LENGTH_SHORT).show();
        }
    }
}
