package com.example.iglesia_app.Controladores.Ingreso;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.iglesia_app.Modelos.ActividadModelo;
import com.example.iglesia_app.Modelos.IngresoModelo;
import com.example.iglesia_app.R;

public class FormularioIngresoControlador extends AppCompatActivity {
    Context context;
    String[] tipos = {"Donacion", "Diezmo","Ofrenda"};
    Button btn_guardar;
    EditText et_monto,et_idActividad;
    Spinner spinner;
    ArrayAdapter adapter;
    private int id;
    IngresoModelo ingresoModelo;
    ActividadModelo actividadModelo;
    IngresoModelo ingresoModeloOriginal;
    ActividadModelo actividadModeloOriginal;
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
                     ingresoModelo = new IngresoModelo(
                            context,
                            id,
                            spinner.getSelectedItem().toString(),
                            Double.parseDouble(et_monto.getText().toString()),
                            Integer.parseInt(et_idActividad.getText().toString())
                    );
                    actividadModelo=new ActividadModelo(context);
                    actividadModelo=actividadModelo.obtenerByID(ingresoModelo.getIdActividad());
                    if (actividadModelo!=null){
                        actividadModelo.setMontoTotal(actividadModelo.getMontoTotal()+ingresoModelo.getMonto());
                        actividadModelo.editarMonto(actividadModelo);
                        if(id==0){
                            ingresoModelo.agregar(ingresoModelo);
                        }else{
                            ingresoModeloOriginal=ingresoModelo.obtenerByID(ingresoModelo.getId());
                            actividadModeloOriginal=actividadModelo.obtenerByID(ingresoModeloOriginal.getIdActividad());
                            actividadModeloOriginal.setMontoTotal(actividadModeloOriginal.getMontoTotal()-ingresoModeloOriginal.getMonto());
                            actividadModeloOriginal.editarMonto(actividadModeloOriginal);
                            ingresoModelo.editar(ingresoModelo);
                        }
                        Intent intent= new Intent(context, ListaIngresoControlador.class);
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