/* ---------------------------------------------------------------------------------------------- */

/*
본 자바 파일은 메인 화면에서 Foreincs 버튼 터치시 나타나는 화면에 대해 수정하는 코드이다.

화면을 구성하는 forensics.xml 파일에 스피너를 사용해 사용자가 원하는 분야 분야 선택 시 해당 가이드가 출력된다.
가이드의 텍스트는 xml파일에 구성을 학고 있으며, 본 파일에서는 분야 선택시 해당 분야에 맞게 화면 전환을 실행한다.
화면 전화의 경우 xml파일에 분야에 맞는 각각의 내용을 모두 중첩 레이아웃을 사용했으며, 본 파일에서 레이아웃의 setVisibility
설정을 수정하여 내용 변경을 보여준다.
*/

/* ---------------------------------------------------------------------------------------------- */


package com.senior.senior_project;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class Forensics extends AppCompatActivity {

    // 각 스피너의 선택한 값을 가져오기 위한 변수 선언
    String main_text = "";      // 대분류
    String sub_text = "";       // 소분류

    // 중첩으로 구성한 레이아웃
    LinearLayout f_basic;       // Basic 내용
    LinearLayout vol_2;         // Memory의 Volatility 2 내용
    LinearLayout comming_soon;  // 아직 구현하지 못한 파일의 내용

    private AdView mAdview;

    // 화면 구성
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forensics);                     // forensics.xml 파일로 레이아웃 구성

        // xml 파일의 스피너 및 레이아웃 선언
        Spinner spinner_main = findViewById(R.id.spinner);
        Spinner spinner_sub = findViewById(R.id.spinner_mem);

        f_basic = findViewById(R.id.f_basic);
        vol_2 = findViewById(R.id.vol_2);
        comming_soon = findViewById(R.id.comming_soon);

        MobileAds.initialize(this);

        // 초기화
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override public void onInitializationComplete(InitializationStatus initializationStatus) {

            } });

        mAdview = findViewById(R.id.adView); //배너광고 레이아웃 가져오기
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdview.loadAd(adRequest);
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER); //광고 사이즈는 배너 사이즈로 설정
        adView.setAdUnitId("ca-app-pub-8067120541813327~5285644714");





        // 대분류 스피너 이벤트
        spinner_main.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            // 스피너 선택시 발생하는 부분
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.BLACK);        // 스피너 색상 지정
                //((TextView)adapterView.getChildAt(0)).setTextSize(20);

                // 선택한 스피너 값 저장
                main_text = (String)spinner_main.getSelectedItem();
                //Toast.makeText(getApplicationContext(), main_text, Toast.LENGTH_SHORT).show();


                // 스피너 선택 값에 따라 화면이 변하는 모습 if문을 통해 제어 하며 각 순서별로 보이는 효과, 안보이는 효과 설정
                // 소분류 스피너
                // Basic 내용
                // Volatility 2 내용
                // Comming soon 내용
                if(main_text.equals("Basic")) {
                    spinner_sub.setVisibility(spinner_sub.GONE);

                    f_basic.setVisibility(LinearLayout.VISIBLE);
                    vol_2.setVisibility(LinearLayout.INVISIBLE);
                    comming_soon.setVisibility(LinearLayout.GONE);

                }

                if(main_text.equals("Memory")) {
                    spinner_sub.setVisibility(spinner_sub.VISIBLE);

                    f_basic.setVisibility(LinearLayout.INVISIBLE);
                    vol_2.setVisibility(LinearLayout.VISIBLE);
                    comming_soon.setVisibility(LinearLayout.GONE);
                    // Toast.makeText(getApplicationContext(), "Check", Toast.LENGTH_LONG).show();
                    //System.exit(0);

                }

                if(main_text.equals("Network")) {
                    spinner_sub.setVisibility(spinner_sub.GONE);


                    f_basic.setVisibility(LinearLayout.INVISIBLE);
                    vol_2.setVisibility(LinearLayout.INVISIBLE);
                    comming_soon.setVisibility(LinearLayout.VISIBLE);
                }

                if(main_text.equals("Disk")) {
                    spinner_sub.setVisibility(spinner_sub.GONE);

                    f_basic.setVisibility(LinearLayout.INVISIBLE);
                    vol_2.setVisibility(LinearLayout.INVISIBLE);
                    comming_soon.setVisibility(LinearLayout.VISIBLE);
                }
            } // public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        }); // spinner_main.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


        // 소분류 스피너 이벤트
        spinner_sub.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            // 스피너 선택시 발생하는 부분
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.BLACK);        // 스피너 색상 지정
                //((TextView)adapterView.getChildAt(0)).setTextSize(20);

                // 선택한 스피너 값 저장
                sub_text = (String)spinner_sub.getSelectedItem();
                // Toast.makeText(getApplicationContext(), sub_text, Toast.LENGTH_SHORT).show();


                // 스피너 선택 값에 따라 화면이 변하는 모습 if문을 통해 제어 하며 각 순서별로 보이는 효과, 안보이는 효과 설정
                // Basic 내용
                // Volatility 2 내용
                // Comming soon 내용
                if(sub_text.equals("Volatility 2")){

                    f_basic.setVisibility(LinearLayout.INVISIBLE);
                    vol_2.setVisibility(LinearLayout.VISIBLE);
                    comming_soon.setVisibility(LinearLayout.GONE);
                }

                if(sub_text.equals("Volatility 3")){

                    f_basic.setVisibility(LinearLayout.INVISIBLE);
                    vol_2.setVisibility(LinearLayout.INVISIBLE);
                    comming_soon.setVisibility(LinearLayout.VISIBLE);
                }
            } // public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        }); // spinner_sub.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


    } // protected void onCreate(Bundle savedInstanceState) {


} // public class Forensics extends AppCompatActivity {
