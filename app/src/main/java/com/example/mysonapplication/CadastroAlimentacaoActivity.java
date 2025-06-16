package com.example.mysonapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class CadastroAlimentacaoActivity extends MudarTemaActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_alimentacao);

        ImageView imgVoltarPrincipal = findViewById(R.id.imagemVoltarDoCadastroAlimentacao);
        Button btnSalvarCadastroAlimentacao = findViewById(R.id.btnSalvarCadastroAlimentacao);

        imgVoltarPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        CadastroAlimentacaoActivity.this,
                        TelaPrincipalActivity.class
                );
                startActivity(intent);
            }
        });



    }
}