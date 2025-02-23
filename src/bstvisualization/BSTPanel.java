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
    private final BSTTree bstTree;
    private final List<Circle> circles = new ArrayList<>();
    private final List<Line> lines = new ArrayList<>();
    private final int levelDefault = 5;

    public BSTPanel(BSTTree bstTree) {
        this.bstTree = bstTree;
        setPreferredSize(new Dimension(500, 500));
    }

    public void drawTree() {
        circles.clear(); // Xóa các hình tròn cũ
        lines.clear();

        List<BSTNode> nodes = bstTree.getNodesInBFSOrder();
        for (BSTNode node : nodes) {
            node.x = (node.index % 2 == 0)
                    ? (int) (node.parent.x + (levelDefault) * (45 / node.level))
                    : (int) (node.parent.x - (levelDefault) * (45 / node.level));
            node.y = node.parent.y + 70;
        }
        drawRoot();
        for (BSTNode node : nodes) {
            drawNode(node);
        }
        repaint();
    }

    private void drawRoot() {
        circles.add(new Circle(450, 100, 50, Color.WHITE, String.valueOf(bstTree.root.data)));
    }

    private void drawNode(BSTNode node) {
        if (node == null)
            return;
        lines.add(new Line(
                node.parent.x + 25, node.parent.y + 50, // Điểm cuối của parent (giữa đáy hình tròn)
                node.x + 25, node.y));// Điểm đầu của node (giữa đỉnh hình tròn
        // Thêm hình tròn vào danh sách
        circles.add(new Circle(node.x, node.y, 50, Color.WHITE, String.valueOf(node.data)));
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
        lines.forEach(line -> line.draw(g2d));
        circles.forEach(c -> c.draw(g2d));
        g2d.dispose();
    }

    class Circle {
        int x;
        int y;
        final int size;
        Color color;
        String text;

        public Circle(int x, int y, int size, Color color, String text) {
            this.x = x;
            this.y = y;
            this.size = size;
            this.color = color;
            this.text = text;
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

    class Line {
        int startX, startY, endX, endY;

        public Line(int startX, int startY, int endX, int endY) {
            this.startX = startX;
            this.startY = startY;
            this.endX = endX;
            this.endY = endY;
        }

        public void draw(Graphics2D g2d) {
            g2d.setColor(Color.BLACK);
            g2d.drawLine(startX, startY, endX, endY);

            // // Tính toán hướng của mũi tên (từ parent đến child)
            // double angle = Math.atan2(endY - startY, endX - startX);
            // int arrowLength = 10; // Độ dài mũi tên
            // int arrowAngle = 30; // Góc mũi tên

            // // Tính toán các điểm của mũi tên
            // double x1 = endX - arrowLength * Math.cos(angle -
            // Math.toRadians(arrowAngle));
            // double y1 = endY - arrowLength * Math.sin(angle -
            // Math.toRadians(arrowAngle));
            // double x2 = endX - arrowLength * Math.cos(angle +
            // Math.toRadians(arrowAngle));
            // double y2 = endY - arrowLength * Math.sin(angle +
            // Math.toRadians(arrowAngle));

            // // Vẽ hai cạnh của mũi tên
            // g2d.drawLine(endX, endY, (int) x1, (int) y1);
            // g2d.drawLine(endX, endY, (int) x2, (int) y2);
        }
    }
}
