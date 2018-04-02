package com.example.salinda.salseforseautomation.Fragment.LeaveTab;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.salinda.salseforseautomation.Other.ConnectionDetector;
import com.example.salinda.salseforseautomation.R;
import com.example.salinda.salseforseautomation.model.LeaveModel;
import com.example.salinda.salseforseautomation.rest.ApiClient;
import com.example.salinda.salseforseautomation.rest.ApiInterface;
import com.example.salinda.salseforseautomation.session.SessionHandler;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddLeaves extends Fragment {
    EditText startingDate, endingDate, reason;
    Button addLeave;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View myFragmentView =  inflater.inflate(R.layout.add_leave, container, false);

        startingDate = (EditText) myFragmentView.findViewById(R.id.starting_date);
        endingDate = (EditText) myFragmentView.findViewById(R.id.ending_date);
        reason = (EditText) myFragmentView.findViewById(R.id.reason);
        addLeave = (Button) myFragmentView.findViewById(R.id.leave);
        addLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sDate = null;
                String eDate = null;
                String reasons = null;
                LeaveModel leaveModel = null;
                try{
                    sDate = String.valueOf(startingDate.getText());
                    eDate = String.valueOf(endingDate.getText());
                    reasons = String.valueOf(reason.getText());
                    leaveModel = new LeaveModel(sDate, eDate, reasons, new SessionHandler(getActivity()).getUserDetails().getId());
                }catch (Exception e){

                }
                if(sDate.isEmpty() | eDate.isEmpty() | reasons.isEmpty()){
                    Toast.makeText(getActivity(),"All Details are Needed", Toast.LENGTH_SHORT).show();
                }else {
                    postLeave(leaveModel);
                }
            }
        });

        return myFragmentView;
    }

    private void postLeave(LeaveModel leaveModel){
        if(new ConnectionDetector(getActivity()).isConnected()){
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Call<ResponseBody> call = apiInterface.postLeave(leaveModel);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.code()==204){
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(),"Leave Placed Successfully", Toast.LENGTH_SHORT).show();
                        startingDate.setText("");
                        endingDate.setText("");
                        reason.setText("");
                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(),"System Error.. Try Again Later", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(),"Leave Fail.. Try Again", Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            Toast.makeText(getActivity(),"Waiting for Network", Toast.LENGTH_SHORT).show();
        }
    }
}
