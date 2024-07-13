/**
 * FibonacciHeap
 *
 * An implementation of a Fibonacci Heap over integers.
 */

public class FibonacciHeap {
    public int heapSize;
    public HeapNode min;
    public HeapNode first;
    public HeapNode last;
    public int markCount;
    public int treeCount;
    public static int linkCount;
    public static int cutCount;

    // Constructor
    public FibonacciHeap() {
        this.heapSize = 0;
        this.min = null;
        this.first = null;
        this.last = null;
    }

    // Constructor
    public FibonacciHeap(HeapNode first) {
        this.first = first;
        this.min = first;
        this.heapSize = 1;
        this.last = first;
        this.treeCount = 1;
        first.setRoot(true);
    }

    public boolean isEmpty() {
        return this.heapSize == 0;
    }

    public HeapNode insert(int key) {
        HeapNode newNode = new HeapNode(key);
        if (this.isEmpty()) {
            this.first = newNode;
            this.last = newNode;
            this.min = newNode;
            newNode.setNext(newNode);
            newNode.setPrev(newNode);
        } else {
            this.first.prev = newNode;
            newNode.next = this.first;
            newNode.prev = this.last;
            this.last.next = newNode;
            this.first = newNode;
            if (key < this.min.getKey()) {
                this.min = newNode;
            }
        }
        this.heapSize++;
        this.treeCount++;
        newNode.setRoot(true);
        return newNode;
    }

