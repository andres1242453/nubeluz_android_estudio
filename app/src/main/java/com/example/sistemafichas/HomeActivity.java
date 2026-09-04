package com.example.sistemafichas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private Button btnClientes;
    private Button btnFichas;
    private Button btnPedidos;
    private Button btnReportes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnClientes = findViewById(R.id.btnClientes);
        btnFichas = findViewById(R.id.btnFichas);
        btnPedidos = findViewById(R.id.btnPedidos);
        btnReportes = findViewById(R.id.btnReportes);
        Button btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        btnClientes.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ClientesActivity.class);
            startActivity(intent);
        });

        btnFichas.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, FichaActivity.class);
            startActivity(intent);
        });

        btnPedidos.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, PedidoActivity.class);
            startActivity(intent);
        });

        btnReportes.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ReportesActivity.class);
            startActivity(intent);
        });

        btnCerrarSesion.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
