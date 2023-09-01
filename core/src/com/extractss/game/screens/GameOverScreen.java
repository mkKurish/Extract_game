package com.extractss.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.extractss.game.ExtractSolarSys;
import com.extractss.game.SimpleClasses.MyButtons;
import com.extractss.game.SimpleClasses.User;

import java.util.ArrayList;

import static com.extractss.game.ExtractSolarSys.backgroundsOther;
import static com.extractss.game.ExtractSolarSys.bitmapFont;
import static com.extractss.game.ExtractSolarSys.downNinePatch;
import static com.extractss.game.ExtractSolarSys.screenManager;
import static com.extractss.game.utils.Constants.APP_HEIGHT;
import static com.extractss.game.utils.Constants.APP_WIDTH;
import static com.extractss.game.utils.Constants.BOTTOM_BUTTONS_TEXT_Y;
import static com.extractss.game.utils.Constants.BUTTON_HEIGHT;
import static com.extractss.game.utils.Constants.SIDE_INDENT;
import static com.extractss.game.utils.Constants.SCALEXY_NEW;
import static com.extractss.game.utils.Constants.SCALEX_NEW_ORIGINAL;
import static com.extractss.game.utils.Constants.SCALEY_NEW_ORIGINAL;
import static com.extractss.game.utils.Operations.parseAndSavePrefsBuildings;

public class GameOverScreen extends BasicScreen {

    private static String title;

    private static Texture pic;
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

        titleX = SIDE_INDENT / 2;
        titleY = APP_HEIGHT - bitmapFont.getCapHeight();

        okX = APP_WIDTH / 2f - "ok".length() * 11 * SCALEXY_NEW;

        title = "congratulations!\n" +
                "you passed the game.";

        if (SCALEX_NEW_ORIGINAL > SCALEY_NEW_ORIGINAL + 1) {
            picSize = APP_WIDTH - SIDE_INDENT * 8;
        } else {
            picSize = APP_HEIGHT - BUTTON_HEIGHT * 5 - bitmapFont.getCapHeight() * 3;
        }
        picX = APP_WIDTH / 2 - picSize / 2;
        picY = APP_HEIGHT / 2 - picSize / 2;

        pic = new Texture(Gdx.files.internal("buildings\\rocket.png"));
    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl20.glClearColor(0, 0, 0, 1);

        doAnimationChange(); // Производим анимацию фона.

        batch.begin();

        batch.draw(backgroundsOther.get(curScreenAnimation), 0, 0, APP_WIDTH, APP_HEIGHT);

        downNinePatch.draw(batch, 0, BUTTON_HEIGHT, APP_WIDTH, APP_HEIGHT - BUTTON_HEIGHT);

        checkButtonTouches(); // Проверяем кнопки на нажатие.

        bitmapFont.draw(batch, title, titleX, titleY);

        batch.draw(pic, picX, picY, picSize, picSize);

        bitmapFont.draw(batch, "ok", okX, BOTTOM_BUTTONS_TEXT_Y);

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
                sys.setScreen(screenManager.getConstructionScreen());
                break;
        }
    }
}
