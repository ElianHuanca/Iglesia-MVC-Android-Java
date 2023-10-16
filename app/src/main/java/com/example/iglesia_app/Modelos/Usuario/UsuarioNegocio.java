package com.example.iglesia_app.Modelos.Usuario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.iglesia_app.Modelos.Asistencia.AsistenciaDato;
import com.example.iglesia_app.conexionDB.conexionDB;

import java.util.ArrayList;
import java.util.List;

public class UsuarioNegocio extends conexionDB {

    //Context context;
    public UsuarioNegocio(@Nullable Context context) {
        super(context);
        //this.context=context;
    }

    public void agregar(UsuarioDato usuarioDato){
        SQLiteDatabase db= this.getWritableDatabase();
        if (db!=null) {
            db.insert("usuarios",null,putContentValues(usuarioDato));
            db.close();
        }
    }

    public List<UsuarioDato> listar(){
        List<UsuarioDato> list = new ArrayList<>();
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM usuarios",null);
        if (cursor.moveToFirst()){
            do {
                list.add(extraerCursor(cursor));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public void editar(UsuarioDato usuarioDato) {
        SQLiteDatabase bd = getWritableDatabase();
        if (bd != null) {
            String[] whereArgs = {String.valueOf(usuarioDato.getId())};
            bd.update("usuarios", putContentValues(usuarioDato), "id=?", whereArgs);
            bd.close();
        }
    }

    public void eliminar(int id){
        SQLiteDatabase bd = getWritableDatabase();
        if (bd != null) {
            String whereClause = "id = ?";
            String[] whereArgs = {String.valueOf(id)};
            bd.delete("usuarios", whereClause, whereArgs);
            bd.close();
        }
    }

    public UsuarioDato obtenerByID(int id){
        SQLiteDatabase db=getReadableDatabase();
        String sql = "SELECT * FROM usuarios WHERE id="+id;
        Cursor cursor=db.rawQuery(sql,null);
        try {
            if (cursor.moveToNext()){
                return extraerCursor(cursor);
            }else{
                return null;
            }
        }catch (Exception e){
            Log.d("TAG","Error elemento(usuario) IglesiaDB"+e.getMessage());
            throw e;
        }finally {
            if (cursor!=null) {
                cursor.close();
                db.close();
            }
        }
    }

    public UsuarioDato obtenerByCI(int ci) {
        SQLiteDatabase db=getReadableDatabase();
        String sql = "SELECT * FROM usuarios WHERE ci='"+ci+"'";
        Cursor cursor=db.rawQuery(sql,null);
        try {
            if (cursor.moveToNext()){
                return extraerCursor(cursor);
            }else{
                return null;
            }
        }catch (Exception e){
            Log.d("TAG","Error elemento(usuario) IglesiaDB"+e.getMessage());
            throw e;
        }finally {
            if (cursor!=null) {
                cursor.close();
                db.close();
            }
        }
    }

    private UsuarioDato extraerCursor(Cursor cursor){
        UsuarioDato usuarioDato=new UsuarioDato(
                cursor.getInt(0),
                cursor.getInt(1),
                cursor.getString(2),
                cursor.getInt(3),
                cursor.getString(4),
                cursor.getString(5)
        );
        return usuarioDato;
    }

    private ContentValues putContentValues(UsuarioDato usuarioDato){
        ContentValues cv = new ContentValues();
        cv.put("ci", usuarioDato.getCi());
        cv.put("nombres", usuarioDato.getNombres());
        cv.put("celular", usuarioDato.getCelular());
        cv.put("fecha", usuarioDato.getFecha());
        cv.put("tipo", usuarioDato.getTipo());
        return cv;
    }
}
