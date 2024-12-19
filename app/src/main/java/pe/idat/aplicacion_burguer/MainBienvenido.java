package pe.idat.aplicacion_burguer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainBienvenido extends AppCompatActivity
{
    private Button btnHamburgesa, btnHealdos, btnCerrar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bienvenido);

        btnHamburgesa = findViewById(R.id.btnHamburgesa);
        btnHealdos = findViewById(R.id.btnHelados);
        btnCerrar = findViewById(R.id.btnCerrar);

        btnHamburgesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainBienvenido.this, MainMenu.class);
                startActivity(intent);
            }
        });

        btnHealdos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainBienvenido.this, MainHelados.class);
                startActivity(intent);
            }
        });

        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainBienvenido.this, "Gracias por su compra, vuelva pronto", Toast.LENGTH_SHORT).show();
                finishAffinity(); // Cierra la aplicación
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
