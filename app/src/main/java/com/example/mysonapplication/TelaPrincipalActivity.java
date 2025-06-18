package com.example.mysonapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TelaPrincipalActivity extends MudarTemaActivity {
    private int usuarioId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

        usuarioId = getIntent().getIntExtra("usuario_id", -1);


        // views para o relatorio
        ImageView imgViewRelatorioAlimentacao = findViewById(R.id.imageViewAlimentacao);
        ImageView imgViewRelatorioSono = findViewById(R.id.imageViewSono);
        //views para o cadastro
        TextView txtViewCadatroAlimentacao = findViewById(R.id.txtCadastrarAlimentacao);
        TextView txtViewCadatroSono = findViewById(R.id.txtCadastrarSono);
        //Imageview para o logout
        ImageView imgViewLogout = findViewById(R.id.imageViewLogout);
        ImageView imgIrPerfil = findViewById(R.id.imagemIrPerfil);
        //Data de hoje
        TextView txtDataAtual = findViewById(R.id.txtData);
        TextView txtIdadeBebe = findViewById(R.id.txtIdadeBebe);


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));
        dateFormat.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
        String dataAtual = dateFormat.format(new Date());
        txtDataAtual.setText(dataAtual);

        String dataNascimento = buscarDataNascimentoBebe(usuarioId);

        if (dataNascimento != null) {
            String idadeFormatada = calcularIdade(dataNascimento);
            txtIdadeBebe.setText(idadeFormatada);
        } else {
            txtIdadeBebe.setText("Bebê não cadastrado");
        }


        //direcionando para o relatorio de alimentação
        imgViewRelatorioAlimentacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        TelaPrincipalActivity.this,
                        RelatorioAlimentacaoActivity.class
                );
                startActivity(intent);
            }
        });
        //direcionando para o relatorio de sono
        imgViewRelatorioSono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        TelaPrincipalActivity.this,
                        RelatorioSonoActivity.class
                );
                startActivity(intent);
            }
        });
        //direciona para o cadastro da alimentação
        txtViewCadatroAlimentacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        TelaPrincipalActivity.this,
                        CadastroAlimentacaoActivity.class
                );
                startActivity(intent);
            }
        });
        // direciona para o cadastro do sono
        txtViewCadatroSono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        TelaPrincipalActivity.this,
                        CadastroSonoActivity.class
                );
                startActivity(intent);
            }
        });
        // Intent de logout
        imgViewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        TelaPrincipalActivity.this,
                        TelaLoginActivity.class
                );
                startActivity(intent);
            }
        });
        // Direcionada para o Perfil
        imgIrPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        TelaPrincipalActivity.this,
                        TelaPerfilActivity.class
                );
                intent.putExtra("usuario_id", usuarioId);
                startActivity(intent);
            }
        });



        
    }
    private String buscarDataNascimentoBebe(int usuarioId) {
        ConexaoDB dbHelper = new ConexaoDB(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String dataNascimento = null;

        Cursor cursor = db.rawQuery("SELECT data_nascimento FROM bebe WHERE usuario_id = ?", new String[]{String.valueOf(usuarioId)});
        if (cursor.moveToFirst()) {
            dataNascimento = cursor.getString(0);
        }

        cursor.close();
        db.close();

        return dataNascimento;
    }

    private String calcularIdade(String dataNascimento) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date nascimento = sdf.parse(dataNascimento);
            Date hoje = new Date();

            long diferencaMillis = hoje.getTime() - nascimento.getTime();
            long diasTotais = diferencaMillis / (1000 * 60 * 60 * 24);

            int anos = (int) (diasTotais / 365);
            int meses = (int) ((diasTotais % 365) / 30);
            int semanas = (int) (((diasTotais % 365) % 30) / 7);
            int dias = (int) (((diasTotais % 365) % 30) % 7);

            StringBuilder idadeStr = new StringBuilder();

            if (anos > 0) idadeStr.append(anos).append(anos == 1 ? " ano, " : " anos, ");
            if (meses > 0) idadeStr.append(meses).append(meses == 1 ? " mês, " : " meses, ");
            if (semanas > 0) idadeStr.append(semanas).append(semanas == 1 ? " semana, " : " semanas, ");
            if (dias > 0 || idadeStr.length() == 0) idadeStr.append(dias).append(dias == 1 ? " dia" : " dias");

            // Remover vírgula final, se tiver
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
}