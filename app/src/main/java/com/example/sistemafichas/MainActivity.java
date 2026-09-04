package com.example.sistemafichas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etCorreo;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        etCorreo = findViewById(R.id.etCorreo);
        etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {

            String correo = etCorreo.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (correo.isEmpty()) {
                etCorreo.setError(getString(R.string.hint_correo_login));
                etCorreo.requestFocus();
                return;
            }

            if (password.isEmpty()) {
                etPassword.setError(getString(R.string.hint_password_login));
                etPassword.requestFocus();
                return;
            }

            // Ir al menú principal
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);

            // Cerrar el Login para que no vuelva atrás
            finish();
        });
    }
}
