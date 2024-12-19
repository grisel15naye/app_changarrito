package pe.idat.aplicacion_burguer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainLista extends AppCompatActivity {

    private ListView listaCompras;
    private TextView TotalPrecio;
    private Button btnBorrarProductos, btnAtras03, btnPagar;
    private ArrayList<String> compras;
    private double totalPrecio;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        // Inicializar componentes
        listaCompras = findViewById(R.id.listaCompras);
        TotalPrecio = findViewById(R.id.TotalPrecio);
        btnBorrarProductos = findViewById(R.id.btnBorrarProductos);
        btnAtras03 = findViewById(R.id.btnAtras03);
        btnPagar = findViewById(R.id.btnPagar);

        btnAtras03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainLista.this, MainBienvenido.class);
                startActivity(intent);
            }
        });

        // Cargar datos de SharedPreferences
        sharedPreferences = getSharedPreferences("ComprasPref", MODE_PRIVATE);
        compras = cargarListaCompras();
        totalPrecio = cargarTotalPrecio();

        // Configurar adaptador para la lista
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, compras);
        listaCompras.setAdapter(adapter);

        // Mostrar el precio total con 2 decimales
        TotalPrecio.setText("Total: s/" + String.format("%.2f", totalPrecio));

        // Configurar botón para borrar la lista
        btnBorrarProductos.setOnClickListener(v -> borrarLista());

        // Configurar botón para pagar
        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pagar();
            }
        });
    }

    private void borrarLista() {
        // Limpiar lista de compras y resetear total
        compras.clear();
        totalPrecio = 0;

        // Guardar cambios en SharedPreferences
        guardarDatos();

        // Actualizar la interfaz de usuario
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, compras);
        listaCompras.setAdapter(adapter);
        TotalPrecio.setText("Total: s/" + String.format("%.2f", totalPrecio));

        Toast.makeText(this, "Productos comprados, procedemos a pagar", Toast.LENGTH_SHORT).show();
    }

    private void pagar() {
        borrarLista();
        Intent intent = new Intent(MainLista.this, MainPago.class);
        startActivity(intent);
    }

    // Guardar datos en SharedPreferences
    private void guardarDatos() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("listaCompras", new HashSet<>(compras));
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
