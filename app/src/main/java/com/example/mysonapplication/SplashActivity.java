package com.example.mysonapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends MudarTemaActivity {

    private int usuarioId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        usuarioId = getIntent().getIntExtra("usuario_id", -1);

        // Aguarda 3 segundos e redireciona
        new Handler().postDelayed(() -> {
            Intent intent;
            if (temBebeCadastrado()) {
                // Se já tem bebê, vai para tela principal
                intent = new Intent(this, TelaPrincipalActivity.class);
            } else {
                // Se não tem, vai para Wizard
                intent = new Intent(this, WizardActivity.class);
            }

            // Passa o ID do usuário para a próxima tela
            intent.putExtra("usuario_id", usuarioId);
            startActivity(intent);
            finish(); // Finaliza a SplashActivity
        }, 3000);
    }

    // Verifica se há algum bebê cadastrado no banco
    private boolean temBebeCadastrado() {
        ConexaoDB dbHelper = new ConexaoDB(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM bebe", null);
        boolean existe = false;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int total = cursor.getInt(0);
                existe = total > 0;
            }
            cursor.close();
        }

        db.close();
        return existe;
    }
}
