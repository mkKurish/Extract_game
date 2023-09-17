package com.extractss.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.extractss.game.ClassesForLists.BasicListItem;
import com.extractss.game.ClassesForLists.BuildingsOnField;
import com.extractss.game.ClassesForLists.ItemResearch;
import com.extractss.game.ClassesForLists.ItemSelectingPlanet;
import com.extractss.game.ClassesForLists.ItemShop;
import com.extractss.game.SimpleClasses.Building;
import com.extractss.game.SimpleClasses.MyButtons;
import com.extractss.game.SimpleClasses.User;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.ArrayList;

import static com.extractss.game.ExtractSolarSys.backFieldAtlas;
import static com.extractss.game.ExtractSolarSys.backgroundMusic;
import static com.extractss.game.ExtractSolarSys.backgroundsMain;
import static com.extractss.game.ExtractSolarSys.backgroundsOther;
import static com.extractss.game.ExtractSolarSys.bitmapFont;
import static com.extractss.game.ExtractSolarSys.bitmapFontSmall;
import static com.extractss.game.ExtractSolarSys.boughtPlanetsIds;
import static com.extractss.game.ExtractSolarSys.buildingsOnFields;
import static com.extractss.game.ExtractSolarSys.buttonDownSound;
import static com.extractss.game.ExtractSolarSys.buttonUpSound;
import static com.extractss.game.ExtractSolarSys.currentPlanet;
import static com.extractss.game.ExtractSolarSys.energyTexture;
import static com.extractss.game.ExtractSolarSys.gameLogo;
import static com.extractss.game.ExtractSolarSys.getIncrementMechanicUpgradeCost;
import static com.extractss.game.ExtractSolarSys.helpButtonSign;
import static com.extractss.game.ExtractSolarSys.incrementEnergy;
import static com.extractss.game.ExtractSolarSys.incrementMechanicMaxValue;
import static com.extractss.game.ExtractSolarSys.incrementMetal;
import static com.extractss.game.ExtractSolarSys.incrementMoney;
import static com.extractss.game.ExtractSolarSys.inventTexture;
import static com.extractss.game.ExtractSolarSys.inventoryBuildings;
import static com.extractss.game.ExtractSolarSys.isTrainingComplete;
import static com.extractss.game.ExtractSolarSys.lastAdNonRewardedShown;
import static com.extractss.game.ExtractSolarSys.lastIncrementGatherTime;
import static com.extractss.game.ExtractSolarSys.lastMeteorFellTime;
import static com.extractss.game.ExtractSolarSys.maxEnergy;
import static com.extractss.game.ExtractSolarSys.maxMetal;
import static com.extractss.game.ExtractSolarSys.maxMoney;
import static com.extractss.game.ExtractSolarSys.metalTexture;
import static com.extractss.game.ExtractSolarSys.moneyTexture;
import static com.extractss.game.ExtractSolarSys.musicTexture;
import static com.extractss.game.ExtractSolarSys.selectingPlanetArrayList;
import static com.extractss.game.ExtractSolarSys.settingsButtonSign;
import static com.extractss.game.ExtractSolarSys.soundTexture;
import static com.extractss.game.ExtractSolarSys.successSound;
import static com.extractss.game.utils.Constants.AVERAGE_VALUE_TO_BUY_RES;
import static com.extractss.game.utils.Constants.BUTTON_HEIGHT;
import static com.extractss.game.utils.Constants.MEDIUM_LEST_ELEMENT_HEIGHT;

public class Operations {
    public static boolean isEnableToBuy(User user, Building listItem) {
        /*
        Возвращаем ответ: может ли игрок купить здание, имея столько ресурсов, сколько у него есть.
         */
        return user.getMoney() >= listItem.getCostMoney() &&
                user.getEnergy() >= listItem.getCostEnergy() &&
                user.getMetal() >= listItem.getCostMetal() &&
                user.getInvents() >= listItem.getInventLvl();
    }

    public static boolean isEnableToBuy(User user, ItemResearch listItem) {
        /*
        Возвращаем ответ: может ли игрок купить здание, имея столько ресурсов, сколько у него есть.
        (Версия для экрана исследований)
         */
        return user.getMoney() >= listItem.getCostMoney() &&
                user.getEnergy() >= listItem.getCostEnergy() &&
                user.getMetal() >= listItem.getCostMetal() &&
                user.getInvents() == listItem.getInventLvl() - 1;
    }

    public static boolean isEnableToBuy(User user, ItemShop listItem) {
        /*
        Возвращаем ответ: может ли игрок купить здание, имея столько ресурсов, сколько у него есть.
        (Версия для экрана магазина)
         */
        return user.getMoney() >= listItem.getCostMoney() &&
                user.getEnergy() >= listItem.getCostEnergy() &&
                user.getMetal() >= listItem.getCostMetal();
    }

