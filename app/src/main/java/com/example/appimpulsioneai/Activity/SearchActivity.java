package com.example.appimpulsioneai.Activity;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.view.View;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appimpulsioneai.R;
import com.example.appimpulsioneai.Services.ApiService;
import com.example.appimpulsioneai.Services.RetrofitClient;
import com.example.appimpulsioneai.Models.EmpreendedorModel;
import com.example.appimpulsioneai.Models.NichoModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "DestaqueActivity";
    private LinearLayout parentLinearLayout;

    private LinearLayout linkPerfilLinearLayout;
    private LinearLayout linkHomeLinearLayout;
    private LinearLayout linkSairLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        LinearLayout linearLayout = findViewById(R.id.nichoLinearLayout);
        ScrollView scrollView = findViewById(R.id.scroLlEmpreendimetos);
        LinearLayout container = new LinearLayout(SearchActivity.this);
        container.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(container);

        linkPerfilLinearLayout = findViewById(R.id.linkPerfilLinearLayout);
        linkHomeLinearLayout = findViewById(R.id.linkHomeLinearLayout);
        linkSairLinearLayout = findViewById(R.id.linkSairLinearLayout);

        linkPerfilLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SearchActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        linkHomeLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SearchActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        linkSairLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        parentLinearLayout = findViewById(R.id.parentLinearLayout);
        fetchEmpreendedores();

        ApiService service = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        Call<List<NichoModel>> call = service.getNichos();
        Call<List<EmpreendedorModel>> empreendedoresGeral = service.getEmpreendedores();

        empreendedoresGeral.enqueue(new Callback<List<EmpreendedorModel>>() {
            @Override
            public void onResponse(Call<List<EmpreendedorModel>> call, Response<List<EmpreendedorModel>> response) {
                List<EmpreendedorModel> empreendimentos = response.body();

                for (EmpreendedorModel empreendedor : empreendimentos){
                    final EmpreendedorModel empreendedorFinal = empreendedor;

                    View empreendimentoView = LayoutInflater.from(SearchActivity.this).inflate(R.layout.component_search_empreendedor, container, false);

                    TextView nomeTextView = empreendimentoView.findViewById(R.id.nomeEmprendimento);
                    TextView nichoTextView = empreendimentoView.findViewById(R.id.nicho);
                    TextView descricaoTextView = empreendimentoView.findViewById(R.id.descircaoEmprendimento);

                    nomeTextView.setText(empreendedor.getNomeEmpreendimento());
                    nichoTextView.setText(empreendedor.getNicho().getNome());
                    descricaoTextView.setText(empreendedor.getBiografia());

                    // Define os LayoutParams dinamicamente
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    params.setMargins(0, 0, 0, 20); // margem inferior de 20dp
                    empreendimentoView.setLayoutParams(params);
                    empreendimentoView.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {
                            // Ação a ser realizada quando o container (ou seja, o empreendimento) é clicado
                            // Aqui você pode iniciar a atividade VitrineActivity passando o ID do empreendimento como parâmetro
                            Intent intent = new Intent(SearchActivity.this, VitrineActivity.class);
                            intent.putExtra("EMPREENDEDOR_ID", empreendedorFinal.getId()); // Passe o ID do empreendedor como parâmetro
                            startActivity(intent);
                        }
                    });

                    container.addView(empreendimentoView);

                }
            }

            @Override
            public void onFailure(Call<List<EmpreendedorModel>> call, Throwable t) {
                Log.e(TAG, "Falha na requisição: " + t.getMessage());
            }
        });

        call.enqueue(new Callback<List<NichoModel>>() {
            @Override
            public void onResponse(Call<List<NichoModel>> call, Response<List<NichoModel>> response) {
                if (response.isSuccessful()) {
                    List<NichoModel> nichos = response.body();
                    for (NichoModel nicho : nichos){
                        TextView textView = new TextView(SearchActivity.this); // Crie um novo TextView
                        textView.setText(nicho.getNome());
                        textView.setTextColor(Color.WHITE);// Defina o texto do TextView com o nome do nicho
                        textView.setTextSize(15);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        params.setMargins(0, 0, 16, 0); // Defina a margem à direita para 16 pixels
                        textView.setLayoutParams(params);
                        linearLayout.addView(textView);

                    }
                } else {
                    Log.e(TAG, "Erro ao receber a resposta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<NichoModel>> call, Throwable t) {
                Log.e(TAG, "Falha na requisição: " + t.getMessage());
            }
        });

    }
    private void fetchEmpreendedores() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://impulsioneaiapi.onrender.com/") // Substitua pelo URL correto da sua API
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
            nichoTextView.setText(empreendedor.getNicho().getNome());
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
