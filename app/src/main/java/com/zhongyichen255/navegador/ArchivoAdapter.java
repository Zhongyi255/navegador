package com.zhongyichen255.navegador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ArchivoAdapter extends BaseAdapter {
    private List<String> rutas;
    private Context context;
    private int layout;


    public ArchivoAdapter(List<String> rutas, Context context, int layout){
        this.rutas = rutas;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return rutas.size();
    }

    @Override
    public Object getItem(int i) {
        return rutas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(layout, null);

        TextView ruta = view.findViewById(R.id.ruta_del_archivo);
        ruta.setText(rutas.get(i));

        return view;
    }

    public void setRutas(List<String> rutas) {
        this.rutas = rutas;
    }
}
