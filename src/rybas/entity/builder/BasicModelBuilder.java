package rybas.entity.builder;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import rybas.entity.Model;
import rybas.entity.IModel;
import rybas.point.MyPoint;
import rybas.point.MyVector;
import rybas.shapes.MyPolygon;
import rybas.shapes.Polyhedron;

public class BasicModelBuilder {

    public static IModel createCube(double size, double centerX, double centerY, double centerZ) {
        MyPoint p1 = new MyPoint(centerX + size / 2, centerY + -size / 2, centerZ + -size / 2);
        MyPoint p2 = new MyPoint(centerX + size / 2, centerY + size / 2, centerZ + -size / 2);
        MyPoint p3 = new MyPoint(centerX + size / 2, centerY + size / 2, centerZ + size / 2);
        MyPoint p4 = new MyPoint(centerX + size / 2, centerY + -size / 2, centerZ + size / 2);
        MyPoint p5 = new MyPoint(centerX + -size / 2, centerY + -size / 2, centerZ + -size / 2);
        MyPoint p6 = new MyPoint(centerX + -size / 2, centerY + size / 2, centerZ + -size / 2);
        MyPoint p7 = new MyPoint(centerX + -size / 2, centerY + size / 2, centerZ + size / 2);
        MyPoint p8 = new MyPoint(centerX + -size / 2, centerY + -size / 2, centerZ + size / 2);

        Polyhedron tetra = new Polyhedron(Arrays.asList(
                new MyPolygon(Color.BLUE, p5, p6, p7, p8),
                new MyPolygon(Color.WHITE, p1, p2, p6, p5),
                new MyPolygon(Color.YELLOW, p1, p5, p8, p4),
                new MyPolygon(Color.GREEN, p2, p6, p7, p3),
                new MyPolygon(Color.ORANGE, p4, p3, p7, p8),
                new MyPolygon(Color.RED, p1, p2, p3, p4)));

        List<Polyhedron> tetras = new ArrayList<Polyhedron>();
        tetras.add(tetra);

        return new Model(tetras);
    }

    public static IModel createDiamond(Color color, double size, double centerX, double centerY, double centerZ) {
        List<Polyhedron> tetras = new ArrayList<Polyhedron>();

        int edges = 20;
        double inFactor = 0.8;
        MyPoint bottom = new MyPoint(centerX, centerY, centerZ - size / 2);
        MyPoint[] outerPoints = new MyPoint[edges];
        MyPoint[] innerPoints = new MyPoint[edges];
        for (int i = 0; i < edges; i++) {
            double theta = 2 * Math.PI / edges * i;
            double xPos = -Math.sin(theta) * size / 2;
            double yPos = Math.cos(theta) * size / 2;
            double zPos = size / 2;
            outerPoints[i] = new MyPoint(centerX + xPos, centerY + yPos, centerZ + zPos * inFactor);
            innerPoints[i] = new MyPoint(centerX + xPos * inFactor, centerY + yPos * inFactor, centerZ + zPos);
        }

        List<MyPolygon> polygons = new ArrayList<>();
        for (int i = 0; i < edges; i++) {
            polygons.add(new MyPolygon(outerPoints[i], bottom, outerPoints[(i + 1) % edges]));
        }
        for (int i = 0; i < edges; i++) {
            polygons.add(new MyPolygon(outerPoints[i],
                    outerPoints[(i + 1) % edges], innerPoints[(i + 1) % edges],
                    innerPoints[i]));
        }
        polygons.add(new MyPolygon(innerPoints));

        Polyhedron tetra = new Polyhedron(color, false, polygons);
        tetras.add(tetra);

        return new Model(tetras);
    }

    public static IModel createSphere(Color color, double radius, double centerX, double centerY, double centerZ) {
        List<MyPolygon> polygons = new LinkedList<>();
        List<Polyhedron> tetras = new ArrayList<>();

        int nSlices = 30;
        int nSegments = 20;
        var startU = 0;
        var startV = 0;
        var endU = Math.PI * 2;
        var endV = Math.PI;
        var stepU = (endU - startU) / nSlices;
        var stepV = (endV - startV) / nSegments;
        for (var i = 0; i < nSlices; i++) {
            for (var j = 0; j < nSegments; j++) {
                var u = i * stepU + startU;
                var v = j * stepV + startV;
                var un = (i + 1) * stepU + startU;
                var vn = (j + 1) * stepV + startV;
                var p0 = new MyPoint(Math.cos(u) * Math.sin(v) * radius,
                        Math.cos(v) * radius,
                        Math.sin(u) * Math.sin(v) * radius);
                var p1 = new MyPoint(Math.cos(u) * Math.sin(vn) * radius,
                        Math.cos(vn) * radius,
                        Math.sin(u) * Math.sin(vn) * radius);
                var p2 = new MyPoint(Math.cos(un) * Math.sin(v) * radius,
                        Math.cos(v) * radius,
                        Math.sin(un) * Math.sin(v) * radius);
                var p3 = new MyPoint(Math.cos(un) * Math.sin(vn) * radius,
                        Math.cos(vn) * radius,
                        Math.sin(un) * Math.sin(vn) * radius);
                polygons.add(new MyPolygon(p0, p2, p1));
                polygons.add(new MyPolygon(p3, p1, p2));
            }
        }

        Polyhedron tetra = new Polyhedron(color, false, polygons);
        tetras.add(tetra);

        return new Model(tetras);
    }

}
