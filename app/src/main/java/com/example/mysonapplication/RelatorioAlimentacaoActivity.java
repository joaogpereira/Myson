package com.example.mysonapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class RelatorioAlimentacaoActivity extends MudarTemaActivity {

    private int usuarioId;
    private int bebeId;
    private LinearLayout layoutListaAlimentacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_alimentacao);

        usuarioId = getIntent().getIntExtra("usuario_id", -1);
        bebeId = getIntent().getIntExtra("bebe_id", -1);

        layoutListaAlimentacao = findViewById(R.id.layoutListaAlimentacao);
        ImageView imgVoltar = findViewById(R.id.imagemVoltarDoRelatorioAlimentacao);

        imgVoltar.setOnClickListener(v -> {
            Intent intent = new Intent(RelatorioAlimentacaoActivity.this, TelaPrincipalActivity.class);
            intent.putExtra("usuario_id", usuarioId);
            intent.putExtra("bebe_id", bebeId);
            startActivity(intent);
            finish();
        });

        carregarAlimentacoes();
    }

    private void carregarAlimentacoes() {
        ConexaoDB dbHelper = new ConexaoDB(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Valida se o bebê pertence ao usuário
        Cursor cursorValida = db.rawQuery(
                "SELECT COUNT(*) FROM bebe WHERE id = ? AND usuario_id = ?",
                new String[]{String.valueOf(bebeId), String.valueOf(usuarioId)}
        );

        if (cursorValida.moveToFirst()) {
            int count = cursorValida.getInt(0);
            if (count == 0) {
                Toast.makeText(this, "Bebê inválido para este usuário.", Toast.LENGTH_SHORT).show();
                cursorValida.close();
                db.close();
                return;
            }
        }
        cursorValida.close();

        // Consulta os dados de alimentação filtrando pelo bebê válido
        Cursor cursor = db.rawQuery(
                "SELECT data_hora, tipo, quantidade FROM alimentacao WHERE bebe_id = ? ORDER BY data_hora DESC",
                new String[]{String.valueOf(bebeId)}
        );

        Map<String, ArrayList<String>> mapaPorData = new LinkedHashMap<>();
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm", Locale.getDefault());

        if (cursor.moveToFirst()) {
            do {
                String dataHora = cursor.getString(cursor.getColumnIndexOrThrow("data_hora"));
                String tipo = cursor.getString(cursor.getColumnIndexOrThrow("tipo"));
                int quantidade = cursor.getInt(cursor.getColumnIndexOrThrow("quantidade"));

                try {
                    Date dataObj = formatoEntrada.parse(dataHora);
                    String dataFormatada = formatoData.format(dataObj);
                    String horaFormatada = formatoHora.format(dataObj);

                    String linha = horaFormatada + " - " + tipo + " - " + quantidade + "ml";

                    if (!mapaPorData.containsKey(dataFormatada)) {
                        mapaPorData.put(dataFormatada, new ArrayList<>());
                    }
                    mapaPorData.get(dataFormatada).add(linha);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            } while (cursor.moveToNext());
        } else {
            Toast.makeText(this, "Nenhuma alimentação encontrada!", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
        db.close();

        layoutListaAlimentacao.removeAllViews();

        for (Map.Entry<String, ArrayList<String>> entrada : mapaPorData.entrySet()) {
            String data = entrada.getKey();

            TextView txtData = new TextView(this);
            txtData.setText(data);
            txtData.setTextSize(18);
            txtData.setPadding(0, 16, 0, 8);
            txtData.setTextColor(getResources().getColor(android.R.color.black, null));
            layoutListaAlimentacao.addView(txtData);

            for (String linha : entrada.getValue()) {
                TextView txtLinha = new TextView(this);
                txtLinha.setText(linha);
                txtLinha.setTextSize(16);
                txtLinha.setPadding(0, 4, 0, 4);
                layoutListaAlimentacao.addView(txtLinha);
            }
        }
    }
}
