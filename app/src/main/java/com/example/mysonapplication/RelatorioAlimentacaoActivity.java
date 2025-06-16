package com.example.mysonapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RelatorioAlimentacaoActivity extends MudarTemaActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_alimentacao);

        ImageView imgVoltarRelatorioAlimentacao = findViewById(R.id.imagemVoltarDoRelatorioAlimentacao);


        imgVoltarRelatorioAlimentacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        RelatorioAlimentacaoActivity.this,
                        TelaPrincipalActivity.class
                );
                startActivity(intent);
            }
        });

    }
}