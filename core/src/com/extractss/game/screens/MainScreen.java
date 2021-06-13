package com.extractss.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.extractss.game.ExtractSolarSys;
import com.extractss.game.SimpleClasses.MyButtons;
import com.extractss.game.SimpleClasses.User;
import com.extractss.game.utils.Constants;

import java.util.ArrayList;

import static com.extractss.game.ExtractSolarSys.backgroundsMain;
import static com.extractss.game.ExtractSolarSys.bitmapFont;
import static com.extractss.game.ExtractSolarSys.buttonDownSound;
import static com.extractss.game.ExtractSolarSys.buttonUpSound;
import static com.extractss.game.ExtractSolarSys.downNinePatch;
import static com.extractss.game.ExtractSolarSys.gameLogo;
import static com.extractss.game.ExtractSolarSys.helpButtonSign;
import static com.extractss.game.ExtractSolarSys.screenManager;
import static com.extractss.game.ExtractSolarSys.settingsButtonSign;
import static com.extractss.game.ExtractSolarSys.upNinePatch;
import static com.extractss.game.utils.Constants.APP_HEIGHT;
import static com.extractss.game.utils.Constants.APP_WIDTH;
import static com.extractss.game.utils.Constants.BUTTONS_VOID;
import static com.extractss.game.utils.Constants.BUTTON_HEIGHT;
import static com.extractss.game.utils.Constants.BUTTON_WIDTH;
import static com.extractss.game.utils.Constants.SCALEXY_NEW;
import static com.extractss.game.utils.Operations.isInPlace;
import static com.extractss.game.utils.Operations.parseAndSavePrefsBuildings;

public class MainScreen implements MyScreen {
    ExtractSolarSys sys;
    User user;

    private Batch batch;

    private ArrayList<MyButtons> myButtons;
    private MyButtons myButton;

    private float touchedX;
    private float touchedY;
    private long lastTouchTime = 0;
    private long lastAnimationTime;
    private int curScreenAnimation = 0;
    private static float aspectRatioLogo;
    private static float aspectRatioReal;
    private static float remainingSpaceY;
    private static float remainingSpaceHigh;
    private static float logoX;
    private static float logoY;
    private static float logoWidth;
    private static float logoHigh;
    private static float helpButtonSignX;
    private static float settingsButtonSignX;
    private static float helpAndSettingsButtonSignY;
    private static float helpAndSettingsButtonSignWidthAndHigh;
    private static float planetTextX;
    private static float planetTextY;
    private static float constructTextX;
    private static float constructTextY;
    private static float researchTextX;
    private static float researchTextY;
    private static float shopTextX;
    private static float shopTextY;

