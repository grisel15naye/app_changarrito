package pe.idat.aplicacion_burguer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MainMenu extends AppCompatActivity {

    private ArrayList<String> listaCompras;
    private Map<String, Double> preciosProductos = new HashMap<>();
    private double totalPrecio;
    private SharedPreferences sharedPreferences;

    private Button btnP1, btnP2, btnP3, btnP4, btnP5, btnP6, btnP7, btnP8, btnAtrasMenu, btnVerLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Inicializar SharedPreferences
        sharedPreferences = getSharedPreferences("ComprasPref", MODE_PRIVATE);
        listaCompras = cargarListaCompras();
        totalPrecio = cargarTotalPrecio();

        // Inicializar los precios de los productos
        preciosProductos.put("Whopper", 21.90);
        preciosProductos.put("Whopper Extreme", 23.90);
        preciosProductos.put("Whopper Guacamole", 19.90);
        preciosProductos.put("Whopper con Queso", 21.90);
        preciosProductos.put("Stacker Doble", 24.90);
        preciosProductos.put("Bacon King Carne", 26.90);
        preciosProductos.put("Chilli King Carne", 23.90);
        preciosProductos.put("Champi King Carne", 26.90);

        // Referencias a los botones
        btnP1 = findViewById(R.id.btnP1);
        btnP2 = findViewById(R.id.btnP2);
        btnP3 = findViewById(R.id.btnP3);
        btnP4 = findViewById(R.id.btnP4);
        btnP5 = findViewById(R.id.btnP5);
        btnP6 = findViewById(R.id.btnP6);
        btnP7 = findViewById(R.id.btnP7);
        btnP8 = findViewById(R.id.btnP8);
        btnAtrasMenu = findViewById(R.id.btnAtrasMenu);
        btnVerLista = findViewById(R.id.btnVerLista);

        // Configuración de los botones
        btnP1.setOnClickListener(v -> agregarProducto("Whopper"));
        btnP2.setOnClickListener(v -> agregarProducto("Whopper Extreme"));
        btnP3.setOnClickListener(v -> agregarProducto("Whopper Guacamole"));
        btnP4.setOnClickListener(v -> agregarProducto("Whopper con Queso"));
        btnP5.setOnClickListener(v -> agregarProducto("Stacker Doble"));
        btnP6.setOnClickListener(v -> agregarProducto("Bacon King Carne"));
        btnP7.setOnClickListener(v -> agregarProducto("Chilli King Carne"));
        btnP8.setOnClickListener(v -> agregarProducto("Champi King Carne"));

        btnAtrasMenu.setOnClickListener(v -> {
            guardarDatos();
            Intent intent = new Intent(MainMenu.this, MainBienvenido.class);
            startActivity(intent);
        });

        btnVerLista.setOnClickListener(v -> {
            guardarDatos();
            Intent intent = new Intent(MainMenu.this, MainLista.class);
            startActivity(intent);
        });
    }

    private void agregarProducto(String producto) {
        listaCompras.add(producto + ": s/" + preciosProductos.get(producto));
        totalPrecio += preciosProductos.get(producto);
        guardarDatos();
        Toast.makeText(this, producto + " agregado a la lista de compras", Toast.LENGTH_SHORT).show();
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
