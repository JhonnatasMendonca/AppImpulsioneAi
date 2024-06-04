package com.example.appimpulsioneai.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appimpulsioneai.R;
import com.example.appimpulsioneai.Services.ApiService;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PartnerRegistrationActivity extends AppCompatActivity {

    private Button buttonUsuario;
    private Button VoltarMenuButton;

    private Button buttonCadastrar;

    private EditText nomeCompletoEditText, dataNascimentoEditText, meiEditText, cpfEditText, senhaEditText,
            confirmarSenhaEditText, ufEditText, cidadeEditText, bairroEditText, numeroEditText, logradouroEditText,
            nomeEmpreendimentoEditText, siteEditText, numeroTelefoneEditText, emailEditText, instagramEditText, facebookEditText;
    private Spinner spinnerNicho, spinnerPlano;
    private RadioGroup radioGroupModalidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_registration);

        buttonUsuario = findViewById(R.id.buttonUsuario);
        VoltarMenuButton = findViewById(R.id.VoltarMenuButton);

        nomeCompletoEditText = findViewById(R.id.nomeCompletoEditText);
        dataNascimentoEditText = findViewById(R.id.dataNascimentoEditText);
        meiEditText = findViewById(R.id.meiEditText);
        cpfEditText = findViewById(R.id.cpfEditText);
        senhaEditText = findViewById(R.id.senhaEditText);
        confirmarSenhaEditText = findViewById(R.id.confirmarSenhaEditText);
        ufEditText = findViewById(R.id.ufEditText);
        cidadeEditText = findViewById(R.id.cidadeEditText);
        bairroEditText = findViewById(R.id.bairroEditText);
        numeroEditText = findViewById(R.id.numeroEditText);
        logradouroEditText = findViewById(R.id.logradouroEditText);
        nomeEmpreendimentoEditText = findViewById(R.id.nomeEmpreendimentoEditText);
        siteEditText = findViewById(R.id.siteEditText);
        numeroTelefoneEditText = findViewById(R.id.numeroTelefoneEditText);
        emailEditText = findViewById(R.id.emailEditText);
        instagramEditText = findViewById(R.id.instagramEditText);
        facebookEditText = findViewById(R.id.facebookEditText);
        spinnerNicho = findViewById(R.id.spinnerNicho);
        spinnerPlano = findViewById(R.id.spinnerPlano);
        radioGroupModalidade = findViewById(R.id.radioGroupModalidade);
        buttonCadastrar = findViewById(R.id.buttonCadastrar);


        VoltarMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PartnerRegistrationActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        buttonUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PartnerRegistrationActivity.this, UserRegistrationActivity.class);
                startActivity(intent);
            }
        });

        // Defina os valores que deseja exibir no Spinner de nicho
        String[] nichoArray = {"Selecione o Nicho", "Gastronomia", "Moda", "Artesanato", "Tecnologia", "Educação", "Saúde", "Estética", "Diversos"};
        ArrayAdapter<String> adapterNicho = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nichoArray);
        adapterNicho.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNicho.setAdapter(adapterNicho);

        // Inicialize a variável 'nichoSelecionado' fora do método
        final String[] nichoSelecionado = {""};

        // Adicione um listener para o Spinner
        spinnerNicho.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Obtenha o número correspondente à posição selecionada no Spinner
                int numeroSelecionado = position - 1; // Subtraindo 1 para compensar a opção "Selecione o Nicho"
                // Atualize a variável 'nichoSelecionado' com o valor selecionado
                nichoSelecionado[0] = String.valueOf(numeroSelecionado);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Implemente o que deseja fazer se nenhum item for selecionado
            }
        });

        Spinner spinnerPlano = findViewById(R.id.spinnerPlano);
        List<String> planos = new ArrayList<>();
        planos.add("Selecione"); // Opção padrão
        planos.add("Gratuito");
        planos.add("Ouro");
        planos.add("Diamante");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, planos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPlano.setAdapter(adapter);

        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obter os valores dos campos
                String nomeCompleto = nomeCompletoEditText.getText().toString();
                String dataNascimento = dataNascimentoEditText.getText().toString();
                String mei = meiEditText.getText().toString();
                String cpf = cpfEditText.getText().toString();
                String senha = senhaEditText.getText().toString();
                String confirmarSenha = confirmarSenhaEditText.getText().toString();
                String uf = ufEditText.getText().toString();
                String cidade = cidadeEditText.getText().toString();
                String bairro = bairroEditText.getText().toString();
                String numero = numeroEditText.getText().toString();
                String logradouro = logradouroEditText.getText().toString();
                String nomeEmpreendimento = nomeEmpreendimentoEditText.getText().toString();
                String site = siteEditText.getText().toString();
                String numeroTelefone = numeroTelefoneEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String instagram = instagramEditText.getText().toString();
                String facebook = facebookEditText.getText().toString();
                String nicho = nichoSelecionado[0]; // Obtendo o número da posição do array
                String plano = spinnerPlano.getSelectedItem().toString();
                int modalidadeId = radioGroupModalidade.getCheckedRadioButtonId();
                RadioButton radioButtonModalidade = findViewById(modalidadeId);
                String modalidade = radioButtonModalidade.getText().toString();

                // Criar objeto Partner com os dados
                ApiService.Endereco endereco = new ApiService.Endereco(uf, cidade, bairro, logradouro, numero);
                ApiService.Partner partner = new ApiService.Partner(nomeCompleto, dataNascimento, cpf, mei, senha, nomeEmpreendimento,
                        site, numeroTelefone, email, plano, instagram, facebook, nicho, modalidade, endereco);

                // Iniciar Retrofit para realizar a chamada à API
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://10.0.2.2:8080/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiService apiService = retrofit.create(ApiService.class);

                // Enviar dados para a API
                Call<ResponseBody> call = apiService.registerPartner(partner);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(PartnerRegistrationActivity.this, "Cadastro realizado com sucesso!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(PartnerRegistrationActivity.this, "Erro ao cadastrar! Verifique os dados inseridos.", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(PartnerRegistrationActivity.this, "Erro: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
