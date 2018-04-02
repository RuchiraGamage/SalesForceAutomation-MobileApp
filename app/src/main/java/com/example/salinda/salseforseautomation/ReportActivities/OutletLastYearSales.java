package com.example.salinda.salseforseautomation.ReportActivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.salinda.salseforseautomation.R;
import com.example.salinda.salseforseautomation.adapter.ReportOutletSelectorAdaptor;
import com.example.salinda.salseforseautomation.model.MonthlyReportModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class OutletLastYearSales extends AppCompatActivity {
    int outletId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_last_year_sales);

        Bundle extras = getIntent().getExtras();
        String outletName = extras.getString(ReportOutletSelectorAdaptor.KEY_OUTLET_NAME);
        List<MonthlyReportModel> monthlyReportModels = (ArrayList<MonthlyReportModel>) extras.getSerializable(ReportOutletSelectorAdaptor.KEY_OUTLET_GRAPH);

        ArrayList<BarEntry> BarEntry = new ArrayList<>();
        for (int i = 0; i<monthlyReportModels.size(); i++){
            BarEntry.add(new BarEntry(monthlyReportModels.get(i).getAmount(), i));
        }

        ArrayList<String> labels = new ArrayList<>();
        for(int i = 0; i<monthlyReportModels.size(); i++){
            switch (monthlyReportModels.get(i).getMonth()){
                case 1: labels.add("Jan"); break;
                case 2: labels.add("Feb"); break;
                case 3: labels.add("Mar"); break;
                case 4: labels.add("Apr"); break;
                case 5: labels.add("May"); break;
                case 6: labels.add("Jun"); break;
                case 7: labels.add("Jul"); break;
                case 8: labels.add("Aug"); break;
                case 9: labels.add("Sep"); break;
                case 10: labels.add("Oct"); break;
                case 11: labels.add("Nov"); break;
                case 12: labels.add("Dec"); break;
            }
        }

        BarChart chart = (BarChart) findViewById(R.id.bar_chart);
        BarDataSet dataSet = new BarDataSet(BarEntry, "Month");
        BarData data = new BarData(labels, dataSet);
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        chart.setData(data);
        chart.setDescription(outletName+" Last Year Sales");
    }

//    private void fetchData(final String X, final String description){
//        if(new ConnectionDetector(this).isConnected()){
//            ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
//            Call<List<MonthlyReportModel>> call = apiInterface.getOutletsMonthlyReport(outletId);
//            call.enqueue(new Callback<List<MonthlyReportModel>>() {
//                @Override
//                public void onResponse(Call<List<MonthlyReportModel>> call, Response<List<MonthlyReportModel>> response) {
//                    if(response.code()==200){
//                        drowGraph(response.body(), X, description);
//                    }else {
//                        Toast.makeText(getApplicationContext(),response.message(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<List<MonthlyReportModel>> call, Throwable t) {
//                    Toast.makeText(getApplicationContext(),"Request fail.. Try Again", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }else {
//            Toast.makeText(getApplicationContext(),"Waiting for Network", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void drowGraph(List<MonthlyReportModel> monthlyReportModels, String X, String description){
//
//        ArrayList<BarEntry> BarEntry = new ArrayList<>();
//        for (int i = 0; i<monthlyReportModels.size(); i++){
//            BarEntry.add(new BarEntry(monthlyReportModels.get(i).getAmount(), i));
//        }
//
//        ArrayList<String> labels = new ArrayList<>();
//        for(int i = 0; i<monthlyReportModels.size(); i++){
//            switch (monthlyReportModels.get(i).getMonth()){
//                case 1: labels.add("Jan"); break;
//                case 2: labels.add("Feb"); break;
//                case 3: labels.add("Mar"); break;
//                case 4: labels.add("Apr"); break;
//                case 5: labels.add("May"); break;
//                case 6: labels.add("Jun"); break;
//                case 7: labels.add("Jul"); break;
//                case 8: labels.add("Aug"); break;
//                case 9: labels.add("Sep"); break;
//                case 10: labels.add("Oct"); break;
//                case 11: labels.add("Nov"); break;
//                case 12: labels.add("Dec"); break;
//            }
//        }
//
//        BarChart chart = (BarChart) findViewById(R.id.bar_chart);
//        BarDataSet dataSet = new BarDataSet(BarEntry, X);
//        BarData data = new BarData(labels, dataSet);
//        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
//        chart.setData(data);
//        chart.setDescription(description);
//    }
}
