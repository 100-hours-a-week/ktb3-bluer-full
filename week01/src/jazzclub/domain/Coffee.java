package jazzclub.domain;

public class Coffee extends Drink {
    private final String beanType;

    public Coffee(String name, int price, String beanType) {
        super(name, price);
        this.beanType = beanType;
    }

    private String getBeanType() {
        return this.beanType;
    }

    public String getDetail() {
        return "원두: " + this.getBeanType();
    }
}
