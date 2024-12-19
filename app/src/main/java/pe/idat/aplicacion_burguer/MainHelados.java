package pe.idat.aplicacion_burguer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import android.content.SharedPreferences;

public class MainHelados extends AppCompatActivity {

    private ArrayList<String> listaCompras;
    private double totalPrecio;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helados);

        // Inicializar SharedPreferences
        sharedPreferences = getSharedPreferences("ComprasPref", MODE_PRIVATE);
        listaCompras = cargarListaCompras();
        totalPrecio = cargarTotalPrecio();

        // Referencias a los botones
        Button btnh1 = findViewById(R.id.btnh1);
        Button btnh2 = findViewById(R.id.btnh2);
        Button btnh3 = findViewById(R.id.btnh3);
        Button btnh4 = findViewById(R.id.btnh4);
        Button btnh5 = findViewById(R.id.btnh5);
        Button btnh6 = findViewById(R.id.btnh6);
        Button btnh7 = findViewById(R.id.btnh7);
        Button btnh8 = findViewById(R.id.btnh8);
        Button btnh9 = findViewById(R.id.btnh9);
        Button btnh10 = findViewById(R.id.btnh10);
        Button btnVerLista01 = findViewById(R.id.btnVerLista01);
        Button btnAtrasMenu01 = findViewById(R.id.btnAtrasMenu01);

        // Configuración de eventos de clic para los botones
        btnh1.setOnClickListener(v -> agregarProducto("Shake Oreo Vainilla", 14.00));
        btnh2.setOnClickListener(v -> agregarProducto("Shake Oreo Fresa", 14.00));
        btnh3.setOnClickListener(v -> agregarProducto("Shake Oreo Chocolate", 14.00));
        btnh4.setOnClickListener(v -> agregarProducto("King Fusion Oreo", 8.50));
        btnh5.setOnClickListener(v -> agregarProducto("Inka Kola 1L", 8.00));
        btnh6.setOnClickListener(v -> agregarProducto("Coca Cola 1L", 8.00));
        btnh7.setOnClickListener(v -> agregarProducto("Inka Kola 1/2L", 8.00));
        btnh8.setOnClickListener(v -> agregarProducto("Coca Cola 1/2L", 8.00));
        btnh9.setOnClickListener(v -> agregarProducto("Fanta 500ml", 4.00));
        btnh10.setOnClickListener(v -> agregarProducto("San Luis 500ml", 4.00));

        // Botón para ver lista de compras
        btnVerLista01.setOnClickListener(v -> {
            guardarDatos();
            Intent intent = new Intent(MainHelados.this, MainLista.class);
            startActivity(intent);
        });

        // Botón para volver al menú principal
        btnAtrasMenu01.setOnClickListener(v -> {
            guardarDatos();
            Intent intent = new Intent(MainHelados.this, MainBienvenido.class);
            startActivity(intent);
        });
    }

    // Método para agregar producto a la lista de compras
    private void agregarProducto(String nombre, double precio) {
        listaCompras.add(nombre + ": S/" + precio);
        totalPrecio += precio;
        Toast.makeText(this, nombre + " agregado a la lista", Toast.LENGTH_SHORT).show();
    }

    // Guardar datos en SharedPreferences
    private void guardarDatos() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("listaCompras", new HashSet<>(listaCompras));
        editor.putFloat("totalPrecio", (float) totalPrecio);
        editor.apply();
    }

    // Cargar lista de compras desde SharedPreferences
    private ArrayList<String> cargarListaCompras() {
        Set<String> set = sharedPreferences.getStringSet("listaCompras", new HashSet<>());
        return new ArrayList<>(set);
    }

    // Cargar total de precio desde SharedPreferences
    private double cargarTotalPrecio() {
        return sharedPreferences.getFloat("totalPrecio", 0);
    }
}

