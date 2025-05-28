package com.example.mysonapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class WizardActivity extends MudarTemaActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wizard);


        EditText edtNomeBebe = findViewById(R.id.edt_nome_bebe);
        EditText edtIdadeBebe = findViewById(R.id.edt_idade_bebe);

        ImageView imgMenino = findViewById(R.id.img_menino);
        ImageView imgMenina = findViewById(R.id.img_menina);

        Toolbar toolbarMenino = findViewById(R.id.materialToolbarMenino);
        Toolbar toolbarMenina = findViewById(R.id.materialToolbarMenina);

        final Toolbar[] selectedToolbar = {null};


        imgMenino.setOnClickListener(v -> {
            if (selectedToolbar[0] != null) {
                selectedToolbar[0].setBackgroundColor(Color.TRANSPARENT);
            }
            toolbarMenino.setBackgroundColor(ContextCompat.getColor(WizardActivity.this, R.color.azul_bebe));
            selectedToolbar[0] = toolbarMenino;
        });

        imgMenina.setOnClickListener(v -> {
            if (selectedToolbar[0] != null) {
                selectedToolbar[0].setBackgroundColor(Color.TRANSPARENT);
            }

            toolbarMenina.setBackgroundColor(ContextCompat.getColor(WizardActivity.this, R.color.rosa_claro));
            selectedToolbar[0] = toolbarMenina;
        });


    }
}