package com.example.mysonapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TelaPerfilActivity extends MudarTemaActivity {

    private int usuarioId;

    private TextView txtNomeUsuario;
    private TextView txtDataNascimentoBebe;
    private RadioButton radioMasculino;
    private RadioButton radioFeminino;
    private ImageView imgVoltarPerfil;
    private TextView txtSonoTotal;
    private TextView txtRefeicoesHoje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_perfil);

        usuarioId = getIntent().getIntExtra("usuario_id", -1);

        txtNomeUsuario = findViewById(R.id.txtNomePerfil);
        txtDataNascimentoBebe = findViewById(R.id.DataDeNascimento);
        radioMasculino = findViewById(R.id.radio_masculino);
        radioFeminino = findViewById(R.id.radio_feminino);
        imgVoltarPerfil = findViewById(R.id.imagemVoltarDoPerfil);
        txtRefeicoesHoje = findViewById(R.id.txtRefeicoesDeHoje);
        txtSonoTotal = findViewById(R.id.txtSonoTotalDeHoje);

        carregarDadosUsuarioEBebe(usuarioId);

        imgVoltarPerfil.setOnClickListener(v -> {
            Intent intent = new Intent(TelaPerfilActivity.this, TelaPrincipalActivity.class);
            intent.putExtra("usuario_id", usuarioId);
            startActivity(intent);
            finish();
        });

        txtSonoTotal.setOnClickListener(v -> {
            Intent intent = new Intent(TelaPerfilActivity.this, RelatorioSonoActivity.class);
            intent.putExtra("usuario_id", usuarioId);
            startActivity(intent);
            finish();
        });

        txtRefeicoesHoje.setOnClickListener(v -> {
            Intent intent = new Intent(TelaPerfilActivity.this, RelatorioAlimentacaoActivity.class);
            intent.putExtra("usuario_id", usuarioId);
            startActivity(intent);
            finish();
        });
    }

    private void carregarDadosUsuarioEBebe(int usuarioId) {
        ConexaoDB dbHelper = new ConexaoDB(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Consulta nome do usuário
        Cursor cursorUsuario = db.rawQuery(
                "SELECT nome FROM usuario WHERE id = ?",
                new String[]{String.valueOf(usuarioId)}
        );

        if (cursorUsuario.moveToFirst()) {
            String nomeUsuario = cursorUsuario.getString(cursorUsuario.getColumnIndexOrThrow("nome"));
            txtNomeUsuario.setText(nomeUsuario);
        } else {
            txtNomeUsuario.setText("Usuário não encontrado");
        }
        cursorUsuario.close();

        // Consulta dados do bebê do usuário
        Cursor cursorBebe = db.rawQuery(
                "SELECT data_nascimento, sexo FROM bebe WHERE usuario_id = ? ORDER BY id DESC LIMIT 1",
                new String[]{String.valueOf(usuarioId)}
        );

        // Desabilita ambos os radios para impedir seleção
        radioMasculino.setEnabled(false);
        radioFeminino.setEnabled(false);

        if (cursorBebe.moveToFirst()) {
            String dataNascimento = cursorBebe.getString(cursorBebe.getColumnIndexOrThrow("data_nascimento"));
            String sexo = cursorBebe.getString(cursorBebe.getColumnIndexOrThrow("sexo"));

            // Converter data de "yyyy-MM-dd" para "dd/MM/yyyy"
            try {
                SimpleDateFormat formatoBanco = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date data = formatoBanco.parse(dataNascimento);

                SimpleDateFormat formatoDesejado = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));
                String dataFormatada = formatoDesejado.format(data);

                txtDataNascimentoBebe.setText(dataFormatada);
            } catch (ParseException e) {
                e.printStackTrace();
                txtDataNascimentoBebe.setText("Data inválida");
            }

            if ("Masculino".equalsIgnoreCase(sexo)) {
                radioMasculino.setChecked(true);
                radioFeminino.setChecked(false);
            } else if ("Feminino".equalsIgnoreCase(sexo)) {
                radioFeminino.setChecked(true);
                radioMasculino.setChecked(false);
            } else {
                radioMasculino.setChecked(false);
                radioFeminino.setChecked(false);
            }

        } else {
            txtDataNascimentoBebe.setText("Bebê não cadastrado");
            radioMasculino.setChecked(false);
            radioFeminino.setChecked(false);
        }
        cursorBebe.close();
        db.close();
    }
}
