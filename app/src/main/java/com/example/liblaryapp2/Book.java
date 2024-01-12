package com.example.liblaryapp2;

import android.health.connect.datatypes.StepsCadenceRecord;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Book implements Serializable {
    @SerializedName("title")
    private String title;
    @SerializedName("author_name")
    private List<String> authors;
    @SerializedName("cover_i")
    private String cover;
    @SerializedName("number_of_pages_median")
    private String numberOfPages;
    @SerializedName("first_sentence")
    private List<String> firstSentence;
    @SerializedName("subtitle")
    private String subtitle;
    @SerializedName("publish_date")
    private List<String> publishDate;
    @SerializedName("subject_key")
    private List<String> subjects;

    public void setTitle(String title)
    {
        this.title=title;
    }
    public void setAuthors(List<String> authors)
    {
        this.authors=authors;
    }
    public void setCover(String cover)
    {
        this.cover = cover;
    }
    public void setNumberOfPages(String nop){
        if(nop!=null){
            this.numberOfPages = nop;
        }
    }
    public String getTitle() {
        return title;
    }
    public String getCover(){
        return cover;
    }
    public String getNumberOfPages()
    {
        return numberOfPages;
    }
    public List<String> getAuthors(){
        return authors;
    }

    public List<String> getFirstSentence() {
        return firstSentence;
    }

    public void setFirstSentence(List<String> firstSentence) {
        this.firstSentence = firstSentence;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public List<String> getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(List<String> publishDate) {
        this.publishDate = publishDate;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }
}
