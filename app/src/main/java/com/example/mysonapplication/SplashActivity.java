package com.example.mysonapplication;

import android.content.Intent;
import android.content.SharedPreferences;
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

        // Buscar usuarioId salvo nas SharedPreferences (sessão)
        SharedPreferences prefs = getSharedPreferences("MySonAppPrefs", MODE_PRIVATE);
        usuarioId = prefs.getInt("usuario_logado_id", -1);

        // Aguarda 3 segundos e redireciona
        new Handler().postDelayed(() -> {
            Intent intent;

            if (usuarioId == -1) {
                // Usuário não logado, vai para tela de login
                intent = new Intent(this, TelaLoginActivity.class);
            } else if (temBebeCadastrado()) {
                // Usuário logado e tem bebê cadastrado, vai para tela principal
                intent = new Intent(this, TelaPrincipalActivity.class);
                intent.putExtra("usuario_id", usuarioId);
            } else {
                // Usuário logado, mas sem bebê cadastrado, vai para Wizard
                intent = new Intent(this, WizardActivity.class);
                intent.putExtra("usuario_id", usuarioId);
            }

            startActivity(intent);
            finish();
        }, 3000);
    }

    private boolean temBebeCadastrado() {
        if (usuarioId == -1) return false;

        ConexaoDB dbHelper = new ConexaoDB(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM bebe WHERE usuario_id = ?", new String[]{String.valueOf(usuarioId)});
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
