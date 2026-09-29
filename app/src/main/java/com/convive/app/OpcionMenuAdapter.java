package com.convive.app;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/**
 * ADAPTADOR del menú: toma la lista de OpcionMenu y "dibuja" cada una
 * usando el diseño item_menu.xml. También resalta la opción seleccionada.
 */
public class OpcionMenuAdapter extends ArrayAdapter<OpcionMenu> {

    private int posicionSeleccionada = MenuFragment.OPCION_PERFIL;

    public OpcionMenuAdapter(@NonNull Context context, @NonNull List<OpcionMenu> opciones) {
        super(context, 0, opciones);
    }

    /** Cambia la opción resaltada y redibuja la lista. */
    public void setPosicionSeleccionada(int posicion) {
        posicionSeleccionada = posicion;
        notifyDataSetChanged();
    }

    public int getPosicionSeleccionada() {
        return posicionSeleccionada;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_menu, parent, false);
        }

        // Vínculo de variables con los identificadores de item_menu.xml
        LinearLayout layoutItemMenu = convertView.findViewById(R.id.layoutItemMenu);
        ImageView ivIconoOpcion = convertView.findViewById(R.id.ivIconoOpcion);
        TextView tvNombreOpcion = convertView.findViewById(R.id.tvNombreOpcion);

        OpcionMenu opcion = getItem(position);
        if (opcion != null) {
            ivIconoOpcion.setImageResource(opcion.getIconoResId());
            tvNombreOpcion.setText(opcion.getTitulo());
        }

        boolean seleccionada = (position == posicionSeleccionada);
        layoutItemMenu.setBackgroundResource(
                seleccionada ? R.drawable.bg_item_menu_seleccionado : R.drawable.bg_item_menu);
        tvNombreOpcion.setTypeface(null, seleccionada ? Typeface.BOLD : Typeface.NORMAL);

        return convertView;
    }
}
