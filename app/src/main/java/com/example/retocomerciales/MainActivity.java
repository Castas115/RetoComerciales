package com.example.retocomerciales;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
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

import java.io.IOException;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    Button _iniciar,llamar,correo;
    Intent intent;
    private GoogleMap mMap;
    Spinner spComercial;
    String telf, emailDelegacion;
    TextView nomDelegacion;

    //permisos de almacenamiento
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //comprobar permisos
        try {
            checkExternalStoragePermission();
        } catch (IOException e) {
            e.printStackTrace();
        }

        _iniciar=findViewById(R.id.btn_iniciar);
        llamar = findViewById(R.id.btnLlamar);
        correo = findViewById(R.id.btnCorreo);
        spComercial = findViewById(R.id.spn_eligeComercial);
        nomDelegacion = findViewById(R.id.lbl_nomDelegacion);

        //cargar los datos necesarios para la clase Datos
        final Datos datos = Datos.getInstance(getResources(), getBaseContext());
        datos.cargarAssets();

        //Spinner Comerciales
        final ArrayAdapter adapterComerciales = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, datos.getNombresComerciales());
        adapterComerciales.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spComercial.setAdapter(adapterComerciales);

        spComercial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                datos.setPosComercial(position);

                nomDelegacion.setText("Delegación provincial " + datos.getComercial(position).getDelegacion());
                emailDelegacion = datos.getComercial(position).getEmailDelegacion();
                telf = datos.getComercial(position).getTelefonoDelegacion();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //llamada
        llamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + telf));
                startActivity(intent);
            }
        });

        //correo a delegación
        correo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearCorreo();
            }
        });

        //botón iniciar
        _iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, activity_menu.class);
                startActivity(intent);
            }
        });

        //fragmento mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    //obicaciones marcadas en le mapa
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
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", emailDelegacion, null));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
 ///////////////////////////////////////////////////////////////////////////////////////////////////////

    private void checkExternalStoragePermission() throws IOException {
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "Acepta los permisos", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 225);

        }

    }


}