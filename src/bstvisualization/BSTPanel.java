/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bstvisualization;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author CE191239 Nguyen Kim Bao Nguyen
 */
public class BSTPanel extends JPanel {

    public static final int bstPanelWidth = 1600;
    public static final int bstPanelHeight = 550;
    private BSTTree tree;

    public BSTPanel(BSTTree tree) {
        this.tree = tree;
        setPreferredSize(new Dimension(bstPanelWidth, bstPanelHeight));
        setBackground(Color.white);
    }

    public BSTTree getTree() {
        return tree;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawTreeStructure(g, tree.root);

        if (ConfigurationPanel.isSearched) {
            ArrayList<BSTNode> path = tree.getPath();
            drawHighlightedPath(g, path);
            ConfigurationPanel.isSearched = false;
        }

        if (ConfigurationPanel.isFound) {
            if (tree.getResult().equalsIgnoreCase(String.valueOf(tree.root.findMax().data))) {
                highlightNode(g, tree.root.findMax());
            } else {
                highlightNode(g, tree.root.findMin());
            }
            ConfigurationPanel.isFound = false;
        }
    }

    private void drawTreeStructure(Graphics g, BSTNode node) {
        if (node == null) {
            return;
        }

        drawLines(g, node);
        drawNode(g, node);
        drawTreeStructure(g, node.left);
        drawTreeStructure(g, node.right);
    }

    private void drawNode(Graphics g, BSTNode node) {
        if (node != null) {
            node.drawNode(g);
        }
    }

    private void drawHighlightedPath(Graphics g, ArrayList<BSTNode> path) {
        for (BSTNode node : path) {
            highlightNode(g, node);
        }
    }

    private void highlightNode(Graphics g, BSTNode node) {
        if (node == null) {
            return;
        }

        int diameter = 40;
        g.setColor(Color.RED);
        g.fillOval(node.x - diameter / 2, node.y - diameter / 2, diameter, diameter);

        g.setColor(Color.YELLOW);
        g.drawOval(node.x - diameter / 2, node.y - diameter / 2, diameter, diameter);

        g.setFont(new Font("Arial", Font.BOLD, 14));
        String text = String.valueOf(node.data);
        FontMetrics fm = g.getFontMetrics();
        int textX = node.x - fm.stringWidth(text) / 2;
        int textY = node.y + fm.getAscent() / 2;
        g.drawString(text, textX, textY);
    }

    private void drawLines(Graphics g, BSTNode node) {
        if (node == null) {
            return;
        }

        g.setColor(Color.BLACK); // Màu mặc định cho đường nối

        if (node.left != null) {
            g.drawLine(node.x, node.y + 25, node.left.x, node.left.y - 25);
        }

        if (node.right != null) {
            g.drawLine(node.x, node.y + 25, node.right.x, node.right.y - 25);
        }
    }
}
