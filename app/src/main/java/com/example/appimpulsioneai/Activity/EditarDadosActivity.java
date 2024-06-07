package com.example.appimpulsioneai.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appimpulsioneai.Models.User;
import com.example.appimpulsioneai.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public class EditarDadosActivity extends AppCompatActivity {

    private static final String TAG = "EditarDadosActivity";

    private EditText editTextNome, editTextDataNascimento, editTextEmail, editTextCpf;
    private TextView textViewEmailError, textViewCpfError;
    private Button buttonSalvar;
    private Button VoltarPerfilButton;

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);

        editTextNome = findViewById(R.id.editTextNome);
        editTextDataNascimento = findViewById(R.id.editTextDataNascimento);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextCpf = findViewById(R.id.editTextCpf);
        textViewEmailError = findViewById(R.id.textViewEmailError);
        textViewCpfError = findViewById(R.id.textViewCpfError);
        buttonSalvar = findViewById(R.id.buttonAtualizar);
        VoltarPerfilButton = findViewById(R.id.VoltarPerfilButton);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        // Buscar o ID do usuário do SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", null);

        if (userId != null) {
            getUserData(userId);
        } else {
            Toast.makeText(this, "ID do usuário não encontrado", Toast.LENGTH_SHORT).show();
        }

        VoltarPerfilButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditarDadosActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarDadosUsuario();
            }
        });
    }

    private void getUserData(String userId) {
        Call<User> call = apiService.getUser(userId);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();
                    editTextNome.setText(user.getNome());
                    editTextDataNascimento.setText(user.getDataNascimento());
                    editTextEmail.setText(user.getEmail());
                    editTextCpf.setText(user.getCpf());
                } else {
                    Toast.makeText(EditarDadosActivity.this, "Falha ao obter dados do usuário", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Falha ao obter dados do usuário: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(EditarDadosActivity.this, "Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Erro ao obter dados do usuário", t);
            }
        });
    }

    private void salvarDadosUsuario() {
        String nome = editTextNome.getText().toString();
        String dataNascimento = editTextDataNascimento.getText().toString();
        String email = editTextEmail.getText().toString();
        String cpf = editTextCpf.getText().toString();

        if (nome.isEmpty() || dataNascimento.isEmpty() || email.isEmpty() || cpf.isEmpty()) {
            Toast.makeText(this, "Todos os campos devem ser preenchidos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Formatar a data no formato correto
        String formattedDate;
        try {
            // Espera o formato ISO 8601 da data de entrada
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault());
            Date date = inputFormat.parse(dataNascimento);

            // Converte a data para o formato "yyyy-MM-dd"
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            formattedDate = outputFormat.format(date);
        } catch (ParseException e) {
            Toast.makeText(this, "Erro ao formatar a data de nascimento", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Erro ao formatar a data de nascimento", e);
            return;
        }

        // Buscar o ID do usuário do SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", null);

        if (userId == null) {
            Toast.makeText(this, "ID do usuário não encontrado", Toast.LENGTH_SHORT).show();
            return;
        }

        // Obter dados atuais do usuário, incluindo a senha
        Call<User> call = apiService.getUser(userId);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User currentUser = response.body();

                    // Criar o objeto User com os novos dados, mantendo a senha atual
                    User updatedUser = new User(nome, formattedDate, cpf, currentUser.getSenha(), email);

                    // Chamar a API para atualizar os dados do usuário
                    Call<Void> updateCall = apiService.updateUser(userId, updatedUser);
                    updateCall.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(EditarDadosActivity.this, "Dados do usuário atualizados com sucesso", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(EditarDadosActivity.this, ProfileActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(EditarDadosActivity.this, "Falha ao atualizar dados do usuário", Toast.LENGTH_SHORT).show();
                                Log.e(TAG, "Falha ao atualizar dados do usuário: " + response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(EditarDadosActivity.this, "Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Erro ao atualizar dados do usuário", t);
                        }
                    });
                } else {
                    Toast.makeText(EditarDadosActivity.this, "Falha ao obter dados do usuário", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Falha ao obter dados do usuário: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(EditarDadosActivity.this, "Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Erro ao obter dados do usuário", t);
            }
        });
    }

    public interface ApiService {
        @GET("usuarios/{id}")
        Call<User> getUser(@Path("id") String userId);

        @PUT("usuariosApp/{id}")
        Call<Void> updateUser(@Path("id") String userId, @Body User user);
    }
}
