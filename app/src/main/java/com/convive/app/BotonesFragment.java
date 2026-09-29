package com.convive.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * FRAGMENTO BOTONES: ejemplo funcional de controles de botones aplicado al pago de cuotas.
 * El usuario elige concepto y medio de pago (RadioButton) y confirma o cancela (Button).
 */
public class BotonesFragment extends Fragment {

    // ===== 1. DECLARACIÓN DE VARIABLES =====
    private RadioGroup rgConcepto;
    private RadioGroup rgMedioPago;
    private TextView tvResumenPago;
    private TextView tvResultadoPago;
    private Button btnCancelarPago;
    private Button btnConfirmarPago;
    private Button btnDescargarRecibo;

    private String ultimaReferencia = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_botones, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vincularVistas(view);
        configurarEventos();
    }

    // ===== 2. VÍNCULO DE VARIABLES CON IDENTIFICADORES =====
    private void vincularVistas(View view) {
        rgConcepto = view.findViewById(R.id.rgConcepto);
        rgMedioPago = view.findViewById(R.id.rgMedioPago);
        tvResumenPago = view.findViewById(R.id.tvResumenPago);
        tvResultadoPago = view.findViewById(R.id.tvResultadoPago);
        btnCancelarPago = view.findViewById(R.id.btnCancelarPago);
        btnConfirmarPago = view.findViewById(R.id.btnConfirmarPago);
        btnDescargarRecibo = view.findViewById(R.id.btnDescargarRecibo);
    }

    // ===== 3. IDENTIFICACIÓN DE EVENTOS =====
    private void configurarEventos() {
        rgConcepto.setOnCheckedChangeListener((grupo, idMarcado) -> actualizarResumen());
        rgMedioPago.setOnCheckedChangeListener((grupo, idMarcado) -> actualizarResumen());
        btnConfirmarPago.setOnClickListener(v -> confirmarPago());
        btnCancelarPago.setOnClickListener(v -> cancelarPago());
        btnDescargarRecibo.setOnClickListener(v -> descargarRecibo());
    }

    // ===== 4. MÉTODOS DE LOS EVENTOS =====

    /** Se ejecuta cada vez que cambia una selección: arma el resumen y habilita "Confirmar". */
    private void actualizarResumen() {
        String concepto = obtenerConcepto();
        String medio = obtenerMedioPago();

        if (concepto == null || medio == null) {
            tvResumenPago.setText(R.string.resumen_incompleto);
            btnConfirmarPago.setEnabled(false);
            return;
        }
        tvResumenPago.setText("Vas a pagar " + formatearValor(obtenerValor())
                + " por " + concepto + " usando " + medio + ".");
        btnConfirmarPago.setEnabled(true);
    }

    /** Pide confirmación en un cuadro de diálogo antes de "pagar". */
    private void confirmarPago() {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Confirmar pago")
                .setMessage(tvResumenPago.getText() + "\n\n¿Deseas continuar?")
                .setPositiveButton("Pagar", (dialogo, boton) -> procesarPago())
                .setNegativeButton("Volver", null)
                .show();
    }

    /** Simula un pago exitoso y muestra el resultado. */
    private void procesarPago() {
        ultimaReferencia = "CV-" + (System.currentTimeMillis() % 1000000);
        tvResultadoPago.setText("✔ Pago exitoso\n" + obtenerConcepto() + " – " + formatearValor(obtenerValor())
                + "\nMedio: " + obtenerMedioPago() + "\nReferencia: " + ultimaReferencia);
        tvResultadoPago.setVisibility(View.VISIBLE);
        btnDescargarRecibo.setVisibility(View.VISIBLE);
    }

    /** Limpia todas las selecciones. */
    private void cancelarPago() {
        rgConcepto.clearCheck();
        rgMedioPago.clearCheck();
        tvResultadoPago.setVisibility(View.GONE);
        btnDescargarRecibo.setVisibility(View.GONE);
        Toast.makeText(requireContext(), "Pago cancelado", Toast.LENGTH_SHORT).show();
    }

    private void descargarRecibo() {
        Toast.makeText(requireContext(),
                "Comprobante " + ultimaReferencia + " descargado (simulación)", Toast.LENGTH_SHORT).show();
    }

    // ===== Métodos auxiliares =====
    // Nota: se usa if/else y no switch porque en Android Studio reciente los R.id no son constantes.

    @Nullable
    private String obtenerConcepto() {
        int id = rgConcepto.getCheckedRadioButtonId();
        if (id == R.id.rbAdministracion) return "Cuota de administración";
        if (id == R.id.rbParqueadero) return "Parqueadero";
        if (id == R.id.rbExtraordinaria) return "Cuota extraordinaria";
        return null;
    }

    private int obtenerValor() {
        int id = rgConcepto.getCheckedRadioButtonId();
        if (id == R.id.rbAdministracion) return 150000;
        if (id == R.id.rbParqueadero) return 40000;
        if (id == R.id.rbExtraordinaria) return 200000;
        return 0;
    }

    @Nullable
    private String obtenerMedioPago() {
        int id = rgMedioPago.getCheckedRadioButtonId();
        if (id == R.id.rbPse) return "PSE";
        if (id == R.id.rbTarjeta) return "Tarjeta de crédito";
        if (id == R.id.rbNequi) return "Nequi";
        return null;
    }

    /** Convierte 150000 en "$150.000" (formato colombiano). */
    private String formatearValor(int valor) {
        NumberFormat formato = NumberFormat.getNumberInstance(Locale.forLanguageTag("es-CO"));
        return "$" + formato.format(valor);
    }
}
