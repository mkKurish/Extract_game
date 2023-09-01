package com.extractss.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.extractss.game.SimpleClasses.Building;
import com.extractss.game.ClassesForLists.BuildingsInInventory;
import com.extractss.game.ExtractSolarSys;
import com.extractss.game.SimpleClasses.MyButtons;
import com.extractss.game.SimpleClasses.User;
import com.extractss.game.utils.IncrementResourcesTimeCheck;

import java.util.ArrayList;

import static com.extractss.game.ExtractSolarSys.backgroundsOther;
import static com.extractss.game.ExtractSolarSys.bitmapFont;
import static com.extractss.game.ExtractSolarSys.bitmapFontSmall;
import static com.extractss.game.ExtractSolarSys.energyTexture;
import static com.extractss.game.ExtractSolarSys.inventoryBuildings;
import static com.extractss.game.ExtractSolarSys.metalTexture;
import static com.extractss.game.ExtractSolarSys.moneyTexture;
import static com.extractss.game.ExtractSolarSys.screenManager;
import static com.extractss.game.ExtractSolarSys.upNinePatch;
import static com.extractss.game.utils.Constants.APP_HEIGHT;
import static com.extractss.game.utils.Constants.APP_WIDTH;
import static com.extractss.game.utils.Constants.BOTTOM_BUTTONS_TEXT_Y;
import static com.extractss.game.utils.Constants.BUTTON_HEIGHT;
import static com.extractss.game.utils.Constants.HEIGHT_FOR_RESOURCES;
import static com.extractss.game.utils.Constants.LIST_ELEMENT_PIC_SIZE;
import static com.extractss.game.utils.Constants.LIST_ELEMENT_PIC_X;
import static com.extractss.game.utils.Constants.LIST_ELEMENT_TITLE_X_CENTER;
import static com.extractss.game.utils.Constants.LIST_HEIGHT;
import static com.extractss.game.utils.Constants.LIST_WIDTH;
import static com.extractss.game.utils.Constants.SCALEXY_NEW;
import static com.extractss.game.utils.Constants.SMALLER_SCALE;
import static com.extractss.game.utils.Constants.TOP_BUTTONS_TEXT_Y;
import static com.extractss.game.utils.Operations.parseAndSavePrefsBuildings;
import static com.extractss.game.utils.Operations.totalListHeight;

public class Inventory extends BasicScrollScreen {

    private Building listElementForCycle;
    private float listElementY;
    private float listElementHeight;
    private static float menuX;
    private static float planetX;
    private static float researchX;
    private static float constructX;

    public Inventory(ExtractSolarSys sys, User user) {
        this.sys = sys;
        this.user = user;

        batch = new SpriteBatch();

        myButtons = new ArrayList<>();
        myButtons.add(new MyButtons(0, APP_WIDTH / 2, 0, BUTTON_HEIGHT));
        myButtons.add(new MyButtons(APP_WIDTH / 2, APP_WIDTH / 2, 0, BUTTON_HEIGHT));
        myButtons.add(new MyButtons(0, APP_WIDTH / 2, APP_HEIGHT - BUTTON_HEIGHT,
                BUTTON_HEIGHT));
        myButtons.add(new MyButtons(APP_WIDTH / 2, APP_WIDTH / 2,
                APP_HEIGHT - BUTTON_HEIGHT, BUTTON_HEIGHT));
        myButton = myButtons.get(0);

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

        menuX = APP_WIDTH / 4 - "menu".length() * 11 * SCALEXY_NEW;
        planetX = 3 * APP_WIDTH / 4 - "planet".length() * 11 * SCALEXY_NEW;
        researchX = APP_WIDTH / 4 - "research".length() * 11 * SCALEXY_NEW;
        constructX = 3 * APP_WIDTH / 4 - "construct".length() * 11.3f * SCALEXY_NEW;

        incrementResourcesTimeCheck = new IncrementResourcesTimeCheck(sys, user);

        lastAnimationTime = System.currentTimeMillis();
    }

