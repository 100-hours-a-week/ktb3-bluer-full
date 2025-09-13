package jazzclub;

public class Cocktail extends Drink {
    private float alcoholPercentage;

    public Cocktail(String name, int price, float alcoholPercentage) {
        super(name, price);
        this.alcoholPercentage = alcoholPercentage;
    }

    public float getAlcoholPercentage() {
        return this.alcoholPercentage;
    }

    public String getDetail() {
        return "도수: " + this.getAlcoholPercentage() + "%";
    }
}
