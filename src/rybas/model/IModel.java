package rybas.model;

import java.awt.Graphics;

import rybas.point.MyVector;

public interface IModel {
	
	void render(Graphics g);
	
	void translate(double x, double y, double z);
	
	void rotate(boolean CW, double xDegrees, double yDegrees, double zDegrees, MyVector lightVector1, MyVector lightVector2);
	
	void setLighting(MyVector lightVector1, MyVector lightVector2);
	
}
