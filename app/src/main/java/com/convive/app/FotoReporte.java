package com.convive.app;

/**
 * Clase modelo: representa UNA foto adjunta a un reporte de daño o PQRS.
 */
public class FotoReporte {

    private final int imagenResId;     // Referencia a la imagen, por ejemplo R.drawable.foto_grieta
    private final String titulo;
    private final String estado;
    private final String descripcion;

    public FotoReporte(int imagenResId, String titulo, String estado, String descripcion) {
        this.imagenResId = imagenResId;
        this.titulo = titulo;
        this.estado = estado;
        this.descripcion = descripcion;
    }

    public int getImagenResId() {
        return imagenResId;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getEstado() {
        return estado;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
