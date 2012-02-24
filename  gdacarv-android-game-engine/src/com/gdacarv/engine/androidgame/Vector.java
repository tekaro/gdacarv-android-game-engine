package com.gdacarv.engine.androidgame;

public class Vector {

	private float x = 0, y = 0, Value = -1;
	
	public Vector() {
	}

	public Vector(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public float getValue(){
		if(Value == -1)
			Value = (float) Math.sqrt((x*x) + (y*y));
		return Value;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
		Value = -1;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
		Value = -1;
	}

	public Vector add(Vector vector){
		x += vector.x;
		y += vector.y;
		Value = -1;
		return this;
	}
	
	public Vector divide(float num){
		x /= num;
		y /= num;
		Value = -1;
		return this;
	}
	
	public Vector mult(float num){
		x *= num;
		y *= num;
		Value = -1;
		return this;
	}
	
	public Vector multX(float num){
		x *= num;
		Value = -1;
		return this;
	}
	
	public Vector multY(float num){
		y *= num;
		Value = -1;
		return this;
	}
	
	public Vector sub(float num){
		float orig = getValue();
		if(orig > 0){
			Value = Math.max(orig-num, 0);		
			float div = Value/orig;
			x *= div;
			y *= div;
		}
		return this;
	}
}
