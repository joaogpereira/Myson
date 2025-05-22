package com.example.mysonapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class WizardActivity extends AppCompatActivity {

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


        imgMenino.setOnClickListener(v -> {
            toolbarMenino.setBackgroundColor(ContextCompat.getColor(WizardActivity.this, R.color.azul_bebe));
        });

        imgMenina.setOnClickListener(v -> {


        });


    }
}