package com.example.mysonapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

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
        ImageView imgIrPerfil = findViewById(R.id.imagemIrPerfil);

        //Data de hoje
        TextView txtDataAtual = findViewById(R.id.txtData);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));
        dateFormat.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
        String dataAtual = dateFormat.format(new Date());
        txtDataAtual.setText(dataAtual);

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
        // Intent de logout
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
        // Direcionada para o Perfil
        imgIrPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        TelaPrincipalActivity.this,
                        TelaPerfilActivity.class
                );
                startActivity(intent);
            }
        });





    }
}