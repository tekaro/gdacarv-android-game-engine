package com.gdacarv.engine.androidgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Sprite {
	
    public int x = 0, y = 0; 
    private GameView mGameView;
    private Bitmap mBitmap;
    public boolean visible = true;
    private int[] useFrames;
    
    private final int BMP_ROWS;
    private final int BMP_COLUMNS;
    
    private int currentFrame = 0;
    public int width;
    public int height;
    
    public Sprite(GameView gameView, Bitmap bmp) {
        this(gameView, bmp, 1, 1);
  }
   
    public Sprite(GameView gameView, Bitmap bmp, int bmp_rows, int bmp_columns) {
          this.mGameView = gameView;
          this.mBitmap = bmp;
          this.BMP_ROWS = bmp_rows;
          this.BMP_COLUMNS = bmp_columns;
          this.width = bmp.getWidth() / BMP_COLUMNS;
          this.height = bmp.getHeight() / BMP_ROWS;
          useFrames = new int[BMP_COLUMNS*BMP_ROWS];
    }

    public void update() {
    	currentFrame = ++currentFrame % (BMP_COLUMNS * BMP_ROWS);
    }
   
    public void onDraw(Canvas canvas) {
    	if(visible){
	    	int srcX = (currentFrame % BMP_COLUMNS) * width;
	        int srcY = (currentFrame / BMP_COLUMNS) * height;
	        canvas.drawBitmap(mBitmap, 
	        		new Rect(srcX, srcY, srcX + width, srcY + height), 
	        		new Rect(x, y, x + width, y + height), 
	        		null);
    	}
    }
}  