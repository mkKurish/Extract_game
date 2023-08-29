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
import static com.extractss.game.ExtractSolarSys.bitmapFontSmall;
import static com.extractss.game.ExtractSolarSys.buttonDownSound;
import static com.extractss.game.ExtractSolarSys.buttonUpSound;
import static com.extractss.game.ExtractSolarSys.downNinePatch;
import static com.extractss.game.ExtractSolarSys.isTrainingComplete;
import static com.extractss.game.ExtractSolarSys.screenManager;
import static com.extractss.game.ExtractSolarSys.upNinePatch;
import static com.extractss.game.utils.Constants.APP_HEIGHT;
import static com.extractss.game.utils.Constants.APP_WIDTH;
import static com.extractss.game.utils.Constants.BOTTOM_BUTTONS_TEXT_Y;
import static com.extractss.game.utils.Constants.BUTTON_HEIGHT;
import static com.extractss.game.utils.Constants.SIDE_INDENT;
import static com.extractss.game.utils.Constants.SCALEXY_NEW;
import static com.extractss.game.utils.Constants.SCALEX_NEW_ORIGINAL;
import static com.extractss.game.utils.Constants.SCALEY_NEW_ORIGINAL;
import static com.extractss.game.utils.Operations.isInPlace;
import static com.extractss.game.utils.Operations.parseAndSavePrefsBuildings;

public class TrainingScreen extends BasicScreen {
    private ArrayList<String> texts;
    private ArrayList<Texture> screenshots;
    private int iterator = 0;
    private static float prevX;
    private static float nextX;
    private static float finishX;
    private static float normalScreenshotX;
    private static float normalScreenshotWidth;
    private static float normalScreenshotHeight;
    private static float miniScreenshotWidth;
    private static float miniScreenshotHeight;

