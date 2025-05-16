package com.example.mysonapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CadastroUsuarioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        ImageView imgVoltarCadastroUsuario = findViewById(R.id.imagemVoltarDoCadastro);
        TextView txtCadastroUsuario = findViewById(R.id.txtCadastroUsuario);

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

    }
}