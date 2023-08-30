package com.extractss.game.SimpleClasses;

import com.badlogic.gdx.graphics.Texture;
import com.extractss.game.ClassesForLists.BasicListItem;

public class Building extends BasicListItem {

    private String name;
    private Texture picture;

    private int costMoney;
    private int costMetal;
    private int costEnergy;
    private int usefulMoney;
    private int usefulMetal;
    private int usefulEnergy;
    private int inventLvl;
    private int buildingLvl;

    private boolean productiveType;
    /*
    true - productive type
    false - storage type
     */

    public Building(String name, Texture picture, boolean productiveType, int costMoney,
                    int costMetal, int costEnergy, int usefulMoney, int usefulMetal,
                    int usefulEnergy, int inventLvl, float y, float elementHeight) {
        this.name = name;
        this.picture = picture;
        this.productiveType = productiveType;
        this.costMoney = costMoney;
        this.costMetal = costMetal;
        this.costEnergy = costEnergy;
        this.usefulMoney = usefulMoney;
        this.usefulMetal = usefulMetal;
        this.usefulEnergy = usefulEnergy;
        this.inventLvl = inventLvl;
        this.y = y;
        this.elementHeight = elementHeight;
        this.buildingLvl = 0;
    }

    public Building(String name, boolean productiveType, int costMoney, int costMetal,
                    int costEnergy, int usefulMoney, int usefulMetal, int usefulEnergy,
                    int inventLvl, float y, float elementHeight) {
        this.name = name;
        this.productiveType = productiveType;
        this.picture = new Texture("buildings\\" + name + ".png");
        this.costMoney = costMoney;
        this.costMetal = costMetal;
        this.costEnergy = costEnergy;
        this.usefulMoney = usefulMoney;
        this.usefulMetal = usefulMetal;
        this.usefulEnergy = usefulEnergy;
        this.inventLvl = inventLvl;
        this.y = y;
        this.elementHeight = elementHeight;
        this.buildingLvl = 0;
    }

    public Building(Building building) {
        this.name = building.name;
        this.productiveType = building.productiveType;
        this.picture = new Texture("buildings\\" + building.name + ".png");
        this.costMoney = building.costMoney;
        this.costMetal = building.costMetal;
        this.costEnergy = building.costEnergy;
        this.usefulMoney = building.usefulMoney;
        this.usefulMetal = building.usefulMetal;
        this.usefulEnergy = building.usefulEnergy;
        this.inventLvl = building.inventLvl;
        this.y = building.y;
        this.elementHeight = building.elementHeight;
        this.buildingLvl = 0;
    }

    public boolean isProductiveType() {
        return productiveType;
    }

    public void setProductiveType(boolean productiveType) {
        this.productiveType = productiveType;
    }

    public int getInventLvl() {
        return inventLvl;
    }

    public void setInventLvl(int inventLvl) {
        this.inventLvl = inventLvl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Texture getPicture() {
        return picture;
    }

    public void setPicture(Texture picture) {
        this.picture = picture;
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

    public int getUsefulMoney() {
        return usefulMoney;
    }

    public void setUsefulMoney(int usefulMoney) {
        this.usefulMoney = usefulMoney;
    }

    public int getUsefulMetal() {
        return usefulMetal;
    }

    public void setUsefulMetal(int usefulMetal) {
        this.usefulMetal = usefulMetal;
    }

    public int getUsefulEnergy() {
        return usefulEnergy;
    }

    public void setUsefulEnergy(int usefulEnergy) {
        this.usefulEnergy = usefulEnergy;
    }

    public int getBuildingLvl() {
        return buildingLvl;
    }

    public void setBuildingLvl(int buildingLvl) {
        this.buildingLvl = buildingLvl;
    }
}
