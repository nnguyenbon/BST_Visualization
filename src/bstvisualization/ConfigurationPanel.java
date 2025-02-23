/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bstvisualization;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 *
 * @author CE191239 Nguyen Kim Bao Nguyen
 */
public class ConfigurationPanel extends JPanel {

    private JLabel errorLabel;
    private JTextField textField;
    private JTextField resultField;
    private final BSTPanel bstPanel;
    private final BSTTree bstTree;

    public ConfigurationPanel(BSTTree bstTree, BSTPanel bstPanel) {
        this.bstTree = bstTree;
        this.bstPanel = bstPanel;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1000, 200));
        setBorder(createTitledBorder());

        // Tạo UI
        add(createInputPanel(), BorderLayout.NORTH);
        add(createResultPanel(), BorderLayout.CENTER);
        add(createErrorPanel(), BorderLayout.SOUTH);
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        textField = createTextField();
        panel.add(textField);

        JButton addButton = createButton("Add Node", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAction(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer data) {
                        handleAddNode(data);
                    }
                });
            }
        });

        JButton randomButton = createButton("Random", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAction(new Runnable() {
                    @Override
                    public void run() {
                        handleRandomNode();
                    }
                });
            }
        });

        JButton searchButton = createButton("Search", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAction(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer data) {
                        handleSearchNode(data);
                    }
                });
            }
        });

        JButton removeButton = createButton("Remove", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAction(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer data) {
                        handleRemoveNode(data);
                    }
                });
            }
        });

        panel.add(addButton);
        panel.add(randomButton);
        panel.add(searchButton);
        panel.add(removeButton);

        return panel;
    }

    private JPanel createResultPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        resultField = new JTextField(20);
        resultField.setSize(50, 30);
        resultField.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(resultField);
        return panel;
    }

    private JPanel createErrorPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        errorLabel = new JLabel(" ");
        errorLabel.setForeground(Color.RED);
        panel.add(errorLabel);
        return panel;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField(20);
        field.setPreferredSize(new Dimension(200, 30));
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        return field;
    }

    private JButton createButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.setBackground(Color.white);
        button.setFocusable(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(120, 40));
        button.addActionListener(action);
        return button;
    }

    private TitledBorder createTitledBorder() {
        return BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Configuration",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 16),
                Color.BLUE);
    }

    // Consumer<Integer> được dùng để đơn giản hóa việc gọi các hàm xử lý nút (Add
    // Node, Random, Search, Remove)
    private void handleAction(Consumer<Integer> action) {
        int data = validInput();
        if (data != -1) {
            action.accept(data);
        }
    }

    private void handleAction(Runnable action) {
        action.run();
    }

    private int validInput() {
        String input = textField.getText().trim();
        String regex = "-?[0-9]+";

        if (input.isEmpty()) {
            showError("Input is empty!");
            return -1;
        }

        if (!input.matches(regex)) {
            showError("Only enter a positive number!");
            return -1;
        }

        resetError();
        return Integer.parseInt(input);
    }

    private void showError(String message) {
        if (errorLabel != null) {
            errorLabel.setText(message);
            textField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        }
    }

    private void resetError() {
        if (errorLabel != null) {
            errorLabel.setText(" ");
            textField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        }
    }

    private void handleAddNode(int data) {
        BSTNode node = bstTree.createNode(data);
        // bstPanel.drawCircle(node);
        bstPanel.drawTree();
        bstTree.inOrder(bstTree.root);
        System.out.println("Adding node: " + data);
    }

    private void handleRandomNode() {
        int data = bstTree.randomNode();
        System.out.println("Random node: " + data);
        textField.setText(data + "");
    }

    private void handleSearchNode(int data) {
        Boolean found = true;
        if (bstTree.search(bstTree.root, data) == null) {
            found = false;
        }
        bstPanel.highlightCircle(data, found);
        System.out.println("Searching node: " + data);
    }

    private void handleRemoveNode(int data) {
        bstTree.delete(bstTree.root, data);
        bstPanel.removeCircle(data);
        System.out.println("Removing node: " + data);
    }
}
