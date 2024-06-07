package com.example.appimpulsioneai.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appimpulsioneai.R;
import com.example.appimpulsioneai.Services.ApiService;
import com.example.appimpulsioneai.Models.User;
import com.example.appimpulsioneai.Utils.CPFValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserRegistrationActivity extends AppCompatActivity {

    private Button VoltarMenuButton, buttonCadastrar;
    private EditText editTextNome, editTextDataNascimento, editTextEmail, editTextCpf, editTextSenha, editTextConfirmarSenha;
    private TextView textViewEmailError, textViewCpfError, textViewSenhaError;

    private Button buttonParceiro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        // Inicializa os componentes da interface do usuário
        VoltarMenuButton = findViewById(R.id.VoltarMenuButton);
        buttonCadastrar = findViewById(R.id.buttonCadastrar);
        editTextNome = findViewById(R.id.editTextNome);
        editTextDataNascimento = findViewById(R.id.editTextDataNascimento);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextCpf = findViewById(R.id.editTextCpf);
        editTextSenha = findViewById(R.id.editTextSenha);
        editTextConfirmarSenha = findViewById(R.id.editTextConfirmarSenha);
        textViewEmailError = findViewById(R.id.textViewEmailError);
        textViewCpfError = findViewById(R.id.textViewCpfError);
        textViewSenhaError = findViewById(R.id.textViewSenhaError);
        buttonParceiro = findViewById(R.id.buttonParceiro);

        // Voltar para página de login
        VoltarMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserRegistrationActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        buttonParceiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserRegistrationActivity.this, PartnerRegistrationActivity.class);
                startActivity(intent);
            }
        });

        // Cadastro do usuário
        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    registerUser();
                }
            }
        });
    }

    private boolean validateInputs() {
        boolean isValid = true;

        // Reseta mensagens de erro
        textViewEmailError.setVisibility(View.GONE);
        textViewCpfError.setVisibility(View.GONE);
        textViewSenhaError.setVisibility(View.GONE);

        // Valida CPF
        String cpf = editTextCpf.getText().toString();
        if (!CPFValidator.isValid(cpf)) {
            textViewCpfError.setVisibility(View.VISIBLE);
            isValid = false;
        }

        // Valida e-mail
        String email = editTextEmail.getText().toString();
        if (!isValidEmail(email)) {
            textViewEmailError.setVisibility(View.VISIBLE);
            isValid = false;
        }

        // Valida senhas
        String senha = editTextSenha.getText().toString();
        String confirmarSenha = editTextConfirmarSenha.getText().toString();
        if (!senha.equals(confirmarSenha)) {
            textViewSenhaError.setVisibility(View.VISIBLE);
            isValid = false;
        }

        return isValid;
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void registerUser() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        String nome = editTextNome.getText().toString();
        String dataNascimento = editTextDataNascimento.getText().toString();
        String cpf = editTextCpf.getText().toString();
        String senha = editTextSenha.getText().toString();
        String email = editTextEmail.getText().toString();

        User user = new User(nome, dataNascimento, cpf, senha, email);

        Call<ResponseBody> call = apiService.registerUser(user);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UserRegistrationActivity.this, "Usuário registrado com sucesso!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(UserRegistrationActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(UserRegistrationActivity.this, "Erro ao registrar usuário!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(UserRegistrationActivity.this, "Erro: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
