package com.extractss.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.extractss.game.ClassesForLists.BuildingsInInventory;
import com.extractss.game.ExtractSolarSys;
import com.extractss.game.ClassesForLists.ItemResearch;
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
import static com.extractss.game.ExtractSolarSys.incrementingThreadTime;
import static com.extractss.game.ExtractSolarSys.inventTexture;
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
import static com.extractss.game.utils.Constants.MEDIUM_LEST_ELEMENT_HEIGHT;
import static com.extractss.game.utils.Constants.LIST_ELEMENT_PIC_SIZE;
import static com.extractss.game.utils.Constants.LIST_ELEMENT_PIC_X;
import static com.extractss.game.utils.Constants.LIST_ELEMENT_TITLE_X_CENTER;
import static com.extractss.game.utils.Constants.LIST_HEIGHT;
import static com.extractss.game.utils.Constants.LIST_WIDTH;
import static com.extractss.game.utils.Constants.SCALEXY_NEW;
import static com.extractss.game.utils.Constants.SMALLER_SCALE;
import static com.extractss.game.utils.Constants.TOP_BUTTONS_TEXT_Y;
import static com.extractss.game.utils.Constants.Y_RESOURCES_TABLE;
import static com.extractss.game.utils.Operations.isEnableToBuy;
import static com.extractss.game.utils.Operations.isInPlaceMain;
import static com.extractss.game.utils.Operations.parseAndSavePrefsBuildings;
import static com.extractss.game.utils.Operations.totalListHeight;

public class Research extends BasicScrollScreen {
    private ArrayList<ItemResearch> listItems;
    private ItemResearch listElementForCycle;
    private static float menuX;
    private static float inventoryX;
    private static float shopX;

