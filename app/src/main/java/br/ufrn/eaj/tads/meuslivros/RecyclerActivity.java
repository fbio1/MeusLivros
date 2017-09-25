package br.ufrn.eaj.tads.meuslivros;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

        recyclerView.setAdapter(new SlideInLeftAnimationAdapter(new LivroAdapterRecycler(context, listaLivro)));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnItemTouchListener(new MyRecycler(RecyclerActivity.this, recyclerView, new MyRecycler.OnItemClickListener() {

            @Override
            public void OnItemClick(View view, int i) {
//              Toast.makeText(RecyclerActivity.this, "Clique simples", Toast.LENGTH_SHORT).show();
                Intent it = new Intent(RecyclerActivity.this,CadastroActivity.class);
                Bundle bundle = new Bundle();

                bundle.putString("titulo",listaLivro.get(i).getTitulo());
                bundle.putString("autor",listaLivro.get(i).getAutor());
                bundle.putString("ano",listaLivro.get(i).getAno());
                bundle.putFloat("nota", (float) listaLivro.get(i).getNota());
                bundle.putLong("id",listaLivro.get(i).getId());

                it.putExtras(bundle);

                startActivity(it);

            }

            @Override
            public void OnItemLongClick(View view, final int position) {
                //Toast.makeText(Main5Activity.this, "Clique longo", Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Confimação")
                        .setMessage("Tem certeza que deseja excluir este livro?")
                        .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                boolean sucesso = false;
                                if(sucesso == false) {
                                    final Livro removido = listaLivro.get(position);
                                    listaLivro.remove(position);
                                    bancoHelper.delete(removido);

                                    recyclerView.getAdapter().notifyItemRemoved(position);
                                    Snackbar.make((View)recyclerView.getParent(), "Excluiu!", Snackbar.LENGTH_SHORT)
                                            .setAction("Action", null).show();
                                }else{
                                    Snackbar.make((View)recyclerView.getParent(), "Cancelou a operação!", Snackbar.LENGTH_SHORT)
                                            .setAction("Action", null).show();
                                }

                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .create()
                        .show();

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

        recyclerView.setItemAnimator(new LandingAnimator());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("livro","onRestart");

        listaLivro = bancoHelper.findAll();
        recyclerView.setAdapter(new SlideInLeftAnimationAdapter(new LivroAdapterRecycler(context, listaLivro)));
    }
}
