package com.example.flyhunt;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Fly2 extends Fly1{
    Bitmap[] fly = new Bitmap[8];
    public Fly2(Context context) {
        super(context);

        fly[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.b1);
        fly[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.b2);
        fly[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.b3);
        fly[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.b4);
        fly[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.b5);
        fly[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.b6);
        fly[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.b7);
        fly[7] = BitmapFactory.decodeResource(context.getResources(), R.drawable.b8);
        resetPosition();
    }

    @Override
    public Bitmap getBitmap() {
        return fly[flyFrame];
    }

    @Override
    public int getWidth() {
        return fly[0].getWidth();
    }

    @Override
    public int getHeight() {
        return fly[0].getHeight();
    }

    @Override
    public void resetPosition() {
        flyx = GameView.dwidth + random.nextInt(1500);
        flyy = random.nextInt(400);
        velocity = 13 + random.nextInt(17);
    }
}
