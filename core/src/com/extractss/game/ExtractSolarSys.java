package com.extractss.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.extractss.game.ClassesForLists.BuildingsInInventory;
import com.extractss.game.ClassesForLists.BuildingsOnField;
import com.extractss.game.ClassesForLists.ItemSelectingPlanet;
import com.extractss.game.SimpleClasses.Building;
import com.extractss.game.SimpleClasses.User;
import com.extractss.game.utils.AdsController;
import com.extractss.game.utils.IncrementResourcesTimeCheck;
import com.extractss.game.utils.ScreenManager;

import java.util.ArrayList;

import static com.extractss.game.utils.Constants.AVERAGE_VALUE_TO_BUY_RES;
import static com.extractss.game.utils.Constants.MAX_INCREMENT_TIME_ALLOWED;
import static com.extractss.game.utils.Constants.defineVariables;
import static com.extractss.game.utils.Operations.getServerTime;
import static com.extractss.game.utils.Operations.initializeSelectingPlanetArrayList;

public class ExtractSolarSys extends Game {
    static User user;

    public static ScreenManager screenManager;

    public static ArrayList<BuildingsInInventory> inventoryBuildings;

    public static ArrayList<ArrayList<com.extractss.game.ClassesForLists.BuildingsOnField>> buildingsOnFields;
    public static int currentPlanet;

    public static ArrayList<ItemSelectingPlanet> selectingPlanetArrayList;

    public static boolean isIncrementNormalModeAllowed = true;
    public static boolean isIncrementSuperModeAllowed = false;

    public static boolean isTrainingComplete;

    public static int incrementMechanicValue = 0;
    public static int incrementMechanicMaxValue;
    public static int getIncrementMechanicUpgradeCost;

    public static long lastMeteorFellTime;
    public static long maxMeteorFellTime = 3600000;
    public static boolean meteorIsActive = false;

    public static int incrementMoney;
    public static int incrementMetal;
    public static int incrementEnergy;
    public static int maxMoney;
    public static int maxMetal;
    public static int maxEnergy;
    public static long lastIncrementGatherTime;
    public static long incrementTimeValue;
    public static long incrementingThreadTime;

    public static Music backgroundMusic;
    public static Sound buttonDownSound;
    public static Sound buttonUpSound;
    public static Sound successSound;
    public static Sound crushSound;
    public static Sound defenseSound;

    public static Texture soundTexture;
    public static Texture musicTexture;

    public static ArrayList<Texture> backgroundsMain;
    public static ArrayList<Texture> backgroundsOther;

    public static Texture moneyTexture;
    public static Texture metalTexture;
    public static Texture energyTexture;
    public static Texture inventTexture;
    public static Texture meteorTexture;

    public static TextureRegion earthTexture;
    public static TextureRegion marsTexture;
    public static TextureRegion venusTexture;
    public static TextureRegion mercuryTexture;
    public static TextureRegion jupiterTexture;
    public static TextureRegion saturnTexture;
    public static TextureRegion uranusTexture;
    public static TextureRegion neptuneTexture;

    public static BitmapFont bitmapFont;
    public static BitmapFont bitmapFontSmall;
    public static BitmapFont bitmapFontReversedColorSmall;

    public static NinePatch progressBarBackNinePatch;
    public static NinePatch progressBarKnobNinePatch;
    public static NinePatch upNinePatch;
    public static NinePatch downNinePatch;
    public static NinePatch listButtonUp;
    public static NinePatch listButtonDown;
    public static NinePatch resetButtonDown;
    public static NinePatch resetButtonUp;

    public static NinePatch unknownNinePatch;

    public static Texture gameLogo;
    public static Texture helpButtonSign;
    public static Texture settingsButtonSign;

    public static Texture backFieldAtlas;

    public static ArrayList<Texture> planetFieldsBackgrounds;
    public static NinePatch planetFieldsBackground;

    private final AdsController adsController;

    public static long lastAdNonRewardedShown;

    public ExtractSolarSys(AdsController adsController) {
        this.adsController = adsController;
    }

    public void showAd(int typeRes, int rewardValue, User user) {
        if (adsController != null) {
            adsController.showRewardedVideo(typeRes, rewardValue, user);
        }
    }

