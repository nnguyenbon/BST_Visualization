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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author CE191239 Nguyen Kim Bao Nguyen
 */
public class ConfigurationPanel extends JPanel {

    private JLabel errorLabel;
    private JTextField textField;
    private JTextField resultField;
    private BSTTree tree;
    private BSTPanel bstPanel;
    public static boolean isFound;
    public static boolean isSearched;
    private MainFrame mainFrame;

    public ConfigurationPanel(BSTTree tree, BSTPanel bstPanel) {
        this.tree = tree;
        this.bstPanel = bstPanel;

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1000, 150));
        setBorder(createTitledBorder());

        // Tạo UI
        add(createInputPanel(), BorderLayout.NORTH);
        add(createResultPanel(), BorderLayout.CENTER);
        add(createErrorPanel(), BorderLayout.SOUTH);
    }

    public JTextField getResultField() {
        return resultField;
    }

    public void setResultField(JTextField resultField) {
        this.resultField = resultField;
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        textField = createTextField();
        panel.add(textField);
        
        textField.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleAction(data -> handleAddNode(data));
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
               
            }
        });

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
        resultField.setPreferredSize(new Dimension(200, 30));
        resultField.setMinimumSize(new Dimension(200, 30));
        resultField.setMaximumSize(new Dimension(200, 30));
        resultField.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(resultField);
        return panel;
    }

    private void resetResultField() {
        resultField.setText("");
    }

    private JPanel createErrorPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        errorLabel = new JLabel(" ");
        errorLabel.setForeground(Color.RED);

        // Đặt kích thước cố định cho errorLabel
        errorLabel.setPreferredSize(new Dimension(700, 20));
        errorLabel.setMinimumSize(new Dimension(700, 20));
        errorLabel.setMaximumSize(new Dimension(700, 20));

        panel.add(errorLabel);

        // Đặt kích thước cố định cho panel
        panel.setPreferredSize(new Dimension(720, 30));
        panel.setMinimumSize(new Dimension(720, 30));
        panel.setMaximumSize(new Dimension(720, 30));

        return panel;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField(20);
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        return field;
    }

    private void resetTextField() {
        textField.setText("");
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
                Color.BLUE
        );
    }

    // Consumer<Integer> được dùng để đơn giản hóa việc gọi các hàm xử lý nút (Add Node, Random, Search, Remove)
    private void handleAction(Consumer<Integer> action) {
        int data = validInput();
        if (data != -1) {
            action.accept(data);
        } else {
            resetTextField();
        }
    }

    private void handleAction(Runnable action) {
        action.run();
    }

    private int validInput() {
        if (textField == null) {
            System.out.println("textField is null!");
            return -1;
        }

        String input = textField.getText().trim();
        if (input.isEmpty()) {
            showError("Input is empty!");
            return -1;
        }

        if (!input.matches("[0-9]+")) {
            showError("\"" + input + "\" Only enter a positive number!");
            return -1;
        }

        resetError();
        return Integer.parseInt(input);
    }

    public void showError(String message) {
        if (errorLabel != null) {
            errorLabel.setText(message);
        }
    }

    private void resetError() {
        if (errorLabel != null) {
            errorLabel.setText(" ");
        }
    }

    public void resetFunction() {
        resetTextField();
        resetError();
        resetResultField();
        tree.resetResult();
    }

    //Dang loi
    public void handleAddNode(int data) {
        resetFunction();
        tree.addNode(data);
        if (tree.cannotAdd) {
            showError(tree.getResult());
        }
        bstPanel.repaint();
    }

    private void handleRandomNode() {
        resetFunction();
        int randomData = (int) (Math.random() * 101);
        handleAddNode(randomData);
        resultField.setText(String.valueOf(randomData));
    }

    private void handleSearchNode(int data) {
        if (tree.isEmpty()) {
            showError("Add data first");
            return;
        }
        resetFunction();
        tree.findNode(data);

        bstPanel.repaint();
        resultField.setText(tree.getResult());
    }

    private void handleRemoveNode(int data) {
        if (tree.isEmpty()) {
            showError("Add data first");
            return;
        }
        resetFunction();
        tree.findNode(data);

        if (isSearched) {
            int choice = JOptionPane.showOptionDialog(bstPanel, "Do you want to remove " + data + " ?",
                    "Remove cofirmation", JOptionPane.YES_NO_OPTION, JOptionPane.CLOSED_OPTION,
                    null, null, JOptionPane.YES_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                tree.removeNode(data);
                resultField.setText("Remove Succesfully");
            }
        } else {
            resultField.setText("Data is not exist");
        }

        isSearched = false;
        bstPanel.repaint();
    }

    public void preOrderResult() {
        if (tree.isEmpty()) {
            showError("Add data first");
            return;
        }
        tree.preOrder();
        resultField.setText(tree.getResult());
        bstPanel.repaint();
    }

    public void postOrderResult() {
        if (tree.isEmpty()) {
            showError("Add data first");
            return;
        }
        tree.postOrder();
        resultField.setText(tree.getResult());
        bstPanel.repaint();
    }

    public void inOrderResult() {
        if (tree.isEmpty()) {
            showError("Add data first");
            return;
        }
        tree.inOrder();
        resultField.setText(tree.getResult());
        bstPanel.repaint();
    }

    public void bfsResult() {
        if (tree.isEmpty()) {
            showError("Add data first");
            return;
        }
        tree.BFS();
        resultField.setText(tree.getResult());
        bstPanel.repaint();
    }

    public void findMax() {
        if (tree.isEmpty()) {
            showError("Add data first");
            return;
        }
        isFound = true;
        tree.setResult(String.valueOf(tree.root.findMax().data));
        resultField.setText(tree.getResult());
        bstPanel.repaint();
    }

    public void findMin() {
        if (tree.isEmpty()) {
            showError("Add data first");
            return;
        }
        isFound = true;
        tree.setResult(String.valueOf(tree.root.findMin().data));
        resultField.setText(tree.getResult());
        bstPanel.repaint();
    }

    public void clearTree() {
        if (tree.isEmpty()) {
            showError("Tree is already empty");
            return;
        }

        tree.deleteTree();
        bstPanel.repaint();
    }

    public void balanceTree() {
        if (tree.isEmpty()) {
            showError("Tree is already empty");
            return;
        }

        tree.balanceTree();
        bstPanel.repaint();
    }
}
