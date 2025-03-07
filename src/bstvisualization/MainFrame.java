/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bstvisualization;

import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 *
 * @author CE191239 Nguyen Kim Bao Nguyen
 */
public class MainFrame extends JFrame {
    MainMenuBar mainMenu = new MainMenuBar();

    public MainFrame() {
        setTitle("BST Visualizer");
        setSize(BstVisualization.WINDOW_SIZE_X, BstVisualization.WINDOW_SIZE_Y);  
        setLocationRelativeTo(null);  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(mainMenu.getMenuBar()); 
        add(new ConfigurationPanel(), BorderLayout.NORTH); 
        add(new BSTPanel(), BorderLayout.SOUTH);
        setVisible(true);
    }
}
