package com.example.sistemafichas;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sistemafichas.model.Cliente;

import java.util.List;

public class ClientesActivity extends AppCompatActivity {

    private EditText edtNombre;
    private EditText edtDocumento;
    private EditText edtTelefono;
    private EditText edtCorreo;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_clientes);

        dbHelper = new DBHelper(this);

        // -----------------------------------------
        // CAMPOS
        // -----------------------------------------

        edtNombre = findViewById(R.id.edtNombreCliente);
        edtDocumento = findViewById(R.id.edtDocumentoCliente);
        edtTelefono = findViewById(R.id.edtTelefonoCliente);
        edtCorreo = findViewById(R.id.edtCorreoCliente);

        // -----------------------------------------
        // BOTONES
        // -----------------------------------------

        Button btnGuardar =
                findViewById(R.id.btnGuardarCliente);

        Button btnConsultar =
                findViewById(R.id.btnConsultarClientes);

        Button btnActualizar =
                findViewById(R.id.btnActualizarCliente);

        Button btnEliminar =
                findViewById(R.id.btnEliminarCliente);

        // -----------------------------------------
        // EVENTOS
        // -----------------------------------------

        btnGuardar.setOnClickListener(
                v -> guardarCliente()
        );

        btnConsultar.setOnClickListener(
                v -> consultarClientes()
        );

        btnActualizar.setOnClickListener(
                v -> actualizarCliente()
        );

        btnEliminar.setOnClickListener(
                v -> confirmarEliminar()
        );
    }

    // =========================================================
    // GUARDAR
    // =========================================================

    private void guardarCliente() {

        String nombre =
                edtNombre.getText().toString().trim();

        String documento =
                edtDocumento.getText().toString().trim();

        String telefono =
                edtTelefono.getText().toString().trim();

        String correo =
                edtCorreo.getText().toString().trim();

        // VALIDAR NOMBRE
        if (nombre.isEmpty()) {

            edtNombre.setError(
                    "Ingrese el nombre"
            );

            edtNombre.requestFocus();

            return;
        }

        // VALIDAR DOCUMENTO
        if (documento.isEmpty()) {

            edtDocumento.setError(
                    "Ingrese el documento"
            );

            edtDocumento.requestFocus();

            return;
        }

        // CREAR CLIENTE
        Cliente cliente = new Cliente(
                nombre,
                documento,
                telefono,
                correo
        );

        // GUARDAR
        long resultado =
                dbHelper.insertarCliente(cliente);

        if (resultado != -1) {

            Toast.makeText(
                    this,
                    "Cliente guardado con éxito",
                    Toast.LENGTH_SHORT
            ).show();

            limpiarCampos();

        } else {

            Toast.makeText(
                    this,
                    "No se pudo guardar. El documento puede estar registrado.",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    // =========================================================
    // CONSULTAR UN CLIENTE
    // =========================================================

    private void consultarCliente() {

        String documento =
                edtDocumento.getText().toString().trim();

        if (documento.isEmpty()) {

            edtDocumento.setError(
                    "Ingrese el documento"
            );

            edtDocumento.requestFocus();

            return;
        }

        Cliente cliente =
                dbHelper.buscarCliente(documento);

        if (cliente != null) {

            edtNombre.setText(
                    cliente.getNombre()
            );

            edtTelefono.setText(
                    cliente.getTelefono()
            );

            edtCorreo.setText(
                    cliente.getCorreo()
            );

            Toast.makeText(
                    this,
                    "Cliente encontrado",
                    Toast.LENGTH_SHORT
            ).show();

        } else {

            Toast.makeText(
                    this,
                    "Cliente no registrado",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    // =========================================================
    // CONSULTAR TODOS LOS CLIENTES
    // =========================================================

    private void consultarClientes() {

        List<Cliente> clientes =
                dbHelper.obtenerTodosLosClientes();

        // NO HAY CLIENTES
        if (clientes.isEmpty()) {

            new AlertDialog.Builder(this)
                    .setTitle("CLIENTES REGISTRADOS")
                    .setMessage(
                            "No hay clientes registrados."
                    )
                    .setPositiveButton(
                            "Aceptar",
                            null
                    )
                    .show();

            return;
        }

        // CREAR TEXTO
        StringBuilder informacion =
                new StringBuilder();

        for (Cliente cliente : clientes) {

            informacion.append(
                    "Nombre: "
            ).append(
                    cliente.getNombre()
            ).append("\n");

            informacion.append(
                    "Documento: "
            ).append(
                    cliente.getDocumento()
            ).append("\n");

            informacion.append(
                    "Teléfono: "
            ).append(
                    cliente.getTelefono()
            ).append("\n");

            informacion.append(
                    "Correo: "
            ).append(
                    cliente.getCorreo()
            ).append("\n");

            informacion.append(
                    "-----------------------------\n"
            );
        }

        // MOSTRAR CLIENTES
        new AlertDialog.Builder(this)
                .setTitle("CLIENTES REGISTRADOS")
                .setMessage(
                        informacion.toString()
                )
                .setPositiveButton(
                        "Cerrar",
                        null
                )
                .show();
    }

    // =========================================================
    // ACTUALIZAR
    // =========================================================

    private void actualizarCliente() {

        String documento =
                edtDocumento.getText().toString().trim();

        String nombre =
                edtNombre.getText().toString().trim();

        String telefono =
                edtTelefono.getText().toString().trim();

        String correo =
                edtCorreo.getText().toString().trim();

        if (documento.isEmpty()) {

            edtDocumento.setError(
                    "Ingrese el documento"
            );

            edtDocumento.requestFocus();

            return;
        }

        if (nombre.isEmpty()) {

            edtNombre.setError(
                    "Ingrese el nombre"
            );

            edtNombre.requestFocus();

            return;
        }

        Cliente cliente = new Cliente(
                nombre,
                documento,
                telefono,
                correo
        );

        int resultado =
                dbHelper.actualizarCliente(cliente);

        if (resultado > 0) {

            Toast.makeText(
                    this,
                    "Cliente actualizado correctamente",
                    Toast.LENGTH_SHORT
            ).show();

        } else {

            Toast.makeText(
                    this,
                    "No se encontró el cliente",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    // =========================================================
    // CONFIRMAR ELIMINACIÓN
    // =========================================================

    private void confirmarEliminar() {

        String documento =
                edtDocumento.getText().toString().trim();

        if (documento.isEmpty()) {

            edtDocumento.setError(
                    "Ingrese el documento"
            );

            edtDocumento.requestFocus();

            return;
        }

        new AlertDialog.Builder(this)
                .setTitle("Eliminar cliente")
                .setMessage(
                        "¿Está seguro de eliminar el cliente con documento "
                                + documento
                                + "?"
                )
                .setPositiveButton(
                        "Sí",
                        (dialog, which) ->
                                eliminarCliente(documento)
                )
                .setNegativeButton(
                        "No",
                        null
                )
                .show();
    }

    // =========================================================
    // ELIMINAR
    // =========================================================

    private void eliminarCliente(
            String documento) {

        int resultado =
                dbHelper.eliminarCliente(documento);

        if (resultado > 0) {

            Toast.makeText(
                    this,
                    "Cliente eliminado con éxito",
                    Toast.LENGTH_SHORT
            ).show();

            limpiarCampos();

        } else {

            Toast.makeText(
                    this,
                    "No se encontró el cliente",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    // =========================================================
    // LIMPIAR CAMPOS
    // =========================================================

    private void limpiarCampos() {

        edtNombre.setText("");
        edtDocumento.setText("");
        edtTelefono.setText("");
        edtCorreo.setText("");

        edtNombre.requestFocus();
    }
}