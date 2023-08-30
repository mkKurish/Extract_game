package com.extractss.game.ClassesForLists;

public class ItemShop extends BasicListItem{

    private String name;

    private int costMoney;
    private int costMetal;
    private int costEnergy;
    private int outputMoney;
    private int outputMetal;
    private int outputEnergy;

    public ItemShop(String name, int costMoney, int costMetal, int costEnergy, int outputMoney,
                    int outputMetal, int outputEnergy, float y, float elementHeight) {
        this.name = name;
        this.costMoney = costMoney;
        this.costMetal = costMetal;
        this.costEnergy = costEnergy;
        this.outputMoney = outputMoney;
        this.outputMetal = outputMetal;
        this.outputEnergy = outputEnergy;
        this.y = y;
        this.elementHeight = elementHeight;
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
}
