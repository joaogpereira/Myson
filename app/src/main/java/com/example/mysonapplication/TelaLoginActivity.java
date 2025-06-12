package com.example.mysonapplication;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TelaLoginActivity extends MudarTemaActivity {
    private EditText edtNome, edtSenha;
    private Button btnLogin;
    private SQLiteDatabase db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login);

        ConexaoDB dbHelper = new ConexaoDB(TelaLoginActivity.this);
        db = dbHelper.getReadableDatabase();


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

                if (nome.isEmpty() || senha.isEmpty()) {
                    Toast.makeText(TelaLoginActivity.this, "Por favor, insira os dados corretamente", Toast.LENGTH_SHORT).show();
                } else {
                    if (validaLogin(nome, senha)) {
                        Toast.makeText(TelaLoginActivity.this, "Login bem-sucedido", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(TelaLoginActivity.this, SplashActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(TelaLoginActivity.this, "Usuário ou senha incorretos", Toast.LENGTH_SHORT).show();
                    }
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
    private boolean validaLogin(String nome,String senha){

        String sql = "SELECT * FROM usuario WHERE email = ? AND senha = ?";
        String[] selectionArgs = { nome, senha };
        try (android.database.Cursor cursor = db.rawQuery(sql, selectionArgs)) {
            if (cursor.moveToFirst()) {
                // Encontrou um registro que bate com usuário e senha
                return true;
            } else {
                // Não encontrou
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }
}