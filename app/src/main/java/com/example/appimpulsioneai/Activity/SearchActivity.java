package com.example.appimpulsioneai.Activity;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appimpulsioneai.R;
import com.example.appimpulsioneai.Services.ApiService;
import com.example.appimpulsioneai.Services.RetrofitClient;
import com.example.appimpulsioneai.models.NichoModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        LinearLayout linearLayout = findViewById(R.id.nichoLinearLayout);

        ApiService service = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        Call<List<NichoModel>> call = service.getNichos();

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
}
