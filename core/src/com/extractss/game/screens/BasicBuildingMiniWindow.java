package com.extractss.game.screens;

import static com.extractss.game.ExtractSolarSys.buttonDownSound;
import static com.extractss.game.ExtractSolarSys.buttonUpSound;
import static com.extractss.game.ExtractSolarSys.downNinePatch;
import static com.extractss.game.ExtractSolarSys.successSound;
import static com.extractss.game.ExtractSolarSys.upNinePatch;
import static com.extractss.game.utils.Operations.isEnableToBuy;
import static com.extractss.game.utils.Operations.isInPlace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.extractss.game.SimpleClasses.Building;

abstract public class BasicBuildingMiniWindow extends BasicScreen {
    protected Texture texture;
    protected TextureRegion picTextureRegion;
    Building building;
    protected static float titleX;
    protected static float titleY;
    protected static float frameX;
    protected static float frameWidth;
    protected static float frameY;
    protected static float frameHigh;
    protected static float firstResourceYMoney;
    protected static float firstResourceYMetal;
    protected static float firstResourceYEnergy;
    protected static float firstResourcePicYMoney;
    protected static float firstResourcePicYMetal;
    protected static float firstResourcePicYEnergy;

    protected static float inventLvlBuildingY;
    protected static float inventLvlBuildingPicY;

    protected static float secondResourceYMoney;
    protected static float secondResourceYMetal;
    protected static float secondResourceYEnergy;
    protected static float secondResourcePicYMoney;
    protected static float secondResourcePicYMetal;
    protected static float secondResourcePicYEnergy;
    protected static float resourceHighWidth;
    protected static float resourceX;
    protected static float resourcePicX;
    protected static float costY;
    protected static float productivityY;
    protected static float picX;
    protected static float picY;
    protected static float picWidth;
    protected static float picHigh;
    protected static float cancelX;
    protected static float cancelY;
    protected int buyAbleButton = -1;
    @Override
    protected void checkButtonTouches() {
        for (int i = 0; i < myButtons.size(); i++) {
            myButton = myButtons.get(i);
            if (Gdx.input.isTouched()) {
                lastTouchTime = System.currentTimeMillis();
                touchedX = Gdx.input.getX();
                touchedY = Gdx.graphics.getHeight() - Gdx.input.getY();
                if (isInPlace(touchedX, touchedY, myButton) || i == buyAbleButton && !isEnableToBuy(user, building)) {
                    downNinePatch.draw(batch, myButton.getX1(), myButton.getY1(), myButton.getWidth(),
                            myButton.getHeight());
                    if (!myButton.isPressedToSound() && (isEnableToBuy(user, building) || i != buyAbleButton)) {
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
                if (isInPlace(touchedX, touchedY, myButton)
                        && lastTouchTime != 0
                        && (i != buyAbleButton || isEnableToBuy(user, building))) {
                    if (myButton.isPressedToSound() && i == buyAbleButton) {
                        successSound.play(user.getSoundsVolume());
                        myButton.setPressedToSound(false);
                    } else if (myButton.isPressedToSound() && i != buyAbleButton) {
                        buttonUpSound.play(user.getSoundsVolume());
                        myButton.setPressedToSound(false);
                    }
                    downNinePatch.draw(batch, myButton.getX1(), myButton.getY1(), myButton.getWidth(),
                            myButton.getHeight());
                    this.buttonActivated(i);
                    touchedX = touchedY = -1;
                } else {
                    if (i != buyAbleButton || isEnableToBuy(user, building)) {
                        upNinePatch.draw(batch, myButton.getX1(), myButton.getY1(), myButton.getWidth(),
                                myButton.getHeight());
                    } else {
                        downNinePatch.draw(batch, myButton.getX1(), myButton.getY1(), myButton.getWidth(),
                                myButton.getHeight());
                    }
                }
            }
        }
    }
}
