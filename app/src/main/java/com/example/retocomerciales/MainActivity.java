package com.example.retocomerciales;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.retocomerciales.Clases.Datos;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    Button _iniciar,llamar,correo;
    Intent intent;
    private GoogleMap mMap;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            checkExternalStoragePermission();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE); //ocultar el menu de navegación

        _iniciar=findViewById(R.id.btn_iniciar);
        llamar = findViewById(R.id.btnLlamar);
        correo = findViewById(R.id.btnCorreo);

        Datos.getInstance(getResources());

        llamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:688847776"));
                startActivity(intent);
            }
        });

        correo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearCorreo();
            }
        });


        _iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, activity_menu.class);
                startActivity(intent);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(43.30472446500069, -2.0168812128018994), 15.0f));
        // Add a marker in Sydney and move the camera
        LatLng cebanc = new LatLng(43.30472446500069, -2.0168812128018994);
        mMap.addMarker(new MarkerOptions().position(cebanc).title("Cebanc"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(cebanc));

        LatLng ibermatica = new LatLng(43.287434143240006, -1.9855139867497127);
        mMap.addMarker(new MarkerOptions().position(ibermatica).title("Ibermatica"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ibermatica));

        LatLng dosSystem = new LatLng(43.323055210313285, -1.9822306305925652);
        mMap.addMarker(new MarkerOptions().position(dosSystem).title("DosSystem"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(dosSystem));
    }

    public void crearCorreo() {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","pistachophone@gmail.com", null));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
 ///////////////////////////////////////////////////////////////////////////////////////////////////////

    private void checkExternalStoragePermission() throws IOException {
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "No Tiene permisos", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 225);

        } else {
            Toast.makeText(getApplicationContext(), "Tiene permisos", Toast.LENGTH_SHORT).show();
            writeToExternalStorage();
        }

    }

    private void writeToExternalStorage() throws IOException {

        int escribe = 6;
        File file = new File(Environment.getExternalStorageDirectory() + "/productos.xml");

        if(file.exists()){
            Toast.makeText(getApplicationContext(), "Existe ", Toast.LENGTH_SHORT).show();
            FileOutputStream f = new FileOutputStream(file);
            f.write(escribe);

            Toast.makeText(getApplicationContext(), "Escrito", Toast.LENGTH_SHORT).show();
            f.close();
        }else{
            Toast.makeText(getApplicationContext(), "No existe", Toast.LENGTH_SHORT).show();
        }



    }

}