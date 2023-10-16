package com.example.iglesia_app.Modelos.Usuario;

public class UsuarioDato {
    private int id;
    private int ci;
    private String nombres;
    private int celular;
    private String fecha;
    private String tipo;

    public UsuarioDato(int id, int ci, String nombres, int celular, String fecha, String tipo) {
        this.id = id;
        this.ci = ci;
        this.nombres = nombres;
        this.celular = celular;
        this.fecha = fecha;
        this.tipo = tipo;
    }

    public UsuarioDato(){
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCi() {
        return ci;
    }

    public void setCi(int ci) {
        this.ci = ci;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public int getCelular() {
        return celular;
    }

    public void setCelular(int celular) {
        this.celular = celular;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "UsuarioModelo{" +
                "id=" + id +
                ", ci=" + ci +
                ", nombres='" + nombres + '\'' +
                ", celular=" + celular +
                ", fecha='" + fecha + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
