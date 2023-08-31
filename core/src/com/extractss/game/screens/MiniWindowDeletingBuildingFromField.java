package com.extractss.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.extractss.game.ClassesForLists.BuildingsOnField;
import com.extractss.game.ExtractSolarSys;
import com.extractss.game.SimpleClasses.MyButtons;
import com.extractss.game.SimpleClasses.User;
import com.extractss.game.utils.Constants;

import java.util.ArrayList;

import static com.extractss.game.ExtractSolarSys.backgroundsOther;
import static com.extractss.game.ExtractSolarSys.bitmapFont;
import static com.extractss.game.ExtractSolarSys.bitmapFontSmall;
import static com.extractss.game.ExtractSolarSys.buildingsOnFields;
import static com.extractss.game.ExtractSolarSys.currentPlanet;
import static com.extractss.game.ExtractSolarSys.energyTexture;
import static com.extractss.game.ExtractSolarSys.incrementEnergy;
import static com.extractss.game.ExtractSolarSys.incrementMetal;
import static com.extractss.game.ExtractSolarSys.incrementMoney;
import static com.extractss.game.ExtractSolarSys.inventTexture;
import static com.extractss.game.ExtractSolarSys.maxEnergy;
import static com.extractss.game.ExtractSolarSys.maxMetal;
import static com.extractss.game.ExtractSolarSys.maxMoney;
import static com.extractss.game.ExtractSolarSys.metalTexture;
import static com.extractss.game.ExtractSolarSys.moneyTexture;
import static com.extractss.game.ExtractSolarSys.screenManager;
import static com.extractss.game.ExtractSolarSys.upNinePatch;
import static com.extractss.game.utils.Constants.APP_HEIGHT;
import static com.extractss.game.utils.Constants.APP_WIDTH;
import static com.extractss.game.utils.Constants.BUTTON_HEIGHT;
import static com.extractss.game.utils.Constants.SMALLER_SCALE;
import static com.extractss.game.utils.Operations.parseAndSavePrefsBuildings;

final public class MiniWindowDeletingBuildingFromField extends BasicBuildingMiniWindow {

    BuildingsOnField buildingsOnField;
    private static float deleteX;
    private static float deleteY;

    private static float upgradeButtonX;
    private static float upgradeButtonY;
    private static float upgradeButtonWidth;
    private static float upgradeTextX;
    private static float upgradeTextY;