    public static boolean isEnableToBuy(User user, ItemSelectingPlanet listItem) {
        /*
        Возвращаем ответ: может ли игрок купить планету, имея столько ресурсов, сколько у него есть.
        (Версия для экрана смены планет)
         */
        return user.getMoney() >= listItem.getCostMoney() &&
                user.getEnergy() >= listItem.getCostEnergy() &&
                user.getMetal() >= listItem.getCostMetal() &&
                user.getInvents() >= listItem.getInventLvl() &&
                currentPlanet != selectingPlanetArrayList.indexOf(listItem);
    }

    public static void parseAndSavePrefsBuildings(User user) {
        /*
        Пересоздаем точку сохранения игры, сохраняем текущий результат: количество ресурсов,
         здания в инвентаре, на поле и т.д.
         */
        Preferences prefs = Gdx.app.getPreferences("com.extractss.GameProgress");

        prefs.clear();
        prefs.flush();

        prefs.putInteger("maxMoney", maxMoney);
        prefs.putInteger("maxMetal", maxMetal);
        prefs.putInteger("maxEnergy", maxEnergy);

        prefs.putInteger("money", user.getMoney());
        prefs.putInteger("metal", user.getMetal());
        prefs.putInteger("energy", user.getEnergy());
        prefs.putInteger("invents", user.getInvents());
        prefs.putInteger("lastIncrementGatherTime", lastIncrementGatherTime);
        prefs.putInteger("incrementMoney", incrementMoney);
        prefs.putInteger("incrementMetal", incrementMetal);
        prefs.putInteger("incrementEnergy", incrementEnergy);
        prefs.putInteger("AVERAGE_VALUE_TO_BUY_RES", AVERAGE_VALUE_TO_BUY_RES);

        if (inventoryBuildings.size() > 0) {
            for (int i = 0; i < inventoryBuildings.size(); i++) {
                prefs.putString("i" + i + "name",
                        inventoryBuildings.get(i).getBuilding().getName());
                prefs.putBoolean("i" + i + "productiveType",
                        inventoryBuildings.get(i).getBuilding().isProductiveType());
                prefs.putInteger("i" + i + "costMoney",
                        inventoryBuildings.get(i).getBuilding().getCostMoney());
                prefs.putInteger("i" + i + "costMetal",
                        inventoryBuildings.get(i).getBuilding().getCostMetal());
                prefs.putInteger("i" + i + "costEnergy",
                        inventoryBuildings.get(i).getBuilding().getCostEnergy());
                prefs.putInteger("i" + i + "usefulMoney",
                        inventoryBuildings.get(i).getBuilding().getUsefulMoney());
                prefs.putInteger("i" + i + "usefulMetal",
                        inventoryBuildings.get(i).getBuilding().getUsefulMetal());
                prefs.putInteger("i" + i + "usefulEnergy",
                        inventoryBuildings.get(i).getBuilding().getUsefulEnergy());
                prefs.putInteger("i" + i + "inventLvl",
                        inventoryBuildings.get(i).getBuilding().getInventLvl());
                prefs.putFloat("i" + i + "y", inventoryBuildings.get(i).y);
                prefs.putFloat("i" + i + "elementHeight", inventoryBuildings.get(i).elementHeight);
            }
        }

        prefs.putInteger("currentPlanet", currentPlanet);

        for (int j = 0; j < buildingsOnFields.size(); j++) {
            if (buildingsOnFields.get(j).size() > 0) {
                for (int i = 0; i < buildingsOnFields.get(j).size(); i++) {
                    prefs.putString(j + "f" + i + "name",
                            buildingsOnFields.get(j).get(i).getBuilding().getName());
                    prefs.putBoolean(j + "f" + i + "productiveType",
                            buildingsOnFields.get(j).get(i).getBuilding().isProductiveType());
                    prefs.putInteger(j + "f" + i + "costMoney",
                            buildingsOnFields.get(j).get(i).getBuilding().getCostMoney());
                    prefs.putInteger(j + "f" + i + "costMetal",
                            buildingsOnFields.get(j).get(i).getBuilding().getCostMetal());
                    prefs.putInteger(j + "f" + i + "costEnergy",
                            buildingsOnFields.get(j).get(i).getBuilding().getCostEnergy());
                    prefs.putInteger(j + "f" + i + "usefulMoney",
                            buildingsOnFields.get(j).get(i).getBuilding().getUsefulMoney());
                    prefs.putInteger(j + "f" + i + "usefulMetal",
                            buildingsOnFields.get(j).get(i).getBuilding().getUsefulMetal());
                    prefs.putInteger(j + "f" + i + "usefulEnergy",
                            buildingsOnFields.get(j).get(i).getBuilding().getUsefulEnergy());
                    prefs.putInteger(j + "f" + i + "inventLvl",
                            buildingsOnFields.get(j).get(i).getBuilding().getInventLvl());
                    prefs.putInteger(j + "f" + i + "i", buildingsOnFields.get(j).get(i).getI());
                    prefs.putInteger(j + "f" + i + "j", buildingsOnFields.get(j).get(i).getJ());
                    prefs.putInteger(j + "f" + i + "buildingLvl",
                            buildingsOnFields.get(j).get(i).getBuilding().getBuildingLvl());
                }
            }
        }

        for (int i = 0; i < boughtPlanetsIds.size(); i++){
            prefs.putInteger(i + "planetsIdList", boughtPlanetsIds.get(i));
        }

        prefs.putBoolean("soundsActive", user.isSoundsActive());
        prefs.putBoolean("musicActive", user.isMusicActive());

        prefs.putFloat("soundsVolume", user.getSoundsVolume());
        prefs.putFloat("musicVolume", user.getMusicVolume());

        prefs.putBoolean("isTrainingComplete", isTrainingComplete);

        prefs.putInteger("incrementMechanicMaxValue", incrementMechanicMaxValue);
        prefs.putInteger("getIncrementMechanicUpgradeCost", getIncrementMechanicUpgradeCost);

        prefs.putLong("lastMeteorFellTime", lastMeteorFellTime);

        prefs.putLong("lastAdNonRewardedShown", lastAdNonRewardedShown);

        prefs.flush();
    }

