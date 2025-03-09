/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bstvisualization;

import java.awt.Dimension;
import java.io.File;
import java.io.FileWriter;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author CE191239 Nguyen Kim Bao Nguyen
 */
public class MainMenuBar extends JMenuBar implements ActionListener {

    private final JMenu fileMenu, traversalMenu, algorithmsMenu, helpMenu;
    private final JMenuItem saveItem, openItem, clearItem, exitItem;
    private final JMenuItem preOItem, inOItem, postOItem, bfsItem;
    private final JMenuItem maxItem, minItem, balanceItem;
    private final JMenuItem docsItem, aboutItem;
    private BSTTree tree;
    private ConfigurationPanel confi;

    public MainMenuBar(BSTTree tree, ConfigurationPanel confi) {
        this.tree = tree;
        this.confi = confi;
        UIManager.put("Menu.font", new Font("Arial", Font.BOLD, 16));
        UIManager.put("MenuItem.font", new Font("Arial", Font.PLAIN, 20));

        // File Menu
        fileMenu = new JMenu("File");
        saveItem = createMenuItem("Save", this);
        openItem = createMenuItem("Open", this);
        clearItem = createMenuItem("Clear", this);
        exitItem = createMenuItem("Exit", this);

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(clearItem);
        fileMenu.add(exitItem);

        // Traversal Menu
        traversalMenu = new JMenu("Traversal");
        preOItem = createMenuItem("Pre-Order", this);
        inOItem = createMenuItem("In-Order", this);
        postOItem = createMenuItem("Post-Order", this);
        bfsItem = createMenuItem("BFS", this);

        traversalMenu.add(preOItem);
        traversalMenu.add(inOItem);
        traversalMenu.add(postOItem);
        traversalMenu.add(bfsItem);

        // Algorithms Menu
        algorithmsMenu = new JMenu("Algorithms");
        maxItem = createMenuItem("Finding Max", this);
        minItem = createMenuItem("Finding Min", this);
        balanceItem = createMenuItem("Balance", this);

        algorithmsMenu.add(maxItem);
        algorithmsMenu.add(minItem);
        algorithmsMenu.add(balanceItem);

        // Help Menu
        helpMenu = new JMenu("Help");
        docsItem = createMenuItem("Docs", this);
        aboutItem = createMenuItem("About", this);

        helpMenu.add(docsItem);
        helpMenu.add(aboutItem);

        // Add Menus to MenuBar
        this.add(fileMenu);
        this.add(traversalMenu);
        this.add(algorithmsMenu);
        this.add(helpMenu);
    }

    private JMenuItem createMenuItem(String title, ActionListener listeners) {
        JMenuItem item = new JMenuItem(title);
        item.addActionListener(listeners);
        return item;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem) e.getSource();

