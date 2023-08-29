package com.extractss.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.extractss.game.ExtractSolarSys;
import com.extractss.game.SimpleClasses.MyButtons;
import com.extractss.game.SimpleClasses.User;
import com.extractss.game.utils.Constants;
import com.extractss.game.utils.IncrementResourcesTimeCheck;

import java.util.ArrayList;

import static com.extractss.game.ExtractSolarSys.incrementEnergy;
import static com.extractss.game.ExtractSolarSys.incrementMetal;
import static com.extractss.game.ExtractSolarSys.incrementMoney;
import static com.extractss.game.ExtractSolarSys.incrementTimeValue;
import static com.extractss.game.ExtractSolarSys.backgroundsOther;
import static com.extractss.game.ExtractSolarSys.bitmapFont;
import static com.extractss.game.ExtractSolarSys.bitmapFontSmall;
import static com.extractss.game.ExtractSolarSys.buttonDownSound;
import static com.extractss.game.ExtractSolarSys.buttonUpSound;
import static com.extractss.game.ExtractSolarSys.downNinePatch;
import static com.extractss.game.ExtractSolarSys.energyTexture;
import static com.extractss.game.ExtractSolarSys.isIncrementNormalModeAllowed;
import static com.extractss.game.ExtractSolarSys.metalTexture;
import static com.extractss.game.ExtractSolarSys.moneyTexture;
import static com.extractss.game.ExtractSolarSys.upNinePatch;
import static com.extractss.game.utils.Constants.APP_HEIGHT;
import static com.extractss.game.utils.Constants.APP_WIDTH;
import static com.extractss.game.utils.Constants.BUTTON_HEIGHT;
import static com.extractss.game.utils.Constants.MAX_INCREMENT_TIME_ALLOWED;
import static com.extractss.game.utils.Constants.SCALEXY_NEW;
import static com.extractss.game.utils.Operations.isInPlace;

public class NoConnectionToIncrementResources extends BasicScreen {
    private static float frameX;
    private static float frameWidth;
    private static float frameY;
    private static float frameHigh;

    private static float connectX;
    private static float connectY;

    private static float acceptX;
    private static float acceptY;

    private static float titleX;
    private static float titleY;

    private static float resourceTextureX;
    private static float resourceTextX;
    private static float moneyTextureY;
    private static float moneyTextY;
    private static float metalTextureY;
    private static float metalTextY;
    private static float energyTextureY;
    private static float energyTextY;
    private static float resourcePicSize;

    public NoConnectionToIncrementResources(ExtractSolarSys sys, User user) {
        this.sys = sys;
        this.user = user;

        batch = new SpriteBatch();

        myButtons = new ArrayList<>();
        myButtons.add(new MyButtons(0, APP_WIDTH / 2,
                APP_HEIGHT / 40, BUTTON_HEIGHT));
        myButtons.add(new MyButtons(APP_WIDTH / 2, APP_WIDTH / 2,
                APP_HEIGHT / 40, BUTTON_HEIGHT));

        myButton = myButtons.get(0);

        frameX = APP_WIDTH / 20;
        frameWidth = 9 * APP_WIDTH / 10;
        frameY = APP_HEIGHT / 40 + myButtons.get(0).getHeight() + myButtons.get(0).getY1();
        frameHigh = APP_HEIGHT - frameY - APP_HEIGHT / 40;

        connectX = APP_WIDTH / 2 - APP_WIDTH / 4 - "connect".length() * 11 * Constants.SCALEXY_NEW;
        connectY = BUTTON_HEIGHT/2 + APP_HEIGHT / 40 + bitmapFont.getCapHeight() / 2;
        acceptX = APP_WIDTH / 2 + APP_WIDTH / 4 - "accept".length() * 11 * Constants.SCALEXY_NEW;
        acceptY = connectY;

        titleX = frameX * 2;
        titleY = frameHigh + frameY - bitmapFont.getCapHeight();

        resourceTextureX = titleX;

        moneyTextureY = titleY - bitmapFontSmall.getCapHeight() * 10;
        moneyTextY = titleY - bitmapFontSmall.getCapHeight() * 9;
        metalTextureY = titleY - bitmapFontSmall.getCapHeight() * 12;
        metalTextY = titleY - bitmapFontSmall.getCapHeight() * 11;
        energyTextureY = titleY - bitmapFontSmall.getCapHeight() * 14;
        energyTextY = titleY - bitmapFontSmall.getCapHeight() * 13;
        resourcePicSize = bitmapFontSmall.getCapHeight();
        resourceTextX = resourceTextureX + resourcePicSize;
    }

    @Override
    public void render(float delta) {
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

        bitmapFontSmall.draw(batch,
                "you were in the game more\n" +
                        "than 24 hours ago and\n" +
                        "are not connected to the\n" +
                        "internet. Do you want\n" +
                        "to collect resources\n" +
                        "in 24 hours or try to\n" +
                        "connect again?", titleX, titleY);

        batch.draw(moneyTexture, resourceTextureX, moneyTextureY, resourcePicSize, resourcePicSize);
        batch.draw(metalTexture, resourceTextureX, metalTextureY, resourcePicSize, resourcePicSize);
        batch.draw(energyTexture, resourceTextureX, energyTextureY, resourcePicSize, resourcePicSize);

        bitmapFontSmall.draw(batch, incrementMoney * (incrementTimeValue -
                        incrementTimeValue % 60000) / 60000 + " <> " + incrementMoney *
                        (MAX_INCREMENT_TIME_ALLOWED - MAX_INCREMENT_TIME_ALLOWED % 60) / 60, resourceTextX,
                moneyTextY);
        bitmapFontSmall.draw(batch, incrementMetal * (incrementTimeValue -
                        incrementTimeValue % 60000) / 60000 + " <> " + incrementMetal *
                        (MAX_INCREMENT_TIME_ALLOWED - MAX_INCREMENT_TIME_ALLOWED % 60) / 60, resourceTextX,
                metalTextY);
        bitmapFontSmall.draw(batch, incrementEnergy * (incrementTimeValue -
                        incrementTimeValue % 60000) / 60000 + " <> " + incrementEnergy *
                        (MAX_INCREMENT_TIME_ALLOWED - MAX_INCREMENT_TIME_ALLOWED % 60) / 60, resourceTextX,
                energyTextY);

        bitmapFont.draw(batch, "connect", connectX, connectY);
        bitmapFont.draw(batch, "accept", acceptX, acceptY);

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
            case 1:
                /*
                Игрок соглашается принять увеличение ресурсов только за 24 часа.
                 */
                isIncrementNormalModeAllowed = false;
            case 0:
                /*
                Пытаемся снова получить значение времени с сервера.
                 */
                IncrementResourcesTimeCheck incrementResourcesTimeCheck =
                        new IncrementResourcesTimeCheck(sys, user);
                incrementResourcesTimeCheck.test();
                break;
        }
    }

}
