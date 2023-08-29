package com.extractss.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.extractss.game.ClassesForLists.ItemResearch;
import com.extractss.game.ClassesForLists.ItemSelectingPlanet;
import com.extractss.game.ClassesForLists.ItemShop;
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
import static com.extractss.game.ExtractSolarSys.buttonDownSound;
import static com.extractss.game.ExtractSolarSys.buttonUpSound;
import static com.extractss.game.ExtractSolarSys.energyTexture;
import static com.extractss.game.ExtractSolarSys.incrementingThreadTime;
import static com.extractss.game.ExtractSolarSys.inventoryBuildings;
import static com.extractss.game.ExtractSolarSys.listButtonDown;
import static com.extractss.game.ExtractSolarSys.listButtonUp;
import static com.extractss.game.ExtractSolarSys.metalTexture;
import static com.extractss.game.ExtractSolarSys.moneyTexture;
import static com.extractss.game.ExtractSolarSys.progressBarKnobNinePatch;
import static com.extractss.game.ExtractSolarSys.progressBarBackNinePatch;
import static com.extractss.game.ExtractSolarSys.screenManager;
import static com.extractss.game.ExtractSolarSys.upNinePatch;
import static com.extractss.game.utils.Constants.APP_HEIGHT;
import static com.extractss.game.utils.Constants.APP_WIDTH;
import static com.extractss.game.utils.Constants.BOTTOM_BUTTONS_TEXT_Y;
import static com.extractss.game.utils.Constants.BUTTON_HEIGHT;
import static com.extractss.game.utils.Constants.HEIGHT_FOR_RESOURCES;
import static com.extractss.game.utils.Constants.HEIGHT_RESOURCES_TABLE;
import static com.extractss.game.utils.Constants.KNOB_WIDTH;
import static com.extractss.game.utils.Constants.KNOB_X;
import static com.extractss.game.utils.Constants.SIDE_INDENT;
import static com.extractss.game.utils.Constants.LIST_ELEMENT_HEIGHT;
import static com.extractss.game.utils.Constants.LIST_ELEMENT_PIC_SIZE;
import static com.extractss.game.utils.Constants.LIST_ELEMENT_PIC_X;
import static com.extractss.game.utils.Constants.LIST_ELEMENT_TITLE_X_CENTER;
import static com.extractss.game.utils.Constants.LIST_HEIGHT;
import static com.extractss.game.utils.Constants.LIST_WIDTH;
import static com.extractss.game.utils.Constants.SCALEXY_NEW;
import static com.extractss.game.utils.Constants.SMALLER_SCALE;
import static com.extractss.game.utils.Constants.TOP_BUTTONS_TEXT_Y;
import static com.extractss.game.utils.Constants.Y_RESOURCES_TABLE;
import static com.extractss.game.utils.Operations.isInPlaceMain;
import static com.extractss.game.utils.Operations.parseAndSavePrefsBuildings;

public class Inventory extends BasicScrollScreen {

    private Building listElementForCycle;
    private long lastListTouchTime = 1000;
    static long lastPanelTouchTime = 0;
    private static boolean listTouchMode;
    private float listElementY;
    private static float menuX;
    private static float planetX;
    private static float researchX;
    private static float constructX;

