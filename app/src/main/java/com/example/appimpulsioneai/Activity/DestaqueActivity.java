package com.example.appimpulsioneai.Activity;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appimpulsioneai.R;

public class DestaqueActivity extends AppCompatActivity {

    private LinearLayout containerEmpreendedores;
    private static final String TAG = "DestaqueActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_component_destaque);

//        containerEmpreendedores = findViewById(R.id.containerEmpreendedores);
//
//        fetchEmpreendedores();
//    }
//
//    private void fetchEmpreendedores() {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://10.0.2.2:8080/") // Alterar para a URL correta da API
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        ApiService apiService = retrofit.create(ApiService.class);
//        Call<List<Empreendedor>> call = apiService.verificaPlanosEmpreendedores();
//
//        call.enqueue(new Callback<List<Empreendedor>>() {
//            @Override
//            public void onResponse(Call<List<Empreendedor>> call, Response<List<Empreendedor>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    List<Empreendedor> empreendedores = response.body();
//                    displayEmpreendedores(empreendedores);
//                } else {
//                    Log.e(TAG, "Response was not successful or body is null");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Empreendedor>> call, Throwable t) {
//                Log.e(TAG, "API call failed", t);
//            }
//        });
//    }
//
//    private void displayEmpreendedores(List<Empreendedor> empreendedores) {
//        for (Empreendedor empreendedor : empreendedores) {
//            LinearLayout layout = new LinearLayout(this);
//            layout.setOrientation(LinearLayout.VERTICAL);
//
//            ImageView imageView = new ImageView(this);
//            imageView.setLayoutParams(new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT));
//            imageView.setImageResource(R.drawable.destaque); // Usar imagem do drawable
//
//            TextView nomeTextView = new TextView(this);
//            nomeTextView.setLayoutParams(new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT));
//            nomeTextView.setText(empreendedor.getNomeEmpreendimento());
//            nomeTextView.setTextColor(getResources().getColor(R.color.white));
//            nomeTextView.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
//
//            TextView nichoTextView = new TextView(this);
//            nichoTextView.setLayoutParams(new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT));
//            nichoTextView.setText(empreendedor.getNicho());
//            nichoTextView.setTextColor(getResources().getColor(R.color.white));
//            nichoTextView.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
//
//            layout.addView(imageView);
//            layout.addView(nomeTextView);
//            layout.addView(nichoTextView);
//
//            containerEmpreendedores.addView(layout);
//        }
//    }
    }
}
