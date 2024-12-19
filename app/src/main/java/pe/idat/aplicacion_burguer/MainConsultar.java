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
import androidx.core.view.WindowInsetsControllerCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainConsultar extends AppCompatActivity
{
    private EditText Documento, Nombres, Password, Correo, Telefono, Sexo, Distrito;
    private Button btnConsultar, btnModificar, btnEliminar, btnAtras02;

    private static final String URL_CONSULTAR = "http://192.168.1.44/burger/consultar.php";
    private static final String URL_MODIFICAR = "http://192.168.1.44/burger/modificar.php";
    private static final String URL_ELIMINAR = "http:/192.168.1.44/burger/eliminar.php";


    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_consultar);

        inicializarComponentes();

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultarCliente();
            }
        });

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificarCliente();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarCliente();
            }
        });

        btnAtras02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainConsultar.this, MainRegistro.class);
                startActivity(intent);
            }
        });


        // Habilitar modo Edge-to-Edge
        View rootLayout = findViewById(R.id.main);
        ViewCompat.setOnApplyWindowInsetsListener(rootLayout, (view, insets) -> {
            Insets systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(systemBarsInsets.left, systemBarsInsets.top, systemBarsInsets.right, systemBarsInsets.bottom);
            return WindowInsetsCompat.CONSUMED;
        });

        // Ocultar barra de estado
        WindowInsetsControllerCompat windowInsetsController = ViewCompat.getWindowInsetsController(rootLayout);
        if (windowInsetsController != null) {
            windowInsetsController.hide(WindowInsetsCompat.Type.statusBars());
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void inicializarComponentes()
    {
        Documento = findViewById(R.id.Documento);

        Nombres = findViewById(R.id.Nombres);
        Password = findViewById(R.id.Password);
        Correo = findViewById(R.id.Correo);
        Telefono = findViewById(R.id.Telefono);
        Sexo = findViewById(R.id.Sexo);
        Distrito = findViewById(R.id.Distrito);

        btnConsultar = findViewById(R.id.btnConsultar);
        btnModificar = findViewById(R.id.btnModificar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnAtras02 = findViewById(R.id.btnAtras02);


        requestQueue = Volley.newRequestQueue(this);
    }

    private void consultarCliente()
    {
        String doc = Documento.getText().toString();

        String url = URL_CONSULTAR + "?documento=" + doc;

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("success")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                JSONObject object = jsonArray.getJSONObject(0);

                                //Las variables de la derecha son de la Base de Datos

                                Nombres.setText(object.getString("nombre"));
                                Password.setText(object.getString("contraseña"));
                                Correo.setText(object.getString("correo"));
                                Telefono.setText(object.getString("telefono"));
                                Sexo.setText(object.getString("sexo"));
                                Distrito.setText(object.getString("distrito"));

                                // Habilitar campos y botón modificar

                                Nombres.setEnabled(true);
                                Password.setEnabled(true);
                                Correo.setEnabled(true);
                                Telefono.setEnabled(true);
                                Sexo.setEnabled(true);
                                Distrito.setEnabled(true);
                                btnModificar.setEnabled(true);
                            } else {
                                Toast.makeText(MainConsultar.this, "Cliente no encontrado", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainConsultar.this, "Error al parsear datos: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainConsultar.this, "Error al consultar cliente: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(stringRequest);
    }

    private void modificarCliente()
    {
        String doc = Documento.getText().toString();

        String nom = Nombres.getText().toString();
        String con = Password.getText().toString();
        String cor = Correo.getText().toString();
        String tel = Telefono.getText().toString();
        String sx = Sexo.getText().toString();
        String dis = Distrito.getText().toString();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL_MODIFICAR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainConsultar.this, "Datos del cliente modificados correctamente", Toast.LENGTH_SHORT).show();
                        limpiarCampos();  // Llamar al metodo para limpiar los campos
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainConsultar.this, "Error al modificar cliente: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("documento", doc);
                params.put("nombre", nom);
                params.put("contraseña", con);
                params.put("correo", cor);
                params.put("telefono", tel);
                params.put("sexo", sx);
                params.put("distrito", dis);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void eliminarCliente() {
        String doc = Documento.getText().toString();

        if (doc.isEmpty()) {
            Toast.makeText(MainConsultar.this, "Por favor, ingresa el documento del cliente a eliminar.", Toast.LENGTH_SHORT).show();
            return;
        }

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL_ELIMINAR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("success")) {
                                Toast.makeText(MainConsultar.this, "Cliente eliminado correctamente", Toast.LENGTH_SHORT).show();
                                limpiarCampos();  // Limpiar campos después de eliminar
                            } else {
                                Toast.makeText(MainConsultar.this, "Error al eliminar cliente", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainConsultar.this, "Error al parsear la respuesta: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainConsultar.this, "Error al eliminar cliente: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("documento", doc); // Enviar el documento al servidor
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }


    // Método para limpiar los campos

    private void limpiarCampos()
    {
        Documento.setText("");
        Nombres.setText("");
        Password.setText("");
        Correo.setText("");
        Telefono.setText("");
        Sexo.setText("");
        Distrito.setText("");

        // Deshabilitar los campos de entrada y el botón de modificar

        Nombres.setEnabled(false);
        Password.setEnabled(false);
        Correo.setEnabled(false);
        Telefono.setEnabled(false);
        Sexo.setEnabled(false);
        Distrito.setEnabled(false);
        btnModificar.setEnabled(false);
    }
}
