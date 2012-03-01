package com.gdacarv.engine.androidgame;

import android.graphics.Bitmap;

public class PhysicalSprite extends Sprite {

	public Vector Movement, Acceleration;
	public float Mass = 1, FrictionX = 0.7f;
	public boolean FrictionXOn;
	
	public PhysicalSprite(Bitmap bmp) {
		this(bmp, 1, 1);
	}

	public PhysicalSprite(Bitmap bmp, int bmp_rows, int bmp_columns) {
		super(bmp, bmp_rows, bmp_columns);
		Movement = new Vector();
		Acceleration = new Vector();
		FrictionXOn = true;
	}
	
	@Override
	public void update() {
		super.update();
		Movement.add(Acceleration);
		applyFriction();
		x += Movement.getX();
		y += Movement.getY();
	}

	private void applyFriction() {
		if(FrictionXOn){
			Movement.multX(FrictionX);
			if(Math.abs(Movement.getX()) < 1f)
				Movement.setX(0f);
		}
	}

	public void applyForce(Vector force){
		Acceleration.add(force.divide(Mass));
	}
	
	public void applyMovement(Vector movement){
		Movement.add(movement);
	}
}
