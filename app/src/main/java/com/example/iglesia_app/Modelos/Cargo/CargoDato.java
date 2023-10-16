package com.example.iglesia_app.Modelos.Cargo;

public class CargoDato {
    private int id;
    private boolean estado;
    private String fecha;
    private String fechaFin;
    private int idMinisterio;
    private int idUsuario;

    public CargoDato(int id, boolean estado, String fecha, String fechaFin, int idMinisterio, int idUsuario) {
        this.id = id;
        this.estado = estado;
        this.fecha = fecha;
        this.fechaFin = fechaFin;
        this.idMinisterio = idMinisterio;
        this.idUsuario = idUsuario;
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
}
