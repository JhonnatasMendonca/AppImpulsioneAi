package com.example.appimpulsioneai.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appimpulsioneai.R;
import com.example.appimpulsioneai.Services.ApiService;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText emailForgotEditText;
    private Button forgotButton;
    private TextView voltarLoginTextView;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailForgotEditText = findViewById(R.id.emailForgotEditText);
        forgotButton = findViewById(R.id.forgotButton);
        voltarLoginTextView = findViewById(R.id.returnLoginTextView);

        // Voltar para página de login
        voltarLoginTextView.setOnClickListener(v -> {
            Intent intent = new Intent(ForgotPasswordActivity.this, MainActivity.class);
            startActivity(intent);
        });

        // Configurar Retrofit
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        forgotButton.setOnClickListener(v -> handleSubmit());
    }

    private void handleSubmit() {
        String email = emailForgotEditText.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Por favor, insira o email cadastrado", Toast.LENGTH_SHORT).show();
            return;
        }

        Call<ResponseBody> call = apiService.verificaUsuarios(email);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String responseBody = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseBody);
                        String idUsuario = jsonObject.getString("id");

                        // Salvar o id do usuário no SharedPreferences
                        SharedPreferences sharedPreferences = getSharedPreferences("myAppPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("idUsuario", idUsuario);
                        editor.apply();

                        Intent intent = new Intent(ForgotPasswordActivity.this, ChangePasswordActivity.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(ForgotPasswordActivity.this, "Erro ao processar a resposta", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "E-mail não encontrado na nossa base de dados :( \nTente novamente!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(ForgotPasswordActivity.this, "Erro ao verificar usuário", Toast.LENGTH_LONG).show();
            }
        });
    }
}
