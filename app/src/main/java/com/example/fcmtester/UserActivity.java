package com.example.fcmtester;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.fcmtester.databinding.ActivityUserBinding;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class UserActivity extends AppCompatActivity {

    private ActivityUserBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user);
        init();
    }

    private void init() {
        try {
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.fullName.setText(getIntent().getStringExtra(MainActivity.FULL_NAME));
            binding.email.setText(getIntent().getStringExtra(MainActivity.EMAIL));
            //load image
            try {
                Glide.with(this)
                        .asBitmap()
                        .load(getIntent().getStringExtra(MainActivity.IMAGE_URL))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .fitCenter()
                        .dontAnimate()
                        .into(new CustomTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                binding.profileImage.setImageBitmap(resource);
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {

                            }
                        });

            } catch (Exception e) {
                e.printStackTrace();
            }

            setUpRecycerView();
            Retrofit retrofit = ApiClient.getClient();
            ApiInterface api = retrofit.create(ApiInterface.class);
            Call<ResponseBody> call = api.getUsers();
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
                            JsonArray arr = jsonRaw.getAsJsonObject().get("data").getAsJsonArray();
                            adapter.updateData(arr);
                            binding.progressBar.setVisibility(View.GONE);
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

    private UsersAdapter adapter;

    private void setUpRecycerView() {
        try {
            binding.recyclerView.setHasFixedSize(true);
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new UsersAdapter(this, new JsonArray());
            binding.recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
