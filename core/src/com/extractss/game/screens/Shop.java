package com.extractss.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.extractss.game.ClassesForLists.BuildingsInInventory;
import com.extractss.game.ExtractSolarSys;
import com.extractss.game.ClassesForLists.ItemShop;
import com.extractss.game.SimpleClasses.Building;
import com.extractss.game.SimpleClasses.MyButtons;
import com.extractss.game.SimpleClasses.User;
import com.extractss.game.utils.IncrementResourcesTimeCheck;

import java.util.ArrayList;

import static com.extractss.game.ExtractSolarSys.backgroundsOther;
import static com.extractss.game.ExtractSolarSys.bitmapFont;
import static com.extractss.game.ExtractSolarSys.bitmapFontSmall;
import static com.extractss.game.ExtractSolarSys.downNinePatch;
import static com.extractss.game.ExtractSolarSys.energyTexture;
import static com.extractss.game.ExtractSolarSys.getIncrementMechanicUpgradeCost;
import static com.extractss.game.ExtractSolarSys.incrementEnergy;
import static com.extractss.game.ExtractSolarSys.incrementMechanicMaxValue;
import static com.extractss.game.ExtractSolarSys.incrementMetal;
import static com.extractss.game.ExtractSolarSys.incrementMoney;
import static com.extractss.game.ExtractSolarSys.maxEnergy;
import static com.extractss.game.ExtractSolarSys.maxMetal;
import static com.extractss.game.ExtractSolarSys.maxMoney;
import static com.extractss.game.ExtractSolarSys.metalTexture;
import static com.extractss.game.ExtractSolarSys.moneyTexture;
import static com.extractss.game.ExtractSolarSys.screenManager;
import static com.extractss.game.ExtractSolarSys.upNinePatch;
import static com.extractss.game.utils.Constants.APP_HEIGHT;
import static com.extractss.game.utils.Constants.APP_WIDTH;
import static com.extractss.game.utils.Constants.AVERAGE_VALUE_TO_BUY_RES;
import static com.extractss.game.utils.Constants.BOTTOM_BUTTONS_TEXT_Y;
import static com.extractss.game.utils.Constants.BUTTON_HEIGHT;
import static com.extractss.game.utils.Constants.HEIGHT_FOR_RESOURCES;
import static com.extractss.game.utils.Constants.KNOB_WIDTH;
import static com.extractss.game.utils.Constants.MEDIUM_LEST_ELEMENT_HEIGHT;
import static com.extractss.game.utils.Constants.LIST_HEIGHT;
import static com.extractss.game.utils.Constants.LIST_WIDTH;
import static com.extractss.game.utils.Constants.SCALEXY_NEW;
import static com.extractss.game.utils.Constants.SMALLER_SCALE;
import static com.extractss.game.utils.Constants.SMALL_LIST_ELEMENT_HEIGHT;
import static com.extractss.game.utils.Constants.TOP_BUTTONS_TEXT_Y;
import static com.extractss.game.utils.Operations.isEnableToBuy;
import static com.extractss.game.utils.Operations.parseAndSavePrefsBuildings;
import static com.extractss.game.utils.Operations.totalListHeight;

public class Shop extends BasicScrollScreen {

    private ArrayList<ItemShop> listItems;
    private ItemShop listElementForCycle;
    private static float xForSecondIconsListResources;
    private static float xForSecondPriceListResources;
    private static int averageValueToGetRes;
    private static int averageValueToGetResAdd;
    private static int averageValueToGetResDonate;
    private static float menuX;
    private static float planetX;
    private static float researchX;
    private static float constructX;

