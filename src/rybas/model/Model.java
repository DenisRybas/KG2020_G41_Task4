package rybas.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import rybas.point.MyVector;
import rybas.shapes.MyPolygon;
import rybas.shapes.Polyhedron;

public class Model implements IModel {

	private List<Polyhedron> polyhedrons;
	private List<MyPolygon> polygons;
	
	public Model(List<Polyhedron> polyhedrons) {
		this.polyhedrons = polyhedrons;
		List<MyPolygon> tempList = new ArrayList<MyPolygon>();
		for(Polyhedron poly : this.polyhedrons) {
			tempList.addAll((poly.getPolygons()));
		}
		this.polygons = tempList;
		this.sortPolygons();
	}
	
	@Override
	public void render(Graphics g) {
		for(MyPolygon poly : this.polygons) {
			poly.render(g);
		}
	}

	@Override
	public void translate(double x, double y, double z) {
		for(Polyhedron poly : this.polyhedrons) {
			poly.translate(x, y, z);
		}
		this.sortPolygons();
	}

	@Override
	public void rotate(boolean CW, double xDegrees, double yDegrees, double zDegrees, MyVector lightVector1, MyVector lightVector2) {
		for(Polyhedron poly : this.polyhedrons) {
			poly.rotate(CW, xDegrees, yDegrees, zDegrees, lightVector1, lightVector2);
		}
		this.sortPolygons();
	}
	
	@Override
	public void setLighting(MyVector lightVector1, MyVector lightVector2) {
		for(Polyhedron poly : this.polyhedrons) {
			poly.setLighting(lightVector1, lightVector2);
		}
	}
	
	private void sortPolygons() {
		MyPolygon.sortPolygons(this.polygons);
	}
}
