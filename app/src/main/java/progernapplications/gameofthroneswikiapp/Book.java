package progernapplications.gameofthroneswikiapp;

public class Book
{

    private String name;
    private String numberOfPages;
    private String publisher;
    private String country;
    private String authors;

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
