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

    private String tvSeries;
    //private String povBooks;


    public Character(String name, String title, String culture, String born,
    String died, String father, String mother, String spouse,  String tvSeries) // ADD POVBOOKS IN CONSTRUCTOR LATER
    {
        this.name = name;
        this.title = title;
        this.culture = culture;
        this.born = born;
        this.died = died;
        this.father = father;
        this.mother = mother;
        this.spouse = spouse;
        //this.povBooks = povBooks;
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



    public String getTvSeries()
    {
        return tvSeries;
    }


}
