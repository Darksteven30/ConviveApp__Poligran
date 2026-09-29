package com.convive.app;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * FRAGMENTO VIDEO: reproduce el video de inducción guardado en res/raw
 * usando el widget VideoView, con controles propios y los de MediaController.
 */
public class VideoFragment extends Fragment {

    // ===== 1. DECLARACIÓN DE VARIABLES =====
    private VideoView vvInduccion;
    private TextView tvEstadoVideo;
    private Button btnReproducir;
    private Button btnPausar;
    private Button btnReiniciar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vincularVistas(view);
        configurarVideo();
        configurarEventos();
    }

    // ===== 2. VÍNCULO DE VARIABLES CON IDENTIFICADORES =====
    private void vincularVistas(View view) {
        vvInduccion = view.findViewById(R.id.vvInduccion);
        tvEstadoVideo = view.findViewById(R.id.tvEstadoVideo);
        btnReproducir = view.findViewById(R.id.btnReproducir);
        btnPausar = view.findViewById(R.id.btnPausar);
        btnReiniciar = view.findViewById(R.id.btnReiniciar);
    }

    /** Indica qué video reproducir y le agrega la barra de controles del sistema. */
    private void configurarVideo() {
        Uri rutaVideo = Uri.parse("android.resource://"
                + requireContext().getPackageName() + "/" + R.raw.video_induccion);
        vvInduccion.setVideoURI(rutaVideo);

        MediaController controles = new MediaController(requireContext());
        controles.setAnchorView(vvInduccion);
        vvInduccion.setMediaController(controles);
    }

    // ===== 3. IDENTIFICACIÓN DE EVENTOS =====
    private void configurarEventos() {
        vvInduccion.setOnPreparedListener(reproductor -> videoListo());
        vvInduccion.setOnCompletionListener(reproductor -> videoTerminado());
        btnReproducir.setOnClickListener(v -> reproducirVideo());
        btnPausar.setOnClickListener(v -> pausarVideo());
        btnReiniciar.setOnClickListener(v -> reiniciarVideo());
    }

    // ===== 4. MÉTODOS DE LOS EVENTOS =====
    private void videoListo() {
        vvInduccion.seekTo(1);   // Muestra el primer cuadro en lugar de una pantalla negra
        tvEstadoVideo.setText("Video listo. Presiona Reproducir.");
    }

    private void reproducirVideo() {
        vvInduccion.start();
        tvEstadoVideo.setText("▶ Reproduciendo…");
    }

    private void pausarVideo() {
        if (vvInduccion.isPlaying()) {
            vvInduccion.pause();
            tvEstadoVideo.setText("❚❚ En pausa");
        }
    }

    private void reiniciarVideo() {
        vvInduccion.seekTo(0);
        vvInduccion.start();
        tvEstadoVideo.setText("▶ Reproduciendo desde el inicio…");
    }

    private void videoTerminado() {
        tvEstadoVideo.setText("✔ Video terminado. Puedes verlo de nuevo con Reiniciar.");
    }

    /** Si el usuario sale de la app o cambia de opción, el video se pausa. */
    @Override
    public void onPause() {
        super.onPause();
        if (vvInduccion != null && vvInduccion.isPlaying()) {
            vvInduccion.pause();
        }
    }
}
