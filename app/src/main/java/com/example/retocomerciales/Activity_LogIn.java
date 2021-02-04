package com.example.retocomerciales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.retocomerciales.Clases.Comercial;
import com.example.retocomerciales.Clases.Datos;

public class Activity_LogIn extends AppCompatActivity {

    TextView user, password;
    Button logIn;
    Datos datos;
    String _user, _password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_log_in);

        user = findViewById(R.id.txt_user);
        password = findViewById(R.id.txt_contrasena);
        logIn = findViewById(R.id.btn_login);

        datos = Datos.getInstance();
        datos.setMainActivityElements(getResources(), getBaseContext());

        if (DBJustCreated.getInstance().isDbJustCreated()){
            datos.cargarAssets();
            datos.insertAll(datos.getDb());
        }else{
            datos.cargarDatosDesdeBD(getBaseContext());
        }

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _user = user.getText().toString();
                _password = password.getText().toString();
                if (_user.length() < 1 || _password.length() < 1){
                    Toast.makeText(getApplicationContext(), "Escribe usuario y contraseña", Toast.LENGTH_SHORT).show();
                }else{
                    loggeado();
                }
            }
        });
    }
    public void loggeado(){
        boolean existe = false;
        int pos = 0;

        for (Comercial comercial: datos.getComerciales()){

            if(comercial.getUsuario().equals(_user) && comercial.getPassword().equals(_password)){
                existe = true;
                break;
            }
            pos++;
        }
        if (existe){
            datos.setPosComercial(pos);

            Intent intent = new Intent(Activity_LogIn.this, MainActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(), "Usuario y/o contraseña erróneos", Toast.LENGTH_SHORT).show();
        }
    }
}