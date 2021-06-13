package com.extractss.game.ClassesForLists;

import com.extractss.game.SimpleClasses.Building;

public class BuildingsOnField {
    private Building building;
    private int i;
    private int j;
    /*
    Переменные 'i' и 'j' нужны для визуального размещения зданий в ячейках поля на экране планеты.
     */

    public BuildingsOnField(Building building, int i, int j) {
        this.building = building;
        this.i = i;
        this.j = j;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }
}
