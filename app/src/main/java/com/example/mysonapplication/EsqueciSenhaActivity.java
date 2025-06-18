package com.example.mysonapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class EsqueciSenhaActivity extends MudarTemaActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esqueci_senha);

        ImageView imgVoltarEsqueciSenha = findViewById(R.id.imagemVoltarDoEsqueciSenha);

        imgVoltarEsqueciSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        EsqueciSenhaActivity.this,
                        TelaLoginActivity.class
                );
                startActivity(intent);
            }
        });

    }
}