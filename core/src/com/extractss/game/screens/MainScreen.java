package com.extractss.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.extractss.game.ExtractSolarSys;
import com.extractss.game.SimpleClasses.MyButtons;
import com.extractss.game.SimpleClasses.User;
import com.extractss.game.utils.Constants;

import java.util.ArrayList;

import static com.extractss.game.ExtractSolarSys.backgroundsMain;
import static com.extractss.game.ExtractSolarSys.bitmapFont;
import static com.extractss.game.ExtractSolarSys.gameLogo;
import static com.extractss.game.ExtractSolarSys.helpButtonSign;
import static com.extractss.game.ExtractSolarSys.screenManager;
import static com.extractss.game.ExtractSolarSys.settingsButtonSign;
import static com.extractss.game.utils.Constants.APP_HEIGHT;
import static com.extractss.game.utils.Constants.APP_WIDTH;
import static com.extractss.game.utils.Constants.BUTTONS_VOID;
import static com.extractss.game.utils.Constants.BUTTON_HEIGHT;
import static com.extractss.game.utils.Constants.BUTTON_WIDTH;
import static com.extractss.game.utils.Operations.parseAndSavePrefsBuildings;

public class MainScreen extends BasicScreen {
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
        myButtons.add(new MyButtons(APP_WIDTH / 10, BUTTON_HEIGHT, // Square button
                APP_HEIGHT - BUTTONS_VOID - BUTTON_HEIGHT, BUTTON_HEIGHT));
        myButtons.add(new MyButtons(9 * APP_WIDTH / 10 - BUTTON_HEIGHT, BUTTON_HEIGHT, // Square button
                APP_HEIGHT - BUTTONS_VOID - BUTTON_HEIGHT, BUTTON_HEIGHT));
        myButton = myButtons.get(3);

        /*
        Делаем адаптивную разметку для логотипа.
         */
        float aspectRatioLogo = gameLogo.getWidth() / (float) gameLogo.getHeight();
        float remainingSpaceY = myButton.getY1() + BUTTON_HEIGHT + BUTTONS_VOID;
        float remainingSpaceHigh = APP_HEIGHT - remainingSpaceY - BUTTONS_VOID * 2 - BUTTON_HEIGHT;
        float aspectRatioReal = (APP_WIDTH - BUTTONS_VOID * 2) / remainingSpaceHigh;
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
    public void render(float delta) {
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl20.glClearColor(0, 0, 0, 1);

        doAnimationChange(); // Производим анимацию фона.

        batch.begin();

        batch.draw(backgroundsMain.get(curScreenAnimation), 0, 0, APP_WIDTH, APP_HEIGHT);

        checkButtonTouches(); // Проверяем кнопки на нажатие.

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
