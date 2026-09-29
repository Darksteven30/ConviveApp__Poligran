package com.convive.app;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * ACTIVIDAD PRINCIPAL de Convive.
 * Es la "pantalla" que contiene a los dos fragmentos:
 *  - Izquierdo: MenuFragment (menú de opciones).
 *  - Derecho: PerfilFragment, FotosFragment, VideoFragment, WebFragment o BotonesFragment.
 *
 * Implementa la interfaz OnOpcionSeleccionadaListener para que el menú le avise
 * cuál opción tocó el usuario (así se comunican los dos fragmentos).
 */
public class MainActivity extends AppCompatActivity
        implements MenuFragment.OnOpcionSeleccionadaListener {

    // ===== 1. DECLARACIÓN DE VARIABLES (una por cada vista que se usa en código) =====
    private TextView tvSubtituloApp;
    private FloatingActionButton fabPagoRapido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);   // Carga la interfaz activity_main.xml
        ajustarBordesPantalla();

        vincularVistas();
        configurarEventos();

        // Solo la primera vez que se abre la app se colocan los fragmentos
        // (si se gira la pantalla, Android los restaura solo).
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contenedorMenu, new MenuFragment())
                    .replace(R.id.contenedorDetalle, new PerfilFragment())
                    .commit();
        }
    }

    // ===== 2. VÍNCULO DE LAS VARIABLES CON LOS IDENTIFICADORES DEL XML =====
    private void vincularVistas() {
        tvSubtituloApp = findViewById(R.id.tvSubtituloApp);
        fabPagoRapido = findViewById(R.id.fabPagoRapido);
    }

    // ===== 3. IDENTIFICACIÓN DE EVENTOS =====
    private void configurarEventos() {
        // Evento clic del botón flotante -> método abrirPagoRapido()
        fabPagoRapido.setOnClickListener(v -> abrirPagoRapido());
    }

    // ===== 4. MÉTODOS DE LOS EVENTOS =====

    /**
     * Se ejecuta cuando el usuario toca una opción del menú izquierdo.
     * Decide qué fragmento mostrar a la derecha.
     */
    @Override
    public void onOpcionSeleccionada(int posicion, String titulo) {
        Fragment fragmento;
        switch (posicion) {
            case MenuFragment.OPCION_FOTOS:
                fragmento = new FotosFragment();
                break;
            case MenuFragment.OPCION_VIDEO:
                fragmento = new VideoFragment();
                break;
            case MenuFragment.OPCION_WEB:
                fragmento = new WebFragment();
                break;
            case MenuFragment.OPCION_BOTONES:
                fragmento = new BotonesFragment();
                break;
            case MenuFragment.OPCION_PERFIL:
            default:
                fragmento = new PerfilFragment();
                break;
        }
        mostrarFragmentoDetalle(fragmento);
        tvSubtituloApp.setText(getString(R.string.subtitulo_seccion, titulo));

        // En la pantalla de pagos el botón flotante sobra, así que se oculta.
        if (posicion == MenuFragment.OPCION_BOTONES) {
            fabPagoRapido.hide();
        } else {
            fabPagoRapido.show();
        }
    }

    /** Reemplaza lo que haya en el contenedor derecho por el fragmento recibido. */
    private void mostrarFragmentoDetalle(Fragment fragmento) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.contenedorDetalle, fragmento)
                .commit();
    }

    /** Evento del botón flotante: selecciona "Botones" en el menú como si el usuario lo tocara. */
    private void abrirPagoRapido() {
        Fragment menu = getSupportFragmentManager().findFragmentById(R.id.contenedorMenu);
        if (menu instanceof MenuFragment) {
            ((MenuFragment) menu).seleccionarOpcion(MenuFragment.OPCION_BOTONES);
        }
    }

    /** Evita que el contenido quede debajo de la barra de estado, la de navegación o el teclado. */
    private void ajustarBordesPantalla() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets barras = insets.getInsets(
                    WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.ime());
            v.setPadding(barras.left, barras.top, barras.right, barras.bottom);
            return insets;
        });
    }
}
