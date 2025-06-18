package com.example.mysonapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

        TextView txtEsqueciSenha = findViewById(R.id.txtEsqueciSenha);
        TextView txtCadastre_se = findViewById(R.id.txtCadastre_se);

        edtNome = findViewById(R.id.edtEmailLogin);
        edtSenha = findViewById(R.id.edtSenhaLogin);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = edtNome.getText().toString().trim();
                String senha = edtSenha.getText().toString().trim();

                if (nome.isEmpty() || senha.isEmpty()) {
                    Toast.makeText(TelaLoginActivity.this, "Por favor, insira os dados corretamente", Toast.LENGTH_SHORT).show();
                } else {
                    int usuarioId = validaLogin(nome, senha);
                    if (usuarioId != -1) {
                        // Salvar usuário logado em SharedPreferences
                        SharedPreferences prefs = getSharedPreferences("MySonAppPrefs", MODE_PRIVATE);
                        prefs.edit().putInt("usuario_logado_id", usuarioId).apply();

                        Toast.makeText(TelaLoginActivity.this, "Login bem-sucedido", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(TelaLoginActivity.this, SplashActivity.class);
                        intent.putExtra("usuario_id", usuarioId);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(TelaLoginActivity.this, "Usuário ou senha incorretos", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        txtEsqueciSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaLoginActivity.this, EsqueciSenhaActivity.class);
                startActivity(intent);
            }
        });

        txtCadastre_se.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaLoginActivity.this, CadastroUsuarioActivity.class);
                startActivity(intent);
            }
        });
    }

    private int validaLogin(String nome, String senha) {
        String sql = "SELECT id FROM usuario WHERE email = ? AND senha = ?";
        String[] selectionArgs = {nome, senha};
        try (android.database.Cursor cursor = db.rawQuery(sql, selectionArgs)) {
            if (cursor.moveToFirst()) {
                return cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            } else {
                return -1; // Não encontrado
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
