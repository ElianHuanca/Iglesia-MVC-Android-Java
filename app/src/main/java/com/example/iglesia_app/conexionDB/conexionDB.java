package com.example.iglesia_app.conexionDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class conexionDB extends SQLiteOpenHelper {

    private String TABLA_USUARIOS="CREATE TABLE usuarios("+
            "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
            "ci INTEGER UNIQUE, "+
            "nombres TEXT, "+
            "celular INTEGER, "+
            "fecha DATE, " +
            "tipo TEXT"+
            ");";

    private String TABLA_ACTIVIDADES="CREATE TABLE actividades("+
            "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
            "nombre TEXT, " +
            "fecha DATE, "+
            "hora TIME, "+
            "horaFin TIME, "+
            "montoTotal DOUBLE "+
            ");";

    private String TABLA_INGRESOS= "CREATE TABLE ingresos(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
            "nombre TEXT, "+
            "monto DOUBLE, "+
            "idActividad INTEGER, "+
            "FOREIGN KEY(idActividad) REFERENCES actividades(id) "+
            ");";

    private String TABLA_ASISTENCIAS="CREATE TABLE asistencias(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
            "hora TIME, "+
            "idUsuario INTEGER, "+
            "idActividad INTEGER, "+
            "FOREIGN KEY(idUsuario) REFERENCES usuarios(id), "+
            "FOREIGN KEY(idActividad) REFERENCES actividades(id)"+
            ");";

    private String TABLA_MINISTERIOS= "CREATE TABLE ministerios(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
            "nombre TEXT, "+
            "descripcion TEXT"+
            ");";

    private String TABLA_CARGOS="CREATE TABLE cargos(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "estado BOOLEAN, " +
            "fecha DATE, " +
            "fechaFin DATE, " +
            "idMinisterio INTEGER, "+
            "idUsuario INTEGER, " +
            "FOREIGN KEY(idUsuario) REFERENCES usuarios(id), " +
            "FOREIGN KEY(idMinisterio) REFERENCES ministerios(id)" +
            ");";

    private String TABLA_INVITACION="CREATE TABLE invitaciones(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "fecha DATE, " +
            "idUsuario INTEGER, "+
            "idUsuario2 INTEGER, "+
            "FOREIGN KEY(idUsuario) REFERENCES usuarios(id),"+
            "FOREIGN KEY(idUsuario2) REFERENCES usuarios(id)" +
            ");";

    private String TABLA_PARENTESCO="CREATE TABLE parentescos(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "tipo TEXT, " +
            "idUsuario INTEGER, " +
            "idUsuario2 INTEGER, " +
            "FOREIGN KEY(idUsuario) REFERENCES usuarios(id),"+
            "FOREIGN KEY(idUsuario2) REFERENCES usuarios(id)" +
            ");";

    public conexionDB(@Nullable Context context) {
        super(context, "iglesia.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLA_USUARIOS);
        db.execSQL(TABLA_ASISTENCIAS);
        db.execSQL(TABLA_MINISTERIOS);
        db.execSQL(TABLA_CARGOS);
        db.execSQL(TABLA_INVITACION);
        db.execSQL(TABLA_PARENTESCO);
        db.execSQL(TABLA_ACTIVIDADES);
        db.execSQL(TABLA_INGRESOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLA_USUARIOS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLA_ASISTENCIAS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLA_MINISTERIOS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLA_CARGOS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLA_INVITACION);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLA_PARENTESCO);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLA_ACTIVIDADES);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLA_INGRESOS);
        onCreate(sqLiteDatabase);
    }
}
