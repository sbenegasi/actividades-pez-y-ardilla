package com.pezardilla.actividades.model;

public class Actividad {
    private String id;
    private String titulo;
    private String descripcion;
    private String fecha;       // Solo para puntuales
    private String hora;
    private String diaSemana;
    private String inicio;      // Solo para regulares
    private String frecuencia;  // Solo para regulares
    private String tipo;        // "puntual" o "regular"

    public Actividad() {
        // Firestore necesita constructor vacío
    }

    public Actividad(String id, String titulo, String descripcion, String fecha,
                     String hora, String diaSemana, String inicio, String frecuencia, String tipo) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.hora = hora;
        this.diaSemana = diaSemana;
        this.inicio = inicio;
        this.frecuencia = frecuencia;
        this.tipo = tipo;
    }

    // Getters y Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
