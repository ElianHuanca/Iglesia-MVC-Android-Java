package com.example.iglesia_app.Controladores.Cargo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.iglesia_app.Modelos.CargoModelo;
import com.example.iglesia_app.Modelos.MinisterioModelo;
import com.example.iglesia_app.Modelos.UsuarioModelo;
import com.example.iglesia_app.R;
import com.example.iglesia_app.TableDynamic;

import java.util.ArrayList;
import java.util.List;

public class ListaCargoControlador extends AppCompatActivity implements View.OnClickListener{
    Context context;
    private TableLayout tableLayout;
    private String[]header = {"ID","USUARIO","MINISTERIO","ESTADO","FECHA INICIO","FECHA FIN"};
    private ArrayList<String[]> rows=new ArrayList<>();
    EditText et_id;
    CargoModelo cargoModelo;
    UsuarioModelo usuarioModelo;
    MinisterioModelo ministerioModelo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargo_lista);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    private void init(){
        context=getApplicationContext();

        et_id=findViewById(R.id.et_id);
        context=getApplicationContext();
        tableLayout= (TableLayout) findViewById(R.id.table);
        TableDynamic tableDynamic= new TableDynamic(tableLayout,context);
        tableDynamic.addHeader(header);
        tableDynamic.addData(obtenerCargos());
        tableDynamic.backgroundHeader(Color.DKGRAY);
        tableDynamic.backgroundData(Color.LTGRAY,Color.WHITE);
        tableDynamic.textColorData(Color.BLACK);
        tableDynamic.textColorHeader(Color.WHITE);
        tableDynamic.lineColor(Color.LTGRAY);
    }

    private ArrayList<String[]> obtenerCargos(){
        cargoModelo=new CargoModelo(context);
        usuarioModelo=new UsuarioModelo(context);
        ministerioModelo=new MinisterioModelo(context);
        List<CargoModelo> cargos=cargoModelo.listar();
        for (CargoModelo cargo:cargos) {
            String usuario=usuarioModelo.obtenerByID(cargo.getIdUsuario()).getNombres();
            String ministerio=ministerioModelo.obtenerByID(cargo.getIdMinisterio()).getNombre();
            rows.add(new String[]{String.valueOf(cargo.getId()),usuario,ministerio,cargo.isEstado()?"Activo":"Inactivo",cargo.getFecha(),cargo.getFechaFin()});
        }
        return rows;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_registrar:
                Intent intent = new Intent(context, FormularioCargoControlador.class);
                Bundle bolsa1 = new Bundle();
                bolsa1.putInt("id",0);
                intent.putExtras(bolsa1);
                startActivity(intent);                
                break;
            case R.id.btn_modificar:
                if (!et_id.getText().toString().isEmpty()){
                    int id= Integer.parseInt(et_id.getText().toString());
                    cargoModelo=new CargoModelo(context);
                    cargoModelo=cargoModelo.obtenerByID(id);
                    if (cargoModelo!=null){
                        Bundle bolsa=new Bundle();
                        bolsa.putInt("id",cargoModelo.getId());
                        bolsa.putBoolean("estado",cargoModelo.isEstado());
                        bolsa.putString("fecha",cargoModelo.getFecha());
                        bolsa.putString("fechaFin",cargoModelo.getFechaFin());
                        bolsa.putInt("idMinisterio",cargoModelo.getIdMinisterio());
                        bolsa.putInt("idUsuario",cargoModelo.getIdUsuario());
                        Intent intent1 = new Intent(context, FormularioCargoControlador.class);
                        intent1.putExtras(bolsa);
                        startActivity(intent1);
                    }else{
                        Toast.makeText(this, "No existe ese id", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "Digite Un ID", Toast.LENGTH_SHORT).show();
                }                    
                break;
            case R.id.btn_eliminar:
                if (!et_id.getText().toString().isEmpty()){
                    cargoModelo=new CargoModelo(context);
                    cargoModelo.eliminar(Integer.parseInt(et_id.getText().toString()));
                    Intent intent2 = new Intent(context, ListaCargoControlador.class);
                    startActivity(intent2);
                }else{
                    Toast.makeText(this, "Digite Un ID", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}