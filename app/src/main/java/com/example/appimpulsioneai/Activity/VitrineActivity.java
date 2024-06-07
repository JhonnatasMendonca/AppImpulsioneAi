package com.example.appimpulsioneai.Activity;

import com.example.appimpulsioneai.Models.Empreendedor;
import com.example.appimpulsioneai.R;
import com.example.appimpulsioneai.Services.ApiService;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VitrineActivity extends AppCompatActivity {

    private TextView nomeEmpreendimento;
    private TextView nomeEmpreendimentoInfo;
    private TextView nomeEmpreendedorInfo;
    private TextView biografia;
    private TextView modalidade;
    private TextView numContato;
    private TextView emailEmpreendedor;
    private TextView instagram;
    private TextView facebook;

    private LinearLayout linkPerfilLinearLayout;
    private LinearLayout linkHomeLinearLayout;
    private LinearLayout linkSairLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vitrine);

        nomeEmpreendimento = findViewById(R.id.nomeEmpreendimento);
        nomeEmpreendimentoInfo = findViewById(R.id.nomeEmpreendimentoInfo);
        biografia = findViewById(R.id.biografia);
        nomeEmpreendedorInfo = findViewById(R.id.nomeEmpreendedorInfo);
        modalidade = findViewById(R.id.modalidade);
        numContato = findViewById(R.id.numContato);
        emailEmpreendedor = findViewById(R.id.emailEmpreendedor);
        instagram = findViewById(R.id.instagram);
        facebook = findViewById(R.id.facebook);

        linkPerfilLinearLayout = findViewById(R.id.linkPerfilLinearLayout);
        linkHomeLinearLayout = findViewById(R.id.linkHomeLinearLayout);
        linkSairLinearLayout = findViewById(R.id.linkSairLinearLayout);

        linkPerfilLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(VitrineActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        linkHomeLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(VitrineActivity.this, EditarDadosActivity.class);
                startActivity(intent);
            }
        });

        linkSairLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(VitrineActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Obtenha o ID do usuário do SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String idUsuarioEmpreendedor = getIntent().getStringExtra("EMPREENDEDOR_ID");

        // Verifique se o ID do usuário não está vazio
        if (!idUsuarioEmpreendedor.isEmpty()) {
            fetchUserData(idUsuarioEmpreendedor);
        } else {
            // Lidar com a ausência de ID do usuário, se necessário
        }
    }

    private void fetchUserData(String empreendedorId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://impulsioneaiapi.onrender.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<Empreendedor> call = apiService.getEmpreendedorData(empreendedorId);

        call.enqueue(new Callback<Empreendedor>() {
            @Override
            public void onResponse(Call<Empreendedor> call, Response<Empreendedor> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Empreendedor empreendedor = response.body();
                    updateEmpreendedor(empreendedor);
                }
            }
            @Override
            public void onFailure(Call<Empreendedor> call, Throwable t) {
                // Lidar com falhas de requisição
            }
        });
    }

    private void updateEmpreendedor(Empreendedor empreendedor) {
        nomeEmpreendimento.setText(empreendedor.getNomeEmpreendimento());
        nomeEmpreendimentoInfo.setText(empreendedor.getNomeEmpreendimento());
        biografia.setText(empreendedor.getBiografia());
        nomeEmpreendedorInfo.setText(empreendedor.getNomeEmpreendedor());
        modalidade.setText(empreendedor.getModalidade());
        numContato.setText(empreendedor.getNumContato());
        emailEmpreendedor.setText(empreendedor.getEmailEmpreendedor());
        instagram.setText(empreendedor.getInstagram());
        facebook.setText(empreendedor.getFacebook());
    }
}