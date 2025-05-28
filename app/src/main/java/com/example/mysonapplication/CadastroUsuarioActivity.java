package com.example.mysonapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CadastroUsuarioActivity extends MudarTemaActivity {

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