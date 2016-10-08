package progernapplications.gameofthroneswikiapp;

/**
 * Created by Олег-PC on 06.10.2016.
 */
public class Character
{

    //Main information
    private String name, title,culture, born, died;

    // Family information

    private String father, mother, spouse;

    // Secondary information

    private String povBooks, tvSeries;
    private String playedBy;

    public Character(String name, String title, String culture, String born,
    String died, String father, String mother, String spouse, String povBooks,  String tvSeries, String playedBy)
    {
        this.name = name;
        this.title = title;
        this.culture = culture;
        this.born = born;
        this.died = died;
        this.father = father;
        this.mother = mother;
        this.spouse = spouse;
        this.povBooks = povBooks;
        this.tvSeries = tvSeries;
        this.playedBy = playedBy;


    }

    public String getName()
    {
       return name;
    }

    public String getTitle()
    {
        return title;
    }

    public String getCulture()
    {
        return culture;
    }

    public String getBornDate()
    {
        return born;
    }

    public String getDeathName()
    {
        return died;
    }

    public String getFather()
    {
        return father;
    }

    public String getMother()
    {
        return mother;
    }

    public String getSpouse()
    {
        return spouse;
    }

    public String getListOfBooks()
    {
        return povBooks;
    }

    public String getTvSeries()
    {
        return tvSeries;
    }

    public String getActor()
    {
        return playedBy;
    }













}
