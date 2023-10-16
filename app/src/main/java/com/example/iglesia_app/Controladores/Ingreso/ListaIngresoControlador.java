package com.example.iglesia_app.Controladores.Ingreso;

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
import com.example.iglesia_app.Modelos.IngresoModelo;
import com.example.iglesia_app.R;
import com.example.iglesia_app.TableDynamic;

import java.util.ArrayList;
import java.util.List;

public class ListaIngresoControlador extends AppCompatActivity implements View.OnClickListener{
    Context context;
    EditText et_id;
    private TableLayout tableLayout;
    ActividadModelo actividadModelo;
    IngresoModelo ingresoModelo;
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
        actividadModelo=new ActividadModelo(context);
        ingresoModelo = new IngresoModelo(context);
        List<IngresoModelo> ingresos=ingresoModelo.listar();
        for (IngresoModelo ingreso:ingresos) {
            actividadModelo=actividadModelo.obtenerByID(ingreso.getIdActividad());
            rows.add(new String[]{
                    String.valueOf(ingreso.getId()),
                    ingreso.getNombre(),
                    String.valueOf(ingreso.getMonto()),
                    String.valueOf(ingreso.getIdActividad()),
                    actividadModelo.getNombre(),
                    actividadModelo.getFecha(),
                    actividadModelo.getHora()
            });
        }
        return rows;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_registrar:
                Intent intent=new Intent(context, FormularioIngresoControlador.class);
                Bundle bolsa1= new Bundle();
                bolsa1.putInt("id",0);
                intent.putExtras(bolsa1);
                startActivity(intent);
                break;
            case R.id.btn_modificar:
                if (!et_id.getText().toString().isEmpty()){
                    int id= Integer.parseInt(et_id.getText().toString());
                    ingresoModelo=new IngresoModelo(context);
                    ingresoModelo=ingresoModelo.obtenerByID(id);
                    if (ingresoModelo!=null){
                        Bundle bolsa=new Bundle();
                        bolsa.putInt("id",ingresoModelo.getId());
                        bolsa.putString("nombre",ingresoModelo.getNombre());
                        bolsa.putDouble("monto",ingresoModelo.getMonto());
                        bolsa.putInt("idActividad",ingresoModelo.getIdActividad());
                        Intent intent1 = new Intent(context, FormularioIngresoControlador.class);
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
                    ingresoModelo=new IngresoModelo(context);
                    ingresoModelo=ingresoModelo.obtenerByID(Integer.parseInt(et_id.getText().toString()));
                    if (ingresoModelo!=null){
                        actividadModelo=new ActividadModelo(context);
                        actividadModelo=actividadModelo.obtenerByID(ingresoModelo.getIdActividad());
                        actividadModelo.setMontoTotal(actividadModelo.getMontoTotal()-ingresoModelo.getMonto());
                        actividadModelo.editarMonto(actividadModelo);
                        ingresoModelo.eliminar(Integer.parseInt(et_id.getText().toString()));
                        Intent intent2=new Intent(context, ListaIngresoControlador.class);
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