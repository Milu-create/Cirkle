package com.example.myapplication;

import android.animation.ArgbEvaluator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;

import java.util.Random;

public class MyThread extends Thread{

    public static final int FRACTION_TIME = 1000;

    private ArgbEvaluator argbEvaluator;

    private Paint paint;

    private SurfaceHolder surfaceHolder;

    private boolean flag;

    private long startTime;

    private long buffTime;


    MyThread(SurfaceHolder ha){
        flag = false;
        surfaceHolder = ha;
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        argbEvaluator = new ArgbEvaluator();
    }

    public void  setRunning(boolean f){
        this.flag = f;
    }

    @Override
    public void run() {
        Canvas canvas;
        startTime = getTime();
        while (flag){
            long currTime = getTime();
            long elapsedTime = currTime - buffTime;
            if(elapsedTime < 500){
                continue;
            }
            canvas = surfaceHolder.lockCanvas();
            drawCircles(canvas);
            surfaceHolder.unlockCanvasAndPost(canvas);
            buffTime = getTime();
        }
    }

    public long getTime(){
        return System.nanoTime()/1000;
    }

    public void drawCircles(Canvas canvas){
        Random random = new Random();
        long currentTime = getTime();
        int centerX = canvas.getWidth()/2;
        int centerY = canvas.getHeight()/2;
        canvas.drawColor(Color.BLACK);
        float maxRadius = Math.min(canvas.getHeight(), canvas.getWidth());
        Log.d("RRRR maxRadius=", Float.toString(maxRadius));
        float fraction = (float)(currentTime%FRACTION_TIME)/FRACTION_TIME;
        Log.d("RRRR fraction=", Float.toString(fraction));
        int color = (int)argbEvaluator.evaluate(fraction, Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)), Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
        Log.d("RRRR color=", Integer.toString(color));
        paint.setColor(color);
        canvas.drawCircle(centerX, centerY, maxRadius*fraction, paint);

    }
}
