package com.example.fcmtester;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.fcmtester.databinding.ActivityWebViewBinding;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

public class WebViewActivity extends AppCompatActivity {

    ActivityWebViewBinding binding;
    private JsonArray jsonViews;
    private int count = 0;
    private int noOfViews = 0;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_web_view);
        init();
    }


    private void init() {
        try {
            actionBar = getSupportActionBar();
            if(actionBar!=null){
                //actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
               // actionBar.setCustomView(R.layout.abs_layout);
            }
            loadData();
            binding.btnNext.setOnClickListener(view -> {
                count++;
                loadWebView();
            });
            binding.btnPrev.setOnClickListener(view -> {
                count--;
                loadWebView();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        try {
            binding.progressBar.setVisibility(View.VISIBLE);
            Retrofit retrofit = ApiClient.getClient();
            ApiInterface api = retrofit.create(ApiInterface.class);
            Call<ResponseBody> call = api.getWebViews();
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    String str = null;
                    try {
                        str = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (str != null) {
                        JsonElement jsonRaw = new JsonParser().parse(str);
                        jsonViews = jsonRaw.getAsJsonArray();
                        noOfViews = jsonViews.size();
                        System.out.println("Received values");
                        binding.progressBar.setVisibility(View.GONE);
                        loadWebView();
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

    private void loadWebView() {
        try {
            checkBtns();
            JsonObject currObj = jsonViews.get(count).getAsJsonObject();
            if(actionBar!=null){
                actionBar.setTitle(currObj.get("title").getAsString());
            }
            String htmlString = currObj.get("content").getAsString();
            //String htmlString = "<ul><li>Morbi in sem quis dui placerat ornare. Pellentesque odio nisi, euismod in, pharetra a, ultricies in, diam. Sed arcu. Cras consequat.</li><li>Praesent dapibus, neque id cursus faucibus, tortor neque egestas augue, eu vulputate magna eros eu erat. Aliquam erat volutpat. Nam dui mi, tincidunt quis, accumsan porttitor, facilisis luctus, metus.</li><li>Phasellus ultrices nulla quis nibh. Quisque a lectus. Donec consectetuer ligula vulputate sem tristique cursus. Nam nulla quam, gravida non, commodo a, sodales sit amet, nisi.</li><li>Pellentesque fermentum dolor. Aliquam quam lectus, facilisis auctor, ultrices ut, elementum vulputate, nunc.</li></ul>";
            binding.webView.loadData(htmlString, "text/html; charset=utf-8", "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkBtns() {
        try{
            if(count == noOfViews-1){
                binding.btnNext.setEnabled(false);
                binding.btnNext.setBackgroundColor(ContextCompat.getColor(this, R.color.disabled));
            }else {
                binding.btnNext.setEnabled(true);
                binding.btnNext.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
            }

            if(count == 0){
                binding.btnPrev.setEnabled(false);
                binding.btnPrev.setBackgroundColor(ContextCompat.getColor(this, R.color.disabled));
            }else {
                binding.btnPrev.setEnabled(true);
                binding.btnPrev.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
