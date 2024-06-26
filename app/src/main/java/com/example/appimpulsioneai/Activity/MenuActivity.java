package com.example.appimpulsioneai.Activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appimpulsioneai.R;
import com.example.appimpulsioneai.Services.ApiService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuActivity extends AppCompatActivity {

    private LinearLayout linkPerfilLinearLayout;
    private LinearLayout linkHomeLinearLayout;
    private LinearLayout linkSairLinearLayout;

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_component_menu);

        linkPerfilLinearLayout = findViewById(R.id.linkPerfilLinearLayout);
        linkHomeLinearLayout = findViewById(R.id.linkHomeLinearLayout);
        linkSairLinearLayout = findViewById(R.id.linkSairLinearLayout);

        linkPerfilLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MenuActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        linkHomeLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MenuActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        linkSairLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
