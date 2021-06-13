package com.extractss.game.utils;

import com.extractss.game.ExtractSolarSys;
import com.extractss.game.SimpleClasses.User;
import com.extractss.game.screens.NoConnectionToIncrementResources;

import static com.extractss.game.ExtractSolarSys.incrementEnergy;
import static com.extractss.game.ExtractSolarSys.incrementMetal;
import static com.extractss.game.ExtractSolarSys.incrementMoney;
import static com.extractss.game.ExtractSolarSys.incrementTimeValue;
import static com.extractss.game.ExtractSolarSys.isIncrementNormalModeAllowed;
import static com.extractss.game.ExtractSolarSys.isIncrementSuperModeAllowed;
import static com.extractss.game.ExtractSolarSys.lastIncrementGatherTime;
import static com.extractss.game.ExtractSolarSys.maxEnergy;
import static com.extractss.game.ExtractSolarSys.maxMetal;
import static com.extractss.game.ExtractSolarSys.maxMoney;
import static com.extractss.game.ExtractSolarSys.screenManager;
import static com.extractss.game.utils.Constants.MAX_INCREMENT_TIME_ALLOWED;
import static com.extractss.game.utils.Operations.getServerTime;

public class IncrementResourcesTimeCheck {
    private ExtractSolarSys sys;
    private User user;

    public IncrementResourcesTimeCheck(ExtractSolarSys sys, User user) {
        this.sys = sys;
        this.user = user;
    }

    public void test() {
        /*
        Проверяем, сколько времени прошло с прошлого увеличения внутриигровых ресурсов игрока,
        если прошла одна минута, то увеличиваем количество внутриигровых ресурсов на значение
        продуктивности, которое совместно дают здания, поставленные игроком на поле.
        Если не удалось получить время с сервера, то получаем системное время на устройстве.
        Если разница между прошлым временем увеличения внутриигровых ресурсов и текущим времем на
        устройстве составляет более 24 часов, то переключаемся на экран с предупреждением.
         */
        try {
            incrementTimeValue = getServerTime() - lastIncrementGatherTime;
            isIncrementSuperModeAllowed = true;
        } catch (Exception e) {
            incrementTimeValue = System.currentTimeMillis() - lastIncrementGatherTime;
            isIncrementSuperModeAllowed = false;
        }
        if (incrementTimeValue < 0)
            incrementTimeValue = 60000;
        if (incrementTimeValue > MAX_INCREMENT_TIME_ALLOWED * 1000 && isIncrementNormalModeAllowed
                && !isIncrementSuperModeAllowed) {
            screenManager.setNoConnectionToIncrementResourcesScreen(
                    new NoConnectionToIncrementResources(sys, user));
            sys.setScreen(screenManager.getNoConnectionToIncrementResourcesScreen());
        } else {
            if (incrementTimeValue >= 60000 && isIncrementNormalModeAllowed) {
                user.setMoney((int) (user.getMoney() + incrementMoney *
                        (incrementTimeValue - incrementTimeValue % 60000) / 60000));
                user.setMetal((int) (user.getMetal() + incrementMetal *
                        (incrementTimeValue - incrementTimeValue % 60000) / 60000));
                user.setEnergy((int) (user.getEnergy() + incrementEnergy *
                        (incrementTimeValue - incrementTimeValue % 60000) / 60000));
                if (user.getMoney() > maxMoney) user.setMoney(maxMoney);
                if (user.getMetal() > maxMetal) user.setMetal(maxMetal);
                if (user.getEnergy() > maxEnergy) user.setEnergy(maxEnergy);
                try {
                    lastIncrementGatherTime = getServerTime();
                } catch (Exception e) {
                    if (lastIncrementGatherTime <= System.currentTimeMillis()) lastIncrementGatherTime = System.currentTimeMillis();
                }
                if (sys.getScreen() != null) {
                    if (sys.getScreen().getClass() == NoConnectionToIncrementResources.class){
                        sys.setScreen(screenManager.getMainScreen());
                    }
                }
            } else if (incrementTimeValue >= 60000) {
                user.setMoney((int) (user.getMoney() + incrementMoney *
                        (MAX_INCREMENT_TIME_ALLOWED - MAX_INCREMENT_TIME_ALLOWED % 60) / 60));
                user.setMetal((int) (user.getMetal() + incrementMetal *
                        (MAX_INCREMENT_TIME_ALLOWED - MAX_INCREMENT_TIME_ALLOWED % 60) / 60));
                user.setEnergy((int) (user.getEnergy() + incrementEnergy *
                        (MAX_INCREMENT_TIME_ALLOWED - MAX_INCREMENT_TIME_ALLOWED % 60) / 60));
                if (user.getMoney() > maxMoney) user.setMoney(maxMoney);
                if (user.getMetal() > maxMetal) user.setMetal(maxMetal);
                if (user.getEnergy() > maxEnergy) user.setEnergy(maxEnergy);
                try {
                    lastIncrementGatherTime = getServerTime();
                } catch (Exception e) {
                    if (lastIncrementGatherTime <= System.currentTimeMillis()) lastIncrementGatherTime = System.currentTimeMillis();
                }
                isIncrementNormalModeAllowed = true;
                sys.setScreen(screenManager.getMainScreen());
            }
        }
    }
}