    public void deleteMin() {
        if (this.min == null) return;
        int rootCount = this.treeCount - 1 + this.min.getRank();
        HeapNode prevNode = this.min.getPrev();
        HeapNode nextNode = this.min.getNext();
        if (this.heapSize == 1) {
            this.first = null;
            this.last = null;
            this.min = null;
            this.heapSize = 0;
            this.markCount = 0;
            this.treeCount = 0;
            return;
        } else if (this.min.getChild() != null) {
            prevNode.setNext(this.min.getChild());
            HeapNode temp = this.min.getChild();
            temp.setParent(null);
            temp.setRoot(true);
            while (temp.getNext().getKey() != this.min.getChild().getKey()) {
                temp = temp.getNext();
                temp.setParent(null);
                temp.setRoot(true);
            }
            temp.setNext(nextNode);
        } else {
            prevNode.setNext(nextNode);
            nextNode.setPrev(prevNode);
        }
        if (this.min.getKey() == this.first.getKey()) {
            this.first = this.first.getNext();
        }
        if (this.min.getKey() == this.last.getKey()) {
            this.last = this.last.getPrev();
        }
        this.min.setChild(null);
        this.min = null;
        this.heapSize--;
        HeapNode[] arr = new HeapNode[(int) (Math.log(this.heapSize) / Math.log(2)) + 2];
        HeapNode start = this.first;
        while (rootCount > 0) {
            int rank = start.getRank();
            HeapNode nextItem = start.next;
            if (arr[rank] == null) {
                arr[rank] = start;
                start = start.getNext();
                rootCount--;
            } else {
                linkCount++;
                HeapNode root1 = arr[rank];
                HeapNode root2 = start;
                if (root1.getKey() > root2.getKey()) {
                    root1.setRoot(false);
                    root1.setParent(root2);
                    if (root2.getChild() != null) {
                        root1.setNext(root2.getChild());
                        root1.setPrev(root2.getChild().getPrev());
                        root2.getChild().getPrev().setNext(root1);
                        root2.getChild().setPrev(root1);
                    } else {
                        root1.setNext(root1);
                        root1.setPrev(root1);
                    }
                    root2.setChild(root1);
                    root2.setRank(root2.getRank() + 1);
                    root2.setNext(nextItem);
                    root2.setPrev(null);
                    start = root2;
                } else {
                    root2.setRoot(false);
                    root2.setParent(root1);
                    if (root1.getChild() != null) {
                        root2.setNext(root1.getChild());
                        root2.setPrev(root1.getChild().getPrev());
                        root1.getChild().getPrev().setNext(root2);
                        root1.getChild().setPrev(root2);
                    } else {
                        root2.setNext(root2);
                        root2.setPrev(root2);
                    }
                    root1.setChild(root2);
                    root1.setRank(root1.getRank() + 1);
                    root1.setNext(nextItem);
                    root1.setPrev(null);
                    start = root1;
                }
                arr[rank] = null;
            }
        }
        this.first = null;
        HeapNode temp = null;
        this.min = null;
        this.treeCount = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null) {
                this.treeCount++;
                if (arr[i].isMarked()) {
                    this.markCount--;
                }
                arr[i].setMark(false);
                arr[i].setRoot(true);
                if (this.first == null) {
                    this.first = arr[i];
                    temp = this.first;
                    this.min = temp;
                } else {
                    temp.setNext(arr[i]);
                    arr[i].setPrev(temp);
                    if (this.min.getKey() > arr[i].getKey()) {
                        this.min = arr[i];
                    }
                    temp = temp.getNext();
                }
            }
        }
        temp.setNext(this.first);
        this.last = temp;
        this.first.setPrev(this.last);
    }

    public HeapNode findMin() {
        return this.min;
    }

    public void meld(FibonacciHeap heap2) {
        this.last.next = heap2.first;
        heap2.first.prev = this.last;
        this.first.prev = heap2.last;
        heap2.last.next = this.first;
        this.last = heap2.last;
        if (heap2.min.getKey() < this.min.getKey()) {
            this.min = heap2.min;
        }
        this.treeCount += heap2.treeCount;
        this.heapSize += heap2.heapSize;
        this.markCount += heap2.markCount;
    }

    public int size() {
        return this.heapSize;
    }

    public int[] countersRep() {
        if (this.heapSize == 0) {
            return new int[0];
        }
        int[] arr = new int[(int) (Math.log(this.heapSize) / Math.log(2)) + 2];
        HeapNode node = this.first;
        arr[node.getRank()]++;
        while (node.next.getKey() != this.first.getKey()) {
            node = node.getNext();
            arr[node.getRank()]++;
        }
        int lastIdx = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 0) {
                lastIdx = i;
            }
        }
        int[] res = new int[lastIdx + 1];
        for (int i = 0; i < lastIdx + 1; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    public void delete(HeapNode x) {
        this.decreaseKey(x, x.getKey() + Math.abs(this.min.getKey()) + 1);
        this.deleteMin();
    }

    public void decreaseKey(HeapNode x, int delta) {
        x.setKey(x.getKey() - delta);
        if (x.getKey() < this.min.getKey()) {
            this.min = x;
        }
        if (x.getParent() == null || x.getKey() > x.parent.getKey()) {
            return;
        }
        HeapNode y = x.getParent();
        if (y.getChild().getKey() == x.getKey()) {
            if (x.getNext().getKey() != x.getKey()) {
                y.setChild(x.getNext());
            } else {
                y.setChild(null);
            }
        }
        x.next.prev = x.prev;
        x.prev.next = x.next;
        x.next = this.first;
        x.prev = this.last;
        this.first.prev = x;
        this.last.next = x;
        this.first = x;
        x.parent = null;
        x.setRoot(true);
        this.treeCount++;
        if (x.isMarked()) {
            this.markCount--;
        }
        x.setMark(false);
        y.setRank(y.getRank() - 1);
        cutCount++;
        if (!y.isMarked() && !y.isRoot()) {
            y.setMark(true);
            this.markCount++;
        } else if (y.isMarked()) {
            while (y.isMarked()) {
                HeapNode z = y.getParent();
                if (z.getChild().getKey() == y.getKey()) {
                    if (y.getNext().getKey() != y.getKey()) {
                        z.setChild(y.getNext());
                    } else {
                        z.setChild(null);
                    }
                }
                y.next.prev = y.prev;
                y.prev.next = y.next;
                y.next = this.first;
                y.prev = this.last;
                this.first.prev = y;
                this.last.next = y;
                this.first = y;
                y.parent = null;
                y.setRoot(true);
                this.treeCount++;
                if (y.isMarked()) {
                    this.markCount--;
                }
                y.setMark(false);
                z.setRank(z.getRank() - 1);
                cutCount++;
                if (!z.isMarked() && !z.isRoot()) {
                    z.setMark(true);
                    this.markCount++;
                    break;
                } else {
                    y = z;
                }
            }
        }
    }

    public int potential() {
        return this.treeCount + this.markCount * 2;
    }

    public static int totalLinks() {
        return linkCount;
    }

    public static int totalCuts() {
        return cutCount;
    }

    private HeapNode insertNode(HeapNode newNode) {
        if (this.isEmpty()) {
            this.first = newNode;
            this.last = newNode;
            this.min = newNode;
            newNode.setNext(newNode);
            newNode.setPrev(newNode);
        } else {
            this.first.prev = newNode;
            newNode.next = this.first;
            newNode.prev = this.last;
            this.last.next = newNode;
            if (newNode.getKey() < this.min.getKey()) {
                this.min = newNode;
            }
        }
        this.heapSize++;
        this.treeCount++;
        newNode.setRoot(true);
        return newNode;
    }

    public static int[] kMin(FibonacciHeap H, int k) {
        int[] arr = new int[k];
        FibonacciHeap assist = new FibonacciHeap();
        if (k == 0) {
            return arr;
        }
        if (k == 1) {
            arr[0] = H.min.getKey();
            return arr;
        }
        HeapNode child = new HeapNode(H.min.getKey());
        child.setInfo(H.min);
        assist.insertNode(child);
        for (int i = 0; i < k; i++) {
            arr[i] = assist.min.getKey();
            HeapNode temp = assist.min.getInfo().getChild();
            if (temp != null) {
                HeapNode nodeInsert = new HeapNode(temp.getKey());
                nodeInsert.setInfo(temp);
                assist.insertNode(nodeInsert);
                HeapNode temp2 = temp.getNext();
                while (temp2.getKey() != temp.getKey()) {
                    nodeInsert = new HeapNode(temp2.getKey());
                    nodeInsert.setInfo(temp2);
                    assist.insertNode(nodeInsert);
                    temp2 = temp2.getNext();
                }
            }
            assist.deleteMin();
        }
        return arr;
    }

    public static class HeapNode {
        public int key;
        public HeapNode parent;
        public HeapNode child;
        public HeapNode next;
        public HeapNode prev;
        public int rank;
        public boolean mark;
        public boolean root;
        private HeapNode info;

        public HeapNode(int key) {
            this.key = key;
        }

        public int getKey() {
            return this.key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public void setParent(HeapNode parent) {
            this.parent = parent;
        }

        public HeapNode getParent() {
            return this.parent;
        }

        public void setChild(HeapNode child) {
            this.child = child;
        }

        public HeapNode getChild() {
            return this.child;
        }

        public void setNext(HeapNode next) {
            this.next = next;
        }

        public HeapNode getNext() {
            return this.next;
        }

        public void setPrev(HeapNode prev) {
            this.prev = prev;
        }

        public HeapNode getPrev() {
            return this.prev;
        }

        public void setMark(boolean isMarked) {
            this.mark = isMarked;
        }

        public boolean isMarked() {
            return this.mark;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public int getRank() {
            return this.rank;
        }

        public void setRoot(boolean isRoot) {
            this.root = isRoot;
        }

        public boolean isRoot() {
            return this.root;
        }

        public void setInfo(HeapNode node) {
            this.info = node;
        }

        public HeapNode getInfo() {
            return this.info;
        }
    }
}
