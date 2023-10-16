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

public class UsuarioModelo extends conexionDB {
    Context context;
    private int id;
    private int ci;
    private String nombres;
    private int celular;
    private String fecha;
    private String tipo;

    public UsuarioModelo(@Nullable Context context,int id, int ci, String nombres, int celular, String fecha, String tipo) {
        super(context);
        this.context=context;
        this.id = id;
        this.ci = ci;
        this.nombres = nombres;
        this.celular = celular;
        this.fecha = fecha;
        this.tipo = tipo;
    }

    public UsuarioModelo(@Nullable Context context) {
        super(context);
        this.context=context;
    }
    
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCi() {
        return ci;
    }

    public void setCi(int ci) {
        this.ci = ci;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public int getCelular() {
        return celular;
    }

    public void setCelular(int celular) {
        this.celular = celular;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void agregar(UsuarioModelo usuarioModelo){
        SQLiteDatabase db= this.getWritableDatabase();
        if (db!=null) {
            db.insert("usuarios",null,putContentValues(usuarioModelo));
            db.close();
        }
    }

    public List<UsuarioModelo> listar(){
        List<UsuarioModelo> list = new ArrayList<>();
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

    public void editar(UsuarioModelo usuarioModelo) {
        SQLiteDatabase bd = getWritableDatabase();
        if (bd != null) {
            String[] whereArgs = {String.valueOf(usuarioModelo.getId())};
            bd.update("usuarios", putContentValues(usuarioModelo), "id=?", whereArgs);
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

    public UsuarioModelo obtenerByID(int id){
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

    public UsuarioModelo obtenerByCI(int ci) {
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

    private UsuarioModelo extraerCursor(Cursor cursor){
        UsuarioModelo usuarioModelo=new UsuarioModelo(
                this.context,
                cursor.getInt(0),
                cursor.getInt(1),
                cursor.getString(2),
                cursor.getInt(3),
                cursor.getString(4),
                cursor.getString(5)
        );
        return usuarioModelo;
    }

    private ContentValues putContentValues(UsuarioModelo usuarioModelo){
        ContentValues cv = new ContentValues();
        cv.put("ci", usuarioModelo.getCi());
        cv.put("nombres", usuarioModelo.getNombres());
        cv.put("celular", usuarioModelo.getCelular());
        cv.put("fecha", usuarioModelo.getFecha());
        cv.put("tipo", usuarioModelo.getTipo());
        return cv;
    }
}
