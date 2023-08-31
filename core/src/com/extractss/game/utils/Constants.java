package com.extractss.game.utils;

import com.badlogic.gdx.Gdx;

import static com.extractss.game.ExtractSolarSys.bitmapFont;
import static com.extractss.game.ExtractSolarSys.bitmapFontReversedColorSmall;
import static com.extractss.game.ExtractSolarSys.bitmapFontSmall;

public class Constants {
    public static int NUMBER_OF_ROWS = 7;
    public static int NUMBER_OF_COLUMNS = 5;
    public static float SIDE_INDENT;
    public static int MAX_INCREMENT_TIME_ALLOWED = 86400;
    public static float APP_WIDTH;
    public static float APP_HEIGHT;
    public static float BUTTONS_VOID;
    public static float BUTTON_HEIGHT;
    public static float BUTTON_WIDTH;
    public static float SCALEY_NEW_ORIGINAL;
    public static float SCALEX_NEW_ORIGINAL;
    public static float SCALEXY_NEW;
    public static float SIDE_OF_FIELD;
    public static float LIST_WIDTH;
    public static float LIST_HEIGHT;
    public static float HEIGHT_FOR_RESOURCES;
    public static float Y_RESOURCES_TABLE;
    public static float HEIGHT_RESOURCES_TABLE;
    public static int AVERAGE_VALUE_TO_BUY_RES;
    public static float SMALLER_SCALE;
    public static float KNOB_WIDTH;
    public static float KNOB_X;
    public static float LIST_ELEMENT_PIC_X;
    public static float LIST_ELEMENT_PIC_SIZE;
    public static float LIST_ELEMENT_TITLE_X_CENTER;
    public static float BOTTOM_BUTTONS_TEXT_Y;
    public static float TOP_BUTTONS_TEXT_Y;
    public static float MEDIUM_LEST_ELEMENT_HEIGHT;
    public static float SMALL_LIST_ELEMENT_HEIGHT;

    public static void defineVariables() {
        APP_WIDTH = Gdx.graphics.getWidth();
        APP_HEIGHT = Gdx.graphics.getHeight();
        BUTTONS_VOID = APP_HEIGHT / 40;
        BUTTON_HEIGHT = APP_HEIGHT / 9;
        BUTTON_WIDTH = APP_WIDTH - 2 * APP_WIDTH / 10;
        SCALEY_NEW_ORIGINAL = (APP_HEIGHT / 960f) * 1.2f;
        SCALEX_NEW_ORIGINAL = (APP_WIDTH / 540f) * 1.2f;

        if (SCALEY_NEW_ORIGINAL > SCALEX_NEW_ORIGINAL) SMALLER_SCALE = (APP_WIDTH / 540f) / 1.2f;
        else SMALLER_SCALE = (APP_HEIGHT / 960f) / 1.2f;

        if (SCALEY_NEW_ORIGINAL > SCALEX_NEW_ORIGINAL) SCALEXY_NEW = SCALEX_NEW_ORIGINAL;
        else SCALEXY_NEW = SCALEY_NEW_ORIGINAL;

        SIDE_INDENT = APP_WIDTH / 15f;

        bitmapFontSmall.getData().setScale(SMALLER_SCALE);
        bitmapFont.getData().setScale(SCALEXY_NEW, SCALEXY_NEW);
        bitmapFontReversedColorSmall.getData().setScale(SMALLER_SCALE);

        MEDIUM_LEST_ELEMENT_HEIGHT = 8 * bitmapFontSmall.getCapHeight();
        LIST_WIDTH = APP_WIDTH - 2 * SIDE_INDENT;
        LIST_HEIGHT = APP_HEIGHT - 2 * BUTTON_HEIGHT - 2 * bitmapFontSmall.getCapHeight();
        HEIGHT_FOR_RESOURCES = APP_HEIGHT - BUTTON_HEIGHT;
        SMALL_LIST_ELEMENT_HEIGHT = 4 * bitmapFontSmall.getCapHeight();
        Y_RESOURCES_TABLE = LIST_HEIGHT + BUTTON_HEIGHT + 1;
        HEIGHT_RESOURCES_TABLE = APP_HEIGHT - BUTTON_HEIGHT - Y_RESOURCES_TABLE;

        KNOB_WIDTH = SIDE_INDENT * 2;
        KNOB_X = LIST_WIDTH;

        LIST_ELEMENT_PIC_X = SIDE_INDENT;
        LIST_ELEMENT_PIC_SIZE = 8 * MEDIUM_LEST_ELEMENT_HEIGHT / 10;
        LIST_ELEMENT_TITLE_X_CENTER = SIDE_INDENT + 9 * MEDIUM_LEST_ELEMENT_HEIGHT / 10 +
                (LIST_WIDTH - 9 * MEDIUM_LEST_ELEMENT_HEIGHT / 10) / 2f;

        BOTTOM_BUTTONS_TEXT_Y = BUTTON_HEIGHT / 2 + bitmapFont.getCapHeight() / 2;
        TOP_BUTTONS_TEXT_Y = APP_HEIGHT - BUTTON_HEIGHT / 2 + bitmapFont.getCapHeight() / 2;
    }
}
