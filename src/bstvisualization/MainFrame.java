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

    //Window size
    public static final int WINDOW_SIZE_X = 1600;
    public static final int WINDOW_SIZE_Y = 700;

    public MainFrame() {
        setTitle("BST Visualizer");
        setSize(WINDOW_SIZE_X, WINDOW_SIZE_Y);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BSTTree tree = new BSTTree(BSTPanel.bstPanelWidth, BSTPanel.bstPanelHeight);
        BSTPanel bstPanel = new BSTPanel(tree);
        
        ConfigurationPanel configPanel = new ConfigurationPanel(tree, bstPanel);
        MainMenuBar menuBar = new MainMenuBar(tree, configPanel);
        setJMenuBar(menuBar);

        add(configPanel, BorderLayout.NORTH);
        add(bstPanel, BorderLayout.CENTER);
        setVisible(true);
    }
}
