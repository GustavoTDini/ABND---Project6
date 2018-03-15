package com.example.android.project6;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.android.project6.databinding.BookListItemBinding;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ssjdini on 3/13/18.
 */

public class BookArrayAdapter extends ArrayAdapter<Book> {

    public BookArrayAdapter(Activity context, ArrayList<Book> books){
        super (context, 0, books);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Book currentBook = getItem( position );
        assert currentBook != null;

        BookListItemBinding binding = BookListItemBinding.inflate(LayoutInflater.from(getContext()), parent, false);

        binding.setBook(currentBook);

        binding.executePendingBindings();

        return binding.getRoot();
    }
}