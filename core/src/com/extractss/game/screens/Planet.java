package com.extractss.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.extractss.game.SimpleClasses.Building;
import com.extractss.game.ClassesForLists.BuildingsInInventory;
import com.extractss.game.ClassesForLists.BuildingsOnField;
import com.extractss.game.ExtractSolarSys;
import com.extractss.game.SimpleClasses.MyButtons;
import com.extractss.game.SimpleClasses.User;
import com.extractss.game.utils.Constants;
import com.extractss.game.utils.IncrementResourcesTimeCheck;

import java.util.ArrayList;
import java.util.Random;

import static com.extractss.game.ExtractSolarSys.backgroundsOther;
import static com.extractss.game.ExtractSolarSys.bitmapFont;
import static com.extractss.game.ExtractSolarSys.bitmapFontSmall;
import static com.extractss.game.ExtractSolarSys.buildingsOnFields;
import static com.extractss.game.ExtractSolarSys.buttonDownSound;
import static com.extractss.game.ExtractSolarSys.buttonUpSound;
import static com.extractss.game.ExtractSolarSys.crushSound;
import static com.extractss.game.ExtractSolarSys.currentPlanet;
import static com.extractss.game.ExtractSolarSys.defenseSound;
import static com.extractss.game.ExtractSolarSys.downNinePatch;
import static com.extractss.game.ExtractSolarSys.earthTexture;
import static com.extractss.game.ExtractSolarSys.energyTexture;
import static com.extractss.game.ExtractSolarSys.incrementMechanicMaxValue;
import static com.extractss.game.ExtractSolarSys.incrementMechanicValue;
import static com.extractss.game.ExtractSolarSys.incrementEnergy;
import static com.extractss.game.ExtractSolarSys.incrementMetal;
import static com.extractss.game.ExtractSolarSys.incrementMoney;
import static com.extractss.game.ExtractSolarSys.incrementTimeValue;
import static com.extractss.game.ExtractSolarSys.inventTexture;
import static com.extractss.game.ExtractSolarSys.inventoryBuildings;
import static com.extractss.game.ExtractSolarSys.jupiterTexture;
import static com.extractss.game.ExtractSolarSys.lastMeteorFellTime;
import static com.extractss.game.ExtractSolarSys.marsTexture;
import static com.extractss.game.ExtractSolarSys.maxEnergy;
import static com.extractss.game.ExtractSolarSys.maxMetal;
import static com.extractss.game.ExtractSolarSys.maxMeteorFellTime;
import static com.extractss.game.ExtractSolarSys.maxMoney;
import static com.extractss.game.ExtractSolarSys.mercuryTexture;
import static com.extractss.game.ExtractSolarSys.metalTexture;
import static com.extractss.game.ExtractSolarSys.meteorIsActive;
import static com.extractss.game.ExtractSolarSys.meteorTexture;
import static com.extractss.game.ExtractSolarSys.moneyTexture;
import static com.extractss.game.ExtractSolarSys.neptuneTexture;
import static com.extractss.game.ExtractSolarSys.planetFieldsBackground;
import static com.extractss.game.ExtractSolarSys.planetFieldsBackgrounds;
import static com.extractss.game.ExtractSolarSys.saturnTexture;
import static com.extractss.game.ExtractSolarSys.screenManager;
import static com.extractss.game.ExtractSolarSys.successSound;
import static com.extractss.game.ExtractSolarSys.unknownNinePatch;
import static com.extractss.game.ExtractSolarSys.upNinePatch;
import static com.extractss.game.ExtractSolarSys.uranusTexture;
import static com.extractss.game.ExtractSolarSys.venusTexture;
import static com.extractss.game.utils.Constants.APP_HEIGHT;
import static com.extractss.game.utils.Constants.APP_WIDTH;
import static com.extractss.game.utils.Constants.BOTTOM_BUTTONS_TEXT_Y;
import static com.extractss.game.utils.Constants.BUTTON_HEIGHT;
import static com.extractss.game.utils.Constants.SIDE_INDENT;
import static com.extractss.game.utils.Constants.NUMBER_OF_COLUMNS;
import static com.extractss.game.utils.Constants.NUMBER_OF_ROWS;
import static com.extractss.game.utils.Constants.SCALEXY_NEW;
import static com.extractss.game.utils.Constants.SCALEX_NEW_ORIGINAL;
import static com.extractss.game.utils.Constants.SCALEY_NEW_ORIGINAL;
import static com.extractss.game.utils.Constants.SIDE_OF_FIELD;
import static com.extractss.game.utils.Operations.isBuildingUnderDefense;
import static com.extractss.game.utils.Operations.isInPlace;
import static com.extractss.game.utils.Operations.isInPlaceMain;
import static com.extractss.game.utils.Operations.parseAndSavePrefsBuildings;

