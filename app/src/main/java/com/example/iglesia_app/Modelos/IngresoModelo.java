package com.example.iglesia_app.Modelos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.iglesia_app.conexionDB.conexionDB;

import java.util.ArrayList;
import java.util.List;

public class IngresoModelo extends conexionDB {
    Context context;
    private int id;
    private String nombre;
    private Double monto;
    private int idActividad;

    public IngresoModelo(@Nullable Context context, int id, String nombre, Double monto, int idActividad) {
        super(context);
        this.context=context;
        this.id = id;
        this.nombre = nombre;
        this.monto = monto;
        this.idActividad = idActividad;
    }
    public IngresoModelo(@Nullable Context context) {
        super(context);
        this.context=context;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public void agregar(IngresoModelo ingresoModelo){
        SQLiteDatabase db= this.getWritableDatabase();
        if (db!=null) {
            db.insert("ingresos",null,putContentValues(ingresoModelo));
            db.close();
        }
    }

    public List<IngresoModelo> listar(){
        List<IngresoModelo> list = new ArrayList<>();
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM ingresos",null);
        if (cursor.moveToFirst()){
            do {
                list.add(extraerCursor(cursor));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public void editar(IngresoModelo ingresoModelo) {
        SQLiteDatabase bd = getWritableDatabase();
        if (bd != null) {
            String whereClause = "id = ?";
            String[] whereArgs = {String.valueOf(ingresoModelo.getId())};
            bd.update("ingresos", putContentValues(ingresoModelo), whereClause, whereArgs);
            bd.close();
        }
    }

    public void eliminar(int id){
        SQLiteDatabase bd = getWritableDatabase();
        if (bd != null) {
            String whereClause = "id = ?";
            String[] whereArgs = {String.valueOf(id)};
            bd.delete("ingresos", whereClause, whereArgs);
            bd.close();
        }
    }

    public IngresoModelo obtenerByID(int id){
        SQLiteDatabase db=getReadableDatabase();
        IngresoModelo ingresoModelo;
        String sql = "SELECT * FROM ingresos WHERE id="+id;
        Cursor cursor=db.rawQuery(sql,null);
        if (cursor.moveToNext()){
            ingresoModelo =extraerCursor(cursor);
        }else{
            ingresoModelo=null;
        }
        cursor.close();
        db.close();
        return ingresoModelo;
    }

    private IngresoModelo extraerCursor(Cursor cursor){
        IngresoModelo ingresoModelo=new IngresoModelo(
                this.context,
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getDouble(2),
                cursor.getInt(3)
        );
        return ingresoModelo;
    }

    private ContentValues putContentValues(IngresoModelo ingresoModelo){
        ContentValues cv = new ContentValues();
        cv.put("nombre", ingresoModelo.getNombre());
        cv.put("monto", ingresoModelo.getMonto());
        cv.put("idActividad", ingresoModelo.getIdActividad());
        return cv;
    }
}
