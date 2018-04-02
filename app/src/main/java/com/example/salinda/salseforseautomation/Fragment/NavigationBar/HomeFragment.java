package com.example.salinda.salseforseautomation.Fragment.NavigationBar;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.salinda.salseforseautomation.Message.Chat.Users;
import com.example.salinda.salseforseautomation.Other.Animation;
import com.example.salinda.salseforseautomation.R;
import com.example.salinda.salseforseautomation.ReportActivities.ReportActivity;
import com.example.salinda.salseforseautomation.activity.AddExpensesActivity;
import com.example.salinda.salseforseautomation.activity.LeaveActivity;
import com.example.salinda.salseforseautomation.activity.OutletSelectorActivity;
import com.example.salinda.salseforseautomation.activity.ShowOutletActivity;
import com.example.salinda.salseforseautomation.activity.ShowProductActivity;
import com.example.salinda.salseforseautomation.mapActivity.RouteActivity;

public class HomeFragment extends Fragment {

    ImageView productImage, outletImage, billingImage, reportImage, messageImage, expensesImage, leaveImage, routeImage;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myFragmentView =  inflater.inflate(R.layout.fragment_home, container, false);
        return myFragmentView;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        // get the button view
        productImage = (ImageView) getView().findViewById(R.id.Product);
        productImage.setClickable(true);
        outletImage = (ImageView) getView().findViewById(R.id.Outlets);
        outletImage.setClickable(true);
        billingImage = (ImageView) getView().findViewById(R.id.Billing);
        billingImage.setClickable(true);
        reportImage = (ImageView) getView().findViewById(R.id.Reports);
        reportImage.setClickable(true);
        messageImage = (ImageView)getView().findViewById(R.id.Message);
        messageImage.setClickable(true);
        expensesImage = (ImageView) getView().findViewById(R.id.Expenses);
        expensesImage.setClickable(true);
        leaveImage = (ImageView) getView().findViewById(R.id.Leave);
        leaveImage.setClickable(true);
        routeImage = (ImageView) getView().findViewById(R.id.Route);
        routeImage.setClickable(true);

        // set a onclick listener for when the button gets clicked
        productImage.setOnClickListener(new View.OnClickListener() {
            // Start new list activity
            public void onClick(View v) {
                new Animation().onClickAnimation(v);
                Intent mainIntent = new Intent(getActivity(), ShowProductActivity.class);
                startActivity(mainIntent);
            }
        });
        outletImage.setOnClickListener(new View.OnClickListener() {
            // Start new list activity
            public void onClick(View v) {
                new Animation().onClickAnimation(v);
                Intent mainIntent = new Intent(getActivity(), ShowOutletActivity.class);
                startActivity(mainIntent);
            }
        });
        billingImage.setOnClickListener(new View.OnClickListener() {
            // Start new list activity
            public void onClick(View v) {
                new Animation().onClickAnimation(v);
                Intent mainIntent = new Intent(getActivity(), OutletSelectorActivity.class);
                startActivity(mainIntent);
            }
        });
        reportImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Animation().onClickAnimation(v);
                Intent mainIntent = new Intent(getActivity(), ReportActivity.class);
                startActivity(mainIntent);
            }
        });
        messageImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Animation().onClickAnimation(v);
                Intent intent = new Intent(getActivity(), Users.class);
                startActivity(intent);
            }
        });
        expensesImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Animation().onClickAnimation(v);
                Intent intent = new Intent(getActivity(), AddExpensesActivity.class);
                startActivity(intent);
            }
        });
        leaveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Animation().onClickAnimation(v);
                Intent intent = new Intent(getActivity(), LeaveActivity.class);
                startActivity(intent);
            }
        });
        routeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Animation().onClickAnimation(v);
                Intent intent = new Intent(getActivity(), RouteActivity.class);
                startActivity(intent);
            }
        });
    }

}
