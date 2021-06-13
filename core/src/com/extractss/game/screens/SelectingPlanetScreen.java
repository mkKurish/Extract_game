package com.extractss.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.extractss.game.ClassesForLists.ItemSelectingPlanet;
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
import static com.extractss.game.ExtractSolarSys.currentPlanet;
import static com.extractss.game.ExtractSolarSys.downNinePatch;
import static com.extractss.game.ExtractSolarSys.energyTexture;
import static com.extractss.game.ExtractSolarSys.incrementingThreadTime;
import static com.extractss.game.ExtractSolarSys.inventTexture;
import static com.extractss.game.ExtractSolarSys.listButtonDown;
import static com.extractss.game.ExtractSolarSys.listButtonUp;
import static com.extractss.game.ExtractSolarSys.metalTexture;
import static com.extractss.game.ExtractSolarSys.moneyTexture;
import static com.extractss.game.ExtractSolarSys.progressBarBackNinePatch;
import static com.extractss.game.ExtractSolarSys.progressBarKnobNinePatch;
import static com.extractss.game.ExtractSolarSys.screenManager;
import static com.extractss.game.ExtractSolarSys.selectingPlanetArrayList;
import static com.extractss.game.ExtractSolarSys.successSound;
import static com.extractss.game.ExtractSolarSys.unknownNinePatch;
import static com.extractss.game.ExtractSolarSys.upNinePatch;
import static com.extractss.game.utils.Constants.APP_HEIGHT;
import static com.extractss.game.utils.Constants.APP_WIDTH;
import static com.extractss.game.utils.Constants.BOTTOM_BUTTONS_TEXT_Y;
import static com.extractss.game.utils.Constants.BUTTON_HEIGHT;
import static com.extractss.game.utils.Constants.HEIGHT_RESOURCES_TABLE;
import static com.extractss.game.utils.Constants.KNOB_WIDTH;
import static com.extractss.game.utils.Constants.KNOB_X;
import static com.extractss.game.utils.Constants.LEFT_INDENT;
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
import static com.extractss.game.utils.Operations.isEnableToBuy;
import static com.extractss.game.utils.Operations.isInPlace;
import static com.extractss.game.utils.Operations.isInPlaceMain;
import static com.extractss.game.utils.Operations.parseAndSavePrefsBuildings;

public class SelectingPlanetScreen implements MyScreen {
    private ExtractSolarSys sys;
    User user;

    private ItemSelectingPlanet listElementForCycle;

    private Batch batch;

    private ArrayList<MyButtons> myButtons;
    private MyButtons myButton;

    private float touchedX;
    private float touchedY;
    private float touchedListY;
    private long lastTouchTime = 0;
    private long lastListTouchTime = 1000;
    static long lastPanelTouchTime = 0;
    private long lastAnimationTime;
    private int curScreenAnimation = 0;
    private float knobHeight;
    private static float yForIcons;
    private static float heightForIcons;
    private static float widthForIcons;
    private static float yForResourcesText;
    private static float xForPriceListElements;
    private static float xForIconsListElements;
    private static float appWidthToTwentyFour = APP_WIDTH / 24;
    private static float firstElementY;
    private static float lastElementY;
    private static float boarderUp;
    private static boolean listTouchMode;
    private static float resCoord;
    private static float moneyTextureX;
    private static float metalTextureX;
    private static float energyTextureX;
    private static float moneyValueX;
    private static float metalValueX;
    private static float energyValueX;
    private static float deltaFirstElementY;
    private static float cancelX;
    private static float thisListHeight;
    private static float thisResourcesLabelY;

    IncrementResourcesTimeCheck incrementResourcesTimeCheck;

