package com.example.iglesia_app.Controladores.Usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;
import com.example.iglesia_app.Modelos.UsuarioModelo;
import com.example.iglesia_app.R;
import com.example.iglesia_app.TableDynamic;

import java.util.ArrayList;
import java.util.List;

public class ListaUsuarioControlador extends AppCompatActivity implements View.OnClickListener{

    Context context;
    private TableLayout tableLayout;
    private String[]header = {"ID","CI","Nombres","Celular","Fecha","Tipo"};
    private ArrayList<String[]> rows=new ArrayList<>();
    UsuarioModelo usuarioModelo;
    EditText et_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_lista);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    private void init(){
        context=getApplicationContext();
        tableLayout= (TableLayout) findViewById(R.id.table);
        TableDynamic tableDynamic= new TableDynamic(tableLayout,getApplicationContext());
        tableDynamic.addHeader(header);
        tableDynamic.addData(obtenerUsuarios());
        tableDynamic.backgroundHeader(Color.DKGRAY);
        tableDynamic.backgroundData(Color.LTGRAY,Color.WHITE);
        tableDynamic.textColorData(Color.BLACK);
        tableDynamic.textColorHeader(Color.WHITE);
        tableDynamic.lineColor(Color.LTGRAY);

        et_id=findViewById(R.id.et_id);
    }

    private ArrayList<String[]> obtenerUsuarios(){
        usuarioModelo=new UsuarioModelo(context);
        List<UsuarioModelo> usuarios=usuarioModelo.listar();
        for (UsuarioModelo usuario:usuarios) {
            rows.add(new String[]{String.valueOf(usuario.getId()),String.valueOf(usuario.getCi()),usuario.getNombres() ,String.valueOf(usuario.getCelular()),usuario.getFecha(),usuario.getTipo()});
        }
        return rows;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_rasistencia:
                Intent intent = new Intent(context, FormularioUsuarioControlador.class);
                Bundle bolsa1 = new Bundle();
                bolsa1.putInt("id",0);
                intent.putExtras(bolsa1);
                startActivity(intent);
                break;
            case R.id.btn_masistencia:
                if (!et_id.getText().toString().isEmpty()){
                    int id= Integer.parseInt(et_id.getText().toString());
                    usuarioModelo=new UsuarioModelo(context);
                    usuarioModelo=usuarioModelo.obtenerByID(id);
                    if (usuarioModelo!=null){
                        Bundle bolsa=new Bundle();
                        bolsa.putInt("id",usuarioModelo.getId());
                        bolsa.putInt("ci",usuarioModelo.getCi());
                        bolsa.putString("nombres",usuarioModelo.getNombres());
                        bolsa.putInt("celular",usuarioModelo.getCelular());
                        bolsa.putString("fecha",usuarioModelo.getFecha());
                        bolsa.putString("tipo",usuarioModelo.getTipo());
                        Intent intent1 = new Intent(context, FormularioUsuarioControlador.class);
                        intent1.putExtras(bolsa);
                        startActivity(intent1);
                    }else{
                        Toast.makeText(this, "No existe ese id en usuarios", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "Digite Un ID", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_easistencia:
                if (!et_id.getText().toString().isEmpty()){
                    usuarioModelo=new UsuarioModelo(context);
                    usuarioModelo.eliminar(Integer.parseInt(et_id.getText().toString()));
                    Intent intent2 = new Intent(context, ListaUsuarioControlador.class);
                    startActivity(intent2);
                }else{
                    Toast.makeText(this, "Digite Un ID", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }
}