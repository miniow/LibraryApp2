package com.example.liblaryapp2;

import static com.example.liblaryapp2.MainActivity.EXTRA_DETAILS_BOOK;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class BookDetailsActivity extends AppCompatActivity {
    private Book recievedBook;

    private ImageView cover;
    private TextView textViewTitle;
    private TextView textViewAuthors;
    private TextView textViewSubtitle;
    private TextView textViewFirstSentence;
    private TextView textViewPublishDate;
    private TextView textViewTags;
   private static final String IMAGE_URLBASE = "http://covers.openlibrary.org/b/id/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        cover = findViewById(R.id.img_cover);
        textViewTitle = findViewById(R.id.titleTextView);
        textViewAuthors = findViewById(R.id.authorsTextView);
        textViewSubtitle = findViewById(R.id.subtitleTextView);
        textViewFirstSentence = findViewById(R.id.firstSentenceTextView);
        textViewPublishDate = findViewById(R.id.publishDateTextView);
        textViewTags = findViewById(R.id.tagsTextView);



        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_DETAILS_BOOK)){
            recievedBook = (Book) intent.getSerializableExtra(EXTRA_DETAILS_BOOK);
            if(recievedBook!=null){
                textViewTitle.setText(recievedBook.getTitle());
                if(recievedBook.getCover()!= null){
                    Picasso.with(cover.getContext())
                            .load(IMAGE_URLBASE + recievedBook.getCover()+"-S.jpg")
                            .placeholder(R.drawable.baseline_book_24).into(cover);
                }else{
                    cover.setImageResource(R.drawable.baseline_book_24);
                }
                if(recievedBook.getAuthors()!= null){
                    textViewAuthors.setText(TextUtils.join(", ", recievedBook.getAuthors()));
                }
                if(recievedBook.getFirstSentence()!=null){
                    textViewFirstSentence.setText(TextUtils.join(", ",recievedBook.getFirstSentence()));
                }
                textViewSubtitle.setText(recievedBook.getSubtitle());
                textViewPublishDate.setText(recievedBook.getPublishDate().stream().findFirst().toString());
                if(recievedBook.getSubjects()!=null){
                    textViewTags.setText(TextUtils.join("\n",recievedBook.getSubjects()));
                }
            }

        }
    }
}
