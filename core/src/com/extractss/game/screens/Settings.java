package com.extractss.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.extractss.game.ExtractSolarSys;
import com.extractss.game.SimpleClasses.MyButtons;
import com.extractss.game.SimpleClasses.User;
import com.extractss.game.utils.Constants;

import java.util.ArrayList;

import static com.extractss.game.ExtractSolarSys.backgroundMusic;
import static com.extractss.game.ExtractSolarSys.backgroundsOther;
import static com.extractss.game.ExtractSolarSys.bitmapFont;
import static com.extractss.game.ExtractSolarSys.bitmapFontSmall;
import static com.extractss.game.ExtractSolarSys.maxMeteorFellTime;
import static com.extractss.game.ExtractSolarSys.lastMeteorFellTime;
import static com.extractss.game.ExtractSolarSys.musicTexture;
import static com.extractss.game.ExtractSolarSys.progressBarBackNinePatch;
import static com.extractss.game.ExtractSolarSys.progressBarKnobNinePatch;
import static com.extractss.game.ExtractSolarSys.resetButtonUp;
import static com.extractss.game.ExtractSolarSys.screenManager;
import static com.extractss.game.ExtractSolarSys.soundTexture;
import static com.extractss.game.ExtractSolarSys.upNinePatch;
import static com.extractss.game.utils.Constants.APP_HEIGHT;
import static com.extractss.game.utils.Constants.APP_WIDTH;
import static com.extractss.game.utils.Constants.BUTTONS_VOID;
import static com.extractss.game.utils.Constants.BUTTON_HEIGHT;
import static com.extractss.game.utils.Constants.BUTTON_WIDTH;
import static com.extractss.game.utils.Constants.SIDE_INDENT;
import static com.extractss.game.utils.Constants.SMALLER_SCALE;
import static com.extractss.game.utils.Operations.isInPlaceMain;
import static com.extractss.game.utils.Operations.parseAndSavePrefsBuildings;

public class Settings extends BasicScreen {
    private static Texture qrCode;
    private static long lastIconsTouchTime = 0;
    private static float menuTextX;
    private static float menuTextY;
    private static float resetTextX;
    private static float resetTextY;
    private static float kingModeX;
    private static float kingModeY;
    private static float progressbarsX;
    private static float progressbarsHigh;
    private static float progressbarsWidth;
    private static float soundAndMusicTextureX;
    private static float soundTextureY;
    private static float musicTextureY;
    private static float soundAndMusicTextureSideSize;
    private static float soundsProgressbarY;
    private static float soundsProgressbarKnobWidth;
    private static float musicProgressbarY;
    private static float musicProgressbarKnobWidth;
    private static float qrX;
    private static float qrY;
    private static float qrWidth;
    private static float qrHigh;

