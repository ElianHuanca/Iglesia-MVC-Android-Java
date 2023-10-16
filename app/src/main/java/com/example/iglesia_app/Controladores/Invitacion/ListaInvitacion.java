package com.example.iglesia_app.Controladores.Invitacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.iglesia_app.Controladores.Invitacion.FormularioInvitacion;
import com.example.iglesia_app.Controladores.Invitacion.ListaInvitacion;
import com.example.iglesia_app.Modelos.Invitacion.InvitacionDato;
import com.example.iglesia_app.Modelos.Invitacion.InvitacionNegocio;
import com.example.iglesia_app.Modelos.Usuario.UsuarioNegocio;
import com.example.iglesia_app.R;
import com.example.iglesia_app.TableDynamic;

import java.util.ArrayList;
import java.util.List;

public class ListaInvitacion extends AppCompatActivity implements View.OnClickListener{
    Context context;
    EditText et_id;
    private TableLayout tableLayout;
    UsuarioNegocio usuarioNegocio;
    InvitacionNegocio invitacionNegocio;
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
        usuarioNegocio=new UsuarioNegocio(context);
        invitacionNegocio = new InvitacionNegocio(context);
        List<InvitacionDato> invitacions=invitacionNegocio.listar();
        for (InvitacionDato invitacion:invitacions) {
            String nombre=usuarioNegocio.obtenerByID(invitacion.getIdUsuario()).getNombres();
            String nombre2=usuarioNegocio.obtenerByID(invitacion.getIdUsuario2()).getNombres();
            rows.add(new String[]{String.valueOf(invitacion.getId()),invitacion.getFecha(),nombre,nombre2});
        }
        return rows;
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_registrar:
                //Toast.makeText(context, "Boton Registrar", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context, FormularioInvitacion.class);
                Bundle bolsa1= new Bundle();
                bolsa1.putInt("id",0);
                intent.putExtras(bolsa1);
                startActivity(intent);
                break;
            case R.id.btn_modificar:
                if (!et_id.getText().toString().isEmpty()){
                    int id= Integer.parseInt(et_id.getText().toString());
                    invitacionNegocio=new InvitacionNegocio(context);
                    InvitacionDato invitacionDato=invitacionNegocio.obtenerByID(id);
                    if (invitacionDato!=null){
                        Bundle bolsa=new Bundle();
                        bolsa.putInt("id",invitacionDato.getId());
                        bolsa.putString("fecha",invitacionDato.getFecha());
                        bolsa.putInt("idUsuario",invitacionDato.getIdUsuario());
                        bolsa.putInt("idUsuario2",invitacionDato.getIdUsuario2());
                        Intent intent1 = new Intent(context, FormularioInvitacion.class);
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
                    invitacionNegocio=new InvitacionNegocio(context);
                    invitacionNegocio.eliminar(Integer.parseInt(et_id.getText().toString()));
                    Intent intent2=new Intent(context, ListaInvitacion.class);
                    startActivity(intent2);
                }else{
                    Toast.makeText(this, "Digite Un ID", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}