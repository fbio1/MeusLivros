package br.ufrn.eaj.tads.meuslivros;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import model.Livro;

public class AutoCompleteActivity extends AppCompatActivity {
    BancoHelper b = new BancoHelper(this);
    Livro lv;
    String[] livros;
    ArrayList<Livro> listaLivro;
    ArrayAdapter<String> adaptador;

    TextView titulo;
    TextView autor;
    TextView ano;
    TextView nota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        titulo=(TextView) findViewById(R.id.textView12);
        autor=(TextView) findViewById(R.id.textView13);
        ano=(TextView) findViewById(R.id.textView11);
        nota=(TextView) findViewById(R.id.textView9);

        listaLivro = (ArrayList<Livro>) b.findAll();
        livros = new String[listaLivro.size()];

        int i = 0;
        for (Livro l: listaLivro){
            livros[i] = l.getTitulo();
            i++;
        }

        android.widget.AutoCompleteTextView autoCompleteFrutas = (android.widget.AutoCompleteTextView) findViewById(R.id.autoComplete);
        adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, livros);
        autoCompleteFrutas.setAdapter(adaptador);

        autoCompleteFrutas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(PrincipalActivity.this, ((TextView) view).getText().toString() +"item="+i+"col="+l   , Toast.LENGTH_SHORT).show();
                //Toast.makeText(AutoCompleteActivity.this, adaptador.getItem(i).toString(), Toast.LENGTH_SHORT).show();
                lv =  b.findBytitulo(adaptador.getItem(i).toString());
                Log.i("teste", String.valueOf(lv.getId()));
                Log.i("teste", String.valueOf(lv.getTitulo()));
                Log.i("teste", String.valueOf(lv.getAutor()));
                Log.i("teste", String.valueOf(lv.getAno()));
                Log.i("teste", String.valueOf(lv.getNota()));
                titulo.setText(lv.getTitulo());
                autor.setText(lv.getAutor());
                ano.setText(lv.getAno());
                nota.setText(""+lv.getNota());

            }
        });
    }
}