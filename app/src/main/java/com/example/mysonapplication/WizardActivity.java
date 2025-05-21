package com.example.mysonapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
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

        // Variável para armazenar a imagem selecionada
        final ImageView[] selectedImage = {null};

        imgMenino.setOnClickListener(v ->{

            if (selectedImage[0] != null) {
                selectedImage[0].setBackgroundColor(Color.TRANSPARENT);
            }
            imgMenino.setBackgroundColor(Color.BLUE);
            selectedImage[0] = imgMenino;
        });

        imgMenina.setOnClickListener(v ->{

            if (selectedImage[0] != null) {
                selectedImage[0].setBackgroundColor(Color.TRANSPARENT);
            }
            imgMenina.setBackgroundColor(Color.MAGENTA);
            selectedImage[0] = imgMenina;
        });


    }
}