    public MainScreen(ExtractSolarSys extractSolarSys, User user) {
        sys = extractSolarSys;
        this.user = user;

        batch = new SpriteBatch();

        myButtons = new ArrayList<>();
        myButtons.add(new MyButtons(APP_WIDTH / 10, BUTTON_WIDTH, BUTTONS_VOID, BUTTON_HEIGHT));
        myButtons.add(new MyButtons(APP_WIDTH / 10, BUTTON_WIDTH,
                BUTTONS_VOID * 2 + BUTTON_HEIGHT, BUTTON_HEIGHT));
        myButtons.add(new MyButtons(APP_WIDTH / 10, BUTTON_WIDTH,
                BUTTONS_VOID * 3 + BUTTON_HEIGHT * 2, BUTTON_HEIGHT));
        myButtons.add(new MyButtons(APP_WIDTH / 10, BUTTON_WIDTH,
                BUTTONS_VOID * 4 + BUTTON_HEIGHT * 3, BUTTON_HEIGHT));
        myButtons.add(new MyButtons(APP_WIDTH / 10, BUTTON_HEIGHT,
                APP_HEIGHT - BUTTONS_VOID - BUTTON_HEIGHT, BUTTON_HEIGHT));
        myButtons.add(new MyButtons(9 * APP_WIDTH / 10 - BUTTON_HEIGHT, BUTTON_HEIGHT,
                APP_HEIGHT - BUTTONS_VOID - BUTTON_HEIGHT, BUTTON_HEIGHT));
        myButton = myButtons.get(3);

        /*
        Делаем адаптивную разметку для логотипа.
         */
        aspectRatioLogo = gameLogo.getWidth() / (float) gameLogo.getHeight();
        remainingSpaceY = myButton.getY1() + BUTTON_HEIGHT + BUTTONS_VOID;
        remainingSpaceHigh = APP_HEIGHT - remainingSpaceY - BUTTONS_VOID * 2 - BUTTON_HEIGHT;
        aspectRatioReal = (APP_WIDTH - BUTTONS_VOID * 2) / remainingSpaceHigh;
        if (aspectRatioReal > aspectRatioLogo) {
            logoY = remainingSpaceY;
            logoHigh = remainingSpaceHigh;
            logoWidth = gameLogo.getWidth() * logoHigh / gameLogo.getHeight();
            logoX = APP_WIDTH / 2 - logoWidth / 2;
        } else {
            logoX = BUTTONS_VOID;
            logoWidth = APP_WIDTH - 2 * BUTTONS_VOID;
            logoHigh = gameLogo.getHeight() * logoWidth / gameLogo.getWidth();
            logoY = remainingSpaceY + remainingSpaceHigh / 2 - logoHigh / 2;
        }

        helpAndSettingsButtonSignWidthAndHigh = 4 * BUTTON_HEIGHT / 5;
        helpButtonSignX = myButtons.get(4).getX1() + BUTTON_HEIGHT / 2 -
                helpAndSettingsButtonSignWidthAndHigh / 2;
        settingsButtonSignX = myButtons.get(5).getX1() + BUTTON_HEIGHT / 2 -
                helpAndSettingsButtonSignWidthAndHigh / 2;
        helpAndSettingsButtonSignY = myButtons.get(5).getY1() + BUTTON_HEIGHT / 2 -
                helpAndSettingsButtonSignWidthAndHigh / 2;

        planetTextX = APP_WIDTH / 2 - "planet".length() * 11 * Constants.SCALEXY_NEW;
        planetTextY = BUTTONS_VOID * 4 + 3.5f * BUTTON_HEIGHT + bitmapFont.getCapHeight() / 2;
        constructTextX = APP_WIDTH / 2 - "construct".length() * 11 * Constants.SCALEXY_NEW;
        constructTextY = BUTTONS_VOID * 3 + 2.5f * BUTTON_HEIGHT + bitmapFont.getCapHeight() / 2;
        researchTextX = APP_WIDTH / 2 - "research".length() * 11 * Constants.SCALEXY_NEW;
        researchTextY = BUTTONS_VOID * 2 + 1.5f * BUTTON_HEIGHT + bitmapFont.getCapHeight() / 2;
        shopTextX = APP_WIDTH / 2 - "shop".length() * 11 * Constants.SCALEXY_NEW;
        shopTextY = BUTTONS_VOID + BUTTON_HEIGHT / 2 + bitmapFont.getCapHeight() / 2;

        lastAnimationTime = System.currentTimeMillis();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl20.glClearColor(0, 0, 0, 1);

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

        batch.draw(backgroundsMain.get(curScreenAnimation), 0, 0, APP_WIDTH, APP_HEIGHT);

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

        batch.draw(gameLogo, logoX, logoY, logoWidth, logoHigh);

        batch.draw(helpButtonSign, helpButtonSignX, helpAndSettingsButtonSignY,
                helpAndSettingsButtonSignWidthAndHigh, helpAndSettingsButtonSignWidthAndHigh);
        batch.draw(settingsButtonSign, settingsButtonSignX, helpAndSettingsButtonSignY,
                helpAndSettingsButtonSignWidthAndHigh, helpAndSettingsButtonSignWidthAndHigh);

        bitmapFont.draw(batch, "planet", planetTextX, planetTextY);
        bitmapFont.draw(batch, "construct", constructTextX, constructTextY);
        bitmapFont.draw(batch, "research", researchTextX, researchTextY);
        bitmapFont.draw(batch, "shop", shopTextX, shopTextY);

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
                screenManager.setShopScreen(new Shop(sys, user));
                sys.setScreen(screenManager.getShopScreen());
                break;
            case 1:
                sys.setScreen(screenManager.getResearchScreen());
                break;
            case 2:
                sys.setScreen(screenManager.getConstructionScreen());
                break;
            case 3:
                sys.setScreen(screenManager.getPlanetScreen());
                break;
            case 4:
                sys.setScreen(screenManager.getTrainingScreen());
                break;
            case 5:
                sys.setScreen(screenManager.getSettingsScreen());
                break;
        }
    }
}
