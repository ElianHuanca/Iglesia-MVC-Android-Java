package com.example.iglesia_app.Controladores.Ministerio;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.iglesia_app.Modelos.MinisterioModelo;
import com.example.iglesia_app.R;

public class FormularioMinisterioControlador extends AppCompatActivity {
    Context context;
    Button btn_guardar;
    EditText et_nombre,et_descripcion;
    private int id;
    MinisterioModelo ministerioModelo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ministerio_formulario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        
        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                     ministerioModelo = new MinisterioModelo(
                             context,
                             id,
                             et_nombre.getText().toString(),
                             et_descripcion.getText().toString()
                    );
                    if (id == 0) {
                        ministerioModelo.agregar(ministerioModelo);
                    }else{
                        ministerioModelo.editar(ministerioModelo);
                    }
                    Intent intent = new Intent(context, ListaMinisterioControlador.class);
                    startActivity(intent);
                }catch (Exception e) {
                    Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    
    private void init(){
        context=getApplicationContext();
        et_nombre=findViewById(R.id.et_nombre);
        et_descripcion=findViewById(R.id.et_descripcion);
        btn_guardar=findViewById(R.id.btn_guardar);
        
        Intent intent = getIntent();
        Bundle bolsa= intent.getExtras();
        id=bolsa.getInt("id");

        if (id!=0){
            et_nombre.setText(bolsa.getString("nombre"));
            et_descripcion.setText(bolsa.getString("descripcion"));
        }
    }
}