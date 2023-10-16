package com.example.iglesia_app.Modelos.Actividad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.iglesia_app.conexionDB.conexionDB;

import java.util.ArrayList;
import java.util.List;

public class ActividadNegocio extends conexionDB {
    public ActividadNegocio(@Nullable Context context) {
        super(context);
    }

    public void agregar(ActividadDato actividadDato){
        SQLiteDatabase db= this.getWritableDatabase();
        if (db!=null) {
            db.insert("actividades",null,putContentValues(actividadDato));
            db.close();
        }
    }

    public List<ActividadDato> listar(){
        List<ActividadDato> list = new ArrayList<>();
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM actividades",null);
        if (cursor.moveToFirst()){
            do {
                list.add(extraerCursor(cursor));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public void editar(ActividadDato actividadDato) {
        SQLiteDatabase bd = getWritableDatabase();
        if (bd != null) {
            String whereClause = "id = ?";
            String[] whereArgs = {String.valueOf(actividadDato.getId())};
            bd.update("actividades", putContentValues2(actividadDato), whereClause, whereArgs);
            bd.close();
        }
    }

    public void editarMonto(ActividadDato actividadDato){
        SQLiteDatabase bd = getWritableDatabase();
        if (bd != null) {
            ContentValues cv = new ContentValues();
            cv.put("montoTotal",actividadDato.getMontoTotal());
            String whereClause = "id = ?";
            String[] whereArgs = {String.valueOf(actividadDato.getId())};
            bd.update("actividades", cv, whereClause, whereArgs);
            bd.close();
        }
    }
    public void eliminar(int id){
        SQLiteDatabase bd = getWritableDatabase();
        if (bd != null) {
            String whereClause = "id = ?";
            String[] whereArgs = {String.valueOf(id)};
            bd.delete("actividades", whereClause, whereArgs);
            bd.close();
        }
    }

    public ActividadDato obtenerByID(int id){
        SQLiteDatabase db=getReadableDatabase();
        ActividadDato actividadDato;
        String sql = "SELECT * FROM actividades WHERE id="+id;
        Cursor cursor=db.rawQuery(sql,null);
        if (cursor.moveToNext()){
            actividadDato =extraerCursor(cursor);
        }else{
            actividadDato=null;
        }
        cursor.close();
        db.close();
        return actividadDato;
    }

    private ActividadDato extraerCursor(Cursor cursor){
        ActividadDato actividadDato=new ActividadDato(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getDouble(5)
        );
        return actividadDato;
    }

    private ContentValues putContentValues(ActividadDato actividadDato){
        ContentValues cv = new ContentValues();
        cv.put("nombre", actividadDato.getNombre());
        cv.put("fecha", actividadDato.getFecha());
        cv.put("hora", actividadDato.getHora());
        cv.put("horaFin", actividadDato.getHoraFin());
        cv.put("montoTotal", actividadDato.getMontoTotal());
        return cv;
    }

    private ContentValues putContentValues2(ActividadDato actividadDato){
        ContentValues cv = new ContentValues();
        cv.put("nombre", actividadDato.getNombre());
        cv.put("fecha", actividadDato.getFecha());
        cv.put("hora", actividadDato.getHora());
        cv.put("horaFin", actividadDato.getHoraFin());
        //cv.put("montoTotal", actividadDato.getMontoTotal());
        return cv;
    }
}
