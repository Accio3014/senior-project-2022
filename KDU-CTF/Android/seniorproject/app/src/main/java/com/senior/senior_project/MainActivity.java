package com.senior.senior_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    // 화면의 가로, 세로 값을 다른 엑티비티에 넘겨주기 위해 선언
    // public static Context context_main;

    // 바트 맵 및 아래 변수들은 초기 테스트를 위한 변수
    Bitmap forensicsButton;
    int forensicsButtonX, forensicsButtonY;

    Bitmap webButton;
    int webButtonX, webButtonY;


    Bitmap crytoButton;
    int crytoButtonX, crytoButtonY;

    int buttonWidth, buttonHeight;

    // screen size
    int WIDTH = 0;
    int HEIGHT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));
    }

    // 화면은 구성하는 부분
    class MyView extends View {

        // 화면의 초기 설정 부분
        MyView(Context context) {
            super(context);
            setBackgroundColor(Color.WHITE);

            // 화면읜 가로, 세로를 크기 호출
            Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            WIDTH = display.getWidth();
            HEIGHT = display.getHeight();

            buttonWidth = WIDTH * 2 / 3;
            buttonHeight = HEIGHT * 1 / 9;

            forensicsButton = BitmapFactory.decodeResource(getResources(), R.drawable.forensics);
            forensicsButtonX = buttonWidth / 4;
            forensicsButtonY = HEIGHT / 9;
            forensicsButton = Bitmap.createScaledBitmap(forensicsButton, buttonWidth, buttonHeight, true);

            webButton = BitmapFactory.decodeResource(getResources(), R.drawable.web);
            webButtonX = buttonWidth / 4;
            webButtonY = HEIGHT * 4 / 9;
            webButton = Bitmap.createScaledBitmap(webButton, buttonWidth, buttonHeight, true);

            crytoButton = BitmapFactory.decodeResource(getResources(), R.drawable.crypto);
            crytoButtonX = buttonWidth / 4;
            crytoButtonY = HEIGHT * 7 / 9;
            crytoButton = Bitmap.createScaledBitmap(crytoButton, buttonWidth, buttonHeight, true);

        } // MyView(Context context) {

        // 실제 화면을 그리는 부분
        @Override
        protected void onDraw(Canvas canvas) {

            Paint paint = new Paint();

            canvas.drawBitmap(forensicsButton, forensicsButtonX, forensicsButtonY, paint);
            canvas.drawBitmap(webButton, webButtonX, webButtonY, paint);
            canvas.drawBitmap(crytoButton, crytoButtonX, crytoButtonY, paint);

        } // protected void onDraw(Canvas canvas) {

        // 화면의 터치 이벤트 부분
        @Override
        public boolean onTouchEvent(MotionEvent event) {

            int x = 0;
            int y = 0;

            /*
            if(event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE){
                x = (int) event.getX();
                y = (int) event.getY();
            }
            */

            if(event.getAction() == MotionEvent.ACTION_DOWN){
                x = (int) event.getX();
                y = (int) event.getY();
            }

            if((x > forensicsButtonX) && (x < forensicsButtonX + buttonWidth) && (y > forensicsButtonY) && (y < forensicsButtonY + buttonHeight)) {

                Intent intentForensics = new Intent(MainActivity.this, Forensics.class);
                startActivity(intentForensics);
                //finish();

                //Toast.makeText(MainActivity.this, "Choice touch test", Toast.LENGTH_SHORT).show();
            }

            if((x > webButtonX) && (x < webButtonX + buttonWidth) && (y > webButtonY) && (y < webButtonY + buttonHeight)) {

                Intent intentWeb = new Intent(MainActivity.this, Web.class);
                startActivity(intentWeb);
//                finish();

                //Toast.makeText(MainActivity.this, "Blank touch test", Toast.LENGTH_SHORT).show();
            }

            if((x > crytoButtonX) && (x < crytoButtonX + buttonWidth) && (y > crytoButtonY) && (y < crytoButtonY + buttonHeight)) {

                Intent intentCrypto = new Intent(MainActivity.this, Crypto.class);
                startActivity(intentCrypto);
//                finish();

                //Toast.makeText(MainActivity.this, "Db touch test", Toast.LENGTH_SHORT).show();
            }

            return true;

        } // public boolean onTouchEvent(MotionEvent event) {

    } // class MyView extends View {
} // public class MainActivity extends AppCompatActivity {