package com.example.mysonapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CadastroSonoActivity extends MudarTemaActivity {

    private int usuarioId;
    private int bebeId;

    private EditText editTextHoraInicio;
    private EditText editTextHoraFim;
    private EditText editTextNotas;

    private Button btnSalvarCadastroSono;
    private ImageView imgVoltarPrincipal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_sono);

        usuarioId = getIntent().getIntExtra("usuario_id", -1);
        bebeId = getIntent().getIntExtra("bebe_id", -1);

        editTextHoraInicio = findViewById(R.id.edtHoraQueDormiu);
        editTextHoraFim = findViewById(R.id.edtHoraQueAcordou);
        editTextNotas = findViewById(R.id.edtNotas);

        btnSalvarCadastroSono = findViewById(R.id.btnCadastroSono);
        imgVoltarPrincipal = findViewById(R.id.imagemVoltarDoCadastroSono);

        imgVoltarPrincipal.setOnClickListener(v -> {
            Intent intent = new Intent(CadastroSonoActivity.this, TelaPrincipalActivity.class);
            intent.putExtra("usuario_id", usuarioId);
            intent.putExtra("bebe_id", bebeId);
            startActivity(intent);
            finish();
        });

        btnSalvarCadastroSono.setOnClickListener(v -> salvarSono());
    }

    private void salvarSono() {
        String horaInicio = editTextHoraInicio.getText().toString().trim();
        String horaFim = editTextHoraFim.getText().toString().trim();
        String notas = editTextNotas.getText().toString().trim(); // Pode ser vazio (NULL)

        if (horaInicio.isEmpty() || horaFim.isEmpty()) {
            Toast.makeText(this, "Preencha os horários de início e fim!", Toast.LENGTH_SHORT).show();
            return;
        }

        SimpleDateFormat formatoBanco = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm", Locale.getDefault());

        String inicioBanco, fimBanco;

        try {
            // Data de hoje no formato yyyy-MM-dd
            Date hoje = new Date();
            SimpleDateFormat formatoData = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String dataHoje = formatoData.format(hoje);

            // Validar formato das horas
            formatoHora.parse(horaInicio);
            formatoHora.parse(horaFim);

            // Montar os campos no formato yyyy-MM-dd HH:mm:ss
            inicioBanco = dataHoje + " " + horaInicio + ":00";
            fimBanco = dataHoje + " " + horaFim + ":00";

            // Validação final (opcional)
            formatoBanco.parse(inicioBanco);
            formatoBanco.parse(fimBanco);

        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "Formato de hora inválido! Use HH:mm", Toast.LENGTH_SHORT).show();
            return;
        }

        ConexaoDB dbHelper = new ConexaoDB(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("bebe_id", bebeId);
        values.put("inicio", inicioBanco);
        values.put("fim", fimBanco);

        if (!notas.isEmpty()) {
            values.put("notas", notas);
        }

        long resultado = db.insert("sono", null, values);

        if (resultado == -1) {
            Toast.makeText(this, "Erro ao salvar sono.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Sono salvo com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        }

        db.close();
    }
}
