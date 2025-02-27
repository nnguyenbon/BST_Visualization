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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author CE191239 Nguyen Kim Bao Nguyen
 */
public class BSTPanel extends JPanel {
    private final BSTTree bstTree;
    private final List<Circle> circles = new ArrayList<>();
    private final List<Line> lines = new ArrayList<>();
    private final int NODE_RADIUS = 36; // Bán kính node
    private final int HORIZONTAL_MARGIN = 50; // Lề ngang
    private final int VERTICAL_SPACING = 80; // Khoảng cách dọc giữa các tầng

    public BSTPanel(BSTTree bstTree) {
        this.bstTree = bstTree;
        setPreferredSize(new Dimension(1000, 490));
        setBackground(Color.white);
    }

    public void drawTree() {
        circles.clear();
        lines.clear();

        calculateNodePositions(bstTree.root, HORIZONTAL_MARGIN, getWidth() - HORIZONTAL_MARGIN, VERTICAL_SPACING);

        drawRoot();
        // Vẽ tất cả các node và đường nối
        for (BSTNode node : bstTree.getNodesInBFSOrder()) {
            drawNode(node);
        }
        repaint();
    }

    // Phương thức đệ quy tính toán vị trí các node con
    private void calculateNodePositions(BSTNode node, int left, int right, int y) {
        if (node == null)
            return;

        // Cập nhật tọa độ y của node
        node.y = y;

        // Tính toán khoảng trống cho node con
        int horizontalSpace = right - left;
        int childY = y + VERTICAL_SPACING;

        // Xử lý node con trái
        if (node.left != null) {
            node.left.x = left + horizontalSpace / 4; // Đặt node trái ở 1/4 khoảng trống
            node.left.parent = node;
            calculateNodePositions(node.left, left, left + horizontalSpace / 2, childY);
        }

        // Xử lý node con phải
        if (node.right != null) {
            node.right.x = right - horizontalSpace / 4; // Đặt node phải ở 3/4 khoảng trống
            node.right.parent = node;
            calculateNodePositions(node.right, left + horizontalSpace / 2, right, childY);
        }
    }

    private void drawNode(BSTNode node) {
        if (node == null || node.parent == null)
            return;

        // Vẽ đường nối từ node cha đến node hiện tại
        lines.add(new Line(
                node.parent.x + NODE_RADIUS / 2,
                node.parent.y + NODE_RADIUS,
                node.x + NODE_RADIUS / 2,
                node.y, node.parent.data, node.data));

        // Vẽ hình tròn
        circles.add(new Circle(node.x, node.y, NODE_RADIUS, Color.WHITE, String.valueOf(node.data)));
    }

    private void drawRoot() {
        circles.add(new Circle(bstTree.root.x, bstTree.root.y, 36, Color.WHITE,
                String.valueOf(bstTree.root.data)));
    }

    public void colorNode(int data, Color color) {
        for (Circle circle : circles) {
            if (circle.text.equals(String.valueOf(data))) {
                circle.color = color;
                break;
            }
        }
        repaint();
    }

    public void drawColor() {
        drawTree();
        List<BSTNode> path = new ArrayList<>(bstTree.path);

        if (path.isEmpty()) {
            return;
        }

        Timer timer = new Timer(1000, new ActionListener() {
            private int index = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (index >= path.size()) {
                    ((Timer) e.getSource()).stop();
                    return;
                }

                BSTNode currentNode = path.get(index);
                colorNode(currentNode.data, Color.yellow);
                repaint();
                index++;
            }
        });
        timer.start();
    }

    public void search(int data) {
        drawTree();
        bstTree.path.clear();
        bstTree.search(bstTree.root, data);
        List<BSTNode> path = new ArrayList<>(bstTree.path);

        if (path.isEmpty()) {
            return;
        }

        Timer timer = new Timer(1000, new ActionListener() {
            private int index = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (index >= path.size()) {
                    ((Timer) e.getSource()).stop();
                    return;
                }

                BSTNode currentNode = path.get(index);
                if (index == path.size() - 1) {

                    if (currentNode.data == data) {
                        colorNode(currentNode.data, Color.GREEN);
                    } else {
                        colorNode(currentNode.data, Color.RED);
                    }
                } else {
                    colorNode(currentNode.data, Color.YELLOW);
                }
                repaint();
                index++;
            }
        });
        timer.start();
    }

    public void removeCircle(int data) {
        circles.removeIf(c -> c.text.equals(String.valueOf(data)));

        lines.removeIf(line -> line.childData == data || line.parentData == data);
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
        int parentData;
        int childData;

        public Line(int startX, int startY, int endX, int endY, int parentData, int childData) {
            this.startX = startX;
            this.startY = startY;
            this.endX = endX;
            this.endY = endY;
            this.parentData = parentData;
            this.childData = childData;
        }

        public void draw(Graphics2D g2d) {
            g2d.setColor(Color.BLACK);
            g2d.drawLine(startX, startY, endX, endY);
        }
    }
}
