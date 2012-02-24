package com.gdacarv.engine.androidgame;

import android.graphics.Bitmap;

public class PhysicalSprite extends Sprite {

	public Vector Movement, Acceleration;
	public float Mass = 1, Friction = 0.9f;
	
	public PhysicalSprite(Bitmap bmp) {
		super(bmp);
		init();
	}

	public PhysicalSprite(Bitmap bmp, int bmp_rows, int bmp_columns) {
		super(bmp, bmp_rows, bmp_columns);
		init();
	}
	
	private void init() {
		Movement = new Vector();
		Acceleration = new Vector();
	}

	@Override
	public void update() {
		super.update();
		Movement.add(Acceleration);
		x += Movement.getX();
		y += Movement.getY();
		applyFriction();
	}

	private void applyFriction() {
		Movement.multX(Friction);
		if(Math.abs(Movement.getX()) < 1f)
			Movement.setX(0f);
		/*if(Math.abs(Movement.getY()) < 1f)
			Movement.setY(0f);*/
	}

	public void applyForce(Vector force){
		Acceleration.add(force.divide(Mass));
	}
	
	public void applyMovement(Vector movement){
		Movement.add(movement);
	}
}