public class Planet extends BasicScreen {
    Building building;
    private BuildingsOnField currBuilding;
    private BuildingsInInventory inventoryItem;
    private BuildingsOnField currMeteorBuilding;

    TextureRegion textureRegion;

    private static float startFieldPosX;
    private static float startFieldPosY;
    private static float productivityLabelY;
    private static float productivityLabelHigh;
    private static float productivityTitleX;
    private float productivityTitleY;
    private static float productivityResourcePicX;
    private static float productivityResourceX;
    private static float productivityMoneyPicY;
    private static float productivityMetalPicY;
    private static float productivityEnergyPicY;
    private static float productivityMoneyY;
    private static float productivityMetalY;
    private static float productivityEnergyY;
    private static float productivityInventPicY;
    private static float productivityInventY;
    private static float cancelX;
    private static float menuX;
    private static float inventoryX;
    private static float meteorX;
    private static float meteorY;
    private static float meteorSpeedX;
    private static float meteorSpeedY;
    private static int meteorBuildingI;
    private static int meteorBuildingJ;
    private static long meteorFallingTime;

    IncrementResourcesTimeCheck incrementResourcesTimeCheck;

    /*
    Конструктор экрана планеты, если экран открыт обычным способом, через кнопку.
     */
    public Planet(ExtractSolarSys extractSolarSys, User user) {
        sys = extractSolarSys;
        this.user = user;
        this.building = null;

        batch = new SpriteBatch();

        switch (currentPlanet) {
            case 0:
                // Earth
                textureRegion = earthTexture;
                planetFieldsBackground = new NinePatch(planetFieldsBackgrounds.get(0), 10, 10, 10, 10);
                break;
            case 1:
                // Mars
                textureRegion = marsTexture;
                planetFieldsBackground = new NinePatch(planetFieldsBackgrounds.get(1), 10, 10, 10, 10);
                break;
            case 2:
                // Venus
                textureRegion = venusTexture;
                planetFieldsBackground = new NinePatch(planetFieldsBackgrounds.get(2), 10, 10, 10, 10);
                break;
            case 3:
                // Mercury
                textureRegion = mercuryTexture;
                planetFieldsBackground = new NinePatch(planetFieldsBackgrounds.get(3), 10, 10, 10, 10);
                break;
            case 4:
                // Jupiter
                textureRegion = jupiterTexture;
                planetFieldsBackground = new NinePatch(planetFieldsBackgrounds.get(2), 10, 10, 10, 10);
                break;
            case 5:
                // Saturn
                textureRegion = saturnTexture;
                planetFieldsBackground = new NinePatch(planetFieldsBackgrounds.get(2), 10, 10, 10, 10);
                break;
            case 6:
                // Uranus
                textureRegion = uranusTexture;
                planetFieldsBackground = new NinePatch(planetFieldsBackgrounds.get(4), 10, 10, 10, 10);
                break;
            case 7:
                // Neptune
                textureRegion = neptuneTexture;
                planetFieldsBackground = new NinePatch(planetFieldsBackgrounds.get(5), 10, 10, 10, 10);
                break;
        }

        productivityLabelHigh = bitmapFont.getCapHeight() * 2 + bitmapFontSmall.getCapHeight() * 6;
        productivityLabelY = APP_HEIGHT - productivityLabelHigh;

        /*
        Делаем адаптивный размер поля.
         */
        if (SCALEY_NEW_ORIGINAL > SCALEX_NEW_ORIGINAL) {
            SIDE_OF_FIELD = APP_WIDTH / (float) NUMBER_OF_COLUMNS - 4;
            startFieldPosX = 0;
            startFieldPosY = (productivityLabelY - BUTTON_HEIGHT - NUMBER_OF_ROWS * (SIDE_OF_FIELD + 4))/ (float) 2 + BUTTON_HEIGHT;
        } else {
            SIDE_OF_FIELD = (productivityLabelY - BUTTON_HEIGHT) / (float) NUMBER_OF_ROWS - 4;
            startFieldPosX = (APP_WIDTH - NUMBER_OF_COLUMNS * (SIDE_OF_FIELD + 4)) / (float) 2;
            startFieldPosY = BUTTON_HEIGHT;
        }

        myButtons = new ArrayList<>();
        myButtons.add(new MyButtons(0, APP_WIDTH / 2, 0, BUTTON_HEIGHT));
        myButtons.add(new MyButtons(APP_WIDTH / 2, APP_WIDTH / 2, 0, BUTTON_HEIGHT));
        myButton = myButtons.get(0);

        // Так как в обычном режиме мы будем отображать не надпись "productivity",
        // а кнопку перехода между планетами, то рассчитываем значение по У
        // исходя из названия кнопки.
        productivityTitleX = APP_WIDTH / 2 - "change planet".length() * 11 * bitmapFontSmall.getScaleX();
        productivityTitleY = APP_HEIGHT - bitmapFontSmall.getCapHeight() * 0.9f;
        productivityResourcePicX = SIDE_INDENT;
        productivityResourceX = productivityResourcePicX + bitmapFontSmall.getCapHeight() * 1.2f;

        myButtons.add(new MyButtons(0, APP_WIDTH, productivityLabelY,
                productivityLabelHigh - (APP_HEIGHT - productivityTitleY + bitmapFontSmall.getCapHeight() * 1.9f))); // Кликкер-кнопка
        myButtons.add(new MyButtons(0, APP_WIDTH, productivityTitleY - bitmapFontSmall.getCapHeight() * 1.9f,
                APP_HEIGHT - productivityTitleY + bitmapFontSmall.getCapHeight() * 1.9f));

        productivityMoneyPicY = productivityTitleY - bitmapFontSmall.getCapHeight() * 2.2f -
                bitmapFontSmall.getCapHeight();
        productivityMetalPicY = productivityTitleY - bitmapFontSmall.getCapHeight() * 2.2f -
                bitmapFontSmall.getCapHeight() * 2.3f;
        productivityEnergyPicY = productivityTitleY - bitmapFontSmall.getCapHeight() * 2.2f -
                bitmapFontSmall.getCapHeight() * 3.6f;
        productivityInventPicY = productivityTitleY - bitmapFontSmall.getCapHeight() * 2.2f -
                bitmapFontSmall.getCapHeight() * 4.9f;
        productivityMoneyY = productivityMoneyPicY + bitmapFontSmall.getCapHeight();
        productivityMetalY = productivityMetalPicY + bitmapFontSmall.getCapHeight();
        productivityEnergyY = productivityEnergyPicY + bitmapFontSmall.getCapHeight();
        productivityInventY = productivityInventPicY + bitmapFontSmall.getCapHeight();

        menuX = APP_WIDTH / 4 - "menu".length() * 11 * Constants.SCALEXY_NEW;
        inventoryX = 3 * APP_WIDTH / 4 - "inventory".length() * 11 * Constants.SCALEXY_NEW;

        meteorX = APP_WIDTH;
        meteorY = APP_HEIGHT;

        incrementResourcesTimeCheck = new IncrementResourcesTimeCheck(user);

        lastAnimationTime = System.currentTimeMillis();
    }

