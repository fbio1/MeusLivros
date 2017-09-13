package br.ufrn.eaj.tads.meuslivros;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class ViewHolder {

    TextView textViewTitulo;
    TextView textViewAutor;
    TextView textViewAno;
    TextView textViewNota;

    public ViewHolder (View v){
        textViewTitulo = v.findViewById(R.id.titulo);
        textViewAutor=  v.findViewById(R.id.autor);
        textViewAno=  v.findViewById(R.id.ano);
        textViewNota = v.findViewById(R.id.nota);
    }
}
