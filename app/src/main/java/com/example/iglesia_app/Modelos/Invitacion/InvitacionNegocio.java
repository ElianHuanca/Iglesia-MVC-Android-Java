package com.example.iglesia_app.Modelos.Invitacion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.iglesia_app.Modelos.Invitacion.InvitacionDato;
import com.example.iglesia_app.conexionDB.conexionDB;

import java.util.ArrayList;
import java.util.List;

public class InvitacionNegocio extends conexionDB {

    public InvitacionNegocio(@Nullable Context context) {
        super(context);
    }

    public void agregar(InvitacionDato invitacionDato){
        SQLiteDatabase db= this.getWritableDatabase();
        if (db!=null) {
            db.insert("invitaciones",null,putContentValues(invitacionDato));
            db.close();
        }
    }

    public List<InvitacionDato> listar(){
        List<InvitacionDato> list = new ArrayList<>();
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

    public void editar(InvitacionDato invitacionDato) {
        SQLiteDatabase bd = getWritableDatabase();
        if (bd != null) {
            String whereClause = "id = ?";
            String[] whereArgs = {String.valueOf(invitacionDato.getId())};
            bd.update("invitaciones", putContentValues(invitacionDato), whereClause, whereArgs);
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

    public InvitacionDato obtenerByID(int id){
        SQLiteDatabase db=getReadableDatabase();
        InvitacionDato invitacionDato;
        String sql = "SELECT * FROM invitaciones WHERE id="+id;
        Cursor cursor=db.rawQuery(sql,null);
        if (cursor.moveToNext()){
            invitacionDato =extraerCursor(cursor);
        }else{
            invitacionDato=null;
        }
        cursor.close();
        db.close();
        return invitacionDato;
    }

    private InvitacionDato extraerCursor(Cursor cursor){
        InvitacionDato invitacionDato=new InvitacionDato(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getInt(2),
                cursor.getInt(3)
        );
        return invitacionDato;
    }

    private ContentValues putContentValues(InvitacionDato invitacionDato){
        ContentValues cv = new ContentValues();
        cv.put("fecha", invitacionDato.getFecha());
        cv.put("idUsuario", invitacionDato.getIdUsuario());
        cv.put("idUsuario2", invitacionDato.getIdUsuario2());
        return cv;
    }
}
