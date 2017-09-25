package br.ufrn.eaj.tads.meuslivros;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import model.Livro;

public class ListViewActivity extends AppCompatActivity {
    android.widget.ListView lista;
    List<Livro> listaLivros = new ArrayList<>();
    BancoHelper b = new BancoHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        lista = (android.widget.ListView) findViewById(R.id.lista);

        listaLivros = b.findAll();

        lista.setAdapter(new LivroAdapterList(this, listaLivros));
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Livro livro = (Livro) adapterView.getAdapter().getItem(i);
               // Toast.makeText(ListViewActivity.this, ""+livro.getTitulo(), Toast.LENGTH_SHORT).show();

                Intent it = new Intent(ListViewActivity.this,CadastroActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("titulo",livro.getTitulo());
                bundle.putString("autor",livro.getAutor());
                bundle.putString("ano",livro.getAno());
                bundle.putFloat("nota", (float) livro.getNota());
                bundle.putLong("id",livro.getId());
                it.putExtras(bundle);
                startActivity(it);
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("livro","onRestart");
        listaLivros = b.findAll();
        lista.setAdapter(new LivroAdapterList(this, listaLivros));
    }
}

