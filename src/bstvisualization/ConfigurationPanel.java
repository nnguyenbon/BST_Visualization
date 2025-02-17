/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bstvisualization;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author CE191239 Nguyen Kim Bao Nguyen
 */
public class ConfigurationPanel extends JPanel {

    public ConfigurationPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setPreferredSize(new Dimension(1000, 200));

        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK), 
                "Configuration", 
                TitledBorder.LEFT, 
                TitledBorder.TOP, 
                new Font("Arial", Font.BOLD, 16),
                Color.BLUE 
        ));
        
        JTextField textField = new JTextField(20); 
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
            textField.getBorder(),
            new EmptyBorder(10, 15, 10, 15) // Padding (top, left, bottom, right)
        ));
        add(textField);

        JButton addButton = new JButton("Add Node");
        JButton randomButton = new JButton("Random");
        JButton searchButton = new JButton("Search");
        JButton removeButton = new JButton("Remove");
    }
}
