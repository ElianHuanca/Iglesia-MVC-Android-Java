package com.example.iglesia_app.Modelos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.iglesia_app.conexionDB.conexionDB;

import java.util.ArrayList;
import java.util.List;

public class AsistenciaModelo extends conexionDB {
    Context context;
    private int id;
    private String hora;
    private int idUsuario;
    private int idActividad;

    public AsistenciaModelo(@Nullable Context context,int id, String hora, int idUsuario, int idActividad) {
        super(context);
        this.context=context;
        this.id = id;
        this.hora = hora;
        this.idUsuario = idUsuario;
        this.idActividad=idActividad;
    }

    public AsistenciaModelo(@Nullable Context context) {
        super(context);
        this.context=context;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }


    public void agregar(AsistenciaModelo asistenciaModelo){
        SQLiteDatabase db= this.getWritableDatabase();
        if (db!=null) {
            db.insert("asistencias",null,putContentValues(asistenciaModelo));
            db.close();
        }
    }

    public List<AsistenciaModelo> listar(){
        List<AsistenciaModelo> list = new ArrayList<>();
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

    public void editar(AsistenciaModelo asistenciaModelo) {
        SQLiteDatabase bd = getWritableDatabase();
        if (bd != null) {
            String whereClause = "id = ?";
            String[] whereArgs = {String.valueOf(asistenciaModelo.getId())};
            bd.update("asistencias", putContentValues(asistenciaModelo), whereClause, whereArgs);
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

    public AsistenciaModelo obtenerByID(int id){
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

    private AsistenciaModelo extraerCursor(Cursor cursor){
        AsistenciaModelo asistenciaModelo=new AsistenciaModelo(
                this.context,
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getInt(2),
                cursor.getInt(3)
        );
        return asistenciaModelo;
    }


    private ContentValues putContentValues(AsistenciaModelo asistenciaModelo){
        ContentValues cv = new ContentValues();
        cv.put("hora", asistenciaModelo.getHora());
        cv.put("idUsuario", asistenciaModelo.getIdUsuario());
        cv.put("idActividad", asistenciaModelo.getIdActividad());
        return cv;
    }
    
}
