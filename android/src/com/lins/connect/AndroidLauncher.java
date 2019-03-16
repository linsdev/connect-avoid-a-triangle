package com.lins.connect;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.lins.Ad;

public class AndroidLauncher extends AndroidApplication {
    InterstitialAd interstitialAd;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.numSamples = 2;
        config.useImmersiveMode = true;
        config.useWakelock = true;
        config.useAccelerometer = false;
        config.useCompass = false;

        final Ad ad = new Ad() {
            @Override
            public void showFullscreenAd() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        if (interstitialAd.isLoaded()) interstitialAd.show();
                    }
                });
            }
        };

        initialize(new App(ad), config);
        initAd();
	}

    private void initAd() {
        interstitialAd = new InterstitialAd(this);

        //interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");   // test
        // TODO: Uncomment this for Release
        interstitialAd.setAdUnitId("ca-app-pub-2930266676054929/2982677497");   // release

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
            }
        });
        requestNewInterstitial();
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder().build();
        interstitialAd.loadAd(adRequest);
    }
}