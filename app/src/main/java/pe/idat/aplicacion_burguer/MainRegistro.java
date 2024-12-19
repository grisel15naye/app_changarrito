package pe.idat.aplicacion_burguer;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainRegistro extends AppCompatActivity
{

    private EditText nombres, password, documento, correo, telefono;
    private Spinner distrito;
    private RadioButton rdmasculino, rdfemenino;
    private Button btnguardar;

    private static final String URL_SERVIDOR = "http://192.168.1.44/burger/insertar.php";
    //http://127.0.0.1
    private RequestQueue requestQueue;

    private String txtsexo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);

        inicializarComponentes();


        btnguardar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                guardarCliente();
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void inicializarComponentes()
    {
        nombres = findViewById(R.id.nombres);
        password = findViewById(R.id.password);
        documento = findViewById(R.id.documento);
        correo = findViewById(R.id.correo);
        telefono = findViewById(R.id.telefono);

        distrito = findViewById(R.id.distrito);

        rdmasculino = findViewById(R.id.rdmasculino);
        rdfemenino = findViewById(R.id.rdfemenino);

        btnguardar = findViewById(R.id.btnguardar);

        requestQueue = Volley.newRequestQueue(this);
    }

    private void guardarCliente() {
        // Verificar que todos los campos estén llenos
        if (nombres.getText().toString().isEmpty() ||
                password.getText().toString().isEmpty() ||
                documento.getText().toString().isEmpty() ||
                correo.getText().toString().isEmpty() ||
                telefono.getText().toString().isEmpty() ||
                distrito.getSelectedItem() == null) {

            Toast.makeText(MainRegistro.this, "Por favor, llena todos los campos.", Toast.LENGTH_SHORT).show();
            return; // Salir del metodo si falta algún campo
        }

        // Determinar el sexo
        if (rdfemenino.isChecked()) {
            txtsexo = "Femenino";
        } else {
            txtsexo = "Masculino";
        }

        // Enviar la solicitud si todos los campos están completos
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL_SERVIDOR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        Toast.makeText(MainRegistro.this, "Cliente registrado correctamente", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainRegistro.this, MainLogin.class);
                        startActivity(intent);
                        finish();// Limpiar los campos después de registrar
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainRegistro.this, "Error al registrar cliente: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nombre", nombres.getText().toString());
                params.put("contraseña", password.getText().toString());
                params.put("documento", documento.getText().toString());
                params.put("correo", correo.getText().toString());
                params.put("telefono", telefono.getText().toString());
                params.put("sexo", txtsexo);
                params.put("distrito", distrito.getSelectedItem().toString());
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }


    // Método para limpiar los campos
    private void limpiarCampos() {
        nombres.setText("");
        password.setText("");
        documento.setText("");
        correo.setText("");
        telefono.setText("");
        distrito.setSelection(0); // Restablecer el Spinner al primer elemento
        rdmasculino.setChecked(false);
        rdfemenino.setChecked(false);
    }
}
