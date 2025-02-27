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
    BSTTree bstTree = new BSTTree();
    BSTPanel bstPanel = new BSTPanel(bstTree);
    MainMenuBar mainMenu = new MainMenuBar(bstTree, bstPanel);

    public MainFrame() {
        setTitle("BST Visualizer");
        setSize(BstVisualization.WINDOW_SIZE_X, BstVisualization.WINDOW_SIZE_Y);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(mainMenu.getMenuBar());
        add(new ConfigurationPanel(bstTree, bstPanel), BorderLayout.NORTH);
        add(bstPanel, BorderLayout.SOUTH, Integer.valueOf(0));
        setVisible(true);
    }
}
