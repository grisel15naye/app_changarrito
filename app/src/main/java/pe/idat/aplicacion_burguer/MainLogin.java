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

public class MainLogin extends AppCompatActivity
{
    private EditText Nombre01, Password01;
    private Button btnRegistrate, btnLogin;
    private static final String URL_LOGIN = "http://192.168.1.44/burger/login.php";
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        Nombre01 = findViewById(R.id.Nombre01);
        Password01 = findViewById(R.id.Password01);
        btnRegistrate = findViewById(R.id.btnRegistrate);
        btnRegistrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainLogin.this, MainRegistro.class);
                startActivity(intent);
            }
        });
        btnLogin = findViewById(R.id.btnLogin);

        requestQueue = Volley.newRequestQueue(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void loginUser() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("success")) {
                            Toast.makeText(MainLogin.this, "Inicio de sesión correctamente", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainLogin.this, MainBienvenido.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(MainLogin.this, "Nombre o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainLogin.this, "Error al iniciar sesión: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nombre", Nombre01.getText().toString());
                params.put("contraseña", Password01.getText().toString());
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}
