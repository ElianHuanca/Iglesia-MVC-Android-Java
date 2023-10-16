package com.example.iglesia_app.Controladores.Ingreso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.iglesia_app.Modelos.Actividad.ActividadDato;
import com.example.iglesia_app.Modelos.Ingreso.IngresoDato;
import com.example.iglesia_app.Modelos.Ingreso.IngresoNegocio;
import com.example.iglesia_app.Modelos.Actividad.ActividadNegocio;
import com.example.iglesia_app.R;
import com.example.iglesia_app.TableDynamic;

import java.util.ArrayList;
import java.util.List;

public class ListaIngreso extends AppCompatActivity implements View.OnClickListener{
    Context context;
    EditText et_id;
    private TableLayout tableLayout;
    ActividadNegocio actividadNegocio;
    IngresoNegocio ingresoNegocio;
    private ArrayList<String[]> rows=new ArrayList<>();
    String[] header={"ID","NOMBRE","MONTO","IDACTIVIDAD","ACTIVIDAD","FECHA","HORA INICIO"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso_lista);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    private void init(){
        context=getApplicationContext();
        et_id=findViewById(R.id.et_id);
        tableLayout= (TableLayout) findViewById(R.id.table);
        TableDynamic tableDynamic= new TableDynamic(tableLayout,getApplicationContext());
        tableDynamic.addHeader(header);
        tableDynamic.addData(obtenerIngresos());
        tableDynamic.backgroundHeader(Color.DKGRAY);
        tableDynamic.backgroundData(Color.LTGRAY,Color.WHITE);
        tableDynamic.textColorData(Color.BLACK);
        tableDynamic.textColorHeader(Color.WHITE);
        tableDynamic.lineColor(Color.LTGRAY);
    }

    private ArrayList<String[]> obtenerIngresos(){
        actividadNegocio=new ActividadNegocio(context);
        ingresoNegocio = new IngresoNegocio(context);
        List<IngresoDato> ingresos=ingresoNegocio.listar();
        for (IngresoDato ingreso:ingresos) {
            ActividadDato actividadDato=actividadNegocio.obtenerByID(ingreso.getIdActividad());
            rows.add(new String[]{
                    String.valueOf(ingreso.getId()),
                    ingreso.getNombre(),
                    String.valueOf(ingreso.getMonto()),
                    String.valueOf(ingreso.getIdActividad()),
                    actividadDato.getNombre(),
                    actividadDato.getFecha(),
                    actividadDato.getHora()
            });
        }
        return rows;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_registrar:
                Intent intent=new Intent(context, FormularioIngreso.class);
                Bundle bolsa1= new Bundle();
                bolsa1.putInt("id",0);
                intent.putExtras(bolsa1);
                startActivity(intent);
                break;
            case R.id.btn_modificar:
                if (!et_id.getText().toString().isEmpty()){
                    int id= Integer.parseInt(et_id.getText().toString());
                    ingresoNegocio=new IngresoNegocio(context);
                    IngresoDato ingresoDato=ingresoNegocio.obtenerByID(id);
                    if (ingresoDato!=null){
                        Bundle bolsa=new Bundle();
                        bolsa.putInt("id",ingresoDato.getId());
                        bolsa.putString("nombre",ingresoDato.getNombre());
                        bolsa.putDouble("monto",ingresoDato.getMonto());
                        bolsa.putInt("idActividad",ingresoDato.getIdActividad());
                        Intent intent1 = new Intent(context, FormularioIngreso.class);
                        intent1.putExtras(bolsa);
                        startActivity(intent1);
                    }else{
                        Toast.makeText(this, "No existe ese id en ingresos", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "Digite Un ID", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_eliminar:
                if (!et_id.getText().toString().isEmpty()){
                    ingresoNegocio=new IngresoNegocio(context);
                    IngresoDato ingresoDato=ingresoNegocio.obtenerByID(Integer.parseInt(et_id.getText().toString()));
                    if (ingresoDato!=null){
                        actividadNegocio=new ActividadNegocio(context);
                        ActividadDato actividadDato=actividadNegocio.obtenerByID(ingresoDato.getIdActividad());
                        actividadDato.setMontoTotal(actividadDato.getMontoTotal()-ingresoDato.getMonto());
                        actividadNegocio.editarMonto(actividadDato);
                        ingresoNegocio.eliminar(Integer.parseInt(et_id.getText().toString()));
                        Intent intent2=new Intent(context, ListaIngreso.class);
                        startActivity(intent2);
                    }else{
                        Toast.makeText(this, "No existe ese id en ingresos", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "Digite Un ID", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}