    public static boolean isInPlace(float curX, float curY, MyButtons item) {
        return isInPlaceMain(curX, curY, item.getX1(), item.getY1(), item.getWidth(),
                item.getHeight());
    }

    public static boolean isInPlaceMain(float curX, float curY, float x, float y, float width,
                                        float height) {
        /*
        Проверяем, попало ли касание в какую-то облась на экране или нет.
         */
        if (curX >= x && curX <= x + width) {
            return curY >= y && curY <= y + height;
        }
        return false;
    }

    public static void disposeAllResources() {
        /*
        Очищаем память от всех (Disposable) ресурсов мгры.
         */
        backgroundMusic.dispose();
        buttonDownSound.dispose();
        buttonUpSound.dispose();
        successSound.dispose();
        soundTexture.dispose();
        musicTexture.dispose();
        for (Texture t : backgroundsMain
        ) {
            t.dispose();
        }
        for (Texture t : backgroundsOther
        ) {
            t.dispose();
        }
        moneyTexture.dispose();
        metalTexture.dispose();
        energyTexture.dispose();
        inventTexture.dispose();
        bitmapFont.dispose();
        bitmapFontSmall.dispose();
        gameLogo.dispose();
        helpButtonSign.dispose();
        settingsButtonSign.dispose();
        backFieldAtlas.dispose();
    }

    public static long getServerTime() throws Exception {
        /*
        Пытаемся получить текущее время сервера.
         */
        System.setProperty("http.agent", "Chrome");
        String url = "https://time.is/Unix_time_now";
        Document doc = Jsoup.parse(new URL(url).openStream(), "UTF-8", url);
        String[] tags = new String[]{
                "div[id=time_section]",
                "div[id=clock0_bg]"
        };
        Elements elements = doc.select(tags[0]);
        for (String tag : tags) {
            elements = elements.select(tag);
        }
        return Long.parseLong(elements.text()) * 1000;
    }

    public static boolean isBuildingUnderDefense(BuildingsOnField buildingOnField) {
        /*
        Провеяем: стоит ли рядом со зданием защитник, чтобы спасти от метеорита.
        Защитник первого уровня действует на 5 клеток, включая себя (крестом).
        -   +   -
        +   +   +
        -   +   -
        Защитник второго уровня действует на 9 клеток, включая себя (квадратом).
        +   +   +
        +   +   +
        +   +   +
         */
        BuildingsOnField buildingForCycle;
        if (buildingOnField.getBuilding().getName().equals("defender")) return true;

        for (int k = 0; k < buildingsOnFields.get(currentPlanet).size(); k++) {
            buildingForCycle = buildingsOnFields.get(currentPlanet).get(k);

            if (buildingForCycle.getBuilding().getName().equals("defender")) {

                if (buildingForCycle.getI() >= buildingOnField.getI() - 1 &&
                        buildingForCycle.getI() <= buildingOnField.getI() + 1) {

                    if (buildingForCycle.getJ() >= buildingOnField.getJ() - 1 &&
                            buildingForCycle.getJ() <= buildingOnField.getJ() + 1) {

                        if (buildingForCycle.getBuilding().getBuildingLvl() == 1 ||
                                buildingForCycle.getI() == buildingOnField.getI() ||
                                buildingForCycle.getJ() == buildingOnField.getJ()) return true;

                    }
                }
            }
        }
        return false;
    }

