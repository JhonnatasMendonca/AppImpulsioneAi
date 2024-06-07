package com.example.appimpulsioneai.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appimpulsioneai.Models.EmpreendedorModel;
import com.example.appimpulsioneai.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class DestaqueActivity extends AppCompatActivity {

    private static final String TAG = "DestaqueActivity";
    private LinearLayout parentLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_component_destaque);

        parentLinearLayout = findViewById(R.id.parentLinearLayout);
        fetchEmpreendedores();
    }

    private void fetchEmpreendedores() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/") // Substitua pelo URL correto da sua API
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

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

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        imageView.setImageResource(R.drawable.destaque); // ou carregue a imagem dinamicamente

        TextView nomeTextView = new TextView(this);
        nomeTextView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        nomeTextView.setText(empreendedor.getNomeEmpreendimento());
        nomeTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        nomeTextView.setTextColor(Color.parseColor("#F3F3F3"));

        TextView nichoTextView = new TextView(this);
        nichoTextView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        if (empreendedor.getNicho() != null) {
            nichoTextView.setText(empreendedor.getNicho().getNome());
        } else {
            nichoTextView.setText("Nicho não disponível");
        }
        nichoTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        nichoTextView.setTextColor(Color.parseColor("#F3F3F3"));

        linearLayout.addView(imageView);
        linearLayout.addView(nomeTextView);
        linearLayout.addView(nichoTextView);

        parentLinearLayout.addView(linearLayout);
    }

    // Interface para Retrofit
    public interface ApiService {
        @GET("/verificaPlanosEmpreendedores")
        Call<List<EmpreendedorModel>> getEmpreendedores();
    }
}
