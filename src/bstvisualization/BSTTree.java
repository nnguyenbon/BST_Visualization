/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bstvisualization;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author CE191239 Nguyen Kim Bao Nguyen
 */
public class BSTTree {
    BSTNode root;

    public BSTTree() {
    }

    public static BSTNode insert(BSTNode node, int data) {
        if (node == null) {
            return new BSTNode(data);
        } else {
            if (data < node.data) {
                node.left = insert(node.left, data);
            } else if (data > node.data) {
                node.right = insert(node.right, data);
            }
        }
        return node;
    }

    public BSTNode createNode(int data) {
        if (root == null) {
            root = new BSTNode();
            root.level = 0;
            root.data = data;
            root.parent = null;
            root.index = 0;
            root.x = 450;
            root.y = 100;
            return root;
        }
        return insert(root, data, 0);
    }

    // Dung de quy them vao mot nut
    private BSTNode insert(BSTNode node, int data, int level) {
        if (node == null) {
            BSTNode newNode = new BSTNode();
            newNode.data = data;
            newNode.level = level;
            return newNode;
        }

        if (data < node.data) {
            BSTNode leftChild = insert(node.left, data, level + 1);
            node.left = leftChild;
            leftChild.parent = node;
            leftChild.index = node.index * 2 + 1;
        } else if (data > node.data) {
            BSTNode rightChild = insert(node.right, data, level + 1);
            node.right = rightChild;
            rightChild.parent = node;
            rightChild.index = node.index * 2 + 2;
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

    void inOrder(BSTNode root) {
        if (root == null)
            return;
        inOrder(root.left);
        System.out.println(root.data);
        inOrder(root.right);
    }

    void preOrder(BSTNode root) {
        if (root == null)
            return;
        System.out.println(root.data);
        inOrder(root.left);
        inOrder(root.right);
    }

    void posOrder(BSTNode root) {
        if (root == null)
            return;
        inOrder(root.left);
        inOrder(root.right);
        System.out.println(root.data);
    }

    public List<BSTNode> getNodesInBFSOrder() {
        List<BSTNode> nodes = new ArrayList<>();
        Queue<BSTNode> queue = new LinkedList<>();
        if (root != null)
            queue.add(root);

        while (!queue.isEmpty()) {
            BSTNode current = queue.poll();
            nodes.add(current);
            if (current.left != null)
                queue.add(current.left);
            if (current.right != null)
                queue.add(current.right);
        }
        nodes.remove(0);
        return nodes;
    }

    // Tính chiều cao cây
    public int getHeight(BSTNode node) {
        if (node == null)
            return 0;
        return 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }
}
