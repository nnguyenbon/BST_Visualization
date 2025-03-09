/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bstvisualization;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class BSTNode {

    int data;
    int x;
    int y;
    int size = 0;
    int level;
    int count;
    BSTNode left;
    BSTNode right;
    BSTNode parent;

    public BSTNode() {
    }

    public BSTNode(int data, int x, int y, int level) {
        this.data = data;
        this.size = size++;
        this.level = level;
        this.left = null;
        this.right = null;
        this.parent = null;
        this.count = 1;
        this.x = x;
        this.y = y;
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }

    public boolean isRoot() {
        return parent == null;
    }

    public boolean isInside() {
        return !isRoot() && !isLeaf();
    }

    public boolean isLeftChild() {
        return parent != null && parent.left == this;
    }

    public boolean isRightChild() {
        return parent != null && parent.right == this;
    }

    public boolean hasLeftChild() {
        return left != null;
    }

    public boolean hasRightChild() {
        return right != null;
    }

    public boolean hasOneChild() {
        return (left != null && right == null) || (left == null && right != null);
    }

    public boolean hasTwoChild() {
        return left != null && right != null;
    }

    public boolean hasLeftChildOnly() {
        return left != null && right == null;
    }

    public boolean hasRightChildOnly() {
        return left == null && right != null;
    }

    public void setLeft(BSTNode left) {
        this.left = left;

        if (left != null) {
            this.left.setParent(this);
        }
    }

    public void setRight(BSTNode right) {
        this.right = right;

        if (right != null) {
            this.right.setParent(this);
        }
    }

    public void setParent(BSTNode parent) {
        this.parent = parent;
        this.level = this.parent.level + 1;
    }

    public int getLevel() {
        return level;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public BSTNode findMin() {
        BSTNode current = this;
        while (current.hasLeftChild()) {
            current = current.left;
        }
        return current;
    }

    public BSTNode findMax() {
        BSTNode current = this;
        while (current.hasRightChild()) {
            current = current.right;
        }
        return current;
    }

    //A node's successor is the node with a greater value than it immediately 
    //after it in the Inorder Traversal (LNR: Left - Node - Right) order.
    public BSTNode getSuccessor() {
        if (this.right != null) {
            return this.right.findMin();
        }

        BSTNode current = this;

        //Check if parent is not null and current node is in the right of parent
        while (this.parent != null && current == current.parent.right) {
            current = current.parent;
        }

        return current.parent;
    }

    public BSTNode Predecessor() {
        if (this.left != null) {
            return this.left.findMax();
        }

        BSTNode current = this;

        //Check if parent is not null and current node is in the left of parent
        while (this.parent != null && current == current.parent.left) {
            current = current.parent;
        }

        return current.parent;
    }

    //Get height due to node
    public int getHeight() {
        int leftHeight = (left == null) ? 0 : left.getHeight();
        int rightHeight = (right == null) ? 0 : right.getHeight();
        return Math.max(leftHeight, rightHeight) + 1;
    }

    public boolean removeLeafChild(BSTNode node) {
        if (node == null) {
            return false;
        }
        if (node.isLeaf()) {
            if (this.hasLeftChild()) { // check node is left child to remove
                if (this.left.data == node.data) {
                    this.setLeft(null);
                    return true;
                }
            }
            if (this.hasRightChild()) { // check node is right child to remove
                if (this.right.data == node.data) {
                    this.setRight(null);
                    return true;
                }
            }
        }
        return false;
    }

    public void drawNode(Graphics g) {
        int diameter = 40;

        // Vẽ hình tròn màu trắng
        g.setColor(Color.WHITE);
        g.fillOval(x - diameter / 2, y - diameter / 2, diameter, diameter);

        // Vẽ viền màu đen
        g.setColor(Color.BLACK);
        g.drawOval(x - diameter / 2, y - diameter / 2, diameter, diameter);

        // Vẽ số bên trong
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        String text = String.valueOf(data);
        FontMetrics fm = g.getFontMetrics();
        int textX = x - fm.stringWidth(text) / 2;
        int textY = y + fm.getAscent() / 2;
        g.drawString(text, textX, textY);
    }
}
