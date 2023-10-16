package com.example.iglesia_app.Modelos.Invitacion;

public class InvitacionDato {
    private int id;
    private String fecha;
    private int idUsuario;
    private int idUsuario2;

    public InvitacionDato(int id, String fecha, int idUsuario, int idUsuario2) {
        this.id = id;
        this.fecha = fecha;
        this.idUsuario = idUsuario;
        this.idUsuario2 = idUsuario2;
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
}
