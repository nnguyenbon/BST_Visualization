/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bstvisualization;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author CE191239 Nguyen Kim Bao Nguyen, CE190173 Truong Khai Toan
 */
public class MainMenuBar implements ActionListener {

    private final JMenuBar menuBar;
    private final JMenu fileMenu, traversalMenu, algorithmsMenu, helpMenu;
    private final JMenuItem saveItem, openItem, clearItem, exitItem;
    private final JMenuItem preOItem, inOItem, postOItem;
    private final JMenuItem bfsItem, dfsItem;
    private final JMenuItem docsItem, aboutItem;
    private BSTTree bstTree;
    private BSTPanel bstPanel;

    public MainMenuBar(BSTTree bstTree, BSTPanel bstPanel) {
        this.bstPanel = bstPanel;
        this.bstTree = bstTree;
        UIManager.put("Menu.font", new Font("Arial", Font.BOLD, 16));
        UIManager.put("MenuItem.font", new Font("Arial", Font.PLAIN, 20));

        menuBar = new JMenuBar();

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

        traversalMenu.add(preOItem);
        traversalMenu.add(inOItem);
        traversalMenu.add(postOItem);

        // Algorithms Menu
        algorithmsMenu = new JMenu("Algorithms");
        bfsItem = createMenuItem("BFS", this);
        dfsItem = createMenuItem("DFS", this);

        algorithmsMenu.add(bfsItem);
        algorithmsMenu.add(dfsItem);

        // Help Menu
        helpMenu = new JMenu("Help");
        docsItem = createMenuItem("Documentation", this);
        aboutItem = createMenuItem("About", this);

        helpMenu.add(docsItem);
        helpMenu.add(aboutItem);

        // Add Menus to MenuBar
        menuBar.add(fileMenu);
        menuBar.add(traversalMenu);
        menuBar.add(algorithmsMenu);
        menuBar.add(helpMenu);
    }

    public JMenuBar getMenuBar() {
        return menuBar;
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
            bstPanel.drawTree();
            System.out.println("Open file...");
        } else if (source == saveItem) {
            saveFile();
            System.out.println("Save file...");
        } else if (source == clearItem) {
            bstTree.clearData();
            bstPanel.clearData();
            System.out.println("Clear data...");
        } else if (source == exitItem) {
            System.exit(0);
        } else if (source == preOItem) {
            bstTree.path.clear();
            bstTree.preOrder(bstTree.root);
            bstPanel.drawColor();
            System.out.println("Pre-Order Traversal...");
        } else if (source == inOItem) {
            bstTree.path.clear();
            bstTree.inOrder(bstTree.root);
            bstPanel.drawColor();
            System.out.println("In-Order Traversal...");
        } else if (source == postOItem) {
            bstTree.path.clear();
            bstTree.posOrder(bstTree.root);
            bstPanel.drawColor();
            System.out.println("Post-Order Traversal...");
        } else if (source == bfsItem) {
            bstTree.path.clear();
            bstTree.path = bstTree.getNodesInBFSOrder();
            bstPanel.drawColor();
            System.out.println("BFS Algorithm...");
        } else if (source == dfsItem) {
            bstTree.path.clear();
            bstTree.preOrder(bstTree.root);
            bstPanel.drawColor();
            System.out.println("DFS Algorithm...");
        } else if (source == docsItem) {
            System.out.println("Open Documentation...");
        } else if (source == aboutItem) {
            System.out.println("About me");
        }
    }

    private void saveFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save BST Data");
        fileChooser.setSelectedFile(new File("bst_data.txt"));

        int userChoice = fileChooser.showSaveDialog(null);

        if (userChoice == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (FileWriter writer = new FileWriter(selectedFile)) {
                String data = bstTree.serialize();
                writer.write(data);
                System.out.println("Đã lưu file thành công: " + selectedFile.getAbsolutePath());
            } catch (IOException ex) {
                System.err.println("Lỗi khi lưu file: " + ex.getMessage());
            }
        }
    }

    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Open BST Data");

        int userChoice = fileChooser.showOpenDialog(null);

        if (userChoice == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                }

                bstTree.deserialize(content.toString());
                System.out.println("Đã mở file thành công: " + selectedFile.getName());

            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "File không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Lỗi đọc file!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Dữ liệu không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
