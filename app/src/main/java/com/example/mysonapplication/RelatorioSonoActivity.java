package com.example.mysonapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Date;
import java.util.Map;

public class RelatorioSonoActivity extends MudarTemaActivity {

    private int usuarioId;
    private int bebeId;
    private LinearLayout layoutListaSono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_sono);

        usuarioId = getIntent().getIntExtra("usuario_id", -1);
        bebeId = getIntent().getIntExtra("bebe_id", -1);

        layoutListaSono = findViewById(R.id.layoutListaSono);
        ImageView imgVoltar = findViewById(R.id.imgVoltarRelatorioSono);

        imgVoltar.setOnClickListener(v -> {
            Intent intent = new Intent(RelatorioSonoActivity.this, TelaPrincipalActivity.class);
            intent.putExtra("usuario_id", usuarioId);
            // Não precisa passar bebe_id para tela principal, já que ela busca por usuario_id, mas pode se preferir manter
            startActivity(intent);
            finish();
        });

        carregarSonos();
    }

    private void carregarSonos() {
        ConexaoDB dbHelper = new ConexaoDB(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT inicio, fim, notas FROM sono WHERE bebe_id = ? ORDER BY inicio DESC",
                new String[]{String.valueOf(bebeId)}
        );

        Map<String, ArrayList<String>> mapaPorData = new LinkedHashMap<>();
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm", Locale.getDefault());

        if (cursor.moveToFirst()) {
            do {
                String inicio = cursor.getString(cursor.getColumnIndexOrThrow("inicio"));
                String fim = cursor.getString(cursor.getColumnIndexOrThrow("fim"));
                String notas = cursor.getString(cursor.getColumnIndexOrThrow("notas"));

                try {
                    Date dataInicio = formatoEntrada.parse(inicio);
                    String dataFormatada = formatoData.format(dataInicio);
                    String horaInicioFormatada = formatoHora.format(dataInicio);

                    String linha = "Início: " + horaInicioFormatada;

                    if (fim != null && !fim.isEmpty()) {
                        Date dataFim = formatoEntrada.parse(fim);
                        String horaFimFormatada = formatoHora.format(dataFim);
                        linha += " - Fim: " + horaFimFormatada;
                    }

                    if (notas != null && !notas.trim().isEmpty()) {
                        linha += " (" + notas.trim() + ")";
                    }

                    if (!mapaPorData.containsKey(dataFormatada)) {
                        mapaPorData.put(dataFormatada, new ArrayList<>());
                    }
                    mapaPorData.get(dataFormatada).add(linha);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        } else {
            Toast.makeText(this, "Nenhum registro de sono encontrado.", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
        db.close();

        layoutListaSono.removeAllViews();

        for (Map.Entry<String, ArrayList<String>> entrada : mapaPorData.entrySet()) {
            String data = entrada.getKey();

            // Cabeçalho da data
            TextView txtData = new TextView(this);
            txtData.setText(data);
            txtData.setTextSize(18);
            txtData.setPadding(0, 16, 0, 8);
            txtData.setTextColor(ContextCompat.getColor(this, android.R.color.black));
            layoutListaSono.addView(txtData);

            // Entradas do sono
            for (String linha : entrada.getValue()) {
                TextView txtLinha = new TextView(this);
                txtLinha.setText(linha);
                txtLinha.setTextSize(16);
                txtLinha.setPadding(0, 4, 0, 4);
                layoutListaSono.addView(txtLinha);
            }
        }
    }
}
