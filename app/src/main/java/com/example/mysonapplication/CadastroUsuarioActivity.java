package com.example.mysonapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CadastroUsuarioActivity extends MudarTemaActivity {
    private EditText edtNome, edtEmail, edtSenha, edtConfirmarSenha;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);
        //identificando campos
        edtNome = findViewById(R.id.edtNomeCadastroUsuario);
        edtEmail = findViewById(R.id.edtEmailCadastroUsuario);
        edtSenha =findViewById(R.id.edtSenhaCadastroUsuario);
        edtSenha =findViewById(R.id.edtSenhaConfirmacaoCadastroUsuario);
        Button btnProximo = findViewById(R.id.btnProximoCadastroUsuario);
        ImageView imgVoltarCadastroUsuario = findViewById(R.id.imagemVoltarDoCadastro);
        TextView txtCadastroUsuario = findViewById(R.id.txtEntrar);


        //Conectando no Banco de dados
        ConexaoDB dbHelper = new ConexaoDB(this);
        db = dbHelper.getWritableDatabase();

        //voltar para a tela de login
        imgVoltarCadastroUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        CadastroUsuarioActivity.this,
                        TelaLoginActivity.class
                );
                startActivity(intent);
            }
        });
        //Voltar para a tela de login com o Botão Entrar
       txtCadastroUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        CadastroUsuarioActivity.this,
                        TelaLoginActivity.class
                );
                startActivity(intent);
            }
        });

       //cadastrar usuário ao clicar em próximo
        btnProximo.setOnClickListener(v ->{
            String nome = edtNome.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String senha = edtSenha.getText().toString().trim();
            String confirmarSenha = edtConfirmarSenha.getText().toString().trim();

            if (nome.isEmpty() || email.isEmpty() ||senha.isEmpty() || confirmarSenha.isEmpty()){
                Toast.makeText(this, "Preencha Todos os Campos", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!senha.equals(confirmarSenha)){
                Toast.makeText(this, "As Senhas não Coincidem", Toast.LENGTH_SHORT).show();
                return;
            }

            ContentValues valores = new ContentValues();
            valores.put("nome",nome);
            valores.put("email",email);
            valores.put("senha",senha);

            long resultado = db.insert("usuario",null,valores);

            if (resultado != -1){
                Toast.makeText(this, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,TelaLoginActivity.class));
                finish();
            } else{
                Toast.makeText(this, "Erro ao cadastrar. tente Novamente", Toast.LENGTH_SHORT).show();
                
            }



        });

    }
}