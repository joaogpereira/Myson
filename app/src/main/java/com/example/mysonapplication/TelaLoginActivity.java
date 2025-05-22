package com.example.mysonapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TelaLoginActivity extends AppCompatActivity {
    private EditText edtNome, edtSenha;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login);

        //Definindo as views das intents
        TextView txtEsqueciSenha = findViewById(R.id.txtEsqueciSenha);
        TextView txtCadastre_se = findViewById(R.id.txtCadastre_se);

        edtNome = findViewById(R.id.edtEmailLogin);
        edtSenha = findViewById(R.id.edtSenhaLogin);
        btnLogin = findViewById(R.id.btnLogin);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = edtNome.getText().toString();
                String senha = edtSenha.getText().toString();

                if (nome.isEmpty() || senha.isEmpty()){
                    Toast.makeText(TelaLoginActivity.this,"Por favor , Insira os Dados Corretamente", Toast.LENGTH_SHORT).show();
                } else {
                    validaLogin(nome,senha);
                }
            }
        });



        txtEsqueciSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        TelaLoginActivity.this,
                        EsqueciSenhaActivity.class
                );
                    startActivity(intent);

            }
        });
        txtCadastre_se.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        TelaLoginActivity.this,
                        CadastroUsuarioActivity.class
                );
                startActivity(intent);

            }
        });

    }
    private void validaLogin(String nome,String senha){
        //Dados ficticios apenas para testes
        // Falta implementar autenticação com o banco de dados e a conexão com o mesmo
        String usuariocorreto ="jeipas";
        String senhacorreta ="123";

        if (nome.equals(usuariocorreto) && senha.equals(senhacorreta)) {
            Toast.makeText(TelaLoginActivity.this,"Login Bem sucedido",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(TelaLoginActivity.this,"Dados Incorretos",Toast.LENGTH_SHORT).show();
        }


    }
}