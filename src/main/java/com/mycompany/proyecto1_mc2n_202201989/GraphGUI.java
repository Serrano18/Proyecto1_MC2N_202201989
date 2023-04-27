/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1_mc2n_202201989;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.border.BevelBorder;
public class GraphGUI extends JFrame implements ActionListener {
    private JButton addVertexBtn, addEdgeBtn, searchBtn,camino1,camino2;
    private JLabel sourceLbl, destLbl,recorridoLbl,content;
    private JTextField sourceTxt, destTxt;
    private GraphPanel graphPanel;
    private JToggleButton addEdgeToggle;
     public static Point firstVertex = null;
      public static Point secondVertex = null;
      public ArrayList<Point> shortestPath;
     public GraphGUI() {
        super("PROYECTO 1 - GRAFOS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null); 
        Color miColor = new Color(204,255,204);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
       Color miC = new Color(255,204,204);
        addVertexBtn = new JButton("AGREGAR VERTICE");
          addVertexBtn.setBackground(miC);
          addVertexBtn.setPreferredSize(new Dimension(200, 50));
        addVertexBtn.setBorder(BorderFactory.createSoftBevelBorder(BevelBorder.RAISED, miColor, miColor.darker()));
        addVertexBtn.addActionListener(this);
        
        topPanel.add(addVertexBtn);
        addEdgeToggle = new JToggleButton("AGREGAR ARISTAS");
         addEdgeToggle.setPreferredSize(new Dimension(200, 50));
        addEdgeToggle.setBorder(BorderFactory.createSoftBevelBorder(BevelBorder.RAISED, miColor, miColor.darker()));
           addEdgeToggle.setBackground(miC);
           addEdgeToggle.addItemListener(new ItemListener() {
               @Override
               public void itemStateChanged(ItemEvent e) {
                   graphPanel.setAddingEdge(addEdgeToggle.isSelected());
               }
           });
         topPanel.add(addEdgeToggle);
         topPanel.setBorder(BorderFactory.createEmptyBorder(100, 0, 100, 0));
         
            topPanel.setBackground(miColor);
            topPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,5));
        add(topPanel, BorderLayout.NORTH);
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        sourceLbl = new JLabel("Vertice Inicio:");
        sourceTxt = new JTextField(3);
        destLbl = new JLabel("Vertice Final:");
        destTxt = new JTextField(3);
        searchBtn = new JButton("Camino Simple");
        camino1 = new JButton("Camino 1");
        camino2 = new JButton("Camino 2");
        searchBtn.setPreferredSize(new Dimension(120, 30));
        searchBtn.setBorder(BorderFactory.createSoftBevelBorder(BevelBorder.RAISED, miColor, miColor.darker()));
        searchBtn.setBackground(miC);
        searchBtn.addActionListener(this);
        camino1.setPreferredSize(new Dimension(120, 30));
        camino1.setBorder(BorderFactory.createSoftBevelBorder(BevelBorder.RAISED, miColor, miColor.darker()));
        camino1.setBackground(miC);
        camino1.addActionListener(this);
        camino2.setPreferredSize(new Dimension(120, 30));
        camino2.setBorder(BorderFactory.createSoftBevelBorder(BevelBorder.RAISED, miColor, miColor.darker()));
        camino2.setBackground(miC);
        camino2.addActionListener(this);
        
        bottomPanel.add(sourceLbl);
        bottomPanel.add(sourceTxt);
        bottomPanel.add(destLbl);
        bottomPanel.add(destTxt);
        bottomPanel.add(searchBtn);
        bottomPanel.add(camino1);
        bottomPanel.add(camino2);
        bottomPanel.add(Box.createVerticalStrut(100)); 
        content= new JLabel("Recorrido:");
        recorridoLbl = new JLabel("");
        bottomPanel.add(content, BorderLayout.PAGE_START);
        bottomPanel.add(recorridoLbl, BorderLayout.PAGE_START);
             
            bottomPanel.setBackground(miColor);
            bottomPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,5));
        add(bottomPanel, BorderLayout.SOUTH);
        graphPanel = new GraphPanel();
     graphPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,5));
        Color m = new Color(204,204,255);
            graphPanel.setBackground(m);
        add(graphPanel, BorderLayout.CENTER);
        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addVertexBtn) {
            graphPanel.setAddingEdge(false);
            addEdgeToggle.setSelected(false);
        } else if (e.getSource() == addEdgeBtn) {
            addEdgeToggle.setSelected(true);
            graphPanel.setAddingEdge(true);
        } else if (e.getSource() == searchBtn) {
            String sourceStr = sourceTxt.getText();
            String destStr = destTxt.getText();
            Point vertex = graphPanel.getVertexByLabel(sourceStr.charAt(0));
             Point vertex2 = graphPanel.getVertexByLabel(destStr.charAt(0));
                           if (vertex != null && vertex2 != null) {
                JOptionPane.showMessageDialog(this, "Vertices encontrados");
                try{
                   shortestPath = graphPanel.findShortestPath(vertex, vertex2);
                    String pathString = graphPanel.setPath(shortestPath);
                    recorridoLbl.setText(pathString);
               
                System.out.println("El camino más corto es: " + shortestPath);
                }catch(Exception ex){
                 JOptionPane.showMessageDialog(null, "No se puede encontrar el camino ingrese nuevos vertices");
                  sourceTxt.setText("");
                  destTxt.setText("");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vertice no encontrado Ingrese Nuevamente");
                sourceTxt.setText("");
                destTxt.setText("");
            }
        } else if(e.getSource() == camino1){
             String sourceStr = sourceTxt.getText();
            String destStr = destTxt.getText();
            Point vertex = graphPanel.getVertexByLabel(sourceStr.charAt(0));
             Point vertex2 = graphPanel.getVertexByLabel(destStr.charAt(0));
                 
           if (vertex != null && vertex2 != null) {
                try{
                   ArrayList<Point> secondShortestPath = graphPanel.findSecondShortestPath(vertex, vertex2 );
                         System.out.println("El segundo camino más corto es: " + secondShortestPath);
                    String pathString = graphPanel.setPath(secondShortestPath);
                    recorridoLbl.setText(pathString);
              
                }catch(Exception ex){
                 JOptionPane.showMessageDialog(null, "No se puede encontrar el camino ingrese nuevos vertices");
                  sourceTxt.setText("");
                  destTxt.setText("");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vertice no encontrado Ingrese Nuevamente");
                sourceTxt.setText("");
                destTxt.setText("");
            }
        }else if(e.getSource() == camino2){
        }else{
        }
    }
}


