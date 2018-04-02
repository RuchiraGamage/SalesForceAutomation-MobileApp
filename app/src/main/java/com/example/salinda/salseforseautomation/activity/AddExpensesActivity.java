package com.example.salinda.salseforseautomation.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.salinda.salseforseautomation.Other.ConnectionDetector;
import com.example.salinda.salseforseautomation.R;
import com.example.salinda.salseforseautomation.model.ExpensesModel;
import com.example.salinda.salseforseautomation.rest.ApiClient;
import com.example.salinda.salseforseautomation.rest.ApiInterface;
import com.example.salinda.salseforseautomation.session.SessionHandler;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddExpensesActivity extends AppCompatActivity {
    EditText date, description, amount;
    Button expensesButton;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expenses);

        date = (EditText) findViewById(R.id.date);
        description = (EditText) findViewById(R.id.description);
        amount = (EditText) findViewById(R.id.amount);
        expensesButton = (Button) findViewById(R.id.expenses);
        expensesButton.setClickable(true);
        expensesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Date = null;
                String Description = null;
                float Amount = 0.0f;
                ExpensesModel expensesModel = null;
                try{
                    Date = String.valueOf(date.getText());
                    Description = String.valueOf(description.getText());
                    Amount = Float.valueOf(String.valueOf(amount.getText()));
                    expensesModel = new ExpensesModel(Date, Description, Amount, new SessionHandler(getApplicationContext()).getUserDetails().getId());
                }catch (Exception e){

                }
                if(Date.isEmpty() | Description.isEmpty() | Amount==0.0f){
                    Toast.makeText(getApplicationContext(),"All Details are Needed", Toast.LENGTH_SHORT).show();
                }else {
                    postExpenses(expensesModel);

                }
            }
        });

    }

    private void postExpenses(ExpensesModel expensesModel){
        if(new ConnectionDetector(getApplicationContext()).isConnected()){
            progressDialog = new ProgressDialog(AddExpensesActivity.this);
            progressDialog.setMessage("Place Waite...");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Call<ResponseBody> call = apiInterface.postExpenses(expensesModel);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.code()==204){
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Expense Placed Successfully", Toast.LENGTH_SHORT).show();
                        date.setText("");
                        description.setText("");
                        amount.setText("");
                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"System Error.. Try Again Later", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Expense Fail.. Try Again", Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            Toast.makeText(getApplicationContext(),"Waiting for Network", Toast.LENGTH_SHORT).show();
        }
    }
}
