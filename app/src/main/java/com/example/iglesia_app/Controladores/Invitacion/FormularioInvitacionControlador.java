package com.example.iglesia_app.Controladores.Invitacion;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.iglesia_app.Modelos.InvitacionModelo;
import com.example.iglesia_app.Modelos.UsuarioModelo;
import com.example.iglesia_app.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FormularioInvitacionControlador extends AppCompatActivity {

    Context context;
    Button btn_guardar;
    EditText et_ci,et_ci2,et_fecha;
    private int id;
    InvitacionModelo invitacionModelo;
    UsuarioModelo usuarioModelo;
    UsuarioModelo usuarioModelo1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitacion_formulario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    usuarioModelo=new UsuarioModelo(context);
                    usuarioModelo=usuarioModelo.obtenerByCI(Integer.parseInt(et_ci.getText().toString()));
                    usuarioModelo1=usuarioModelo.obtenerByCI(Integer.parseInt(et_ci2.getText().toString()));
                    if (usuarioModelo !=null && usuarioModelo1!=null){
                        int idUsuario=usuarioModelo.getId();
                        int idUsuario2=usuarioModelo1.getId();
                         invitacionModelo = new InvitacionModelo(
                                 context,
                                 id,
                                et_fecha.getText().toString(),
                                idUsuario,
                                idUsuario2
                        );
                        if (id == 0) {
                            Calendar calendar = Calendar.getInstance();
                            Date fechaHoraActual = calendar.getTime();
                            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                            String fecha=formato.format(fechaHoraActual);
                            invitacionModelo.setFecha(fecha);
                            invitacionModelo.agregar(invitacionModelo);
                        }else{
                            invitacionModelo.editar(invitacionModelo);
                        }
                        Intent intent = new Intent(context, ListaInvitacionControlador.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(context, "Un CI no existe en el registro de Usuario", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e) {
                    Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void init(){
        context=getApplicationContext();
        et_fecha=findViewById(R.id.et_fecha);
        et_ci=findViewById(R.id.et_ci);
        et_ci2=findViewById(R.id.et_ci2);
        btn_guardar=findViewById(R.id.btn_guardar);

        Intent intent = getIntent();
        Bundle bolsa= intent.getExtras();
        id=bolsa.getInt("id");

        if (id!=0){
            usuarioModelo = new UsuarioModelo(context);
            int ci=usuarioModelo.obtenerByID(bolsa.getInt("idUsuario")).getCi();
            int ci2=usuarioModelo.obtenerByID(bolsa.getInt("idUsuario2")).getCi();
            et_ci.setText(ci+"");
            et_ci2.setText(ci2+"");
            et_fecha.setText(bolsa.getString("fecha"));
        }else{
            et_fecha.setEnabled(false);
        }
    }
}