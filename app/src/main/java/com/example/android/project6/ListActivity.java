package com.example.android.project6;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    /**
     * Strings para levar os elementos para o list intent
     */
    private static final String URL_STRING = "URLString";
    private static final String LIST_SIZE = "ListSize";
    /**
     * Valor constante para o ID do loader de book.
     */
    private static final int BOOK_LOADER_ID = 1;
    /**
     * Adapter da lista de earthquakes
     */
    private BookArrayAdapter adapter;
    /**
     * TextView que é mostrada quando a lista encontra um erro
     */
    private TextView errorStateTextView;
    /**
     * ProgressBar que é mostrada enquanto a conexão é realizada
     */
    private ProgressBar loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // cria uma Intent que receberá os valores de extra recebidos pela SearchActivity
        Intent intent = getIntent();
        Bundle args = intent.getExtras();

        //Define a list view que receberá os Books
        ListView bookListView = findViewById(R.id.list);

        // cria um novo ArrayAdapter de Book
        adapter = new BookArrayAdapter(this, new ArrayList<Book>());

        // Associa os elementos criados aos respectivos elementos do layout XML
        loadingBar = findViewById(R.id.loading_spinner);
        errorStateTextView = findViewById(R.id.error_messages);

        bookListView.setAdapter(adapter);
        bookListView.setEmptyView(errorStateTextView);

        // Obtém uma referência ao ConectivityManager para testar a conexão
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            // Obtém uma referência ao LoaderManager, a fim de interagir com loaders.
            LoaderManager loaderManager = getLoaderManager();
            // Inicializa o loader. Passa um ID constante int definido acima e passa nulo para
            // o bundle. Passa esta activity para o parâmetro LoaderCallbacks (que é válido
            // porque esta activity implementa a interface LoaderCallbacks).
            loaderManager.initLoader(BOOK_LOADER_ID, args, this);
        } else {
            // Caso não haja conexão, a mensagem de erro aparecerá
            loadingBar.setVisibility(View.GONE);
            errorStateTextView.setText(R.string.no_internet_connection);
        }

        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book thisBook = adapter.getItem(position);
                assert thisBook != null;
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(thisBook.getBookURL()));
                startActivity(browserIntent);
            }
        });

    }

    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        String url = bundle.getString(URL_STRING);
        int listSize = bundle.getInt(LIST_SIZE);
        return new BookLoader(this, url, listSize);
    }

    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        // Seta o texto de estado vazio para mostrar "Nenhum terremoto encontrado."
        errorStateTextView.setText(R.string.no_book_related_to_the_search);

        // Limpa o adapter de dados de earthquake anteriores
        adapter.clear();

        loadingBar.setVisibility(View.GONE);

        // Se há uma lista válida de {@link Book}s, então os adiciona ao data set do adapter.
        // Isto ativará a atualização da ListView.
        if (books != null && !books.isEmpty()) {
            adapter.addAll(books);
        }
    }

    public void onLoaderReset(Loader<List<Book>> loader) {
        // Reseta o Loader, então podemos limpar nossos dados existentes.
        adapter.clear();
    }
}