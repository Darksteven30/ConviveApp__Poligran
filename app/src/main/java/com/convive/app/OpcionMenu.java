package com.convive.app;

/**
 * Clase modelo: representa UNA opción del menú izquierdo (su nombre y su ícono).
 */
public class OpcionMenu {

    private final String titulo;
    private final int iconoResId;   // Referencia a un drawable, por ejemplo R.drawable.ic_perfil

    public OpcionMenu(String titulo, int iconoResId) {
        this.titulo = titulo;
        this.iconoResId = iconoResId;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getIconoResId() {
        return iconoResId;
    }
}
