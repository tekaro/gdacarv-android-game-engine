//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Android Game Engine developed by Gustavo Carvalho (gdacarv@gmail.com)													//
//Use it freely, but keep and give credits, please.																		//
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Android Game Engine desenvolvido por Gustavo Carvalho (gdacarv@gmail.com)												//
//Use-o livremente, mas mantenha e der os créditos, por favor.															//
//Visite http://tutoriandroid.blogspot.com para tutoriais de Android e detalhamento da implementação desta Game Engine. //
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


package com.gdacarv.engine.androidgame;

import android.graphics.Canvas;

public class GameLoopThread extends Thread {
	
    private GameView mGameView;
    private boolean mRunning = false;
   
    public GameLoopThread(GameView view) {
          this.mGameView = view;
    }

    public void setRunning(boolean run) {
          mRunning = run;
    }

    @Override
    public void run() {
    	long ticksPS = 1000 / mGameView.FPS;
        long startTime, sleepTime;
		  while (mRunning) {
		         Canvas c = null;
		         startTime = System.currentTimeMillis();
		         try {
		                c = mGameView.getHolder().lockCanvas();
		                synchronized (mGameView.getHolder()) {
		                	mGameView.preUpdate();
		                	mGameView.input();
		                	mGameView.update();
		                	mGameView.onDraw(c);
		                	mGameView.posUpdate();
		                }
		         } finally {
		                if (c != null) {
		                       mGameView.getHolder().unlockCanvasAndPost(c);
		                }
		         }
		         sleepTime = ticksPS-(System.currentTimeMillis() - startTime);
                 try {
                        if (sleepTime > 0)
                               sleep(sleepTime);
                 } catch (Exception e) {}
		  }
		  
    }
}  