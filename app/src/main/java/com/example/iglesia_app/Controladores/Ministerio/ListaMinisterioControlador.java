package com.example.iglesia_app.Controladores.Ministerio;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.iglesia_app.Modelos.MinisterioModelo;
import com.example.iglesia_app.R;
import com.example.iglesia_app.TableDynamic;

import java.util.ArrayList;
import java.util.List;

public class ListaMinisterioControlador extends AppCompatActivity implements View.OnClickListener{
    Context context;
    private TableLayout tableLayout;
    private String[]header = {"ID","NOMBRE","DESCRIPCION"};
    private ArrayList<String[]> rows=new ArrayList<>();
    EditText et_id;
    MinisterioModelo ministerioModelo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ministerio_lista);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }
    private void init(){
        context=getApplicationContext();

        et_id=findViewById(R.id.et_id);
        context=getApplicationContext();
        tableLayout= (TableLayout) findViewById(R.id.table);
        TableDynamic tableDynamic= new TableDynamic(tableLayout,getApplicationContext());
        tableDynamic.addHeader(header);
        tableDynamic.addData(obtenerMinisterios());
        tableDynamic.backgroundHeader(Color.DKGRAY);
        tableDynamic.backgroundData(Color.LTGRAY,Color.WHITE);
        tableDynamic.textColorData(Color.BLACK);
        tableDynamic.textColorHeader(Color.WHITE);
        tableDynamic.lineColor(Color.LTGRAY);
    }

    private ArrayList<String[]> obtenerMinisterios(){
        ministerioModelo=new MinisterioModelo(context);
        List<MinisterioModelo> ministerios=ministerioModelo.listar();
        for (MinisterioModelo ministerio:ministerios) {
            rows.add(new String[]{String.valueOf(ministerio.getId()),ministerio.getNombre(),ministerio.getDescripcion()});
        }
        return rows;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_rministerio:
                Intent intent = new Intent(context, FormularioMinisterioControlador.class);
                Bundle bolsa1 = new Bundle();
                bolsa1.putInt("id",0);
                intent.putExtras(bolsa1);
                startActivity(intent);
                break;
            case R.id.btn_mministerio:
                if (!et_id.getText().toString().isEmpty()){
                    int id= Integer.parseInt(et_id.getText().toString());
                    ministerioModelo=new MinisterioModelo(context);
                    ministerioModelo=ministerioModelo.obtenerByID(id);

                    if (ministerioModelo!=null){
                        Bundle bolsa=new Bundle();
                        bolsa.putInt("id",ministerioModelo.getId());
                        bolsa.putString("nombre",ministerioModelo.getNombre());
                        bolsa.putString("descripcion",ministerioModelo.getDescripcion());
                        Intent intent1 = new Intent(context, FormularioMinisterioControlador.class);
                        intent1.putExtras(bolsa);
                        startActivity(intent1);
                    }else{
                        Toast.makeText(this, "No existe ese id", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "Digite Un ID", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_eministerio:
                if (!et_id.getText().toString().isEmpty()){
                    ministerioModelo=new MinisterioModelo(context);
                    ministerioModelo.eliminar(Integer.parseInt(et_id.getText().toString()));
                    Intent intent2 = new Intent(context, ListaMinisterioControlador.class);
                    startActivity(intent2);
                }else{
                    Toast.makeText(this, "Digite Un ID", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}