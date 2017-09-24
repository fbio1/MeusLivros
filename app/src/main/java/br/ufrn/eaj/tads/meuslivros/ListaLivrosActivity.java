package br.ufrn.eaj.tads.meuslivros;

import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.util.List;

import model.Livro;

public class ListaLivrosActivity extends AppCompatActivity {
    private TextView titulo;
    private TextView autor;
    private TextView ano;
    private TextView nota;
    private List<Livro> lista;
    private int livroatual = -1;
    ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        layout = (ConstraintLayout) findViewById(R.id.layout3);
        BancoHelper db = new BancoHelper(this);

        titulo = (TextView) findViewById(R.id.titulo);
        autor = (TextView) findViewById(R.id.autor);
        ano = (TextView) findViewById(R.id.ano);
        nota = (TextView) findViewById(R.id.nota);

        lista = db.findAll();

        for (int i = 0; i < lista.size(); i++) {
            if (i == 0) {
                atualizarParametros(i);
                livroatual = 0;
            }
        }
    }

    private void atualizarParametros(int i) {
        titulo.setText(lista.get(i).getTitulo());
        autor.setText(lista.get(i).getAutor());
        ano.setText(String.valueOf(lista.get(i).getAno()));
        nota.setText(String.valueOf(lista.get(i).getNota()));
    }

    public void proximo(View v) {
        if (livroatual == lista.size() - 1) {
            Snackbar snack = Snackbar.make((View) layout.getParent(), "Você está no último Registro", Snackbar.LENGTH_SHORT);
            snack.show();
        } else {
            atualizarParametros(livroatual + 1);
            livroatual++;
        }
    }

    public void anterior(View v) {
        if (livroatual == 0) {
            Snackbar snack = Snackbar.make((View) layout.getParent(), "Você está no primeiro Registro", Snackbar.LENGTH_SHORT);
            snack.show();
        } else {
            atualizarParametros(livroatual - 1);
            livroatual--;
        }
    }
}
