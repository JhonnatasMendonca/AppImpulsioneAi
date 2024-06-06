package com.example.appimpulsioneai.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appimpulsioneai.Models.User;
import com.example.appimpulsioneai.R;

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

    private EditText editTextNome, editTextDataNascimento, editTextEmail, editTextCpf;
    private TextView textViewEmailError, textViewCpfError;
    private Button buttonSalvar;

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
        buttonSalvar = findViewById(R.id.buttonCadastrar);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        // Buscar o ID do usuário do SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", null);

        if (userId != null) {
            getUserData(userId); // Passe o ID do usuário que deseja buscar
        } else {
            Toast.makeText(this, "ID do usuário não encontrado", Toast.LENGTH_SHORT).show();
        }

        buttonSalvar.setOnClickListener(v -> salvarDadosUsuario());
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
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(EditarDadosActivity.this, "Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void salvarDadosUsuario() {
        String nome = editTextNome.getText().toString();
        String dataNascimento = editTextDataNascimento.getText().toString();
        String email = editTextEmail.getText().toString();
        String cpf = editTextCpf.getText().toString();

        // Validar os dados se necessário
        if (nome.isEmpty() || dataNascimento.isEmpty() || email.isEmpty() || cpf.isEmpty()) {
            Toast.makeText(this, "Todos os campos devem ser preenchidos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Buscar o ID do usuário do SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", null);

        if (userId == null) {
            Toast.makeText(this, "ID do usuário não encontrado", Toast.LENGTH_SHORT).show();
            return;
        }

        // Criar o objeto User com os novos dados
        User user = new User(nome, dataNascimento, cpf, "", email); // Senha pode ser deixada em branco se não estiver sendo editada

        // Chamar a API para atualizar os dados do usuário
        Call<User> call = apiService.updateUser(userId, user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditarDadosActivity.this, "Dados do usuário atualizados com sucesso", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditarDadosActivity.this, "Falha ao atualizar dados do usuário", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(EditarDadosActivity.this, "Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Interface da API embutida na mesma classe
    public interface ApiService {
        @GET("usuarios/{id}")
        Call<User> getUser(@Path("id") String userId);

        @PUT("usuarios/{id}")
        Call<User> updateUser(@Path("id") String userId, @Body User user);
    }
}