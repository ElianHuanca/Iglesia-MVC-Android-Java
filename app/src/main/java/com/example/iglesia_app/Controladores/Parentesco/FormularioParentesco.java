package com.example.iglesia_app.Controladores.Parentesco;

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

import com.example.iglesia_app.Controladores.Parentesco.ListaParentesco;
import com.example.iglesia_app.Modelos.Parentesco.ParentescoDato;
import com.example.iglesia_app.Modelos.Parentesco.ParentescoNegocio;
import com.example.iglesia_app.Modelos.Usuario.UsuarioDato;
import com.example.iglesia_app.Modelos.Usuario.UsuarioNegocio;
import com.example.iglesia_app.R;

public class FormularioParentesco extends AppCompatActivity {
    Context context;
    String[] tipos = {"Padre", "Madre","Hermano","Hermana","Tio","Tia","Primo","Prima","Abuelo","Abuela"};
    Button btn_guardar;
    EditText et_ci,et_ci2;
    Spinner spinner;
    ArrayAdapter adapter;
    private int id;
    ParentescoNegocio parentescoNegocio;
    UsuarioNegocio usuarioNegocio;
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
                    parentescoNegocio = new ParentescoNegocio(context);
                    usuarioNegocio = new UsuarioNegocio(context);
                    UsuarioDato usuarioDato= usuarioNegocio.obtenerByCI(Integer.parseInt(et_ci.getText().toString()));
                    UsuarioDato usuarioDato2= usuarioNegocio.obtenerByCI(Integer.parseInt(et_ci2.getText().toString()));
                    if(usuarioDato!=null && usuarioDato2!=null){
                        ParentescoDato parentescoDato = new ParentescoDato(id,
                                spinner.getSelectedItem().toString(),
                                usuarioDato.getId(),
                                usuarioDato2.getId()
                        );
                        if(id==0){
                            parentescoNegocio.agregar(parentescoDato);
                        }else{
                            parentescoNegocio.editar(parentescoDato);
                        }
                        Intent intent= new Intent(context, ListaParentesco.class);
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
                et_ci.setText(bolsa.getInt("idUsuario")+"");
                et_ci2.setText(bolsa.getInt("idUsuario2")+"");
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