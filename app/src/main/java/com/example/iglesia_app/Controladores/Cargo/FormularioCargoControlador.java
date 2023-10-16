package com.example.iglesia_app.Controladores.Cargo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.iglesia_app.Modelos.CargoModelo;
import com.example.iglesia_app.Modelos.MinisterioModelo;
import com.example.iglesia_app.Modelos.UsuarioModelo;
import com.example.iglesia_app.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FormularioCargoControlador extends AppCompatActivity {
    Context context;
    Button btn_guardar;
    EditText et_fecha,et_fecha2,et_ci;
    private int id;
    Switch switch1;

    Spinner spinner;
    ArrayAdapter adapter;
    CargoModelo cargoModelo;
    MinisterioModelo ministerioModelo;
    UsuarioModelo usuarioModelo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargo_formulario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    ministerioModelo=new MinisterioModelo(context);
                    usuarioModelo=new UsuarioModelo(context);
                    int idMinisterio=ministerioModelo.obtenerByNombre(spinner.getSelectedItem().toString()).getId();
                    usuarioModelo= usuarioModelo.obtenerByCI(Integer.parseInt(et_ci.getText().toString()));
                    if (usuarioModelo!=null){
                        int idUsuario= usuarioModelo.getId();
                         cargoModelo = new CargoModelo(
                                context,
                                id,
                                switch1.isChecked(),
                                et_fecha.getText().toString(),
                                et_fecha2.getText().toString(),
                                idMinisterio,
                                idUsuario
                        );

                        if (id == 0){
                            Calendar calendar = Calendar.getInstance();
                            Date fechaHoraActual = calendar.getTime();
                            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                            String fecha=formato.format(fechaHoraActual);
                            cargoModelo.setFecha(fecha);
                            cargoModelo.agregar(cargoModelo);
                        }else{
                            cargoModelo.editar(cargoModelo);
                        }
                        Intent intent = new Intent(context, ListaCargoControlador.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(context, "No existe el usuario con ese CI", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e) {
                    Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void init(){
        context=getApplicationContext();
        et_fecha=findViewById(R.id.et_fecha);
        et_fecha2=findViewById(R.id.et_fecha2);
        et_ci=findViewById(R.id.et_ci);
        switch1 = findViewById(R.id.switch1);
        btn_guardar=findViewById(R.id.btn_guardar);

        ministerioModelo=new MinisterioModelo(context);
        usuarioModelo=new UsuarioModelo(context);
        List<MinisterioModelo> ministerios = ministerioModelo.listar();
        String[] lista = new String[ministerios.size()];
        int j=0;
        for (MinisterioModelo ministerio:ministerios) {
            lista[j]=ministerio.getNombre();
            j++;
        }

        spinner = findViewById(R.id.spinner);
        adapter= new ArrayAdapter(context, android.R.layout.simple_spinner_item,lista);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Intent intent = getIntent();
        Bundle bolsa= intent.getExtras();
        id=bolsa.getInt("id");


        if (id!=0){
            et_fecha.setText(bolsa.getString("fecha"));
            et_fecha2.setText(bolsa.getString("fechaFin"));
            int idUsuario=bolsa.getInt("idUsuario");
            int ci=usuarioModelo.obtenerByID(idUsuario).getCi();
            et_ci.setText(ci+"");
            int idMinisterio=bolsa.getInt("idMinisterio");
            String nombre=ministerioModelo.obtenerByID(idMinisterio).getNombre();
            for (int i = 0; i < lista.length; i++) {
                if (lista[i].equals(nombre)) {
                    spinner.setSelection(i);
                }
            }
            switch1.setChecked(bolsa.getBoolean("estado"));
        }else{
            et_fecha.setEnabled(false);
            et_fecha2.setEnabled(false);
        }
    }
}