package com.example.mysonapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends MudarTemaActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Aguarda 2 segundos e redireciona
        new Handler().postDelayed(() -> {
            if (temBebeCadastrado()) {
                // Se já tem bebê, vai para tela principal
                startActivity(new Intent(this, TelaPrincipalActivity.class));
            } else {
                // Se não tem, vai para Wizard
                startActivity(new Intent(this, WizardActivity.class));
            }
            finish(); // Finaliza a SplashActivity
        }, 2000);
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
