package com.boss.milos.pricecounter;

/**
 * Created by MiloS on 26.08.2016.
 */
public class Element {
    private String itemName;
    private Integer count;
    private Double price;

    public Element() {
        this.itemName = "";
        this.count = 0;
        this.price = Double.valueOf(0);
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String drinkName) {
        this.itemName = drinkName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void increaseCount() {
        count++;
    }

    public void decreaseCount() {
        if(count > 0)
            count--;
    }
}
