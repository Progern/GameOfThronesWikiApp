package progernapplications.gameofthroneswikiapp;


public class House {

    private String name;
    private String region;
    private String words;
    private String seat;
    private String ancestralWeapons;

    public House(String name, String region, String words, String seat, String ancestralWeapons) {
        this.name = name;
        this.region = region;
        this.words = words;
        this.seat = seat;
        this.ancestralWeapons = ancestralWeapons;
    }

    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    public String getWords() {
        return words;
    }

    public String getSeat() {
        return seat;
    }

    public String getWeapons() {
        return ancestralWeapons;
    }

}
