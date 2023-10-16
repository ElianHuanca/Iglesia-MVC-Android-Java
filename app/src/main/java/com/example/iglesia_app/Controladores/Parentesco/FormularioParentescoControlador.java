package com.example.iglesia_app.Controladores.Parentesco;

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


import com.example.iglesia_app.Modelos.ParentescoModelo;
import com.example.iglesia_app.Modelos.UsuarioModelo;
import com.example.iglesia_app.R;

public class FormularioParentescoControlador extends AppCompatActivity {
    Context context;
    String[] tipos = {"Padre", "Madre","Hermano","Hermana","Tio","Tia","Primo","Prima","Abuelo","Abuela"};
    Button btn_guardar;
    EditText et_ci,et_ci2;
    Spinner spinner;
    ArrayAdapter adapter;
    private int id;
    ParentescoModelo parentescoModelo;
    UsuarioModelo usuarioModelo;
    UsuarioModelo usuarioModelo2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parentesco_formulario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    usuarioModelo = new UsuarioModelo(context);
                    usuarioModelo= usuarioModelo.obtenerByCI(Integer.parseInt(et_ci.getText().toString()));
                    usuarioModelo2= usuarioModelo.obtenerByCI(Integer.parseInt(et_ci2.getText().toString()));
                    if(usuarioModelo!=null && usuarioModelo2!=null){
                         parentescoModelo = new ParentescoModelo(
                                context,
                                id,
                                spinner.getSelectedItem().toString(),
                                usuarioModelo.getId(),
                                usuarioModelo2.getId()
                        );
                        if(id==0){
                            parentescoModelo.agregar(parentescoModelo);
                        }else{
                            parentescoModelo.editar(parentescoModelo);
                        }
                        Intent intent= new Intent(context, ListaParentescoControlador.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(context, "Error: Usuario no encontrado", Toast.LENGTH_SHORT).show();
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
            et_ci = findViewById(R.id.et_ci);
            et_ci2 = findViewById(R.id.et_ci2);

            spinner = findViewById(R.id.spinner);
            adapter= new ArrayAdapter(context, android.R.layout.simple_spinner_item,tipos);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            Intent intent = getIntent();
            Bundle bolsa= intent.getExtras();
            id=bolsa.getInt("id");

            if (id!=0){
                int id = bolsa.getInt("idUsuario");
                int id2 = bolsa.getInt("idUsuario2");
                usuarioModelo = new UsuarioModelo(context);
                et_ci.setText(usuarioModelo.obtenerByID(id).getCi()+"");
                et_ci2.setText(usuarioModelo.obtenerByID(id2).getCi()+"");
                for (int i = 0; i < tipos.length; i++) {
                    if(tipos[i].equals(bolsa.getString("tipo"))){
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