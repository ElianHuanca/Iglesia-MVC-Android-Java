package com.example.iglesia_app.Modelos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;
import com.example.iglesia_app.conexionDB.conexionDB;

import java.util.ArrayList;
import java.util.List;

public class ActividadModelo extends conexionDB {
    Context context;
    private int id;
    private String nombre;
    private String fecha;
    private String hora;
    private String horaFin;
    private Double montoTotal;

    public ActividadModelo(@Nullable Context context, int id, String nombre, String fecha, String hora, String horaFin, Double montoTotal) {
        super(context);
        this.context=context;
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.hora = hora;
        this.horaFin = horaFin;
        this.montoTotal = montoTotal;
    }
    public ActividadModelo(@Nullable Context context) {
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public void agregar(ActividadModelo actividadModelo){
        SQLiteDatabase db= this.getWritableDatabase();
        if (db!=null) {
            db.insert("actividades",null,putContentValues(actividadModelo));
            db.close();
        }
    }

    public List<ActividadModelo> listar(){
        List<ActividadModelo> list = new ArrayList<>();
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

    public void editar(ActividadModelo actividadModelo) {
        SQLiteDatabase bd = getWritableDatabase();
        if (bd != null) {
            String whereClause = "id = ?";
            String[] whereArgs = {String.valueOf(actividadModelo.getId())};
            bd.update("actividades", putContentValues2(actividadModelo), whereClause, whereArgs);
            bd.close();
        }
    }

    public void editarMonto(ActividadModelo actividadModelo){
        SQLiteDatabase bd = getWritableDatabase();
        if (bd != null) {
            ContentValues cv = new ContentValues();
            cv.put("montoTotal",actividadModelo.getMontoTotal());
            String whereClause = "id = ?";
            String[] whereArgs = {String.valueOf(actividadModelo.getId())};
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

    public ActividadModelo obtenerByID(int id){
        SQLiteDatabase db=getReadableDatabase();
        ActividadModelo actividadModelo;
        String sql = "SELECT * FROM actividades WHERE id="+id;
        Cursor cursor=db.rawQuery(sql,null);
        if (cursor.moveToNext()){
            actividadModelo =extraerCursor(cursor);
        }else{
            actividadModelo=null;
        }
        cursor.close();
        db.close();
        return actividadModelo;
    }

    private ActividadModelo extraerCursor(Cursor cursor){
        ActividadModelo actividadModelo=new ActividadModelo(
                this.context,
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getDouble(5)
        );
        return actividadModelo;
    }

    private ContentValues putContentValues(ActividadModelo actividadModelo){
        ContentValues cv = new ContentValues();
        cv.put("nombre", actividadModelo.getNombre());
        cv.put("fecha", actividadModelo.getFecha());
        cv.put("hora", actividadModelo.getHora());
        cv.put("horaFin", actividadModelo.getHoraFin());
        cv.put("montoTotal", actividadModelo.getMontoTotal());
        return cv;
    }

    private ContentValues putContentValues2(ActividadModelo actividadModelo){
        ContentValues cv = new ContentValues();
        cv.put("nombre", actividadModelo.getNombre());
        cv.put("fecha", actividadModelo.getFecha());
        cv.put("hora", actividadModelo.getHora());
        cv.put("horaFin", actividadModelo.getHoraFin());
        //cv.put("montoTotal", actividadModelo.getMontoTotal());
        return cv;
    }
}
