//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Android Game Engine developed by Gustavo Carvalho (gdacarv@gmail.com)													//
//Use it freely, but keep and give credits, please.																		//
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Android Game Engine desenvolvido por Gustavo Carvalho (gdacarv@gmail.com)												//
//Use-o livremente, mas mantenha e der os créditos, por favor.															//
//Visite http://tutoriandroid.blogspot.com para tutoriais de Android e detalhamento da implementação desta Game Engine. //
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package com.gdacarv.engine.androidgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Sprite {

	public static final int ANIM_STOP = 0;
	public static final int ANIM_GO = 1;
	public static final int ANIM_GOBACK = 2;
	public static final int ANIM_JUSTGO = 3;
	
	private int animation = ANIM_GOBACK;
	
    public int x = 0, y = 0; 
    protected Bitmap mBitmap;
    public boolean visible = true;
    
    private final int BMP_ROWS;
    protected final int BMP_COLUMNS;
    
    protected float currentFrame = 0;
    public int width,height;
    private int firstFrame = 0, lastFrame = 1;
    public OnAnimationEndListener endAnimationListener;
    
    protected float animationSpeed = 1f;
    
    private boolean animationControl = false;
    public Paint mPaint;
    
    public Sprite(Bitmap bmp) {
        this(bmp, 1, 1, 0, 0);
    }
    
    public Sprite(int x, int y, Bitmap bmp) {
        this(bmp, 1, 1, x, y);
    }
   
    public Sprite(Bitmap bmp, int bmp_rows, int bmp_columns) {
          this(bmp, bmp_rows, bmp_columns, 0, 0);
    }
    
    public Sprite(Bitmap bmp, int bmp_rows, int bmp_columns, int x, int y) {
        this.mBitmap = bmp;
        this.BMP_ROWS = bmp_rows;
        this.BMP_COLUMNS = bmp_columns;
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;
        lastFrame = BMP_COLUMNS*BMP_ROWS;
        if(getFrameCount() == 1)
      	  animation = ANIM_STOP;
        this.x = x;
        this.y = y;
  }

    public void update() {
    	switch (animation) {
		case ANIM_GO:
			currentFrame = ((currentFrame+animationSpeed-firstFrame) % (lastFrame-firstFrame)) + firstFrame;
			break;
		case ANIM_GOBACK:
			if(currentFrame+1 == lastFrame)
				animationControl = true;
			else if(currentFrame == firstFrame)
				animationControl = false;
			currentFrame = currentFrame+(animationControl ? -animationSpeed : animationSpeed);
			break;
		case ANIM_JUSTGO:
			if(currentFrame < lastFrame-1)
				currentFrame += animationSpeed;
			else{
				setAnimation(ANIM_STOP);
				if(endAnimationListener != null)
					endAnimationListener = endAnimationListener.OnAnimationEnd();
			}
			break;
		}
    }
   
    public void onDraw(Canvas canvas, int cameraX, int cameraY) {
    	if(visible){
	        int endX = x - cameraX,
	        	endY = y - cameraY;
	    	if(endX + width > 0 && endX < canvas.getWidth() && endY + height > 0 && endY < canvas.getHeight()){
	    		canvas.drawBitmap(mBitmap, 
		        		getFrameRect(), 
		        		new Rect(endX, endY, endX + width, endY + height), 
		        		mPaint);
	    	}
    	}
    }
    
    protected Rect getFrameRect(){
    	int srcX = (((int)currentFrame) % BMP_COLUMNS) * width,
    	srcY = (((int)currentFrame) / BMP_COLUMNS) * height;
    	return new Rect(srcX, srcY, srcX + width, srcY + height);
    }
    
    public void setFirstFrame(int frame){
    	firstFrame = frame;
    	if(firstFrame >= lastFrame){
    		lastFrame = firstFrame + 1;
    		currentFrame = firstFrame;
    		animationControl = false;
    	}else
    	if(currentFrame < firstFrame){
    		currentFrame = firstFrame;
    		animationControl = false;
    	}
    }
    
    public void setLastFrame(int frame){
    	lastFrame = frame;
    	if(lastFrame <= firstFrame){
    		firstFrame = lastFrame - 1;
			currentFrame = firstFrame;
			animationControl = false;
    	}else
    	if(currentFrame > frame){
    		currentFrame = firstFrame;
    		animationControl = false;
    	}
    }
    
    public int getFrameCount(){
    	return BMP_COLUMNS*BMP_ROWS;
    }
    
    public void setCurrentFrame(int frame){
    	currentFrame = frame;
    	firstFrame = frame;
    	lastFrame = frame+1;
    }
    
    public boolean setAnimation(int frame, int iframe, int lframe, int type){
    	if(frame < iframe || frame >= lframe || iframe >= lframe || type < 0 || type > 3)
    		return false;
    	currentFrame = frame;
    	firstFrame = iframe;
    	lastFrame = lframe;
    	if(getFrameCount() > 1)
    		animation = type;
    	return true;
    }
    
    public boolean setAnimation(int frame, int iframe, int lframe, OnAnimationEndListener endAnimation){
    	boolean result = setAnimation(frame, iframe, lframe, ANIM_JUSTGO);
		if(result){
			endAnimationListener = endAnimation;
	    	animation = ANIM_JUSTGO;
		}
    	return result;
    }
	
	public boolean setAnimation(int frame, int iframe, int lframe, int type, float speed) {
		boolean result = setAnimation(frame, iframe, lframe, type);
		if(result)
			animationSpeed = speed;
		return result;
	}
	
	public boolean setAnimation(int frame, int iframe, int lframe, float speed, OnAnimationEndListener endAnimation){
		boolean result = setAnimation(frame, iframe, lframe, ANIM_JUSTGO, speed);
		if(result){
			endAnimationListener = endAnimation;
	    	animation = ANIM_JUSTGO;
		}
		return result;
	}
    
    public boolean setAnimation(int type){
    	if(getFrameCount() > 1){
    		animation = type;
    		return true;
    	}
		else
			return false;
    			
    }
    
    public boolean collides(int testX, int testY){
    	return testX >= x && testY >= y && testX <= x+width && testY <= y+height;
    }
    
    public interface OnAnimationEndListener{
    	public OnAnimationEndListener OnAnimationEnd();
    }
}  