package com.extractss.game.SimpleClasses;

public class User {

    private int money;
    private int energy;
    private int metal;
    private int invents;
    private float soundsVolume;
    private float musicVolume;
    private boolean soundsActive;
    private boolean musicActive;

    public User(int money, int metal,  int energy,int invents) {
        this.money = money;
        this.energy = energy;
        this.metal = metal;
        this.invents = invents;
        this.soundsVolume = 0.4f;
        this.musicVolume = 0.4f;
        this.soundsActive = true;
        this.musicActive = true;
    }

    public User() {
        this.money = 0;
        this.energy = 0;
        this.metal = 0;
        this.invents = 0;
        this.soundsVolume = 0.4f;
        this.musicVolume = 0.4f;
        this.soundsActive = true;
        this.musicActive = true;
    }

    public boolean isSoundsActive() {
        return soundsActive;
    }

    public void setSoundsActive(boolean soundsActive) {
        this.soundsActive = soundsActive;
    }

    public boolean isMusicActive() {
        return musicActive;
    }

    public void setMusicActive(boolean musicActive) {
        this.musicActive = musicActive;
    }

    public float getSoundsVolume() {
        return soundsVolume;
    }

    public void setSoundsVolume(float soundsVolume) {
        this.soundsVolume = soundsVolume;
    }

    public float getMusicVolume() {
        return musicVolume;
    }

    public void setMusicVolume(float musicVolume) {
        this.musicVolume = musicVolume;
    }

    public int getInvents() {
        return invents;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getMetal() {
        return metal;
    }

    public void setMetal(int metal) {
        this.metal = metal;
    }

    public void addInvents(){
        invents++;
    }
}
