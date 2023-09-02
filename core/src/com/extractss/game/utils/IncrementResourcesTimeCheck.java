package com.extractss.game.utils;

import com.extractss.game.SimpleClasses.User;

import static com.extractss.game.ExtractSolarSys.incrementEnergy;
import static com.extractss.game.ExtractSolarSys.incrementMetal;
import static com.extractss.game.ExtractSolarSys.incrementMoney;
import static com.extractss.game.ExtractSolarSys.incrementTimeValue;
import static com.extractss.game.ExtractSolarSys.lastIncrementGatherTime;
import static com.extractss.game.ExtractSolarSys.maxEnergy;
import static com.extractss.game.ExtractSolarSys.maxMetal;
import static com.extractss.game.ExtractSolarSys.maxMoney;

public class IncrementResourcesTimeCheck {
    private final User user;

    public IncrementResourcesTimeCheck(User user) {
        this.user = user;
    }

    public void checkToIncrement() {
        /*
        Проверяем, сколько времени прошло с прошлого увеличения внутриигровых ресурсов игрока,
        если прошла одна минута, то увеличиваем количество внутриигровых ресурсов на значение
        продуктивности, которое совместно дают здания, поставленные игроком на поле.
         */
        incrementTimeValue = (int) (System.nanoTime() / 1_000_000_000L) - lastIncrementGatherTime;
        if (incrementTimeValue >= 60) {
            user.setMoney((int) (user.getMoney() + incrementMoney *
                    (incrementTimeValue - incrementTimeValue % 60) / 60));
            user.setMetal((int) (user.getMetal() + incrementMetal *
                    (incrementTimeValue - incrementTimeValue % 60) / 60));
            user.setEnergy((int) (user.getEnergy() + incrementEnergy *
                    (incrementTimeValue - incrementTimeValue % 60) / 60));
            if (user.getMoney() > maxMoney) user.setMoney(maxMoney);
            if (user.getMetal() > maxMetal) user.setMetal(maxMetal);
            if (user.getEnergy() > maxEnergy) user.setEnergy(maxEnergy);
            lastIncrementGatherTime = (int) (System.nanoTime() / 1_000_000_000L);
        }
    }
}
