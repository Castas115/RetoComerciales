package com.example.retocomerciales;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class activity_gestionPartner extends AppCompatActivity {


    Button alta,volver;
    Intent intent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_gestion_partner);

        //findViewById
        alta = findViewById(R.id.btnAlta);
        volver = findViewById(R.id.btnVolver);


        alta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(activity_gestionPartner.this, activity_addPartner.class);
                startActivity(intent);
            }
        });

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(activity_gestionPartner.this, activity_menu.class);
                startActivity(intent);
            }
        });

    }


}