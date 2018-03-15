package com.example.android.project6;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity {

    /** Botão que irá chamar a lista */
    Button searchButton;

    /** EditText que contem o termo de Busca */
    EditText searchText;

    /** EditText que contem o tamanho da lista */
    EditText listNumber;

    /** Int Final com o tamanho maximo da lista*/
    static final int MAX_LIST_SIZE = 40;

    /** Strings Final para montar  a URL do API*/
    static final String URL = "https://www.googleapis.com/books/v1/volumes?q={";
    static final String MAX_RESULTS = "}&maxResults=40";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Associa os elementos criados aos respectivos elementos do layout XML
        searchButton = findViewById(R.id.search_button);
        searchText = findViewById(R.id.search_text);
        listNumber = findViewById(R.id.list_size);

        /** clickListener que define a ação do botão de busca, caso o editText de busca esteja vazio ele cria um Toast alertando
         * mas se tiver um termo de busca, ele abre umn intent, e manda a URL já modificada para criar a lista de livros
         * se não for escolhido o tamanho da lista, o máxima de 40 será fixad*/
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchterm = searchText.getText().toString();

                // se não houver termo de busca criamos um toast para alertar o  usuário da falta do termo
                if (searchterm.equals( "" )){
                    String toastText = getString(R.string.no_search_term_toast);
                    Toast noSearchToast = Toast.makeText(getApplicationContext(),toastText, Toast.LENGTH_LONG);
                    noSearchToast.show();
                } else{
                    //se estiver tudo certo ele cria um novo intent e passa a URL atraves de um extra
                    Intent bookListActivity = new Intent(getBaseContext(), ListActivity.class);
                    String searchTopic = searchText.getText().toString();
                    String URLFinal = URL + searchTopic + MAX_RESULTS;
                    Log.i( "URL", "onClick: "+ URLFinal );
                    bookListActivity.putExtra("URLString",URLFinal);

                    // Define o valor do tamanho da lista em 40
                    int listSize = MAX_LIST_SIZE;

                    // define o tamanho da lista selecionada, se não tiver nada selecionado, intui que o usuario
                    // quer todos os livros e mantem o tamanho maxima da lista de 40
                    if ( !(TextUtils.isEmpty( listNumber.getText().toString()))) {
                        listSize = Integer.parseInt( listNumber.getText().toString() );
                    }
                    bookListActivity.putExtra("ListSize", listSize);
                    startActivity(bookListActivity);
                }
            }
        });
    }

}