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

public class MinisterioModelo extends conexionDB {
    Context context;
    private int id;
    private String nombre;
    private String descripcion;
    
    public MinisterioModelo(@Nullable Context context, int id, String nombre, String descripcion) {
        super(context);
        this.context=context;
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public MinisterioModelo(@Nullable Context context) {
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void agregar(MinisterioModelo ministerioModelo){
        SQLiteDatabase db= this.getWritableDatabase();
        if (db!=null) {
            db.insert("ministerios",null,putContentValues(ministerioModelo));
            db.close();
        }
    }

    public List<MinisterioModelo> listar(){
        List<MinisterioModelo> list = new ArrayList<>();
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM ministerios",null);
        if (cursor.moveToFirst()){
            do {
                list.add(extraerCursor(cursor));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public void editar(MinisterioModelo ministerioModelo) {
        SQLiteDatabase bd = getWritableDatabase();
        if (bd != null) {
            String whereClause = "id = ?";
            String[] whereArgs = {String.valueOf(ministerioModelo.getId())};
            bd.update("ministerios", putContentValues(ministerioModelo), whereClause, whereArgs);
            bd.close();
        }
    }

    public void eliminar(int id){
        SQLiteDatabase bd = getWritableDatabase();
        if (bd != null) {
            String whereClause = "id = ?";
            String[] whereArgs = {String.valueOf(id)};
            bd.delete("ministerios", whereClause, whereArgs);
            bd.close();
        }
    }

    public MinisterioModelo obtenerByID(int id){
        SQLiteDatabase db=getReadableDatabase();
        String sql = "SELECT * FROM ministerios WHERE id="+id;
        Cursor cursor=db.rawQuery(sql,null);
        try {
            if (cursor.moveToNext()){
                return extraerCursor(cursor);
            }else{
                return null;
            }
        }catch (Exception e){
            Log.d("TAG","Error elemento(ministerio) IglesiaDB"+e.getMessage());
            throw e;
        }finally {
            if (cursor!=null) {
                cursor.close();
                db.close();
            }
        }
    }

    public MinisterioModelo obtenerByNombre(String nombre) {
        SQLiteDatabase db=getReadableDatabase();
        String sql = "SELECT * FROM ministerios WHERE nombre='"+nombre+"'";
        Cursor cursor=db.rawQuery(sql,null);
        try {
            if (cursor.moveToNext()){
                return extraerCursor(cursor);
            }else{
                return null;
            }
        }catch (Exception e){
            Log.d("TAG","Error elemento(Ministerio) IglesiaDB"+e.getMessage());
            throw e;
        }finally {
            if (cursor!=null) {
                cursor.close();
                db.close();
            }
        }
    }

    private MinisterioModelo extraerCursor(Cursor cursor){
        MinisterioModelo ministerioModelo=new MinisterioModelo(
                context,
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2)
        );
        return ministerioModelo;
    }


    private ContentValues putContentValues(MinisterioModelo ministerioModelo){
        ContentValues cv = new ContentValues();
        cv.put("nombre", ministerioModelo.getNombre());
        cv.put("descripcion", ministerioModelo.getDescripcion());
        return cv;
    }
}
