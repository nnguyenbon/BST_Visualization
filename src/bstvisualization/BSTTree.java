/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bstvisualization;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author CE191239 Nguyen Kim Bao Nguyen
 */
public class BSTTree implements Serializable {
    BSTNode root;
    ArrayList<BSTNode> path;

    public BSTTree() {
        path = new ArrayList<>();
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
            root.x = 473;
            root.y = 25;
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
        }
        path.add(currentNode);
        if (currentNode.data == target) {
            return currentNode;
        } else if (currentNode.data < target) {
            return search(currentNode.right, target);
        } else {
            return search(currentNode.left, target);
        }
    }

    public BSTNode delete(BSTNode currentNode, int target) {
        if (currentNode == null) {
            return null;
        }
        path.add(currentNode);
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

    public void clearData() {
        root = null;
    }

    public int randomNode() {
        return (int) (Math.random() * 101);
    }

    void inOrder(BSTNode node) {
        if (node == null)
            return;

        inOrder(node.left);
        path.add(node);
        System.out.println(node.data);
        inOrder(node.right);
    }

    void preOrder(BSTNode node) {
        if (node == null)
            return;
        path.add(node);
        System.out.println(node.data);
        preOrder(node.left);
        preOrder(node.right);
    }

    void posOrder(BSTNode node) {
        if (node == null)
            return;
        posOrder(node.left);
        posOrder(node.right);
        path.add(node);
        System.out.println(node.data);
    }

    public ArrayList<BSTNode> getNodesInBFSOrder() {
        ArrayList<BSTNode> nodes = new ArrayList<>();
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
        return nodes;
    }

    // Tính chiều cao cây
    public int getHeight(BSTNode node) {
        if (node == null)
            return 0;
        return 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    public String serialize() {
        StringBuilder sb = new StringBuilder();
        serializeHelper(root, sb);
        return sb.toString().trim();
    }

    private void serializeHelper(BSTNode node, StringBuilder sb) {
        if (node == null)
            return;
        sb.append(node.data).append(" ");
        serializeHelper(node.left, sb);
        serializeHelper(node.right, sb);
    }

    public void deserialize(String data) {
        clearData();
        String[] values = data.split(" ");
        for (String value : values) {
            try {
                int num = Integer.parseInt(value);
                this.createNode(num);
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("Dữ liệu không hợp lệ");
            }
        }
    }
}
