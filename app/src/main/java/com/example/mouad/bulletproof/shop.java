package com.example.mouad.bulletproof;



import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.Arrays;

public class shop extends AppCompatActivity {

    public final static String SHAR="shared prefs";
    public static final String Coins="coins";
    int width, height;
    String coins;
    InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        Point size= new Point() ;
        display.getSize(size);
        width = size.x;
        height = size.y;

        //ADD INTERSTITIAL
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        //SET BACKGROUND
        background();

        //BACK BUTON
        back_button();

        //GET THE CURRENT COINS
        SharedPreferences sharedPreferences= getSharedPreferences(SHAR,MODE_PRIVATE);
        coins =sharedPreferences.getString(Coins, "0");

        //SHOW COINS BAR
        coins_bar();

        ListView list = new ListView(this );
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,sety(1770-250)-getStatusBarHeight());
        addContentView(list,layoutParams);
        list.setY(sety(250));



        ArrayList<Integer> images= new ArrayList<>(Arrays.asList(R.drawable.classic,R.drawable.red,R.drawable.blue
                                                                ,R.drawable.black,R.drawable.white,R.drawable.grey,
                                                                 R.drawable.gold,R.drawable.diamond,R.drawable.jade,
                                                                 R.drawable.black_rifle,R.drawable.white_rifle,R.drawable.grey_rifle,
                                                                 R.drawable.gold_rifle,R.drawable.diamond_rifle,R.drawable.jade_rifle));
        int items=15;


        final shop_adapter adapter=new shop_adapter(this,items,images,sharedPreferences,width,height);
        list.setAdapter(adapter);



    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public void coins_bar(){
        //GET THE CURRENT COINS
        SharedPreferences sharedPreferences= getSharedPreferences(SHAR,MODE_PRIVATE);
        coins =sharedPreferences.getString(Coins, "0");
        //SHOW COINS BAR
        RelativeLayout coinsContainer= new RelativeLayout(this );
        RelativeLayout.LayoutParams layoutParams5 = new RelativeLayout.LayoutParams(setx(300),sety(150));
        addContentView(coinsContainer,layoutParams5);
        coinsContainer.setBackgroundResource(R.drawable.coins_container);
        coinsContainer.setY(sety(50));
        coinsContainer.setX(setx(700));

        ImageView coinsIv= new ImageView(this);
        RelativeLayout.LayoutParams layoutParams6 = new RelativeLayout.LayoutParams(setx(80),sety(150));
        coinsContainer.addView(coinsIv,layoutParams6);
        coinsIv.setBackgroundResource(R.drawable.coins);
        coinsIv.setX(setx(200));

        TextView coinsTv = new TextView(this );
        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(setx(200),sety(150));
        coinsContainer.addView(coinsTv,layoutParams1);
        coinsTv.setText(coins);
        coinsTv.setGravity(Gravity.CENTER);
        coinsTv.setTextColor(Color.WHITE);
        assert coins != null;
        coinsTv.setTextSize(sety(20-coins.length()));
    }

    public void back_button(){

        Button back=new Button(this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(setx(100),sety(100));
        back.setBackgroundResource(R.drawable.back_button);
        addContentView(back,layoutParams);
        back.setY(sety(50));
        back.setX(setx(50));


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(shop.this,start.class);
                startActivity(intent);
                mInterstitialAd.show();
            }
        });

    }

    public void background(){
        //SET THE LAYOUT BACKGROUND
        RelativeLayout layout = new RelativeLayout(this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        addContentView(layout,layoutParams);
        int back_color= Color.parseColor("#a3c6c8");
        layout.setBackgroundColor(back_color);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


        Intent intent=new Intent(shop.this,start.class);
        startActivity(intent);
        mInterstitialAd.show();
    }

    public int setx(int x){
        int i;

        i=(x*width)/1080;

        return i;
    }

    public int sety(int x){
        int i;

        i=(x*height)/1770;

        return i;
    }
}
