package com.example.mysonapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CadastroAlimentacaoActivity extends MudarTemaActivity {

    private int usuarioId;
    private int bebeId;


    private EditText editTextData;
    private EditText editTextHora;
    private EditText editTextTipoAlimentacao;
    private EditText editTextVolume;

    private Button btnSalvarCadastroAlimentacao;
    private ImageView imgVoltarPrincipal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_alimentacao);

        usuarioId = getIntent().getIntExtra("usuario_id", -1);
        bebeId = getIntent().getIntExtra("bebe_id", -1);

        editTextData = findViewById(R.id.edtDataCadastroAlimentacao);
        editTextHora = findViewById(R.id.edtCadastroHoraAlimentacao);
        editTextTipoAlimentacao = findViewById(R.id.edtCadastroTipoAlimentacao);
        editTextVolume = findViewById(R.id.edtCadastroVolumeAlimentacao);

        btnSalvarCadastroAlimentacao = findViewById(R.id.btnSalvarCadastroAlimentacao);
        imgVoltarPrincipal = findViewById(R.id.imagemVoltarDoCadastroAlimentacao);

        imgVoltarPrincipal.setOnClickListener(v -> {
            Intent intent = new Intent(CadastroAlimentacaoActivity.this, TelaPrincipalActivity.class);
            intent.putExtra("usuario_id", usuarioId);
            intent.putExtra("bebe_id", bebeId);
            startActivity(intent);
            finish();
        });

        btnSalvarCadastroAlimentacao.setOnClickListener(v -> salvarAlimentacao());
    }

    private void salvarAlimentacao() {
        String data = editTextData.getText().toString().trim();
        String hora = editTextHora.getText().toString().trim();
        String tipoAlimentacao = editTextTipoAlimentacao.getText().toString().trim();
        String volumeStr = editTextVolume.getText().toString().trim();

        if (data.isEmpty() || hora.isEmpty() || tipoAlimentacao.isEmpty() || volumeStr.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validar e montar a string de data_hora para salvar no banco no formato: "yyyy-MM-dd HH:mm:ss"
        SimpleDateFormat formatoEntradaData = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat formatoBanco = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        String dataHoraBanco;

        try {
            Date dataObj = formatoEntradaData.parse(data);

            // Vamos montar a string data no formato yyyy-MM-dd
            String dataFormatada = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(dataObj);

            // Completar com a hora e segundos fixos ":00"
            dataHoraBanco = dataFormatada + " " + hora + ":00";

            // Validar se dataHoraBanco realmente pode ser parseada (opcional)
            formatoBanco.parse(dataHoraBanco);
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "Data ou hora inválida!", Toast.LENGTH_SHORT).show();
            return;
        }

        int volume;
        try {
            volume = Integer.parseInt(volumeStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Volume inválido!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Agora vamos salvar no banco
        ConexaoDB dbHelper = new ConexaoDB(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("bebe_id", bebeId);
        values.put("data_hora", dataHoraBanco);
        values.put("tipo_alimentacao", tipoAlimentacao);
        values.put("volume", volume);

        long resultado = db.insert("alimentacao", null, values);

        if (resultado == -1) {
            Toast.makeText(this, "Erro ao salvar alimentação.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Alimentação salva com sucesso!", Toast.LENGTH_SHORT).show();
            finish(); // Fecha a activity após salvar
        }

        db.close();
    }
}
