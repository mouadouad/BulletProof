package com.example.mouad.bulletproof;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class finish extends AppCompatActivity {

    public final static String score_key = "com.mouad0.hp.myapplication.score_key";
    public final static String SHAR="shared prefs";
    public final static String Bscore ="int";
    public static final String Coins="coins";
    int width, height;

    String coins;
    TextView BscoreTv, scoreTv;
    Button play_again;
    int coins_gained;
    private int saved_Bscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        Point size= new Point() ;
        display.getSize(size);
        width = size.x;
        height = size.y;

        //GET CURRENT SCORE
        int score;
        String Sscore;
        Intent i=getIntent();
        Sscore = i.getStringExtra(score_key);
        score=Integer.parseInt(Sscore);

        //SET THE LAYOUT BACKGROUND
        background();

        //BACK BUTTON
        back_button();

        //SET THE TEXT VIEWS AND PLAY AGAIN BUTTON ON A CONTAINER
        BscoreTv = new TextView(this );
        scoreTv = new TextView(this );
        play_again = new Button(this );
        set_layout();

        //GET SAVED SCORE AND COINS
        Load();

        //ADD NEW COINS TO OLD COINS
        //coins_gained = score/10;
        coins_gained= 500;
        coins = String.valueOf(Integer.parseInt(coins)+coins_gained);

        //IF NEW BEST SCORE
        if (score>saved_Bscore) {
            saved_Bscore=score;
        }

        //SAVE DATA
        saveData();

        //SHOW SCORE
        scoreTv.append("\n"+Sscore);

        //SHOW BEST SCORE
        BscoreTv.append(String.valueOf(saved_Bscore));

        //SHOW COINS BAR
        coins_bar();
        banner();


    }

    public void set_layout(){
        RelativeLayout Container= new RelativeLayout(this );
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(setx(1000),sety(1080));
        addContentView(Container,layoutParams);
        Container.setBackgroundResource(R.drawable.coins_container);
        Container.setY(sety(250));
        Container.setX(setx(40));


        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(setx(1000),RelativeLayout.LayoutParams.WRAP_CONTENT);
        Container.addView(BscoreTv,layoutParams1);
        BscoreTv.setY(sety(800));
        BscoreTv.setText(R.string.best);
        BscoreTv.setTextSize(sety(30));
        BscoreTv.setGravity(Gravity.CENTER);
        BscoreTv.setTextColor(Color.WHITE);

        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(setx(1000),RelativeLayout.LayoutParams.WRAP_CONTENT);
        Container.addView(scoreTv,layoutParams2);
        scoreTv.setY(sety(80));
        scoreTv.setText(R.string.SCORE);
        scoreTv.setTextSize(sety(30));
        scoreTv.setGravity(Gravity.CENTER);
        scoreTv.setTextColor(Color.LTGRAY);

        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(setx(300),sety(200));
        Container.addView(play_again,layoutParams3);
        play_again.setBackgroundResource(R.drawable.play_again);
        play_again.setY(sety(450));
        play_again.setX(setx(350));

        play_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(finish.this, main_game.class);
                startActivity(i);
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

        //SHOW COINS BAR
        RelativeLayout coinsContainer= new RelativeLayout(this );
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(setx(300),sety(150));
        addContentView(coinsContainer,layoutParams);
        coinsContainer.setBackgroundResource(R.drawable.coins_container);
        coinsContainer.setY(sety(50));
        coinsContainer.setX(setx(700));


        ImageView coinsIv= new ImageView(this);
        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(setx(80),sety(150));
        coinsContainer.addView(coinsIv,layoutParams1);
        coinsIv.setBackgroundResource(R.drawable.coins);
        coinsIv.setX(setx(200));

        TextView coinsTv = new TextView(this );
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(setx(200),sety(150));
        coinsContainer.addView(coinsTv,layoutParams2);
        coinsTv.setText(coins);
        coinsTv.setGravity(Gravity.CENTER);
        coinsTv.setTextColor(Color.WHITE);
        assert coins != null;
        coinsTv.setTextSize(sety(20-coins.length()));

        TextView added_coins = new TextView(this );
        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,sety(150));
        addContentView(added_coins,layoutParams3);
        added_coins.setY(sety(50));
        added_coins.setX(setx(600-String.valueOf(added_coins).length()));
        added_coins.setGravity(Gravity.CENTER);
        added_coins.setTextColor(Color.YELLOW);
        added_coins.setText("+");
        added_coins.append(String.valueOf(coins_gained));
        added_coins.setTextSize(setx(20));



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

                Intent intent=new Intent(finish.this,start.class);
                startActivity(intent);
            }
        });

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


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent=new Intent(finish.this,start.class);
        startActivity(intent);

    }

    public void Load(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHAR,MODE_PRIVATE);
        //GET THE CURRENT COINS AND BEST SCORE
        saved_Bscore = sharedPreferences.getInt(Bscore,0);
        coins =sharedPreferences.getString(Coins, "0");

    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHAR,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        editor.putInt(Bscore,saved_Bscore);
        editor.putString(Coins,coins);

        editor.apply();
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
