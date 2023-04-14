/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1_mc2n_202201989;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
public class GraphGUI extends JFrame implements ActionListener {
    private JButton addVertexBtn, addEdgeBtn, searchBtn;
    private JLabel sourceLbl, destLbl,recorridoLbl,content;
    private JTextField sourceTxt, destTxt;
    private GraphPanel graphPanel;
    private JToggleButton addEdgeToggle;
     public static Point firstVertex = null;
      public static Point secondVertex = null;

     public GraphGUI() {
        super("Graph GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
         addVertexBtn = new JButton("AGREGAR VERTICE");
        addVertexBtn.addActionListener(this);
        topPanel.add(addVertexBtn);
        addEdgeToggle = new JToggleButton("AGREGAR ARISTAS");
           addEdgeToggle.addItemListener(new ItemListener() {
               @Override
               public void itemStateChanged(ItemEvent e) {
                   graphPanel.setAddingEdge(addEdgeToggle.isSelected());
               }
           });
         topPanel.add(addEdgeToggle);
        add(topPanel, BorderLayout.NORTH);
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        sourceLbl = new JLabel("Vertice Inicio:");
        sourceTxt = new JTextField(5);
        destLbl = new JLabel("Vertice Final:");
        destTxt = new JTextField(5);
        searchBtn = new JButton("Camino Simple");
        searchBtn.addActionListener(this);
        bottomPanel.add(sourceLbl, BorderLayout.WEST);
        bottomPanel.add(sourceTxt, BorderLayout.CENTER);
        bottomPanel.add(destLbl, BorderLayout.EAST);
        bottomPanel.add(destTxt, BorderLayout.LINE_END);
        bottomPanel.add(searchBtn, BorderLayout.SOUTH);
        bottomPanel.add(Box.createVerticalStrut(40)); 
        content= new JLabel("Recorrido:");
        recorridoLbl = new JLabel("");
        bottomPanel.add(content, BorderLayout.PAGE_START);
        bottomPanel.add(recorridoLbl, BorderLayout.PAGE_START);
        add(bottomPanel, BorderLayout.SOUTH);
      
        graphPanel = new GraphPanel();
        add(graphPanel, BorderLayout.CENTER);
        setVisible(true);
        
    }
   
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addVertexBtn) {
            System.out.println("hola");
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
               
                String pathString = graphPanel.setPath(graphPanel.findShortestPath(vertex, vertex2));
               recorridoLbl.setText(pathString);
                // podría llamar al algoritmo de búsqueda de caminos 
            // y mostrar los resultados en la interfaz gráfica.
                
            } else {
                JOptionPane.showMessageDialog(this, "Vertice no encontrado Ingrese Nuevamente");
                sourceTxt.setText("");
                destTxt.setText("");
            }
           
        } else {
        }
    }
}


