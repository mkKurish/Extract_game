package com.extractss.game.utils;

import com.extractss.game.ExtractSolarSys;
import com.extractss.game.SimpleClasses.User;
import com.extractss.game.screens.Construction;
import com.extractss.game.screens.GameOverScreen;
import com.extractss.game.screens.Inventory;
import com.extractss.game.screens.MainScreen;
import com.extractss.game.screens.MiniWindowBuying;
import com.extractss.game.screens.MiniWindowDeletingBuildingFromField;
import com.extractss.game.screens.MiniWindowInventory;
import com.extractss.game.screens.MiniWindowResetConfirmation;
import com.extractss.game.screens.NoConnectionToIncrementResources;
import com.extractss.game.screens.Planet;
import com.extractss.game.screens.Research;
import com.extractss.game.screens.SelectingPlanetScreen;
import com.extractss.game.screens.Settings;
import com.extractss.game.screens.Shop;
import com.extractss.game.screens.TrainingScreen;

public class ScreenManager {
    private Construction constructionScreen;
    private Inventory inventoryScreen;
    private MainScreen mainScreen;
    private Planet planetScreen;
    private Research researchScreen;
    private Settings settingsScreen;
    private Shop shopScreen;
    private TrainingScreen trainingScreen;
    private GameOverScreen gameOverScreen;
    private SelectingPlanetScreen selectingPlanetScreen;

    private MiniWindowBuying miniWindowBuyingScreen;
    private MiniWindowDeletingBuildingFromField miniWindowDeletingBuildingFromFieldScreen;
    private MiniWindowInventory miniWindowInventoryScreen;
    private MiniWindowResetConfirmation miniWindowResetConfirmationScreen;
    private NoConnectionToIncrementResources noConnectionToIncrementResourcesScreen;

    public ScreenManager(ExtractSolarSys sys, User user) {
        mainScreen = new MainScreen(sys, user);
        constructionScreen = new Construction(sys, user);
        inventoryScreen = new Inventory(sys, user);
        planetScreen = new Planet(sys, user);
        researchScreen = new Research(sys, user);
        settingsScreen = new Settings(sys, user);
        shopScreen = new Shop(sys, user);
        trainingScreen = new TrainingScreen(sys, user);
        gameOverScreen = new GameOverScreen(sys, user);
        selectingPlanetScreen = new SelectingPlanetScreen(sys, user);
    }

    public SelectingPlanetScreen getSelectingPlanetScreen() {
        return selectingPlanetScreen;
    }

    public void setSelectingPlanetScreen(SelectingPlanetScreen selectingPlanetScreen) {
        this.selectingPlanetScreen = selectingPlanetScreen;
    }

    public MiniWindowBuying getMiniWindowBuyingScreen() {
        return miniWindowBuyingScreen;
    }

    public void setMiniWindowBuyingScreen(MiniWindowBuying miniWindowBuyingScreen) {
        this.miniWindowBuyingScreen = miniWindowBuyingScreen;
    }

    public MiniWindowDeletingBuildingFromField getMiniWindowDeletingBuildingFromFieldScreen() {
        return miniWindowDeletingBuildingFromFieldScreen;
    }

    public void setMiniWindowDeletingBuildingFromFieldScreen(
            MiniWindowDeletingBuildingFromField miniWindowDeletingBuildingFromFieldScreen) {
        this.miniWindowDeletingBuildingFromFieldScreen = miniWindowDeletingBuildingFromFieldScreen;
    }

    public MiniWindowInventory getMiniWindowInventoryScreen() {
        return miniWindowInventoryScreen;
    }

    public void setMiniWindowInventoryScreen(MiniWindowInventory miniWindowInventoryScreen) {
        this.miniWindowInventoryScreen = miniWindowInventoryScreen;
    }

    public MiniWindowResetConfirmation getMiniWindowResetConfirmationScreen() {
        return miniWindowResetConfirmationScreen;
    }

    public void setMiniWindowResetConfirmationScreen(
            MiniWindowResetConfirmation miniWindowResetConfirmationScreen) {
        this.miniWindowResetConfirmationScreen = miniWindowResetConfirmationScreen;
    }

    public Construction getConstructionScreen() {
        return constructionScreen;
    }

    public Inventory getInventoryScreen() {
        return inventoryScreen;
    }

    public MainScreen getMainScreen() {
        return mainScreen;
    }

    public Planet getPlanetScreen() {
        return planetScreen;
    }

    public void setPlanetScreen(Planet planetScreen) {
        this.planetScreen = planetScreen;
    }

    public Research getResearchScreen() {
        return researchScreen;
    }

    public Settings getSettingsScreen() {
        return settingsScreen;
    }

    public Shop getShopScreen() {
        return shopScreen;
    }

    public void setShopScreen(Shop shopScreen) {
        this.shopScreen = shopScreen;
    }

    public TrainingScreen getTrainingScreen() {
        return trainingScreen;
    }

    public NoConnectionToIncrementResources getNoConnectionToIncrementResourcesScreen() {
        return noConnectionToIncrementResourcesScreen;
    }

    public void setNoConnectionToIncrementResourcesScreen(
            NoConnectionToIncrementResources noConnectionToIncrementResourcesScreen) {
        this.noConnectionToIncrementResourcesScreen = noConnectionToIncrementResourcesScreen;
    }

    public GameOverScreen getGameOverScreen() {
        return gameOverScreen;
    }

    public void setGameOverScreen(GameOverScreen gameOverScreen) {
        this.gameOverScreen = gameOverScreen;
    }
}
