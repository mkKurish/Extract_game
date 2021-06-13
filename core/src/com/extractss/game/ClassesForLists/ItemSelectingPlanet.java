package com.extractss.game.ClassesForLists;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import javax.xml.soap.Text;

public class ItemSelectingPlanet {
    private String name;
    private TextureRegion picture;

    private int costMoney;
    private int costMetal;
    private int costEnergy;

    private int inventLvl;

    public float y;
    /*
    Данная переменная 'y' нужна для визуального создания списка на экране исследований.
     */

    public ItemSelectingPlanet(String name, TextureRegion picture, int costMoney, int costMetal, int costEnergy, int inventLvl, float y) {
        this.name = name;
        this.picture = picture;
        this.costMoney = costMoney;
        this.costMetal = costMetal;
        this.costEnergy = costEnergy;
        this.inventLvl = inventLvl;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TextureRegion getPicture() {
        return picture;
    }

    public void setPicture(TextureRegion picture) {
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

    public int getInventLvl() {
        return inventLvl;
    }

    public void setInventLvl(int inventLvl) {
        this.inventLvl = inventLvl;
    }
}
