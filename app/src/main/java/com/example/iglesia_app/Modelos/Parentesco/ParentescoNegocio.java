package com.example.iglesia_app.Modelos.Parentesco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;


import com.example.iglesia_app.conexionDB.conexionDB;

import java.util.ArrayList;
import java.util.List;

public class ParentescoNegocio extends conexionDB {
    public ParentescoNegocio(@Nullable Context context) {
        super(context);
    }

    public void agregar(ParentescoDato parentescoDato){
        SQLiteDatabase db= this.getWritableDatabase();
        if (db!=null) {
            db.insert("parentescos",null,putContentValues(parentescoDato));
            db.close();
        }
    }

    public List<ParentescoDato> listar(){
        List<ParentescoDato> list = new ArrayList<>();
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

    public void editar(ParentescoDato parentescoDato) {
        SQLiteDatabase bd = getWritableDatabase();
        if (bd != null) {
            String whereClause = "id = ?";
            String[] whereArgs = {String.valueOf(parentescoDato.getId())};
            bd.update("parentescos", putContentValues(parentescoDato), whereClause, whereArgs);
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

    public ParentescoDato obtenerByID(int id){
        SQLiteDatabase db=getReadableDatabase();
        ParentescoDato parentescoDato;
        String sql = "SELECT * FROM parentescos WHERE id="+id;
        Cursor cursor=db.rawQuery(sql,null);
        if (cursor.moveToNext()){
            parentescoDato =extraerCursor(cursor);
        }else{
            parentescoDato=null;
        }
        cursor.close();
        db.close();
        return parentescoDato;
    }

    private ParentescoDato extraerCursor(Cursor cursor){
        ParentescoDato parentescoDato=new ParentescoDato(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getInt(2),
                cursor.getInt(3)
        );
        return parentescoDato;
    }

    private ContentValues putContentValues(ParentescoDato parentescoDato){
        ContentValues cv = new ContentValues();
        cv.put("tipo", parentescoDato.getTipo());
        cv.put("idUsuario", parentescoDato.getIdUsuario());
        cv.put("idUsuario2", parentescoDato.getIdUsuario2());
        return cv;
    }
}
