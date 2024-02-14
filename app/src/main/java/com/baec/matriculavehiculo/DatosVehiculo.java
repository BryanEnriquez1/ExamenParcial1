package com.baec.matriculavehiculo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class DatosVehiculo extends AppCompatActivity {

    private final static String CHANNEL_ID = "canal";
    String fecha = "";
    EditText txtPlaca, txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_vehiculo);
        EditText txtCedula = findViewById(R.id.edCedula);
        txtCedula.setInputType(InputType.TYPE_CLASS_NUMBER);
        EditText txtNombres = findViewById(R.id.edNombres);
        EditText txtPlaca = findViewById(R.id.edPlaca);
        TextView txtAnioFab = findViewById(R.id.tvanio);
        txtAnioFab.setInputType(InputType.TYPE_CLASS_NUMBER);
        EditText txtvalor = findViewById(R.id.edValor);
        txtvalor.setInputType(InputType.TYPE_CLASS_NUMBER);
        EditText txtmultas = findViewById(R.id.edMultas);
        txtmultas.setInputType(InputType.TYPE_CLASS_NUMBER);
        Button btncalcular = findViewById(R.id.btCalcula);
        Button btCalendar = findViewById(R.id.btnCalendar);
        Button btnotify = findViewById(R.id.btNotifica);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            generarNoticacionCanal();
        }else{
            generarNoticacionSinCanal();
        }

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

                double valorVe = Double.parseDouble(valorV);

                BDHelper admin=new BDHelper(DatosVehiculo.this,"fichaVehiculo.db",null,1);
                SQLiteDatabase bd=admin.getWritableDatabase();
                String placa = placaV;
                String color = colorV;
                String marca = marcaV;
                String tipo = tipoV;
                Double valorv = valorVe;
                if(!placaV.isEmpty()&&!color.isEmpty()&&!marca.isEmpty()&&!tipo.isEmpty()){
                    ContentValues datosVehiculo = new ContentValues();
                    datosVehiculo.put("vhe_placa",placa);
                    datosVehiculo.put("vhe_color",color);
                    datosVehiculo.put("vhe_marca",marca);
                    datosVehiculo.put("vhe_tipo",tipo);
                    datosVehiculo.put("vhe_valor",valorv);

                    bd.insert("tblVehiculos",null,datosVehiculo);
                    Toast.makeText(DatosVehiculo.this,"VEHICULO REGISTRADO CORRECTAMENTE",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(DatosVehiculo.this,"VEHICULO NO REGISTRADO",Toast.LENGTH_LONG).show();
                }

//                try {
//
//                    Integer valor = Integer.parseInt(valorV);
//                    Integer multas = Integer.parseInt(multasV);
//
//                    if(!cedulaP.isEmpty() && !nombresP.isEmpty() && !placaV.isEmpty() && valor != null && multas != null){
//                        Toast.makeText(DatosVehiculo.this, "CALCULANDO SU VALOR", Toast.LENGTH_SHORT).show();
//                        datos.putString("cedulaP", cedulaP);
//                        datos.putString("nombresP", nombresP);
//                        datos.putString("placaV", placaV);
//                        datos.putString("anioV", anioV);
//                        datos.putString("marcaV", marcaV);
//                        datos.putString("colorV", colorV);
//                        datos.putString("tipoV", tipoV);
//                        datos.putInt("valorV", valor);
//                        datos.putInt("multasV", multas);
//                        intent.putExtras(datos);
//                        startActivity(intent);
//                    } else {
//                        Toast.makeText(DatosVehiculo.this, "INGRESE DATOS", Toast.LENGTH_SHORT).show();
//                    }
//                } catch (NumberFormatException e) {
//                    // Maneja la excepción si ocurre un error al convertir las cadenas a números
//                    Toast.makeText(DatosVehiculo.this, "ASEGURECE DE LLENAR BIEN LOS CAMPOS", Toast.LENGTH_SHORT).show();
//                }
            }
        });

        btCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal= Calendar.getInstance();
                int anio= cal.get(Calendar.YEAR);
                int mes= cal.get(Calendar.MONTH);
                int dia= cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd=new DatePickerDialog(DatosVehiculo.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        fecha = dayOfMonth+"/"+(month+1)+"/"+year;
                        txtAnioFab.setText(fecha);
                    }
                },dia,mes,anio);
                dpd.show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void generarNoticacionCanal(){
        NotificationChannel channel=new NotificationChannel(CHANNEL_ID,"NEW", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager manager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);
        generarNoticacionSinCanal();
    }

    public void generarNoticacionSinCanal(){

        NotificationCompat.Builder builder=new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID)
                .setSmallIcon(R.drawable.icon_n)
                .setContentTitle("VEHICLE ASIGN V.A")
                .setContentText("MATRICULA TU VEHICULO POR MEDIO DE ESTA APP")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("FICHA VEHICULAR"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);
        // Obtener el NotificationManager
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Mostrar la notificación
        notificationManager.notify(0, builder.build());

    }

}