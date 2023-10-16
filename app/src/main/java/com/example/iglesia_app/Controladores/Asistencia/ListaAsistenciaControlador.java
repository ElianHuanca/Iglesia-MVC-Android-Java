package com.example.iglesia_app.Controladores.Asistencia;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.iglesia_app.Modelos.ActividadModelo;
import com.example.iglesia_app.Modelos.AsistenciaModelo;
import com.example.iglesia_app.Modelos.UsuarioModelo;
import com.example.iglesia_app.R;
import com.example.iglesia_app.TableDynamic;

import java.util.ArrayList;
import java.util.List;

public class ListaAsistenciaControlador extends AppCompatActivity implements View.OnClickListener {
    Context context;
    EditText et_idasistencia;
    private TableLayout tableLayout;
    UsuarioModelo usuarioModelo;
    AsistenciaModelo asistenciaModelo;
    ActividadModelo actividadModelo;
    private ArrayList<String[]> rows=new ArrayList<>();
    String[] header={"ID","HORA ASISTENCIA","FECHA","ID ACTIVIDAD","USUARIO"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistencia_lista);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    private void init(){
        context=getApplicationContext();
        et_idasistencia=findViewById(R.id.et_idasistencia);
        tableLayout= (TableLayout) findViewById(R.id.tablaAsistencia);
        TableDynamic tableDynamic= new TableDynamic(tableLayout,getApplicationContext());
        tableDynamic.addHeader(header);
        tableDynamic.addData(obtenerAsistencias());
        tableDynamic.backgroundHeader(Color.DKGRAY);
        tableDynamic.backgroundData(Color.LTGRAY,Color.WHITE);
        tableDynamic.textColorData(Color.BLACK);
        tableDynamic.textColorHeader(Color.WHITE);
        tableDynamic.lineColor(Color.LTGRAY);
    }

    private ArrayList<String[]> obtenerAsistencias(){
        usuarioModelo=new UsuarioModelo(context);
        asistenciaModelo = new AsistenciaModelo(context);
        actividadModelo = new ActividadModelo(context);
        List<AsistenciaModelo> asistencias=asistenciaModelo.listar();
        for (AsistenciaModelo asistencia:asistencias) {
            String fecha=actividadModelo.obtenerByID(asistencia.getIdActividad()).getFecha();
            String nombre=usuarioModelo.obtenerByID(asistencia.getIdUsuario()).getNombres();
            rows.add(new String[]{String.valueOf(asistencia.getId()),asistencia.getHora(),fecha,String.valueOf(asistencia.getIdActividad()),nombre});
        }
        return rows;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_rasistencia:
                Intent intent=new Intent(context,FormularioAsistenciaControlador.class);
                Bundle bolsa1= new Bundle();
                bolsa1.putInt("id",0);
                intent.putExtras(bolsa1);
                startActivity(intent);
                break;
            case R.id.btn_masistencia:
                if (!et_idasistencia.getText().toString().isEmpty()){
                    int id= Integer.parseInt(et_idasistencia.getText().toString());
                    asistenciaModelo=new AsistenciaModelo(context);
                    asistenciaModelo=asistenciaModelo.obtenerByID(id);
                    if (asistenciaModelo!=null){
                        Bundle bolsa=new Bundle();
                        bolsa.putInt("id",asistenciaModelo.getId());
                        bolsa.putString("hora",asistenciaModelo.getHora());
                        bolsa.putInt("idUsuario",asistenciaModelo.getIdUsuario());
                        bolsa.putInt("idActividad",asistenciaModelo.getIdActividad());
                        Intent intent1 = new Intent(context, FormularioAsistenciaControlador.class);
                        intent1.putExtras(bolsa);
                        startActivity(intent1);
                    }else{
                        Toast.makeText(this, "No existe ese id en asistencias", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "Digite Un ID", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_easistencia:
                if (!et_idasistencia.getText().toString().isEmpty()){
                    asistenciaModelo=new AsistenciaModelo(context);
                    asistenciaModelo.eliminar(Integer.parseInt(et_idasistencia.getText().toString()));
                    Intent intent2=new Intent(context, ListaAsistenciaControlador.class);
                    startActivity(intent2);
                }else{
                    Toast.makeText(this, "Digite Un ID", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}