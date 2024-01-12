package com.example.liblaryapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{

    public static final String EXTRA_DETAILS_BOOK = "pb.edu.pl.EDIT_BOOK_TITLE";
    public static final int DETAILS_BOOK_ACTIVITY_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);

        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                fetchBookData(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void fetchBookData(String s) {
        String finalQuery = prepareQuery(s);
        BookService bookService = RetrofitInstance.getRetrofitInstance().create(BookService.class);

        Call<BookContainer> booksApiCall = bookService.findBooks(finalQuery);

        booksApiCall.enqueue(new Callback<BookContainer>() {
            @Override
            public void onResponse(Call<BookContainer> call, Response<BookContainer> response) {
                if(response.body()!=null){
                    setupBookListView(response.body().getBookList());
                }
            }

            @Override
            public void onFailure(Call<BookContainer> call, Throwable t) {
                Snackbar.make(findViewById(R.id.main_view),"Something went wrong... Please try later!", BaseTransientBottomBar.LENGTH_LONG).show();
            }
        });
    }

    private void setupBookListView(List<Book> books){
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final BookAdapter adapter = new BookAdapter();
        adapter.setBooks(books);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    private String prepareQuery(String query){
        String[] queryParts = query.split("\\s+");
        return TextUtils.join("+", queryParts);
    }


    private class BookHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private static final String IMAGE_URLBASE = "http://covers.openlibrary.org/b/id/";
        private final TextView bookTitleTextView;
        private final TextView bookAuthorTextView;
        private final TextView numberOfPagesTextView;
        private final ImageView bookCover;
        private Book book;

        public BookHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.book_list_item,parent,false));
            bookTitleTextView=itemView.findViewById(R.id.book_title);
            bookAuthorTextView=itemView.findViewById(R.id.book_author);
            numberOfPagesTextView=itemView.findViewById(R.id.number_of_pages);
            bookCover = itemView.findViewById(R.id.img_cover);
            itemView.setOnClickListener(this);
        }
        public void bind(Book book){
            if(book!=null && checkNullOrEmpty(book.getTitle())){
                this.book=book;
                bookTitleTextView.setText(book.getTitle());
                if(book.getAuthors()!= null){
                    bookAuthorTextView.setText(TextUtils.join(", ", book.getAuthors()));
                }
                numberOfPagesTextView.setText(book.getNumberOfPages());
                if(book.getCover()!= null){
                    Picasso.with(itemView.getContext())
                            .load(IMAGE_URLBASE + book.getCover()+"-S.jpg")
                            .placeholder(R.drawable.baseline_book_24).into(bookCover);
                }else{
                    bookCover.setImageResource(R.drawable.baseline_book_24);
                }
            }
        }
        private boolean checkNullOrEmpty(String title) {
            return title != null && !title.isEmpty();
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, BookDetailsActivity.class);
            intent.putExtra(EXTRA_DETAILS_BOOK, this.book);
            startActivityForResult(intent, DETAILS_BOOK_ACTIVITY_REQUEST_CODE);
        }
    }
    private class BookAdapter extends RecyclerView.Adapter<BookHolder>{

        private List<Book> books;
        @NonNull
        @Override
        public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new BookHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull BookHolder holder, int position) {
            if(books != null){
                Book book = books.get(position);
                holder.bind(book);
            }else{
                Log.d("MainActivity", " no books");
            }
        }
        void setBooks(List<Book> books){
            this.books = books;
            notifyDataSetChanged();
        }
        @Override
        public int getItemCount() {
            return books.size();
        }
    }
}