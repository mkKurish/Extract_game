package com.extractss.game.SimpleClasses;

public class MyButtons{
    private float x1;
    private float width;
    private float y1;
    private float height;
    private boolean isPressedToSound;

    public MyButtons(float x1, float width, float y1, float height) {
        this.x1 = x1;
        this.width = width;
        this.y1 = y1;
        this.height = height;
        isPressedToSound = false;
    }

    public boolean isPressedToSound() {
        return isPressedToSound;
    }

    public void setPressedToSound(boolean pressedToSound) {
        isPressedToSound = pressedToSound;
    }

    public float getX1() {
        return x1;
    }

    public void setX1(float x1) {
        this.x1 = x1;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getY1() {
        return y1;
    }

    public void setY1(float y1) {
        this.y1 = y1;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
