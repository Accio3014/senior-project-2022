package com.senior.senior_project;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class Crypto extends AppCompatActivity {

    private AdView mAdview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comingsoon);    // 웹 부분은 아직 미구현 상태라 Comingsoon 화면으로 이동

        MobileAds.initialize(this);

        // 광고 초기화
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override public void onInitializationComplete(InitializationStatus initializationStatus) {

            } });

        mAdview = findViewById(R.id.adView_com);                        //배너광고 레이아웃 가져오기
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdview.loadAd(adRequest);
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);                                //광고 사이즈는 배너 사이즈로 설정
        adView.setAdUnitId("ca-app-pub-8067120541813327~5285644714");
    }

} // public class Crypto extends AppCompatActivity {