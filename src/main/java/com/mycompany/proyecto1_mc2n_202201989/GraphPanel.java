/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1_mc2n_202201989;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import javax.swing.JPanel;
public class GraphPanel extends JPanel{
    private ArrayList<Point> vertices;  // Lista de puntos que representan los vértices del grafo
    private ArrayList<Point> selectedVertex = new ArrayList<>();
    private ArrayList<int[]> edges;     // Lista de aristas del grafo (un par de índices de vértices)
    public static boolean addingEdge;
    private int vertexCount = 0;
    private ArrayList<Point> path = new ArrayList<>(); 
    private char[] labels = "abcdefghijklmnopqrstuvwxyz".toCharArray();
      
    public GraphPanel() {
        vertices = new ArrayList<Point>();
        edges = new ArrayList<int[]>();
     
         addingEdge = false;
      addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
        if (addingEdge) {
            // Llamar al método selectVertex para agregar el vértice seleccionado a la lista
            selectVertex(e.getPoint());
            
            // Si se han seleccionado dos vértices, agregar el borde y reiniciar la lista
            if (selectedVertex.size() == 2) {
                addEdge(selectedVertex.get(0), selectedVertex.get(1));
                selectedVertex.clear();
            }
        } else {
            // Agregar un vértice en las coordenadas del clic
            addVertex(e.getPoint().x, e.getPoint().y);
            }
        }
        });
    }
        public void addVertex(int x, int y) {
         vertices.add(new Point(x, y));
         vertexCount++;
         repaint();
        }
    private void selectVertex(Point point) {
        final int SELECT_THRESHOLD = 5; // distancia mínima para seleccionar un vértice
        for (Point vertex : vertices) {
            double distance = Math.sqrt(Math.pow(point.x - vertex.x, 2) + Math.pow(point.y - vertex.y, 2));
            if (distance < SELECT_THRESHOLD) {
                selectedVertex.add(vertex);
                break;
            }
        }
    }

 
      public void addEdge(Point p1, Point p2) {
         Point closest1 = getClosestVertex((int) p1.getX(), (int) p1.getY());
         Point closest2 = getClosestVertex((int) p2.getX(), (int) p2.getY());
         if (closest1 != null && closest2 != null) {
            int u = vertices.indexOf(closest1);
            int v = vertices.indexOf(closest2);
            int[] edge1 = {u, v};
            int[] edge2 = {v, u};
             edges.add(edge1);
           edges.add(edge2);
           repaint();
         }
        }
        private Point getClosestVertex(int x, int y) {
          double minDistance = Double.MAX_VALUE;
            Point closestVertex = null;

            for (Point v : vertices) {
                double distance = Math.sqrt(Math.pow(x - v.getX(), 2) + Math.pow(y - v.getY(), 2));
                if (distance < minDistance) {
                    minDistance = distance;
                    closestVertex = v;
                }
            }
          return closestVertex;
          }
        public void setAddingEdge(boolean addingEdge) {
         this.addingEdge = addingEdge;
        }
        
        public boolean isAddingEdge() {
            return addingEdge;
        }
        public Point getVertexByLabel(char label) {
            for (int i = 0; i < vertices.size(); i++) {
                if (labels[i] == label) {
                    return vertices.get(i);
                }
            }
            return null;
        }
    @Override
    protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    // Dibujar los vértices
    g.setColor(Color.BLUE);
    for (int i = 0; i < vertices.size(); i++) {
        Point p = vertices.get(i);
        g.fillOval(p.x - 5, p.y - 5, 10, 10);
        g.drawString(String.valueOf(labels[i]), p.x - 3, p.y + 15);
    }

    // Dibujar las aristas
    g.setColor(Color.BLACK);
    for (int[] edge : edges) {
        Point u = vertices.get(edge[0]);
        Point v = vertices.get(edge[1]);
        g.drawLine(u.x, u.y, v.x, v.y);
    }

    // Dibujar el camino encontrado con una línea más gruesa
    g2d.setColor(Color.RED);
    g2d.setStroke(new BasicStroke(3)); // Grosor de la línea
        for (int i = 0; i < path.size() - 1; i++) {
            Point u = path.get(i);
            Point v = path.get(i + 1);
            g2d.drawLine(u.x, u.y, v.x, v.y);
        }
    }
        public ArrayList<Point> findShortestPath(Point start, Point end) {
        // Mapa de distancia y vértices previos
        HashMap<Point, Double> distance = new HashMap<>();
        HashMap<Point, Point> previousVertex = new HashMap<>();

        // Inicializar todas las distancias como infinitas y los vértices previos como nulos
        for (Point vertex : vertices) {
            distance.put(vertex, Double.MAX_VALUE);
            previousVertex.put(vertex, null);
        }

        // La distancia al vértice de inicio es 0
        distance.put(start, 0.0);

        // Conjunto de vértices no visitados
        HashSet<Point> unvisited = new HashSet<>(vertices);

        while (!unvisited.isEmpty()) {
            // Encontrar el vértice no visitado más cercano
            Point current = null;
            double shortestDistance = Double.MAX_VALUE;
            for (Point vertex : unvisited) {
                if (distance.get(vertex) < shortestDistance) {
                    shortestDistance = distance.get(vertex);
                    current = vertex;
                }
            }

            // Si el vértice más cercano es el vértice final, se ha encontrado el camino más corto
            if (current.equals(end)) {
                break;
            }

            // Eliminar el vértice más cercano del conjunto de no visitados
            unvisited.remove(current);

            // Actualizar las distancias y los vértices previos para los vecinos no visitados del vértice actual
            for (int[] edge : edges) {
                Point u = vertices.get(edge[0]);
                Point v = vertices.get(edge[1]);
                if (u.equals(current)) {
                    if (unvisited.contains(v)) {
                        double alt = distance.get(current) + getDistance(current, v);
                        if (alt < distance.get(v)) {
                            distance.put(v, alt);
                            previousVertex.put(v, current);
                        }
                    }
                } else if (v.equals(current)) {
                    if (unvisited.contains(u)) {
                        double alt = distance.get(current) + getDistance(current, u);
                        if (alt < distance.get(u)) {
                            distance.put(u, alt);
                            previousVertex.put(u, current);
                        }
                    }
                }
            }
        }

        // Reconstruir el camino más corto desde el vértice final al vértice de inicio
        ArrayList<Point> path = new ArrayList<>();
        Point current = end;
        while (previousVertex.get(current) != null) {
            path.add(current);
            current = previousVertex.get(current);
        }
        path.add(current);
        Collections.reverse(path);

        return path;
    }
    public String setPath(ArrayList<Point> path) {
        this.path = path;
     
        String pathString = "";
        for (int i = 0; i < path.size(); i++) {
            Point vertex = path.get(i);
            pathString += labels[vertices.indexOf(vertex)];
            if (i < path.size() - 1) {
                pathString += "->";
            }
        }
        
        repaint();
        return pathString;
    }
    // Método auxiliar para obtener la distancia entre dos vértices
    private double getDistance(Point u, Point v) {
        return Math.sqrt(Math.pow(u.x - v.x, 2) + Math.pow(u.y - v.y, 2));
    }  
}
