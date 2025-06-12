package com.example.mysonapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WizardActivity extends MudarTemaActivity {

    private int usuarioId = -1;
    private String sexoSelecionado = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wizard);

        usuarioId = getIntent().getIntExtra("usuario_id", -1);

        // Identificadores dos campos
        EditText edtNomeBebe = findViewById(R.id.edt_nome_bebe);
        EditText edtDataNascimentoBebe = findViewById(R.id.edt_idade_bebe); // novo campo para data

        ImageView imgMenino = findViewById(R.id.img_menino);
        ImageView imgMenina = findViewById(R.id.img_menina);

        Toolbar toolbarMenino = findViewById(R.id.materialToolbarMenino);
        Toolbar toolbarMenina = findViewById(R.id.materialToolbarMenina);

        Button btnProximoWizard = findViewById(R.id.btnProximoWizard);

        final Toolbar[] selectedToolbar = {null};

        imgMenino.setOnClickListener(v -> {
            if (selectedToolbar[0] != null) {
                GradientDrawable bg = (GradientDrawable) selectedToolbar[0].getBackground();
                bg.setColor(ContextCompat.getColor(this, R.color.Cor_container));
            }
            GradientDrawable bgMenino = (GradientDrawable) toolbarMenino.getBackground();
            bgMenino.setColor(ContextCompat.getColor(this, R.color.azul_bebe));
            selectedToolbar[0] = toolbarMenino;
            sexoSelecionado = "Masculino";
        });

        imgMenina.setOnClickListener(v -> {
            if (selectedToolbar[0] != null) {
                GradientDrawable bg = (GradientDrawable) selectedToolbar[0].getBackground();
                bg.setColor(ContextCompat.getColor(this, R.color.Cor_container));
            }
            GradientDrawable bgMenina = (GradientDrawable) toolbarMenina.getBackground();
            bgMenina.setColor(ContextCompat.getColor(this, R.color.rosa_claro));
            selectedToolbar[0] = toolbarMenina;
            sexoSelecionado = "Feminino";
        });

        btnProximoWizard.setOnClickListener(v -> {
            String nomeBebe = edtNomeBebe.getText().toString();
            String dataNascimentoStr = edtDataNascimentoBebe.getText().toString();

            if (nomeBebe.isEmpty() || dataNascimentoStr.isEmpty() || sexoSelecionado.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos e selecione o sexo", Toast.LENGTH_SHORT).show();
                return;
            }

            // Converter a data para formato yyyy-MM-dd para salvar no banco
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dataNascimentoDb;

            try {
                Date data = inputFormat.parse(dataNascimentoStr);
                dataNascimentoDb = dbFormat.format(data);
            } catch (ParseException e) {
                Toast.makeText(this, "Formato de data inválido. Use dd/MM/yyyy", Toast.LENGTH_SHORT).show();
                return;
            }

            ConexaoDB dbHelper = new ConexaoDB(this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("nome", nomeBebe);
            values.put("data_nascimento", dataNascimentoDb);
            values.put("sexo", sexoSelecionado);
            values.put("usuario_id", usuarioId);

            long resultado = db.insert("bebe", null, values);
            db.close();

            if (resultado != -1) {
                Toast.makeText(this, "Bebê cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, TelaPrincipalActivity.class);
                intent.putExtra("usuario_id", usuarioId);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Erro ao cadastrar bebê", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
