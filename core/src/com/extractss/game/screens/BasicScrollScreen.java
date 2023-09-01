package com.extractss.game.screens;

import static com.extractss.game.ExtractSolarSys.backgroundsOther;
import static com.extractss.game.ExtractSolarSys.bitmapFont;
import static com.extractss.game.ExtractSolarSys.bitmapFontSmall;
import static com.extractss.game.ExtractSolarSys.currentPlanet;
import static com.extractss.game.ExtractSolarSys.energyTexture;
import static com.extractss.game.ExtractSolarSys.getIncrementMechanicUpgradeCost;
import static com.extractss.game.ExtractSolarSys.incrementMechanicMaxValue;
import static com.extractss.game.ExtractSolarSys.incrementMechanicValue;
import static com.extractss.game.ExtractSolarSys.incrementingThreadTime;
import static com.extractss.game.ExtractSolarSys.metalTexture;
import static com.extractss.game.ExtractSolarSys.moneyTexture;
import static com.extractss.game.ExtractSolarSys.progressBarBackNinePatch;
import static com.extractss.game.ExtractSolarSys.progressBarKnobNinePatch;
import static com.extractss.game.ExtractSolarSys.screenManager;
import static com.extractss.game.ExtractSolarSys.selectingPlanetArrayList;
import static com.extractss.game.ExtractSolarSys.successSound;
import static com.extractss.game.ExtractSolarSys.upNinePatch;
import static com.extractss.game.utils.Constants.APP_HEIGHT;
import static com.extractss.game.utils.Constants.APP_WIDTH;
import static com.extractss.game.utils.Constants.AVERAGE_VALUE_TO_BUY_RES;
import static com.extractss.game.utils.Constants.BUTTON_HEIGHT;
import static com.extractss.game.utils.Constants.HEIGHT_RESOURCES_TABLE;
import static com.extractss.game.utils.Constants.KNOB_WIDTH;
import static com.extractss.game.utils.Constants.KNOB_X;
import static com.extractss.game.utils.Constants.LIST_HEIGHT;
import static com.extractss.game.utils.Constants.LIST_WIDTH;
import static com.extractss.game.utils.Constants.Y_RESOURCES_TABLE;
import static com.extractss.game.utils.Operations.isEnableToBuy;
import static com.extractss.game.utils.Operations.isInPlaceMain;
import static com.extractss.game.utils.Operations.parseAndSavePrefsBuildings;
import static com.extractss.game.utils.Operations.totalListHeight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.extractss.game.ClassesForLists.BasicListItem;
import com.extractss.game.ClassesForLists.BuildingsInInventory;
import com.extractss.game.ClassesForLists.ItemResearch;
import com.extractss.game.ClassesForLists.ItemSelectingPlanet;
import com.extractss.game.ClassesForLists.ItemShop;
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

        ItemResearch listElementResearch;
        ItemSelectingPlanet listElementSelectingPlanet;
        ItemShop listElementShop;
        
        
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
                                listElementResearch = (ItemResearch)arrayList.get(i);
                                user.addInvents();
                                user.setMoney(user.getMoney() - listElementResearch.getCostMoney());
                                user.setMetal(user.getMetal() - listElementResearch.getCostMetal());
                                user.setEnergy(user.getEnergy() - listElementResearch.getCostEnergy());
                                parseAndSavePrefsBuildings(user);
                                successSound.play(user.getSoundsVolume());
                                break;
                            }else if (this instanceof SelectingPlanetScreen && isEnableToBuy(user, (ItemSelectingPlanet) arrayList.get(i))) {
                                listElementSelectingPlanet = (ItemSelectingPlanet)arrayList.get(i);
                                user.setMoney(user.getMoney() - listElementSelectingPlanet.getCostMoney());
                                user.setMetal(user.getMetal() - listElementSelectingPlanet.getCostMetal());
                                user.setEnergy(user.getEnergy() - listElementSelectingPlanet.getCostEnergy());
                                listElementSelectingPlanet.setCostMoney(0);
                                listElementSelectingPlanet.setCostMetal(0);
                                listElementSelectingPlanet.setCostEnergy(0);
                                currentPlanet = i;
                                parseAndSavePrefsBuildings(user);
                                successSound.play(user.getSoundsVolume());
                                screenManager.setPlanetScreen(new Planet(sys, user));
                                sys.setScreen(screenManager.getPlanetScreen());
                                break;
                            }else if (this instanceof Shop) {
                                listElementShop = (ItemShop)arrayList.get(i);
                                parseAndSavePrefsBuildings(user);
                                if (listElementShop.getName() == "faster click" && isEnableToBuy(user, listElementShop) && incrementMechanicMaxValue > 2) {
                                    user.setMoney(user.getMoney() - listElementShop.getCostMoney());
                                    user.setMetal(user.getMetal() - listElementShop.getCostMetal());
                                    user.setEnergy(user.getEnergy() - listElementShop.getCostEnergy());
                                    incrementMechanicMaxValue--;
                                    incrementMechanicValue = 0;
                                    if (incrementMechanicMaxValue == 2) getIncrementMechanicUpgradeCost = 0;
                                    else getIncrementMechanicUpgradeCost *= 1.5f;
                                    listElementShop.setCostMoney(getIncrementMechanicUpgradeCost);
                                    listElementShop.setCostMetal(getIncrementMechanicUpgradeCost);
                                    listElementShop.setCostEnergy(getIncrementMechanicUpgradeCost);
                                    successSound.play(user.getSoundsVolume());
                                } else if (listElementShop.getCostMoney() == 1) miniWindowActivated(0, false);
                                else if (listElementShop.getCostMetal() == 1) miniWindowActivated(1, false);
                                else if (listElementShop.getCostEnergy() == 1) miniWindowActivated(2, false);
                                else if (listElementShop.getCostMoney() == 2) miniWindowActivated(0, true);
                                else if (listElementShop.getCostMetal() == 2) miniWindowActivated(1, true);
                                else if (listElementShop.getCostEnergy() == 2) miniWindowActivated(2, true);
                                else if (listElementShop.getCostMoney() == AVERAGE_VALUE_TO_BUY_RES && isEnableToBuy(user, listElementShop)){
                                    if (listElementShop.getOutputMetal() != 0) {
                                        user.setMetal(user.getMetal() + listElementShop.getOutputMetal());
                                    } else {
                                        user.setEnergy(user.getEnergy() + listElementShop.getOutputEnergy());
                                    }
                                    successSound.play(user.getSoundsVolume());
                                    user.setMoney(user.getMoney() - listElementShop.getCostMoney());
                                } else if (listElementShop.getCostMetal() == AVERAGE_VALUE_TO_BUY_RES && isEnableToBuy(user, listElementShop)){
                                    if (listElementShop.getOutputMoney() != 0) {
                                        user.setMoney(user.getMoney() + listElementShop.getOutputMoney());
                                    } else {
                                        user.setEnergy(user.getEnergy() + listElementShop.getOutputEnergy());
                                    }
                                    successSound.play(user.getSoundsVolume());
                                    user.setMetal(user.getMetal() - listElementShop.getCostMetal());
                                } else if (listElementShop.getCostEnergy() == AVERAGE_VALUE_TO_BUY_RES && isEnableToBuy(user, listElementShop)){
                                    if (listElementShop.getOutputMetal() != 0) {
                                        user.setMetal(user.getMetal() + listElementShop.getOutputMetal());
                                    } else {
                                        user.setMoney(user.getMoney() + listElementShop.getOutputMoney());
                                    }
                                    successSound.play(user.getSoundsVolume());
                                    user.setEnergy(user.getEnergy() - listElementShop.getCostEnergy());
                                }
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

    protected void limitListSizeOnScreen(){
        batch.draw(new TextureRegion(backgroundsOther.get(curScreenAnimation), 0,
                        19 * backgroundsOther.get(curScreenAnimation).getHeight() / 9,
                        backgroundsOther.get(curScreenAnimation).getWidth(),
                        backgroundsOther.get(curScreenAnimation).getHeight() / 9), 0,
                APP_HEIGHT - BUTTON_HEIGHT - 2 * bitmapFontSmall.getCapHeight(), APP_WIDTH,
                BUTTON_HEIGHT + 2 * bitmapFont.getCapHeight());
        batch.draw(new TextureRegion(backgroundsOther.get(curScreenAnimation), 0,
                        0, backgroundsOther.get(curScreenAnimation).getWidth(),
                        backgroundsOther.get(curScreenAnimation).getHeight() / 9), 0,
                0, APP_WIDTH, BUTTON_HEIGHT);
    }

    protected void drawAndCheckKnob(ArrayList<? extends BasicListItem> arrayList) {
        progressBarBackNinePatch.draw(batch, KNOB_X, BUTTON_HEIGHT, KNOB_WIDTH, LIST_HEIGHT);
        if (totalListHeight(arrayList) > LIST_HEIGHT) {
            progressBarKnobNinePatch.draw(batch, KNOB_X, BUTTON_HEIGHT - knobHeight *
                    (arrayList.get(0).y - BUTTON_HEIGHT) / LIST_HEIGHT, KNOB_WIDTH, knobHeight);
            /*
            Если нажать на ползунок, мы переместимся в ту часть списка, в какую часть ползунка мы нажали.
             */
            if (Gdx.input.isTouched() && isInPlaceMain(touchedX, touchedY, KNOB_X, BUTTON_HEIGHT,
                    KNOB_WIDTH, LIST_HEIGHT)) {
                if (touchedY + knobHeight / 2 > LIST_HEIGHT + BUTTON_HEIGHT) {
                    touchedY = LIST_HEIGHT + BUTTON_HEIGHT - knobHeight / 2;
                } else if (touchedY - knobHeight / 2 < BUTTON_HEIGHT) {
                    touchedY = BUTTON_HEIGHT + knobHeight / 2;
                }
                deltaFirstElementY = -arrayList.get(0).y + (LIST_HEIGHT *
                        (BUTTON_HEIGHT - touchedY + knobHeight / 2) / knobHeight + BUTTON_HEIGHT);
                for (int i = 0; i < arrayList.size(); i++) {
                    arrayList.get(i).y += deltaFirstElementY;
                }
            }
        }
    }
    protected abstract void miniWindowActivated(Building building);

    protected abstract void miniWindowActivated(BuildingsInInventory buildingInInventory);

    protected abstract void miniWindowActivated(int typeRes, boolean isAds);

    public void checkMinuteToIncrementResources(){
        if (System.currentTimeMillis() - incrementingThreadTime > 60000) {
            incrementResourcesTimeCheck.test();
            incrementingThreadTime = System.currentTimeMillis();
        }
    }

    protected void drawResourcesLabel(){
        upNinePatch.draw(batch, 0, Y_RESOURCES_TABLE, APP_WIDTH, HEIGHT_RESOURCES_TABLE);
        batch.draw(moneyTexture, moneyTextureX, yForIcons, widthForIcons, heightForIcons);
        batch.draw(metalTexture, metalTextureX, yForIcons, widthForIcons, heightForIcons);
        batch.draw(energyTexture, energyTextureX, yForIcons, widthForIcons, heightForIcons);
        bitmapFontSmall.draw(batch, String.valueOf(user.getMoney()),
                moneyValueX, yForResourcesText);
        bitmapFontSmall.draw(batch, String.valueOf(user.getMetal()),
                metalValueX, yForResourcesText);
        bitmapFontSmall.draw(batch, String.valueOf(user.getEnergy()),
                energyValueX, yForResourcesText);
    }
}
