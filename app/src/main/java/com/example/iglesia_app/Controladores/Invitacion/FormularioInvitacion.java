package com.example.iglesia_app.Controladores.Invitacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.iglesia_app.Controladores.Invitacion.ListaInvitacion;
import com.example.iglesia_app.Modelos.Invitacion.InvitacionDato;
import com.example.iglesia_app.Modelos.Invitacion.InvitacionNegocio;
import com.example.iglesia_app.Modelos.Usuario.UsuarioDato;
import com.example.iglesia_app.Modelos.Usuario.UsuarioNegocio;
import com.example.iglesia_app.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FormularioInvitacion extends AppCompatActivity {

    Context context;
    Button btn_guardar;
    EditText et_ci,et_ci2,et_fecha;
    private int id;
    InvitacionNegocio invitacionNegocio;
    UsuarioNegocio usuarioNegocio;
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
                    usuarioNegocio=new UsuarioNegocio(context);
                    UsuarioDato usuarioDato=usuarioNegocio.obtenerByCI(Integer.parseInt(et_ci.getText().toString()));
                    UsuarioDato usuarioDato1=usuarioNegocio.obtenerByCI(Integer.parseInt(et_ci2.getText().toString()));
                    if (usuarioDato !=null && usuarioDato1!=null){
                        int idUsuario=usuarioDato.getId();
                        int idUsuario2=usuarioDato1.getId();
                        InvitacionDato invitacionDato = new InvitacionDato(id,
                                et_fecha.getText().toString(),
                                idUsuario,
                                idUsuario2
                        );
                        invitacionNegocio = new InvitacionNegocio(context);
                        if (id == 0) {
                            Calendar calendar = Calendar.getInstance();
                            Date fechaHoraActual = calendar.getTime();
                            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                            String fecha=formato.format(fechaHoraActual);
                            invitacionDato.setFecha(fecha);
                            invitacionNegocio.agregar(invitacionDato);
                        }else{
                            invitacionNegocio.editar(invitacionDato);
                        }
                        Intent intent = new Intent(context, ListaInvitacion.class);
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
            usuarioNegocio = new UsuarioNegocio(context);
            int ci=usuarioNegocio.obtenerByID(bolsa.getInt("idUsuario")).getCi();
            int ci2=usuarioNegocio.obtenerByID(bolsa.getInt("idUsuario2")).getCi();
            et_ci.setText(ci+"");
            et_ci2.setText(ci2+"");
            et_fecha.setText(bolsa.getString("fecha"));
        }else{
            et_fecha.setEnabled(false);
        }
    }
}