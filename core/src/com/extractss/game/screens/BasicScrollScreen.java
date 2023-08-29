package com.extractss.game.screens;

import static com.extractss.game.ExtractSolarSys.bitmapFontSmall;
import static com.extractss.game.ExtractSolarSys.inventoryBuildings;
import static com.extractss.game.ExtractSolarSys.progressBarBackNinePatch;
import static com.extractss.game.ExtractSolarSys.progressBarKnobNinePatch;
import static com.extractss.game.ExtractSolarSys.screenManager;
import static com.extractss.game.utils.Constants.APP_HEIGHT;
import static com.extractss.game.utils.Constants.APP_WIDTH;
import static com.extractss.game.utils.Constants.BUTTON_HEIGHT;
import static com.extractss.game.utils.Constants.KNOB_WIDTH;
import static com.extractss.game.utils.Constants.KNOB_X;
import static com.extractss.game.utils.Constants.LIST_ELEMENT_HEIGHT;
import static com.extractss.game.utils.Constants.LIST_HEIGHT;
import static com.extractss.game.utils.Constants.LIST_WIDTH;
import static com.extractss.game.utils.Operations.isInPlaceMain;

import com.badlogic.gdx.Gdx;
import com.extractss.game.ClassesForLists.BasicListItem;
import com.extractss.game.ClassesForLists.BuildingsInInventory;
import com.extractss.game.ClassesForLists.ItemResearch;
import com.extractss.game.ClassesForLists.ItemSelectingPlanet;
import com.extractss.game.ClassesForLists.ItemShop;
import com.extractss.game.ExtractSolarSys;
import com.extractss.game.SimpleClasses.Building;
import com.extractss.game.utils.IncrementResourcesTimeCheck;
import com.sun.org.apache.bcel.internal.classfile.JavaClass;

import java.util.ArrayList;

abstract public class BasicScrollScreen extends BasicScreen {
    protected float knobHeight;
    protected float touchedListY;
    protected float yForIcons;
    protected static float heightForIcons;
    protected static float widthForIcons;
    protected float yForResourcesText;
    protected float xForPriceListElements;
    protected float xForIconsListElements;
    protected static float appWidthToTwentyFour = APP_WIDTH / 24;
    protected float firstElementY;
    protected float lastElementY;
    protected static float boarderUp = APP_HEIGHT - BUTTON_HEIGHT - LIST_ELEMENT_HEIGHT - 2 * bitmapFontSmall.getCapHeight();
    ;
    protected float deltaFirstElementY;
    protected int startedTouchX;
    protected int startedTouchY;
    protected float resCoord = 0;
    protected float moneyTextureX;
    protected float metalTextureX;
    protected float energyTextureX;
    protected float moneyValueX;
    protected float metalValueX;
    protected float energyValueX;
    protected boolean screenWasTouchedJustNow = false;
    protected long lastListTouchTime = 1000;
    IncrementResourcesTimeCheck incrementResourcesTimeCheck;

    protected void scrollTouchMechanic(ArrayList<? extends BasicListItem> arrayList) {
        boolean isBigList = arrayList.size() * LIST_ELEMENT_HEIGHT > LIST_HEIGHT;

        if (arrayList.size() != 0) {
            firstElementY = arrayList.get(0).y;
            lastElementY = arrayList.get(arrayList.size() - 1).y;
        }
        if (isInPlaceMain(Gdx.input.getX(),
                Gdx.graphics.getHeight() - Gdx.input.getY(), 0, BUTTON_HEIGHT,
                LIST_WIDTH, LIST_HEIGHT)) {
            if (Gdx.input.isTouched()) {
                if (screenWasTouchedJustNow) {
                    if (isBigList) {
                        if (System.currentTimeMillis() - lastListTouchTime < 50) {
                            resCoord = (-touchedListY + Gdx.graphics.getHeight() - Gdx.input.getY()) * 2;
                            if (firstElementY + resCoord > BUTTON_HEIGHT) {
                                resCoord -= firstElementY + resCoord - BUTTON_HEIGHT;
                            } else if (lastElementY + resCoord < boarderUp) {
                                resCoord += boarderUp - lastElementY - resCoord;
                            }
                            for (int i = 0; i < arrayList.size(); i++) {
                                arrayList.get(i).y += resCoord;
                            }
                            lastListTouchTime = System.currentTimeMillis() - lastListTouchTime;
                        } else {
                            lastListTouchTime = System.currentTimeMillis();
                        }
                        touchedListY = Gdx.graphics.getHeight() - Gdx.input.getY();
                    }
                } else {
                    startedTouchX = Gdx.input.getX();
                    startedTouchY = Gdx.input.getY();
                    screenWasTouchedJustNow = true;
                }
            } else {
                if (Gdx.input.getX() == startedTouchX && Gdx.input.getY() == startedTouchY) {
                    // List touch
                    for (int i = 0; i < arrayList.size(); i++) {
                        /*
                        Если в режиме нажатий коснулись элемента списка, открываем экран с информацией о здании,
                        где его можно купить (при покупке автоматически появится в инвентаре).
                        */
                        touchedY = Gdx.graphics.getHeight() - Gdx.input.getY();
                        if (touchedY > arrayList.get(i).y
                                && touchedY < arrayList.get(i).y + LIST_ELEMENT_HEIGHT
                                && user.getInvents() >= arrayList.get(i).getInventLvl()) {
                            miniWindowSwitch(arrayList.get(i));
                            break;
                        }
                    }
                }
            }
        }

        if (!Gdx.input.isTouched()) {
            screenWasTouchedJustNow = false;
            if (isBigList) {
                if (lastElementY < boarderUp) {
                    for (int i = 0; i < arrayList.size(); i++) {
                        arrayList.get(i).y += boarderUp - lastElementY;
                    }
                } else if (firstElementY > BUTTON_HEIGHT) {
                    for (int i = 0; i < arrayList.size(); i++) {
                        arrayList.get(i).y += BUTTON_HEIGHT - firstElementY;
                    }
                }
            }
        }
    }

    protected <T extends BasicListItem> void miniWindowSwitch(T building) {
        if (building.getClass().equals(Building.class)) {
            miniWindowActivated((Building) building);
        } else if (building.getClass().equals(BuildingsInInventory.class)) {
            miniWindowActivated((BuildingsInInventory) building);
        } else if (building.getClass().equals(ItemSelectingPlanet.class)) {
            miniWindowActivated((ItemSelectingPlanet) building);
        }
    }

    protected abstract void miniWindowActivated(Building building);

    protected abstract void miniWindowActivated(BuildingsInInventory buildingInInventory);

    protected abstract void miniWindowActivated(ItemSelectingPlanet itemSelectingPlanet);
}
