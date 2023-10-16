package com.example.iglesia_app.Controladores.Invitacion;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.iglesia_app.Modelos.InvitacionModelo;
import com.example.iglesia_app.Modelos.UsuarioModelo;
import com.example.iglesia_app.R;
import com.example.iglesia_app.TableDynamic;

import java.util.ArrayList;
import java.util.List;

public class ListaInvitacionControlador extends AppCompatActivity implements View.OnClickListener{
    Context context;
    EditText et_id;
    private TableLayout tableLayout;
    UsuarioModelo usuarioModelo;
    InvitacionModelo invitacionModelo;
    private ArrayList<String[]> rows=new ArrayList<>();
    String[] header={"ID","FECHA","INVITANTE","INVITADO"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitacion_lista);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }
    
    private void init(){
        context=getApplicationContext();
        et_id=findViewById(R.id.et_id);
        tableLayout= (TableLayout) findViewById(R.id.table);
        TableDynamic tableDynamic= new TableDynamic(tableLayout,getApplicationContext());
        tableDynamic.addHeader(header);
        tableDynamic.addData(obtenerInvitacions());
        tableDynamic.backgroundHeader(Color.DKGRAY);
        tableDynamic.backgroundData(Color.LTGRAY,Color.WHITE);
        tableDynamic.textColorData(Color.BLACK);
        tableDynamic.textColorHeader(Color.WHITE);
        tableDynamic.lineColor(Color.LTGRAY);
    }

    private ArrayList<String[]> obtenerInvitacions(){
        usuarioModelo=new UsuarioModelo(context);
        invitacionModelo = new InvitacionModelo(context);
        List<InvitacionModelo> invitacions=invitacionModelo.listar();
        for (InvitacionModelo invitacion:invitacions) {
            String nombre=usuarioModelo.obtenerByID(invitacion.getIdUsuario()).getNombres();
            String nombre2=usuarioModelo.obtenerByID(invitacion.getIdUsuario2()).getNombres();
            rows.add(new String[]{String.valueOf(invitacion.getId()),invitacion.getFecha(),nombre,nombre2});
        }
        return rows;
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_registrar:
                Intent intent=new Intent(context, FormularioInvitacionControlador.class);
                Bundle bolsa1= new Bundle();
                bolsa1.putInt("id",0);
                intent.putExtras(bolsa1);
                startActivity(intent);
                break;
            case R.id.btn_modificar:
                if (!et_id.getText().toString().isEmpty()){
                    int id= Integer.parseInt(et_id.getText().toString());
                    invitacionModelo=new InvitacionModelo(context);
                    invitacionModelo=invitacionModelo.obtenerByID(id);
                    if (invitacionModelo!=null){
                        Bundle bolsa=new Bundle();
                        bolsa.putInt("id",invitacionModelo.getId());
                        bolsa.putString("fecha",invitacionModelo.getFecha());
                        bolsa.putInt("idUsuario",invitacionModelo.getIdUsuario());
                        bolsa.putInt("idUsuario2",invitacionModelo.getIdUsuario2());
                        Intent intent1 = new Intent(context, FormularioInvitacionControlador.class);
                        intent1.putExtras(bolsa);
                        startActivity(intent1);
                    }else{
                        Toast.makeText(this, "No existe ese id en invitacions", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "Digite Un ID", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_eliminar:
                if (!et_id.getText().toString().isEmpty()){
                    invitacionModelo=new InvitacionModelo(context);
                    invitacionModelo.eliminar(Integer.parseInt(et_id.getText().toString()));
                    Intent intent2=new Intent(context, ListaInvitacionControlador.class);
                    startActivity(intent2);
                }else{
                    Toast.makeText(this, "Digite Un ID", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}