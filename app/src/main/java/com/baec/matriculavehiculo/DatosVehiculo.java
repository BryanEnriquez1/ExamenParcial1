package com.baec.matriculavehiculo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class DatosVehiculo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_vehiculo);
        EditText txtCedula = findViewById(R.id.edCedula);
        EditText txtNombres = findViewById(R.id.edNombres);
        EditText txtPlaca = findViewById(R.id.edPlaca);
        EditText txtAnioFab = findViewById(R.id.edAnioFab);
        EditText txtvalor = findViewById(R.id.edValor);
        EditText txtmultas = findViewById(R.id.edMultas);
        Button btncalcular = findViewById(R.id.btCalcula);

        Spinner marcas = (Spinner) findViewById(R.id.cbxmarca);
        String [] opMarcas = {
                "TOYOTA",
                "SUSUKI",
                "CHEVROLET"
        };
        ArrayAdapter<String> marcasVehiculo = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opMarcas);
        marcas.setAdapter(marcasVehiculo);

        Spinner colores = (Spinner) findViewById(R.id.cbxcolor);
        String [] opColor = {
                "PLOMO",
                "BLANCO",
                "NEGRO"
        };
        ArrayAdapter<String> coloresVehiculo = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opColor);
        colores.setAdapter(coloresVehiculo);

        Spinner tipos = (Spinner) findViewById(R.id.cbxTipo);
        String [] opTipo = {
                "JEEP",
                "CAMIONETA",
                "VITARA",
                "AUTOMOVIL"
        };
        ArrayAdapter<String> tiposVehiculo = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opTipo);
        tipos.setAdapter(tiposVehiculo);

        btncalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DatosVehiculo.this, DatosMatricula.class);
                Bundle datos = new Bundle();
                String cedulaP = txtCedula.getText().toString();
                String nombresP = txtNombres.getText().toString();
                String placaV = txtPlaca.getText().toString();
                String anioV = txtAnioFab.getText().toString();
                String marcaV = marcas.getSelectedItem().toString();
                String colorV = colores.getSelectedItem().toString();
                String tipoV = tipos.getSelectedItem().toString();
                String valorV = txtvalor.getText().toString();
                String multasV = txtmultas.getText().toString();

                try {

                    Integer anioFab = Integer.parseInt(anioV);
                    Integer valor = Integer.parseInt(valorV);
                    Integer multas = Integer.parseInt(multasV);

                    if(!cedulaP.isEmpty() && !nombresP.isEmpty() && !placaV.isEmpty() && anioFab != null && valor != null && multas != null){
                        Toast.makeText(DatosVehiculo.this, "CALCULANDO SU VALOR", Toast.LENGTH_SHORT).show();
                        datos.putString("cedulaP", cedulaP);
                        datos.putString("nombresP", nombresP);
                        datos.putString("placaV", placaV);
                        datos.putInt("anioV", anioFab);
                        datos.putString("marcaV", marcaV);
                        datos.putString("colorV", colorV);
                        datos.putString("tipoV", tipoV);
                        datos.putInt("valorV", valor);
                        datos.putInt("multasV", multas);
                        intent.putExtras(datos);
                        startActivity(intent);
                    } else {
                        Toast.makeText(DatosVehiculo.this, "INGRESE DATOS", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    // Maneja la excepción si ocurre un error al convertir las cadenas a números
                    Toast.makeText(DatosVehiculo.this, "ASEGURECE DE LLENAR BIEN LOS CAMPOS", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}