    public SelectingPlanetScreen(ExtractSolarSys sys, User user) {
        this.sys = sys;
        this.user = user;

        batch = new SpriteBatch();

        myButtons = new ArrayList<>();
        myButtons.add(new MyButtons(0, APP_WIDTH, 0, BUTTON_HEIGHT));
        myButton = myButtons.get(0);

        thisListHeight = LIST_HEIGHT + BUTTON_HEIGHT;
        thisResourcesLabelY = Y_RESOURCES_TABLE + BUTTON_HEIGHT;

        knobHeight = thisListHeight * thisListHeight / (selectingPlanetArrayList.size() * LIST_ELEMENT_HEIGHT);

        yForIcons = APP_HEIGHT - 7 * bitmapFontSmall.getCapHeight() / 4;
        moneyTextureX = 2 * appWidthToTwentyFour / 3;
        metalTextureX = moneyTextureX + 8 * appWidthToTwentyFour;
        energyTextureX = moneyTextureX + 16 * appWidthToTwentyFour;
        moneyValueX = moneyTextureX + 2 * appWidthToTwentyFour;
        metalValueX = metalTextureX + 2 * appWidthToTwentyFour;
        energyValueX = energyTextureX + 2 * appWidthToTwentyFour;
        heightForIcons = 3 * bitmapFontSmall.getCapHeight() / 2;
        widthForIcons = 3 * bitmapFontSmall.getCapHeight() / 2;
        yForResourcesText = APP_HEIGHT - bitmapFontSmall.getCapHeight() / 2;
        xForPriceListElements = LEFT_INDENT + LIST_ELEMENT_HEIGHT + 3 * bitmapFontSmall.getCapHeight() / 2;
        xForIconsListElements = LEFT_INDENT + LIST_ELEMENT_HEIGHT + bitmapFontSmall.getCapHeight() / 2;

        boarderUp = APP_HEIGHT - LIST_ELEMENT_HEIGHT - 2 * bitmapFontSmall.getCapHeight();

        cancelX = APP_WIDTH / 2f - "cancel".length() * 11 * SCALEXY_NEW;

        incrementResourcesTimeCheck = new IncrementResourcesTimeCheck(sys, user);

        lastAnimationTime = System.currentTimeMillis();
    }