    public TrainingScreen(ExtractSolarSys sys, User user) {
        this.sys = sys;
        this.user = user;

        batch = new SpriteBatch();

        myButtons = new ArrayList<>();
        myButtons.add(new MyButtons(0, APP_WIDTH / 2, 0, BUTTON_HEIGHT));
        myButtons.add(new MyButtons(APP_WIDTH / 2, APP_WIDTH / 2, 0, BUTTON_HEIGHT));
        myButton = myButtons.get(0);

        prevX = APP_WIDTH / 4 - "prev".length() * 11 * SCALEXY_NEW;
        nextX = APP_WIDTH / 2 + APP_WIDTH / 4 - "next".length() * 11 * SCALEXY_NEW;
        finishX = APP_WIDTH / 2 + APP_WIDTH / 4 - "finish".length() * 11 * SCALEXY_NEW;

        texts = new ArrayList<>();
        texts.add("hello!\n" +
                "it is training guide to\n" +
                "extract: solar system\n\n" +
                "it is four main points at the\n" +
                "game: construct + inventory,\n" +
                "research, planet, shop");
        texts.add("to buy a building in\n" +
                "construct, click on the\n" +
                "button on the left and\n" +
                "click on the building");
        texts.add("the purchased building will\n" +
                "be in inventory (click\n" +
                "the building again to place)");
        texts.add("on the planet in the selected\n" +
                "cell there will be a building\n" +
                "if you click on it, you can\n" +
                "delete or upgrade");
        texts.add("buy inventions to unlock\n" +
                "new buildings");
        texts.add("if you are having\n" +
                "problems with resources,\n" +
                "go to the shop");
        texts.add("click the left\n" +
                "button on the planet to\n" +
                "take resources faster");
        texts.add("you can change planet\n" +
                "if you want");
        texts.add("every hour a meteor hits one\n" +
                "field. if a meteor hits an\n" +
                "unprotected building, it is\n" +
                "destroyed. defender:\n" +
                "1 lvl = 4 fields + itself\n" +
                "2 lvl = 8 fields + itself");
        texts.add("if you want to reset\n" +
                "your result, open\n" +
                "the settings\n\n" +
                "Have a good game!");

        screenshots = new ArrayList<>();
        screenshots.add(new Texture(Gdx.files.internal("trainingPac\\1.jpg")));
        screenshots.add(new Texture(Gdx.files.internal("trainingPac\\2.jpg")));
        screenshots.add(new Texture(Gdx.files.internal("trainingPac\\3.jpg")));
        screenshots.add(new Texture(Gdx.files.internal("trainingPac\\4.jpg")));
        screenshots.add(new Texture(Gdx.files.internal("trainingPac\\5.jpg")));
        screenshots.add(new Texture(Gdx.files.internal("trainingPac\\6.jpg")));
        screenshots.add(new Texture(Gdx.files.internal("trainingPac\\7.jpg")));
        screenshots.add(new Texture(Gdx.files.internal("trainingPac\\8.jpg")));
        screenshots.add(new Texture(Gdx.files.internal("trainingPac\\9.jpg")));
        screenshots.add(new Texture(Gdx.files.internal("trainingPac\\10.jpg")));
        screenshots.add(new Texture(Gdx.files.internal("trainingPac\\11.jpg")));
        screenshots.add(new Texture(Gdx.files.internal("trainingPac\\12.jpg")));
        screenshots.add(new Texture(Gdx.files.internal("trainingPac\\13.jpg")));
        screenshots.add(new Texture(Gdx.files.internal("trainingPac\\14.jpg")));
        screenshots.add(new Texture(Gdx.files.internal("trainingPac\\15.jpg")));

        /*
        Делаем адаптивную разметку для картинок.
         */
        if (SCALEY_NEW_ORIGINAL > SCALEX_NEW_ORIGINAL + 0.3f) {
            normalScreenshotWidth = APP_WIDTH * 328 / 540f;
            normalScreenshotHeight = screenshots.get(0).getHeight() *
                    normalScreenshotWidth / screenshots.get(0).getWidth();
            normalScreenshotX = APP_WIDTH / 2 - normalScreenshotWidth / 2;
            miniScreenshotWidth = APP_WIDTH * 187 / 540f;
            miniScreenshotHeight = screenshots.get(0).getHeight() *
                    miniScreenshotWidth / screenshots.get(0).getWidth();
        } else {
            normalScreenshotHeight = APP_HEIGHT - BUTTON_HEIGHT -
                    bitmapFontSmall.getCapHeight() * 8 - SIDE_INDENT;
            normalScreenshotWidth = screenshots.get(0).getWidth() *
                    normalScreenshotHeight / screenshots.get(0).getHeight();
            normalScreenshotX = APP_WIDTH / 2 - normalScreenshotWidth / 2;
            miniScreenshotHeight = (APP_HEIGHT - BUTTON_HEIGHT -
                    bitmapFontSmall.getCapHeight() * 5 - SIDE_INDENT) / 2f;
            miniScreenshotWidth = screenshots.get(0).getWidth() *
                    miniScreenshotHeight / screenshots.get(0).getHeight();
        }
    }

