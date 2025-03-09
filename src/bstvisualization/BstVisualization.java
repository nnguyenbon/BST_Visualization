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
public class BstVisualization {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
    }
    /**
     * 1 Người dùng nhập số vào textField và bấm "Add Node". 
     * 2 Gọi handleAddNode(int data) trong ConfigurationPanel. 
     * 3 Gọi addNode(int data) trong BSTTree để thêm nút vào cây.
     * Nếu cây chưa có root, tạo root. Nếu cây đã có root, gọi insert(BSTNode
     * currentNode, int data). 
     * 4 Gọi insert(BSTNode currentNode, int data) trong BSTTree để chèn nút vào
     * đúng vị trí. Xác định vị trí trái hoặc phải. Tính toán tọa độ (x, y) của 
     * node mới. Gán node vào cây. 
     * 5 Gọi bstPanel.repaint() để vẽ lại toàn bộ cây. 
     * 6 Gọi paintComponent(Graphics g) trong BSTPanel để vẽ cây. 
     * 7 Gọi drawTree(Graphics g, BSTNode node) để vẽ từng node. Vẽ đường nối 
     * giữa các nút. Gọi drawNode(Graphics g) trong BSTNode để vẽ nút.
     */
}
