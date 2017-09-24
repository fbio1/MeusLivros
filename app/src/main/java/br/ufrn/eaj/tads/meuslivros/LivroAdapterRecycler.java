package br.ufrn.eaj.tads.meuslivros;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import model.Livro;


public class LivroAdapterRecycler extends RecyclerView.Adapter {

    Context context;
    List<Livro> livroList;
    BancoHelper bancoHelper;

    public LivroAdapterRecycler(Context context, List<Livro> livroList) {
        this.context = context;
        this.livroList = livroList;
        this.bancoHelper = new BancoHelper(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.livro_card_inflater, parent, false);
        LivroHolder holder = new LivroHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        LivroHolder livroHolder = (LivroHolder) holder;
        final Livro livroEscolhido = livroList.get(position);

        livroHolder.textViewTitulo.setText(livroEscolhido.getTitulo());
        livroHolder.textViewAutor.setText(livroEscolhido.getAutor());
        livroHolder.textViewAno.setText(livroEscolhido.getAno());
        livroHolder.textViewNota.setText(""+livroEscolhido.getNota());
    }

    @Override
    public int getItemCount() {
        return livroList == null ? 0 : livroList.size();
    }

    public class LivroHolder extends RecyclerView.ViewHolder {

        final TextView textViewTitulo;
        final TextView textViewAutor;
        final TextView textViewAno;
        final TextView textViewNota;

        public LivroHolder(View v) {
            super(v);
            textViewTitulo = v.findViewById(R.id.titulo);
            textViewAutor=  v.findViewById(R.id.autor);
            textViewAno=  v.findViewById(R.id.ano);
            textViewNota = v.findViewById(R.id.nota);
        }
    }

}
