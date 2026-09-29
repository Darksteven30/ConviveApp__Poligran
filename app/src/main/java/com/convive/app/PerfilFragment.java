package com.convive.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.materialswitch.MaterialSwitch;

/**
 * FRAGMENTO PERFIL: foto, nombre, unidad y estado de cuenta del propietario,
 * todo dentro de un ScrollView (vista con barra de desplazamiento).
 */
public class PerfilFragment extends Fragment {

    // ===== 1. DECLARACIÓN DE VARIABLES =====
    private ImageView ivFotoPerfil;
    private TextView tvNombre;
    private TextView tvUnidad;
    private TextView tvSaldo;
    private TextView tvEstadoPago;
    private TextView tvCorreo;
    private TextView tvTelefono;
    private TextView tvHistorial;
    private MaterialSwitch swNotificaciones;
    private Button btnDescargarComprobante;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_perfil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vincularVistas(view);
        cargarDatosPerfil();
        configurarEventos();
    }

    // ===== 2. VÍNCULO DE VARIABLES CON IDENTIFICADORES =====
    private void vincularVistas(View view) {
        ivFotoPerfil = view.findViewById(R.id.ivFotoPerfil);
        tvNombre = view.findViewById(R.id.tvNombre);
        tvUnidad = view.findViewById(R.id.tvUnidad);
        tvSaldo = view.findViewById(R.id.tvSaldo);
        tvEstadoPago = view.findViewById(R.id.tvEstadoPago);
        tvCorreo = view.findViewById(R.id.tvCorreo);
        tvTelefono = view.findViewById(R.id.tvTelefono);
        tvHistorial = view.findViewById(R.id.tvHistorial);
        swNotificaciones = view.findViewById(R.id.swNotificaciones);
        btnDescargarComprobante = view.findViewById(R.id.btnDescargarComprobante);
    }

    /** Muestra datos de ejemplo. En entregas futuras vendrán del servidor. */
    private void cargarDatosPerfil() {
        tvNombre.setText("Juan C. Muñoz Rojas");
        tvUnidad.setText("Propietario – Torre 3, Apto 502");
        tvSaldo.setText("Saldo actual: $150.000");
        tvEstadoPago.setText("Estado: Pendiente · vence el 5 oct. 2026");
        tvCorreo.setText("✉  jmunoz@convive.app");
        tvTelefono.setText("☎  300 000 0000");
        tvHistorial.setText(
                "• Administración – sep. 2026 – Pendiente\n"
                        + "• Administración – ago. 2026 – $150.000 ✔\n"
                        + "• Administración – jul. 2026 – $150.000 ✔\n"
                        + "• Parqueadero – jul. 2026 – $40.000 ✔\n"
                        + "• Administración – jun. 2026 – $150.000 ✔\n"
                        + "• Cuota extraordinaria – may. 2026 – $200.000 ✔\n"
                        + "• Administración – may. 2026 – $150.000 ✔");
        swNotificaciones.setChecked(true);
    }

    // ===== 3. IDENTIFICACIÓN DE EVENTOS =====
    private void configurarEventos() {
        btnDescargarComprobante.setOnClickListener(v -> descargarComprobante());
        swNotificaciones.setOnCheckedChangeListener((boton, activado) -> cambiarNotificaciones(activado));
        ivFotoPerfil.setOnClickListener(v -> cambiarFotoPerfil());
    }

    // ===== 4. MÉTODOS DE LOS EVENTOS =====
    private void descargarComprobante() {
        Toast.makeText(requireContext(),
                "Comprobante de agosto 2026 descargado (simulación)", Toast.LENGTH_SHORT).show();
    }

    private void cambiarNotificaciones(boolean activado) {
        String mensaje = activado ? "Notificaciones activadas" : "Notificaciones desactivadas";
        Toast.makeText(requireContext(), mensaje, Toast.LENGTH_SHORT).show();
    }

    private void cambiarFotoPerfil() {
        Toast.makeText(requireContext(),
                "Aquí se podrá cambiar la foto de perfil (próxima entrega)", Toast.LENGTH_SHORT).show();
    }
}
