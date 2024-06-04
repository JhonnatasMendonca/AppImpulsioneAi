package com.example.appimpulsioneai.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appimpulsioneai.R;
import com.example.appimpulsioneai.Services.ApiService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText etNovaSenha, etConfirmarSenha;
    private Button btnSalvar;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        etNovaSenha = findViewById(R.id.newChangeEditText);
        etConfirmarSenha = findViewById(R.id.confirmNewChangeEditText);
        btnSalvar = findViewById(R.id.changePasswordButton);

        // Configure Retrofit
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
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

        btnSalvar.setOnClickListener(view -> handleSubmit());
    }

    private void handleSubmit() {
        String senha = etNovaSenha.getText().toString();
        String confirmarSenha = etConfirmarSenha.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences("myAppPrefs", MODE_PRIVATE);
        String idUsuario = sharedPreferences.getString("idUsuario", "");

        if (TextUtils.isEmpty(senha) || TextUtils.isEmpty(confirmarSenha)) {
            Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (senha.equals(confirmarSenha)) {
            ApiService.PasswordChangeRequest request = new ApiService.PasswordChangeRequest(senha);
            Call<Void> call = apiService.changePassword(idUsuario, request);

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(ChangePasswordActivity.this, "Senha alterada com sucesso!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ChangePasswordActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(ChangePasswordActivity.this, "Erro ao alterar senha", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(ChangePasswordActivity.this, "Erro ao alterar senha", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Senhas diferentes", Toast.LENGTH_SHORT).show();
        }
    }
}
