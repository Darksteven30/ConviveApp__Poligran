package com.convive.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/**
 * ADAPTADOR de la lista de fotos: dibuja cada FotoReporte usando item_foto.xml.
 */
public class FotoAdapter extends ArrayAdapter<FotoReporte> {

    public FotoAdapter(@NonNull Context context, @NonNull List<FotoReporte> fotos) {
        super(context, 0, fotos);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_foto, parent, false);
        }

        // Vínculo de variables con los identificadores de item_foto.xml
        ImageView ivMiniaturaFoto = convertView.findViewById(R.id.ivMiniaturaFoto);
        TextView tvTituloFotoItem = convertView.findViewById(R.id.tvTituloFotoItem);
        TextView tvEstadoFotoItem = convertView.findViewById(R.id.tvEstadoFotoItem);

        FotoReporte foto = getItem(position);
        if (foto != null) {
            ivMiniaturaFoto.setImageResource(foto.getImagenResId());
            tvTituloFotoItem.setText(foto.getTitulo());
            tvEstadoFotoItem.setText(foto.getEstado());
        }
        return convertView;
    }
}
