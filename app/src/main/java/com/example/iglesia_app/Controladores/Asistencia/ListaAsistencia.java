package com.example.iglesia_app.Controladores.Asistencia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.iglesia_app.Controladores.Usuario.FormularioUsuario;
import com.example.iglesia_app.Modelos.Actividad.ActividadNegocio;
import com.example.iglesia_app.Modelos.Asistencia.AsistenciaDato;
import com.example.iglesia_app.Modelos.Asistencia.AsistenciaNegocio;
import com.example.iglesia_app.Modelos.Usuario.UsuarioDato;
import com.example.iglesia_app.Modelos.Usuario.UsuarioNegocio;
import com.example.iglesia_app.R;
import com.example.iglesia_app.TableDynamic;

import java.util.ArrayList;
import java.util.List;

public class ListaAsistencia extends AppCompatActivity implements View.OnClickListener {
    Context context;
    EditText et_idasistencia;
    private TableLayout tableLayout;
    UsuarioNegocio usuarioNegocio;
    AsistenciaNegocio asistenciaNegocio;
    ActividadNegocio actividadNegocio;
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
        usuarioNegocio=new UsuarioNegocio(context);
        asistenciaNegocio = new AsistenciaNegocio(context);
        actividadNegocio = new ActividadNegocio(context);
        List<AsistenciaDato> asistencias=asistenciaNegocio.listar();
        for (AsistenciaDato asistencia:asistencias) {
            String fecha=actividadNegocio.obtenerByID(asistencia.getIdActividad()).getFecha();
            String nombre=usuarioNegocio.obtenerByID(asistencia.getIdUsuario()).getNombres();
            rows.add(new String[]{String.valueOf(asistencia.getId()),asistencia.getHora(),fecha,String.valueOf(asistencia.getIdActividad()),nombre});
        }
        return rows;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_rasistencia:
                Intent intent=new Intent(context,FormularioAsistencia.class);
                Bundle bolsa1= new Bundle();
                bolsa1.putInt("id",0);
                intent.putExtras(bolsa1);
                startActivity(intent);
                break;
            case R.id.btn_masistencia:
                if (!et_idasistencia.getText().toString().isEmpty()){
                    int id= Integer.parseInt(et_idasistencia.getText().toString());
                    asistenciaNegocio=new AsistenciaNegocio(context);
                    AsistenciaDato asistenciaDato=asistenciaNegocio.obtenerByID(id);
                    if (asistenciaDato!=null){
                        Bundle bolsa=new Bundle();
                        bolsa.putInt("id",asistenciaDato.getId());
                        bolsa.putString("hora",asistenciaDato.getHora());
                        bolsa.putInt("idUsuario",asistenciaDato.getIdUsuario());
                        bolsa.putInt("idActividad",asistenciaDato.getIdActividad());
                        Intent intent1 = new Intent(context, FormularioAsistencia.class);
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
                    asistenciaNegocio=new AsistenciaNegocio(context);
                    asistenciaNegocio.eliminar(Integer.parseInt(et_idasistencia.getText().toString()));
                    Intent intent2=new Intent(context,ListaAsistencia.class);
                    startActivity(intent2);
                }else{
                    Toast.makeText(this, "Digite Un ID", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}