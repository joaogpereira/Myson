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


    private int usuarioId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_alimentacao);
        usuarioId = getIntent().getIntExtra("usuario_id", -1);

        ImageView imgVoltarRelatorioAlimentacao = findViewById(R.id.imagemVoltarDoRelatorioAlimentacao);


        imgVoltarRelatorioAlimentacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        RelatorioAlimentacaoActivity.this,
                        TelaPrincipalActivity.class
                );
                intent.putExtra("usuario_id", usuarioId);
                startActivity(intent);
                finish();

            }
        });

    }
}