    /*
    Конструктор экрана планеты, если экран открыт через установление здания из инвентаря.
     */
    public Planet(ExtractSolarSys extractSolarSys, User user, BuildingsInInventory item) {
        sys = extractSolarSys;
        this.user = user;
        this.building = item.getBuilding();
        this.inventoryItem = item;

        batch = new SpriteBatch();

        switch (currentPlanet) {
            case 0:
                // Earth
                textureRegion = earthTexture;
                planetFieldsBackground = new NinePatch(planetFieldsBackgrounds.get(0), 10, 10, 10, 10);
                break;
            case 1:
                // Mars
                textureRegion = marsTexture;
                planetFieldsBackground = new NinePatch(planetFieldsBackgrounds.get(1), 10, 10, 10, 10);
                break;
            case 2:
                // Venus
                textureRegion = venusTexture;
                planetFieldsBackground = new NinePatch(planetFieldsBackgrounds.get(2), 10, 10, 10, 10);
                break;
            case 3:
                // Mercury
                textureRegion = mercuryTexture;
                planetFieldsBackground = new NinePatch(planetFieldsBackgrounds.get(3), 10, 10, 10, 10);
                break;
            case 4:
                // Jupiter
                textureRegion = jupiterTexture;
                planetFieldsBackground = new NinePatch(planetFieldsBackgrounds.get(2), 10, 10, 10, 10);
                break;
            case 5:
                // Saturn
                textureRegion = saturnTexture;
                planetFieldsBackground = new NinePatch(planetFieldsBackgrounds.get(2), 10, 10, 10, 10);
                break;
            case 6:
                // Uranus
                textureRegion = uranusTexture;
                planetFieldsBackground = new NinePatch(planetFieldsBackgrounds.get(4), 10, 10, 10, 10);
                break;
            case 7:
                // Neptune
                textureRegion = neptuneTexture;
                planetFieldsBackground = new NinePatch(planetFieldsBackgrounds.get(5), 10, 10, 10, 10);
                break;
        }

        productivityLabelHigh = bitmapFont.getCapHeight() * 2 + bitmapFontSmall.getCapHeight() * 6;
        productivityLabelY = APP_HEIGHT - productivityLabelHigh;

        /*
        Делаем адаптивный размер поля.
         */
        if (SCALEY_NEW_ORIGINAL > SCALEX_NEW_ORIGINAL) {
            SIDE_OF_FIELD = APP_WIDTH / (float) NUMBER_OF_COLUMNS - 4;
            startFieldPosX = 0;
            startFieldPosY = (productivityLabelY - BUTTON_HEIGHT - NUMBER_OF_ROWS * (SIDE_OF_FIELD + 4))/ (float) 2 + BUTTON_HEIGHT;
        } else {
            SIDE_OF_FIELD = (productivityLabelY - BUTTON_HEIGHT) / (float) NUMBER_OF_ROWS - 4;
            startFieldPosX = (APP_WIDTH - NUMBER_OF_COLUMNS * (SIDE_OF_FIELD + 4)) / (float) 2;
            startFieldPosY = BUTTON_HEIGHT;
        }

        myButtons = new ArrayList<>();
        myButtons.add(new MyButtons(0, APP_WIDTH, 0, BUTTON_HEIGHT));
        myButton = myButtons.get(0);

        bitmapFont = new BitmapFont(Gdx.files.internal("fontFiles\\ExtractFont.fnt"));
        bitmapFont.getData().setScale(Constants.SCALEXY_NEW, SCALEXY_NEW);

        productivityTitleX = APP_WIDTH / 2 - "productivity".length() * 11 * Constants.SCALEXY_NEW;
        productivityTitleY = APP_HEIGHT - bitmapFont.getCapHeight() * 0.9f;
        productivityResourcePicX = SIDE_INDENT;
        productivityResourceX = productivityResourcePicX + bitmapFontSmall.getCapHeight() * 1.2f;

        productivityMoneyPicY = APP_HEIGHT - bitmapFont.getCapHeight() -
                bitmapFont.getCapHeight() * 1.3f - bitmapFontSmall.getCapHeight();
        productivityMetalPicY = APP_HEIGHT - bitmapFont.getCapHeight() -
                bitmapFont.getCapHeight() * 1.3f - bitmapFontSmall.getCapHeight() * 2.3f;
        productivityEnergyPicY = APP_HEIGHT - bitmapFont.getCapHeight() -
                bitmapFont.getCapHeight() * 1.3f - bitmapFontSmall.getCapHeight() * 3.6f;
        productivityInventPicY = APP_HEIGHT - bitmapFont.getCapHeight() -
                bitmapFont.getCapHeight() * 1.3f - bitmapFontSmall.getCapHeight() * 4.9f;
        productivityMoneyY = productivityMoneyPicY + bitmapFontSmall.getCapHeight();
        productivityMetalY = productivityMetalPicY + bitmapFontSmall.getCapHeight();
        productivityEnergyY = productivityEnergyPicY + bitmapFontSmall.getCapHeight();
        productivityInventY = productivityInventPicY + bitmapFontSmall.getCapHeight();

        cancelX = APP_WIDTH / 2 - "cancel".length() * 11 * Constants.SCALEXY_NEW;

        meteorX = APP_WIDTH;
        meteorY = APP_HEIGHT;

        incrementResourcesTimeCheck = new IncrementResourcesTimeCheck(user);

        lastAnimationTime = System.currentTimeMillis();
    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl20.glClearColor(0, 0, 0, 1);

        doAnimationChange(); // Производим анимацию фона.

        /*
        Проверяем, прошла ли минута, чтобы увеличить значение внутриигровых рерурсов.
         */
        incrementResourcesTimeCheck.checkToIncrement();

        batch.begin();

        batch.draw(backgroundsOther.get(curScreenAnimation), 0, 0, APP_WIDTH, APP_HEIGHT);

        planetFieldsBackground.draw(batch, startFieldPosX, startFieldPosY,
                (SIDE_OF_FIELD + 4) * 5, (SIDE_OF_FIELD + 4) * 7);
        for (int i = 0; i < NUMBER_OF_COLUMNS; i++) {
            for (int j = 0; j < NUMBER_OF_ROWS; j++) {
                batch.draw(textureRegion, startFieldPosX + (SIDE_OF_FIELD + 4) * i,
                        startFieldPosY + (SIDE_OF_FIELD + 4) * j, SIDE_OF_FIELD, SIDE_OF_FIELD);
                /*
                Устанавливаем здание на выбранную ячейку поля и проверяем кнопки на нажатие.
                 */
                if (building != null && notInListBuildingsOnField(buildingsOnFields.get(currentPlanet), i, j)
                        && lastTouchTime != 0
                        && !Gdx.input.isTouched()
                        && isInPlaceMain(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY(),
                        startFieldPosX + (SIDE_OF_FIELD + 4) * i,
                        startFieldPosY + (SIDE_OF_FIELD + 4) * j, SIDE_OF_FIELD, SIDE_OF_FIELD)) {
                    if (building.isProductiveType()) {
                        incrementMoney += building.getUsefulMoney();
                        incrementMetal += building.getUsefulMetal();
                        incrementEnergy += building.getUsefulEnergy();
                    } else {
                        maxMoney += building.getUsefulMoney();
                        maxMetal += building.getUsefulMetal();
                        maxEnergy += building.getUsefulEnergy();
                        if (maxMoney > 999999) maxMoney = 999999;
                        if (maxMetal > 999999) maxMetal = 999999;
                        if (maxEnergy > 999999) maxEnergy = 999999;
                    }
                    building.setCostMoney((int) (building.getCostMoney() * 1.7f));
                    building.setCostMetal((int) (building.getCostMetal() * 1.7f));
                    building.setCostEnergy((int) (building.getCostEnergy() * 1.7f));
                    buildingsOnFields.get(currentPlanet).add(new BuildingsOnField(building, i, j));
                    successSound.play(user.getSoundsVolume());
                    buttonActivated(8);
                }
            }
        }

        /*
        Отрисовываем здания на поле.
         */
        if (buildingsOnFields.get(currentPlanet).size() != 0) {
            for (int i = 0; i < buildingsOnFields.get(currentPlanet).size(); i++) {
                batch.draw(buildingsOnFields.get(currentPlanet).get(i).getBuilding().getPicture(),
                        startFieldPosX + (SIDE_OF_FIELD + 4) * buildingsOnFields.get(currentPlanet).get(i).getI(), // startFieldPosX + (SIDE_OF_FIELD + 4) * buildingsOnFields.get(currentPlanet).get(i).getI(),
                        startFieldPosY + (SIDE_OF_FIELD + 4) * buildingsOnFields.get(currentPlanet).get(i).getJ(),
                        SIDE_OF_FIELD, SIDE_OF_FIELD);
            }
        }

        checkButtonTouches(); // Проверяем кнопки на нажатие.

        if (building != null) {
            upNinePatch.draw(batch, 0, productivityLabelY, APP_WIDTH, productivityLabelHigh);
            bitmapFont.draw(batch, "productivity:", productivityTitleX, productivityTitleY);
        } else bitmapFontSmall.draw(batch, "change planet", productivityTitleX, productivityTitleY);

        batch.draw(moneyTexture, productivityResourcePicX, productivityMoneyPicY,
                bitmapFontSmall.getCapHeight(), bitmapFontSmall.getCapHeight());
        batch.draw(metalTexture, productivityResourcePicX, productivityMetalPicY,
                bitmapFontSmall.getCapHeight(), bitmapFontSmall.getCapHeight());
        batch.draw(energyTexture, productivityResourcePicX, productivityEnergyPicY,
                bitmapFontSmall.getCapHeight(), bitmapFontSmall.getCapHeight());
        batch.draw(inventTexture, productivityResourcePicX, productivityInventPicY,
                bitmapFontSmall.getCapHeight(), bitmapFontSmall.getCapHeight());
        bitmapFontSmall.draw(batch, user.getMoney() + " / " + maxMoney + " (" + incrementMoney + "/min)",
                productivityResourceX, productivityMoneyY);
        bitmapFontSmall.draw(batch, user.getMetal() + " / " + maxMetal + " (" + incrementMetal + "/min)",
                productivityResourceX, productivityMetalY);
        bitmapFontSmall.draw(batch, user.getEnergy() + " / " + maxEnergy + " (" + incrementEnergy + "/min)",
                productivityResourceX, productivityEnergyY);
        bitmapFontSmall.draw(batch, user.getInvents() + " (LvL)", productivityResourceX,
                productivityInventY);

        if (meteorIsActive && building == null) {
            if (System.currentTimeMillis() - meteorFallingTime > 10) {
                meteorX -= meteorSpeedX;
                meteorY -= meteorSpeedY;
                meteorFallingTime = System.currentTimeMillis();
            }
            if (meteorX > startFieldPosX + (SIDE_OF_FIELD + 4) * meteorBuildingI - SIDE_OF_FIELD / 6f && // if (meteorX > startFieldPosX + (SIDE_OF_FIELD + 4) * meteorBuildingI - SIDE_OF_FIELD / 6f &&
                    meteorX < startFieldPosX + (SIDE_OF_FIELD + 4) * meteorBuildingI + SIDE_OF_FIELD / 6f) { // meteorX < startFieldPosX + (SIDE_OF_FIELD + 4) * meteorBuildingI + SIDE_OF_FIELD / 6f) {
                if (currMeteorBuilding != null) {
                    if (isBuildingUnderDefense(currMeteorBuilding)) {
                        defenseSound.play(user.getSoundsVolume());
                    } else {
                        if (currMeteorBuilding.getBuilding().isProductiveType()) {
                            incrementMoney -= currMeteorBuilding.getBuilding().getUsefulMoney();
                            incrementMetal -= currMeteorBuilding.getBuilding().getUsefulMetal();
                            incrementEnergy -= currMeteorBuilding.getBuilding().getUsefulEnergy();
                        } else {
                            maxMoney -= currMeteorBuilding.getBuilding().getUsefulMoney();
                            maxMetal -= currMeteorBuilding.getBuilding().getUsefulMetal();
                            maxEnergy -= currMeteorBuilding.getBuilding().getUsefulEnergy();
                            if (user.getMoney() > maxMoney) user.setMoney(maxMoney);
                            if (user.getMetal() > maxMetal) user.setMetal(maxMetal);
                            if (user.getEnergy() > maxEnergy) user.setEnergy(maxEnergy);
                        }
                        buildingsOnFields.get(currentPlanet).remove(currMeteorBuilding);
                        crushSound.play(user.getSoundsVolume());
                        parseAndSavePrefsBuildings(user);
                    }
                    meteorX = APP_WIDTH;
                    meteorY = APP_HEIGHT;
                    currMeteorBuilding = null;
                    meteorIsActive = false;
                }
            }
            if (meteorY < 0) {
                meteorX = APP_WIDTH;
                meteorY = APP_HEIGHT;
                currMeteorBuilding = null;
                meteorIsActive = false;
            }
            batch.draw(meteorTexture, meteorX, meteorY, SIDE_OF_FIELD, SIDE_OF_FIELD);
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

    private boolean notInListBuildingsOnField(ArrayList<BuildingsOnField> buildingsOnField,
                                              int i, int j) {
        for (int k = 0; k < buildingsOnField.size(); k++) {
            if (buildingsOnField.get(k).getI() == i && buildingsOnField.get(k).getJ() == j) {
                return false;
            }
        }
        return true;
    }

    private void miniWindowActivated(BuildingsOnField item) {
        screenManager.setMiniWindowDeletingBuildingFromFieldScreen(
                new MiniWindowDeletingBuildingFromField(sys, user, item));
        sys.setScreen(screenManager.getMiniWindowDeletingBuildingFromFieldScreen());
    }

    @Override
    public void buttonActivated(int i) {
        switch (i) {
            case 0:
                sys.setScreen(screenManager.getMainScreen());
                break;
            case 8:
                /*
                Удаляем здание из инвентаря.
                 */
                for (int j = inventoryBuildings.indexOf(inventoryItem) + 1; j < inventoryBuildings.size(); j++) {
                    inventoryBuildings.get(j).y -= inventoryItem.elementHeight;
                }
                inventoryBuildings.remove(inventoryItem);
            case 1:
                screenManager.setPlanetScreen(new Planet(sys, user));
                sys.setScreen(screenManager.getInventoryScreen());
                break;
            case 9:
                screenManager.setPlanetScreen(new Planet(sys, user));
                sys.setScreen(screenManager.getInventoryScreen());
                break;
            case 2:
                /*
                Увеличиваем значение внутриигровых ресурсов механически (нажатием кнопки слева).
                 */
                incrementMechanicValue++;
                if (incrementMechanicValue == incrementMechanicMaxValue) {
                    incrementMechanicValue = 0;
                    user.setMoney(user.getMoney() + incrementMoney);
                    user.setMetal(user.getMetal() + incrementMetal);
                    user.setEnergy(user.getEnergy() + incrementEnergy);
                    if (user.getMoney() > maxMoney) user.setMoney(maxMoney);
                    if (user.getMetal() > maxMetal) user.setMetal(maxMetal);
                    if (user.getEnergy() > maxEnergy) user.setEnergy(maxEnergy);
                    successSound.play(user.getSoundsVolume());
                }
                break;
            case 3:
                if (!meteorIsActive) sys.setScreen(screenManager.getSelectingPlanetScreen());
                break;
        }
    }

    @Override
    protected void checkButtonTouches() {
        if (building == null) {
            if (System.currentTimeMillis() - lastMeteorFellTime >= maxMeteorFellTime && !meteorIsActive) {
                Random random = new Random();
                meteorBuildingI = random.nextInt(NUMBER_OF_COLUMNS);
                meteorBuildingJ = random.nextInt(NUMBER_OF_ROWS);
                meteorSpeedX = (APP_WIDTH - (startFieldPosX + (SIDE_OF_FIELD + 4) * meteorBuildingI)) / 50; // meteorSpeedX = (APP_WIDTH - (startFieldPosX + (SIDE_OF_FIELD + 4) * meteorBuildingI)) / 50;
                meteorSpeedY = (APP_HEIGHT - (startFieldPosY + (SIDE_OF_FIELD + 4) * meteorBuildingJ)) / 50;
                lastMeteorFellTime = System.currentTimeMillis();
                meteorFallingTime = System.currentTimeMillis();
                for (int i = 0; i < buildingsOnFields.get(currentPlanet).size(); i++) {
                    if (buildingsOnFields.get(currentPlanet).get(i).getI() == meteorBuildingI && buildingsOnFields.get(currentPlanet).get(i).getJ() == meteorBuildingJ) {
                        currMeteorBuilding = buildingsOnFields.get(currentPlanet).get(i);
                    }
                }
                meteorIsActive = true;
            }

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
            for (int i = 0; i < buildingsOnFields.get(currentPlanet).size(); i++) {
                currBuilding = buildingsOnFields.get(currentPlanet).get(i);
                if (!Gdx.input.isTouched() && isInPlaceMain(touchedX, touchedY,
                        startFieldPosX + (SIDE_OF_FIELD + 4) * currBuilding.getI(), // startFieldPosX + (SIDE_OF_FIELD + 4) * currBuilding.getI()
                        startFieldPosY + (SIDE_OF_FIELD + 4) * currBuilding.getJ(),
                        SIDE_OF_FIELD, SIDE_OF_FIELD)) {
                    miniWindowActivated(currBuilding);
                }
            }
            bitmapFont.draw(batch, "menu", menuX, BOTTOM_BUTTONS_TEXT_Y);
            bitmapFont.draw(batch, "inventory", inventoryX, BOTTOM_BUTTONS_TEXT_Y);

            // Шкала кликкера
            unknownNinePatch.draw(batch, 0,
                    productivityLabelY, APP_WIDTH, productivityLabelHigh - (APP_HEIGHT - productivityTitleY + bitmapFontSmall.getCapHeight() * 1.9f));
            downNinePatch.draw(batch, 0,
                    productivityLabelY, APP_WIDTH *
                            ((incrementTimeValue) / (float) 60),
                    productivityLabelHigh - (APP_HEIGHT - productivityTitleY + bitmapFontSmall.getCapHeight() * 1.9f));
            upNinePatch.draw(batch, 0,
                    productivityLabelY, APP_WIDTH *
                            (incrementMechanicValue / (float) incrementMechanicMaxValue + 1 / (float) incrementMechanicMaxValue),
                    productivityLabelHigh - (APP_HEIGHT - productivityTitleY + bitmapFontSmall.getCapHeight() * 1.9f));
        } else {
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
                    this.buttonActivated(9);
                    touchedX = touchedY = -1;
                } else {
                    upNinePatch.draw(batch, myButton.getX1(), myButton.getY1(), myButton.getWidth(),
                            myButton.getHeight());
                }
            }
            bitmapFont.draw(batch, "cancel", cancelX, BOTTOM_BUTTONS_TEXT_Y);
        }
    }
}
