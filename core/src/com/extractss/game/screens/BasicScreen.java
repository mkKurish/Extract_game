package com.extractss.game.screens;

import static com.extractss.game.ExtractSolarSys.buttonDownSound;
import static com.extractss.game.ExtractSolarSys.buttonUpSound;
import static com.extractss.game.ExtractSolarSys.downNinePatch;
import static com.extractss.game.ExtractSolarSys.upNinePatch;
import static com.extractss.game.utils.Operations.isInPlace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.extractss.game.ExtractSolarSys;
import com.extractss.game.SimpleClasses.MyButtons;
import com.extractss.game.SimpleClasses.User;

import java.util.ArrayList;

public abstract class BasicScreen implements Screen {
    User user;
    ExtractSolarSys sys;
    protected Batch batch;
    protected ArrayList<MyButtons> myButtons;
    protected MyButtons myButton;
    protected long lastAnimationTime;
    protected int curScreenAnimation = 0;
    protected float touchedX;
    protected float touchedY;
    protected long lastTouchTime = 0;

    @Override
    public void show() {

    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    protected void checkButtonTouches(){
        for (int i = 0; i < myButtons.size(); i++) {
            myButton = myButtons.get(i);
            if (Gdx.input.isTouched()) {
                lastTouchTime = System.currentTimeMillis();
                touchedX = Gdx.input.getX();
                touchedY = Gdx.graphics.getHeight() - Gdx.input.getY();
                if (isInPlace(touchedX, touchedY, myButton)) {
                    downNinePatch.draw(batch, myButton.getX1(), myButton.getY1(), myButton.getWidth(),
                            myButton.getHeight());
                    if (!myButton.isPressedToSound()) {
                        buttonDownSound.play(user.getSoundsVolume());
                        myButton.setPressedToSound(true);
                    }
                } else {
                    upNinePatch.draw(batch, myButton.getX1(), myButton.getY1(), myButton.getWidth(),
                            myButton.getHeight());
                    if (myButton.isPressedToSound()) {
                        buttonUpSound.play(user.getSoundsVolume());
                        myButton.setPressedToSound(false);
                    }
                }
            } else {
                if (isInPlace(touchedX, touchedY, myButton) && lastTouchTime != 0) {
                    if (myButton.isPressedToSound()) {
                        buttonUpSound.play(user.getSoundsVolume());
                        myButton.setPressedToSound(false);
                    }
                    downNinePatch.draw(batch, myButton.getX1(), myButton.getY1(), myButton.getWidth(),
                            myButton.getHeight());
                    this.buttonActivated(i);
                    touchedX = touchedY = -1;
                } else {
                    upNinePatch.draw(batch, myButton.getX1(), myButton.getY1(), myButton.getWidth(),
                            myButton.getHeight());
                }
            }
        }
    }

    abstract void buttonActivated(int i);
}
