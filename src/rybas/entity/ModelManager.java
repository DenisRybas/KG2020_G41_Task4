package rybas.entity;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import rybas.entity.builder.BasicModelBuilder;
import rybas.input.ClickType;
import rybas.input.Keyboard;
import rybas.input.Mouse;
import rybas.input.UserInput;
import rybas.point.MyVector;
import rybas.point.PointConverter;
import rybas.world.Camera;

public class ModelManager {
	
	private List<IModel> entities;
	private int initialX, initialY;
	private double mouseSensitivity = 2.5;
	private double moveSpeed = 10;
    private MyVector lightVector1 = MyVector.normalize(new MyVector(300, 1, 300));
    private MyVector lightVector2 = MyVector.normalize(new MyVector(300, 500, -300));
	private Mouse mouse;
	private Keyboard keyboard;
	private Camera camera;
	
	public ModelManager() {
		this.entities = new ArrayList<IModel>();
		this.camera = new Camera(0, 0, 0);
	}
	
	public void init(UserInput userInput) {
		this.mouse = userInput.mouse;
		this.keyboard = userInput.keyboard;
		this.entities.add(BasicModelBuilder.createSphere(Color.CYAN, 100,
				0, 0, 0));
		this.setLighting();
	}
	
	public void update() {
		int x = this.mouse.getX();
		int y = this.mouse.getY();
		if(this.mouse.getButton() == ClickType.LeftClick) {
			int xDif = x - initialX;
			int yDif = y - initialY;
			
			this.rotate(true, 0, -yDif/mouseSensitivity, -xDif/mouseSensitivity);
		}
		else if(this.mouse.getButton() == ClickType.RightClick) {
			int xDif = x - initialX;
			
			this.rotate(true, -xDif/mouseSensitivity, 0, 0);
		}
		
		if(this.mouse.isScrollingUp()) {
			PointConverter.zoomIn();
		}
		else if(this.mouse.isScrollingDown()) {
			PointConverter.zoomOut();
		}
		
		if(this.keyboard.getLeft()) {
			this.camera.translate(0, -moveSpeed, 0);
			for(IModel entity : this.entities) {
				entity.translate(0, moveSpeed, 0);
			}
		}
		if(this.keyboard.getRight()) {
			this.camera.translate(0, moveSpeed, 0);
			for(IModel entity : this.entities) {
				entity.translate(0, -moveSpeed, 0);
			}
		}
		if(this.keyboard.getUp()) {
			this.camera.translate(0, 0, moveSpeed);
			for(IModel entity : this.entities) {
				entity.translate(0, 0, -moveSpeed);
			}
		}
		if(this.keyboard.getDown()) {
			this.camera.translate(0, 0, -moveSpeed);
			for(IModel entity : this.entities) {
				entity.translate(0, 0, moveSpeed);
			}
		}
		if(this.keyboard.getForward()) {
			this.camera.translate(-moveSpeed, 0, 0);
			for(IModel entity : this.entities) {
				entity.translate(moveSpeed, 0, 0);
			}
		}
		if(this.keyboard.getBackward()) {
			this.camera.translate(moveSpeed, 0, 0);
			for(IModel entity : this.entities) {
				entity.translate(-moveSpeed, 0, 0);
			}
		}
		
		this.mouse.resetScroll();
		this.keyboard.update();

		initialX = x;
		initialY = y;
	}
	
	public void render(Graphics g) {
		for(IModel entity : this.entities) {
			entity.render(g);
		}
	}
	
	private void rotate(boolean CW, double xDegrees, double yDegrees, double zDegrees) {
		for(IModel entity : this.entities) {
			entity.rotate(CW, xDegrees, yDegrees, zDegrees, this.lightVector1, this.lightVector2);
		}
	}
	
	private void setLighting() {
		for(IModel entity : this.entities) {
			entity.setLighting(this.lightVector1, this.lightVector2);
		}
	}
	

}