    public Settings(ExtractSolarSys sys, User user) {
        this.sys = sys;
        this.user = user;

        touchedX = -1;
        touchedY = -1;

        batch = new SpriteBatch();

        myButtons = new ArrayList<>();
        myButtons.add(new MyButtons(APP_WIDTH / 10, BUTTON_WIDTH, BUTTONS_VOID, BUTTON_HEIGHT));
        myButtons.add(new MyButtons(APP_WIDTH / 5, 3 * APP_WIDTH / 5,
                BUTTONS_VOID * 2 + 3 * BUTTON_HEIGHT / 2, BUTTON_HEIGHT / 2));
        //Another button down in constructor
        myButton = myButtons.get(0);

        menuTextX = APP_WIDTH / 2 - "menu".length() * 11 * Constants.SCALEXY_NEW;
        menuTextY = myButtons.get(0).getY1() + BUTTON_HEIGHT / 2 + bitmapFont.getCapHeight() / 2;
        resetTextX = APP_WIDTH / 2 - "reset".length() * 11 * SMALLER_SCALE;
        resetTextY = myButtons.get(1).getY1() + myButtons.get(1).getHeight() / 2 + bitmapFontSmall.getCapHeight() / 2;

        soundAndMusicTextureX = APP_WIDTH / 20;
        soundAndMusicTextureSideSize = bitmapFont.getCapHeight() * 2;
        progressbarsX = soundAndMusicTextureX + soundAndMusicTextureSideSize;
        progressbarsWidth = APP_WIDTH - progressbarsX - soundAndMusicTextureX;
        progressbarsHigh = bitmapFont.getCapHeight();
        if (user.isSoundsActive())
            soundTexture = new Texture(Gdx.files.internal("pngFiles\\sounds on icon.png"));
        else soundTexture = new Texture(Gdx.files.internal("pngFiles\\sounds off icon.png"));
        soundsProgressbarY = (myButtons.get(1).getY1() + myButtons.get(1).getHeight()) + BUTTON_HEIGHT;
        soundTextureY = soundsProgressbarY - soundAndMusicTextureSideSize / 4;
        if (user.isSoundsActive())
            soundsProgressbarKnobWidth = progressbarsWidth * user.getSoundsVolume();
        else soundsProgressbarKnobWidth = progressbarsWidth * 0.5f;
        if (user.isMusicActive())
            musicTexture = new Texture(Gdx.files.internal("pngFiles\\music on icon.png"));
        else musicTexture = new Texture(Gdx.files.internal("pngFiles\\music off icon.png"));
        musicProgressbarY = soundsProgressbarY + soundAndMusicTextureSideSize + BUTTON_HEIGHT;
        musicTextureY = musicProgressbarY - soundAndMusicTextureSideSize / 4;
        if (user.isMusicActive())
            musicProgressbarKnobWidth = progressbarsWidth * user.getMusicVolume();
        else musicProgressbarKnobWidth = progressbarsWidth * 0.5f;

        qrCode = new Texture(Gdx.files.internal("pngFiles\\QrGitHub.png"));

        float aspectRatioLogo = qrCode.getWidth() / (float) qrCode.getHeight();
        float remainingSpaceY = musicTextureY + soundAndMusicTextureSideSize + SIDE_INDENT;
        float remainingSpaceHigh = APP_HEIGHT - remainingSpaceY - SIDE_INDENT * 2;
        float aspectRatioReal = (APP_WIDTH - SIDE_INDENT * 2) / remainingSpaceHigh;
        if (aspectRatioReal > aspectRatioLogo) {
            qrY = remainingSpaceY;
            qrHigh = remainingSpaceHigh;
            qrWidth = qrCode.getWidth() * qrHigh / qrCode.getHeight();
            qrX = APP_WIDTH / 2 - qrWidth / 2;
        } else {
            qrX = BUTTONS_VOID;
            qrWidth = APP_WIDTH - 2 * BUTTONS_VOID;
            qrHigh = qrCode.getHeight() * qrWidth / qrCode.getWidth();
            qrY = remainingSpaceY + remainingSpaceHigh / 2 - qrHigh / 2;
        }
        myButtons.add(new MyButtons(qrX - 10, qrWidth + 20, qrY - 10, qrHigh + 20));
        lastAnimationTime = System.currentTimeMillis();
    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl20.glClearColor(0, 0, 0, 1);

        doAnimationChange(); // Производим анимацию фона.

        /*
        Рассчитываем размер ползунка громкости в зависимости от настройки громкости музыки и звука.
         */
        if (user.isSoundsActive())
            soundsProgressbarKnobWidth = progressbarsWidth * user.getSoundsVolume();
        if (user.isMusicActive())
            musicProgressbarKnobWidth = progressbarsWidth * user.getMusicVolume();

        batch.begin();

        batch.draw(backgroundsOther.get(curScreenAnimation), 0, 0, APP_WIDTH, APP_HEIGHT);


        checkButtonTouches(); // Проверяем кнопки на нажатие.
        resetButtonUp.draw(batch, myButtons.get(1).getX1(), myButtons.get(1).getY1(), myButtons.get(1).getWidth(),
                myButtons.get(1).getHeight());

        /*
        Проверяем меню звуков и музыки на нажатие.
         */
        if (Gdx.input.isTouched()) {
            if (isInPlaceMain(touchedX, touchedY, (int) progressbarsX, (int) soundsProgressbarY,
                    (int) progressbarsWidth, (int) progressbarsHigh) && (touchedX - progressbarsX) > 20 && user.isSoundsActive()) {
                user.setSoundsVolume((touchedX - progressbarsX) / progressbarsWidth);
            } else if (isInPlaceMain(touchedX, touchedY, (int) progressbarsX, (int) musicProgressbarY,
                    (int) progressbarsWidth, (int) progressbarsHigh) && (touchedX - progressbarsX) > 20 && user.isMusicActive()) {
                user.setMusicVolume((touchedX - progressbarsX) / progressbarsWidth);
                backgroundMusic.setVolume(user.getMusicVolume());
            } else if (isInPlaceMain(touchedX, touchedY, (int) soundAndMusicTextureX, (int) soundTextureY,
                    (int) soundAndMusicTextureSideSize, (int) soundAndMusicTextureSideSize)
                    && System.currentTimeMillis() - lastIconsTouchTime > 200) {
                if (user.isSoundsActive()) {
                    user.setSoundsVolume(0);
                    user.setSoundsActive(false);
                    soundTexture = new Texture(Gdx.files.internal("pngFiles\\sounds off icon.png"));
                } else {
                    user.setSoundsVolume(0.3f);
                    user.setSoundsActive(true);
                    soundTexture = new Texture(Gdx.files.internal("pngFiles\\sounds on icon.png"));
                }
                lastIconsTouchTime = System.currentTimeMillis();
            } else if (isInPlaceMain(touchedX, touchedY, (int) soundAndMusicTextureX, (int) musicTextureY,
                    (int) soundAndMusicTextureSideSize, (int) soundAndMusicTextureSideSize)
                    && System.currentTimeMillis() - lastIconsTouchTime > 200) {
                if (user.isMusicActive()) {
                    user.setMusicVolume(0);
                    user.setMusicActive(false);
                    musicTexture = new Texture(Gdx.files.internal("pngFiles\\music off icon.png"));
                } else {
                    user.setMusicVolume(0.3f);
                    user.setMusicActive(true);
                    musicTexture = new Texture(Gdx.files.internal("pngFiles\\music on icon.png"));
                }
                backgroundMusic.setVolume(user.getMusicVolume());
                lastIconsTouchTime = System.currentTimeMillis();
            }
        }

        upNinePatch.draw(batch, soundAndMusicTextureX, soundTextureY, soundAndMusicTextureSideSize, soundAndMusicTextureSideSize);
        upNinePatch.draw(batch, soundAndMusicTextureX, musicTextureY, soundAndMusicTextureSideSize, soundAndMusicTextureSideSize);
        batch.draw(soundTexture, soundAndMusicTextureX, soundTextureY, soundAndMusicTextureSideSize, soundAndMusicTextureSideSize);
        batch.draw(musicTexture, soundAndMusicTextureX, musicTextureY, soundAndMusicTextureSideSize, soundAndMusicTextureSideSize);

        batch.draw(qrCode, qrX, qrY, qrWidth, qrHigh);

        /*
        Отрисовываем ползунки громкости звуков и музыки.
         */
        progressBarBackNinePatch.draw(batch, progressbarsX, soundsProgressbarY, progressbarsWidth, progressbarsHigh);
        progressBarBackNinePatch.draw(batch, progressbarsX, musicProgressbarY, progressbarsWidth, progressbarsHigh);
        if (user.isSoundsActive())
            progressBarKnobNinePatch.draw(batch, progressbarsX, soundsProgressbarY, soundsProgressbarKnobWidth, progressbarsHigh);
        else
            progressBarBackNinePatch.draw(batch, progressbarsX, soundsProgressbarY, soundsProgressbarKnobWidth, progressbarsHigh);
        if (user.isMusicActive())
            progressBarKnobNinePatch.draw(batch, progressbarsX, musicProgressbarY, musicProgressbarKnobWidth, progressbarsHigh);
        else
            progressBarBackNinePatch.draw(batch, progressbarsX, musicProgressbarY, musicProgressbarKnobWidth, progressbarsHigh);

        bitmapFont.draw(batch, "menu", menuTextX, menuTextY);
        bitmapFontSmall.draw(batch, "reset", resetTextX, resetTextY);
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

    private void miniWindowActivated() {
        screenManager.setMiniWindowResetConfirmationScreen(new MiniWindowResetConfirmation(sys, user));
        sys.setScreen(screenManager.getMiniWindowResetConfirmationScreen());
    }

    @Override
    public void buttonActivated(int i) {
        switch (i) {
            case 0:
                /*
                Extra-resources will be disable in production (but not in usual github release)
                 */
                user.setMoney(999999);
                user.setMetal(999999);
                user.setEnergy(999999);
                lastMeteorFellTime = maxMeteorFellTime;
                sys.setScreen(screenManager.getMainScreen());
                break;
            case 1:
                miniWindowActivated();
                break;
            case 2:
                try {
                    sys.uriOpenner.openURI_inBrowser("https://github.com/mkKurish");
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
                //Gdx.net.openURI("https://github.com/mkKurish"); Doesn't working on realme 8 (android 13), but do on asus zenfone max 3 (android 7)
                break;
        }
    }
}
