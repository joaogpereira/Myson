package com.example.mysonapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TelaPrincipalActivity extends MudarTemaActivity {
    private int usuarioId;
    private Bebê bebe; // Guardar o bebê encontrado

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

        usuarioId = getIntent().getIntExtra("usuario_id", -1);

        // Se não recebeu um usuário válido, volta para login
        if (usuarioId == -1) {
            Intent intent = new Intent(this, TelaLoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        ImageView imgViewRelatorioAlimentacao = findViewById(R.id.imageViewAlimentacao);
        ImageView imgViewRelatorioSono = findViewById(R.id.imageViewSono);
        TextView txtViewCadastroAlimentacao = findViewById(R.id.txtCadastrarAlimentacao);
        TextView txtViewCadastroSono = findViewById(R.id.txtCadastrarSono);
        ImageView imgViewLogout = findViewById(R.id.imageViewLogout);
        ImageView imgIrPerfil = findViewById(R.id.imagemIrPerfil);
        TextView txtDataAtual = findViewById(R.id.txtData);
        TextView txtIdadeBebe = findViewById(R.id.txtIdadeBebe);

        // Data atual formatada
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));
        dateFormat.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
        String dataAtual = dateFormat.format(new Date());
        txtDataAtual.setText(dataAtual);

        // Buscar bebê do usuário (id e data nascimento)
        bebe = buscarBebeDoUsuario(usuarioId);

        if (bebe != null) {
            txtIdadeBebe.setText(calcularIdade(bebe.dataNascimento));
        } else {
            txtIdadeBebe.setText("Bebê não cadastrado");
        }

        imgViewRelatorioAlimentacao.setOnClickListener(v -> {
            Intent intent = new Intent(this, RelatorioAlimentacaoActivity.class);
            intent.putExtra("usuario_id", usuarioId);
            if (bebe != null) {
                intent.putExtra("bebe_id", bebe.id);
            }
            startActivity(intent);
        });

        imgViewRelatorioSono.setOnClickListener(v -> {
            Intent intent = new Intent(this, RelatorioSonoActivity.class);
            intent.putExtra("usuario_id", usuarioId);
            if (bebe != null) {
                intent.putExtra("bebe_id", bebe.id);
            }
            startActivity(intent);
        });

        txtViewCadastroAlimentacao.setOnClickListener(v -> {
            Intent intent = new Intent(this, CadastroAlimentacaoActivity.class);
            intent.putExtra("usuario_id", usuarioId);
            if (bebe != null) {
                intent.putExtra("bebe_id", bebe.id);
            }
            startActivity(intent);
        });

        txtViewCadastroSono.setOnClickListener(v -> {
            Intent intent = new Intent(this, CadastroSonoActivity.class);
            intent.putExtra("usuario_id", usuarioId);
            if (bebe != null) {
                intent.putExtra("bebe_id", bebe.id);
            }
            startActivity(intent);
        });

        imgViewLogout.setOnClickListener(v -> {
            // Se usar SharedPreferences para login, limpar aqui (não mostrado)
            Intent intent = new Intent(this, TelaLoginActivity.class);
            startActivity(intent);
            finish();
        });

        imgIrPerfil.setOnClickListener(v -> {
            Intent intent = new Intent(this, TelaPerfilActivity.class);
            intent.putExtra("usuario_id", usuarioId);
            if (bebe != null) {
                intent.putExtra("bebe_id", bebe.id);
            }
            startActivity(intent);
        });
    }

    private Bebê buscarBebeDoUsuario(int usuarioId) {
        ConexaoDB dbHelper = new ConexaoDB(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Bebê bebe = null;

        Cursor cursor = db.rawQuery("SELECT id, data_nascimento FROM bebe WHERE usuario_id = ?", new String[]{String.valueOf(usuarioId)});

        if (cursor.moveToFirst()) {
            int bebeId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String dataNascimento = cursor.getString(cursor.getColumnIndexOrThrow("data_nascimento"));
            bebe = new Bebê(bebeId, dataNascimento);
        }

        cursor.close();
        db.close();

        return bebe;
    }

    private String calcularIdade(String dataNascimento) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        try {
            Date nascimento = sdf.parse(dataNascimento);
            Date hoje = new Date();

            long diffMillis = hoje.getTime() - nascimento.getTime();
            long diasTotais = diffMillis / (1000 * 60 * 60 * 24);

            int anos = (int) (diasTotais / 365);
            int meses = (int) ((diasTotais % 365) / 30);
            int semanas = (int) (((diasTotais % 365) % 30) / 7);
            int dias = (int) (((diasTotais % 365) % 30) % 7);

            StringBuilder idadeStr = new StringBuilder();

            if (anos > 0) idadeStr.append(anos).append(anos == 1 ? " ano, " : " anos, ");
            if (meses > 0) idadeStr.append(meses).append(meses == 1 ? " mês, " : " meses, ");
            if (semanas > 0) idadeStr.append(semanas).append(semanas == 1 ? " semana, " : " semanas, ");
            if (dias > 0 || idadeStr.length() == 0) idadeStr.append(dias).append(dias == 1 ? " dia" : " dias");

            String idadeFormatada = idadeStr.toString().trim();

            if (idadeFormatada.endsWith(",")) {
                idadeFormatada = idadeFormatada.substring(0, idadeFormatada.length() - 1);
            }

            return idadeFormatada;

        } catch (ParseException e) {
            e.printStackTrace();
            return "Data inválida";
        }
    }

    // Classe interna para guardar dados do bebê
    private static class Bebê {
        int id;
        String dataNascimento;

        Bebê(int id, String dataNascimento) {
            this.id = id;
            this.dataNascimento = dataNascimento;
        }
    }
}
