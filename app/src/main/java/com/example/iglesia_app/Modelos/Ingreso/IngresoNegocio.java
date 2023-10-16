package com.example.iglesia_app.Modelos.Ingreso;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;


import com.example.iglesia_app.conexionDB.conexionDB;

import java.util.ArrayList;
import java.util.List;

public class IngresoNegocio extends conexionDB {

    public IngresoNegocio(@Nullable Context context) {
        super(context);
    }

    public void agregar(IngresoDato ingresoDato){
        SQLiteDatabase db= this.getWritableDatabase();
        if (db!=null) {
            db.insert("ingresos",null,putContentValues(ingresoDato));
            db.close();
        }
    }

    public List<IngresoDato> listar(){
        List<IngresoDato> list = new ArrayList<>();
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

    public void editar(IngresoDato ingresoDato) {
        SQLiteDatabase bd = getWritableDatabase();
        if (bd != null) {
            String whereClause = "id = ?";
            String[] whereArgs = {String.valueOf(ingresoDato.getId())};
            bd.update("ingresos", putContentValues(ingresoDato), whereClause, whereArgs);
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

    public IngresoDato obtenerByID(int id){
        SQLiteDatabase db=getReadableDatabase();
        IngresoDato ingresoDato;
        String sql = "SELECT * FROM ingresos WHERE id="+id;
        Cursor cursor=db.rawQuery(sql,null);
        if (cursor.moveToNext()){
            ingresoDato =extraerCursor(cursor);
        }else{
            ingresoDato=null;
        }
        cursor.close();
        db.close();
        return ingresoDato;
    }

    private IngresoDato extraerCursor(Cursor cursor){
        IngresoDato ingresoDato=new IngresoDato(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getDouble(2),
                cursor.getInt(3)
        );
        return ingresoDato;
    }

    private ContentValues putContentValues(IngresoDato ingresoDato){
        ContentValues cv = new ContentValues();
        cv.put("nombre", ingresoDato.getNombre());
        cv.put("monto", ingresoDato.getMonto());
        cv.put("idActividad", ingresoDato.getIdActividad());
        return cv;
    }
}