    @Override
    public void show() {

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

        /*
        Прокручиваем список исследований, если палец скользит по списку.
         */
        firstElementY = selectingPlanetArrayList.get(0).y;
        lastElementY = selectingPlanetArrayList.get(selectingPlanetArrayList.size() - 1).y;
        if (!listTouchMode && Gdx.input.isTouched() && isInPlaceMain(Gdx.input.getX(),
                Gdx.graphics.getHeight() - Gdx.input.getY(), LEFT_INDENT, BUTTON_HEIGHT,
                LIST_WIDTH, thisListHeight)) {
            if (System.currentTimeMillis() - lastListTouchTime < 50) {
                resCoord = (-touchedListY + Gdx.graphics.getHeight() - Gdx.input.getY()) * 2;
                if (firstElementY + resCoord > BUTTON_HEIGHT) {
                    resCoord -= firstElementY + resCoord - BUTTON_HEIGHT;
                } else if (lastElementY + resCoord < boarderUp) {
                    resCoord += boarderUp - lastElementY - resCoord;
                }
                for (int i = 0; i < selectingPlanetArrayList.size(); i++) {
                    selectingPlanetArrayList.get(i).y += resCoord;
                }
                lastListTouchTime = System.currentTimeMillis() - lastListTouchTime;
            } else {
                lastListTouchTime = System.currentTimeMillis();
            }
            touchedListY = Gdx.graphics.getHeight() - Gdx.input.getY();
        } else {
            if (lastElementY < boarderUp) {
                for (int i = 0; i < selectingPlanetArrayList.size(); i++) {
                    selectingPlanetArrayList.get(i).y += boarderUp - lastElementY + 1;
                }
            } else if (firstElementY > BUTTON_HEIGHT) {
                for (int i = 0; i < selectingPlanetArrayList.size(); i++) {
                    selectingPlanetArrayList.get(i).y += BUTTON_HEIGHT - firstElementY;
                }
            }
        }

        for (int i = 0; i < selectingPlanetArrayList.size(); i++) {
            listElementForCycle = selectingPlanetArrayList.get(i);
            /*
            Если в режиме нажатий коснулись элемента списка, планета покупается
            (и/или устанавливается, если уже куплена).
             */
            if (listTouchMode
                    && Gdx.input.isTouched()
                    && isInPlaceMain(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY(),
                    LEFT_INDENT, listElementForCycle.y, LIST_WIDTH, LIST_ELEMENT_HEIGHT)
                    && Gdx.input.getY() < (thisListHeight + BUTTON_HEIGHT)
                    && Gdx.input.getY() > BUTTON_HEIGHT && isEnableToBuy(user, listElementForCycle)) {
                lastTouchTime = System.currentTimeMillis();
                touchedX = Gdx.input.getX();
                touchedY = Gdx.graphics.getHeight() - Gdx.input.getY();
                user.setMoney(user.getMoney() - listElementForCycle.getCostMoney());
                user.setMetal(user.getMetal() - listElementForCycle.getCostMetal());
                user.setEnergy(user.getEnergy() - listElementForCycle.getCostEnergy());
                selectingPlanetArrayList.get(i).setCostMoney(0);
                selectingPlanetArrayList.get(i).setCostMetal(0);
                selectingPlanetArrayList.get(i).setCostEnergy(0);
                currentPlanet = i;
                parseAndSavePrefsBuildings(user);
                successSound.play(user.getSoundsVolume());
                listTouchMode = false;
                screenManager.setPlanetScreen(new Planet(sys, user));
                sys.setScreen(screenManager.getPlanetScreen());
            }
            /*
            Отрисовываем каждый элемент списка, который помещается на экран.
             */
            if ((listElementForCycle.y < APP_HEIGHT + BUTTON_HEIGHT
                    && listElementForCycle.y > -LIST_ELEMENT_HEIGHT)) {
                if (isEnableToBuy(user, listElementForCycle)) {
                    upNinePatch.draw(batch, LEFT_INDENT, listElementForCycle.y,
                            LIST_WIDTH, LIST_ELEMENT_HEIGHT);
                } else {
                    downNinePatch.draw(batch, LEFT_INDENT, listElementForCycle.y,
                            LIST_WIDTH, LIST_ELEMENT_HEIGHT);
                }
                if (user.getInvents() >= listElementForCycle.getInventLvl()) {
                    upNinePatch.draw(batch, LIST_ELEMENT_PIC_X - 1,
                            listElementForCycle.y + LIST_ELEMENT_HEIGHT / 10 - 1,
                            LIST_ELEMENT_PIC_SIZE + 2, LIST_ELEMENT_PIC_SIZE + 2);
                    batch.draw(listElementForCycle.getPicture(), LIST_ELEMENT_PIC_X + 10,
                            listElementForCycle.y + LIST_ELEMENT_HEIGHT / 10 + 10,
                            LIST_ELEMENT_PIC_SIZE - 20, LIST_ELEMENT_PIC_SIZE - 20);
                    bitmapFontSmall.draw(batch, listElementForCycle.getName(),
                            LIST_ELEMENT_TITLE_X_CENTER - listElementForCycle.getName().length() * 11 * SMALLER_SCALE,
                            listElementForCycle.y - bitmapFontSmall.getCapHeight() + LIST_ELEMENT_HEIGHT);
                    bitmapFontSmall.draw(batch, String.valueOf(listElementForCycle.getCostMoney()),
                            xForPriceListElements,
                            listElementForCycle.y - 3 * bitmapFontSmall.getCapHeight() + LIST_ELEMENT_HEIGHT);
                    bitmapFontSmall.draw(batch, String.valueOf(listElementForCycle.getCostMetal()),
                            xForPriceListElements,
                            listElementForCycle.y - 4.5f * bitmapFontSmall.getCapHeight() + LIST_ELEMENT_HEIGHT);
                    bitmapFontSmall.draw(batch, String.valueOf(listElementForCycle.getCostEnergy()),
                            xForPriceListElements,
                            listElementForCycle.y - 6 * bitmapFontSmall.getCapHeight() + LIST_ELEMENT_HEIGHT);
                } else {
                    unknownNinePatch.draw(batch, LIST_ELEMENT_PIC_X - 1,
                            listElementForCycle.y + LIST_ELEMENT_HEIGHT / 10f - 1, LIST_ELEMENT_PIC_SIZE + 2, LIST_ELEMENT_PIC_SIZE + 2);
                    bitmapFontSmall.draw(batch, "?????", LIST_ELEMENT_TITLE_X_CENTER - "?????".length() * 11 * SMALLER_SCALE,
                            listElementForCycle.y - bitmapFontSmall.getCapHeight() + LIST_ELEMENT_HEIGHT);
                    bitmapFontSmall.draw(batch, "???",
                            xForPriceListElements, listElementForCycle.y -
                                    3 * bitmapFontSmall.getCapHeight() + LIST_ELEMENT_HEIGHT);
                    bitmapFontSmall.draw(batch, "???",
                            xForPriceListElements, listElementForCycle.y -
                                    4.5f * bitmapFontSmall.getCapHeight() + LIST_ELEMENT_HEIGHT);
                    bitmapFontSmall.draw(batch, "???",
                            xForPriceListElements, listElementForCycle.y -
                                    6 * bitmapFontSmall.getCapHeight() + LIST_ELEMENT_HEIGHT);
                }
                batch.draw(moneyTexture, xForIconsListElements, listElementForCycle.y -
                                4 * bitmapFontSmall.getCapHeight() + LIST_ELEMENT_HEIGHT, bitmapFontSmall.getCapHeight(),
                        bitmapFontSmall.getCapHeight());
                batch.draw(metalTexture, xForIconsListElements, listElementForCycle.y -
                                5.5f * bitmapFontSmall.getCapHeight() + LIST_ELEMENT_HEIGHT, bitmapFontSmall.getCapHeight(),
                        bitmapFontSmall.getCapHeight());
                batch.draw(energyTexture, xForIconsListElements, listElementForCycle.y -
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
                APP_HEIGHT - 2 * bitmapFontSmall.getCapHeight(), APP_WIDTH,
                BUTTON_HEIGHT + 2 * bitmapFont.getCapHeight());
        batch.draw(new TextureRegion(backgroundsOther.get(curScreenAnimation), 0,
                        0, backgroundsOther.get(curScreenAnimation).getWidth(),
                        backgroundsOther.get(curScreenAnimation).getHeight() / 9), 0,
                0, APP_WIDTH, BUTTON_HEIGHT);

        upNinePatch.draw(batch, 0, thisResourcesLabelY, APP_WIDTH, HEIGHT_RESOURCES_TABLE);
        batch.draw(moneyTexture, moneyTextureX, yForIcons, widthForIcons, heightForIcons);
        batch.draw(metalTexture, metalTextureX, yForIcons, widthForIcons, heightForIcons);
        batch.draw(energyTexture, energyTextureX, yForIcons, widthForIcons, heightForIcons);
        bitmapFontSmall.draw(batch, String.valueOf(user.getMoney()),
                moneyValueX, yForResourcesText);
        bitmapFontSmall.draw(batch, String.valueOf(user.getMetal()),
                metalValueX, yForResourcesText);
        bitmapFontSmall.draw(batch, String.valueOf(user.getEnergy()),
                energyValueX, yForResourcesText);

        /*
        Проверяем кнопки на нажатие.
         */
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

        /*
        Отрисовываем ползунок, показывающий место в списке, в котором мы находимся.
         */
        progressBarBackNinePatch.draw(batch, KNOB_X, BUTTON_HEIGHT, KNOB_WIDTH, thisListHeight);
        progressBarKnobNinePatch.draw(batch, KNOB_X, BUTTON_HEIGHT - knobHeight *
                (selectingPlanetArrayList.get(0).y - BUTTON_HEIGHT) / thisListHeight, KNOB_WIDTH, knobHeight);

        /*
        Если нажать на ползунок, мы переместимся в ту часть списка, в какую часть ползунка мы нажали.
         */
        if (Gdx.input.isTouched() && isInPlaceMain(touchedX, touchedY, KNOB_X, BUTTON_HEIGHT,
                KNOB_WIDTH, thisListHeight)) {
            if (touchedY + knobHeight / 2 > thisListHeight + BUTTON_HEIGHT) {
                touchedY = thisListHeight + BUTTON_HEIGHT - knobHeight / 2;
            } else if (touchedY - knobHeight / 2 < BUTTON_HEIGHT) {
                touchedY = BUTTON_HEIGHT + knobHeight / 2;
            }
            deltaFirstElementY = -selectingPlanetArrayList.get(0).y + (thisListHeight
                    * (BUTTON_HEIGHT - touchedY + knobHeight / 2) / knobHeight + BUTTON_HEIGHT);
            for (int i = 0; i < selectingPlanetArrayList.size(); i++) {
                selectingPlanetArrayList.get(i).y += deltaFirstElementY;
            }
        }

        /*
        Если нажата кнопка слева от списка, то переключается режим:
        режим пролистывания - режим нажатия на элемент.
         */
        if (Gdx.input.isTouched() && isInPlaceMain(Gdx.input.getX(),
                Gdx.graphics.getHeight() - Gdx.input.getY(), 0, BUTTON_HEIGHT, LEFT_INDENT, thisListHeight)
                && System.currentTimeMillis() - lastPanelTouchTime > 300) {
            if (!listTouchMode) buttonDownSound.play(user.getSoundsVolume());
            else buttonUpSound.play(user.getSoundsVolume());
            listTouchMode = !listTouchMode;
            lastPanelTouchTime = System.currentTimeMillis();
        }

        /*
        Отрисовываем кнопку режимов слева от списка.
         */
        if (listTouchMode) {
            listButtonDown.draw(batch, 0, BUTTON_HEIGHT, LEFT_INDENT, thisListHeight);
        } else {
            listButtonUp.draw(batch, 0, BUTTON_HEIGHT, LEFT_INDENT, thisListHeight);
        }

        bitmapFont.draw(batch, "cancel", cancelX, BOTTOM_BUTTONS_TEXT_Y);

        batch.end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void buttonActivated(int i) {
        switch (i) {
            case 0:
                sys.setScreen(screenManager.getPlanetScreen());
                break;
        }
    }
}
