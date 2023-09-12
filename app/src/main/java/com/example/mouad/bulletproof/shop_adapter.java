package com.example.mouad.bulletproof;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;



public class shop_adapter extends BaseAdapter {

    private Context context;
    private static final String selected="selected";
    private static final String Bought="bought";
    private static final String Coins="coins";
    private ArrayList<Integer> bought = new ArrayList<>();
    private int height,width,items;
    private ArrayList<Integer> images;
    private SharedPreferences sharedPreferences;
    private int selected_image,coins;


    shop_adapter(Context context, int items, ArrayList<Integer> images, SharedPreferences sharedPreferences, int width, int height) {

        this.items=items;
        this.context=context;
        this.images=images;
        this.width=width;
        this.height=height;
        this.sharedPreferences=sharedPreferences;

        Load();



    }
    @Override
    public int getCount() {
        return items;
    }

    @Override
    public Object getItem(int i) {
        return items;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = LayoutInflater.from(context).
                inflate(R.layout.list_items, viewGroup, false);

        Button button1=view.findViewById(R.id.button);
        Button button2=view.findViewById(R.id.button2);
        Button button3=view.findViewById(R.id.button3);
        RelativeLayout layout=view.findViewById(R.id.layout);

        Button state1 = new Button(context);
        Button state2 = new Button(context);
        Button state3 = new Button(context);



        //SET THE WIDTH AND HEIGHT OF EACH BUTTON
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) button1.getLayoutParams();
        params.width = width/3;
        params.height=height/4;
        button1.setLayoutParams(params);
        state1.setLayoutParams(params);
        layout.addView(state1);

        RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) button2.getLayoutParams();
        params2.width = width/3;
        params2.height=height/4;
        button2.setLayoutParams(params2);
        state2.setLayoutParams(params2);
        layout.addView(state2);

        RelativeLayout.LayoutParams params3 = (RelativeLayout.LayoutParams) button3.getLayoutParams();
        params3.width = width/3;
        params3.height=height/4;
        button3.setLayoutParams(params3);
        state3.setLayoutParams(params3);
        layout.addView(state3);

        if (getCount()>(i+1)*3-3) {
            button1.setBackgroundResource(images.get((i + 1) * 3 - 3));
            button2.setBackgroundResource(images.get((i + 1) * 3 - 2));
            button3.setBackgroundResource(images.get((i + 1) * 3 - 1));



            final int i1=(i + 1) * 3 - 3;
            state1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (selected_image!=i1){
                        if (bought.contains(i1)){
                            selected_image=i1;
                        }else {
                           buy_dialog(i1);
                        }
                    }
                    saveData();

                }
            });

            final int i2=(i + 1) * 3 - 2;
            state2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (selected_image!=i2){
                        if (bought.contains(i2)){
                            selected_image=i2;
                        }else {
                            buy_dialog(i2);
                        }
                    }
                    saveData();

                }
            });

            final int i3=(i + 1) * 3 - 1;
            state3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (selected_image!=i3){
                        if (bought.contains(i3)){
                            selected_image=i3;
                        }else {
                            buy_dialog(i3);
                        }
                    }
                    saveData();

                }
            });

            if (selected_image==i1) {
                state1.setBackgroundResource(R.drawable.selected);
            }else if (!bought.contains(i1)){
                state1.setBackgroundResource(R.drawable.locked);
            }else{
                state1.setBackgroundColor(Color.TRANSPARENT);
            }

            if (selected_image==i2) {
                state2.setBackgroundResource(R.drawable.selected);
            }else if (!bought.contains(i2)){
                state2.setBackgroundResource(R.drawable.locked);
            }else{
                state2.setBackgroundColor(Color.TRANSPARENT);
            }

            if (selected_image==i3) {
                state3.setBackgroundResource(R.drawable.selected);
            }else if (!bought.contains(i3)){
                state3.setBackgroundResource(R.drawable.locked);
            }else{
                state3.setBackgroundColor(Color.TRANSPARENT);
            }



        }


        if (getCount()>(i+1)*3-3){
            return view;}else {
            view = LayoutInflater.from(context).
                    inflate(R.layout.empty, viewGroup, false);
            return view;
        } //SHOW THE ROW OR NOT
    }

    private void buy_dialog(int item){

        final RelativeLayout message_box;

        message_box=new RelativeLayout(context);
        Button buy= new Button(context);
        ImageView skin = new ImageView(context);

        //BOX
        message_box.setBackgroundResource(R.drawable.coins_container);
        RelativeLayout.LayoutParams layoutParams4= new RelativeLayout.LayoutParams(width/3+setx(100),height/4+sety(200) );
        message_box.setLayoutParams(layoutParams4);

        FrameLayout image_container= new FrameLayout(context);
        image_container.setBackgroundColor(Color.LTGRAY);
        skin.setBackgroundResource(images.get(item));
        RelativeLayout.LayoutParams layoutParams= new RelativeLayout.LayoutParams(width/3,height/4 );
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParams.bottomMargin=sety(50);
        layoutParams.topMargin=sety(50);
        skin.setLayoutParams(layoutParams);
        skin.setId(View.generateViewId());
        image_container.setLayoutParams(layoutParams);



        buy.setBackgroundResource(R.drawable.buy_100);
        RelativeLayout.LayoutParams layoutParams1= new RelativeLayout.LayoutParams(setx(300),sety(100) );
        layoutParams1.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParams1.addRule(RelativeLayout.BELOW,skin.getId());
        layoutParams1.bottomMargin=sety(50);
        buy.setLayoutParams(layoutParams1);


        message_box.addView(image_container);
        message_box.addView(skin);
        message_box.addView(buy);


        final Dialog alertDialog ;

        alertDialog = new Dialog(context);
        alertDialog.setContentView(message_box);
        if (alertDialog.getWindow()!=null) {
            alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
        alertDialog.show();

        int price=100;

        if (item>=3&&item<6){
            buy.setBackgroundResource(R.drawable.buy_150);
            price=150;
        }else if (item>=6&&item<9){
            buy.setBackgroundResource(R.drawable.buy_200);
            price=200;
        }else if (item>=9){
            buy.setBackgroundResource(R.drawable.buy_300);
            price=300;
        }

        final int finalItem = item;
        final int finalPrice = price;
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (coins>= finalPrice) {
                    bought.add(finalItem);
                    selected_image = finalItem;
                    coins -= finalPrice;
                    alertDialog.cancel();
                }

                saveData();
            }
        });


    }

    private void Load(){
        ArrayList<Integer> emptyList= new ArrayList<>(Collections.singletonList(0));
        //GET THE CURRENT COINS AND BEST SCORE
        selected_image = sharedPreferences.getInt(selected,0);
        Gson gson = new Gson();
        String emptyjson = gson.toJson(emptyList);
        String json = sharedPreferences.getString(Bought, emptyjson);
        Type type = new TypeToken<ArrayList<Integer>>() {}.getType();
        bought= gson.fromJson(json, type);
        String Scoins = sharedPreferences.getString(Coins, "0");
        assert Scoins != null;
        coins=Integer.parseInt(Scoins);



    }

    private void saveData() {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt(selected,selected_image);
        Gson gson = new Gson();
        String json = gson.toJson(bought);
        editor.putString(Bought, json);
        editor.putString(Coins, String.valueOf(coins));
        editor.apply();
        notifyDataSetChanged();

        ((shop)context).coins_bar();


    }

    private int setx(int x){
        int i;

        i=(x*width)/1080;

        return i;
    }

    private int sety(int x){
        int i;

        i=(x*height)/1770;

        return i;
    }
}
