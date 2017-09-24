package br.ufrn.eaj.tads.meuslivros;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import model.Livro;

public class CadastroActivity extends AppCompatActivity {
    EditText titulo;
    EditText autor;
    EditText ano;
    RatingBar nota;
    Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        bundle = getIntent().getExtras();

        titulo=(EditText) findViewById(R.id.titulo);
        autor=(EditText) findViewById(R.id.autor);
        ano=(EditText) findViewById(R.id.ano);
        nota=(RatingBar) findViewById(R.id.nota);


        if(bundle != null){
            titulo.setText(bundle.getString("titulo"));
            autor.setText(bundle.getString("autor"));
            ano.setText(bundle.getString("ano"));
            nota.setRating(bundle.getFloat("nota"));
        }else{
            Log.i("livro", "BUNDLE VAZIO");
        }



    }

    public void salvar(View v){
        Intent i = new Intent();
        BancoHelper db = new BancoHelper(this);
        Livro livro = new Livro(titulo.getText().toString(),
                autor.getText().toString(),
                ano.getText().toString(),
                nota.getRating());
        if(bundle != null){
            livro.setId(bundle.getLong("id"));
        }
        db.save(livro);
        setResult(RESULT_OK, i);
        finish();
    }

    public void cancelar(View v){
        setResult(RESULT_CANCELED);
        finish();
    }
}