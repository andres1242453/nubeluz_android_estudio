package com.example.sistemafichas;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sistemafichas.model.Ficha;

public class FichaActivity extends AppCompatActivity {

    private EditText edtCodigo;
    private EditText edtNombre;
    private EditText edtCategoria;
    private EditText edtTela;
    private EditText edtObservaciones;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fichas);

        // Conectar con la base de datos
        dbHelper = new DBHelper(this);

        // ==============================
        // CAMPOS
        // ==============================

        edtCodigo = findViewById(R.id.edtCodigoFicha);
        edtNombre = findViewById(R.id.edtNombreFicha);
        edtCategoria = findViewById(R.id.edtCategoriaFicha);
        edtTela = findViewById(R.id.edtTelaFicha);
        edtObservaciones = findViewById(R.id.edtObservacionesFicha);

        // ==============================
        // BOTONES
        // ==============================

        Button btnGuardar = findViewById(R.id.btnGuardarFicha);
        Button btnConsultar = findViewById(R.id.btnConsultarFicha);
        Button btnActualizar = findViewById(R.id.btnActualizarFicha);
        Button btnEliminar = findViewById(R.id.btnEliminarFicha);

        // ==============================
        // EVENTOS
        // ==============================

        btnGuardar.setOnClickListener(v -> guardarFicha());

        btnConsultar.setOnClickListener(v -> consultarFicha());

        btnActualizar.setOnClickListener(v -> actualizarFicha());

        btnEliminar.setOnClickListener(v -> confirmarEliminar());
    }

    // =========================================================
    // GUARDAR FICHA
    // =========================================================

    private void guardarFicha() {

        String codigo = edtCodigo.getText().toString().trim();
        String nombre = edtNombre.getText().toString().trim();
        String categoria = edtCategoria.getText().toString().trim();
        String tela = edtTela.getText().toString().trim();
        String observaciones = edtObservaciones.getText().toString().trim();

        // Validar campos obligatorios
        if (codigo.isEmpty()) {

            Toast.makeText(
                    this,
                    "Ingrese el código de la ficha",
                    Toast.LENGTH_SHORT
            ).show();

            edtCodigo.requestFocus();
            return;
        }

        if (nombre.isEmpty()) {

            Toast.makeText(
                    this,
                    "Ingrese el nombre de la ficha",
                    Toast.LENGTH_SHORT
            ).show();

            edtNombre.requestFocus();
            return;
        }

        // Crear objeto Ficha
        Ficha ficha = new Ficha(
                codigo,
                nombre,
                categoria,
                tela,
                observaciones
        );

        // Guardar en SQLite
        long resultado = dbHelper.insertarFicha(ficha);

        if (resultado != -1) {

            Toast.makeText(
                    this,
                    "Ficha guardada correctamente",
                    Toast.LENGTH_SHORT
            ).show();

            limpiarCampos();

        } else {

            Toast.makeText(
                    this,
                    "No se pudo guardar la ficha. El código ya puede estar registrado.",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    // =========================================================
    // CONSULTAR FICHA
    // =========================================================

    private void consultarFicha() {

        String codigo = edtCodigo.getText().toString().trim();

        if (codigo.isEmpty()) {

            Toast.makeText(
                    this,
                    "Ingrese el código de la ficha para consultar",
                    Toast.LENGTH_SHORT
            ).show();

            edtCodigo.requestFocus();
            return;
        }

        // Buscar ficha en la base de datos
        Ficha ficha = dbHelper.buscarFicha(codigo);

        if (ficha != null) {

            // Mostrar información encontrada
            edtCodigo.setText(ficha.getCodigo());
            edtNombre.setText(ficha.getNombre());
            edtCategoria.setText(ficha.getCategoria());
            edtTela.setText(ficha.getTela());
            edtObservaciones.setText(ficha.getObservaciones());

            Toast.makeText(
                    this,
                    "Ficha encontrada",
                    Toast.LENGTH_SHORT
            ).show();

        } else {

            Toast.makeText(
                    this,
                    "No existe una ficha con ese código",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    // =========================================================
    // ACTUALIZAR FICHA
    // =========================================================

    private void actualizarFicha() {

        String codigo = edtCodigo.getText().toString().trim();
        String nombre = edtNombre.getText().toString().trim();
        String categoria = edtCategoria.getText().toString().trim();
        String tela = edtTela.getText().toString().trim();
        String observaciones = edtObservaciones.getText().toString().trim();

        // Validaciones
        if (codigo.isEmpty()) {

            Toast.makeText(
                    this,
                    "Ingrese el código de la ficha",
                    Toast.LENGTH_SHORT
            ).show();

            edtCodigo.requestFocus();
            return;
        }

        if (nombre.isEmpty()) {

            Toast.makeText(
                    this,
                    "Ingrese el nombre de la ficha",
                    Toast.LENGTH_SHORT
            ).show();

            edtNombre.requestFocus();
            return;
        }

        // Crear objeto con los nuevos datos
        Ficha ficha = new Ficha(
                codigo,
                nombre,
                categoria,
                tela,
                observaciones
        );

        // Actualizar
        int resultado = dbHelper.actualizarFicha(ficha);

        if (resultado > 0) {

            Toast.makeText(
                    this,
                    "Ficha actualizada correctamente",
                    Toast.LENGTH_SHORT
            ).show();

        } else {

            Toast.makeText(
                    this,
                    "No se encontró la ficha para actualizar",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    // =========================================================
    // CONFIRMAR ELIMINACIÓN
    // =========================================================

    private void confirmarEliminar() {

        String codigo = edtCodigo.getText().toString().trim();

        if (codigo.isEmpty()) {

            Toast.makeText(
                    this,
                    "Ingrese el código de la ficha",
                    Toast.LENGTH_SHORT
            ).show();

            edtCodigo.requestFocus();
            return;
        }

        // Ventana de confirmación
        new AlertDialog.Builder(this)
                .setTitle("Eliminar ficha")
                .setMessage(
                        "¿Está seguro de eliminar la ficha con código: "
                                + codigo + "?"
                )
                .setPositiveButton(
                        "Sí, eliminar",
                        (dialog, which) -> eliminarFicha(codigo)
                )
                .setNegativeButton(
                        "Cancelar",
                        null
                )
                .show();
    }

    // =========================================================
    // ELIMINAR FICHA
    // =========================================================

    private void eliminarFicha(String codigo) {

        int resultado = dbHelper.eliminarFicha(codigo);

        if (resultado > 0) {

            Toast.makeText(
                    this,
                    "Ficha eliminada correctamente",
                    Toast.LENGTH_SHORT
            ).show();

            limpiarCampos();

        } else {

            Toast.makeText(
                    this,
                    "No se encontró la ficha",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    // =========================================================
    // LIMPIAR CAMPOS
    // =========================================================

    private void limpiarCampos() {

        edtCodigo.setText("");
        edtNombre.setText("");
        edtCategoria.setText("");
        edtTela.setText("");
        edtObservaciones.setText("");

        edtCodigo.requestFocus();
    }
}