package com.extractss.game.ClassesForLists;

import com.extractss.game.SimpleClasses.Building;

public class BuildingsInInventory {
    private Building building;
    private float y;
    /*
    Данная переменная 'y' нужна для визуального создания списка на экране инвентаря.
     */

    public BuildingsInInventory(Building building, float y) {
        this.building = building;
        this.y = y;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
