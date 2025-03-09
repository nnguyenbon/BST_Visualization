/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bstvisualization;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author CE191239 Nguyen Kim Bao Nguyen
 */
public class BSTTree {

    private String result;
    private ArrayList<BSTNode> path;
    BSTNode root;
    private final int panelWidth, panelHeight;
    private ArrayList<Integer> sortedList;
    public static boolean cannotAdd;

    public BSTTree(int panelWidth, int panelHeight) {
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
    }

    public void addNode(int data) {
        if (root == null) {
            root = new BSTNode(data, panelWidth / 2 - 10, 30, 0);
        } else {
            insert(root, data);
        }
    }

    private void insert(BSTNode currentNode, int data) {
        int level = currentNode.level + 1;

        if (currentNode.level == 5) {
            result = "Limit of display level";
            cannotAdd = true;
            return;
        }

        int dx = (panelWidth - 100) / (int) Math.pow(2, level + 1);

        if (data < currentNode.data) {
            if (currentNode.left == null) {
                currentNode.left = new BSTNode(data, currentNode.x - dx, currentNode.y + 80, level);
                currentNode.left.parent = currentNode;
            } else {
                insert(currentNode.left, data);
            }
        } else if (data > currentNode.data) {
            if (currentNode.right == null) {
                currentNode.right = new BSTNode(data, currentNode.x + dx, currentNode.y + 80, level);
                currentNode.right.parent = currentNode;
            } else {
                insert(currentNode.right, data);
            }
        }
    }

    public int countLevels() {
        return countLevels(root);
    }

    private int countLevels(BSTNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(countLevels(node.left), countLevels(node.right));
    }

    public int countNodes() {
        return countNodes(root); // Gọi đệ quy từ nút gốc
    }

    private int countNodes(BSTNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + countNodes(node.left) + countNodes(node.right);
    }

    public int getNodeLevel(int data) {
        return getNodeLevel(root, data, 0);
    }

    private int getNodeLevel(BSTNode node, int data, int level) {
        if (node == null) {
            return -1; // Không tìm thấy node
        }
        if (node.data == data) {
            return level; // Trả về cấp độ của node
        }
        if (data < node.data) {
            return getNodeLevel(node.left, data, level + 1);
        } else {
            return getNodeLevel(node.right, data, level + 1);
        }
    }

    public BSTNode findNode(int data) {
        result = "";
        path = new ArrayList<>();
        BSTNode currentNode = root;

        while (currentNode != null) {
            path.add(currentNode);
            result += "->" + currentNode.data;
            if (data < currentNode.data) {
                currentNode = currentNode.left;
            } else if (data > currentNode.data) {
                currentNode = currentNode.right;
            } else {
                ConfigurationPanel.isSearched = true;
                result = result.substring(2);
                return currentNode;
            }
        }

        ConfigurationPanel.isSearched = false;
        result = "Not found";
        return null;
    }

    public ArrayList<BSTNode> getPath() {
        return path;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void resetResult() {
        result = "";
    }

    public boolean removeNode(int data) {
        BSTNode current = findNode(data);
        return removeNode(current);
    }

    public boolean removeNode(BSTNode current) {
        if (current == null) {
            return false;
        }

        current.count--;
        if (current.count > 0) {
            return true;
        }

        if (current.isLeaf()) {
            if (current.isRoot() && current.count == 0) {
                root = null;
            } else {
                current.parent.removeLeafChild(current);
            }
            return true;
        }

        BSTNode temp;
        if (current.hasLeftChild()) {
            temp = current.left.findMax();
        } else {
            temp = current.right.findMin();
        }

        current.data = temp.data;
        current.count = temp.count;

        temp.count = 1;
        return removeNode(temp);
    }

    //Node - Left - Right
    public void preOrder() {
        preOder(root);
        result = result.substring(2);
    }

    public void preOder(BSTNode currentNode) {
        if (currentNode == null) {

            return;
        }

        for (int i = 0; i < currentNode.count; i++) {
            result += "->" + currentNode.data;
        }
        preOder(currentNode.left);
        preOder(currentNode.right);
    }

    //Left - Right - Node
    public void postOrder() {
        postOrder(root);
        result = result.substring(2);
    }

    public void postOrder(BSTNode currentNode) {
        if (currentNode == null) {
            return;
        }

        postOrder(currentNode.left);
        postOrder(currentNode.right);

        for (int i = 0; i < currentNode.count; i++) {
            result += "->" + currentNode.data;
        }
    }

    //Left - Node - Right
    public void inOrder() {
        sortedList = new ArrayList<>();
        inOrder(root);
        result = result.substring(2);
    }

    public void inOrder(BSTNode currentNode) {
        if (currentNode == null) {
            return;
        }

        inOrder(currentNode.left);
        for (int i = 0; i < currentNode.count; i++) {
            result += "->" + currentNode.data;
            sortedList.add(currentNode.data);
        }
        inOrder(currentNode.right);
    }

    public void BFS() {
        Queue<BSTNode> s = new LinkedList<>();
        s.add(root);
        BSTNode currentNode;
        while (!s.isEmpty()) {
            currentNode = s.poll();
            if (currentNode != null) {

                for (int i = 0; i < currentNode.count; i++) {
                    result += "->" + currentNode.data;
                }
                s.add(currentNode.left);
                s.add(currentNode.right);
            }
        }
        result = result.substring(2);
    }

    public void balanceTree() {
        if (root == null) {
            return;
        }

        ArrayList<Integer> balanceList = new ArrayList<>();
        sortedList = new ArrayList<>();
        inOrder();

        buildBalancedTree(sortedList, 0, sortedList.size() - 1, balanceList);

        deleteTree();
        for (Integer element : balanceList) {
            addNode(element);
        }
    }

    private void buildBalancedTree(ArrayList<Integer> sortedList, int start, int end, ArrayList<Integer> balanceList) {
        if (start > end) {
            return;
        }

        int mid = (start + end) / 2;
        balanceList.add(sortedList.get(mid));

        buildBalancedTree(sortedList, start, mid - 1, balanceList);
        buildBalancedTree(sortedList, mid + 1, end, balanceList);
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void deleteTree() {
        root = deleteTreeRecursive(root);
    }

    private BSTNode deleteTreeRecursive(BSTNode node) {
        if (node == null) {
            return null;
        }

        node.left = deleteTreeRecursive(node.left);
        node.right = deleteTreeRecursive(node.right);

        return null;
    }
}
