package com.gdacarv.engine.androidgame;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class GameView extends SurfaceView {
	
	private SurfaceHolder mHolder;
	private GameLoopThread gameLoopThread;
	private ArrayList<Sprite> mSprites;
	
	public GameView(Context context) {
		super(context);
		mSprites = new ArrayList<Sprite>();
		gameLoopThread = new GameLoopThread(this);
		mHolder = getHolder();
        mHolder.addCallback(new SurfaceHolder.Callback() {

               @Override
               public void surfaceDestroyed(SurfaceHolder holder) {
            	   boolean retry = true;
                   gameLoopThread.setRunning(false);
                   while (retry) {
                          try {
                                gameLoopThread.join();
                                retry = false;
                          } catch (InterruptedException e) {
                          }
                   }
               }

               @Override
               public void surfaceCreated(SurfaceHolder holder) {
            	   gameLoopThread.setRunning(true);
                   gameLoopThread.start();
                   mSprites.add(new Sprite(GameView.this, BitmapFactory.decodeResource(getResources(), R.drawable.bad4), 4, 3)); //Take off
               }

               @Override
               public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
               }
        });
	}

	@Override
    protected void onDraw(Canvas canvas) {
		canvas.drawRGB(0, 0, 0);
		for (Sprite sprite : mSprites) 
            sprite.onDraw(canvas);
    }
	
	@Override
    public boolean onTouchEvent(MotionEvent event) {
		synchronized (getHolder()) {
			TouchEvents(event);
		}
		return true;
	}
	
	public void TouchEvents(MotionEvent event){
		int x = (int) event.getX();
		int y = (int) event.getY();
		mSprites.get(0).x = x;
		mSprites.get(0).y = y;
	}
	
	

	public void update() {
		for (Sprite sprite : mSprites) 
            sprite.update();
	}

	
}