    public Research(ExtractSolarSys sys, User user) {
        this.sys = sys;
        this.user = user;

        batch = new SpriteBatch();

        myButtons = new ArrayList<>();
        myButtons.add(new MyButtons(0, APP_WIDTH / 2, 0, BUTTON_HEIGHT));
        myButtons.add(new MyButtons(APP_WIDTH / 2, APP_WIDTH / 2, 0, BUTTON_HEIGHT));
        myButtons.add(new MyButtons(0, APP_WIDTH, APP_HEIGHT - BUTTON_HEIGHT, BUTTON_HEIGHT));
        myButton = myButtons.get(0);

        listItems = new ArrayList<>();
        listItems.add(new ItemResearch("LvL 1", inventTexture,
                27, 26, 24, 1,
                BUTTON_HEIGHT, MEDIUM_LEST_ELEMENT_HEIGHT));
        listItems.add(new ItemResearch("LvL 2", inventTexture,
                182, 196, 144, 2,
                listItems.get(listItems.size()-1).y + MEDIUM_LEST_ELEMENT_HEIGHT,
                MEDIUM_LEST_ELEMENT_HEIGHT));
        listItems.add(new ItemResearch("LvL 3", inventTexture,
                1263, 1312, 1294, 3,
                listItems.get(listItems.size()-1).y + MEDIUM_LEST_ELEMENT_HEIGHT,
                MEDIUM_LEST_ELEMENT_HEIGHT));
        listItems.add(new ItemResearch("LvL 4", inventTexture,
                3843, 4124, 3035, 4,
                listItems.get(listItems.size()-1).y + MEDIUM_LEST_ELEMENT_HEIGHT,
                MEDIUM_LEST_ELEMENT_HEIGHT));
        listItems.add(new ItemResearch("LvL 5", inventTexture,
                36513, 37945, 37422, 5,
                listItems.get(listItems.size()-1).y + MEDIUM_LEST_ELEMENT_HEIGHT,
                MEDIUM_LEST_ELEMENT_HEIGHT));
        listItems.add(new ItemResearch("LvL 6", inventTexture,
                152089, 162963, 116222, 6,
                listItems.get(listItems.size()-1).y + MEDIUM_LEST_ELEMENT_HEIGHT,
                MEDIUM_LEST_ELEMENT_HEIGHT));
        listItems.add(new ItemResearch("LvL 7", inventTexture,
                273760, 293333, 209200, 7,
                listItems.get(listItems.size()-1).y + MEDIUM_LEST_ELEMENT_HEIGHT,
                MEDIUM_LEST_ELEMENT_HEIGHT));
        listItems.add(new ItemResearch("max LvL", inventTexture,
                629648, 674666, 481160, 8,
                listItems.get(listItems.size()-1).y + MEDIUM_LEST_ELEMENT_HEIGHT,
                MEDIUM_LEST_ELEMENT_HEIGHT));

        knobHeight = LIST_HEIGHT * LIST_HEIGHT / (totalListHeight(listItems));

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

        menuX = APP_WIDTH / 4f - "menu".length() * 11 * SCALEXY_NEW;
        inventoryX = 3 * APP_WIDTH / 4f - "inventory".length() * 11 * SCALEXY_NEW;
        shopX = APP_WIDTH / 2f - "shop".length() * 11 * SCALEXY_NEW;

        incrementResourcesTimeCheck = new IncrementResourcesTimeCheck(sys, user);

        lastAnimationTime = System.currentTimeMillis();
    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

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

        scrollTouchMechanic(listItems);

        for (int i = 0; i < listItems.size(); i++) {
            listElementForCycle = listItems.get(i);
            /*
            Отрисовываем каждый элемент списка, который помещается на экран.
             */
            if ((listElementForCycle.y < APP_HEIGHT + BUTTON_HEIGHT
                    && listElementForCycle.y > -listElementForCycle.elementHeight)) {
                if (isEnableToBuy(user, listElementForCycle)) {
                    upNinePatch.draw(batch, 0, listElementForCycle.y,
                            LIST_WIDTH, listElementForCycle.elementHeight);
                } else {
                    downNinePatch.draw(batch, 0, listElementForCycle.y,
                            LIST_WIDTH, listElementForCycle.elementHeight);
                }
                upNinePatch.draw(batch, LIST_ELEMENT_PIC_X - 1,
                        listElementForCycle.y + listElementForCycle.elementHeight / 10 - 1,
                        LIST_ELEMENT_PIC_SIZE + 2, LIST_ELEMENT_PIC_SIZE + 2);
                batch.draw(listElementForCycle.getPicture(), LIST_ELEMENT_PIC_X,
                        listElementForCycle.y + listElementForCycle.elementHeight / 10,
                        LIST_ELEMENT_PIC_SIZE, LIST_ELEMENT_PIC_SIZE);
                bitmapFontSmall.draw(batch, listElementForCycle.getName(),
                        LIST_ELEMENT_TITLE_X_CENTER - listElementForCycle.getName().length() * 11 * SMALLER_SCALE,
                        listElementForCycle.y - bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight);
                if (user.getInvents() >= listElementForCycle.getInventLvl() - 1) {
                    bitmapFontSmall.draw(batch, String.valueOf(listElementForCycle.getCostMoney()),
                            xForPriceListElements,
                            listElementForCycle.y - 3 * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight);
                    bitmapFontSmall.draw(batch, String.valueOf(listElementForCycle.getCostMetal()),
                            xForPriceListElements,
                            listElementForCycle.y - 4.5f * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight);
                    bitmapFontSmall.draw(batch, String.valueOf(listElementForCycle.getCostEnergy()),
                            xForPriceListElements,
                            listElementForCycle.y - 6 * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight);
                } else {
                    bitmapFontSmall.draw(batch, "???",
                            xForPriceListElements, listElementForCycle.y -
                                    3 * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight);
                    bitmapFontSmall.draw(batch, "???",
                            xForPriceListElements, listElementForCycle.y -
                                    4.5f * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight);
                    bitmapFontSmall.draw(batch, "???",
                            xForPriceListElements, listElementForCycle.y -
                                    6 * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight);
                }
                batch.draw(moneyTexture, xForIconsListElements, listElementForCycle.y -
                                4 * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight, bitmapFontSmall.getCapHeight(),
                        bitmapFontSmall.getCapHeight());
                batch.draw(metalTexture, xForIconsListElements, listElementForCycle.y -
                                5.5f * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight, bitmapFontSmall.getCapHeight(),
                        bitmapFontSmall.getCapHeight());
                batch.draw(energyTexture, xForIconsListElements, listElementForCycle.y -
                                7 * bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight, bitmapFontSmall.getCapHeight(),
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
        batch.draw(new TextureRegion(backgroundsOther.get(curScreenAnimation), 0,
                        0, backgroundsOther.get(curScreenAnimation).getWidth(),
                        backgroundsOther.get(curScreenAnimation).getHeight() / 9), 0,
                0, APP_WIDTH, BUTTON_HEIGHT);

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
         */
        progressBarBackNinePatch.draw(batch, KNOB_X, BUTTON_HEIGHT, KNOB_WIDTH, LIST_HEIGHT);
        progressBarKnobNinePatch.draw(batch, KNOB_X, BUTTON_HEIGHT - knobHeight *
                (listItems.get(0).y - BUTTON_HEIGHT) / LIST_HEIGHT, KNOB_WIDTH, knobHeight);

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
            deltaFirstElementY = -listItems.get(0).y + (LIST_HEIGHT
                    * (BUTTON_HEIGHT - touchedY + knobHeight / 2) / knobHeight + BUTTON_HEIGHT);
            for (int i = 0; i < listItems.size(); i++) {
                listItems.get(i).y += deltaFirstElementY;
            }
        }

        bitmapFont.draw(batch, "menu", menuX, BOTTOM_BUTTONS_TEXT_Y);
        bitmapFont.draw(batch, "inventory", inventoryX, BOTTOM_BUTTONS_TEXT_Y);
        bitmapFont.draw(batch, "shop", shopX, TOP_BUTTONS_TEXT_Y);

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
                sys.setScreen(screenManager.getInventoryScreen());
                break;
            case 2:
                sys.setScreen(screenManager.getShopScreen());
                break;
        }
    }

    @Override
    protected void miniWindowActivated(Building building) {

    }

    @Override
    protected void miniWindowActivated(BuildingsInInventory buildingInInventory) {

    }
}