    public void showNonRewardedAd() {
        if (adsController != null) {
            adsController.showInitializeNonRewardedVideo();
        }
    }


    @Override
    public void create() {
        /*
        Эти списки будем использовать для установки зданий в инвентарь и на поле.
         */
        inventoryBuildings = new ArrayList<>();
        buildingsOnFields = new ArrayList<>(8);
        for (int i = 0; i < 8; i++) {
            buildingsOnFields.add(new ArrayList<BuildingsOnField>());
        }
        selectingPlanetArrayList = new ArrayList<>();

        backFieldAtlas = new Texture(Gdx.files.internal("pngFiles\\PlanetTextures.png"));

        earthTexture = new TextureRegion(backFieldAtlas, 0, 2 * backFieldAtlas.getHeight() / 3 + 1,
                backFieldAtlas.getWidth() / 3, backFieldAtlas.getHeight() / 3);
        marsTexture = new TextureRegion(backFieldAtlas, 0, backFieldAtlas.getHeight() / 3 + 1,
                backFieldAtlas.getWidth() / 3, backFieldAtlas.getHeight() / 3-2);
        venusTexture = new TextureRegion(backFieldAtlas, 0, 0,
                backFieldAtlas.getWidth() / 3, backFieldAtlas.getHeight() / 3);
        mercuryTexture = new TextureRegion(backFieldAtlas, backFieldAtlas.getHeight() / 3, 0,
                backFieldAtlas.getWidth() / 3, backFieldAtlas.getHeight() / 3);
        jupiterTexture = new TextureRegion(backFieldAtlas, 2 * backFieldAtlas.getHeight() / 3 + 1, 0,
                backFieldAtlas.getWidth() / 3-1, backFieldAtlas.getHeight() / 3);
        saturnTexture = new TextureRegion(backFieldAtlas, 2 * backFieldAtlas.getHeight() / 3 + 1, backFieldAtlas.getHeight() / 3,
                backFieldAtlas.getWidth() / 3-1, backFieldAtlas.getHeight() / 3);
        uranusTexture = new TextureRegion(backFieldAtlas, 2 * backFieldAtlas.getHeight() / 3, 2 * backFieldAtlas.getHeight() / 3 + 1,
                backFieldAtlas.getWidth() / 3, backFieldAtlas.getHeight() / 3-1);
        neptuneTexture = new TextureRegion(backFieldAtlas, backFieldAtlas.getHeight() / 3, 2 * backFieldAtlas.getHeight() / 3,
                backFieldAtlas.getWidth() / 3, backFieldAtlas.getHeight() / 3);

        /*
        Пытаемся достать сохранения. Если сохранений не найдено,
        производим загрузку для самого начала игры.
         */
        Preferences preferences = Gdx.app.getPreferences("com.extractss.GameProgress");

        if (!preferences.contains("money")) {
            lastIncrementGatherTime = 0;
            user = new com.extractss.game.SimpleClasses.User(40, 40, 40, 0);

            incrementMoney = 0;
            incrementMetal = 0;
            incrementEnergy = 0;

            maxMoney = 1000;
            maxMetal = 1000;
            maxEnergy = 1000;

            currentPlanet = 0;

            AVERAGE_VALUE_TO_BUY_RES = 0;

            incrementMechanicMaxValue = 21;

            getIncrementMechanicUpgradeCost = 150;

            lastMeteorFellTime = System.currentTimeMillis();

            lastAdNonRewardedShown = System.currentTimeMillis();

            selectingPlanetArrayList = initializeSelectingPlanetArrayList(selectingPlanetArrayList);

            isTrainingComplete = false;
            try {
                lastIncrementGatherTime = getServerTime();
            } catch (Exception e) {
                lastIncrementGatherTime = System.currentTimeMillis();
            }
        } else {
            lastIncrementGatherTime = preferences.getLong("lastIncrementGatherTime");

            maxMoney = preferences.getInteger("maxMoney");
            maxMetal = preferences.getInteger("maxMetal");
            maxEnergy = preferences.getInteger("maxEnergy");

            user = new User(preferences.getInteger("money"),
                    preferences.getInteger("metal"),
                    preferences.getInteger("energy"),
                    preferences.getInteger("invents"));

            incrementMoney = preferences.getInteger("incrementMoney");
            incrementMetal = preferences.getInteger("incrementMetal");
            incrementEnergy = preferences.getInteger("incrementEnergy");

            AVERAGE_VALUE_TO_BUY_RES = preferences.getInteger("AVERAGE_VALUE_TO_BUY_RES");

            for (int i = 0; i < 999; i++) {
                if (!preferences.contains("i" + i + "name")) {
                    break;
                }
                inventoryBuildings.add(new BuildingsInInventory(
                        new Building(
                                preferences.getString("i" + i + "name"),
                                preferences.getBoolean("i" + i + "productiveType"),
                                preferences.getInteger("i" + i + "costMoney"),
                                preferences.getInteger("i" + i + "costMetal"),
                                preferences.getInteger("i" + i + "costEnergy"),
                                preferences.getInteger("i" + i + "usefulMoney"),
                                preferences.getInteger("i" + i + "usefulMetal"),
                                preferences.getInteger("i" + i + "usefulEnergy"),
                                preferences.getInteger("i" + i + "inventLvl"),
                                0, 0),
                        preferences.getFloat("i" + i + "y"),
                        preferences.getFloat("i" + i + "elementHeight")));
            }

            currentPlanet = preferences.getInteger("currentPlanet");

            for (int j = 0; j < buildingsOnFields.size(); j++) {
                for (int i = 0; i < 999; i++) {
                    if (!preferences.contains(j + "f" + i + "name")) {
                        break;
                    }
                    buildingsOnFields.get(j).add(new BuildingsOnField(
                            new Building(preferences.getString(j + "f" + i + "name"),
                                    preferences.getBoolean(j + "f" + i + "productiveType"),
                                    preferences.getInteger(j + "f" + i + "costMoney"),
                                    preferences.getInteger(j + "f" + i + "costMetal"),
                                    preferences.getInteger(j + "f" + i + "costEnergy"),
                                    preferences.getInteger(j + "f" + i + "usefulMoney"),
                                    preferences.getInteger(j + "f" + i + "usefulMetal"),
                                    preferences.getInteger(j + "f" + i + "usefulEnergy"),
                                    preferences.getInteger(j + "f" + i + "inventLvl"),
                                    0, 0),
                            preferences.getInteger(j + "f" + i + "i"),
                            preferences.getInteger(j + "f" + i + "j")));
                    buildingsOnFields.get(j).get(i).getBuilding().setBuildingLvl(
                            preferences.getInteger(j + "f" + i + "buildingLvl"));
                    if (preferences.getInteger(j + "f" + i + "buildingLvl") == 1)
                        buildingsOnFields.get(j).get(i).getBuilding().setPicture(
                                new Texture(Gdx.files.internal("buildings lvl up\\" +
                                        buildingsOnFields.get(j).get(i).getBuilding().getName() + ".png")));
                }
            }

            for (int i = 0; i < 8; i++) {
                TextureRegion thisTexture;
                switch (i){
                    case 0:
                        thisTexture = new TextureRegion(new Texture(Gdx.files.internal("pngFiles\\planets\\earth.png")));
                        break;
                    case 1:
                        thisTexture = new TextureRegion(new Texture(Gdx.files.internal("pngFiles\\planets\\mars.png")));
                        break;
                    case 2:
                        thisTexture = new TextureRegion(new Texture(Gdx.files.internal("pngFiles\\planets\\venus.png")));
                        break;
                    case 3:
                        thisTexture = new TextureRegion(new Texture(Gdx.files.internal("pngFiles\\planets\\mercury.png")));
                        break;
                    case 4:
                        thisTexture = new TextureRegion(new Texture(Gdx.files.internal("pngFiles\\planets\\jupiter.png")));
                        break;
                    case 5:
                        thisTexture = new TextureRegion(new Texture(Gdx.files.internal("pngFiles\\planets\\saturn.png")));
                        break;
                    case 6:
                        thisTexture = new TextureRegion(new Texture(Gdx.files.internal("pngFiles\\planets\\uranus.png")));
                        break;
                    case 7:
                        thisTexture = new TextureRegion(new Texture(Gdx.files.internal("pngFiles\\planets\\neptune.png")));
                        break;
                    default:
                        thisTexture = null;
                }
                selectingPlanetArrayList.add(new ItemSelectingPlanet(
                        preferences.getString(i + "selectingPlanetArrayList" + "name"),
                        thisTexture,
                        preferences.getInteger(i + "selectingPlanetArrayList" + "money"),
                        preferences.getInteger(i + "selectingPlanetArrayList" + "metal"),
                        preferences.getInteger(i + "selectingPlanetArrayList" + "energy"),
                        preferences.getInteger(i + "selectingPlanetArrayList" + "invent"),
                        preferences.getFloat(i + "selectingPlanetArrayList" + "y"),
                        preferences.getFloat(i + "selectingPlanetArrayList" + "elementHeight")));
            }

            user.setSoundsActive(preferences.getBoolean("soundsActive"));
            user.setMusicActive(preferences.getBoolean("musicActive"));

            user.setSoundsVolume(preferences.getFloat("soundsVolume"));
            user.setMusicVolume(preferences.getFloat("musicVolume"));

            isTrainingComplete = preferences.getBoolean("isTrainingComplete");

            incrementMechanicMaxValue = preferences.getInteger("incrementMechanicMaxValue");
            getIncrementMechanicUpgradeCost = preferences.getInteger("getIncrementMechanicUpgradeCost");

            lastMeteorFellTime = preferences.getLong("lastMeteorFellTime");

            lastAdNonRewardedShown = preferences.getLong("lastAdNonRewardedShown");

            if (System.currentTimeMillis() < lastMeteorFellTime)
                lastMeteorFellTime = maxMeteorFellTime;

            preferences.flush();
        }

        if (System.currentTimeMillis() - lastAdNonRewardedShown > 21600000) {
            showNonRewardedAd();
            lastAdNonRewardedShown = System.currentTimeMillis();
        }

        /*
        Загружаем все нужные текстуры, которые будут использоваться на разных экранах.
        (Делаем это только один раз, здесь, чтобы в дальнейшем не пришлось загружать каждый
        раз, когда они потребуются)
         */
        backgroundsMain = new ArrayList<>();
        backgroundsMain.add(new Texture(Gdx.files.internal(
                "backgrounds\\mainScreenBack\\mainScreenBackground animation1.png")));
        backgroundsMain.add(new Texture(Gdx.files.internal(
                "backgrounds\\mainScreenBack\\mainScreenBackground animation2.png")));
        backgroundsOther = new ArrayList<>();
        backgroundsOther.add(new Texture(Gdx.files.internal(
                "backgrounds\\otherScreensBack\\otherScreenBackground animation1.png")));
        backgroundsOther.add(new Texture(Gdx.files.internal(
                "backgrounds\\otherScreensBack\\otherScreenBackground animation2.png")));

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("audio\\music\\backgroundTrackMyOwn.mp3"));
        buttonDownSound = Gdx.audio.newSound(Gdx.files.internal("audio\\sounds\\button down.mp3"));
        buttonUpSound = Gdx.audio.newSound(Gdx.files.internal("audio\\sounds\\button up.mp3"));
        successSound = Gdx.audio.newSound(Gdx.files.internal("audio\\sounds\\success.mp3"));

