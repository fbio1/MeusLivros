package br.ufrn.eaj.tads.meuslivros;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import model.Livro;


public class LivroAdapterList extends BaseAdapter {

    Context context;
    List<Livro> listaLivros;

    public LivroAdapterList(Context context, List<Livro> listaLivros){
        this.context = context;
        this.listaLivros = listaLivros;
    }
    @Override
    public int getCount() {
        return listaLivros != null ? listaLivros.size(): 0;
    }

    @Override
    public Object getItem(int i) {
        return listaLivros.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        View view;
        ViewHolder holder;

        if(convertView == null){
            view = LayoutInflater.from(context).inflate(R.layout.livro_item_inflater, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);

        }else{
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        //Preenche os dados do livro
        Livro livroescolhido = listaLivros.get(i);
        holder.textViewTitulo.setText(livroescolhido.getTitulo());
        holder.textViewAutor.setText(livroescolhido.getAutor());
        holder.textViewAno.setText(livroescolhido.getAno().toString());
        holder.textViewNota.setText(""+livroescolhido.getNota());

        return view;
    }
}
