package com.extractss.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.extractss.game.ExtractSolarSys;
import com.extractss.game.SimpleClasses.MyButtons;
import com.extractss.game.SimpleClasses.User;

import java.util.ArrayList;

import static com.extractss.game.ExtractSolarSys.backgroundMusic;
import static com.extractss.game.ExtractSolarSys.backgroundsOther;
import static com.extractss.game.ExtractSolarSys.bitmapFont;
import static com.extractss.game.ExtractSolarSys.buttonDownSound;
import static com.extractss.game.ExtractSolarSys.buttonUpSound;
import static com.extractss.game.ExtractSolarSys.downNinePatch;
import static com.extractss.game.ExtractSolarSys.screenManager;
import static com.extractss.game.ExtractSolarSys.upNinePatch;
import static com.extractss.game.utils.Constants.APP_HEIGHT;
import static com.extractss.game.utils.Constants.APP_WIDTH;
import static com.extractss.game.utils.Constants.BUTTON_HEIGHT;
import static com.extractss.game.utils.Constants.SCALEXY_NEW;
import static com.extractss.game.utils.Operations.disposeAllResources;
import static com.extractss.game.utils.Operations.isInPlace;
import static com.extractss.game.utils.Operations.parseAndSavePrefsBuildings;

public class MiniWindowResetConfirmation extends BasicScreen {
    private static float titleX;
    private static float titleY;

    private static float frameX;
    private static float frameWidth;
    private static float frameY;
    private static float frameHigh;

    private static float confirmX;
    private static float confirmY;
    private static float noX;
    private static float noY;

    public MiniWindowResetConfirmation(ExtractSolarSys sys, User user) {
        this.sys = sys;
        this.user = user;

        batch = new SpriteBatch();

        frameX = APP_WIDTH / 20;
        frameWidth = 9 * APP_WIDTH / 10;
        frameY = APP_HEIGHT / 20;
        frameHigh = 18 * APP_HEIGHT / 20;

        titleX = frameX * 2;
        titleY = frameY + frameHigh - bitmapFont.getCapHeight() * 1.5f;

        myButtons = new ArrayList<>();
        myButtons.add(new MyButtons(frameX + 3, frameWidth - 6,
                frameY + 3, 3 * BUTTON_HEIGHT / 2));
        myButtons.add(new MyButtons(APP_WIDTH / 4, APP_WIDTH / 2,
                frameY + 3 * BUTTON_HEIGHT, BUTTON_HEIGHT));
        myButton = myButtons.get(0);

        confirmX = APP_WIDTH / 2 - "confirm".length() * 11 * SCALEXY_NEW;
        confirmY = myButtons.get(1).getY1() + BUTTON_HEIGHT / 2 + bitmapFont.getCapHeight() / 2;
        noX = APP_WIDTH / 2 - "no".length() * 11 * SCALEXY_NEW;
        noY = myButtons.get(0).getY1() + myButtons.get(0).getHeight() / 2 + bitmapFont.getCapHeight() / 2;
    }

    @Override
    public void render(float v) {
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

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

        upNinePatch.draw(batch, frameX, frameY, frameWidth, frameHigh);

        checkButtonTouches(); // Проверяем кнопки на нажатие.

        bitmapFont.draw(batch, "Do you seriously\nwant to reset\nyour progress?", titleX, titleY);

        bitmapFont.draw(batch, "confirm", confirmX, confirmY);
        bitmapFont.draw(batch, "no!", noX, noY);

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
                sys.setScreen(screenManager.getSettingsScreen());
                break;
            case 1:
                /*
                Стираем результат и сохранения.
                 */
                Preferences prefs = Gdx.app.getPreferences("com.extractss.GameProgress");

                prefs.clear();
                prefs.flush();

                backgroundMusic.stop();

                disposeAllResources();

                sys.create();
                break;
        }
    }
}
