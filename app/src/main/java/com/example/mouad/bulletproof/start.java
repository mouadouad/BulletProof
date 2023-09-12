package com.example.mouad.bulletproof;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;


public class start extends AppCompatActivity {

    Button start,shop,sound;
    int width, height;
    public final static String SHAR="shared prefs";
    public final static String sound_SHAREDPREFS="sound_SHAREDPREFS";
    public static final String Coins="coins";
    SharedPreferences sharedPreferences;
    boolean sound_boolean;
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

        //GET PREVIOUS SOUND BOOLEAN
        sharedPreferences= getSharedPreferences(SHAR,MODE_PRIVATE);
        sound_boolean= sharedPreferences.getBoolean(sound_SHAREDPREFS,true);

        //SET THE LAYOUT BACKGROUND
        background();

        //SHOW COINS BAR
        coins_bar();

        //SET THE BUTTONS
        start = new Button(this );
        shop = new Button(this );
        sound= new Button(this);
        make_buttons();

        final Animation from_top= AnimationUtils.loadAnimation(this,R.anim.from_top);
        final Animation from_bottom= AnimationUtils.loadAnimation(this,R.anim.from_bottom);
        shop.setAnimation(from_top);

        start.setVisibility(View.GONE);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                start.setVisibility(View.VISIBLE);
                start.setAnimation(from_bottom);
            }
        }, 800);

        banner();



    }

    public void make_buttons(){
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(setx(300),sety(200));
        addContentView(start,layoutParams2);
        start.setY(sety(700));
        start.setX(setx(400));
        start.setBackgroundResource(R.drawable.start);

        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(setx(300),sety(200));
        addContentView(shop,layoutParams3);
        shop.setY(sety(1000));
        shop.setX(setx(400));
        shop.setBackgroundResource(R.drawable.shop);

        RelativeLayout.LayoutParams layoutParams4 = new RelativeLayout.LayoutParams(setx(150),sety(150));
        addContentView(sound,layoutParams4);
        sound.setY(sety(50));
        sound.setX(setx(100));

        //CHOOSE ICON
        if (!sound_boolean){
            sound.setBackgroundResource(R.drawable.no_sound);
        }else {
            sound.setBackgroundResource(R.drawable.sound);
        }
        sound_click();


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(start.this, main_game.class);
                startActivity(i);
            }
        });

        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(start.this, shop.class);
                startActivity(intent);
                mInterstitialAd.show();
            }
        });
    }

    public void sound_click(){

        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //INVERSE SOUND BOOLEAN


                //INVERSE ICON
                if (sound_boolean){
                    sound.setBackgroundResource(R.drawable.no_sound);
                    sound_boolean=false;
                }else {
                    sound.setBackgroundResource(R.drawable.sound);
                    sound_boolean=true;
                }

                //SAVE
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putBoolean(sound_SHAREDPREFS,sound_boolean);
                editor.apply();

            }
        });
    }

    public void background(){
        //SET THE LAYOUT BACKGROUND
        RelativeLayout layout = new RelativeLayout(this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        addContentView(layout,layoutParams);
        layout.setBackground(ContextCompat.getDrawable(this, R.drawable.background));
    }

    public void coins_bar(){
        //GET THE CURRENT COINS
        SharedPreferences sharedPreferences= getSharedPreferences(SHAR,MODE_PRIVATE);
        String coins =sharedPreferences.getString(Coins, "0");

        //SHOW COINS BAR
        final RelativeLayout coinsContainer= new RelativeLayout(this );
        final RelativeLayout.LayoutParams layoutParams5 = new RelativeLayout.LayoutParams(setx(300),sety(150));

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


        final Animation fade_in= AnimationUtils.loadAnimation(this,R.anim.fade_in);

         coinsContainer.setAnimation(fade_in);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                addContentView(coinsContainer,layoutParams5);
                coinsContainer.setAnimation(fade_in);
            }
        }, 2400);

    }

    public void banner(){
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-3922358669029120/2831354657");

        RelativeLayout layout=new RelativeLayout(this);
        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams((int) width, (int) height-getStatusBarHeight());
        addContentView(layout,layoutParams1);


        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layout.addView(adView,layoutParams);

        MobileAds.initialize(this,"ca-app-pub-3922358669029120~3985187056");
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);


    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
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
