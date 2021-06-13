package com.extractss.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.extractss.game.ExtractSolarSys;
import com.extractss.game.SimpleClasses.MyButtons;
import com.extractss.game.SimpleClasses.User;

import java.util.ArrayList;

import static com.extractss.game.ExtractSolarSys.backgroundsOther;
import static com.extractss.game.ExtractSolarSys.bitmapFont;
import static com.extractss.game.ExtractSolarSys.buttonDownSound;
import static com.extractss.game.ExtractSolarSys.buttonUpSound;
import static com.extractss.game.ExtractSolarSys.downNinePatch;
import static com.extractss.game.ExtractSolarSys.screenManager;
import static com.extractss.game.ExtractSolarSys.upNinePatch;
import static com.extractss.game.utils.Constants.APP_HEIGHT;
import static com.extractss.game.utils.Constants.APP_WIDTH;
import static com.extractss.game.utils.Constants.BOTTOM_BUTTONS_TEXT_Y;
import static com.extractss.game.utils.Constants.BUTTON_HEIGHT;
import static com.extractss.game.utils.Constants.LEFT_INDENT;
import static com.extractss.game.utils.Constants.SCALEXY_NEW;
import static com.extractss.game.utils.Constants.SCALEX_NEW_ORIGINAL;
import static com.extractss.game.utils.Constants.SCALEY_NEW_ORIGINAL;
import static com.extractss.game.utils.Operations.isInPlace;
import static com.extractss.game.utils.Operations.parseAndSavePrefsBuildings;

public class GameOverScreen implements MyScreen {
    ExtractSolarSys sys;
    User user;

    private Batch batch;

    private ArrayList<MyButtons> myButtons;
    private MyButtons myButton;

    private static String title;

    private static Texture pic;

    private float touchedX;
    private float touchedY;
    private long lastTouchTime = 0;
    private long lastAnimationTime;
    private int curScreenAnimation = 0;
    private static float titleX;
    private static float titleY;
    private static float okX;
    private static float picY;
    private static float picX;
    private static float picSize;

    public GameOverScreen(ExtractSolarSys sys, User user) {
        this.sys = sys;
        this.user = user;

        batch = new SpriteBatch();

        myButtons = new ArrayList<>();
        myButtons.add(new MyButtons(0, APP_WIDTH, 0, BUTTON_HEIGHT));
        myButton = myButtons.get(0);

        titleX = LEFT_INDENT / 2;
        titleY = APP_HEIGHT - bitmapFont.getCapHeight();

        okX = APP_WIDTH / 2f - "ok".length() * 11 * SCALEXY_NEW;

        title = "congratulations!\n" +
                "you passed the game.";

        if (SCALEX_NEW_ORIGINAL > SCALEY_NEW_ORIGINAL + 1) {
            picSize = APP_WIDTH - LEFT_INDENT * 8;
        } else {
            picSize = APP_HEIGHT - BUTTON_HEIGHT * 5 - bitmapFont.getCapHeight() * 3;
        }
        picX = APP_WIDTH / 2 - picSize / 2;
        picY = APP_HEIGHT / 2 - picSize / 2;

        pic = new Texture(Gdx.files.internal("buildings\\rocket.png"));
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

        batch.draw(backgroundsOther.get(curScreenAnimation), 0, 0, APP_WIDTH, APP_HEIGHT);

        downNinePatch.draw(batch, 0, BUTTON_HEIGHT, APP_WIDTH, APP_HEIGHT - BUTTON_HEIGHT);

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

        bitmapFont.draw(batch, title, titleX, titleY);

        batch.draw(pic, picX, picY, picSize, picSize);

        bitmapFont.draw(batch, "ok", okX, BOTTOM_BUTTONS_TEXT_Y);

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
                sys.setScreen(screenManager.getConstructionScreen());
                break;
        }
    }
}
