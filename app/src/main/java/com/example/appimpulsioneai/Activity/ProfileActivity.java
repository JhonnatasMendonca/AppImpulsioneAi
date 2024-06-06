package com.example.appimpulsioneai.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appimpulsioneai.R;
import com.example.appimpulsioneai.Services.ApiService;
import com.example.appimpulsioneai.Models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class ProfileActivity extends AppCompatActivity {
    private TextView primeiroNomeUsuarioTextView;
    private TextView nomeUsuarioTextView;
    private TextView dataNascimentoTextView;
    private TextView cpfTextView;
    private TextView emailTextView;
    private Button editarDadosButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        primeiroNomeUsuarioTextView = findViewById(R.id.primeiroNomeUsuarioTextView);
        nomeUsuarioTextView = findViewById(R.id.nomeUsuarioTextView);
        dataNascimentoTextView = findViewById(R.id.dataNascimentoTextView);
        cpfTextView = findViewById(R.id.cpfTextView);
        emailTextView = findViewById(R.id.emailTextView);
        editarDadosButton = findViewById(R.id.editarDadosButton);

        // Obtenha o ID do usuário do SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String idUsuario = sharedPreferences.getString("userId", "");

        // Verifique se o ID do usuário não está vazio
        if (!idUsuario.isEmpty()) {
            fetchUserData(idUsuario);
        } else {
            // Lidar com a ausência de ID do usuário, se necessário
        }

        editarDadosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, EditarDadosActivity.class);
                startActivity(intent);
            }
        });
    }

    private void fetchUserData(String userId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<User> call = apiService.getUserData(userId);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();
                    updateUI(user);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Lidar com falhas de requisição
            }
        });
    }

    private void updateUI(User user) {
        primeiroNomeUsuarioTextView.setText("Olá, " + user.getNome().split(" ")[0] + " !");
        nomeUsuarioTextView.setText(user.getNome());
        dataNascimentoTextView.setText(user.getDataNascimento());
        cpfTextView.setText(user.getCpf());
        emailTextView.setText(user.getEmail());
    }


}