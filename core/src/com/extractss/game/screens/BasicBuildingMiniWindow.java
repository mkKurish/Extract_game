package com.extractss.game.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.extractss.game.SimpleClasses.Building;

abstract public class BasicBuildingMiniWindow extends BasicScreen {
    protected Texture texture;
    protected TextureRegion picTextureRegion;
    Building building;
    protected static float titleX;
    protected static float titleY;
    protected static float frameX;
    protected static float frameWidth;
    protected static float frameY;
    protected static float frameHigh;
    protected static float firstResourceYMoney;
    protected static float firstResourceYMetal;
    protected static float firstResourceYEnergy;
    protected static float firstResourcePicYMoney;
    protected static float firstResourcePicYMetal;
    protected static float firstResourcePicYEnergy;

    protected static float inventLvlBuildingY;
    protected static float inventLvlBuildingPicY;

    protected static float secondResourceYMoney;
    protected static float secondResourceYMetal;
    protected static float secondResourceYEnergy;
    protected static float secondResourcePicYMoney;
    protected static float secondResourcePicYMetal;
    protected static float secondResourcePicYEnergy;
    protected static float resourceHighWidth;
    protected static float resourceX;
    protected static float resourcePicX;

    protected static float costY;

    protected static float productivityY;

    protected static float picX;
    protected static float picY;
    protected static float picWidth;
    protected static float picHigh;

    protected static float cancelX;
    protected static float cancelY;
}
