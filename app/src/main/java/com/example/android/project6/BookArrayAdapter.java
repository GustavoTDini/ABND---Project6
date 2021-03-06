package com.example.android.project6;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.android.project6.databinding.BookListItemBinding;

import java.util.ArrayList;

/**
 * Created by ssjdini on 3/13/18.
 */

@SuppressWarnings("ALL")
public class BookArrayAdapter extends ArrayAdapter<Book> {

    public BookArrayAdapter(Activity context, ArrayList<Book> books) {
        super(context, 0, books);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Book currentBook = getItem(position);
        assert currentBook != null;

        BookListItemBinding binding = BookListItemBinding.inflate(LayoutInflater.from(getContext()), parent, false);

        binding.setBook(currentBook);

        binding.executePendingBindings();

        return binding.getRoot();
    }
}