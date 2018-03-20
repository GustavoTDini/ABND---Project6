package com.example.android.project6;

/**
 * Created by ssjdini on 3/13/18.
 */

@SuppressWarnings("ALL")
public class Book {

    /**
     * String que contem o Título do Livro
     */
    private String mBookTitle;

    /**
     * String que contem o Autor do Livro
     */
    private String mBookAuthor;

    /**
     * String que contem o URL para informações sobre o Livro
     */
    private String mBookURL;

    /**
     * Contrutor da Classe Book
     */
    public Book(String bookTitle, String bookAuthor, String bookURL) {
        this.mBookTitle = bookTitle;
        this.mBookAuthor = bookAuthor;
        this.mBookURL = bookURL;
    }

    public String getBookTitle() {
        return mBookTitle;
    }

    public String getBookAuthor() {
        return mBookAuthor;
    }

    public String getBookURL() {
        return mBookURL;
    }
}