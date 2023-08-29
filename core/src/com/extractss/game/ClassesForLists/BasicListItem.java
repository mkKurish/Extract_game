package com.extractss.game.ClassesForLists;

abstract public class BasicListItem {
    public float y;
    /*
    Данная переменная 'y' нужна для визуального создания списка на экране магазина.
     */
    protected int inventLvl = 1;

    public int getInventLvl(){
        return inventLvl;
    }
}
