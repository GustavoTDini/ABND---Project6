package com.example.android.project6;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ssjdini on 3/13/18.
 */

final class BookUtilities {

    /** Tag for the log messages */
    private static final String LOG_TAG = ListActivity.class.getSimpleName();

    public BookUtilities() {
    }

    /**
     * Retorna um Objeto URL a partir de Uma String.
     */
    private static URL createUrl(String stringUrl) {
        URL url;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG, "Error with creating URL", exception);
            return null;
        }
        return url;
    }

    /**
     * Faz Uma SolicitaçAo de Http e retorna uma String com a informação do JSon
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else{
                Log.e( LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }

        } catch (IOException e) {
            Log.e(LOG_TAG, "");
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Converte o  {@link InputStream} em uma String que contem a resposta JSON do servidor
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


    /**
     * Returna a lista dos {@link Book} que foram retirados do resposta JSON criado pela URL
     */
    private static List<Book> extractFeatureFromJson(String bookJson, int selectedListSize) {

        if (TextUtils.isEmpty(bookJson)){
            return null;
        }

        Log.i( "TAG", "extractFeatureFromJson: " + selectedListSize );

        // Cria uma nova lista vazia aonde iremos colocar os Livros obtidos pelo JSON
        List<Book> books = new ArrayList<>();

        // Tenta Retirar as respostas do JSON, caso não consiga, por algum problema, o aplicativo irá jogar uma excessão e mostrar a
        // mensagem de Log, ao  inves de travar
        try {

            JSONObject root = new JSONObject(bookJson);

            JSONArray bookItemsArray = root.getJSONArray("items");

            int listSize = bookItemsArray.length();

            //define o tamanho da lista de acordo com a opção no inicio e com o total de
            if (listSize > selectedListSize){
                listSize = selectedListSize;
            }

            Log.i( "TAGAfter", "extractFeatureFromJson: " + listSize );

            for (int bookIndex = 0; bookIndex < listSize; bookIndex ++){

                JSONObject thisBookItem = bookItemsArray.getJSONObject(bookIndex);
                JSONObject thisBookVolumeInfo= thisBookItem.getJSONObject( "volumeInfo");

                String bookTitle = thisBookVolumeInfo.getString("title");
                JSONArray authors = thisBookVolumeInfo.getJSONArray("authors");
                String bookAuthor = "";
                for(int authorIndex = 0; authorIndex < authors.length(); authorIndex ++) {
                    bookAuthor += authors.getString(authorIndex) + " ";
                }

                String url = thisBookVolumeInfo.getString( "infoLink" );

                books.add( new Book( bookTitle, bookAuthor , url));
            }

        } catch (JSONException e) {
            // Se um erro for encontrado, ele irá jogar uma excessão e mostrar a mensagem abaixo
            // assim o app não irá travar
            Log.e("BookUtilities", "Problem parsing the book JSON results", e);
        }

        // Returna a lista com os Livros
        return books;
    }

    static List<Book> fetchBookData(String requestURL, int listSize){

        URL urlRequest = createUrl(  requestURL);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(urlRequest);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        Log.i( "JSon", "fetchBookData: "  + extractFeatureFromJson( jsonResponse , listSize));

        return extractFeatureFromJson( jsonResponse, listSize);
    }
}