package com.convive.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * FRAGMENTO FOTOS: lista desplazable (ListView) de fotos adjuntas a reportes.
 * Al tocar una foto se muestra en grande con su descripción.
 */
public class FotosFragment extends Fragment {

    // ===== 1. DECLARACIÓN DE VARIABLES =====
    private ImageView ivFotoSeleccionada;
    private TextView tvTituloFotoSeleccionada;
    private TextView tvDescripcionFoto;
    private Button btnAdjuntarFoto;
    private ListView lvFotos;
    private final List<FotoReporte> fotos = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fotos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vincularVistas(view);
        cargarFotos();
        configurarEventos();
    }

    // ===== 2. VÍNCULO DE VARIABLES CON IDENTIFICADORES =====
    private void vincularVistas(View view) {
        ivFotoSeleccionada = view.findViewById(R.id.ivFotoSeleccionada);
        tvTituloFotoSeleccionada = view.findViewById(R.id.tvTituloFotoSeleccionada);
        tvDescripcionFoto = view.findViewById(R.id.tvDescripcionFoto);
        btnAdjuntarFoto = view.findViewById(R.id.btnAdjuntarFoto);
        lvFotos = view.findViewById(R.id.lvFotos);
    }

    /** Crea la lista de fotos de ejemplo y la conecta al ListView mediante el adaptador. */
    private void cargarFotos() {
        fotos.clear();
        fotos.add(new FotoReporte(R.drawable.foto_grieta, "Grieta en muro – Torre 2", "En revisión",
                "Reportada el 12 sep. 2026 por el apto 204. Grieta diagonal junto a la escalera."));
        fotos.add(new FotoReporte(R.drawable.foto_fuga, "Fuga de agua – Parqueadero", "Asignada a mantenimiento",
                "Reportada el 15 sep. 2026. Goteo constante sobre los puestos 18 y 19."));
        fotos.add(new FotoReporte(R.drawable.foto_luminaria, "Luminaria dañada – Sendero", "Resuelto",
                "Reportada el 2 sep. 2026. La luminaria fue reemplazada el 6 sep. 2026."));
        fotos.add(new FotoReporte(R.drawable.foto_puerta, "Puerta de acceso – Portería", "Radicado",
                "Reportada el 20 sep. 2026. La bisagra superior está suelta y la puerta no cierra."));

        lvFotos.setAdapter(new FotoAdapter(requireContext(), fotos));
        mostrarFoto(fotos.get(0));   // Al abrir, se muestra la primera foto
    }

    // ===== 3. IDENTIFICACIÓN DE EVENTOS =====
    private void configurarEventos() {
        // Evento clic sobre una foto de la lista -> método mostrarFoto()
        lvFotos.setOnItemClickListener((parent, itemView, posicion, id) -> mostrarFoto(fotos.get(posicion)));
        btnAdjuntarFoto.setOnClickListener(v -> adjuntarFoto());
    }

    // ===== 4. MÉTODOS DE LOS EVENTOS =====
    private void mostrarFoto(FotoReporte foto) {
        ivFotoSeleccionada.setImageResource(foto.getImagenResId());
        tvTituloFotoSeleccionada.setText(foto.getTitulo() + " · " + foto.getEstado());
        tvDescripcionFoto.setText(foto.getDescripcion());
    }

    private void adjuntarFoto() {
        Toast.makeText(requireContext(),
                "Aquí se abrirá la cámara o la galería (próxima entrega)", Toast.LENGTH_SHORT).show();
    }
}
