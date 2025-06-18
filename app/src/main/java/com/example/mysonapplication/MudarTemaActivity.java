package com.example.mysonapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import java.util.Calendar;

public class MudarTemaActivity extends AppCompatActivity {

    private static int ultimoModoAplicado = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        verificarEAplicarTema();
        super.onCreate(savedInstanceState);
    }

    private void verificarEAplicarTema() {
        int hora = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

        int novoModo = (hora < 9 || hora >= 17)
                ? AppCompatDelegate.MODE_NIGHT_YES
                : AppCompatDelegate.MODE_NIGHT_NO;

        if (novoModo != ultimoModoAplicado) {
            AppCompatDelegate.setDefaultNightMode(novoModo);
            ultimoModoAplicado = novoModo;

        }
    }
}
