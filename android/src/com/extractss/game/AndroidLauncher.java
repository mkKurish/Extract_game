package com.extractss.game;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.extractss.game.SimpleClasses.User;
import com.extractss.game.utils.AdsController;
import com.extractss.game.utils.URIOpenner;
/*import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;*/

public class AndroidLauncher extends AndroidApplication implements AdsController, URIOpenner { // , RewardedVideoAdListener
    User user;
    int typeRes;
    int rewardValue;

    //private RewardedVideoAd rewardedVideoAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new ExtractSolarSys(this, this), config);
        //rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        //rewardedVideoAd.setRewardedVideoAdListener(this);
    }



    @Override
    public void showRewardedVideo(int typeRes, int rewardValue, User user) {
        this.user = user;
        this.typeRes = typeRes;
        this.rewardValue = rewardValue;
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
/*                if(rewardedVideoAd.isLoaded()){
                    rewardedVideoAd.show();
                }
                else loadRewardedVideoAd();*/
            }
        });
    }

    @Override
    public void showInitializeNonRewardedVideo() {
        this.user = null;
        this.typeRes = -1;
        this.rewardValue = 0;
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
/*                if(rewardedVideoAd.isLoaded()){
                    rewardedVideoAd.show();
                }
                else loadRewardedVideoAd();*/
            }
        });
    }

    @Override
    public void loadRewardedVideoAd() {
/*        rewardedVideoAd.loadAd("ca-app-pub-6468301349850125/3728648718",
                new AdRequest.Builder().build());*/
    }

//    @Override
    public void onRewardedVideoAdLoaded() {
    }

//    @Override
    public void onRewardedVideoAdOpened() {

    }

//    @Override
    public void onRewardedVideoStarted() {

    }

//    @Override
    public void onRewardedVideoAdClosed() {
        loadRewardedVideoAd();
    }

//    @Override
/*    public void onRewarded(RewardItem rewardItem) {
        switch (typeRes) {
            case 0:
                user.setMoney(user.getMoney() + rewardValue);
                break;
            case 1:
                user.setMetal(user.getMetal() + rewardValue);
                break;
            case 2:
                user.setEnergy(user.getEnergy() + rewardValue);
                break;
        }
    }*/

//    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

//    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

//    @Override
    public void onRewardedVideoCompleted() {

    }

    @Override
    public void openURI_inBrowser(String uri) {
        Intent intentURI = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intentURI);
    }
}
