package com.example.appimpulsioneai.Activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appimpulsioneai.R;
import com.example.appimpulsioneai.Services.ApiService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VitrineActivity extends AppCompatActivity {

    private ApiService apiService;
    private TextView nomeEmpreendimento, nomeEmpreendedorInfo, biografia, modalidade, numContato, email, instagram, facebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vitrine);

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

        nomeEmpreendimento = findViewById(R.id.nomeEmpreendimento);
        biografia = findViewById(R.id.biografia);
        nomeEmpreendedorInfo = findViewById(R.id.nomeEmpreendimentoInfo);
        modalidade = findViewById(R.id.modalidade);
        numContato = findViewById(R.id.numContato);
        email = findViewById(R.id.email);
        instagram = findViewById(R.id.instagram);
        facebook = findViewById(R.id.facebook);

        apiService = ApiClient.getClient().create(ApiService.class);
        idEmpreendedor = getIntent().getStringExtra("idEmpreendedor");

        loadEmpreendedor();
        setupRecyclerView();
        setupListeners();
    }
    private void loadEmpreendedor() {
        Call<Empreendedor> call = apiService.getEmpreendedor(idEmpreendedor);
        call.enqueue(new Callback<Empreendedor>() {
            @Override
            public void onResponse(Call<Empreendedor> call, Response<Empreendedor> response) {
                if (response.isSuccessful()) {
                    Empreendedor empreendedor = response.body();
                    title.setText(empreendedor.getNomeEmpreendimento());
                    biografia.setText(empreendedor.getBiografia());
                    loadProdutos(empreendedor.getProdutos());
                }
            }

            @Override
            public void onFailure(Call<Empreendedor> call, Throwable t) {
                Toast.makeText(VitrineActivity.this, "Erro ao carregar dados", Toast.LENGTH_SHORT).show();
            }
        });


}
