package com.example.iglesia_app.Modelos.Cargo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.iglesia_app.conexionDB.conexionDB;

import java.util.ArrayList;
import java.util.List;

public class CargoNegocio extends conexionDB {

    public CargoNegocio(@Nullable Context context) {
        super(context);
    }

    public void agregar(CargoDato cargoDato){
        SQLiteDatabase db= this.getWritableDatabase();
        if (db!=null) {
            db.insert("cargos",null,putContentValues(cargoDato));
            db.close();
        }
    }

    public List<CargoDato> listar(){
        List<CargoDato> list = new ArrayList<>();
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM cargos",null);
        if (cursor.moveToFirst()){
            do {
                list.add(extraerCursor(cursor));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public void editar(CargoDato cargoDato) {
        SQLiteDatabase bd = getWritableDatabase();
        if (bd != null) {
            String whereClause = "id = ?";
            String[] whereArgs = {String.valueOf(cargoDato.getId())};
            bd.update("cargos", putContentValues(cargoDato), whereClause, whereArgs);
            bd.close();
        }
    }

    public void eliminar(int id){
        SQLiteDatabase bd = getWritableDatabase();
        if (bd != null) {
            String whereClause = "id = ?";
            String[] whereArgs = {String.valueOf(id)};
            bd.delete("cargos", whereClause, whereArgs);
            bd.close();
        }
    }

    public CargoDato obtenerByID(int id){
        SQLiteDatabase db=getReadableDatabase();
        CargoDato cargoDato;
        String sql = "SELECT * FROM cargos WHERE id="+id;
        Cursor cursor=db.rawQuery(sql,null);
        if (cursor.moveToNext()){
            cargoDato =extraerCursor(cursor);
        }else{
            cargoDato=null;
        }
        cursor.close();
        db.close();
        return cargoDato;
    }

    private CargoDato extraerCursor(Cursor cursor){
        CargoDato cargoDato=new CargoDato(
                cursor.getInt(0),
                cursor.getInt(1) == 1 ? true : false,
                cursor.getString(2),
                cursor.getString(3),
                cursor.getInt(4),
                cursor.getInt(5)
        );
        return cargoDato;
    }

    private ContentValues putContentValues(CargoDato cargoDato){
        ContentValues cv = new ContentValues();
        cv.put("estado", cargoDato.isEstado());
        cv.put("fecha", cargoDato.getFecha());
        cv.put("fechaFin", cargoDato.getFechaFin());
        cv.put("idMinisterio", cargoDato.getIdMinisterio());
        cv.put("idUsuario", cargoDato.getIdUsuario());
        return cv;
    }
}
