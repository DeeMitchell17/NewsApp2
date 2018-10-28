package com.example.android.newsapp2;

public class Movie {

    private String mTitle;

    private String mSection;

    private String mPublicationDate;

    private String mAuthor;

    private int mImageResourceId;

    private String mUrl;

    public Movie(String section, String author, String title, String publicationDate, String url) {
        mSection = section;
        mAuthor = author;
        mTitle = title;
        mPublicationDate = publicationDate;
        mUrl = url;
    }


    public Movie(String section, String author, String title, String publicationDate, int imageResourceId, String url) {
        mSection = section;
        mAuthor = author;
        mTitle = title;
        mPublicationDate = publicationDate;
        mUrl = url;
        mImageResourceId = imageResourceId;
    }

    public String getSection() {
        return mSection;
    }

    public String getAuthor(){
        return mAuthor;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getPublicationDate() {
        return mPublicationDate;
    }

    public String getUrl() {
        return mUrl;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }
}