    @Override
    public void show() {
        /*
        Устанавливаем размер ползунка в зависимости от размера самого списка.
         */
        if (inventoryBuildings.size() > 0) {
            knobHeight = LIST_HEIGHT * LIST_HEIGHT / (totalListHeight(inventoryBuildings));
        } else {
            knobHeight = LIST_HEIGHT;
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        checkMinuteToIncrementResources(); //Проверяем, прошла ли минута, чтобы увеличить значение внутриигровых рерурсов.

        doAnimationChange(); // Производим анимацию фона.

        batch.begin();

        batch.draw(backgroundsOther.get(curScreenAnimation), 0, 0, APP_WIDTH, APP_HEIGHT);

        /*
        Прокручиваем список зданий, если палец скользит по списку.
         */
        scrollTouchMechanic(inventoryBuildings);

        /*
        Отрисовываем каждый элемент списка, который помещается на экран.
        */
        for (int i = 0; i < inventoryBuildings.size(); i++) {
            listElementForCycle = inventoryBuildings.get(i).getBuilding();
            listElementY = inventoryBuildings.get(i).y;
            listElementHeight = inventoryBuildings.get(i).elementHeight;
            if ((listElementY < APP_HEIGHT + BUTTON_HEIGHT && listElementY > -listElementHeight)) {
                upNinePatch.draw(batch, 0, listElementY, LIST_WIDTH, listElementHeight);
                upNinePatch.draw(batch, LIST_ELEMENT_PIC_X - 1,
                        listElementY + listElementHeight / 10 - 1,
                        LIST_ELEMENT_PIC_SIZE + 2, LIST_ELEMENT_PIC_SIZE + 2);
                batch.draw(listElementForCycle.getPicture(), LIST_ELEMENT_PIC_X,
                        listElementY + listElementHeight / 10, LIST_ELEMENT_PIC_SIZE, LIST_ELEMENT_PIC_SIZE);
                bitmapFontSmall.draw(batch, listElementForCycle.getName(),
                        LIST_ELEMENT_TITLE_X_CENTER - listElementForCycle.getName().length() * 11 * SMALLER_SCALE,
                        listElementY - bitmapFontSmall.getCapHeight() + listElementHeight);
                if (listElementForCycle.isProductiveType()) {
                    bitmapFontSmall.draw(batch, listElementForCycle.getUsefulMoney() + "/min",
                            xForPriceListElements,
                            listElementY - 3 * bitmapFontSmall.getCapHeight() + listElementHeight);
                    bitmapFontSmall.draw(batch, listElementForCycle.getUsefulMetal() + "/min",
                            xForPriceListElements,
                            listElementY - 4.5f * bitmapFontSmall.getCapHeight() + listElementHeight);
                    bitmapFontSmall.draw(batch, listElementForCycle.getUsefulEnergy() + "/min",
                            xForPriceListElements,
                            listElementY - 6 * bitmapFontSmall.getCapHeight() + listElementHeight);
                } else {
                    bitmapFontSmall.draw(batch, "+" + listElementForCycle.getUsefulMoney(),
                            xForPriceListElements,
                            listElementY - 3 * bitmapFontSmall.getCapHeight() + listElementHeight);
                    bitmapFontSmall.draw(batch, "+" + listElementForCycle.getUsefulMetal(),
                            xForPriceListElements,
                            listElementY - 4.5f * bitmapFontSmall.getCapHeight() + listElementHeight);
                    bitmapFontSmall.draw(batch, "+" + listElementForCycle.getUsefulEnergy(),
                            xForPriceListElements,
                            listElementY - 6 * bitmapFontSmall.getCapHeight() + listElementHeight);
                }
                batch.draw(moneyTexture, xForIconsListElements, listElementY -
                                4 * bitmapFontSmall.getCapHeight() + listElementHeight, bitmapFontSmall.getCapHeight(),
                        bitmapFontSmall.getCapHeight());
                batch.draw(metalTexture, xForIconsListElements, listElementY -
                                5.5f * bitmapFontSmall.getCapHeight() + listElementHeight, bitmapFontSmall.getCapHeight(),
                        bitmapFontSmall.getCapHeight());
                batch.draw(energyTexture, xForIconsListElements, listElementY -
                                7 * bitmapFontSmall.getCapHeight() + listElementHeight, bitmapFontSmall.getCapHeight(),
                        bitmapFontSmall.getCapHeight());
            }
        }

        limitListSizeOnScreen(); // Ограничиваем размер списка на экране.

        drawResourcesLabel(); // Рисуем табличку с ресурсами игрока.

        checkButtonTouches(); // Проверяем кнопки на нажатие.

        /*
        Отрисовываем ползунок, показывающий место в списке, в котором мы находимся.
        Если список зданий в инвентаре полностью помещается на экран,
        ползунок справа делать не нужно.
         */
        drawAndCheckKnob(inventoryBuildings);

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
    protected void miniWindowActivated(BuildingsInInventory item) {
        screenManager.setMiniWindowInventoryScreen(new MiniWindowInventory(sys, user, item));
        sys.setScreen(screenManager.getMiniWindowInventoryScreen());
    }

    @Override
    protected void miniWindowActivated(int typeRes, boolean isAds){

    }
}
