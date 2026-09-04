package com.example.sistemafichas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sistemafichas.model.Cliente;
import com.example.sistemafichas.model.Ficha;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "sistema_fichas.db";
    private static final int DATABASE_VERSION = 4;

    // ============================================================
    // TABLA CLIENTES
    // ============================================================

    private static final String TABLE_CLIENTES = "clientes";

    private static final String COLUMN_DOCUMENTO = "documento";
    private static final String COLUMN_NOMBRE = "nombre";
    private static final String COLUMN_TELEFONO = "telefono";
    private static final String COLUMN_CORREO = "correo";


    // ============================================================
    // TABLA FICHAS
    // ============================================================

    private static final String TABLE_FICHAS = "fichas";

    private static final String COLUMN_CODIGO = "codigo";
    private static final String COLUMN_NOMBRE_FICHA = "nombre";
    private static final String COLUMN_CATEGORIA = "categoria";
    private static final String COLUMN_TELA = "tela";
    private static final String COLUMN_OBSERVACIONES = "observaciones";


    // ============================================================
    // TABLA PEDIDOS
    // ============================================================

    private static final String TABLE_PEDIDOS = "pedidos";

    private static final String COLUMN_ID_PEDIDO = "id";
    private static final String COLUMN_CLIENTE_PEDIDO = "cliente_documento";
    private static final String COLUMN_FECHA_PEDIDO = "fecha";
    private static final String COLUMN_ESTADO_PEDIDO = "estado";
    private static final String COLUMN_TOTAL_PEDIDO = "total";


    // ============================================================
    // CONSTRUCTOR
    // ============================================================

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    // ============================================================
    // CREAR BASE DE DATOS
    // ============================================================

    @Override
    public void onCreate(SQLiteDatabase db) {

        // ---------------- CLIENTES ----------------

        String CREATE_CLIENTES = "CREATE TABLE " + TABLE_CLIENTES + " ("
                + COLUMN_DOCUMENTO + " TEXT PRIMARY KEY,"
                + COLUMN_NOMBRE + " TEXT NOT NULL,"
                + COLUMN_TELEFONO + " TEXT,"
                + COLUMN_CORREO + " TEXT"
                + ")";


        // ---------------- FICHAS ----------------

        String CREATE_FICHAS = "CREATE TABLE " + TABLE_FICHAS + " ("
                + COLUMN_CODIGO + " TEXT PRIMARY KEY,"
                + COLUMN_NOMBRE_FICHA + " TEXT,"
                + COLUMN_CATEGORIA + " TEXT,"
                + COLUMN_TELA + " TEXT,"
                + COLUMN_OBSERVACIONES + " TEXT"
                + ")";


        // ---------------- PEDIDOS ----------------

        String CREATE_PEDIDOS = "CREATE TABLE " + TABLE_PEDIDOS + " ("
                + COLUMN_ID_PEDIDO + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_CLIENTE_PEDIDO + " TEXT NOT NULL,"
                + COLUMN_FECHA_PEDIDO + " TEXT,"
                + COLUMN_ESTADO_PEDIDO + " TEXT,"
                + COLUMN_TOTAL_PEDIDO + " REAL"
                + ")";


        db.execSQL(CREATE_CLIENTES);
        db.execSQL(CREATE_FICHAS);
        db.execSQL(CREATE_PEDIDOS);
    }


    // ============================================================
    // ACTUALIZAR BASE DE DATOS
    // ============================================================

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FICHAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PEDIDOS);

        onCreate(db);
    }


    // ============================================================
    // ======================= CLIENTES ===========================
    // ============================================================


    // ------------------------------------------------------------
    // INSERTAR CLIENTE
    // ------------------------------------------------------------

    public long insertarCliente(Cliente cliente) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_DOCUMENTO, cliente.getDocumento());
        values.put(COLUMN_NOMBRE, cliente.getNombre());
        values.put(COLUMN_TELEFONO, cliente.getTelefono());
        values.put(COLUMN_CORREO, cliente.getCorreo());

        long resultado = db.insert(TABLE_CLIENTES, null, values);

        db.close();

        return resultado;
    }


    // ------------------------------------------------------------
    // BUSCAR UN CLIENTE POR DOCUMENTO
    // ------------------------------------------------------------

    public Cliente buscarCliente(String documento) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_CLIENTES,

                new String[]{
                        COLUMN_DOCUMENTO,
                        COLUMN_NOMBRE,
                        COLUMN_TELEFONO,
                        COLUMN_CORREO
                },

                COLUMN_DOCUMENTO + "=?",

                new String[]{
                        documento
                },

                null,
                null,
                null
        );


        Cliente cliente = null;


        if (cursor != null && cursor.moveToFirst()) {

            cliente = new Cliente(
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOCUMENTO)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TELEFONO)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CORREO))
            );
        }


        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return cliente;
    }


    // ------------------------------------------------------------
    // OBTENER TODOS LOS CLIENTES
    // ------------------------------------------------------------

    public List<Cliente> obtenerTodosLosClientes() {

        List<Cliente> lista = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_CLIENTES,

                new String[]{
                        COLUMN_DOCUMENTO,
                        COLUMN_NOMBRE,
                        COLUMN_TELEFONO,
                        COLUMN_CORREO
                },

                null,
                null,
                null,
                null,

                COLUMN_NOMBRE + " ASC"
        );


        if (cursor != null && cursor.moveToFirst()) {

            do {

                Cliente cliente = new Cliente(

                        cursor.getString(
                                cursor.getColumnIndexOrThrow(COLUMN_NOMBRE)
                        ),

                        cursor.getString(
                                cursor.getColumnIndexOrThrow(COLUMN_DOCUMENTO)
                        ),

                        cursor.getString(
                                cursor.getColumnIndexOrThrow(COLUMN_TELEFONO)
                        ),

                        cursor.getString(
                                cursor.getColumnIndexOrThrow(COLUMN_CORREO)
                        )
                );


                lista.add(cliente);


            } while (cursor.moveToNext());
        }


        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return lista;
    }


    // ------------------------------------------------------------
    // ACTUALIZAR CLIENTE
    // ------------------------------------------------------------

    public int actualizarCliente(Cliente cliente) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_NOMBRE, cliente.getNombre());
        values.put(COLUMN_TELEFONO, cliente.getTelefono());
        values.put(COLUMN_CORREO, cliente.getCorreo());


        int resultado = db.update(
                TABLE_CLIENTES,
                values,
                COLUMN_DOCUMENTO + "=?",
                new String[]{
                        cliente.getDocumento()
                }
        );


        db.close();

        return resultado;
    }


    // ------------------------------------------------------------
    // ELIMINAR CLIENTE
    // ------------------------------------------------------------

    public int eliminarCliente(String documento) {

        SQLiteDatabase db = this.getWritableDatabase();

        int resultado = db.delete(
                TABLE_CLIENTES,
                COLUMN_DOCUMENTO + "=?",
                new String[]{
                        documento
                }
        );

        db.close();

        return resultado;
    }


    // ============================================================
    // ======================== FICHAS ============================
    // ============================================================


    // ------------------------------------------------------------
    // INSERTAR FICHA
    // ------------------------------------------------------------

    public long insertarFicha(Ficha ficha) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_CODIGO, ficha.getCodigo());
        values.put(COLUMN_NOMBRE_FICHA, ficha.getNombre());
        values.put(COLUMN_CATEGORIA, ficha.getCategoria());
        values.put(COLUMN_TELA, ficha.getTela());
        values.put(COLUMN_OBSERVACIONES, ficha.getObservaciones());


        long resultado = db.insert(
                TABLE_FICHAS,
                null,
                values
        );


        db.close();

        return resultado;
    }


    // ------------------------------------------------------------
    // BUSCAR FICHA
    // ------------------------------------------------------------

    public Ficha buscarFicha(String codigo) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_FICHAS,

                new String[]{
                        COLUMN_CODIGO,
                        COLUMN_NOMBRE_FICHA,
                        COLUMN_CATEGORIA,
                        COLUMN_TELA,
                        COLUMN_OBSERVACIONES
                },

                COLUMN_CODIGO + "=?",

                new String[]{
                        codigo
                },

                null,
                null,
                null
        );


        Ficha ficha = null;


        if (cursor != null && cursor.moveToFirst()) {

            ficha = new Ficha(

                    cursor.getString(
                            cursor.getColumnIndexOrThrow(COLUMN_CODIGO)
                    ),

                    cursor.getString(
                            cursor.getColumnIndexOrThrow(COLUMN_NOMBRE_FICHA)
                    ),

                    cursor.getString(
                            cursor.getColumnIndexOrThrow(COLUMN_CATEGORIA)
                    ),

                    cursor.getString(
                            cursor.getColumnIndexOrThrow(COLUMN_TELA)
                    ),

                    cursor.getString(
                            cursor.getColumnIndexOrThrow(COLUMN_OBSERVACIONES)
                    )
            );
        }


        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return ficha;
    }


    // ------------------------------------------------------------
    // ACTUALIZAR FICHA
    // ------------------------------------------------------------

    public int actualizarFicha(Ficha ficha) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(
                COLUMN_NOMBRE_FICHA,
                ficha.getNombre()
        );

        values.put(
                COLUMN_CATEGORIA,
                ficha.getCategoria()
        );

        values.put(
                COLUMN_TELA,
                ficha.getTela()
        );

        values.put(
                COLUMN_OBSERVACIONES,
                ficha.getObservaciones()
        );


        int resultado = db.update(
                TABLE_FICHAS,
                values,
                COLUMN_CODIGO + "=?",
                new String[]{
                        ficha.getCodigo()
                }
        );


        db.close();

        return resultado;
    }


    // ------------------------------------------------------------
    // ELIMINAR FICHA
    // ------------------------------------------------------------

    public int eliminarFicha(String codigo) {

        SQLiteDatabase db = this.getWritableDatabase();

        int resultado = db.delete(
                TABLE_FICHAS,
                COLUMN_CODIGO + "=?",
                new String[]{
                        codigo
                }
        );


        db.close();

        return resultado;
    }


    // ============================================================
    // ======================== PEDIDOS ===========================
    // ============================================================


    // ------------------------------------------------------------
    // INSERTAR PEDIDO
    // ------------------------------------------------------------

    public long insertarPedido(
            String clienteDoc,
            String fecha,
            String estado,
            double total
    ) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(
                COLUMN_CLIENTE_PEDIDO,
                clienteDoc
        );

        values.put(
                COLUMN_FECHA_PEDIDO,
                fecha
        );

        values.put(
                COLUMN_ESTADO_PEDIDO,
                estado
        );

        values.put(
                COLUMN_TOTAL_PEDIDO,
                total
        );


        long resultado = db.insert(
                TABLE_PEDIDOS,
                null,
                values
        );


        db.close();

        return resultado;
    }


    // ------------------------------------------------------------
    // OBTENER TODOS LOS PEDIDOS
    // ------------------------------------------------------------

    public List<String> obtenerTodosLosPedidos() {

        List<String> lista = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();


        String consulta =
                "SELECT p.id, " +
                        "c.nombre, " +
                        "p.fecha, " +
                        "p.estado, " +
                        "p.total " +

                        "FROM " + TABLE_PEDIDOS + " p " +

                        "INNER JOIN " + TABLE_CLIENTES + " c " +

                        "ON p." + COLUMN_CLIENTE_PEDIDO +
                        " = c." + COLUMN_DOCUMENTO +

                        " ORDER BY p.id DESC";


        Cursor cursor = db.rawQuery(
                consulta,
                null
        );


        if (cursor != null && cursor.moveToFirst()) {

            do {

                String pedido =

                        "ID: " +
                                cursor.getInt(0) +

                                "\nCliente: " +
                                cursor.getString(1) +

                                "\nFecha: " +
                                cursor.getString(2) +

                                "\nEstado: " +
                                cursor.getString(3) +

                                "\nTotal: $" +
                                cursor.getDouble(4);


                lista.add(pedido);


            } while (cursor.moveToNext());
        }


        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return lista;
    }


    // ------------------------------------------------------------
    // BUSCAR PEDIDO POR ID
    // ------------------------------------------------------------

    public String buscarPedido(int id) {

        SQLiteDatabase db = this.getReadableDatabase();


        String consulta =
                "SELECT p.id, " +
                        "c.nombre, " +
                        "p.fecha, " +
                        "p.estado, " +
                        "p.total " +

                        "FROM " + TABLE_PEDIDOS + " p " +

                        "INNER JOIN " + TABLE_CLIENTES + " c " +

                        "ON p." + COLUMN_CLIENTE_PEDIDO +
                        " = c." + COLUMN_DOCUMENTO +

                        " WHERE p.id=?";


        Cursor cursor = db.rawQuery(
                consulta,
                new String[]{
                        String.valueOf(id)
                }
        );


        String resultado = null;


        if (cursor != null && cursor.moveToFirst()) {

            resultado =

                    "ID: " +
                            cursor.getInt(0) +

                            "\nCliente: " +
                            cursor.getString(1) +

                            "\nFecha: " +
                            cursor.getString(2) +

                            "\nEstado: " +
                            cursor.getString(3) +

                            "\nTotal: $" +
                            cursor.getDouble(4);
        }


        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return resultado;
    }


    // ------------------------------------------------------------
    // ACTUALIZAR ESTADO DEL PEDIDO
    // ------------------------------------------------------------

    public int actualizarEstadoPedido(
            int id,
            String nuevoEstado
    ) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(
                COLUMN_ESTADO_PEDIDO,
                nuevoEstado
        );


        int resultado = db.update(
                TABLE_PEDIDOS,
                values,
                COLUMN_ID_PEDIDO + "=?",
                new String[]{
                        String.valueOf(id)
                }
        );


        db.close();

        return resultado;
    }


    // ------------------------------------------------------------
    // ELIMINAR PEDIDO
    // ------------------------------------------------------------

    public int eliminarPedido(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        int resultado = db.delete(
                TABLE_PEDIDOS,
                COLUMN_ID_PEDIDO + "=?",
                new String[]{
                        String.valueOf(id)
                }
        );


        db.close();

        return resultado;
    }
}