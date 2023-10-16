package com.example.iglesia_app.Controladores.Asistencia;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.iglesia_app.Modelos.ActividadModelo;
import com.example.iglesia_app.Modelos.AsistenciaModelo;
import com.example.iglesia_app.Modelos.UsuarioModelo;
import com.example.iglesia_app.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FormularioAsistenciaControlador extends AppCompatActivity {
    EditText et_ciasistencia,et_horaasistencia,et_idActividad;
    Button btn_gasistencia;
    private int id;
    AsistenciaModelo asistenciaModelo;
    UsuarioModelo usuarioModelo;
    ActividadModelo actividadModelo;
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
                    usuarioModelo = new UsuarioModelo(context);
                    actividadModelo = new ActividadModelo(context);
                    usuarioModelo=usuarioModelo.obtenerByCI(Integer.parseInt(et_ciasistencia.getText().toString()));
                    actividadModelo=actividadModelo.obtenerByID(Integer.parseInt(et_idActividad.getText().toString()));
                    if (usuarioModelo!=null && actividadModelo!=null){
                        int idUsuario=usuarioModelo.getId();
                        AsistenciaModelo asistenciaModelo=new AsistenciaModelo(context,id,et_horaasistencia.getText().toString(),idUsuario,Integer.parseInt(et_idActividad.getText().toString()));
                        if(id==0){
                            Calendar calendar = Calendar.getInstance();
                            Date fechaHoraActual = calendar.getTime();
                            SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                            String hora=formato.format(fechaHoraActual);
                            asistenciaModelo.setHora(hora);
                            asistenciaModelo.agregar(asistenciaModelo);
                        }else{
                            asistenciaModelo.editar(asistenciaModelo);
                        }
                        Intent intent=new Intent(context,ListaAsistenciaControlador.class);
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
            usuarioModelo = new UsuarioModelo(context);
            int ci=usuarioModelo.obtenerByID(bolsa.getInt("idUsuario")).getCi();
            et_ciasistencia.setText(ci+"");
            et_horaasistencia.setText(bolsa.getString("hora"));
            et_idActividad.setText(bolsa.getInt("idActividad")+"");
        }else{
            et_horaasistencia.setEnabled(false);
        }
    }


}