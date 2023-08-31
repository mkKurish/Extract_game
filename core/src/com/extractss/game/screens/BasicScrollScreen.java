package com.extractss.game.screens;

import static com.extractss.game.ExtractSolarSys.bitmapFontSmall;
import static com.extractss.game.ExtractSolarSys.currentPlanet;
import static com.extractss.game.ExtractSolarSys.screenManager;
import static com.extractss.game.ExtractSolarSys.selectingPlanetArrayList;
import static com.extractss.game.ExtractSolarSys.successSound;
import static com.extractss.game.utils.Constants.APP_HEIGHT;
import static com.extractss.game.utils.Constants.APP_WIDTH;
import static com.extractss.game.utils.Constants.BUTTON_HEIGHT;
import static com.extractss.game.utils.Constants.LIST_HEIGHT;
import static com.extractss.game.utils.Constants.LIST_WIDTH;
import static com.extractss.game.utils.Constants.MEDIUM_LEST_ELEMENT_HEIGHT;
import static com.extractss.game.utils.Operations.isEnableToBuy;
import static com.extractss.game.utils.Operations.isInPlaceMain;
import static com.extractss.game.utils.Operations.parseAndSavePrefsBuildings;
import static com.extractss.game.utils.Operations.totalListHeight;

import com.badlogic.gdx.Gdx;
import com.extractss.game.ClassesForLists.BasicListItem;
import com.extractss.game.ClassesForLists.BuildingsInInventory;
import com.extractss.game.ClassesForLists.ItemResearch;
import com.extractss.game.ClassesForLists.ItemSelectingPlanet;
import com.extractss.game.SimpleClasses.Building;
import com.extractss.game.utils.IncrementResourcesTimeCheck;

import java.util.ArrayList;

abstract public class BasicScrollScreen extends BasicScreen {
    protected float knobHeight;
    protected float touchedListY;
    protected float yForIcons;
    protected static float heightForIcons;
    protected static float widthForIcons;
    protected float yForResourcesText;
    protected float xForPriceListElements = 9.5f * bitmapFontSmall.getCapHeight();
    protected float xForIconsListElements = 8.5f * bitmapFontSmall.getCapHeight();
    protected static float appWidthToTwentyFour = APP_WIDTH / 24;
    protected float firstElementY;
    protected float lastElementY;
    protected static float boarderUp = APP_HEIGHT - BUTTON_HEIGHT - 2 * bitmapFontSmall.getCapHeight();
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
    protected float lastElementHeight;
    protected boolean screenWasTouchedJustNow = false;
    protected long lastListTouchTime = 1000;
    IncrementResourcesTimeCheck incrementResourcesTimeCheck;

    protected void scrollTouchMechanic(ArrayList<? extends BasicListItem> arrayList) {
        boolean isBigList = totalListHeight(arrayList) > LIST_HEIGHT;

        if (arrayList.size() != 0) {
            firstElementY = arrayList.get(0).y;
            lastElementY = arrayList.get(arrayList.size() - 1).y;
            lastElementHeight = arrayList.get(arrayList.size() - 1).elementHeight;
        }
        if (isInPlaceMain(Gdx.input.getX(),
                Gdx.graphics.getHeight() - Gdx.input.getY(), 0, BUTTON_HEIGHT,
                LIST_WIDTH, LIST_HEIGHT)) {
            if (Gdx.input.isTouched()) {
                if (screenWasTouchedJustNow) {
                    if (isBigList) {
                        // List scroll
                        if (System.currentTimeMillis() - lastListTouchTime < 50) {
                            resCoord = (-touchedListY + Gdx.graphics.getHeight() - Gdx.input.getY()) * 2;
                            if (firstElementY + resCoord > BUTTON_HEIGHT) {
                                resCoord -= firstElementY + resCoord - BUTTON_HEIGHT;
                            } else if (lastElementY + lastElementHeight + resCoord < boarderUp) {
                                resCoord += boarderUp - lastElementY - resCoord - lastElementHeight;
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
                if (Gdx.input.getX() == startedTouchX && Gdx.input.getY() == startedTouchY && screenWasTouchedJustNow) {
                    // List touch
                    for (int i = 0; i < arrayList.size(); i++) {
                        touchedY = Gdx.graphics.getHeight() - Gdx.input.getY();
                        if (touchedY > arrayList.get(i).y
                                && touchedY < arrayList.get(i).y + arrayList.get(i).elementHeight) {
                            if ((this instanceof Construction || this instanceof Inventory)
                                    && user.getInvents() >= arrayList.get(i).getInventLvl()){
                                miniWindowSwitch(arrayList.get(i));
                                break;
                            } else if (this instanceof Research && isEnableToBuy(user, (ItemResearch) arrayList.get(i))) {
                                user.addInvents();
                                user.setMoney(user.getMoney() - ((ItemResearch)arrayList.get(i)).getCostMoney());
                                user.setMetal(user.getMetal() - ((ItemResearch)arrayList.get(i)).getCostMetal());
                                user.setEnergy(user.getEnergy() - ((ItemResearch)arrayList.get(i)).getCostEnergy());
                                parseAndSavePrefsBuildings(user);
                                successSound.play(user.getSoundsVolume());
                                break;
                            }else if (this instanceof SelectingPlanetScreen && isEnableToBuy(user, (ItemSelectingPlanet) arrayList.get(i))) {
                                user.setMoney(user.getMoney() - ((ItemSelectingPlanet)arrayList.get(i)).getCostMoney());
                                user.setMetal(user.getMetal() - ((ItemSelectingPlanet)arrayList.get(i)).getCostMetal());
                                user.setEnergy(user.getEnergy() - ((ItemSelectingPlanet)arrayList.get(i)).getCostEnergy());
                                selectingPlanetArrayList.get(i).setCostMoney(0);
                                selectingPlanetArrayList.get(i).setCostMetal(0);
                                selectingPlanetArrayList.get(i).setCostEnergy(0);
                                currentPlanet = i;
                                parseAndSavePrefsBuildings(user);
                                successSound.play(user.getSoundsVolume());
                                screenManager.setPlanetScreen(new Planet(sys, user));
                                sys.setScreen(screenManager.getPlanetScreen());
                                break;
                            }else if (this instanceof Shop) {
                                //TODO: touch mechanic is very complicated, fix it!
                                break;
                            }
                        }
                    }
                }
                screenWasTouchedJustNow = false;
            }
        }

        if (!Gdx.input.isTouched()) {
            screenWasTouchedJustNow = false;
            if (isBigList) {
                if (lastElementY + lastElementHeight < boarderUp) {
                    for (int i = 0; i < arrayList.size(); i++) {
                        arrayList.get(i).y += boarderUp - lastElementY - lastElementHeight;
                    }
                } else if (firstElementY > BUTTON_HEIGHT) {
                    for (int i = 0; i < arrayList.size(); i++) {
                        arrayList.get(i).y += BUTTON_HEIGHT - firstElementY;
                    }
                }
            }else{
                if (arrayList.size() > 0 && firstElementY != BUTTON_HEIGHT) {
                    arrayList.get(0).y = BUTTON_HEIGHT;
                    for (int i = 1; i < arrayList.size(); i++) {
                        arrayList.get(i).y = arrayList.get(i-1).y + arrayList.get(i-1).elementHeight;
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
        }
    }

    protected abstract void miniWindowActivated(Building building);

    protected abstract void miniWindowActivated(BuildingsInInventory buildingInInventory);
}
