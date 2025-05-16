package com.example.mysonapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TelaLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_login);



        TextView txtEsqueciSenha = findViewById(R.id.txtEsqueciSenha);
        TextView txtCadastre_se = findViewById(R.id.txtCadastre_se);


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
}