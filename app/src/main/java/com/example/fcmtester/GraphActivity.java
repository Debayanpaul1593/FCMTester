package com.example.fcmtester;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.fcmtester.databinding.ActivityGraphBinding;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity {

    private ActivityGraphBinding binding;
    private JsonArray chartData;
    private String mode = "all";
    private String descText = "All Data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_graph);
        init();
    }

    private void init() {
        try {
            callDataService();
            binding.btnProceed.setOnClickListener(view -> {
                startActivity(new Intent(GraphActivity.this, WebViewActivity.class));
            });
            binding.btnMode.setOnClickListener(view -> {
                changeMode();
               setUpChart();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changeMode() {
        try{
            if(mode.equals("all")){
                binding.btnMode.setText("Show All Data");
                mode = "july";
                descText = "July Data";
            }else if(mode.equals("july")){
                binding.btnMode.setText("Show July Data");
                mode = "all";
                descText = "All Data";
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void setUpChart() {
        try {
            ArrayList<BarEntry> chartDataList = new ArrayList<>(chartData.size());
            for (int i = 0; i < chartData.size(); i++) {
                try {
                    JsonObject chartObj = chartData.get(i).getAsJsonObject();
                    if(mode.equals("july")){
                        String date = chartObj.get("date_created").getAsString();
                        String month = date.split("-")[1];
                        if (month.equals("07") || month.equals("7")){
                            chartDataList.add(new BarEntry(i, chartObj.get("amount").getAsInt()));
                        }
                    }else{
                        chartDataList.add(new BarEntry(i, chartObj.get("amount").getAsInt()));
                    }

                } catch (Exception e) {
                    continue;
                }

            }
            BarDataSet barDataSet = new BarDataSet(chartDataList, "Sample Data");
            barDataSet.setColor(Color.rgb(255, 0, 0));
            ArrayList dataSet = new ArrayList<>();
            dataSet.add(barDataSet);
            BarData barData = new BarData(dataSet);
            barData.setValueFormatter(new MyValueFormatter());
            binding.barChart.setData(barData);
            binding.barChart.setVisibleXRangeMaximum(5);
            binding.barChart.getDescription().setText(descText);
            binding.barChart.invalidate();
            binding.progressBar.setVisibility(View.GONE);
            XAxis xAxis = binding.barChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawGridLines(false);
            xAxis.setValueFormatter((value, axis) -> GraphActivity.this.chartData.get((int) value).getAsJsonObject().get("date_created").getAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callDataService() {
        try {
            Retrofit retrofit = ApiClient.getClient();
            ApiInterface api = retrofit.create(ApiInterface.class);
            Call<ResponseBody> call = api.getGraphs();
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String str = null;
                        try {
                            str = response.body().string();
                        } catch (IOException e) {
                            binding.progressBar.setVisibility(View.GONE);
                        }
                        if (str != null) {
                            JsonElement jsonRaw = new JsonParser().parse(str);
                            chartData = jsonRaw.getAsJsonArray();
                            setUpChart();
                            System.out.println("Received values");
                        }
                    } catch (Exception e) {
                        binding.progressBar.setVisibility(View.GONE);
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    binding.progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