        if (source == openItem) {
            openFile();
        } else if (source == saveItem) {
            saveFile();
        } else if (source == clearItem) {
            clearData();
        } else if (source == exitItem) {
            System.exit(0);
        } else if (source == preOItem) {
            preOrder();
        } else if (source == inOItem) {
            inOrder();
        } else if (source == postOItem) {
            postOrder();
        } else if (source == bfsItem) {
            bfs();
        } else if (source == maxItem) {
            max();
        } else if (source == minItem) {
            min();
        } else if (source == balanceItem) {
            balanceTree();
        } else if (source == docsItem) {
            docsItem();
        } else if (source == aboutItem) {
            aboutMe();
        }
    }

    public void openFile() {
        File parentDirectory = new File("").getAbsoluteFile().getParentFile();

        JFileChooser fileChooser = new JFileChooser(parentDirectory);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files (*.txt)", "txt"));

        int response = fileChooser.showOpenDialog(null);

        if (response == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            if (!file.getName().toLowerCase().endsWith(".txt")) {
                confi.showError("Only .txt files are allowed!");
                return;
            }

            clearData();
            System.out.println("Opened file: " + file.getAbsolutePath());

            try (Scanner sc = new Scanner(file)) {
                if (!sc.hasNextInt()) {
                    throw new IllegalArgumentException("Invalid file format: First line must be an integer (n).");
                }

                int n = sc.nextInt();
                int count = 0;

                while (sc.hasNextInt()) {
                    if (count >= n) {
                        throw new IllegalArgumentException("Invalid file format: More data than expected.");
                    }
                    int data = sc.nextInt();
                    confi.handleAddNode(data);
                    count++;
                }

                if (count < n) {
                    throw new IllegalArgumentException("Invalid file format: Missing data for nodes.");
                }

            } catch (Exception e) {
                confi.showError("Error reading file - " + e.getMessage());
            }
        }
    }

    private void saveFile() {
        try {
            if (tree.isEmpty()) {
                throw new Exception("BST is empty");
            }

            String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            int len = 8;

            Random random = new Random();
            StringBuilder sb = new StringBuilder(len);
            sb.append("tree_");

            for (int i = 0; i < len; i++) {
                int index = random.nextInt(CHARACTERS.length());
                sb.append(CHARACTERS.charAt(index));
            }

            sb.append(".txt");

            File file = new File(sb.toString());

            try (FileWriter out = new FileWriter(file)) {
                out.write(tree.countNodes() + "\n");
                tree.setResult("");
                tree.BFS();
                
                String treeStructure = tree.getResult();
                treeStructure = treeStructure.replaceAll("->", " ");
                System.out.println("result: " + treeStructure);

                out.write(treeStructure + "\n");
            }
        } catch (IOException e) {
            confi.showError("File write error: " + e.getMessage());
        } catch (Exception e) {
            confi.showError(e.getMessage());
        }
    }

    private void clearData() {
        confi.clearTree();
    }

    private void preOrder() {
        confi.resetFunction();
        confi.preOrderResult();
    }

    private void postOrder() {
        confi.resetFunction();
        confi.postOrderResult();
    }

    private void inOrder() {
        confi.resetFunction();
        confi.inOrderResult();
    }

    private void bfs() {
        confi.resetFunction();
        confi.bfsResult();
    }

    private void balanceTree() {
        confi.resetFunction();
        confi.balanceTree();
    }

    private void max() {
        confi.resetFunction();
        confi.findMax();
    }

    private void min() {
        confi.resetFunction();
        confi.findMin();
    }

    private void docsItem() {
        String userGuide = "User Guide\n\n"
                + "I. Menu Functions\n"
                + "------------------\n"
                + "1. File\n"
                + "   - Open: Open a saved tree data file.\n"
                + "   - Save: Save the current tree to a file.\n"
                + "   - Clear: Delete all tree data.\n"
                + "   - Exit: Exit the program.\n\n"
                + "2. Traversal\n"
                + "   - PreOrder: Traverse the tree in preorder.\n"
                + "   - InOrder: Traverse the tree in inorder.\n"
                + "   - PostOrder: Traverse the tree in postorder.\n"
                + "   - BFS: Traverse the tree using breadth-first search.\n\n"
                + "3. Algorithms\n"
                + "   - FindMax: Find the maximum value in the tree.\n"
                + "   - FindMin: Find the minimum value in the tree.\n"
                + "   - Balance: Balance the tree.\n\n"
                + "4. Help\n"
                + "   - Docs: Display the user guide.\n"
                + "   - About: Show program information.\n\n"
                + "II. Button Functions\n"
                + "----------------------\n"
                + "1. Add Node: Enter an integer value to add a node.\n"
                + "2. Add Random Node: Add a random value between 0 and 100.\n"
                + "3. Search Node: Find and highlight a node in the tree.\n"
                + "4. Remove Node: Delete a node if it exists.\n";

        JTextArea textArea = new javax.swing.JTextArea(userGuide);
        textArea.setFont(new java.awt.Font("Arial", Font.PLAIN, 20));
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 400));

        JOptionPane.showMessageDialog(null, scrollPane, "User Guide", JOptionPane.INFORMATION_MESSAGE);
    }

    private void aboutMe() {
        String introduce = "CSD201 - SE1905 - Group 3\n"
                + "Mentor: Le Thi Phuong Dung\n"
                + "Member: \n"
                + "1. Nguyen Kim Bao Nguyen\n"
                + "2. Do Duc Hai\n"
                + "3. Truong Khai Toan\n"
                + "4. Truong Doan Minh Phuc\n"
                + "5. Duong Nguyen Kim Chi\n"
                + "6. Pham Nguyen Khanh";

        JOptionPane.showMessageDialog(null, introduce, "About Us", JOptionPane.INFORMATION_MESSAGE);
    }
}
