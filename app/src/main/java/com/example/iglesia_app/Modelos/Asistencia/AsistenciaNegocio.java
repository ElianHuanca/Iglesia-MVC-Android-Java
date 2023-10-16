package com.example.iglesia_app.Modelos.Asistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;


import com.example.iglesia_app.Modelos.Cargo.CargoDato;
import com.example.iglesia_app.conexionDB.conexionDB;

import java.util.ArrayList;
import java.util.List;

public class AsistenciaNegocio extends conexionDB {
    public AsistenciaNegocio(@Nullable Context context) {
        super(context);
    }

    public void agregar(AsistenciaDato asistenciaDato){
        SQLiteDatabase db= this.getWritableDatabase();
        if (db!=null) {
            db.insert("asistencias",null,putContentValues(asistenciaDato));
            db.close();
        }
    }

    public List<AsistenciaDato> listar(){
        List<AsistenciaDato> list = new ArrayList<>();
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM asistencias",null);
        if (cursor.moveToFirst()){
            do {
                list.add(extraerCursor(cursor));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public void editar(AsistenciaDato asistenciaDato) {
        SQLiteDatabase bd = getWritableDatabase();
        if (bd != null) {
            String whereClause = "id = ?";
            String[] whereArgs = {String.valueOf(asistenciaDato.getId())};
            bd.update("asistencias", putContentValues(asistenciaDato), whereClause, whereArgs);
            bd.close();
        }
    }

    public void eliminar(int id){
        SQLiteDatabase bd = getWritableDatabase();
        if (bd != null) {
            String whereClause = "id = ?";
            String[] whereArgs = {String.valueOf(id)};
            bd.delete("asistencias", whereClause, whereArgs);
            bd.close();
        }
    }

    public AsistenciaDato obtenerByID(int id){
        SQLiteDatabase db=getReadableDatabase();
        String sql = "SELECT * FROM asistencias WHERE id="+id;
        Cursor cursor=db.rawQuery(sql,null);
        try {
            if (cursor.moveToNext()){
                return extraerCursor(cursor);
            }else{
                return null;
            }
        }catch (Exception e){
            Log.d("TAG","Error elemento(asistencia) IglesiaDB"+e.getMessage());
            throw e;
        }finally {
            if (cursor!=null) {
                cursor.close();
                db.close();
            }
        }
    }

    private AsistenciaDato extraerCursor(Cursor cursor){
        AsistenciaDato asistenciaDato=new AsistenciaDato(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getInt(2),
                cursor.getInt(3)
        );
        return asistenciaDato;
    }


    private ContentValues putContentValues(AsistenciaDato asistenciaDato){
        ContentValues cv = new ContentValues();
        cv.put("hora", asistenciaDato.getHora());
        cv.put("idUsuario", asistenciaDato.getIdUsuario());
        cv.put("idActividad", asistenciaDato.getIdActividad());
        return cv;
    }
}
