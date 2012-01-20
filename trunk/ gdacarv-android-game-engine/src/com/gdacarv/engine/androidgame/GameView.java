package com.gdacarv.engine.androidgame;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public abstract class GameView extends SurfaceView{
	
	private SurfaceHolder mHolder;
	protected GameLoopThread gameLoopThread;
	protected ArrayList<Sprite> mSprites;
	
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
            	   onLoad();
            	   gameLoopThread.setRunning(true);
                   gameLoopThread.start();
               }

               @Override
               public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
               }
        });
	}

	protected abstract void onLoad();

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
	
	public abstract void TouchEvents(MotionEvent event);
	
	

	public void update() {
		for (Sprite sprite : mSprites) 
            sprite.update();
	}

	
}
