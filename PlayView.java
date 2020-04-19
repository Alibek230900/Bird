package com.example.balapan;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

public class PlayView extends View {

    private Button pauseBtn;

    private int canvasWidth;
    private int canvasHeight;

    private Bitmap bird[]=new Bitmap[2];
    private int birdX=10;
    private int birdY;
    private int speed;

    private int feedX;
    private int feedY;
    private int feedspeed=15;
    private Paint feedPaint=new Paint();

    private int obstacleX;
    private int obstacleY;
    private int obstaclespeed=20;
    private Paint obstaclePaint=new Paint();


    //private Bitmap bird;
    private Bitmap Background;
    private Paint scorePaint=new Paint();
    private int score;
    private Paint levelPaint=new Paint();
    private Bitmap life[]=new Bitmap[2];
    private Timer timer=new Timer();

    private boolean touch_flag=false;
    private boolean pause=false;
    private int lifecounter;
    private Handler handler=new Handler();





    public PlayView(Context context) {
        super(context);


        bird[0]=BitmapFactory.decodeResource(getResources(),R.drawable.qus90);
        bird[1]=BitmapFactory.decodeResource(getResources(),R.drawable.qus80);

        Background=BitmapFactory.decodeResource(getResources(), R.drawable.fon);

        feedPaint.setColor(Color.YELLOW);
        feedPaint.setAntiAlias(false);


        obstaclePaint.setColor(Color.BLACK);
        obstaclePaint.setAntiAlias(false);


        scorePaint.setColor(Color.RED);
        scorePaint.setTextSize(32);
        scorePaint.setTypeface(Typeface.DEFAULT);
        scorePaint.setAntiAlias(true);


        levelPaint.setColor(Color.BLUE);
        levelPaint.setTextSize(32);
        levelPaint.setTypeface(Typeface.DEFAULT);
        levelPaint.setTextAlign(Paint.Align.CENTER);
        levelPaint.setAntiAlias(true);

        life[0]=BitmapFactory.decodeResource(getResources(),R.drawable.zhurek);
        life[1]=BitmapFactory.decodeResource(getResources(), R.drawable.jurek);


        birdY=500;
        score=0;
        lifecounter=3;

       //timer.schedule(new TimerTask() {
         //   @Override
           // public void run() {
             //   handler.post(new Runnable() {
               //     @Override
                 //   public void run() {
                   //    birdX,birdY
                    //}
                //})
            //}
        //},0,20);

    }




    @Override
    protected void onDraw(Canvas canvas) {

        canvasWidth=canvas.getWidth();
        canvasHeight=canvas.getHeight();




        canvas.drawBitmap(Background,0,0,null);

        int minBirdY=bird[0].getHeight();
        int maxBirdY=canvasHeight-bird[0].getHeight()*3;

        birdY+=speed;
        if (birdY<minBirdY) birdY=minBirdY;
        if (birdY>maxBirdY) birdY=maxBirdY;
        speed+=2;

        if (touch_flag){
            canvas.drawBitmap(bird[1],birdX,birdY,null);
            touch_flag=false;
        } else{
            canvas.drawBitmap(bird[0],birdX,birdY,null);
        }

        feedX-=feedspeed;
        if (hitCheck(feedX,feedY)){
            score+=10;
            feedX=-100;

        }
        if (feedX<0){
            feedX=canvasWidth+20;
            feedY=(int)Math.floor(Math.random()*(maxBirdY-minBirdY))+minBirdY;
        }
        canvas.drawCircle(feedX,feedY,10,feedPaint);

        obstacleX-=obstaclespeed;
        if (hitCheck(obstacleX,obstacleY)){
            obstacleX-=100;
            lifecounter--;
            if (lifecounter==0){
                Log.v("Message","Utyldyn");
            }

        } if (obstacleX<0){
            obstacleX=canvasWidth+200;
            obstacleY=(int)Math.floor(Math.random()*(maxBirdY-minBirdY))+minBirdY;
        }
        canvas.drawCircle(obstacleX,obstacleY,20,obstaclePaint);





        //canvas.drawBitmap(bird, 0, 0, null);

        canvas.drawText("Upay sany:0"+score,20,60,scorePaint);

        canvas.drawText("Lv1",canvasWidth/2,60,levelPaint);

        for (int i=0;i<3;i++){
            int x=(int)(700+life[0].getWidth()*1.5*i);
            int y=30;

            if (i<lifecounter){
                canvas.drawBitmap(life[0],x,y,null);
            } else {
                canvas.drawBitmap(life[1],x,y,null);
            }
        }

        //canvas.drawBitmap(life[0],700,40,null);
        //canvas.drawBitmap(life[0],800,40,null);
        //canvas.drawBitmap(life[1],900,40,null);

    }

    public boolean hitCheck(int x, int y){
        if (birdX<x && x<(birdX+bird[0].getWidth()) && birdY<y && y<(birdY+bird[0].getHeight())){
            return true;
        }
        return false;
    }

    public  void pausePushed(View view){
        if(pauseBtn==false){
            pause=true;
            timer.cancel();
            timer=null;
            pauseBtn.setText("Start");
        } else {
            pause=false;
            pauseBtn.setText("Pause");

            timer=new Timer();
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    View.invalidate();
                }
            },0, 20);


        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_DOWN){
            touch_flag=true;
            speed=-20;
        }
        return true;
    }
}
