package com.example.iglesia_app.Modelos.Ministerio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.iglesia_app.Modelos.Ministerio.MinisterioDato;
import com.example.iglesia_app.Modelos.Usuario.UsuarioDato;
import com.example.iglesia_app.conexionDB.conexionDB;

import java.util.ArrayList;
import java.util.List;

public class MinisterioNegocio extends conexionDB {

    public MinisterioNegocio(@Nullable Context context) {
        super(context);
    }

    public void agregar(MinisterioDato ministerioDato){
        SQLiteDatabase db= this.getWritableDatabase();
        if (db!=null) {
            db.insert("ministerios",null,putContentValues(ministerioDato));
            db.close();
        }
    }

    public List<MinisterioDato> listar(){
        List<MinisterioDato> list = new ArrayList<>();
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

    public void editar(MinisterioDato ministerioDato) {
        SQLiteDatabase bd = getWritableDatabase();
        if (bd != null) {
            String whereClause = "id = ?";
            String[] whereArgs = {String.valueOf(ministerioDato.getId())};
            bd.update("ministerios", putContentValues(ministerioDato), whereClause, whereArgs);
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

    public MinisterioDato obtenerByID(int id){
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

    public MinisterioDato obtenerByNombre(String nombre) {
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

    private MinisterioDato extraerCursor(Cursor cursor){
        MinisterioDato ministerioDato=new MinisterioDato(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2)
        );
        return ministerioDato;
    }


    private ContentValues putContentValues(MinisterioDato ministerioDato){
        ContentValues cv = new ContentValues();
        cv.put("nombre", ministerioDato.getNombre());
        cv.put("descripcion", ministerioDato.getDescripcion());
        return cv;
    }
}
