package com.convive.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * FRAGMENTO WEB: el usuario escribe una URL en un EditText y se carga en un WebView.
 * Por seguridad solo se aceptan direcciones https://.
 * Requiere el permiso INTERNET en AndroidManifest.xml.
 */
public class WebFragment extends Fragment {

    // ===== 1. DECLARACIÓN DE VARIABLES =====
    private EditText etUrl;
    private Button btnCargarUrl;
    private ProgressBar pbCargaWeb;
    private WebView wvPortal;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_web, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vincularVistas(view);
        configurarWebView();
        configurarEventos();
        cargarUrl();   // Carga la URL inicial que ya trae la caja de texto
    }

    // ===== 2. VÍNCULO DE VARIABLES CON IDENTIFICADORES =====
    private void vincularVistas(View view) {
        etUrl = view.findViewById(R.id.etUrl);
        btnCargarUrl = view.findViewById(R.id.btnCargarUrl);
        pbCargaWeb = view.findViewById(R.id.pbCargaWeb);
        wvPortal = view.findViewById(R.id.wvPortal);
    }

    /** Ajustes del navegador interno. */
    private void configurarWebView() {
        WebSettings ajustes = wvPortal.getSettings();
        ajustes.setJavaScriptEnabled(true);   // La mayoría de portales lo necesitan
        ajustes.setDomStorageEnabled(true);

        // WebViewClient: hace que los enlaces se abran DENTRO de la app y avisa inicio/fin de carga
        wvPortal.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                pbCargaWeb.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                pbCargaWeb.setVisibility(View.GONE);
                etUrl.setText(url);
            }
        });

        // WebChromeClient: informa el porcentaje de carga para la barra de progreso
        wvPortal.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int progreso) {
                pbCargaWeb.setProgress(progreso);
            }
        });
    }

    // ===== 3. IDENTIFICACIÓN DE EVENTOS =====
    private void configurarEventos() {
        // Evento clic del botón "Ir"
        btnCargarUrl.setOnClickListener(v -> cargarUrl());

        // Evento tecla "Ir" del teclado del celular
        etUrl.setOnEditorActionListener((vista, accion, evento) -> {
            if (accion == EditorInfo.IME_ACTION_GO) {
                cargarUrl();
                return true;
            }
            return false;
        });
    }

    // ===== 4. MÉTODOS DE LOS EVENTOS =====
    private void cargarUrl() {
        String url = etUrl.getText().toString().trim();

        if (url.isEmpty()) {
            etUrl.setError("Escribe una dirección web");
            return;
        }
        if (url.startsWith("http://")) {
            etUrl.setError("Por seguridad solo se permiten direcciones https://");
            return;
        }
        if (!url.startsWith("https://")) {
            url = "https://" + url;   // Si el usuario escribe "google.com", se completa
        }

        wvPortal.loadUrl(url);
        ocultarTeclado();
    }

    private void ocultarTeclado() {
        InputMethodManager teclado =
                (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (teclado != null) {
            teclado.hideSoftInputFromWindow(etUrl.getWindowToken(), 0);
        }
    }
}
