package com.example.iglesia_app.Modelos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.iglesia_app.conexionDB.conexionDB;

import java.util.ArrayList;
import java.util.List;

public class CargoModelo extends conexionDB {
    Context context;
    private int id;
    private boolean estado;
    private String fecha;
    private String fechaFin;
    private int idMinisterio;
    private int idUsuario;

    public CargoModelo(@Nullable Context context,int id, boolean estado, String fecha, String fechaFin, int idMinisterio, int idUsuario) {
        super(context);
        this.context=context;
        this.id = id;
        this.estado = estado;
        this.fecha = fecha;
        this.fechaFin = fechaFin;
        this.idMinisterio = idMinisterio;
        this.idUsuario = idUsuario;
    }

    public CargoModelo(@Nullable Context context) {
        super(context);
        this.context=context;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getIdMinisterio() {
        return idMinisterio;
    }

    public void setIdMinisterio(int idMinisterio) {
        this.idMinisterio = idMinisterio;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void agregar(CargoModelo cargoModelo){
        SQLiteDatabase db= this.getWritableDatabase();
        if (db!=null) {
            db.insert("cargos",null,putContentValues(cargoModelo));
            db.close();
        }
    }

    public List<CargoModelo> listar(){
        List<CargoModelo> list = new ArrayList<>();
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM cargos",null);
        if (cursor.moveToFirst()){
            do {
                list.add(extraerCursor(cursor));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public void editar(CargoModelo cargoModelo) {
        SQLiteDatabase bd = getWritableDatabase();
        if (bd != null) {
            String whereClause = "id = ?";
            String[] whereArgs = {String.valueOf(cargoModelo.getId())};
            bd.update("cargos", putContentValues(cargoModelo), whereClause, whereArgs);
            bd.close();
        }
    }

    public void eliminar(int id){
        SQLiteDatabase bd = getWritableDatabase();
        if (bd != null) {
            String whereClause = "id = ?";
            String[] whereArgs = {String.valueOf(id)};
            bd.delete("cargos", whereClause, whereArgs);
            bd.close();
        }
    }

    public CargoModelo obtenerByID(int id){
        SQLiteDatabase db=getReadableDatabase();
        CargoModelo cargoModelo;
        String sql = "SELECT * FROM cargos WHERE id="+id;
        Cursor cursor=db.rawQuery(sql,null);
        if (cursor.moveToNext()){
            cargoModelo =extraerCursor(cursor);
        }else{
            cargoModelo=null;
        }
        cursor.close();
        db.close();
        return cargoModelo;
    }

    private CargoModelo extraerCursor(Cursor cursor){
        CargoModelo cargoModelo=new CargoModelo(
                this.context,
                cursor.getInt(0),
                cursor.getInt(1) == 1 ? true : false,
                cursor.getString(2),
                cursor.getString(3),
                cursor.getInt(4),
                cursor.getInt(5)
        );
        return cargoModelo;
    }

    private ContentValues putContentValues(CargoModelo cargoModelo){
        ContentValues cv = new ContentValues();
        cv.put("estado", cargoModelo.isEstado());
        cv.put("fecha", cargoModelo.getFecha());
        cv.put("fechaFin", cargoModelo.getFechaFin());
        cv.put("idMinisterio", cargoModelo.getIdMinisterio());
        cv.put("idUsuario", cargoModelo.getIdUsuario());
        return cv;
    }
}
