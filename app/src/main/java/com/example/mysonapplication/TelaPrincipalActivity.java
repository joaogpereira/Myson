package com.example.mysonapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TelaPrincipalActivity extends MudarTemaActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

        ImageView imgViewAlimentacao = findViewById(R.id.imageViewAlimentacao);
        ImageView imgViewSono = findViewById(R.id.imageViewSono);


        imgViewAlimentacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        TelaPrincipalActivity.this,
                        RelatorioAlimentacaoActivity.class
                );
                startActivity(intent);
            }
        });

        imgViewSono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        TelaPrincipalActivity.this,
                        RelatorioSonoActivity.class
                );
                startActivity(intent);
            }
        });


    }
}