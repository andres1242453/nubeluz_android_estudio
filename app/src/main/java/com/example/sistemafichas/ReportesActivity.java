package com.example.sistemafichas;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ReportesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes);

        ListView lvPedidos = findViewById(R.id.lvPedidos);
        Button btnVolver = findViewById(R.id.btnVolverReportes);

        try (DBHelper dbHelper = new DBHelper(this)) {
            List<String> pedidos = dbHelper.obtenerTodosLosPedidos();
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, pedidos);
            lvPedidos.setAdapter(adapter);
        }

        btnVolver.setOnClickListener(v -> finish());
    }
}
