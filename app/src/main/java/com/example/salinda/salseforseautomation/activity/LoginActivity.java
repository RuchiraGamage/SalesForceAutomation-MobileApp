package com.example.salinda.salseforseautomation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.salinda.salseforseautomation.Other.BackgroundService;
import com.example.salinda.salseforseautomation.Other.ConnectionDetector;
import com.example.salinda.salseforseautomation.Other.ShowAlert;
import com.example.salinda.salseforseautomation.R;
import com.example.salinda.salseforseautomation.SQLite.SQliteDataInsert;
import com.example.salinda.salseforseautomation.model.LoginModel;
import com.example.salinda.salseforseautomation.rest.ApiClient;
import com.example.salinda.salseforseautomation.rest.ApiInterface;
import com.example.salinda.salseforseautomation.session.SessionHandler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ApiInterface apiInterface;
    private EditText UserName;
    private EditText Password;
    ShowAlert alert = new ShowAlert(this);
    SessionHandler session;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        session = new SessionHandler(getApplicationContext());
        if(session.isLoggedIn()){
            if (new ConnectionDetector(getApplicationContext()).isConnected()){
                new SQliteDataInsert(getApplicationContext()).InsertDatabase();

                Intent backgroundIntent = new Intent(getBaseContext(), BackgroundService.class);
                startService(backgroundIntent);

                Intent intent = new Intent(getApplicationContext() , HomePageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }else {
                alert.ShowLoginAlert("App Starting Fail..", "Network Connection is Essential", false);
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        UserName = (EditText)findViewById(R.id.userName);
        Password = (EditText)findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    public void LoginValidate(View v) {
        progressBar.setVisibility(View.VISIBLE);
        session = new SessionHandler(getApplicationContext());
        if(session.isOnclicked()){
            return;
        }else {
            session.createOnclickSession(true);
        }

        if(new ConnectionDetector(this).isConnected()){

            final String userName = UserName.getText().toString().trim();
            String password = Password.getText().toString().trim();

            apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Call<LoginModel> call = apiInterface.getLogin(userName,password);

            call.enqueue(new Callback<LoginModel>() {
                @Override
                public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                    if (response.code()==200){
                        HomePage(response.body(), userName);
                    }else {
                        Toast.makeText(getApplicationContext(), String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<LoginModel> call, Throwable t) {
                    session.createOnclickSession(false);
                    Toast.makeText(getApplicationContext(),"Login Fail... Try Again", Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            session.createOnclickSession(false);
            Toast.makeText(getApplicationContext(),"Waiting for Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void HomePage(LoginModel results, String username){
        session = new SessionHandler(getApplicationContext());
        session.createOnclickSession(false);
        progressBar.setVisibility(View.GONE);

        if (results.getStatus().equals("Fail")){
            UserName.setText("");
            Password.setText("");
            alert.ShowLoginAlert("Login failed..", "Invalid Username or Password", false);
        }else if (results.getStatus().equals("No Username")){
            UserName.setText("");
            Password.setText("");
            alert.ShowLoginAlert("Login failed..", "Place Enter valid Username", false);
        }else if(results.getStatus().equals("Success")) {
            if(new ConnectionDetector(getApplicationContext()).isConnected()){
                session.createLoginSession(username, results);
                new SQliteDataInsert(getApplicationContext()).InsertDatabase();

                Intent backgroundIntent = new Intent(getBaseContext(), BackgroundService.class);
                startService(backgroundIntent);

                Intent intent = new Intent(getApplicationContext() , HomePageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }else {
                alert.ShowLoginAlert("App Starting Fail..", "Network Connection is Essential", false);
            }
        }else {
            UserName.setText("");
            Password.setText("");
            alert.ShowLoginAlert("Login failed..", "Place Enter valid Username", false);
        }
    }
}