    public Shop(ExtractSolarSys sys, User user) {
        this.sys = sys;
        this.user = user;

        batch = new SpriteBatch();

        myButtons = new ArrayList<>();
        myButtons.add(new MyButtons(0, APP_WIDTH / 2, 0, BUTTON_HEIGHT));
        myButtons.add(new MyButtons(APP_WIDTH / 2, APP_WIDTH / 2,
                0, BUTTON_HEIGHT));
        myButtons.add(new MyButtons(0, APP_WIDTH / 2,
                APP_HEIGHT - BUTTON_HEIGHT, BUTTON_HEIGHT));
        myButtons.add(new MyButtons(APP_WIDTH / 2, APP_WIDTH / 2,
                APP_HEIGHT - BUTTON_HEIGHT, BUTTON_HEIGHT));
        myButton = myButtons.get(0);

        calculatePrices();

        /*
        Создаем изначальный список элементов магазина.
         */
        listItems = new ArrayList<>();
        listItems.add(new ItemShop("Money to Metal",
                AVERAGE_VALUE_TO_BUY_RES, 0, 0,
                0, averageValueToGetRes, 0, BUTTON_HEIGHT, SMALL_LIST_ELEMENT_HEIGHT));
        listItems.add(new ItemShop("Money to Energy",
                AVERAGE_VALUE_TO_BUY_RES, 0, 0,
                0, 0, averageValueToGetRes,
                listItems.get(listItems.size() - 1).y + listItems.get(listItems.size() - 1).elementHeight,
                SMALL_LIST_ELEMENT_HEIGHT));
        listItems.add(new ItemShop("Metal to Money",
                0, AVERAGE_VALUE_TO_BUY_RES, 0,
                averageValueToGetRes, 0, 0,
                listItems.get(listItems.size() - 1).y + listItems.get(listItems.size() - 1).elementHeight,
                SMALL_LIST_ELEMENT_HEIGHT));
        listItems.add(new ItemShop("Metal to Energy",
                0, AVERAGE_VALUE_TO_BUY_RES, 0,
                0, 0, averageValueToGetRes,
                listItems.get(listItems.size() - 1).y + listItems.get(listItems.size() - 1).elementHeight,
                SMALL_LIST_ELEMENT_HEIGHT));
        listItems.add(new ItemShop("Energy to Money",
                0, 0, AVERAGE_VALUE_TO_BUY_RES,
                averageValueToGetRes, 0, 0,
                listItems.get(listItems.size() - 1).y + listItems.get(listItems.size() - 1).elementHeight,
                SMALL_LIST_ELEMENT_HEIGHT));
        listItems.add(new ItemShop("Energy to Metal",
                0, 0, AVERAGE_VALUE_TO_BUY_RES,
                0, averageValueToGetRes, 0,
                listItems.get(listItems.size() - 1).y + listItems.get(listItems.size() - 1).elementHeight,
                SMALL_LIST_ELEMENT_HEIGHT));
        listItems.add(new ItemShop("faster click",
                getIncrementMechanicUpgradeCost, getIncrementMechanicUpgradeCost, getIncrementMechanicUpgradeCost,
                0, 0, 0,
                listItems.get(listItems.size() - 1).y + listItems.get(listItems.size() - 1).elementHeight,
                MEDIUM_LEST_ELEMENT_HEIGHT));
        listItems.add(new ItemShop("Ads for Money",
                2, 0, 0,
                averageValueToGetResAdd, 0, 0,
                listItems.get(listItems.size() - 1).y + listItems.get(listItems.size() - 1).elementHeight,
                SMALL_LIST_ELEMENT_HEIGHT));
        listItems.add(new ItemShop("Ads for Metal",
                0, 2, 0,
                0, averageValueToGetResAdd, 0,
                listItems.get(listItems.size() - 1).y + listItems.get(listItems.size() - 1).elementHeight,
                SMALL_LIST_ELEMENT_HEIGHT));
        listItems.add(new ItemShop("Ads for Energy",
                0, 0, 2,
                0, 0, averageValueToGetResAdd,
                listItems.get(listItems.size() - 1).y + listItems.get(listItems.size() - 1).elementHeight,
                SMALL_LIST_ELEMENT_HEIGHT));
/*        listItems.add(new ItemShop("Buy Money",
                1, 0, 0,
                averageValueToGetResDonate, 0, 0,
                listItems.get(listItems.size() - 1).y + listItems.get(listItems.size() - 1).elementHeight,
                SMALL_LIST_ELEMENT_HEIGHT));
        listItems.add(new ItemShop("Buy Metal",
                0, 1, 0,
                0, averageValueToGetResDonate, 0,
                listItems.get(listItems.size() - 1).y + listItems.get(listItems.size() - 1).elementHeight,
                SMALL_LIST_ELEMENT_HEIGHT));
        listItems.add(new ItemShop("Buy Energy",
                0, 0, 1,
                0, 0, averageValueToGetResDonate,
                listItems.get(listItems.size() - 1).y + listItems.get(listItems.size() - 1).elementHeight,
                SMALL_LIST_ELEMENT_HEIGHT));*/

        xForPriceListElements = 5.5f * bitmapFontSmall.getCapHeight();
        xForIconsListElements = 4.5f * bitmapFontSmall.getCapHeight();

        knobHeight = LIST_HEIGHT * LIST_HEIGHT / totalListHeight(listItems);

        yForIcons = HEIGHT_FOR_RESOURCES - 7 * bitmapFontSmall.getCapHeight() / 4;
        moneyTextureX = 2 * appWidthToTwentyFour / 3;
        metalTextureX = moneyTextureX + 8 * appWidthToTwentyFour;
        energyTextureX = moneyTextureX + 16 * appWidthToTwentyFour;
        moneyValueX = moneyTextureX + 2 * appWidthToTwentyFour;
        metalValueX = metalTextureX + 2 * appWidthToTwentyFour;
        energyValueX = energyTextureX + 2 * appWidthToTwentyFour;
        heightForIcons = 3 * bitmapFontSmall.getCapHeight() / 2;
        widthForIcons = 3 * bitmapFontSmall.getCapHeight() / 2;
        yForResourcesText = HEIGHT_FOR_RESOURCES - bitmapFontSmall.getCapHeight() / 2;
        xForSecondIconsListResources = xForIconsListElements + SCALEXY_NEW * 11 *
                (AVERAGE_VALUE_TO_BUY_RES + " ->  ").length() + bitmapFontSmall.getCapHeight();
        xForSecondPriceListResources = xForSecondIconsListResources + bitmapFontSmall.getCapHeight();

        menuX = APP_WIDTH / 4 - "menu".length() * 11 * SCALEXY_NEW;
        planetX = 3 * APP_WIDTH / 4 - "planet".length() * 11 * SCALEXY_NEW;
        researchX = APP_WIDTH / 4 - "research".length() * 11 * SCALEXY_NEW;
        constructX = 3 * APP_WIDTH / 4 - "construct".length() * 11.3f * SCALEXY_NEW;

        incrementResourcesTimeCheck = new IncrementResourcesTimeCheck(sys, user);

        lastAnimationTime = System.currentTimeMillis();
    }

