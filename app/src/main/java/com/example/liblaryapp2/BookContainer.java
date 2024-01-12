package com.example.liblaryapp2;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookContainer {
    @SerializedName("docs")
    private List<Book> bookList;
    public void setBookList(List<Book> books)
    {
        if(books!=null){
            bookList = books;
        }
    }
    public List<Book> getBookList(){
        return bookList;
    }
}