    public Inventory(ExtractSolarSys sys, User user) {
        this.sys = sys;
        this.user = user;

        batch = new SpriteBatch();

        listTouchMode = false;

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
        xForPriceListElements = LIST_ELEMENT_HEIGHT + 3 * bitmapFontSmall.getCapHeight() / 2;
        xForIconsListElements = LIST_ELEMENT_HEIGHT + bitmapFontSmall.getCapHeight() / 2;

        menuX = APP_WIDTH / 4 - "menu".length() * 11 * SCALEXY_NEW;
        planetX = 3 * APP_WIDTH / 4 - "planet".length() * 11 * SCALEXY_NEW;
        researchX = APP_WIDTH / 4 - "research".length() * 11 * SCALEXY_NEW;
        constructX = 3 * APP_WIDTH / 4 - "construct".length() * 11.3f * SCALEXY_NEW;

        incrementResourcesTimeCheck = new IncrementResourcesTimeCheck(sys, user);

        lastAnimationTime = System.currentTimeMillis();
    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        /*
        Устанавливаем размер ползунка в зависимости от размера самого списка.
         */
        if (inventoryBuildings.size() > 0) {
            knobHeight = LIST_HEIGHT * LIST_HEIGHT / (inventoryBuildings.size() * LIST_ELEMENT_HEIGHT);
        } else {
            knobHeight = LIST_HEIGHT;
        }

        /*
        Проверяем, прошла ли минута, чтобы увеличить значение внутриигровых рерурсов.
         */
        if (System.currentTimeMillis() - incrementingThreadTime > 60000) {
            incrementResourcesTimeCheck.test();
            incrementingThreadTime = System.currentTimeMillis();
        }

        /*
        Производим анимацию фона.
         */
        if (System.currentTimeMillis() - lastAnimationTime >= 550) {

            switch (curScreenAnimation) {
                case 0:
                    curScreenAnimation = 1;
                    break;
                case 1:
                    curScreenAnimation = 0;
                    break;
            }
            lastAnimationTime = System.currentTimeMillis();
        }

        batch.begin();

        batch.draw(backgroundsOther.get(curScreenAnimation), 0, 0, APP_WIDTH, APP_HEIGHT);

        /*
        Прокручиваем список зданий, если палец скользит по списку.
         */
        scrollTouchMechanic(inventoryBuildings);

        for (int i = 0; i < inventoryBuildings.size(); i++) {
            listElementForCycle = inventoryBuildings.get(i).getBuilding();
            listElementY = inventoryBuildings.get(i).getY();
            /*
            Отрисовываем каждый элемент списка, который помещается на экран.
             */
            if ((listElementY < APP_HEIGHT + BUTTON_HEIGHT && listElementY > -LIST_ELEMENT_HEIGHT)) {
                upNinePatch.draw(batch, 0, listElementY, LIST_WIDTH, LIST_ELEMENT_HEIGHT);
                upNinePatch.draw(batch, LIST_ELEMENT_PIC_X - 1,
                        listElementY + LIST_ELEMENT_HEIGHT / 10 - 1,
                        LIST_ELEMENT_PIC_SIZE + 2, LIST_ELEMENT_PIC_SIZE + 2);
                batch.draw(listElementForCycle.getPicture(), LIST_ELEMENT_PIC_X,
                        listElementY + LIST_ELEMENT_HEIGHT / 10, LIST_ELEMENT_PIC_SIZE, LIST_ELEMENT_PIC_SIZE);
                bitmapFontSmall.draw(batch, listElementForCycle.getName(),
                        LIST_ELEMENT_TITLE_X_CENTER - listElementForCycle.getName().length() * 11 * SMALLER_SCALE,
                        listElementY - bitmapFontSmall.getCapHeight() + LIST_ELEMENT_HEIGHT);
                if (listElementForCycle.isProductiveType()) {
                    bitmapFontSmall.draw(batch, listElementForCycle.getUsefulMoney() + "/min",
                            xForPriceListElements,
                            listElementY - 3 * bitmapFontSmall.getCapHeight() + LIST_ELEMENT_HEIGHT);
                    bitmapFontSmall.draw(batch, listElementForCycle.getUsefulMetal() + "/min",
                            xForPriceListElements,
                            listElementY - 4.5f * bitmapFontSmall.getCapHeight() + LIST_ELEMENT_HEIGHT);
                    bitmapFontSmall.draw(batch, listElementForCycle.getUsefulEnergy() + "/min",
                            xForPriceListElements,
                            listElementY - 6 * bitmapFontSmall.getCapHeight() + LIST_ELEMENT_HEIGHT);
                } else {
                    bitmapFontSmall.draw(batch, "+" + listElementForCycle.getUsefulMoney(),
                            xForPriceListElements,
                            listElementY - 3 * bitmapFontSmall.getCapHeight() + LIST_ELEMENT_HEIGHT);
                    bitmapFontSmall.draw(batch, "+" + listElementForCycle.getUsefulMetal(),
                            xForPriceListElements,
                            listElementY - 4.5f * bitmapFontSmall.getCapHeight() + LIST_ELEMENT_HEIGHT);
                    bitmapFontSmall.draw(batch, "+" + listElementForCycle.getUsefulEnergy(),
                            xForPriceListElements,
                            listElementY - 6 * bitmapFontSmall.getCapHeight() + LIST_ELEMENT_HEIGHT);
                }
                batch.draw(moneyTexture, xForIconsListElements, listElementY -
                                4 * bitmapFontSmall.getCapHeight() + LIST_ELEMENT_HEIGHT, bitmapFontSmall.getCapHeight(),
                        bitmapFontSmall.getCapHeight());
                batch.draw(metalTexture, xForIconsListElements, listElementY -
                                5.5f * bitmapFontSmall.getCapHeight() + LIST_ELEMENT_HEIGHT, bitmapFontSmall.getCapHeight(),
                        bitmapFontSmall.getCapHeight());
                batch.draw(energyTexture, xForIconsListElements, listElementY -
                                7 * bitmapFontSmall.getCapHeight() + LIST_ELEMENT_HEIGHT, bitmapFontSmall.getCapHeight(),
                        bitmapFontSmall.getCapHeight());
            }
        }

        /*
        Ограничиваем размер списка на экране.
         */
        batch.draw(new TextureRegion(backgroundsOther.get(curScreenAnimation), 0,
                        19 * backgroundsOther.get(curScreenAnimation).getHeight() / 9,
                        backgroundsOther.get(curScreenAnimation).getWidth(),
                        backgroundsOther.get(curScreenAnimation).getHeight() / 9), 0,
                APP_HEIGHT - BUTTON_HEIGHT - 2 * bitmapFontSmall.getCapHeight(), APP_WIDTH,
                BUTTON_HEIGHT + 2 * bitmapFont.getCapHeight());
        batch.draw(new TextureRegion(backgroundsOther.get(curScreenAnimation), 0, 0,
                        backgroundsOther.get(curScreenAnimation).getWidth(),
                        backgroundsOther.get(curScreenAnimation).getHeight() / 9), 0, 0,
                APP_WIDTH, BUTTON_HEIGHT);

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

        checkButtonTouches(); // Проверяем кнопки на нажатие.

        /*
        Отрисовываем ползунок, показывающий место в списке, в котором мы находимся.
        Если список зданий в инвентаре полностью помещается на экран,
        ползунок справа делать не нужно.
         */
        progressBarBackNinePatch.draw(batch, KNOB_X, BUTTON_HEIGHT, KNOB_WIDTH, LIST_HEIGHT);
        if (inventoryBuildings.size() * LIST_ELEMENT_HEIGHT > LIST_HEIGHT) {
            progressBarKnobNinePatch.draw(batch, KNOB_X, BUTTON_HEIGHT - knobHeight *
                    (inventoryBuildings.get(0).getY() - BUTTON_HEIGHT) / LIST_HEIGHT, KNOB_WIDTH, knobHeight);
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
                deltaFirstElementY = -inventoryBuildings.get(0).getY() + (LIST_HEIGHT *
                        (BUTTON_HEIGHT - touchedY + knobHeight / 2) / knobHeight + BUTTON_HEIGHT);
                for (int i = 0; i < inventoryBuildings.size(); i++) {
                    inventoryBuildings.get(i).setY(inventoryBuildings.get(i).getY() + deltaFirstElementY);
                }
            }
        }

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
    protected void miniWindowActivated(ItemSelectingPlanet building) {

    }

}
