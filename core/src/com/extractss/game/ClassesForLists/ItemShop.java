package com.extractss.game.ClassesForLists;


import static com.extractss.game.utils.Constants.LIST_ELEMENT_HEIGHT;
import static com.extractss.game.utils.Constants.LIST_SHOP_ELEMENT_HEIGHT;

public class ItemShop {

    private String name;

    private int costMoney;
    private int costMetal;
    private int costEnergy;
    private int outputMoney;
    private int outputMetal;
    private int outputEnergy;

    private float height;
    public float y;
    /*
    Данная переменная 'y' нужна для визуального создания списка на экране магазина.
     */

    public ItemShop(String name, int costMoney, int costMetal, int costEnergy, int outputMoney,
                    int outputMetal, int outputEnergy, float y) {
        this.name = name;
        this.costMoney = costMoney;
        this.costMetal = costMetal;
        this.costEnergy = costEnergy;
        this.outputMoney = outputMoney;
        this.outputMetal = outputMetal;
        this.outputEnergy = outputEnergy;
        this.y = y;
        if (name == "faster click") this.height = LIST_ELEMENT_HEIGHT;
        else this.height = LIST_SHOP_ELEMENT_HEIGHT;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCostMoney() {
        return costMoney;
    }

    public void setCostMoney(int costMoney) {
        this.costMoney = costMoney;
    }

    public int getCostMetal() {
        return costMetal;
    }

    public void setCostMetal(int costMetal) {
        this.costMetal = costMetal;
    }

    public int getCostEnergy() {
        return costEnergy;
    }

    public void setCostEnergy(int costEnergy) {

        this.costEnergy = costEnergy;
    }

    public int getOutputMoney() {
        return outputMoney;
    }

    public void setOutputMoney(int outputMoney) {

        this.outputMoney = outputMoney;
    }

    public int getOutputMetal() {
        return outputMetal;
    }

    public void setOutputMetal(int outputMetal) {

        this.outputMetal = outputMetal;
    }

    public int getOutputEnergy() {
        return outputEnergy;
    }

    public void setOutputEnergy(int outputEnergy) {

        this.outputEnergy = outputEnergy;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
