package com.example.iglesia_app.Controladores.Usuario;

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

import com.example.iglesia_app.MainActivity;
import com.example.iglesia_app.Modelos.Usuario.UsuarioDato;
import com.example.iglesia_app.Modelos.Usuario.UsuarioNegocio;
import com.example.iglesia_app.R;

public class FormularioUsuario extends AppCompatActivity {

    Context context;
    String[] roles = {"Miembro", "Visitante"};
    Button btn_add;
    EditText et_ci,et_nombres,et_celular,et_fecha;
    Spinner spinner;
    ArrayAdapter adapter;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_formulario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    UsuarioDato usuarioDato = new UsuarioDato(id,
                            Integer.parseInt(et_ci.getText().toString()),
                            et_nombres.getText().toString(),
                            Integer.parseInt(et_celular.getText().toString()),
                            et_fecha.getText().toString(),
                            spinner.getSelectedItem().toString()
                            );
                    UsuarioNegocio uc = new UsuarioNegocio(context);
                    if(id==0){
                        uc.agregar(usuarioDato);
                    }else{
                        uc.editar(usuarioDato);
                    }
                    Intent intent= new Intent(context, ListaUsuario.class);
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

            btn_add=findViewById(R.id.btn_guardar);
            et_ci = findViewById(R.id.et_ci);
            et_nombres = findViewById(R.id.et_nombres);
            et_celular = findViewById(R.id.et_celular);
            et_fecha = findViewById(R.id.et_fecha);

            spinner = findViewById(R.id.spinner);
            adapter= new ArrayAdapter(context, android.R.layout.simple_spinner_item,roles);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            Intent intent = getIntent();
            Bundle bolsa= intent.getExtras();
            id=bolsa.getInt("id");

            if (id!=0){
                et_ci.setText(bolsa.getInt("ci")+"");
                et_nombres.setText(bolsa.getString("nombres"));
                et_celular.setText(bolsa.getInt("celular")+"");
                et_fecha.setText(bolsa.getString("fecha"));
                for (int i = 0; i < roles.length; i++) {
                    if(roles[i].equals(bolsa.getString("tipo"))){
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