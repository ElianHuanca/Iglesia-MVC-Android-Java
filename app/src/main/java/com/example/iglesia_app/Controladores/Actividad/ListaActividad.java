package com.example.iglesia_app.Controladores.Actividad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.iglesia_app.Controladores.Actividad.FormularioActividad;
import com.example.iglesia_app.Controladores.Actividad.ListaActividad;
import com.example.iglesia_app.Modelos.Actividad.ActividadDato;
import com.example.iglesia_app.Modelos.Actividad.ActividadNegocio;
import com.example.iglesia_app.R;
import com.example.iglesia_app.TableDynamic;

import java.util.ArrayList;
import java.util.List;

public class ListaActividad extends AppCompatActivity implements View.OnClickListener{

    Context context;
    private TableLayout tableLayout;
    private String[]header = {"ID","NOMBRE","FECHA","HORA INICIO","HORA FIN","MONTO TOTAL"};
    private ArrayList<String[]> rows=new ArrayList<>();
    ActividadNegocio actividadNegocio;
    EditText et_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_lista);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    private void init(){
        context=getApplicationContext();
        tableLayout= (TableLayout) findViewById(R.id.table);
        TableDynamic tableDynamic= new TableDynamic(tableLayout,getApplicationContext());
        tableDynamic.addHeader(header);
        tableDynamic.addData(obtenerActividades());
        tableDynamic.backgroundHeader(Color.DKGRAY);
        tableDynamic.backgroundData(Color.LTGRAY,Color.WHITE);
        tableDynamic.textColorData(Color.BLACK);
        tableDynamic.textColorHeader(Color.WHITE);
        tableDynamic.lineColor(Color.LTGRAY);

        et_id=findViewById(R.id.et_id);
    }

    private ArrayList<String[]> obtenerActividades(){
        actividadNegocio=new ActividadNegocio(context);
        List<ActividadDato> actividades=actividadNegocio.listar();
        for (ActividadDato actividad:actividades) {
            rows.add(new String[]{String.valueOf(actividad.getId()),actividad.getNombre(),actividad.getFecha(),actividad.getHora(),actividad.getHoraFin(),String.valueOf(actividad.getMontoTotal())});
        }
        return rows;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_registrar:
                Intent intent = new Intent(context, FormularioActividad.class);
                Bundle bolsa1 = new Bundle();
                bolsa1.putInt("id",0);
                intent.putExtras(bolsa1);
                startActivity(intent);
                break;
            case R.id.btn_modificar:
                if (!et_id.getText().toString().isEmpty()){
                    int id= Integer.parseInt(et_id.getText().toString());
                    actividadNegocio=new ActividadNegocio(context);
                    ActividadDato actividadDato=actividadNegocio.obtenerByID(id);
                    if (actividadDato!=null){
                        Bundle bolsa=new Bundle();
                        bolsa.putInt("id",actividadDato.getId());
                        bolsa.putString("nombre",actividadDato.getNombre());
                        bolsa.putString("fecha",actividadDato.getFecha());
                        bolsa.putString("hora",actividadDato.getHora());
                        bolsa.putString("hora2",actividadDato.getHoraFin());
                        Intent intent1 = new Intent(context, FormularioActividad.class);
                        intent1.putExtras(bolsa);
                        startActivity(intent1);
                    }else{
                        Toast.makeText(this, "No existe ese id en actividads", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "Digite Un ID", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_eliminar:
                if (!et_id.getText().toString().isEmpty()){
                    actividadNegocio=new ActividadNegocio(context);
                    actividadNegocio.eliminar(Integer.parseInt(et_id.getText().toString()));
                    Intent intent2 = new Intent(context, ListaActividad.class);
                    startActivity(intent2);
                }else{
                    Toast.makeText(this, "Digite Un ID", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}