    @Override
    public void render(float v) {
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

        bitmapFont.draw(batch, "prev", prevX, BOTTOM_BUTTONS_TEXT_Y);
        if (iterator == texts.size() - 1)
            bitmapFont.draw(batch, "finish", finishX, BOTTOM_BUTTONS_TEXT_Y);
        else bitmapFont.draw(batch, "next", nextX, BOTTOM_BUTTONS_TEXT_Y);

        bitmapFontSmall.draw(batch, texts.get(iterator), SIDE_INDENT / 3,
                APP_HEIGHT - bitmapFontSmall.getCapHeight());

        /*
        Переключаем обучающие элементы в соответствии с текущим этапом обучения (iterator).
         */
        switch (iterator) {
            case 0:
                upNinePatch.draw(batch, normalScreenshotX - 10, BUTTON_HEIGHT + SIDE_INDENT / 2 - 10,
                        normalScreenshotWidth + 20, normalScreenshotHeight + 20);
                batch.draw(screenshots.get(0), normalScreenshotX, BUTTON_HEIGHT + SIDE_INDENT / 2,
                        normalScreenshotWidth, normalScreenshotHeight);
                break;
            case 1:
                upNinePatch.draw(batch, APP_WIDTH / 2 - miniScreenshotWidth - SIDE_INDENT / 4 - 5, BUTTON_HEIGHT + SIDE_INDENT / 2 + miniScreenshotHeight + 10 - 5,
                        miniScreenshotWidth + 10, miniScreenshotHeight + 10);
                upNinePatch.draw(batch, APP_WIDTH / 2 + SIDE_INDENT / 4 - 5, BUTTON_HEIGHT + SIDE_INDENT / 2 + miniScreenshotHeight + 10 - 5,
                        miniScreenshotWidth + 10, miniScreenshotHeight + 10);
                upNinePatch.draw(batch, APP_WIDTH / 2 - miniScreenshotWidth / 2 - 5, BUTTON_HEIGHT + SIDE_INDENT / 2 - 5,
                        miniScreenshotWidth + 10, miniScreenshotHeight + 10);

                batch.draw(screenshots.get(1), APP_WIDTH / 2 - miniScreenshotWidth - SIDE_INDENT / 4,
                        BUTTON_HEIGHT + SIDE_INDENT / 2 + miniScreenshotHeight + 10, miniScreenshotWidth, miniScreenshotHeight);
                batch.draw(screenshots.get(2), APP_WIDTH / 2 + SIDE_INDENT / 4,
                        BUTTON_HEIGHT + SIDE_INDENT / 2 + miniScreenshotHeight + 10, miniScreenshotWidth, miniScreenshotHeight);
                batch.draw(screenshots.get(3), APP_WIDTH / 2 - miniScreenshotWidth / 2,
                        BUTTON_HEIGHT + SIDE_INDENT / 2, miniScreenshotWidth, miniScreenshotHeight);
                break;
            case 2:
                upNinePatch.draw(batch, APP_WIDTH / 2 - miniScreenshotWidth - SIDE_INDENT / 4 - 5, BUTTON_HEIGHT + SIDE_INDENT / 2 + miniScreenshotHeight + 10 - 5,
                        miniScreenshotWidth + 10, miniScreenshotHeight + 10);
                upNinePatch.draw(batch, APP_WIDTH / 2 + SIDE_INDENT / 4 - 5, BUTTON_HEIGHT + SIDE_INDENT / 2 + miniScreenshotHeight + 10 - 5,
                        miniScreenshotWidth + 10, miniScreenshotHeight + 10);
                upNinePatch.draw(batch, APP_WIDTH / 2 - miniScreenshotWidth / 2 - 5, BUTTON_HEIGHT + SIDE_INDENT / 2 - 5,
                        miniScreenshotWidth + 10, miniScreenshotHeight + 10);

                batch.draw(screenshots.get(4), APP_WIDTH / 2 - miniScreenshotWidth - SIDE_INDENT / 4,
                        BUTTON_HEIGHT + SIDE_INDENT / 2 + miniScreenshotHeight + 10, miniScreenshotWidth, miniScreenshotHeight);
                batch.draw(screenshots.get(5), APP_WIDTH / 2 + SIDE_INDENT / 4,
                        BUTTON_HEIGHT + SIDE_INDENT / 2 + miniScreenshotHeight + 10, miniScreenshotWidth, miniScreenshotHeight);
                batch.draw(screenshots.get(6), APP_WIDTH / 2 - miniScreenshotWidth / 2,
                        BUTTON_HEIGHT + SIDE_INDENT / 2, miniScreenshotWidth, miniScreenshotHeight);
                break;
            case 3:
                upNinePatch.draw(batch, normalScreenshotX - 10, BUTTON_HEIGHT + SIDE_INDENT / 2 - 10,
                        normalScreenshotWidth + 20, normalScreenshotHeight + 20);
                batch.draw(screenshots.get(11), normalScreenshotX,
                        BUTTON_HEIGHT + SIDE_INDENT / 2, normalScreenshotWidth, normalScreenshotHeight);
                break;
            case 4:
                upNinePatch.draw(batch, normalScreenshotX - 10, BUTTON_HEIGHT + SIDE_INDENT / 2 - 10,
                        normalScreenshotWidth + 20, normalScreenshotHeight + 20);
                batch.draw(screenshots.get(7), normalScreenshotX,
                        BUTTON_HEIGHT + SIDE_INDENT / 2, normalScreenshotWidth, normalScreenshotHeight);
                break;
            case 5:
                upNinePatch.draw(batch, normalScreenshotX - 10, BUTTON_HEIGHT + SIDE_INDENT / 2 - 10,
                        normalScreenshotWidth + 20, normalScreenshotHeight + 20);
                batch.draw(screenshots.get(8), normalScreenshotX,
                        BUTTON_HEIGHT + SIDE_INDENT / 2, normalScreenshotWidth, normalScreenshotHeight);
                break;
            case 6:
                upNinePatch.draw(batch, normalScreenshotX - 10, BUTTON_HEIGHT + SIDE_INDENT / 2 - 10,
                        normalScreenshotWidth + 20, normalScreenshotHeight + 20);
                batch.draw(screenshots.get(6), normalScreenshotX,
                        BUTTON_HEIGHT + SIDE_INDENT / 2, normalScreenshotWidth, normalScreenshotHeight);
                break;
            case 7:
                upNinePatch.draw(batch, APP_WIDTH / 2 - miniScreenshotWidth - SIDE_INDENT / 4 - 5, BUTTON_HEIGHT + SIDE_INDENT / 2 + miniScreenshotHeight + 10 - 5,
                        miniScreenshotWidth + 10, miniScreenshotHeight + 10);
                upNinePatch.draw(batch, APP_WIDTH / 2 + SIDE_INDENT / 4 - 5, BUTTON_HEIGHT + SIDE_INDENT / 2 + miniScreenshotHeight + 10 - 5,
                        miniScreenshotWidth + 10, miniScreenshotHeight + 10);
                upNinePatch.draw(batch, APP_WIDTH / 2 - miniScreenshotWidth / 2 - 5, BUTTON_HEIGHT + SIDE_INDENT / 2 - 5,
                        miniScreenshotWidth + 10, miniScreenshotHeight + 10);

                batch.draw(screenshots.get(12), APP_WIDTH / 2 - miniScreenshotWidth - SIDE_INDENT / 4,
                        BUTTON_HEIGHT + SIDE_INDENT / 2 + miniScreenshotHeight + 10, miniScreenshotWidth, miniScreenshotHeight);
                batch.draw(screenshots.get(13), APP_WIDTH / 2 + SIDE_INDENT / 4,
                        BUTTON_HEIGHT + SIDE_INDENT / 2 + miniScreenshotHeight + 10, miniScreenshotWidth, miniScreenshotHeight);
                batch.draw(screenshots.get(14), APP_WIDTH / 2 - miniScreenshotWidth / 2,
                        BUTTON_HEIGHT + SIDE_INDENT / 2, miniScreenshotWidth, miniScreenshotHeight);
                break;
            case 8:
                upNinePatch.draw(batch, normalScreenshotX - 10, BUTTON_HEIGHT + SIDE_INDENT / 2 - 10,
                        normalScreenshotWidth + 20, normalScreenshotHeight + 20);
                batch.draw(screenshots.get(10), normalScreenshotX,
                        BUTTON_HEIGHT + SIDE_INDENT / 2, normalScreenshotWidth, normalScreenshotHeight);
                break;
            case 9:
                upNinePatch.draw(batch, normalScreenshotX - 10, BUTTON_HEIGHT + SIDE_INDENT / 2 - 10,
                        normalScreenshotWidth + 20, normalScreenshotHeight + 20);
                batch.draw(screenshots.get(9), normalScreenshotX,
                        BUTTON_HEIGHT + SIDE_INDENT / 2, normalScreenshotWidth, normalScreenshotHeight);
                break;
        }

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
                if (iterator > 0) iterator--;
                break;
            case 1:
                if (iterator < texts.size() - 1) iterator++;
                else {
                    isTrainingComplete = true;
                    iterator = 0;
                    sys.setScreen(screenManager.getMainScreen());
                }
                break;
        }
    }
}
