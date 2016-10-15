package progernapplications.gameofthroneswikiapp;


import java.util.ArrayList;

public class Book
{

    private String name, numberOfPages, publisher, country, authors;

    public Book(String name, String numberOfPages, String publisher,  String country, String authors)
    {
        this.name = name;
        this.numberOfPages = numberOfPages;
        this.publisher = publisher;
        this.country = country;
        this.authors = authors;
    }

    public String getName() { return name; }
    public String getPages() { return numberOfPages; }
    public String getCountryOfRelease() { return  country; }
    public String getPublisher() {return publisher;}
    public String getAuthorsList() {return authors;}



}
