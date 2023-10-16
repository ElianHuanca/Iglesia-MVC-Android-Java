package com.example.iglesia_app.Controladores.Actividad;

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

import com.example.iglesia_app.Controladores.Actividad.ListaActividad;
import com.example.iglesia_app.Modelos.Actividad.ActividadDato;
import com.example.iglesia_app.Modelos.Actividad.ActividadNegocio;
import com.example.iglesia_app.R;

public class FormularioActividad extends AppCompatActivity {
    Context context;
    String[] tipos = {"Misa", "Culto","Oracion"};
    Button btn_guardar;
    EditText et_nombre,et_fecha,et_hora,et_hora2;
    Spinner spinner;
    ArrayAdapter adapter;
    private int id;
    ActividadNegocio actividadNegocio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_formulario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ActividadDato actividadDato = new ActividadDato(id,
                            spinner.getSelectedItem().toString(),
                            et_fecha.getText().toString(),
                            et_hora.getText().toString(),
                            et_hora2.getText().toString(),
                            0.0
                    );
                    actividadNegocio = new ActividadNegocio(context);
                    if(id==0){
                        actividadNegocio.agregar(actividadDato);
                    }else{
                        actividadNegocio.editar(actividadDato);
                    }
                    Intent intent= new Intent(context, ListaActividad.class);
                    startActivity(intent);
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
            et_nombre = findViewById(R.id.et_nombre);
            et_fecha = findViewById(R.id.et_fecha);
            et_hora = findViewById(R.id.et_hora);
            et_hora2 = findViewById(R.id.et_hora2);

            spinner = findViewById(R.id.spinner);
            adapter= new ArrayAdapter(context, android.R.layout.simple_spinner_item,tipos);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            Intent intent = getIntent();
            Bundle bolsa= intent.getExtras();
            id=bolsa.getInt("id");

            if (id!=0){
                //et_nombre.setText(bolsa.getInt("nombre")+"");
                et_fecha.setText(bolsa.getString("fecha"));
                et_hora.setText(bolsa.getString("hora"));
                et_hora2.setText(bolsa.getString("hora2"));
                
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