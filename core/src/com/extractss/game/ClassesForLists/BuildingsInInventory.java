package com.extractss.game.ClassesForLists;

import com.extractss.game.SimpleClasses.Building;

public class BuildingsInInventory extends BasicListItem {
    private Building building;

    public BuildingsInInventory(Building building, float y, float elementHeight) {
        this.building = building;
        this.y = y;
        this.elementHeight = elementHeight;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }
}
