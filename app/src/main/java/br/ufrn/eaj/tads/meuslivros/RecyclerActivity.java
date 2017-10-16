package br.ufrn.eaj.tads.meuslivros;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;
import jp.wasabeef.recyclerview.animators.LandingAnimator;
import model.Livro;

/**
 * Created by fbio_ on 24/09/2017.
 */

public class RecyclerActivity extends AppCompatActivity {
    Livro livro;
    List<Livro> listaLivro = new ArrayList<>();
    BancoHelper bancoHelper = new BancoHelper(this);
    RecyclerView recyclerView;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        listaLivro = bancoHelper.findAll();

        recyclerView.setAdapter(new LivroAdapterRecycler(context, listaLivro));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnItemTouchListener(new MyRecycler(RecyclerActivity.this, recyclerView, new MyRecycler.OnItemClickListener() {
            //Verificar a virw que esta sendo clicada, pois no click de "desfazer" entra no edit(OnItemClick)
            @Override
            public void OnItemClick(View view, int i) {
//              Toast.makeText(RecyclerActivity.this, "Clique simples", Toast.LENGTH_SHORT).show();
//                Intent it = new Intent(RecyclerActivity.this,CadastroActivity.class);
//                Bundle bundle = new Bundle();
//
//                bundle.putString("titulo",listaLivro.get(i).getTitulo());
//                bundle.putString("autor",listaLivro.get(i).getAutor());
//                bundle.putString("ano",listaLivro.get(i).getAno());
//                bundle.putFloat("nota", (float) listaLivro.get(i).getNota());
//                bundle.putLong("id",listaLivro.get(i).getId());
//
//                it.putExtras(bundle);
//
//                startActivity(it);
            }

            @Override
            public void OnItemLongClick(View view, final int position) {
                //Toast.makeText(Main5Activity.this, "Clique longo", Toast.LENGTH_SHORT).show();

//                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
//                builder.setTitle("Confimação")
//                        .setMessage("Tem certeza que deseja excluir este livro?")
//                        .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                boolean sucesso = false;
//                                if(sucesso == false) {
//                                    final Livro removido = listaLivro.get(position);
//                                    listaLivro.remove(position);
//                                    bancoHelper.delete(removido);
//
//                                    recyclerView.getAdapter().notifyItemRemoved(position);
//                                    Snackbar.make((View)recyclerView.getParent(), "Excluiu!", Snackbar.LENGTH_SHORT)
//                                            .setAction("Action", null).show();
//                                }else{
//                                    Snackbar.make((View)recyclerView.getParent(), "Cancelou a operação!", Snackbar.LENGTH_SHORT)
//                                            .setAction("Action", null).show();
//                                }
//
//                            }
//                        })
//                        .setNegativeButton("Cancelar", null)
//                        .create()
//                        .show();

//                final Livro removido = listaLivro.get(position);
//                listaLivro.remove(position);
//                recyclerView.getAdapter().notifyItemRemoved(position);
//
//                Snackbar snack = Snackbar.make((View)recyclerView.getParent(),"Removido", Snackbar.LENGTH_LONG)
//                        .setAction("Cancelar", new View.OnClickListener(){
//                            @Override
//                            public void onClick(View view) {
//                                listaLivro.add(position, removido);
//                                recyclerView.getAdapter().notifyItemInserted(position);
//                                recyclerView.scrollToPosition(position);
//                            }
//                        });
//                snack.show();
            }
        }));

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        ItemTouchHelper itemHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.START | ItemTouchHelper.END) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //é usado para operações drag and drop
                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();

                LivroAdapterRecycler adapter = (LivroAdapterRecycler) recyclerView.getAdapter();

                adapter.mover(fromPosition, toPosition);
                return true;// true se moveu, se não moveu, retorne falso
            }

            @Override
            public boolean isLongPressDragEnabled() {
                //return false; se quiser, é possivel desabilitar o drag and drop
                return true;
            }

            @Override
            public boolean isItemViewSwipeEnabled() {
                //return false; se quiser, é possivel desabilitar o swipe
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int posicao = viewHolder.getAdapterPosition();
                LivroAdapterRecycler adapterRecycler = (LivroAdapterRecycler) recyclerView.getAdapter();
                adapterRecycler.removerComTempo(posicao);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                View itemView = viewHolder.itemView;
                Drawable background = new ColorDrawable(Color.RED);

                // not sure why, but this method get's called for viewholder that are already swiped away
                if (viewHolder.getAdapterPosition() == -1) {
                    // not interested in those
                    return;
                }

                // draw red background
                background.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
                background.draw(c);

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }


        });

        itemHelper.attachToRecyclerView(recyclerView);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("livro","onRestart");

        listaLivro = bancoHelper.findAll();
        recyclerView.setAdapter(new SlideInLeftAnimationAdapter(new LivroAdapterRecycler(context, listaLivro)));
    }
}
