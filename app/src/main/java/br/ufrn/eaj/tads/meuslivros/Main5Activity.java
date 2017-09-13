package br.ufrn.eaj.tads.meuslivros;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Main5Activity extends AppCompatActivity {
    ListView lista;
    List<Livro> listaLivros = new ArrayList<>();
    BancoHelper b = new BancoHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        lista = (ListView) findViewById(R.id.lista);

        listaLivros = b.findAll();

        lista.setAdapter(new LivroAdapter(this, listaLivros));
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Livro livro = listaLivros.get(i);
                Toast.makeText(Main5Activity.this, ""+livro.getTitulo(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}

