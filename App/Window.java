/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;

/**
 *
 * @author ahaas
 */
public class Window extends JFrame{
    
    private JPanel mainPanel;
    
    public Window(int w, int l, int nbThreads) throws IOException{
        super("JuliaSet");
        getContentPane().setBackground( Color.WHITE );
        //Add a new JPanel
        this.mainPanel = new JPanel(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(w, l);
        //JuliaSet
        setupWindow(w, l);
        PanelComponent panel = new PanelComponent("Julia", w, l, nbThreads);
        this.setContentPane(panel);
        this.setVisible(true);
    }
    
    //JuliaSet
    private void setupWindow(int w, int l){
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(w, l));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                dispose();
            }
        }); 
    }

    public void setVisibility(boolean isVisible){
        this.setVisible(isVisible);
    }
}
