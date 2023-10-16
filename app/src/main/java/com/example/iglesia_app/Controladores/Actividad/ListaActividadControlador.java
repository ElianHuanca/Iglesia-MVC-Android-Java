package com.example.iglesia_app.Controladores.Actividad;

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
import com.example.iglesia_app.R;
import com.example.iglesia_app.TableDynamic;

import java.util.ArrayList;
import java.util.List;

public class ListaActividadControlador extends AppCompatActivity implements View.OnClickListener{

    Context context;
    private TableLayout tableLayout;
    private String[]header = {"ID","NOMBRE","FECHA","HORA INICIO","HORA FIN","MONTO TOTAL"};
    private ArrayList<String[]> rows=new ArrayList<>();
    ActividadModelo actividadModelo;
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
        actividadModelo=new ActividadModelo(context);
        List<ActividadModelo> actividades=actividadModelo.listar();
        for (ActividadModelo actividad:actividades) {
            rows.add(new String[]{String.valueOf(actividad.getId()),actividad.getNombre(),actividad.getFecha(),actividad.getHora(),actividad.getHoraFin(),String.valueOf(actividad.getMontoTotal())});
        }
        return rows;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_registrar:
                Intent intent = new Intent(context, FormularioActividadControlador.class);
                Bundle bolsa1 = new Bundle();
                bolsa1.putInt("id",0);
                intent.putExtras(bolsa1);
                startActivity(intent);
                break;
            case R.id.btn_modificar:
                if (!et_id.getText().toString().isEmpty()){
                    int id= Integer.parseInt(et_id.getText().toString());
                    actividadModelo=new ActividadModelo(context);
                    actividadModelo=actividadModelo.obtenerByID(id);
                    if (actividadModelo!=null){
                        Bundle bolsa=new Bundle();
                        bolsa.putInt("id",actividadModelo.getId());
                        bolsa.putString("nombre",actividadModelo.getNombre());
                        bolsa.putString("fecha",actividadModelo.getFecha());
                        bolsa.putString("hora",actividadModelo.getHora());
                        bolsa.putString("hora2",actividadModelo.getHoraFin());
                        Intent intent1 = new Intent(context, FormularioActividadControlador.class);
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
                    actividadModelo=new ActividadModelo(context);
                    actividadModelo.eliminar(Integer.parseInt(et_id.getText().toString()));
                    Intent intent2 = new Intent(context, ListaActividadControlador.class);
                    startActivity(intent2);
                }else{
                    Toast.makeText(this, "Digite Un ID", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}