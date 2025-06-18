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

public class RelatorioSonoActivity extends MudarTemaActivity {
    private int usuarioId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_sono);
        usuarioId = getIntent().getIntExtra("usuario_id", -1);

        ImageView imgVoltarRelatorioSono = findViewById(R.id.imgVoltarRelatorioSono);

        imgVoltarRelatorioSono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        RelatorioSonoActivity.this,
                        TelaPrincipalActivity.class
                );
                intent.putExtra("usuario_id", usuarioId);
                startActivity(intent);
                finish();
            }
        });

    }
}