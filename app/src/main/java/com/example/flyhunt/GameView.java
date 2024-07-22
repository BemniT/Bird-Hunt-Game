package com.example.flyhunt;
import static java.lang.Package.getPackage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.VideoView;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.ConsoleHandler;

public class GameView extends View {
    //VideoView background;
    Bitmap background;
    Bitmap ball,target;
    Rect rect;
    static int dwidth, dheight;
    int flyWidth;
    Handler handler;
    Runnable runnable;
    final long UPDATE_MILLIS = 30;
    ArrayList<Fly1> fly1;
    ArrayList<Fly2> fly2;
    float ballX,ballY;
    float sX,sY;
    float fX,fY;
    float dX,dY;
    float tempX,tempY;
    Paint borderPaint;
    int score;
    static int highScore;
    int life = 10;
    Context context;
    MediaPlayer fly1_hit,fly2_hit,fly_miss,ballthrow;
    boolean gameState = true;

    public GameView(Context context) {
        super(context);
        background = BitmapFactory.decodeResource(getResources(),R.drawable.background);
        Display display = ((Activity)getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dwidth = size.x;
        dheight = size.y;
        rect = new Rect(0 , 0,dwidth ,dheight);
        handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };

       fly1 = new ArrayList<>();
       fly2 = new ArrayList<>();
       for(int i=0; i<2; i++) {
           Fly1 fly_1 = new Fly1(context);
           fly1.add(fly_1);
       }
       for (int i=0; i<=1; i++){
           Fly2 fly_2 = new Fly2(context);
           fly2.add(fly_2);
       }
       ball = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
       target = BitmapFactory.decodeResource(getResources(), R.drawable.aim);
       ballX = ballY = 0;
       sX = sY = fX = fY =0;
       dX = dY = 0;
       tempX  = tempY = 0;
       borderPaint = new Paint();
       borderPaint.setColor(Color.rgb(56,56,56));
       borderPaint.setStrokeWidth(5);
       this.context = context;
       fly1_hit = MediaPlayer.create(context, R.raw.bird);
       fly2_hit = MediaPlayer.create(context, R.raw.bird);
       ballthrow = MediaPlayer.create(context, R.raw.gameover);
       fly_miss = MediaPlayer.create(context, R.raw.gameover);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        SharedPreferences.Editor
        if(highScore < score){
            highScore = score;
        }
        if (life < 1){
            Intent intent = new Intent(context, GameOver.class);
            intent.putExtra("score" , score);
            intent.putExtra("hScore", highScore);
            context.startActivity(intent);
            ((Activity)context).finish();
            gameState = false;
        }
        canvas.drawBitmap(background, null, rect, null);
        for(int i=0; i< fly1.size(); i++){
            canvas.drawBitmap(fly1.get(i).getBitmap(), fly1.get(i).flyx, fly1.get(i).flyy, null);
            fly1.get(i).flyFrame++;
            if(fly1.get(i).flyFrame >7){
                fly1.get(i).flyFrame = 0;
            }
            fly1.get(i).flyx -= fly1.get(i).velocity;
            if (fly1.get(i).flyx < -fly1.get(i).getWidth()){
                fly1.get(i).resetPosition();
                life--;
               if(fly_miss != null){
                    fly_miss.start();
                }
            }
            canvas.drawBitmap(fly2.get(i).getBitmap(), fly2.get(i).flyx, fly2.get(i).flyy, null);
            fly2.get(i).flyFrame++;
            if(fly2.get(i).flyFrame > 7){
                fly2.get(i).flyFrame = 0;
            }
            fly2.get(i).flyx -= fly2.get(i).velocity;
            if (fly2.get(i).flyx < -fly2.get(i).getWidth()){
                fly2.get(i).resetPosition();
                life--;
                if(fly_miss != null){
                    fly_miss.start();
                }
            }
          if (ballX <= (fly1.get(i).flyx + fly1.get(i).getWidth())
                  && ballX + ball.getWidth() >= fly1.get(i).flyx
                  && ballY <= (fly1.get(i).flyy + fly1.get(i).getHeight())
                  && ballY >= fly1.get(i).flyy){
              fly1.get(i).resetPosition();
              score++;
              /*if(fly1_hit != null){
                  fly1_hit.start();
              }*/
          }
            if (ballX <= (fly2.get(i).flyx + fly2.get(i).getWidth())
                    && ballX + ball.getWidth() >= fly2.get(i).flyx
                    && ballY <= (fly2.get(i).flyy + fly2.get(i).getHeight())
                    && ballY >= fly2.get(i).flyy){
                fly2.get(i).resetPosition();
                score++;
                /*if(fly2_hit != null){
                    fly2_hit.start();
                }*/
            }
        }
        if(sX > 0 && sY > dheight * .75f){
            canvas.drawBitmap(target, sX - target.getWidth()/2, sY - target.getHeight()/2, null);
        }
        if ((Math.abs(fX - sX) > 0 || Math.abs(fY - sY) > 0) && fY > 0 && fY > dheight * .75f){
            canvas.drawBitmap(target, fX - target.getWidth()/2, fY - target.getHeight()/2, null);
        }
        if ((Math.abs(dX) > 10 || Math.abs(dY) > 0) && sY > dheight *.75f){
            ballX = fX - ball.getWidth()/2 - tempX;
            ballY = fY - ball.getHeight()/2 - tempY;
            canvas.drawBitmap(ball,ballX,ballY,null);
            tempX += dX;
            tempY += dY;
        }
        canvas.drawLine(0, dheight*.75f,dwidth, dheight*.75f,borderPaint);
        if (gameState)
        {

        handler.postDelayed(runnable,UPDATE_MILLIS);}
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                dX = dY = fX = fY = tempX = tempY = 0;
                sX = event.getX();
                sY = event.getY();
                break;
                case MotionEvent.ACTION_MOVE:
                    fX = event.getX();
                    fY = event.getY();
                    break;
            case MotionEvent.ACTION_UP:
                fX = event.getX();
                fY = event.getY();
                ballX = event.getX();
                ballY = event.getY();
                dX = fX - sX;
                dY = fY - sY;
                break;
        }
        return true;
    }

}
