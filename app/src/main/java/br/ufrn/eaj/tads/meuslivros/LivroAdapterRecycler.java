package br.ufrn.eaj.tads.meuslivros;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import model.Livro;


public class LivroAdapterRecycler extends RecyclerView.Adapter {

    Context context;
    List<Livro> livroList;
    BancoHelper bancoHelper;

    private static final int PENDING_REMOVAL_TIMEOUT = 3000;
    List<Livro> itemsPendingRemoval;

    private Handler handler = new Handler();
    HashMap<Livro, Runnable> pendingRunnables = new HashMap<>();


    public LivroAdapterRecycler(Context context, List<Livro> livroList) {
        this.context = context;
        this.livroList = livroList;
        this.bancoHelper = new BancoHelper(context);
        this.itemsPendingRemoval = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.livro_inflater_novo, parent, false);
        LivroHolder holder = new LivroHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        LivroHolder livroHolder = (LivroHolder) holder;
        final Livro livroEscolhido = livroList.get(position);

        if (itemsPendingRemoval.contains(livroEscolhido)) {
            //view do swipe/delete
            livroHolder.layoutNormal.setVisibility(View.GONE);
            livroHolder.layoutGone.setVisibility(View.VISIBLE);
            livroHolder.undoButton.setVisibility(View.VISIBLE);
            livroHolder.undoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // usou o undo, remover a pendingRennable
                    Runnable pendingRemovalRunnable = pendingRunnables.get(livroEscolhido);
                    pendingRunnables.remove(livroEscolhido);
                    if (pendingRemovalRunnable != null){
                        handler.removeCallbacks(pendingRemovalRunnable);
                    }
                    itemsPendingRemoval.remove(livroEscolhido);
                    //binda novamente para redesenhar
                    notifyItemChanged(livroList.indexOf(livroEscolhido));
                }
            });
        }else{
            livroHolder.textViewTitulo.setText(livroEscolhido.getTitulo());
            livroHolder.textViewAutor.setText(livroEscolhido.getAutor());
            livroHolder.textViewAno.setText(livroEscolhido.getAno());
            livroHolder.textViewNota.setText(""+livroEscolhido.getNota());
            livroHolder.layoutNormal.setVisibility(View.VISIBLE);
            livroHolder.layoutGone.setVisibility(View.GONE);
            livroHolder.undoButton.setVisibility(View.GONE);
            livroHolder.undoButton.setOnClickListener(null);
        }
    }

    @Override
    public int getItemCount() {
        return livroList == null ? 0 : livroList.size();
    }

    public void removerComTempo(int position) {
        final Livro livro = livroList.get(position);
        if (!itemsPendingRemoval.contains(livro)) {
            itemsPendingRemoval.add(livro);
            notifyItemChanged(position);
            Runnable pendingRemovalRunnable = new Runnable() {
                @Override
                public void run() {
                    remover(livroList.indexOf(livro));
                }
            };
            handler.postDelayed(pendingRemovalRunnable, PENDING_REMOVAL_TIMEOUT);
            pendingRunnables.put(livro, pendingRemovalRunnable);
        }
    }

    public void remover(int position) {
        Livro livro = livroList.get(position);
        if (itemsPendingRemoval.contains(livro)) {
            itemsPendingRemoval.remove(livro);
            //Pode implementar como se fosse uma outra lista
        }
        if (livroList.contains(livro)) {
            livroList.remove(position);
            bancoHelper.delete(livro);
            notifyItemRemoved(position);
        }
    }


    public void mover(int fromPosition, int toPosition){

        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(livroList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(livroList, i, i - 1);
            }
        }


        notifyItemMoved(fromPosition, toPosition);
        notifyItemChanged(toPosition);
        notifyItemChanged(fromPosition);
    }


    public class LivroHolder extends RecyclerView.ViewHolder {

        final TextView textViewTitulo;
        final TextView textViewAutor;
        final TextView textViewAno;
        final TextView textViewNota;
        final LinearLayout layoutNormal;
        final LinearLayout layoutGone;
        final Button undoButton;

        public LivroHolder(View v) {
            super(v);
            textViewTitulo = v.findViewById(R.id.titulo);
            textViewAutor=  v.findViewById(R.id.autor);
            textViewAno=  v.findViewById(R.id.ano);
            textViewNota = v.findViewById(R.id.nota);
            layoutNormal = v.findViewById(R.id.layout_normal);
            layoutGone = v.findViewById(R.id.layout_gone);
            undoButton = v.findViewById(R.id.undo_button);
        }
    }
}