        crushSound = Gdx.audio.newSound(Gdx.files.internal("audio\\sounds\\crush.mp3"));
        defenseSound = Gdx.audio.newSound(Gdx.files.internal("audio\\sounds\\defense.mp3"));

        moneyTexture = new Texture(Gdx.files.internal("resourceIcons\\money icon.png"));
        metalTexture = new Texture(Gdx.files.internal("resourceIcons\\metal icon.png"));
        energyTexture = new Texture(Gdx.files.internal("resourceIcons\\energy icon.png"));
        inventTexture = new Texture(Gdx.files.internal("resourceIcons\\invents.png"));

        meteorTexture = new Texture(Gdx.files.internal("pngFiles\\meteor.png"));

        bitmapFont = new BitmapFont(Gdx.files.internal("fontFiles\\ExtractFont.fnt"));
        bitmapFontSmall = new BitmapFont(Gdx.files.internal("fontFiles\\ExtractFont.fnt"));
        bitmapFontReversedColorSmall = new BitmapFont(Gdx.files.internal("fontFiles\\ExtractFontReversedColor.fnt"));

        progressBarBackNinePatch = new NinePatch(new Texture(Gdx.files.internal(
                "interactive\\interactiveBlueDown.png")), 14, 14, 14, 14);
        progressBarKnobNinePatch = new NinePatch(new Texture(Gdx.files.internal(
                "interactive\\interactiveBlueUp.png")), 14, 14, 14, 14);
        upNinePatch = new NinePatch(new Texture(Gdx.files.internal(
                "interactive\\interactiveBlueUp.png")), 14, 14, 14, 14);
        downNinePatch = new NinePatch(new Texture(Gdx.files.internal(
                "interactive\\interactiveBlueDown.png")), 14, 14, 14, 14);
        listButtonUp = new NinePatch(new Texture(Gdx.files.internal(
                "interactive\\interactiveBlueUp.png")), 14, 14, 14, 14);
        listButtonDown = new NinePatch(new Texture(Gdx.files.internal(
                "interactive\\interactiveRedDown.png")), 14, 14, 14, 14);
        resetButtonDown = new NinePatch(new Texture(Gdx.files.internal(
                "interactive\\interactiveRedDown.png")), 14, 14, 14, 14);
        resetButtonUp = new NinePatch(new Texture(Gdx.files.internal(
                "interactive\\interactiveRedUp.png")), 14, 14, 14, 14);