    @Override
    public void show() {
        calculatePrices();
    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        checkMinuteToIncrementResources(); //Проверяем, прошла ли минута, чтобы увеличить значение внутриигровых рерурсов.

        doAnimationChange(); // Производим анимацию фона.

        batch.begin();

        batch.draw(backgroundsOther.get(curScreenAnimation), 0, 0, APP_WIDTH, APP_HEIGHT);

        scrollTouchMechanic(listItems);

        for (int i = 0; i < listItems.size(); i++) {
            listElementForCycle = listItems.get(i);
            /*
            Отрисовываем каждый элемент списка, который помещается на экран.
             */
            if ((listElementForCycle.y < APP_HEIGHT + BUTTON_HEIGHT && listElementForCycle.y > -listElementForCycle.elementHeight)) {
                if (listElementForCycle.getName() == "faster click") {
                    if (isEnableToBuy(user, listElementForCycle) && incrementMechanicMaxValue > 2) {
                        upNinePatch.draw(batch, 0, listElementForCycle.y, LIST_WIDTH,
                                listElementForCycle.elementHeight);
                    } else {
                        downNinePatch.draw(batch, 0, listElementForCycle.y, LIST_WIDTH, listElementForCycle.elementHeight);
                    }
                    batch.draw(moneyTexture, xForIconsListElements, listElementForCycle.y -
                                    3 * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight,
                            bitmapFontSmall.getCapHeight(), bitmapFontSmall.getCapHeight());
                    batch.draw(metalTexture, xForIconsListElements, listElementForCycle.y -
                                    5f * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight,
                            bitmapFontSmall.getCapHeight(), bitmapFontSmall.getCapHeight());
                    batch.draw(energyTexture, xForIconsListElements, listElementForCycle.y -
                                    7 * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight,
                            bitmapFontSmall.getCapHeight(), bitmapFontSmall.getCapHeight());
                    bitmapFontSmall.draw(batch, String.valueOf(listElementForCycle.getCostMoney()),
                            xForPriceListElements, listElementForCycle.y -
                                    2 * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight);
                    bitmapFontSmall.draw(batch, String.valueOf(listElementForCycle.getCostMoney()),
                            xForPriceListElements, listElementForCycle.y -
                                    4f * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight);
                    bitmapFontSmall.draw(batch, String.valueOf(listElementForCycle.getCostMoney()),
                            xForPriceListElements, listElementForCycle.y -
                                    6 * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight);
                }
                if (listElementForCycle.getCostMoney() == AVERAGE_VALUE_TO_BUY_RES) {
                    if (isEnableToBuy(user, listElementForCycle)) {
                        upNinePatch.draw(batch, 0, listElementForCycle.y, LIST_WIDTH, listElementForCycle.elementHeight);
                    } else {
                        downNinePatch.draw(batch, 0, listElementForCycle.y, LIST_WIDTH, listElementForCycle.elementHeight);
                    }
                    bitmapFontSmall.draw(batch, listElementForCycle.getCostMoney() + " -> ",
                            xForPriceListElements, listElementForCycle.y -
                                    2 * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight);
                    batch.draw(moneyTexture, xForIconsListElements, listElementForCycle.y -
                                    3 * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight,
                            bitmapFontSmall.getCapHeight(), bitmapFontSmall.getCapHeight());
                    if (listElementForCycle.getOutputMetal() != 0) {
                        bitmapFontSmall.draw(batch, String.valueOf(listElementForCycle.getOutputMetal()),
                                xForSecondPriceListResources, listElementForCycle.y -
                                        2 * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight);
                        batch.draw(metalTexture, xForSecondIconsListResources,
                                listElementForCycle.y - 3 * bitmapFontSmall.getCapHeight() +
                                        listElementForCycle.elementHeight, bitmapFontSmall.getCapHeight(),
                                bitmapFontSmall.getCapHeight());
                    } else {
                        bitmapFontSmall.draw(batch, String.valueOf(listElementForCycle.getOutputEnergy()),
                                xForSecondPriceListResources, listElementForCycle.y -
                                        2 * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight);
                        batch.draw(energyTexture, xForSecondIconsListResources,
                                listElementForCycle.y - 3 * bitmapFontSmall.getCapHeight() +
                                        listElementForCycle.elementHeight, bitmapFontSmall.getCapHeight(),
                                bitmapFontSmall.getCapHeight());
                    }
                } else if (listElementForCycle.getCostMoney() == 1) {
                    upNinePatch.draw(batch, 0, listElementForCycle.y, LIST_WIDTH, listElementForCycle.elementHeight);
                    bitmapFontSmall.draw(batch, "cash ->",
                            xForPriceListElements, listElementForCycle.y - 2 * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight);
                    bitmapFontSmall.draw(batch, String.valueOf(listElementForCycle.getOutputMoney()),
                            12 * xForSecondPriceListResources / 10, listElementForCycle.y -
                                    2 * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight);
                    batch.draw(moneyTexture, 12 * xForSecondIconsListResources / 10,
                            listElementForCycle.y - 3 * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight,
                            bitmapFontSmall.getCapHeight(), bitmapFontSmall.getCapHeight());
                    //TODO: add ads
                } else if (listElementForCycle.getCostMoney() == 2) {
                    upNinePatch.draw(batch, 0, listElementForCycle.y, LIST_WIDTH, listElementForCycle.elementHeight);
                    bitmapFontSmall.draw(batch, "ads ->",
                            xForPriceListElements, listElementForCycle.y -
                                    2 * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight);
                    bitmapFontSmall.draw(batch, String.valueOf(listElementForCycle.getOutputMoney()),
                            11 * xForSecondPriceListResources / 10, listElementForCycle.y -
                                    2 * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight);
                    batch.draw(moneyTexture, 11 * xForSecondIconsListResources / 10,
                            listElementForCycle.y - 3 * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight,
                            bitmapFontSmall.getCapHeight(), bitmapFontSmall.getCapHeight());
                    //TODO: add donations
                } else if (listElementForCycle.getCostMetal() == AVERAGE_VALUE_TO_BUY_RES) {
                    if (isEnableToBuy(user, listElementForCycle)) {
                        upNinePatch.draw(batch, 0, listElementForCycle.y, LIST_WIDTH, listElementForCycle.elementHeight);
                    } else {
                        downNinePatch.draw(batch, 0, listElementForCycle.y, LIST_WIDTH, listElementForCycle.elementHeight);
                    }
                    bitmapFontSmall.draw(batch, listElementForCycle.getCostMetal() + " -> ",
                            xForPriceListElements, listElementForCycle.y -
                                    2 * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight);
                    batch.draw(metalTexture, xForIconsListElements,
                            listElementForCycle.y - 3 * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight,
                            bitmapFontSmall.getCapHeight(), bitmapFontSmall.getCapHeight());
                    if (listElementForCycle.getOutputMoney() != 0) {
                        bitmapFontSmall.draw(batch, String.valueOf(listElementForCycle.getOutputMoney()),
                                xForSecondPriceListResources, listElementForCycle.y -
                                        2 * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight);
                        batch.draw(moneyTexture, xForSecondIconsListResources,
                                listElementForCycle.y - 3 * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight,
                                bitmapFontSmall.getCapHeight(), bitmapFontSmall.getCapHeight());
                    } else {
                        bitmapFontSmall.draw(batch, String.valueOf(listElementForCycle.getOutputEnergy()),
                                xForSecondPriceListResources, listElementForCycle.y -
                                        2 * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight);
                        batch.draw(energyTexture, xForSecondIconsListResources,
                                listElementForCycle.y - 3 * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight,
                                bitmapFontSmall.getCapHeight(), bitmapFontSmall.getCapHeight());
                    }
                } else if (listElementForCycle.getCostMetal() == 1) {
                    upNinePatch.draw(batch, 0, listElementForCycle.y, LIST_WIDTH, listElementForCycle.elementHeight);
                    bitmapFontSmall.draw(batch, "cash ->",
                            xForPriceListElements, listElementForCycle.y -
                                    2 * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight);
                    bitmapFontSmall.draw(batch, String.valueOf(listElementForCycle.getOutputMetal()),
                            12 * xForSecondPriceListResources / 10, listElementForCycle.y -
                                    2 * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight);
                    batch.draw(metalTexture, 12 * xForSecondIconsListResources / 10,
                            listElementForCycle.y - 3 * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight,
                            bitmapFontSmall.getCapHeight(), bitmapFontSmall.getCapHeight());
                    //TODO: add ads
                } else if (listElementForCycle.getCostMetal() == 2) {
                    upNinePatch.draw(batch, 0, listElementForCycle.y, LIST_WIDTH, listElementForCycle.elementHeight);
                    bitmapFontSmall.draw(batch, "ads ->",
                            xForPriceListElements, listElementForCycle.y -
                                    2 * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight);
                    bitmapFontSmall.draw(batch, String.valueOf(listElementForCycle.getOutputMetal()),
                            11 * xForSecondPriceListResources / 10,
                            listElementForCycle.y - 2 * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight);
                    batch.draw(metalTexture, 11 * xForSecondIconsListResources / 10,
                            listElementForCycle.y - 3 * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight,
                            bitmapFontSmall.getCapHeight(), bitmapFontSmall.getCapHeight());
                    //TODO: add donations
                } else if (listElementForCycle.getCostEnergy() == AVERAGE_VALUE_TO_BUY_RES) {
                    if (isEnableToBuy(user, listElementForCycle)) {
                        upNinePatch.draw(batch, 0, listElementForCycle.y, LIST_WIDTH, listElementForCycle.elementHeight);
                    } else {
                        downNinePatch.draw(batch, 0, listElementForCycle.y, LIST_WIDTH, listElementForCycle.elementHeight);
                    }
                    bitmapFontSmall.draw(batch, listElementForCycle.getCostEnergy() + " -> ",
                            xForPriceListElements, listElementForCycle.y -
                                    2 * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight);
                    batch.draw(energyTexture, xForIconsListElements,
                            listElementForCycle.y - 3 * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight,
                            bitmapFontSmall.getCapHeight(), bitmapFontSmall.getCapHeight());
                    if (listElementForCycle.getOutputMetal() != 0) {
                        bitmapFontSmall.draw(batch, String.valueOf(listElementForCycle.getOutputMetal()),
                                xForSecondPriceListResources, listElementForCycle.y -
                                        2 * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight);
                        batch.draw(metalTexture, xForSecondIconsListResources,
                                listElementForCycle.y - 3 * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight,
                                bitmapFontSmall.getCapHeight(), bitmapFontSmall.getCapHeight());
                    } else {
                        bitmapFontSmall.draw(batch, String.valueOf(listElementForCycle.getOutputMoney()),
                                xForSecondPriceListResources, listElementForCycle.y -
                                        2 * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight);
                        batch.draw(moneyTexture, xForSecondIconsListResources,
                                listElementForCycle.y - 3 * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight,
                                bitmapFontSmall.getCapHeight(), bitmapFontSmall.getCapHeight());
                    }
                } else if (listElementForCycle.getCostEnergy() == 1) {
                    upNinePatch.draw(batch, 0, listElementForCycle.y, LIST_WIDTH, listElementForCycle.elementHeight);
                    bitmapFontSmall.draw(batch, "cash ->",
                            xForPriceListElements, listElementForCycle.y -
                                    2 * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight);
                    bitmapFontSmall.draw(batch, String.valueOf(listElementForCycle.getOutputEnergy()),
                            12 * xForSecondPriceListResources / 10,
                            listElementForCycle.y - 2 * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight);
                    batch.draw(energyTexture, 12 * xForSecondIconsListResources / 10,
                            listElementForCycle.y - 3 * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight,
                            bitmapFontSmall.getCapHeight(), bitmapFontSmall.getCapHeight());
                    //TODO: add ads
                } else if (listElementForCycle.getCostEnergy() == 2) {
                    upNinePatch.draw(batch, 0, listElementForCycle.y, LIST_WIDTH, listElementForCycle.elementHeight);
                    bitmapFontSmall.draw(batch, "ads ->",
                            xForPriceListElements, listElementForCycle.y -
                                    2 * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight);
                    bitmapFontSmall.draw(batch, String.valueOf(listElementForCycle.getOutputEnergy()),
                            11 * xForSecondPriceListResources / 10,
                            listElementForCycle.y - 2 * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight);
                    batch.draw(energyTexture, 11 * xForSecondIconsListResources / 10,
                            listElementForCycle.y - 3 * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight,
                            bitmapFontSmall.getCapHeight(), bitmapFontSmall.getCapHeight());
                    //TODO: add donations
                }

                bitmapFontSmall.draw(batch, listElementForCycle.getName(),
                        (APP_WIDTH - KNOB_WIDTH) / 2 - listElementForCycle.getName().length() * 11 * SMALLER_SCALE,
                        listElementForCycle.y - bitmapFontSmall.getCapHeight() * 0.7f + listElementForCycle.elementHeight);
            }
        }

        if (user.getMoney() > maxMoney) user.setMoney(maxMoney);
        if (user.getMetal() > maxMetal) user.setMetal(maxMetal);
        if (user.getEnergy() > maxEnergy) user.setEnergy(maxEnergy);

        limitListSizeOnScreen(); // Ограничиваем размер списка на экране.

        drawResourcesLabel(); // Рисуем табличку с ресурсами игрока.

        checkButtonTouches(); // Проверяем кнопки на нажатие.

        drawAndCheckKnob(listItems); //Отрисовываем ползунок, показывающий место в списке, в котором мы находимся.

        bitmapFont.draw(batch, "menu", menuX, BOTTOM_BUTTONS_TEXT_Y);
        bitmapFont.draw(batch, "planet", planetX, BOTTOM_BUTTONS_TEXT_Y);
        bitmapFont.draw(batch, "research", researchX, TOP_BUTTONS_TEXT_Y);
        bitmapFont.draw(batch, "construct", constructX, TOP_BUTTONS_TEXT_Y);

        batch.end();
    }

