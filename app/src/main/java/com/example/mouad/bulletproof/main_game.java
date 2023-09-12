package com.example.mouad.bulletproof;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class main_game extends AppCompatActivity {


    public final static String score_key = "com.mouad0.hp.myapplication.score_key";
    public final static String SHAR="shared prefs";
    public static final String selected="selected";
    public final static String sound_SHAREDPREFS="sound_SHAREDPREFS";

    Boolean still_playing=true;
    Button perso1,perso2;
    int width,height,pers1=0,pers2=0,score=-1;
    int p,m,a,b,c,d,e,f,travel=1000,nb1;
    ImageView b1,b2,b3,b4,b5,b6;
    TextView tv;
    String Sscore;
    float y,y2,x;
    ArrayList<int[]> skins= new ArrayList<>();
    int skin,G_skin;
    Boolean sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Display display = getWindowManager().getDefaultDisplay();
        Point size= new Point() ;
        display.getSize(size);
        width = size.x;
        height = size.y;


        //MAKE IMAGEVIEWS AND BUTTONS
        perso1= new Button(this);
        perso2= new Button(this);
        b1=new ImageView(this);
        b2=new ImageView(this);
        b3=new ImageView(this);
        b4=new ImageView(this);
        b5=new ImageView(this);
        b6=new ImageView(this);
        tv=findViewById(R.id.tv1);

        //BACK BUTTON
        back_button();

        //MAKE BULLETS
        make_bullets();

        //SKINS LIST
        skins_list();
        //FIND SKIN
        SharedPreferences sharedPreferences= getSharedPreferences(SHAR,MODE_PRIVATE);
        int selected_image = sharedPreferences.getInt(selected,0);
        skin=skins.get(selected_image)[0];
        G_skin=skins.get(selected_image)[1];

        //MAKE PERSO BUTTONS
        make_buttons();

        // SEE WHICH PERSO WILL BE GLOWING FIRST
        Random rand = new Random();
        nb1=rand.nextInt(2);
        if (nb1==0){
            perso1.setBackgroundResource(G_skin);
            pers1=1;
        }else {

            perso2.setBackgroundResource(G_skin);
            pers2=1;
        }


        //GET SOUND BOOLEAN
        sound= sharedPreferences.getBoolean(sound_SHAREDPREFS,true);

        //START GAME
        RAND();

    }

    public void skins_list(){
        //SKINS LIST
        int[] classic= new int[2];
        classic[0]=R.drawable.classic;
        classic[1]=R.drawable.classic_g;
        skins.add(classic);
        int[] red= new int[2];
        red[0]=R.drawable.red;
        red[1]=R.drawable.red_g;
        skins.add(red);
        int[] blue= new int[2];
        blue[0]=R.drawable.blue;
        blue[1]=R.drawable.blue_g;
        skins.add(blue);
        int[] black= new int[2];
        black[0]=R.drawable.black;
        black[1]=R.drawable.black_g;
        skins.add(black);
        int[] white= new int[2];
        white[0]=R.drawable.white;
        white[1]=R.drawable.white_g;
        skins.add(white);
        int[] grey= new int[2];
        grey[0]=R.drawable.grey;
        grey[1]=R.drawable.grey_g;
        skins.add(grey);
        int[] gold= new int[2];
        gold[0]=R.drawable.gold;
        gold[1]=R.drawable.gold_g;
        skins.add(gold);
        int[] diamond= new int[2];
        diamond[0]=R.drawable.diamond;
        diamond[1]=R.drawable.diamond_g;
        skins.add(diamond);
        int[] jade= new int[2];
        jade[0]=R.drawable.jade;
        jade[1]=R.drawable.jade_g;
        skins.add(jade);
        int[] black_rifle= new int[2];
        black_rifle[0]=R.drawable.black_rifle;
        black_rifle[1]=R.drawable.black_rifle_g;
        skins.add(black_rifle);
        int[] white_rifle= new int[2];
        white_rifle[0]=R.drawable.white_rifle;
        white_rifle[1]=R.drawable.white_rifle_g;
        skins.add(white_rifle);
        int[] grey_rifle= new int[2];
        grey_rifle[0]=R.drawable.grey_rifle;
        grey_rifle[1]=R.drawable.grey_rifle_g;
        skins.add(grey_rifle);
        int[] gold_rifle= new int[2];
        gold_rifle[0]=R.drawable.gold_rifle;
        gold_rifle[1]=R.drawable.gold_rifle_g;
        skins.add(gold_rifle);
        int[] diamond_rifle= new int[2];
        diamond_rifle[0]=R.drawable.diamond_rifle;
        diamond_rifle[1]=R.drawable.diamond_rifle_g;
        skins.add(diamond_rifle);
        int[] jade_rifle= new int[2];
        jade_rifle[0]=R.drawable.jade_rifle;
        jade_rifle[1]=R.drawable.jade_rifle_g;
        skins.add(jade_rifle);


    }

    public void make_buttons(){
        //MAKE PERSO BUTTONS
        RelativeLayout.LayoutParams layoutParams4 = new RelativeLayout.LayoutParams(setx(180),sety(420));
        addContentView(perso1,layoutParams4);
        perso1.setBackgroundResource(skin);
        perso1.setY(sety(1100));
        perso1.setX(0);


        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(setx(180),sety(420));
        addContentView(perso2,layoutParams);
        perso2.setBackgroundResource(skin);
        perso2.setY(sety(1100));
        perso2.setX(0);

        perso1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                perso1.setBackgroundResource(G_skin);
                perso2.setBackgroundResource(skin);

                pers1=1;pers2=0;
            }
        });
        perso2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                perso2.setBackgroundResource(G_skin);
                perso1.setBackgroundResource(skin);

                pers1=0;pers2=1;


            }
        });

    }

    public void make_bullets(){
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(setx(175),sety(262));
        addContentView(b1,layoutParams);
        b1.setBackgroundResource(R.drawable.bullet);
        addContentView(b2,layoutParams);
        b2.setBackgroundResource(R.drawable.bullet);
        addContentView(b3,layoutParams);
        b3.setBackgroundResource(R.drawable.bullet);
        addContentView(b4,layoutParams);
        b4.setBackgroundResource(R.drawable.bullet);
        addContentView(b5,layoutParams);
        b5.setBackgroundResource(R.drawable.bullet);
        addContentView(b6,layoutParams);
        b6.setBackgroundResource(R.drawable.bullet);

    }

    public void RAND(){

        y=sety(1100);
        y2=sety(210);
        x=setx(90);

        // SET THE 6 POSTIONS
        p=width/6;
        m=p/2;
        a= (int) (m-x); b= (int) (m+p-x); c= (int) (2*p+m-x); d= (int) (3*p+m-x); e= (int) (4*p+m-x); f= (int) (5*p+m-x);

        //FIND THE STARTING POSITIONS
        if (score==-1){
        Random rand1 = new Random();
        switch (rand1.nextInt(3)){

            case 0:
                PERSO1_AT_a();
                break;
            case 1:
                PERSO1_AT_b();
                break;
            case 2:
                PERSO1_AT_c();
                break;

        }

        Random rand2 = new Random();

        switch (rand2.nextInt(3)){

            case 0:
                PERSO2_AT_d();
                break;
            case 1:
                PERSO2_AT_e();
                break;
            case 2:
                PERSO2_AT_f();
                break;

        }}

        // UPDATE SCORES
        score++; Sscore=String.valueOf(score); tv.setText(Sscore);

        //DECREASE THE TIME OF TRAVELLING BY SCORE (INCREASE SPEED)
        travel=1000-score-(score/500)*((score-500)/2)+(score/500)*(score-500);

        //SEE WHAT SEC TO PLAY
        Random rand3 = new Random();
        switch (rand3.nextInt(36)){

            case 0:
                P_sec1();
                break;
            case 1:
                P_sec2();
                break;
            case 2:
                P_sec3();
                break;
            case 3:
                P_sec4();
                break;
            case 4:
                P_sec5();
                break;
            case 5:
                P_sec6();
                break;
            case 6:
                P_sec7();
                break;
            case 7:
                P_sec8();
                break;
            case 8:
                P_sec9();
                break;
            case 9:
                P_sec10();
                break;
            case 10:
                P_sec11();
                break;
            case 11:
                P_sec12();
                break;
            case 12:
                P_sec13();
                break;
            case 13:
                P_sec14();
                break;
            case 14:
                P_sec15();
                break;
            case 15:
                P_sec16();
                break;
            case 16:
                P_sec17();
                break;
            case 17:
                P_sec18();
                break;
            case 18:
                P_sec19();
                break;
            case 19:
                P_sec20();
                break;
            case 20:
                P_sec21();
                break;
            case 21:
                P_sec22();
                break;
            case 22:
                P_sec23();
                break;
            case 23:
                P_sec24();
                break;
            case 24:
                P_sec25();
                break;
            case 25:
                P_sec26();
                break;
            case 26:
                P_sec27();
                break;
            case 27:
                P_sec28();
                break;
            case 28:
                P_sec29();
                break;
            case 29:
                P_sec30();
                break;
            case 30:
                P_sec31();
                break;
            case 31:
                P_sec32();
                break;
            case 32:
                P_sec33();
                break;
            case 33:
                P_sec34();
                break;
            case 34:
                P_sec35();
                break;
            case 35:
                P_sec36();
                break;

        }

        //SOUND
        if (sound&&still_playing) {

            //IMPACT SOUND
            final float travel=this.travel;

            final float playbackSpeed=(1/travel)*1f;
            final SoundPool soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);

            final int soundId = soundPool.load(this, R.raw.impact,1);
            AudioManager mgr = (AudioManager) getSystemService(AUDIO_SERVICE);
            final float volume = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

            soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener()
            {
                @Override
                public void onLoadComplete(SoundPool arg0, int arg1, int arg2)
                {
                            soundPool.play(soundId, volume, volume, 1, 0, playbackSpeed);


                }
            });


        }

    }

    public void a(){

        //MAKE PERSO1 RED WHEN HE IS GETTING THE BULLET
        perso1.setBackgroundResource(R.drawable.dead);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                perso1.setBackgroundResource(skin);

            }
        },travel/5);

    }

    public void b(){

        //MAKE PERSO2 RED WHEN HE IS GETTING THE BULLET
        perso2.setBackgroundResource(R.drawable.dead);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                perso2.setBackgroundResource(skin);

            }
        },travel/5);

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
                still_playing=false;
                Intent intent=new Intent(main_game.this,start.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        still_playing=false;
        Intent intent=new Intent(main_game.this,start.class);
        startActivity(intent);
        finish();

    }

    public void P_sec1(){

        B1_1();B2_1();B3_D_1();B4_1();B5_D_1();B6_1();


        PERSO1_c();PERSO2_f();B1_2();B2_2();B3_D_2();B4_2();B5_D_2();B6_2();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                PERSO1_b();PERSO2_e();


            }
        },travel);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {



                if (pers2==1){

                    lost();
                }else {
                    b();

                    RAND();
                }

            }
        },2*travel);



    }

    public void P_sec2(){



        B1_1();B2_1();B3_D_1();B4_D_1();B5_1();B6_1();


        PERSO1_c();PERSO2_d();B1_2();B2_2();B3_D_2();B4_D_2();B5_2();B6_2();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                PERSO1_b();PERSO2_d();


            }
        },travel);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {



                if (pers2==1){

                    lost();
                }else{
                    b();

                    RAND();

                }



            }
        },2*travel);



    }

    public void P_sec3(){




        B1_1();B2_1();B3_D_1();B4_1();B5_1();B6_D_1();


        PERSO1_b();PERSO2_f();B1_2();B2_2();B3_D_2();B4_2();B5_2();B6_D_2();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                PERSO1_c();PERSO2_d();


            }
        },travel);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {



                if (pers1==1){

                    lost();
                }else {
                    a();

                    RAND();
                }

            }
        },2*travel);



    }

    public void P_sec4(){


        B1_D_1();B2_1();B3_1();B4_D_1();B5_1();B6_1();


        PERSO1_c();PERSO2_f();B1_D_2();B2_2();B3_2();B4_D_2();B5_2();B6_2();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                PERSO1_a();PERSO2_e();


            }
        },travel);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {



                if (pers1==1){

                    lost();
                }else{
                    a();

                    RAND();

                }



            }
        },2*travel);



    }

    public void P_sec5(){




        B1_D_1();B2_1();B3_1();B4_1();B5_D_1();B6_1();


        PERSO1_a();PERSO2_f();B1_D_2();B2_2();B3_2();B4_2();B5_D_2();B6_2();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                PERSO1_b();PERSO2_d();


            }
        },travel);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                RAND();

            }
        },2*travel);



    }

    public void P_sec6(){



        B1_D_1();B2_1();B3_1();B4_1();B5_1();B6_D_1();


        PERSO1_c();PERSO2_e();B1_D_2();B2_2();B3_2();B4_2();B5_2();B6_D_2();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                PERSO1_a();PERSO2_d();


            }
        },travel);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {



                if (pers1==1){

                    lost();
                }else{
                    a();

                    RAND();

                }



            }
        },2*travel);



    }

    public void P_sec7(){




        B1_1();B2_D_1();B3_1();B4_D_1();B5_1();B6_1();


        PERSO1_c();PERSO2_e();B1_2();B2_D_2();B3_2();B4_D_2();B5_2();B6_2();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                PERSO1_b();PERSO2_f();


            }
        },travel);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {



                if (pers1==1){

                    lost();
                }else {

                    a();

                    RAND();
                }

            }
        },2*travel);



    }

    public void P_sec8(){


        B1_1();B2_D_1();B3_1();B4_1();B5_D_1();B6_1();


        PERSO1_c();PERSO2_e();B1_2();B2_D_2();B3_2();B4_2();B5_D_2();B6_2();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                PERSO1_a();PERSO2_e();


            }
        },travel);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {



                if (pers2==1){

                    lost();
                }else{

                    b();

                    RAND();

                }



            }
        },2*travel);



    }

    public void P_sec9(){




        B1_1();B2_D_1();B3_1();B4_1();B5_1();B6_D_1();


        PERSO1_c();PERSO2_d();B1_2();B2_D_2();B3_2();B4_2();B5_2();B6_D_2();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                PERSO1_a();PERSO2_f();


            }
        },travel);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {



                if (pers2==1){

                    lost();
                }else {

                    b();

                    RAND();
                }

            }
        },2*travel);



    }

    public void P_sec10(){




        B1_D_1();B2_1();B3_1();B4_D_1();B5_D_1();B6_1();


        PERSO1_c();PERSO2_f();B1_D_2();B2_2();B3_2();B4_D_2();B5_D_2();B6_2();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                PERSO1_b();PERSO2_e();


            }
        },travel);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {



                if (pers2==1){

                    lost();
                }else {

                    b();

                    RAND();
                }

            }
        },2*travel);



    }

    public void P_sec11(){



        B1_D_1();B2_1();B3_1();B4_D_1();B5_1();B6_D_1();


        PERSO1_a();PERSO2_f();B1_D_2();B2_2();B3_2();B4_D_2();B5_2();B6_D_2();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                PERSO1_a();PERSO2_e();


            }
        },travel);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {



                if (pers1==1){

                    lost();
                }else{

                    a();

                    RAND();

                }



            }
        },2*travel);



    }

    public void P_sec12(){




        B1_D_1();B2_1();B3_1();B4_1();B5_D_1();B6_D_1();


        PERSO1_a();PERSO2_f();B1_D_2();B2_2();B3_2();B4_2();B5_D_2();B6_D_2();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                PERSO1_c();PERSO2_d();


            }
        },travel);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                RAND();


            }
        },2*travel);



    }

    public void P_sec13(){


        B1_1();B2_D_1();B3_1();B4_D_1();B5_D_1();B6_1();


        PERSO1_b();PERSO2_f();B1_2();B2_D_2();B3_2();B4_D_2();B5_D_2();B6_2();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                PERSO1_c();PERSO2_e();


            }
        },travel);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {



                if (pers2==1){

                    lost();
                }else{

                    b();

                    RAND();

                }



            }
        },2*travel);



    }

    public void P_sec14(){




        B1_1();B2_D_1();B3_1();B4_D_1();B5_1();B6_D_1();


        PERSO1_a();PERSO2_d();B1_2();B2_D_2();B3_2();B4_D_2();B5_2();B6_D_2();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                PERSO1_b();PERSO2_e();


            }
        },travel);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                if (pers1==1){

                    lost();
                }else{

                    a();

                    RAND();

                }


            }
        },2*travel);



    }

    public void P_sec15(){


        B1_1();B2_D_1();B3_1();B4_1();B5_D_1();B6_D_1();


        PERSO1_c();PERSO2_e();B1_2();B2_D_2();B3_2();B4_2();B5_D_2();B6_D_2();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                PERSO1_a();PERSO2_f();


            }
        },travel);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {



                if (pers2==1){

                    lost();
                }else{

                    b();

                    RAND();

                }



            }
        },2*travel);



    }

    public void P_sec16(){




        B1_1();B2_1();B3_D_1();B4_D_1();B5_D_1();B6_1();


        PERSO1_c();PERSO2_d();B1_2();B2_2();B3_D_2();B4_D_2();B5_D_2();B6_2();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                PERSO1_c();PERSO2_f();


            }
        },travel);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {



                if (pers1==1){

                    lost();
                }else {

                    a();

                    RAND();
                }

            }
        },2*travel);



    }

    public void P_sec17(){


        B1_1();B2_1();B3_D_1();B4_D_1();B5_1();B6_D_1();


        PERSO1_c();PERSO2_d();B1_2();B2_2();B3_D_2();B4_D_2();B5_2();B6_D_2();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                PERSO1_a();PERSO2_f();


            }
        },travel);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {



                if (pers2==1){

                    lost();
                }else{

                    b();

                    RAND();

                }



            }
        },2*travel);



    }

    public void P_sec18(){

        B1_1();B2_1();B3_D_1();B4_1();B5_D_1();B6_D_1();


        PERSO1_c();PERSO2_d();B1_2();B2_2();B3_D_2();B4_2();B5_D_2();B6_D_2();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                PERSO1_c();PERSO2_d();


            }
        },travel);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {



                if (pers1==1){

                    lost();
                }else {

                    a();

                    RAND();
                }

            }
        },2*travel);



    }

    public void P_sec19(){


        B1_D_1();B2_D_1();B3_1();B4_D_1();B5_1();B6_1();

        PERSO1_c();PERSO2_f();B1_D_2();B2_D_2();B3_2();B4_D_2();B5_2();B6_2();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                PERSO1_c();PERSO2_d();


            }
        },travel);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {



                if (pers2==1){

                    lost();
                }else {

                    b();

                    RAND();
                }

            }
        },2*travel);



    }

    public void P_sec20(){




        B1_D_1();B2_D_1();B3_1();B4_1();B5_D_1();B6_1();


        PERSO1_c();PERSO2_f();B1_D_2();B2_D_2();B3_2();B4_2();B5_D_2();B6_2();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                PERSO1_b();PERSO2_d();


            }
        },travel);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {



                if (pers1==1){

                    lost();
                }else {

                    a();

                    RAND();
                }

            }
        },2*travel);



    }

    public void P_sec21(){




        B1_D_1();B2_D_1();B3_1();B4_1();B5_1();B6_D_1();


        PERSO1_b();PERSO2_e();B1_D_2();B2_D_2();B3_2();B4_2();B5_2();B6_D_2();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                PERSO1_a();PERSO2_d();


            }
        },travel);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {



                if (pers1==1){

                    lost();
                }else {

                    a();

                    RAND();
                }

            }
        },2*travel);



    }

    public void P_sec22(){




        B1_D_1();B2_1();B3_D_1();B4_D_1();B5_1();B6_1();


        PERSO1_a();PERSO2_f();B1_D_2();B2_2();B3_D_2();B4_D_2();B5_2();B6_2();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                PERSO1_b();PERSO2_d();


            }
        },travel);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {



                if (pers2==1){

                    lost();
                }else {

                    b();

                    RAND();
                }

            }
        },2*travel);



    }

    public void P_sec23(){




        B1_D_1();B2_1();B3_D_1();B4_1();B5_D_1();B6_1();


        PERSO1_c();PERSO2_d();B1_D_2();B2_2();B3_D_2();B4_2();B5_D_2();B6_2();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                PERSO1_a();PERSO2_f();


            }
        },travel);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {



                if (pers1==1){

                    lost();
                }else {

                    a();

                    RAND();
                }

            }
        },2*travel);



    }

    public void P_sec24(){




        B1_D_1();B2_1();B3_D_1();B4_1();B5_1();B6_D_1();


        PERSO1_c();PERSO2_d();B1_D_2();B2_2();B3_D_2();B4_2();B5_2();B6_D_2();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                PERSO1_b();PERSO2_f();


            }
        },travel);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {



                if (pers2==1){

                    lost();
                }else {

                    b();

                    RAND();
                }

            }
        },2*travel);



    }

    public void P_sec25(){




        B1_1();B2_D_1();B3_D_1();B4_D_1();B5_1();B6_1();


        PERSO1_b();PERSO2_d();B1_2();B2_D_2();B3_D_2();B4_D_2();B5_2();B6_2();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                PERSO1_c();PERSO2_e();


            }
        },travel);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {



                if (pers1==1){

                    lost();
                }else {

                    a();

                    RAND();
                }

            }
        },2*travel);



    }

    public void P_sec26(){




        B1_1();B2_D_1();B3_D_1();B4_1();B5_D_1();B6_1();


        PERSO1_c();PERSO2_d();B1_2();B2_D_2();B3_D_2();B4_2();B5_D_2();B6_2();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                PERSO1_a();PERSO2_f();


            }
        },travel);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {




                RAND();


            }
        },2*travel);



    }

    public void P_sec27(){




        B1_1();B2_D_1();B3_D_1();B4_1();B5_1();B6_D_1();


        PERSO1_c();PERSO2_d();B1_2();B2_D_2();B3_D_2();B4_2();B5_2();B6_D_2();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                PERSO1_b();PERSO2_e();


            }
        },travel);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {



                if (pers1==1){

                    lost();
                }else {

                    a();

                    RAND();
                }

            }
        },2*travel);



    }

    public void P_sec28(){




        B1_D_1();B2_D_1();B3_1();B4_D_1();B5_D_1();B6_1();


        PERSO1_b();PERSO2_d();B1_D_2();B2_D_2();B3_2();B4_D_2();B5_D_2();B6_2();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                PERSO1_b();PERSO2_f();


            }
        },travel);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {



                if (pers1==1){

                    lost();
                }else {

                    a();

                    RAND();
                }

            }
        },2*travel);



    }

    public void P_sec29(){




        B1_D_1();B2_D_1();B3_1();B4_D_1();B5_1();B6_D_1();


        PERSO1_a();PERSO2_d();B1_D_2();B2_D_2();B3_2();B4_D_2();B5_2();B6_D_2();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                PERSO1_b();PERSO2_e();


            }
        },travel);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {



                if (pers1==1){

                    lost();
                }else {

                    a();

                    RAND();
                }

            }
        },2*travel);



    }

    public void P_sec30(){


        B1_D_1();B2_D_1();B3_1();B4_1();B5_D_1();B6_D_1();


        PERSO1_a();PERSO2_e();B1_D_2();B2_D_2();B3_2();B4_2();B5_D_2();B6_D_2();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                PERSO1_c();PERSO2_e();


            }
        },travel);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {



                if (pers2==1){

                    lost();
                }else {

                    b();

                    RAND();
                }

            }
        },2*travel);



    }

    public void P_sec31(){




        B1_D_1();B2_1();B3_D_1();B4_D_1();B5_D_1();B6_1();


        PERSO1_b();PERSO2_f();B1_D_2();B2_2();B3_D_2();B4_D_2();B5_D_2();B6_2();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                PERSO1_b();PERSO2_e();


            }
        },travel);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {



                if (pers2==1){

                    lost();
                }else {

                    b();

                    RAND();
                }

            }
        },2*travel);



    }

    public void P_sec32(){




        B1_D_1();B2_1();B3_D_1();B4_D_1();B5_1();B6_D_1();


        PERSO1_b();PERSO2_d();B1_D_2();B2_2();B3_D_2();B4_D_2();B5_2();B6_D_2();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                PERSO1_c();PERSO2_e();


            }
        },travel);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {



                if (pers1==1){

                    lost();
                }else {
                    a();

                    RAND();
                }

            }
        },2*travel);



    }

    public void P_sec33(){


        B1_D_1();B2_1();B3_D_1();B4_1();B5_D_1();B6_D_1();


        PERSO1_b();PERSO2_e();B1_D_2();B2_2();B3_D_2();B4_2();B5_D_2();B6_D_2();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                PERSO1_a();PERSO2_d();


            }
        },travel);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {



                if (pers1==1){

                    lost();
                }else {

                    a();

                    RAND();
                }

            }
        },2*travel);



    }

    public void P_sec34(){

        B1_1();B2_D_1();B3_D_1();B4_D_1();B5_D_1();B6_1();


        PERSO1_a();PERSO2_e();B1_2();B2_D_2();B3_D_2();B4_D_2();B5_D_2();B6_2();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                PERSO1_a();PERSO2_d();


            }
        },travel);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {



                if (pers2==1){

                    lost();
                }else {

                    b();

                    RAND();
                }

            }
        },2*travel);



    }

    public void P_sec35(){

        B1_1();B2_D_1();B3_D_1();B4_D_1();B5_1();B6_D_1();


        PERSO1_c();PERSO2_d();B1_2();B2_D_2();B3_D_2();B4_D_2();B5_2();B6_D_2();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                PERSO1_a();PERSO2_f();


            }
        },travel);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {



                if (pers2==1){

                    lost();
                }else {

                    b();

                    RAND();
                }

            }
        },2*travel);



    }

    public void P_sec36(){

        B1_1();B2_D_1();B3_D_1();B4_1();B5_D_1();B6_D_1();


        PERSO1_a();PERSO2_f();B1_2();B2_D_2();B3_D_2();B4_2();B5_D_2();B6_D_2();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                PERSO1_c();PERSO2_d();


            }
        },travel);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {



                if (pers1==1){

                    lost();
                }else {

                    a();

                    RAND();
                }

            }
        },2*travel);



    }



    public void PERSO1_AT_a(){

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(perso1,"x",a);
        animatorX.setDuration(0);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX);
        animatorSet.start();



    }

    public void PERSO1_AT_b(){

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(perso1,"x",b);
        animatorX.setDuration(0);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX);
        animatorSet.start();





    }

    public void PERSO1_AT_c(){

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(perso1,"x",c);
        animatorX.setDuration(0);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX);
        animatorSet.start();



    }



    public void PERSO2_AT_d(){

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(perso2,"x", d);
        animatorX.setDuration(0);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX);
        animatorSet.start();


    }

    public void PERSO2_AT_e(){

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(perso2,"x", e);
        animatorX.setDuration(0);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX);
        animatorSet.start();

    }

    public void PERSO2_AT_f(){

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(perso2,"x", f);
        animatorX.setDuration(travel);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX);
        animatorSet.start();


    }


    public void PERSO1_a(){

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(perso1,"x",a);
        animatorX.setDuration(travel);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX);
        animatorSet.start();



    }

    public void PERSO1_b(){

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(perso1,"x",b);
        animatorX.setDuration(travel);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX);
        animatorSet.start();



    }

    public void PERSO1_c(){

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(perso1,"x",c);
        animatorX.setDuration(travel);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX);
        animatorSet.start();



    }


    public void PERSO2_d(){

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(perso2,"x", d);
        animatorX.setDuration(travel);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX);
        animatorSet.start();


    }

    public void PERSO2_e(){

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(perso2,"x", e);
        animatorX.setDuration(travel);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX);
        animatorSet.start();


    }

    public void PERSO2_f(){

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(perso2,"x", f);
        animatorX.setDuration(travel);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX);
        animatorSet.start();


    }


    public void B1_D_1(){

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(b1,"x", a);
        animatorX.setDuration(0);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(b1,"y", 50);
        animatorY.setDuration(0);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX,animatorY);
        animatorSet.start();

    }

    public void B1_D_2(){


        ObjectAnimator animatorY = ObjectAnimator.ofFloat(b1,"y",y);
        animatorY.setDuration(2*travel);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorY);
        animatorSet.start();

    }

    public void B1_1(){

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(b1,"x", a);
        animatorX.setDuration(0);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(b1,"y", 0);
        animatorY.setDuration(0);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX,animatorY);
        animatorSet.start();

    }

    public void B1_2(){

        ObjectAnimator animatorY = ObjectAnimator.ofFloat(b1,"y",y-y2);
        animatorY.setDuration(2*travel);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorY);
        animatorSet.start();

    }


    public void B2_D_1(){

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(b2,"x", b);
        animatorX.setDuration(0);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(b2,"y", 50);
        animatorY.setDuration(0);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX,animatorY);
        animatorSet.start();

    }

    public void B2_D_2(){


        ObjectAnimator animatorY = ObjectAnimator.ofFloat(b2,"y", y);
        animatorY.setDuration(2*travel);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorY);
        animatorSet.start();

    }

    public void B2_1(){

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(b2,"x", b);
        animatorX.setDuration(0);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(b2,"y", 0);
        animatorY.setDuration(0);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX,animatorY);
        animatorSet.start();

    }

    public void B2_2(){

        ObjectAnimator animatorY = ObjectAnimator.ofFloat(b2,"y", y-y2);
        animatorY.setDuration(2*travel);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorY);
        animatorSet.start();

    }


    public void B3_D_1(){

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(b3,"x", c);
        animatorX.setDuration(0);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(b3,"y", 50);
        animatorY.setDuration(0);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX,animatorY);
        animatorSet.start();

    }

    public void B3_D_2(){


        ObjectAnimator animatorY = ObjectAnimator.ofFloat(b3,"y",  y);
        animatorY.setDuration(2*travel);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorY);
        animatorSet.start();

    }

    public void B3_1(){

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(b3,"x", c);
        animatorX.setDuration(0);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(b3,"y", 0);
        animatorY.setDuration(0);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX,animatorY);
        animatorSet.start();

    }

    public void B3_2(){

        ObjectAnimator animatorY = ObjectAnimator.ofFloat(b3,"y",  y-y2);
        animatorY.setDuration(2*travel);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorY);
        animatorSet.start();

    }


    public void B4_D_1(){

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(b4,"x", d);
        animatorX.setDuration(0);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(b4,"y", 50);
        animatorY.setDuration(0);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX,animatorY);
        animatorSet.start();

    }

    public void B4_D_2(){


        ObjectAnimator animatorY = ObjectAnimator.ofFloat(b4,"y",  y);
        animatorY.setDuration(2*travel);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorY);
        animatorSet.start();

    }

    public void B4_1(){

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(b4,"x", d);
        animatorX.setDuration(0);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(b4,"y", 0);
        animatorY.setDuration(0);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX,animatorY);
        animatorSet.start();

    }

    public void B4_2(){

        ObjectAnimator animatorY = ObjectAnimator.ofFloat(b4,"y",  y-y2);
        animatorY.setDuration(2*travel);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorY);
        animatorSet.start();

    }


    public void B5_D_1(){

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(b5,"x", e);
        animatorX.setDuration(0);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(b5,"y", 50);
        animatorY.setDuration(0);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX,animatorY);
        animatorSet.start();

    }

    public void B5_D_2(){


        ObjectAnimator animatorY = ObjectAnimator.ofFloat(b5,"y",  y);
        animatorY.setDuration(2*travel);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorY);
        animatorSet.start();

    }

    public void B5_1(){

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(b5,"x", e);
        animatorX.setDuration(0);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(b5,"y", 0);
        animatorY.setDuration(0);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX,animatorY);
        animatorSet.start();

    }

    public void B5_2(){

        ObjectAnimator animatorY = ObjectAnimator.ofFloat(b5,"y",  y-y2);
        animatorY.setDuration(2*travel);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorY);
        animatorSet.start();

    }


    public void B6_D_1(){

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(b6,"x", f);
        animatorX.setDuration(0);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(b6,"y", 50);
        animatorY.setDuration(0);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX,animatorY);
        animatorSet.start();

    }

    public void B6_D_2(){


        ObjectAnimator animatorY = ObjectAnimator.ofFloat(b6,"y",  y);
        animatorY.setDuration(2*travel);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorY);
        animatorSet.start();

    }

    public void B6_1(){

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(b6,"x", f);
        animatorX.setDuration(0);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(b6,"y", 0);
        animatorY.setDuration(0);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX,animatorY);
        animatorSet.start();

    }

    public void B6_2(){

        ObjectAnimator animatorY = ObjectAnimator.ofFloat(b6,"y",  y-y2);
        animatorY.setDuration(2*travel);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorY);
        animatorSet.start();

    }


    public void lost(){

        if(still_playing) {
            Intent i = new Intent(main_game.this, finish.class);
            i.putExtra(score_key, Sscore);
            startActivity(i);
        }
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


