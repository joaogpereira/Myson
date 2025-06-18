package com.example.mysonapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class CadastroAlimentacaoActivity extends MudarTemaActivity {

    private int usuarioId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_alimentacao);
        usuarioId = getIntent().getIntExtra("usuario_id", -1);

        ImageView imgVoltarPrincipal = findViewById(R.id.imagemVoltarDoCadastroAlimentacao);
        Button btnSalvarCadastroAlimentacao = findViewById(R.id.btnSalvarCadastroAlimentacao);

        imgVoltarPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        CadastroAlimentacaoActivity.this,
                        TelaPrincipalActivity.class
                );
                intent.putExtra("usuario_id", usuarioId);
                startActivity(intent);
                finish();
            }
        });



    }
}