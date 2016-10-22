package progernapplications.gameofthroneswikiapp;

/**
 * Created by Олег-PC on 06.10.2016.
 */
public class Character
{

    //Main information
    private String name, title,culture, born, died;

    private String tvSeries;
    //private String povBooks;


    public Character(String name, String title, String culture, String born,
                     String died, String tvSeries)
    {
        this.name = name;
        this.title = title;
        this.culture = culture;
        this.born = born;
        this.died = died;
        this.tvSeries = tvSeries;

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

    public String getDeathDate()
    {
        return died;
    }

    public String getTvSeries()
    {
        return tvSeries;
    }


}
