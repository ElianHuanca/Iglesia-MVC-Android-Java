package com.example.iglesia_app.Modelos.Parentesco;

public class ParentescoDato {
    private int id;
    private String tipo;
    private int idUsuario;
    private int idUsuario2;

    public ParentescoDato(int id, String tipo, int idUsuario, int idUsuario2) {
        this.id = id;
        this.tipo = tipo;
        this.idUsuario = idUsuario;
        this.idUsuario2 = idUsuario2;
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
}
