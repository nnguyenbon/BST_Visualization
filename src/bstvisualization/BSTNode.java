/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bstvisualization;

public class BSTNode {

    int data;
    int size;
    int level;
    int count;
    int index;
    int x;
    int y;
    BSTNode left;
    BSTNode right;
    BSTNode parent;

    public BSTNode() {
    }

    public BSTNode(int data) {
        this.data = data;
        this.size = 1;
        this.level = 0;
        this.left = null;
        this.right = null;
        this.parent = null;
        this.count = 1;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}