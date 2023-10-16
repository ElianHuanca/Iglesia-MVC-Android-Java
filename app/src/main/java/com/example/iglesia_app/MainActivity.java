package com.example.iglesia_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.iglesia_app.Controladores.Actividad.ListaActividad;
import com.example.iglesia_app.Controladores.Actividad.ListaActividadControlador;
import com.example.iglesia_app.Controladores.Asistencia.ListaAsistencia;
import com.example.iglesia_app.Controladores.Asistencia.ListaAsistenciaControlador;
import com.example.iglesia_app.Controladores.Cargo.ListaCargo;
import com.example.iglesia_app.Controladores.Cargo.ListaCargoControlador;
import com.example.iglesia_app.Controladores.Ingreso.ListaIngreso;
import com.example.iglesia_app.Controladores.Ingreso.ListaIngresoControlador;
import com.example.iglesia_app.Controladores.Invitacion.ListaInvitacion;
import com.example.iglesia_app.Controladores.Invitacion.ListaInvitacionControlador;
import com.example.iglesia_app.Controladores.Ministerio.ListaMinisterio;
import com.example.iglesia_app.Controladores.Ministerio.ListaMinisterioControlador;
import com.example.iglesia_app.Controladores.Parentesco.ListaParentesco;
import com.example.iglesia_app.Controladores.Parentesco.ListaParentescoControlador;
import com.example.iglesia_app.Controladores.Usuario.ListaUsuario;
import com.example.iglesia_app.Controladores.Usuario.ListaUsuarioControlador;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Context context;
    Button btn_usuarios,btn_asistencias,btn_ministerios,btn_cargos,btn_invitaciones,btn_parentescos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        context=getApplicationContext();
        btn_usuarios=findViewById(R.id.btn_usuarios);
        btn_asistencias=findViewById(R.id.btn_asistencias);
        btn_ministerios=findViewById(R.id.btn_ministerios);
        btn_cargos=findViewById(R.id.btn_cargos);
        btn_invitaciones=findViewById(R.id.btn_invitaciones);
        btn_parentescos=findViewById(R.id.btn_parentescos);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_usuarios:
                /*Intent intent = new Intent(context, ListaUsuario.class);
                startActivity(intent);*/
                Intent intent = new Intent(context, ListaUsuarioControlador.class);
                startActivity(intent);
                break;
            case R.id.btn_asistencias:
                /*Intent intent2 = new Intent(context, ListaAsistencia.class);
                startActivity(intent2);*/
                Intent intent2 = new Intent(context, ListaAsistenciaControlador.class);
                startActivity(intent2);
                break;
            case R.id.btn_ministerios:
                Intent intent3 = new Intent(context, ListaMinisterioControlador.class);
                startActivity(intent3);
                break;
            case R.id.btn_cargos:
                Intent intent4 = new Intent(context, ListaCargoControlador.class);
                startActivity(intent4);
                break;
            case R.id.btn_invitaciones:
                Intent intent5 = new Intent(context, ListaInvitacionControlador.class);
                startActivity(intent5);
                break;
            case R.id.btn_parentescos:
                Intent intent6 = new Intent(context, ListaParentescoControlador.class);
                startActivity(intent6);
                break;
            case R.id.btn_actividades:
                Intent intent7 = new Intent(context, ListaActividadControlador.class);
                startActivity(intent7);
                break;
            case R.id.btn_ingresos:
                Intent intent8 = new Intent(context, ListaIngresoControlador.class);
                startActivity(intent8);
                break;
        }
    }
}