package com.example.android.project6;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by ssjdini on 3/13/18.
 */

public class BookLoader extends AsyncTaskLoader<List<Book>> {

    /**
     * URL da busca
     */
    private String mUrl;

    /**
     * int do tamanho da Lista
     */
    private int mListSize;

    /**
     * Construtor do BookLoader
     */
    public BookLoader(Context context, String url, int listSize) {
        super(context);
        mUrl = url;
        mListSize = listSize;
    }

    /**
     * Metodo que irá iniciar o load em segundo plano ao iniciar a activity
     */
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * Metodo que realizará a busca em segundo plano do metodo
     */
    @Override
    public List<Book> loadInBackground() {
        //Retorna Null se
        if (mUrl == null) {
            return null;
        }

        // Realiza requisição de rede, decodifica a resposta, e extrai uma lista de livros.
        return BookUtilities.fetchBookData(mUrl, mListSize);
    }
}