    public MiniWindowDeletingBuildingFromField(ExtractSolarSys sys, User user,
                                               BuildingsOnField buildingsOnField) {
        this.sys = sys;
        this.user = user;
        this.buildingsOnField = buildingsOnField;
        building = buildingsOnField.getBuilding();

        batch = new SpriteBatch();

        myButtons = new ArrayList<>();
        myButtons.add(new MyButtons(0, APP_WIDTH / 2, APP_HEIGHT / 40, BUTTON_HEIGHT));
        myButtons.add(new MyButtons(APP_WIDTH / 2, APP_WIDTH / 2, APP_HEIGHT / 40,
                BUTTON_HEIGHT));

        buyAbleButton = 2;

        myButton = myButtons.get(0);

        texture = buildingsOnField.getBuilding().getPicture();

        picTextureRegion = new TextureRegion(texture, texture.getWidth(), texture.getHeight());


        titleX = APP_WIDTH / 2 - building.getName().length() * 11 * Constants.SCALEXY_NEW;
        titleY = APP_HEIGHT - bitmapFont.getCapHeight() * 1.5f;

        cancelX = APP_WIDTH / 2 - APP_WIDTH / 4 - "cancel".length() * 11 * Constants.SCALEXY_NEW;
        cancelY = BUTTON_HEIGHT / 2 + APP_HEIGHT / 40 + bitmapFont.getCapHeight() / 2;
        deleteX = APP_WIDTH / 2 + APP_WIDTH / 4 - "delete".length() * 11 * Constants.SCALEXY_NEW;
        deleteY = cancelY;

        frameX = APP_WIDTH / 20;
        frameWidth = 9 * APP_WIDTH / 10;
        frameY = APP_HEIGHT / 40 + myButtons.get(0).getHeight() + myButtons.get(0).getY1();
        frameHigh = APP_HEIGHT - frameY - APP_HEIGHT / 40;

        picWidth = frameHigh / 5;
        picHigh = frameHigh / 5;
        picX = frameX * 2;
        picY = APP_HEIGHT - bitmapFont.getCapHeight() * 3 - frameHigh / 5;

        /*
        Если здание еще не улучщено, создаем возможность его улучшить.
         */
        if (buildingsOnField.getBuilding().getBuildingLvl() == 0) {
            upgradeButtonX = picX + picWidth + frameX;
            upgradeButtonWidth = APP_WIDTH - upgradeButtonX - frameX * 2;
            upgradeButtonY = picY + picHigh / 2 - BUTTON_HEIGHT / 2;
            upgradeTextX = upgradeButtonX + upgradeButtonWidth / 2 - "upgrade".length() * 11 * SMALLER_SCALE;
            upgradeTextY = upgradeButtonY + BUTTON_HEIGHT / 2 + bitmapFontSmall.getCapHeight() / 2;
            myButtons.add(new MyButtons(upgradeButtonX, upgradeButtonWidth, upgradeButtonY, BUTTON_HEIGHT));
        }

        costY = picY - bitmapFont.getCapHeight() * 0.5f;

        resourceX = APP_WIDTH / 10 + bitmapFont.getCapHeight();
        resourcePicX = APP_WIDTH / 10;

        firstResourcePicYMoney = costY - bitmapFont.getCapHeight() * 2.5f;
        firstResourcePicYMetal = costY - bitmapFont.getCapHeight() * 3.6f;
        firstResourcePicYEnergy = costY - bitmapFont.getCapHeight() * 4.7f;
        inventLvlBuildingPicY = costY - bitmapFont.getCapHeight() * 5.8f;
        firstResourceYMoney = firstResourcePicYMoney + bitmapFont.getCapHeight();
        firstResourceYMetal = firstResourcePicYMetal + bitmapFont.getCapHeight();
        firstResourceYEnergy = firstResourcePicYEnergy + bitmapFont.getCapHeight();
        inventLvlBuildingY = inventLvlBuildingPicY + bitmapFont.getCapHeight();

        productivityY = inventLvlBuildingY - bitmapFont.getCapHeight() * 1.5f;

        secondResourcePicYMoney = productivityY - bitmapFont.getCapHeight() * 2.5f;
        secondResourcePicYMetal = productivityY - bitmapFont.getCapHeight() * 3.6f;
        secondResourcePicYEnergy = productivityY - bitmapFont.getCapHeight() * 4.7f;
        secondResourceYMoney = secondResourcePicYMoney + bitmapFont.getCapHeight();
        secondResourceYMetal = secondResourcePicYMetal + bitmapFont.getCapHeight();
        secondResourceYEnergy = secondResourcePicYEnergy + bitmapFont.getCapHeight();

        resourceHighWidth = bitmapFont.getCapHeight();
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

        bitmapFont.draw(batch, building.getName(), titleX, titleY);

        upNinePatch.draw(batch, picX - 1, picY - 1, picWidth + 2, picHigh + 2);
        batch.draw(picTextureRegion, picX, picY, picWidth, picHigh);

        bitmapFont.draw(batch, "cost:", resourcePicX, costY);
        if (building.isProductiveType())
            bitmapFont.draw(batch, "productivity:", resourcePicX, productivityY);
        else bitmapFont.draw(batch, "storage:", resourcePicX, productivityY);

        bitmapFont.draw(batch, String.valueOf(building.getCostMoney()), resourceX, firstResourceYMoney);
        bitmapFont.draw(batch, String.valueOf(building.getCostMetal()), resourceX, firstResourceYMetal);
        bitmapFont.draw(batch, String.valueOf(building.getCostEnergy()), resourceX, firstResourceYEnergy);
        bitmapFont.draw(batch, ">=" + building.getInventLvl(), resourceX, inventLvlBuildingY);

        batch.draw(moneyTexture, resourcePicX, firstResourcePicYMoney, resourceHighWidth, resourceHighWidth);
        batch.draw(metalTexture, resourcePicX, firstResourcePicYMetal, resourceHighWidth, resourceHighWidth);
        batch.draw(energyTexture, resourcePicX, firstResourcePicYEnergy, resourceHighWidth, resourceHighWidth);
        batch.draw(inventTexture, resourcePicX, inventLvlBuildingPicY, resourceHighWidth, resourceHighWidth);

        if (building.isProductiveType()) {
            bitmapFont.draw(batch, building.getUsefulMoney() + "/min", resourceX, secondResourceYMoney);
            bitmapFont.draw(batch, building.getUsefulMetal() + "/min", resourceX, secondResourceYMetal);
            bitmapFont.draw(batch, building.getUsefulEnergy() + "/min", resourceX, secondResourceYEnergy);
        } else {
            bitmapFont.draw(batch, "+" + building.getUsefulMoney(), resourceX, secondResourceYMoney);
            bitmapFont.draw(batch, "+" + building.getUsefulMetal(), resourceX, secondResourceYMetal);
            bitmapFont.draw(batch, "+" + building.getUsefulEnergy(), resourceX, secondResourceYEnergy);
        }

        batch.draw(moneyTexture, resourcePicX, secondResourcePicYMoney, resourceHighWidth, resourceHighWidth);
        batch.draw(metalTexture, resourcePicX, secondResourcePicYMetal, resourceHighWidth, resourceHighWidth);
        batch.draw(energyTexture, resourcePicX, secondResourcePicYEnergy, resourceHighWidth, resourceHighWidth);

        if (buildingsOnField.getBuilding().getBuildingLvl() == 0) {
            bitmapFontSmall.draw(batch, "upgrade", upgradeTextX, upgradeTextY);
        }

        bitmapFont.draw(batch, "cancel", cancelX, cancelY);
        bitmapFont.draw(batch, "delete", deleteX, deleteY);

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
                screenManager.setPlanetScreen(new Planet(sys, user));
                sys.setScreen(screenManager.getPlanetScreen());
                break;
            case 1:
                buildingsOnFields.get(currentPlanet).remove(buildingsOnField);
                if (building.isProductiveType()) {
                    incrementMoney -= building.getUsefulMoney();
                    incrementMetal -= building.getUsefulMetal();
                    incrementEnergy -= building.getUsefulEnergy();
                } else {
                    maxMoney -= building.getUsefulMoney();
                    maxMetal -= building.getUsefulMetal();
                    maxEnergy -= building.getUsefulEnergy();
                    if (user.getMoney() > maxMoney) user.setMoney(maxMoney);
                    if (user.getMetal() > maxMetal) user.setMetal(maxMetal);
                    if (user.getEnergy() > maxEnergy) user.setEnergy(maxEnergy);
                }
                sys.setScreen(screenManager.getPlanetScreen());
                break;
            case 2:
                /*
                Производим покупку улучшения здания и само улучшение.
                 */
                if (building.isProductiveType()) {
                    incrementMoney += building.getUsefulMoney();
                    incrementMetal += building.getUsefulMetal();
                    incrementEnergy += building.getUsefulEnergy();
                } else {
                    maxMoney += building.getUsefulMoney();
                    maxMetal += building.getUsefulMetal();
                    maxEnergy += building.getUsefulEnergy();
                }

                user.setMoney(user.getMoney() - building.getCostMoney());
                user.setMetal(user.getMetal() - building.getCostMetal());
                user.setEnergy(user.getEnergy() - building.getCostEnergy());

                building.setPicture(new Texture(Gdx.files.internal(
                        "buildings lvl up\\" + building.getName() + ".png")));
                building.setCostMoney(0);
                building.setCostMetal(0);
                building.setCostEnergy(0);
                building.setUsefulMoney(building.getUsefulMoney() * 2);
                building.setUsefulMetal(building.getUsefulMetal() * 2);
                building.setUsefulEnergy(building.getUsefulEnergy() * 2);
                building.setBuildingLvl(1);

                buildingsOnFields.get(currentPlanet).remove(buildingsOnField);
                buildingsOnFields.get(currentPlanet).add(new BuildingsOnField(building, buildingsOnField.getI(),
                        buildingsOnField.getJ()));

                screenManager.setMiniWindowDeletingBuildingFromFieldScreen(
                        new MiniWindowDeletingBuildingFromField(sys, user,
                                buildingsOnFields.get(currentPlanet).get(buildingsOnFields.get(currentPlanet).size() - 1)));
                sys.setScreen(screenManager.getMiniWindowDeletingBuildingFromFieldScreen());
        }
    }
}
