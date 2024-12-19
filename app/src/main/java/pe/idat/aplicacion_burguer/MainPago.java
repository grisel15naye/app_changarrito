package pe.idat.aplicacion_burguer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class MainPago extends AppCompatActivity {

    private EditText Documento03, Correo03;
    private Button btnPagoFinal;
    private static final String URL_VALIDAR = "http://192.168.1.44/burger/validar.php";
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pago);

        Documento03 = findViewById(R.id.Documento03);
        Correo03 = findViewById(R.id.Correo03);
        btnPagoFinal = findViewById(R.id.btnPagoFinal);

        requestQueue = Volley.newRequestQueue(this);

        btnPagoFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarDatos();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void validarDatos() {
        String documento = Documento03.getText().toString();
        String correo = Correo03.getText().toString();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL_VALIDAR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("success")) {
                            Toast.makeText(MainPago.this, "Pago exitoso, le llegara un mensaje a su correo por compra", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainPago.this, MainBienvenido.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(MainPago.this, "Documento o correo incorrecto", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainPago.this, "Error al validar datos: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("documento", documento);
                params.put("correo", correo);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}
