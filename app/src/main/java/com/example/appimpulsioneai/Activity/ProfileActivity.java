package com.example.appimpulsioneai.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appimpulsioneai.Models.EmpreendedorModel;
import com.example.appimpulsioneai.R;
import com.example.appimpulsioneai.Services.ApiService;
import com.example.appimpulsioneai.Models.User;

import java.util.List;

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

    private static final String TAG = "DestaqueActivity";
    private LinearLayout parentLinearLayout;
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

        parentLinearLayout = findViewById(R.id.parentLinearLayout);
        fetchEmpreendedores();
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

    private void fetchEmpreendedores() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/") // Substitua pelo URL correto da sua API
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DestaqueActivity.ApiService apiService = retrofit.create(DestaqueActivity.ApiService.class);

        apiService.getEmpreendedores().enqueue(new Callback<List<EmpreendedorModel>>() {
            @Override
            public void onResponse(Call<List<EmpreendedorModel>> call, Response<List<EmpreendedorModel>> response) {
                if (response.isSuccessful()) {
                    List<EmpreendedorModel> empreendedores = response.body();
                    if (empreendedores != null && !empreendedores.isEmpty()) {
                        for (EmpreendedorModel empreendedor : empreendedores) {
                            addEmpreendedorToLayout(empreendedor);
                        }
                    } else {
                        Log.d(TAG, "No data received from API");
                    }
                } else {
                    Log.e(TAG, "Response was not successful");
                }
            }

            @Override
            public void onFailure(Call<List<EmpreendedorModel>> call, Throwable t) {
                Log.e(TAG, "API call failed", t);
            }
        });
    }

    private void addEmpreendedorToLayout(EmpreendedorModel empreendedor) {
        Log.d(TAG, "Adding empreendedor: " + empreendedor.getNomeEmpreendimento());

        LinearLayout outerLinearLayout = new LinearLayout(this);
        outerLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        outerLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout innerLinearLayout = new LinearLayout(this);
        innerLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        innerLinearLayout.setOrientation(LinearLayout.VERTICAL);
        innerLinearLayout.setGravity(Gravity.CENTER);

        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        imageView.setImageResource(R.drawable.destaque);

        TextView nomeTextView = new TextView(this);
        nomeTextView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        nomeTextView.setText(empreendedor.getNomeEmpreendimento());
        nomeTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        nomeTextView.setTextColor(Color.parseColor("#F3F3F3"));

        TextView nichoTextView = new TextView(this);
        nichoTextView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        if (empreendedor.getNicho() != null) {
            nichoTextView.setText(empreendedor.getNicho().getNicho());
        } else {
            nichoTextView.setText("Nicho não disponível");
        }
        nichoTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        nichoTextView.setTextColor(Color.parseColor("#F3F3F3"));

        innerLinearLayout.addView(imageView);
        innerLinearLayout.addView(nomeTextView);
        innerLinearLayout.addView(nichoTextView);

        outerLinearLayout.addView(innerLinearLayout);

        parentLinearLayout.addView(outerLinearLayout);
    }


}