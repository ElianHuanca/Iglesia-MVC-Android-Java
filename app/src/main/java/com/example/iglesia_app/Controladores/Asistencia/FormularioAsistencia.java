package com.example.iglesia_app.Controladores.Asistencia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.iglesia_app.Modelos.Actividad.ActividadDato;
import com.example.iglesia_app.Modelos.Actividad.ActividadNegocio;
import com.example.iglesia_app.Modelos.Asistencia.AsistenciaDato;
import com.example.iglesia_app.Modelos.Asistencia.AsistenciaNegocio;
import com.example.iglesia_app.Modelos.Usuario.UsuarioDato;
import com.example.iglesia_app.Modelos.Usuario.UsuarioNegocio;
import com.example.iglesia_app.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FormularioAsistencia extends AppCompatActivity {
    EditText et_ciasistencia,et_horaasistencia,et_idActividad;
    Button btn_gasistencia;
    private int id;
    AsistenciaNegocio asistenciaNegocio;
    UsuarioNegocio usuarioNegocio;
    ActividadNegocio actividadNegocio;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistencia_formulario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();

        btn_gasistencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    asistenciaNegocio=new AsistenciaNegocio(context);
                    usuarioNegocio = new UsuarioNegocio(context);
                    actividadNegocio = new ActividadNegocio(context);
                    UsuarioDato usuarioDato=usuarioNegocio.obtenerByCI(Integer.parseInt(et_ciasistencia.getText().toString()));
                    ActividadDato actividadDato=actividadNegocio.obtenerByID(Integer.parseInt(et_idActividad.getText().toString()));
                    if (usuarioDato!=null && actividadDato!=null){
                        int idUsuario=usuarioDato.getId();
                        AsistenciaDato asistenciaDato=new AsistenciaDato(id,et_horaasistencia.getText().toString(),idUsuario,Integer.parseInt(et_idActividad.getText().toString()));
                        if(id==0){
                            Calendar calendar = Calendar.getInstance();
                            Date fechaHoraActual = calendar.getTime();
                            SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                            String hora=formato.format(fechaHoraActual);
                            asistenciaDato.setHora(hora);
                            asistenciaNegocio.agregar(asistenciaDato);
                        }else{
                            asistenciaNegocio.editar(asistenciaDato);
                        }
                        Intent intent=new Intent(context,ListaAsistencia.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(context, "Hubo un problema al guardar los datos", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e) {
                Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void init(){
        context=getApplicationContext();
        et_ciasistencia=findViewById(R.id.et_ciasistencia);
        et_idActividad=findViewById(R.id.et_idActividad);
        et_horaasistencia=findViewById(R.id.et_horaasistencia);
        btn_gasistencia = findViewById(R.id.btn_gasistencia);

        Intent intent = getIntent();
        Bundle bolsa= intent.getExtras();
        id=bolsa.getInt("id");

        if (id!=0){
            usuarioNegocio = new UsuarioNegocio(context);
            int ci=usuarioNegocio.obtenerByID(bolsa.getInt("idUsuario")).getCi();
            et_ciasistencia.setText(ci+"");
            et_horaasistencia.setText(bolsa.getString("hora"));
            et_idActividad.setText(bolsa.getInt("idActividad")+"");
        }else{
            et_horaasistencia.setEnabled(false);
        }
    }


}