    @Override
    public void hide() {
        parseAndSavePrefsBuildings(user);
    }

    @Override
    public void dispose() {
        parseAndSavePrefsBuildings(user);
        batch.dispose();
    }

    private void startAdd(int typeRes) {
        /*
        type 0 -> money
        type 1 -> metal
        type 2 -> energy
         */

        sys.showAd(typeRes, averageValueToGetResAdd, user);
    }

    private void startDonation(int typeRes) {
        /*
        type 0 -> money
        type 1 -> metal
        type 2 -> energy
         */
    }

    @Override
    public void buttonActivated(int i) {
        switch (i) {
            case 0:
                sys.setScreen(screenManager.getMainScreen());
                break;
            case 1:
                sys.setScreen(screenManager.getPlanetScreen());
                break;
            case 2:
                sys.setScreen(screenManager.getResearchScreen());
                break;
            case 3:
                sys.setScreen(screenManager.getConstructionScreen());
                break;
        }
    }

    @Override
    protected void miniWindowActivated(Building building) {

    }

    @Override
    protected void miniWindowActivated(BuildingsInInventory buildingInInventory) {

    }

    @Override
    protected void miniWindowActivated(int typeRes, boolean isAds) {
        if (isAds) {
            startAdd(typeRes);
        } else {
            startDonation(typeRes);
        }
    }

    private void calculatePrices(){
        /*
        Рассчет количества ресурсов для установления цен в магазине.
         */
        if (AVERAGE_VALUE_TO_BUY_RES < 50 + 3 * (incrementMoney + incrementMetal + incrementEnergy))
            AVERAGE_VALUE_TO_BUY_RES = 50 + 3 * (incrementMoney + incrementMetal + incrementEnergy);
        averageValueToGetRes = AVERAGE_VALUE_TO_BUY_RES / 2;
        averageValueToGetResAdd = 13 * AVERAGE_VALUE_TO_BUY_RES;
        averageValueToGetResDonate = AVERAGE_VALUE_TO_BUY_RES * 200;

        if (AVERAGE_VALUE_TO_BUY_RES > 999999) AVERAGE_VALUE_TO_BUY_RES = 999999;
        if (averageValueToGetRes > 999999) averageValueToGetRes = 999999;
        if (averageValueToGetResAdd > 999999) averageValueToGetResAdd = 999999;
        if (averageValueToGetResDonate > 999999) averageValueToGetResDonate = 999999;
    }
}