    public static void initializeSelectingPlanetArrayList (ArrayList<ItemSelectingPlanet> listItems){
        int[] basicPricesMoney = {0, 167, 1243, 7567, 10743, 30278, 58345, 112345};
        int[] basicPricesMetal = {0, 348, 843, 3125, 15342, 37152, 64375, 75682};
        int[] basicPricesEnergy = {0, 243, 2134, 4657, 13467, 42057, 49835, 87351};
        for (int i = 0; i < 8; i++){
            if (boughtPlanetsIds.contains(i)){
                basicPricesMoney[i] = 0;
                basicPricesMetal[i] = 0;
                basicPricesEnergy[i] = 0;
            }
        }
        initializeSelectingPlanetArrayList(listItems, basicPricesMoney, basicPricesMetal, basicPricesEnergy);
    }
    private static void initializeSelectingPlanetArrayList (ArrayList<ItemSelectingPlanet> listItems, int [] moneyPlanets, int[] metalPlanets, int[] energyPlanets){
        listItems.clear();
        listItems.add(new ItemSelectingPlanet("earth", new TextureRegion(new Texture(Gdx.files.internal("pngFiles\\planets\\earth.png"))),
                moneyPlanets[0], metalPlanets[0], energyPlanets[0], 0,
                BUTTON_HEIGHT, MEDIUM_LEST_ELEMENT_HEIGHT));
        listItems.add(new ItemSelectingPlanet("mars", new TextureRegion(new Texture(Gdx.files.internal("pngFiles\\planets\\mars.png"))),
                moneyPlanets[1], metalPlanets[1], energyPlanets[1],1,
                listItems.get(listItems.size()-1).y + listItems.get(listItems.size() - 1).elementHeight,
                MEDIUM_LEST_ELEMENT_HEIGHT));
        listItems.add(new ItemSelectingPlanet("venus", new TextureRegion(new Texture(Gdx.files.internal("pngFiles\\planets\\venus.png"))),
                moneyPlanets[2], metalPlanets[2], energyPlanets[2],1,
                listItems.get(listItems.size()-1).y + listItems.get(listItems.size() - 1).elementHeight,
                MEDIUM_LEST_ELEMENT_HEIGHT));
        listItems.add(new ItemSelectingPlanet("mercury", new TextureRegion(new Texture(Gdx.files.internal("pngFiles\\planets\\mercury.png"))),
                moneyPlanets[3], metalPlanets[3], energyPlanets[3],2,
                listItems.get(listItems.size()-1).y + listItems.get(listItems.size() - 1).elementHeight,
                MEDIUM_LEST_ELEMENT_HEIGHT));
        listItems.add(new ItemSelectingPlanet("jupiter", new TextureRegion(new Texture(Gdx.files.internal("pngFiles\\planets\\jupiter.png"))),
                moneyPlanets[4], metalPlanets[4], energyPlanets[4],3,
                listItems.get(listItems.size()-1).y + listItems.get(listItems.size() - 1).elementHeight,
                MEDIUM_LEST_ELEMENT_HEIGHT));
        listItems.add(new ItemSelectingPlanet("saturn", new TextureRegion(new Texture(Gdx.files.internal("pngFiles\\planets\\saturn.png"))),
                moneyPlanets[5], metalPlanets[5], energyPlanets[5],3,
                listItems.get(listItems.size()-1).y + listItems.get(listItems.size() - 1).elementHeight,
                MEDIUM_LEST_ELEMENT_HEIGHT));
        listItems.add(new ItemSelectingPlanet("uranus", new TextureRegion(new Texture(Gdx.files.internal("pngFiles\\planets\\uranus.png"))),
                moneyPlanets[6], metalPlanets[6], energyPlanets[6],4,
                listItems.get(listItems.size()-1).y + listItems.get(listItems.size() - 1).elementHeight,
                MEDIUM_LEST_ELEMENT_HEIGHT));
        listItems.add(new ItemSelectingPlanet("neptune", new TextureRegion(new Texture(Gdx.files.internal("pngFiles\\planets\\neptune.png"))),
                moneyPlanets[7], metalPlanets[7], energyPlanets[7], 5,
                listItems.get(listItems.size()-1).y + listItems.get(listItems.size() - 1).elementHeight,
                MEDIUM_LEST_ELEMENT_HEIGHT));
    }

    public static float totalListHeight(ArrayList<? extends BasicListItem> lst){
        float result = 0;
        for (BasicListItem elem : lst){
            result += elem.elementHeight;
        }
        return result;
    }
}
