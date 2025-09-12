package jazzclub;

public class Coffee extends Drink {
    private String beanType;

    public Coffee(String name, int price, String beanType) {
        super(name, price);
        this.beanType = beanType;
    }

    public String getBeanType() {
        return this.beanType;
    }
}
