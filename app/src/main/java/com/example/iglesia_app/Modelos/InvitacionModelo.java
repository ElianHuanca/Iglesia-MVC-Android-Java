package com.example.iglesia_app.Modelos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;
import com.example.iglesia_app.conexionDB.conexionDB;

import java.util.ArrayList;
import java.util.List;

public class InvitacionModelo extends conexionDB {
    Context context;
    private int id;
    private String fecha;
    private int idUsuario;
    private int idUsuario2;

    public InvitacionModelo(@Nullable Context context, int id, String fecha, int idUsuario, int idUsuario2) {
        super(context);
        this.context=context;
        this.id = id;
        this.fecha = fecha;
        this.idUsuario = idUsuario;
        this.idUsuario2 = idUsuario2;
    }
    public InvitacionModelo(@Nullable Context context) {
        super(context);
        this.context=context;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
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

    public void agregar(InvitacionModelo invitacionModelo){
        SQLiteDatabase db= this.getWritableDatabase();
        if (db!=null) {
            db.insert("invitaciones",null,putContentValues(invitacionModelo));
            db.close();
        }
    }

    public List<InvitacionModelo> listar(){
        List<InvitacionModelo> list = new ArrayList<>();
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM invitaciones",null);
        if (cursor.moveToFirst()){
            do {
                list.add(extraerCursor(cursor));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public void editar(InvitacionModelo invitacionModelo) {
        SQLiteDatabase bd = getWritableDatabase();
        if (bd != null) {
            String whereClause = "id = ?";
            String[] whereArgs = {String.valueOf(invitacionModelo.getId())};
            bd.update("invitaciones", putContentValues(invitacionModelo), whereClause, whereArgs);
            bd.close();
        }
    }

    public void eliminar(int id){
        SQLiteDatabase bd = getWritableDatabase();
        if (bd != null) {
            String whereClause = "id = ?";
            String[] whereArgs = {String.valueOf(id)};
            bd.delete("invitaciones", whereClause, whereArgs);
            bd.close();
        }
    }

    public InvitacionModelo obtenerByID(int id){
        SQLiteDatabase db=getReadableDatabase();
        InvitacionModelo invitacionModelo;
        String sql = "SELECT * FROM invitaciones WHERE id="+id;
        Cursor cursor=db.rawQuery(sql,null);
        if (cursor.moveToNext()){
            invitacionModelo =extraerCursor(cursor);
        }else{
            invitacionModelo=null;
        }
        cursor.close();
        db.close();
        return invitacionModelo;
    }

    private InvitacionModelo extraerCursor(Cursor cursor){
        InvitacionModelo invitacionModelo=new InvitacionModelo(
                this.context,
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getInt(2),
                cursor.getInt(3)
        );
        return invitacionModelo;
    }

    private ContentValues putContentValues(InvitacionModelo invitacionModelo){
        ContentValues cv = new ContentValues();
        cv.put("fecha", invitacionModelo.getFecha());
        cv.put("idUsuario", invitacionModelo.getIdUsuario());
        cv.put("idUsuario2", invitacionModelo.getIdUsuario2());
        return cv;
    }
}
