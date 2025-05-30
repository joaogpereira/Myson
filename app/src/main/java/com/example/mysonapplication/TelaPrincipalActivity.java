package com.example.mysonapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TelaPrincipalActivity extends MudarTemaActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

        // views para o relatorio
        ImageView imgViewRelatorioAlimentacao = findViewById(R.id.imageViewAlimentacao);
        ImageView imgViewRelatorioSono = findViewById(R.id.imageViewSono);

        //views para o cadastro
        TextView txtViewCadatroAlimentacao = findViewById(R.id.txtCadastrarAlimentacao);
        TextView txtViewCadatroSono = findViewById(R.id.txtCadastrarSono);

        //Imageview para o logout
        ImageView imgViewLogout = findViewById(R.id.imageViewLogout);

        //direcionando para o relatorio de alimentação
        imgViewRelatorioAlimentacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        TelaPrincipalActivity.this,
                        RelatorioAlimentacaoActivity.class
                );
                startActivity(intent);
            }
        });
        //direcionando para o relatorio de sono
        imgViewRelatorioSono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        TelaPrincipalActivity.this,
                        RelatorioSonoActivity.class
                );
                startActivity(intent);
            }
        });
        //direciona para o cadastro da alimentação
        txtViewCadatroAlimentacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        TelaPrincipalActivity.this,
                        CadastroAlimentacaoActivity.class
                );
                startActivity(intent);
            }
        });
        // direciona para o cadastro do sono
        txtViewCadatroSono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        TelaPrincipalActivity.this,
                        CadastroSonoActivity.class
                );
                startActivity(intent);
            }
        });
        // imagem view logout
        imgViewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        TelaPrincipalActivity.this,
                        TelaLoginActivity.class
                );
                startActivity(intent);
            }
        });




    }
}