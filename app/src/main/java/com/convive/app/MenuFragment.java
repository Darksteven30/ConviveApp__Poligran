package com.convive.app;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * FRAGMENTO IZQUIERDO: muestra el menú de opciones en una lista (ListView).
 * Cuando el usuario toca una opción, le avisa a la actividad mediante una interfaz.
 */
public class MenuFragment extends Fragment {

    // Posiciones de cada opción dentro de la lista
    public static final int OPCION_PERFIL = 0;
    public static final int OPCION_FOTOS = 1;
    public static final int OPCION_VIDEO = 2;
    public static final int OPCION_WEB = 3;
    public static final int OPCION_BOTONES = 4;

    private static final String CLAVE_SELECCION = "opcion_seleccionada";

    /** Interfaz de comunicación: la actividad la implementa para saber qué se seleccionó. */
    public interface OnOpcionSeleccionadaListener {
        void onOpcionSeleccionada(int posicion, String titulo);
    }

    // ===== 1. DECLARACIÓN DE VARIABLES =====
    private ListView lvMenu;
    private OpcionMenuAdapter adaptadorMenu;
    private final List<OpcionMenu> opciones = new ArrayList<>();
    private OnOpcionSeleccionadaListener listener;

    /** Se ejecuta cuando el fragmento se une a la actividad: ahí guardamos el "oyente". */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnOpcionSeleccionadaListener) {
            listener = (OnOpcionSeleccionadaListener) context;
        } else {
            throw new IllegalStateException(
                    "La actividad debe implementar MenuFragment.OnOpcionSeleccionadaListener");
        }
    }

    /** Carga la interfaz fragment_menu.xml. */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    /** Se ejecuta cuando la interfaz ya existe: aquí se vinculan vistas y eventos. */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vincularVistas(view);
        cargarOpciones();
        configurarEventos();

        if (savedInstanceState != null) {
            adaptadorMenu.setPosicionSeleccionada(
                    savedInstanceState.getInt(CLAVE_SELECCION, OPCION_PERFIL));
        }
    }

    // ===== 2. VÍNCULO DE VARIABLES CON IDENTIFICADORES =====
    private void vincularVistas(View view) {
        lvMenu = view.findViewById(R.id.lvMenu);
    }

    /** Llena la lista con las 5 opciones y le asigna el adaptador. */
    private void cargarOpciones() {
        opciones.clear();
        opciones.add(new OpcionMenu(getString(R.string.menu_perfil), R.drawable.ic_perfil));
        opciones.add(new OpcionMenu(getString(R.string.menu_fotos), R.drawable.ic_fotos));
        opciones.add(new OpcionMenu(getString(R.string.menu_video), R.drawable.ic_video));
        opciones.add(new OpcionMenu(getString(R.string.menu_web), R.drawable.ic_web));
        opciones.add(new OpcionMenu(getString(R.string.menu_botones), R.drawable.ic_pago));

        adaptadorMenu = new OpcionMenuAdapter(requireContext(), opciones);
        lvMenu.setAdapter(adaptadorMenu);
    }

    // ===== 3. IDENTIFICACIÓN DE EVENTOS =====
    private void configurarEventos() {
        // Evento clic sobre un ítem de la lista -> método seleccionarOpcion()
        lvMenu.setOnItemClickListener((parent, itemView, posicion, id) -> seleccionarOpcion(posicion));
    }

    // ===== 4. MÉTODOS DE LOS EVENTOS =====

    /** Resalta la opción y avisa a la actividad para que cambie el fragmento derecho. */
    public void seleccionarOpcion(int posicion) {
        if (adaptadorMenu == null) {
            return;
        }
        adaptadorMenu.setPosicionSeleccionada(posicion);
        lvMenu.smoothScrollToPosition(posicion);
        if (listener != null) {
            listener.onOpcionSeleccionada(posicion, opciones.get(posicion).getTitulo());
        }
    }

    /** Guarda qué opción estaba resaltada por si se gira la pantalla. */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (adaptadorMenu != null) {
            outState.putInt(CLAVE_SELECCION, adaptadorMenu.getPosicionSeleccionada());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
