package com.example.iglesia_app.Controladores.Cargo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.iglesia_app.Modelos.Cargo.CargoDato;
import com.example.iglesia_app.Modelos.Cargo.CargoNegocio;
import com.example.iglesia_app.Modelos.Ministerio.MinisterioNegocio;
import com.example.iglesia_app.Modelos.Usuario.UsuarioNegocio;
import com.example.iglesia_app.R;
import com.example.iglesia_app.TableDynamic;

import java.util.ArrayList;
import java.util.List;

public class ListaCargo extends AppCompatActivity implements View.OnClickListener{
    Context context;
    private TableLayout tableLayout;
    private String[]header = {"ID","USUARIO","MINISTERIO","ESTADO","FECHA INICIO","FECHA FIN"};
    private ArrayList<String[]> rows=new ArrayList<>();
    EditText et_id;
    CargoNegocio cargoNegocio;
    UsuarioNegocio usuarioNegocio;
    MinisterioNegocio ministerioNegocio;
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
        cargoNegocio=new CargoNegocio(context);
        usuarioNegocio=new UsuarioNegocio(context);
        ministerioNegocio=new MinisterioNegocio(context);
        List<CargoDato> cargos=cargoNegocio.listar();
        for (CargoDato cargo:cargos) {
            String usuario=usuarioNegocio.obtenerByID(cargo.getIdUsuario()).getNombres();
            String ministerio=ministerioNegocio.obtenerByID(cargo.getIdMinisterio()).getNombre();
            rows.add(new String[]{String.valueOf(cargo.getId()),usuario,ministerio,cargo.isEstado()?"Activo":"Inactivo",cargo.getFecha(),cargo.getFechaFin()});
        }
        return rows;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_registrar:
                Intent intent = new Intent(context, FormularioCargo.class);
                Bundle bolsa1 = new Bundle();
                bolsa1.putInt("id",0);
                intent.putExtras(bolsa1);
                startActivity(intent);                
                break;
            case R.id.btn_modificar:
                if (!et_id.getText().toString().isEmpty()){
                    int id= Integer.parseInt(et_id.getText().toString());
                    cargoNegocio=new CargoNegocio(context);
                    CargoDato cargoDato=cargoNegocio.obtenerByID(id);
                    if (cargoDato!=null){
                        Bundle bolsa=new Bundle();
                        bolsa.putInt("id",cargoDato.getId());
                        bolsa.putBoolean("estado",cargoDato.isEstado());
                        bolsa.putString("fecha",cargoDato.getFecha());
                        bolsa.putString("fechaFin",cargoDato.getFechaFin());
                        bolsa.putInt("idMinisterio",cargoDato.getIdMinisterio());
                        bolsa.putInt("idUsuario",cargoDato.getIdUsuario());
                        Intent intent1 = new Intent(context, FormularioCargo.class);
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
                    cargoNegocio=new CargoNegocio(context);
                    cargoNegocio.eliminar(Integer.parseInt(et_id.getText().toString()));
                    Intent intent2 = new Intent(context, ListaCargo.class);
                    startActivity(intent2);
                }else{
                    Toast.makeText(this, "Digite Un ID", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}