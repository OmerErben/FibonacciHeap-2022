# FibonacciHeap Project

This project implements a Fibonacci Heap, a data structure consisting of a collection of trees which are min-heap ordered. It has desirable properties such as supporting faster operations for insertions and minimum extraction, making it particularly useful for network optimization algorithms.

## How to Run the Project

**Clone the Repository**:
```bash
git clone https://github.com/MIKIHERSHCOVITZ/FibonacciHeap-2022.git
cd FibonacciHeap-2022
```

**Open in IntelliJ IDEA**:

Open IntelliJ IDEA.

Click on Open and select the FibonacciHeapProject directory.

**Run the Project:**:

Ensure FibonacciHeap.java and Main.java are located in the src directory.

Open Main.java in IntelliJ IDEA.

Click on the green run arrow next to the main method or right-click and select Run 'Main.main()'.

## Functionality of the Project

The FibonacciHeap class provides the following functionality:

1. Insertion: Inserts a key into the Fibonacci heap.
2. Find Minimum: Retrieves the minimum key from the Fibonacci heap.
3. Union: Combines two Fibonacci heaps into one.
4. Extract Minimum: Removes and returns the minimum key from the Fibonacci heap.
5. Decrease Key: Decreases the value of a given key.
6. Is Empty: Checks if the Fibonacci heap is empty. 
7. Size: Returns the number of nodes in the Fibonacci heap.

## What the Script Can Do

1. Insert Elements: You can insert elements into the Fibonacci heap. 
2. Extract Minimum: You can extract the minimum element from the Fibonacci heap. 
3. Decrease Key: You can decrease the key of a node in the Fibonacci heap. 
4. Union Heaps: You can combine two Fibonacci heaps into one. 
5. Check Emptiness: You can check if the Fibonacci heap is empty. 
6. Get Size: You can get the number of elements in the Fibonacci heap.

# Example

The main method in Main.java constructs a Fibonacci heap, inserts several nodes, and performs various operations.

Here is a visualization of the example:
```
Heap after insertions:
└── 50
└── 40
└── 30
└── 20
└── 10

Minimum element: 10

Heap after deleting minimum element:
└── 20
├── 40
│   └── 50
└── 30

Heap after decreasing key of 30 to 20:
└── 20
└── 20
└── 40
└── 50
```

