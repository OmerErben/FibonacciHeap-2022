import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        FibonacciHeap heap = new FibonacciHeap();

        // Insert elements into the heap
        heap.insert(10);
        heap.insert(20);
        heap.insert(30);
        heap.insert(40);
        heap.insert(50);

        System.out.println("Heap after insertions:");
        printHeap(heap);

        // Find the minimum element
        System.out.println("Minimum element: " + heap.findMin().getKey());

        // Extract the minimum element
        heap.deleteMin();
        System.out.println("Heap after deleting minimum element:");
        printHeap(heap);

        // Decrease the key of an element
        FibonacciHeap.HeapNode node30 = findNode(heap, 30); // Find node with key 30
        if (node30 != null) {
            heap.decreaseKey(node30, 10); // Decrease key of node 30 to 20
            System.out.println("Heap after decreasing key of 30 to 20:");
            printHeap(heap);
        } else {
            System.out.println("Node with key 30 not found.");
        }
    }

    public static void printHeap(FibonacciHeap heap) {
        if (heap.isEmpty()) {
            System.out.println("Heap is empty.");
            return;
        }

        List<FibonacciHeap.HeapNode> roots = new ArrayList<>();
        FibonacciHeap.HeapNode current = heap.first;
        do {
            roots.add(current);
            current = current.getNext();
        } while (current != heap.first);

        for (FibonacciHeap.HeapNode root : roots) {
            printTree(root, "", true);
        }
    }

    public static void printTree(FibonacciHeap.HeapNode node, String prefix, boolean isTail) {
        System.out.println(prefix + (isTail ? "└── " : "├── ") + node.getKey());
        List<FibonacciHeap.HeapNode> children = new ArrayList<>();
        FibonacciHeap.HeapNode current = node.getChild();
        if (current != null) {
            do {
                children.add(current);
                current = current.getNext();
            } while (current != node.getChild());
        }
        for (int i = 0; i < children.size() - 1; i++) {
            printTree(children.get(i), prefix + (isTail ? "    " : "│   "), false);
        }
        if (children.size() > 0) {
            printTree(children.get(children.size() - 1), prefix + (isTail ? "    " : "│   "), true);
        }
    }

    public static FibonacciHeap.HeapNode findNode(FibonacciHeap heap, int key) {
        return findNodeInTree(heap.first, key);
    }

    public static FibonacciHeap.HeapNode findNodeInTree(FibonacciHeap.HeapNode node, int key) {
        if (node == null) return null;
        if (node.getKey() == key) return node;
        FibonacciHeap.HeapNode foundNode = null;
        FibonacciHeap.HeapNode current = node.getChild();
        if (current != null) {
            do {
                foundNode = findNodeInTree(current, key);
                if (foundNode != null) return foundNode;
                current = current.getNext();
            } while (current != node.getChild());
        }
        return null;
    }
}
