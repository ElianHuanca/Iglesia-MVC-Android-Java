package com.example.iglesia_app.Controladores.Ingreso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.iglesia_app.Modelos.Actividad.ActividadDato;
import com.example.iglesia_app.Modelos.Actividad.ActividadNegocio;
import com.example.iglesia_app.Modelos.Ingreso.IngresoDato;
import com.example.iglesia_app.Modelos.Ingreso.IngresoNegocio;
import com.example.iglesia_app.R;

public class FormularioIngreso extends AppCompatActivity {
    Context context;
    String[] tipos = {"Donacion", "Diezmo","Ofrenda"};
    Button btn_guardar;
    EditText et_monto,et_idActividad;
    Spinner spinner;
    ArrayAdapter adapter;
    private int id;
    IngresoNegocio ingresoNegocio;
    ActividadNegocio actividadNegocio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso_formulario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    IngresoDato ingresoDato = new IngresoDato(id,
                            spinner.getSelectedItem().toString(),
                            Double.parseDouble(et_monto.getText().toString()),
                            Integer.parseInt(et_idActividad.getText().toString())
                    );
                    ingresoNegocio = new IngresoNegocio(context);
                    actividadNegocio=new ActividadNegocio(context);
                    ActividadDato actividadDato=actividadNegocio.obtenerByID(ingresoDato.getIdActividad());
                    if (actividadDato!=null){
                        actividadDato.setMontoTotal(actividadDato.getMontoTotal()+ingresoDato.getMonto());
                        actividadNegocio.editarMonto(actividadDato);
                        if(id==0){
                            ingresoNegocio.agregar(ingresoDato);
                        }else{
                            IngresoDato ingresoDatoOriginal=ingresoNegocio.obtenerByID(ingresoDato.getId());
                            ActividadDato actividadDatoOriginal=actividadNegocio.obtenerByID(ingresoDatoOriginal.getIdActividad());
                            actividadDatoOriginal.setMontoTotal(actividadDatoOriginal.getMontoTotal()-ingresoDatoOriginal.getMonto());
                            actividadNegocio.editarMonto(actividadDatoOriginal);
                            ingresoNegocio.editar(ingresoDato);
                        }
                        Intent intent= new Intent(context, ListaIngreso.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(context, "No existe la actividad", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e) {
                    Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void init(){
        try {
            context=getApplicationContext();
            btn_guardar=findViewById(R.id.btn_guardar);
            et_monto= findViewById(R.id.et_monto);
            et_idActividad= findViewById(R.id.et_idActividad);
            spinner = findViewById(R.id.spinner);
            adapter= new ArrayAdapter(context, android.R.layout.simple_spinner_item,tipos);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            Intent intent = getIntent();
            Bundle bolsa= intent.getExtras();
            id=bolsa.getInt("id");

            if (id!=0){
                et_monto.setText(bolsa.getDouble("monto")+"");
                et_idActividad.setText(bolsa.getInt("idActividad")+"");
                for (int i = 0; i < tipos.length; i++) {
                    if(tipos[i].equals(bolsa.getString("nombre"))){
                        spinner.setSelection(i);
                        break;
                    }
                }
            }
        }catch (Exception e) {
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}