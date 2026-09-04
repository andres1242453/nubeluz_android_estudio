package com.example.sistemafichas;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sistemafichas.model.Cliente;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PedidoActivity extends AppCompatActivity {

    private EditText edtDoc;
    private EditText edtFecha;
    private EditText edtTotal;

    private TextView tvNombre;

    private DBHelper dbHelper;

    private boolean clienteVerificado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pedidos);

        dbHelper = new DBHelper(this);

        // =========================================
        // CAMPOS
        // =========================================

        edtDoc = findViewById(R.id.edtDocClientePedido);
        edtFecha = findViewById(R.id.edtFechaPedido);
        edtTotal = findViewById(R.id.edtTotalPedido);

        tvNombre = findViewById(R.id.tvNombreClientePedido);

        // =========================================
        // BOTONES
        // =========================================

        Button btnVerificar =
                findViewById(R.id.btnVerificarCliente);

        Button btnGuardar =
                findViewById(R.id.btnGuardarPedido);

        // =========================================
        // FECHA ACTUAL
        // =========================================

        String fechaActual =
                new SimpleDateFormat(
                        "dd/MM/yyyy",
                        Locale.getDefault()
                ).format(new Date());

        edtFecha.setText(fechaActual);

        // =========================================
        // EVENTOS
        // =========================================

        btnVerificar.setOnClickListener(
                v -> verificarCliente()
        );

        btnGuardar.setOnClickListener(
                v -> guardarPedido()
        );
    }

    // =====================================================
    // VERIFICAR CLIENTE
    // =====================================================

    private void verificarCliente() {

        String documento =
                edtDoc.getText().toString().trim();

        if (documento.isEmpty()) {

            edtDoc.setError(
                    "Ingrese el documento"
            );

            edtDoc.requestFocus();

            return;
        }

        Cliente cliente =
                dbHelper.buscarCliente(documento);

        if (cliente != null) {

            tvNombre.setText(
                    "Cliente: " + cliente.getNombre()
            );

            clienteVerificado = true;

            Toast.makeText(
                    this,
                    "Cliente verificado correctamente",
                    Toast.LENGTH_SHORT
            ).show();

        } else {

            tvNombre.setText(
                    "Cliente no encontrado"
            );

            clienteVerificado = false;

            Toast.makeText(
                    this,
                    "Debe registrar el cliente primero",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    // =====================================================
    // GUARDAR PEDIDO
    // =====================================================

    private void guardarPedido() {

        // -----------------------------------------
        // VERIFICAR CLIENTE
        // -----------------------------------------

        if (!clienteVerificado) {

            Toast.makeText(
                    this,
                    "Primero debe verificar el cliente",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        // -----------------------------------------
        // OBTENER DATOS
        // -----------------------------------------

        String documento =
                edtDoc.getText().toString().trim();

        String fecha =
                edtFecha.getText().toString().trim();

        String totalTexto =
                edtTotal.getText().toString().trim();

        // -----------------------------------------
        // VALIDAR FECHA
        // -----------------------------------------

        if (fecha.isEmpty()) {

            edtFecha.setError(
                    "Ingrese la fecha"
            );

            edtFecha.requestFocus();

            return;
        }

        // -----------------------------------------
        // VALIDAR TOTAL
        // -----------------------------------------

        if (totalTexto.isEmpty()) {

            edtTotal.setError(
                    "Ingrese el total"
            );

            edtTotal.requestFocus();

            return;
        }

        // -----------------------------------------
        // CONVERTIR TOTAL
        // -----------------------------------------

        double total;

        try {

            total = Double.parseDouble(
                    totalTexto
            );

        } catch (NumberFormatException e) {

            edtTotal.setError(
                    "Ingrese un número válido"
            );

            edtTotal.requestFocus();

            return;
        }

        // -----------------------------------------
        // VALIDAR TOTAL POSITIVO
        // -----------------------------------------

        if (total <= 0) {

            edtTotal.setError(
                    "El total debe ser mayor que 0"
            );

            edtTotal.requestFocus();

            return;
        }

        // -----------------------------------------
        // GUARDAR EN BASE DE DATOS
        // -----------------------------------------

        long id =
                dbHelper.insertarPedido(
                        documento,
                        fecha,
                        "PENDIENTE",
                        total
                );

        if (id != -1) {

            Toast.makeText(
                    this,
                    "Pedido #" + id +
                            " creado con éxito",
                    Toast.LENGTH_LONG
            ).show();

            limpiarPedido();

        } else {

            Toast.makeText(
                    this,
                    "No se pudo crear el pedido",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    // =====================================================
    // LIMPIAR PEDIDO
    // =====================================================

    private void limpiarPedido() {

        edtDoc.setText("");
        edtTotal.setText("");

        tvNombre.setText(
                "Cliente no seleccionado"
        );

        clienteVerificado = false;

        String fechaActual =
                new SimpleDateFormat(
                        "dd/MM/yyyy",
                        Locale.getDefault()
                ).format(new Date());

        edtFecha.setText(fechaActual);

        edtDoc.requestFocus();
    }
}