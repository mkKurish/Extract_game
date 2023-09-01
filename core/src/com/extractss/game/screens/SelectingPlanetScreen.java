package com.extractss.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.extractss.game.ClassesForLists.BuildingsInInventory;
import com.extractss.game.ClassesForLists.ItemSelectingPlanet;
import com.extractss.game.ExtractSolarSys;
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
import static com.extractss.game.ExtractSolarSys.metalTexture;
import static com.extractss.game.ExtractSolarSys.moneyTexture;
import static com.extractss.game.ExtractSolarSys.screenManager;
import static com.extractss.game.ExtractSolarSys.selectingPlanetArrayList;
import static com.extractss.game.ExtractSolarSys.unknownNinePatch;
import static com.extractss.game.ExtractSolarSys.upNinePatch;
import static com.extractss.game.utils.Constants.APP_HEIGHT;
import static com.extractss.game.utils.Constants.APP_WIDTH;
import static com.extractss.game.utils.Constants.BOTTOM_BUTTONS_TEXT_Y;
import static com.extractss.game.utils.Constants.BUTTON_HEIGHT;
import static com.extractss.game.utils.Constants.LIST_ELEMENT_PIC_SIZE;
import static com.extractss.game.utils.Constants.LIST_ELEMENT_PIC_X;
import static com.extractss.game.utils.Constants.LIST_ELEMENT_TITLE_X_CENTER;
import static com.extractss.game.utils.Constants.LIST_HEIGHT;
import static com.extractss.game.utils.Constants.LIST_WIDTH;
import static com.extractss.game.utils.Constants.SCALEXY_NEW;
import static com.extractss.game.utils.Constants.SMALLER_SCALE;
import static com.extractss.game.utils.Constants.TOP_BUTTONS_TEXT_Y;
import static com.extractss.game.utils.Operations.isEnableToBuy;
import static com.extractss.game.utils.Operations.totalListHeight;

public class SelectingPlanetScreen extends BasicScrollScreen {

    private ItemSelectingPlanet listElementForCycle;
    private static float cancelX;
    private static float researchX;

    public SelectingPlanetScreen(ExtractSolarSys sys, User user) {
        this.sys = sys;
        this.user = user;

        batch = new SpriteBatch();

        myButtons = new ArrayList<>();
        myButtons.add(new MyButtons(0, APP_WIDTH, 0, BUTTON_HEIGHT));
        myButtons.add(new MyButtons(0, APP_WIDTH, APP_HEIGHT - BUTTON_HEIGHT, BUTTON_HEIGHT));
        myButton = myButtons.get(0);

        knobHeight = LIST_HEIGHT * LIST_HEIGHT / (totalListHeight(selectingPlanetArrayList));

        yForIcons = APP_HEIGHT - BUTTON_HEIGHT - 7 * bitmapFontSmall.getCapHeight() / 4;
        moneyTextureX = 2 * appWidthToTwentyFour / 3;
        metalTextureX = moneyTextureX + 8 * appWidthToTwentyFour;
        energyTextureX = moneyTextureX + 16 * appWidthToTwentyFour;
        moneyValueX = moneyTextureX + 2 * appWidthToTwentyFour;
        metalValueX = metalTextureX + 2 * appWidthToTwentyFour;
        energyValueX = energyTextureX + 2 * appWidthToTwentyFour;
        heightForIcons = 3 * bitmapFontSmall.getCapHeight() / 2;
        widthForIcons = 3 * bitmapFontSmall.getCapHeight() / 2;
        yForResourcesText = APP_HEIGHT - BUTTON_HEIGHT -bitmapFontSmall.getCapHeight() / 2;

        cancelX = APP_WIDTH / 2f - "cancel".length() * 11 * SCALEXY_NEW;
        researchX = APP_WIDTH / 2f - "research".length() * 11 * SCALEXY_NEW;

        incrementResourcesTimeCheck = new IncrementResourcesTimeCheck(sys, user);

        lastAnimationTime = System.currentTimeMillis();
    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        checkMinuteToIncrementResources(); //Проверяем, прошла ли минута, чтобы увеличить значение внутриигровых рерурсов.

        doAnimationChange(); // Производим анимацию фона.

        batch.begin();

        batch.draw(backgroundsOther.get(curScreenAnimation), 0, 0, APP_WIDTH, APP_HEIGHT);

        scrollTouchMechanic(selectingPlanetArrayList);

        for (int i = 0; i < selectingPlanetArrayList.size(); i++) {
            listElementForCycle = selectingPlanetArrayList.get(i);
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
                if (user.getInvents() >= listElementForCycle.getInventLvl()) {
                    upNinePatch.draw(batch, LIST_ELEMENT_PIC_X - 1,
                            listElementForCycle.y + listElementForCycle.elementHeight / 10 - 1,
                            LIST_ELEMENT_PIC_SIZE + 2, LIST_ELEMENT_PIC_SIZE + 2);
                    batch.draw(listElementForCycle.getPicture(), LIST_ELEMENT_PIC_X + 10,
                            listElementForCycle.y + listElementForCycle.elementHeight / 10 + 10,
                            LIST_ELEMENT_PIC_SIZE - 20, LIST_ELEMENT_PIC_SIZE - 20);
                    bitmapFontSmall.draw(batch, listElementForCycle.getName(),
                            LIST_ELEMENT_TITLE_X_CENTER - listElementForCycle.getName().length() * 11 * SMALLER_SCALE,
                            listElementForCycle.y - bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight);
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
                    unknownNinePatch.draw(batch, LIST_ELEMENT_PIC_X - 1,
                            listElementForCycle.y + listElementForCycle.elementHeight / 10f - 1, LIST_ELEMENT_PIC_SIZE + 2, LIST_ELEMENT_PIC_SIZE + 2);
                    bitmapFontSmall.draw(batch, "?????", LIST_ELEMENT_TITLE_X_CENTER - "?????".length() * 11 * SMALLER_SCALE,
                            listElementForCycle.y - bitmapFontSmall.getCapHeight() + listElementForCycle.elementHeight);
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

        limitListSizeOnScreen(); // Ограничиваем размер списка на экране.

        drawResourcesLabel(); // Рисуем табличку с ресурсами игрока.

        checkButtonTouches(); // Проверяем кнопки на нажатие.

        drawAndCheckKnob(selectingPlanetArrayList); //Отрисовываем ползунок, показывающий место в списке, в котором мы находимся.

        bitmapFont.draw(batch, "cancel", cancelX, BOTTOM_BUTTONS_TEXT_Y);
        bitmapFont.draw(batch, "research", researchX, TOP_BUTTONS_TEXT_Y);

        batch.end();

    }


    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public void buttonActivated(int i) {
        switch (i) {
            case 0:
                sys.setScreen(screenManager.getPlanetScreen());
                break;
            case 1:
                sys.setScreen(screenManager.getResearchScreen());
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
    protected void miniWindowActivated(int typeRes, boolean isAds){

    }
}