        unknownNinePatch = new NinePatch(new Texture(Gdx.files.internal(
                "interactive\\interactiveGrey.png")), 14, 14, 14, 14);

        gameLogo = new Texture("pngFiles\\LogoMainScreen.png");
        helpButtonSign = new Texture(Gdx.files.internal("pngFiles\\helpButtonMainScreen.png"));
        settingsButtonSign = new Texture(Gdx.files.internal("pngFiles\\settingsButtonMainScreen.png"));

        planetFieldsBackgrounds = new ArrayList<>();
        planetFieldsBackgrounds.add(new Texture(Gdx.files.internal("pngFiles\\forPlanetsFieldsBackground\\1.png")));
        planetFieldsBackgrounds.add(new Texture(Gdx.files.internal("pngFiles\\forPlanetsFieldsBackground\\2.png")));
        planetFieldsBackgrounds.add(new Texture(Gdx.files.internal("pngFiles\\forPlanetsFieldsBackground\\3.png")));
        planetFieldsBackgrounds.add(new Texture(Gdx.files.internal("pngFiles\\forPlanetsFieldsBackground\\4.png")));
        planetFieldsBackgrounds.add(new Texture(Gdx.files.internal("pngFiles\\forPlanetsFieldsBackground\\5.png")));
        planetFieldsBackgrounds.add(new Texture(Gdx.files.internal("pngFiles\\forPlanetsFieldsBackground\\6.png")));

        backgroundMusic.setVolume(user.getMusicVolume());
        backgroundMusic.setLooping(true);
        backgroundMusic.play();

        /*
        Определяем все переменные, которые нам потребуются для расположения элементов игры на экране.
        Размеры и координаты рассчитываются на основе разрешения экрана устройства, на котором
        запущена игра, что делает дизайн адаптивным.
         */
        defineVariables();

        incrementingThreadTime = System.currentTimeMillis();

        /*
        Инициализируем сразу все экраны, которые можем, чтобы это не занимало лишнего времени
        в течение игры.
         */

        screenManager = new ScreenManager(this, user);

        /*
        Проходим обучение перед игрой, если игра запущена первый раз.
         */
        if (isTrainingComplete) {
            IncrementResourcesTimeCheck incrementResourcesTimeCheck =
                    new IncrementResourcesTimeCheck(this, user);
            incrementResourcesTimeCheck.test();

            if (incrementTimeValue <= MAX_INCREMENT_TIME_ALLOWED * 1000L)
                this.setScreen(screenManager.getMainScreen());
        } else {
            this.setScreen(screenManager.getTrainingScreen());
        }
    }
}
