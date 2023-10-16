package com.example.iglesia_app.Controladores.Parentesco;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.iglesia_app.Modelos.ParentescoModelo;
import com.example.iglesia_app.Modelos.UsuarioModelo;
import com.example.iglesia_app.R;
import com.example.iglesia_app.TableDynamic;

import java.util.ArrayList;
import java.util.List;

public class ListaParentescoControlador extends AppCompatActivity implements View.OnClickListener{

    Context context;
    private TableLayout tableLayout;
    private String[]header = {"ID","USUARIO","TIPO DE FAMILIARIDAD","FAMILIAR DE USUARIO"};
    private ArrayList<String[]> rows=new ArrayList<>();
    ParentescoModelo parentescoModelo;
    UsuarioModelo usuarioModelo;
    EditText et_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parentesco_lista);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    private void init(){
        context=getApplicationContext();
        tableLayout= (TableLayout) findViewById(R.id.table);
        TableDynamic tableDynamic= new TableDynamic(tableLayout,getApplicationContext());
        tableDynamic.addHeader(header);
        tableDynamic.addData(obtenerParentescos());
        tableDynamic.backgroundHeader(Color.DKGRAY);
        tableDynamic.backgroundData(Color.LTGRAY,Color.WHITE);
        tableDynamic.textColorData(Color.BLACK);
        tableDynamic.textColorHeader(Color.WHITE);
        tableDynamic.lineColor(Color.LTGRAY);

        et_id=findViewById(R.id.et_id);
    }

    private ArrayList<String[]> obtenerParentescos(){
        parentescoModelo=new ParentescoModelo(context);
        usuarioModelo=new UsuarioModelo(context);
        List<ParentescoModelo> parentescoes=parentescoModelo.listar();
        for (ParentescoModelo parentesco:parentescoes) {
            String nombreUsuario=usuarioModelo.obtenerByID(parentesco.getIdUsuario()).getNombres();
            String nombreFamiliar=usuarioModelo.obtenerByID(parentesco.getIdUsuario2()).getNombres();
            rows.add(new String[]{
                    String.valueOf(parentesco.getId()),
                    nombreUsuario,
                    parentesco.getTipo(),
                    nombreFamiliar
            });
        }
        return rows;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_registrar:
                Intent intent = new Intent(context, FormularioParentescoControlador.class);
                Bundle bolsa1 = new Bundle();
                bolsa1.putInt("id",0);
                intent.putExtras(bolsa1);
                startActivity(intent);
                break;
            case R.id.btn_modificar:
                if (!et_id.getText().toString().isEmpty()){
                    int id= Integer.parseInt(et_id.getText().toString());
                    parentescoModelo=new ParentescoModelo(context);
                    parentescoModelo=parentescoModelo.obtenerByID(id);
                    if (parentescoModelo!=null){
                        Bundle bolsa=new Bundle();
                        bolsa.putInt("id",parentescoModelo.getId());
                        bolsa.putString("tipo",parentescoModelo.getTipo());
                        bolsa.putInt("idUsuario",parentescoModelo.getIdUsuario());
                        bolsa.putInt("idUsuario2",parentescoModelo.getIdUsuario2());
                        Intent intent1 = new Intent(context, FormularioParentescoControlador.class);
                        intent1.putExtras(bolsa);
                        startActivity(intent1);
                    }else{
                        Toast.makeText(this, "No existe ese id en parentescos", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "Digite Un ID", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_eliminar:
                if (!et_id.getText().toString().isEmpty()){
                    parentescoModelo=new ParentescoModelo(context);
                    parentescoModelo.eliminar(Integer.parseInt(et_id.getText().toString()));
                    Intent intent2 = new Intent(context, ListaParentescoControlador.class);
                    startActivity(intent2);
                }else{
                    Toast.makeText(this, "Digite Un ID", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}