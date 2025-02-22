/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bstvisualization;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author CE191239 Nguyen Kim Bao Nguyen
 */
public class BSTPanel extends JPanel {
    private BSTTree bstTree;
    private final List<Circle> circles = new ArrayList<>();
    private static final int NODE_SPACING_X = 80;
    private static final int NODE_SPACING_Y = 100;

    public BSTPanel(BSTTree bstTree) {
        this.bstTree = bstTree;
        setPreferredSize(new Dimension(1000, 500));
    }

    public void drawCircle(BSTNode node) {
        circles.add(new Circle(node, 500, 250, 50, Color.white));
        repaint();
    }

    public void highlightCircle(int data, boolean found) {
        circles.stream()
                .filter(c -> c.text.equals(String.valueOf(data)))
                .findFirst()
                .ifPresent(c -> c.color = found ? Color.GREEN : Color.RED);
        repaint();
    }

    public void removeCircle(int data) {
        circles.removeIf(c -> c.text.equals(String.valueOf(data)));
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        circles.forEach(c -> c.draw(g2d));
        g2d.dispose();
    }

    // private void calculatePositions() {
    // int treeHeight = bstTree.getHeight(bstTree.root);
    // int canvasWidth = getWidth();

    // // Tính toán vị trí theo thuật toán Reingold-Tilford
    // calculateNodePositions(bstTree.root, 0, canvasWidth, 0, canvasWidth,
    // treeHeight);
    // }

    // private void calculateNodePositions(BSTNode node, int minX, int maxX, int
    // level, int parentX, int treeHeight) {
    // if (node == null)
    // return;

    // // Tính toán vị trí x
    // int x = (minX + maxX) / 2;
    // // Tính toán vị trí y dựa trên level
    // int y = (level + 1) * NODE_SPACING_Y;

    // node.circle = new Circle(node, x, y, 50, Color.white);

    // // Đệ quy cho node con
    // calculateNodePositions(node.left, minX, x, level + 1, x, treeHeight);
    // calculateNodePositions(node.right, x, maxX, level + 1, x, treeHeight);
    // }

    class Circle {
        int x;
        int y;
        final int size;
        Color color;
        BSTNode bstNode;
        String text;

        public Circle(BSTNode node, int x, int y, int size, Color color) {
            this.bstNode = node;
            this.x = x;
            this.y = y;
            this.size = size;
            this.color = color;
            text = node.data + "";
        }

        public boolean contains(Point p) {
            int centerX = x + size / 2;
            int centerY = y + size / 2;
            return Math.hypot(p.x - centerX, p.y - centerY) <= size / 2;
        }

        public void draw(Graphics2D g2d) {
            // Vẽ hình tròn
            g2d.setColor(color);
            g2d.fillOval(x, y, size, size);

            // Vẽ viền
            g2d.setColor(Color.BLACK);
            g2d.drawOval(x, y, size, size);

            // Vẽ text căn giữa
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(text);
            int textHeight = fm.getHeight();

            g2d.setColor(Color.BLACK);
            g2d.drawString(text,
                    x + (size - textWidth) / 2,
                    y + (size - textHeight) / 2 + fm.getAscent());
        }
    }
}
