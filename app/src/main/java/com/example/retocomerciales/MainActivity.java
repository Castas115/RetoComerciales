package com.example.retocomerciales;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.example.retocomerciales.Clases.Partner;
import com.example.retocomerciales.Clases.Producto;
import com.example.retocomerciales.Clases.ReadXML;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    Button _iniciar,llamar,correo;
    Intent intent;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE); //ocultar el menu de navegación

        _iniciar=findViewById(R.id.btn_iniciar);
        llamar = findViewById(R.id.btnLlamar);
        correo = findViewById(R.id.btnCorreo);

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

}