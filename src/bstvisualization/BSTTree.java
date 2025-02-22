/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bstvisualization;

/**
 *
 * @author CE191239 Nguyen Kim Bao Nguyen
 */
public class BSTTree {
    BSTNode root;

    public BSTTree() {
    }

    public BSTNode createNode(int data) {
        if (root == null) {
            root = new BSTNode();
            root.level = 0;
            root.data = data;
            return root;
        }
        BSTNode newNode = new BSTNode();
        newNode.data = data;
        int level = 1;
        return insert(newNode, data, level);
    }

    // Dung de quy them vao mot nut
    private BSTNode insert(BSTNode node, int data, int level) {
        if (data < node.data) {
            node.left = insert(node.left, data, level++);
        } else if (data > node.data) {
            node.right = insert(node.right, data, level++);
        } else {
            node.count++;
        }
        return node;
    }

    // Dung de quy tim kiem muc tieu
    public BSTNode search(BSTNode currentNode, int target) {
        if (currentNode == null) {
            return null;
        } else if (currentNode.data == target) {
            return currentNode;
        } else if (currentNode.data < target) {
            return search(currentNode.left, target);
        } else {
            return search(currentNode.right, target);
        }
    }

    public BSTNode delete(BSTNode currentNode, int target) {
        if (currentNode == null) {
            return null;
        }

        if (currentNode.data < target) {
            return delete(currentNode.left, target);
        } else if (currentNode.data > target) {
            return delete(currentNode.right, target);
        } else {
            if (currentNode.left == null) {
                return currentNode.right;
            } else if (currentNode.right == null) {
                return currentNode.left;
            }
            // Tim nut nho nhat o phia ben phai
            currentNode.data = minValueNode(currentNode.right).data;
            currentNode.right = delete(currentNode.right, target);
        }
        return currentNode;
    }

    // Tim nut nho nhat
    private BSTNode minValueNode(BSTNode node) {
        BSTNode current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    public int randomNode() {
        return (int) (Math.random() * 101);
    }
}
