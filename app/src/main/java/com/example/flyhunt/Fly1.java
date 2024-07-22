package com.example.flyhunt;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class Fly1 {
    Bitmap fly[] = new Bitmap[8];
    int flyx,flyy,velocity,flyFrame;
    Random random;


    public Fly1(Context context){


        fly[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.d1);
        fly[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.d2);
        fly[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.d3);
        fly[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.d4);
        fly[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.d5);
        fly[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.d6);
        fly[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.d7);
        fly[7] = BitmapFactory.decodeResource(context.getResources(), R.drawable.d8);
        random = new Random();
        resetPosition();

        flyFrame = 0;
    }
    public Bitmap getBitmap(){
        return fly[flyFrame];
    }

    public int getWidth(){
        return fly[0].getWidth();
    }

    public int getHeight(){
        return fly[0].getHeight();
    }

    public void resetPosition(){
        flyx = GameView.dwidth + random.nextInt(1200);
        flyy = random.nextInt(300);
        velocity = 12 + random.nextInt(15);
    }
}
