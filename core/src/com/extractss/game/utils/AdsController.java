package com.extractss.game.utils;

import com.extractss.game.SimpleClasses.User;

public interface AdsController {
    public void showRewardedVideo(int typeRes, int rewardValue, User user);
    public void showInitializeNonRewardedVideo();
    public void loadRewardedVideoAd();
}
