package rybas.model.builder;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import rybas.model.Model;
import rybas.model.IModel;
import rybas.point.MyPoint;
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

    public static IModel createSphere(Color color, double radius) {
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

    public static IModel createCylinder(int slices, int radius, Color color) {
        List<MyPolygon> polygons = new LinkedList<>();
        List<Polyhedron> tetras = new ArrayList<>();

        for (int i = 0; i < slices; i++) {
            double theta = i * 2.0 * Math.PI;
            double nextTheta = (i + 1) * 2.0 * Math.PI;
            /*vertex at middle of end */
            MyPoint point1 = new MyPoint(0.0, radius, 0.0);
            /*vertices at edges of circle*/
            MyPoint point2 = new MyPoint(radius * Math.cos(theta), radius,
                    radius * Math.sin(theta));
            MyPoint point3 = new MyPoint(radius * Math.cos(nextTheta), radius,
                    radius * Math.sin(nextTheta));
            /* the same vertices at the bottom of the cylinder*/
            MyPoint point4 = new MyPoint(radius * Math.cos(nextTheta), -radius,
                    radius * Math.sin(nextTheta));
            MyPoint point5 = new MyPoint(radius * Math.cos(theta), -radius,
                    radius * Math.sin(theta));
            MyPoint point6 = new MyPoint(0.0, -radius, 0.0);

            polygons.add(new MyPolygon(point1, point2, point3, point4, point5, point6));
        }

        Polyhedron tetra = new Polyhedron(color, false, polygons);
        tetras.add(tetra);

        return new Model(tetras);
    }
}
