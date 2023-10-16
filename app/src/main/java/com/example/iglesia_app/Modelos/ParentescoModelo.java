package com.example.iglesia_app.Modelos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;
import com.example.iglesia_app.conexionDB.conexionDB;

import java.util.ArrayList;
import java.util.List;

public class ParentescoModelo extends conexionDB {
    Context context;
    private int id;
    private String tipo;
    private int idUsuario;
    private int idUsuario2;

    public ParentescoModelo(@Nullable Context context,int id, String tipo, int idUsuario, int idUsuario2) {
        super(context);
        this.context=context;
        this.id = id;
        this.tipo = tipo;
        this.idUsuario = idUsuario;
        this.idUsuario2 = idUsuario2;
    }
    public ParentescoModelo(@Nullable Context context) {
        super(context);
        this.context=context;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdUsuario2() {
        return idUsuario2;
    }

    public void setIdUsuario2(int idUsuario2) {
        this.idUsuario2 = idUsuario2;
    }
    
    public void agregar(ParentescoModelo parentescoModelo){
        SQLiteDatabase db= this.getWritableDatabase();
        if (db!=null) {
            db.insert("parentescos",null,putContentValues(parentescoModelo));
            db.close();
        }
    }

    public List<ParentescoModelo> listar(){
        List<ParentescoModelo> list = new ArrayList<>();
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM parentescos",null);
        if (cursor.moveToFirst()){
            do {
                list.add(extraerCursor(cursor));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public void editar(ParentescoModelo parentescoModelo) {
        SQLiteDatabase bd = getWritableDatabase();
        if (bd != null) {
            String whereClause = "id = ?";
            String[] whereArgs = {String.valueOf(parentescoModelo.getId())};
            bd.update("parentescos", putContentValues(parentescoModelo), whereClause, whereArgs);
            bd.close();
        }
    }

    public void eliminar(int id){
        SQLiteDatabase bd = getWritableDatabase();
        if (bd != null) {
            String whereClause = "id = ?";
            String[] whereArgs = {String.valueOf(id)};
            bd.delete("parentescos", whereClause, whereArgs);
            bd.close();
        }
    }

    public ParentescoModelo obtenerByID(int id){
        SQLiteDatabase db=getReadableDatabase();
        ParentescoModelo parentescoModelo;
        String sql = "SELECT * FROM parentescos WHERE id="+id;
        Cursor cursor=db.rawQuery(sql,null);
        if (cursor.moveToNext()){
            parentescoModelo =extraerCursor(cursor);
        }else{
            parentescoModelo=null;
        }
        cursor.close();
        db.close();
        return parentescoModelo;
    }

    private ParentescoModelo extraerCursor(Cursor cursor){
        ParentescoModelo parentescoModelo=new ParentescoModelo(
                this.context,
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getInt(2),
                cursor.getInt(3)
        );
        return parentescoModelo;
    }

    private ContentValues putContentValues(ParentescoModelo parentescoModelo){
        ContentValues cv = new ContentValues();
        cv.put("tipo", parentescoModelo.getTipo());
        cv.put("idUsuario", parentescoModelo.getIdUsuario());
        cv.put("idUsuario2", parentescoModelo.getIdUsuario2());
        return cv;
    }
}
