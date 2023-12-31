import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class Main {

    public static class Cell {
        private int rowIndex;
        private int columnIndex;
        private char type;
        private boolean visit;

        public Cell (int x, int y, char type){
            this.rowIndex = x;
            this.columnIndex = y;
            this.type = type;
            this.visit = false;
        }
        public void printCell() {
            System.out.println("Row Index: " + rowIndex);
            System.out.println("Column Index: " + columnIndex);
            System.out.println("Type: " + type);
            System.out.println("Visited: " + visit);
        }
        public int getRowIndex() {
            return rowIndex;
        }
        public int getColumnIndex() {
            return columnIndex;
        }
        public char getType() {
            return type;
        }
    }

    public static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;
    }


    public static class LinkedStack<T> {

        private Node<T> top;

        public LinkedStack() {
            top = null;
        }

        public boolean isEmpty() {
            return (top == null);
        }


        /**
         * Precondition: None
         * Postcondition: Adds new node, the top of the stack becomes new node
         */
        public void push(T item) {
            Node<T> newNode = new Node<T>();
            newNode.value = item;

            if (!isEmpty()) {
                newNode.next = top;
                top.prev = newNode;
            }
            top = newNode;
        }

        /**
         * Precondition: Must not be empty set
         * Postcondition: Returns the node which was removed/popped
         */
         public T pop() {
             if (isEmpty()) {
                 System.out.println("Cannot pop from an empty stack!");
                 return null;
             }

             else {
                 Node<T> nodePop = top;
                 top = top.next;
                 if (top != null) {
                     top.prev = null;
                 }
                 nodePop.next = null;
                 return nodePop.value;
             }
         }
     }

    public static class LinkedQueue<T> {
        private class Node<T>
        {
            private T value;
            private Node<T> next;
        }
        private Node<T> front, rear;

        /**
         * Constructor for objects of class LinkedQueue
         */
        public LinkedQueue()
        {
            rear = null;
            front = null;
        }

        /**
         * Precondition: None
         * Postcondition: returns true if queue is empty
         */
        public boolean isEmpty()
        {
            return (front == null);
        }

        /**
         * Precondition: None
         * Postcondition: returns false
         */
        public boolean isFull()
        {
            return false;
        }


        /**
         * Precondition: None
         * Postcondition: Adds a new element to the queue
         */
        public void enqueue(T item)
        {
            Node<T> newNode = new Node<T>();
            newNode.value = item;
            newNode.next = rear;
            rear = newNode;
            if (front == null) front = rear;
        }


        /**
         * Precondition: Qeueue is not empty
         * Postcondition: removes and retuens the front item from the queue
         */
        public T dequeue()
        {
            T tmp = front.value;
            if (front == rear) // singleton
            {
                front = null;
                rear = null;
            }
            else {
                Node<T> tmpN = rear;
                while (tmpN.next != front) tmpN = tmpN.next;
                front = tmpN;
                front.next = null;
            }
            return tmp;
        }

        /**
         * Precondition: None
         * Postcondition: prints the contents of the queue
         */
        public void printQueuetoFile(String filename) {
            try {
                PrintWriter writer = new PrintWriter(new FileWriter(filename));

                int counter = 0;
                writer.print("r-> ");

                Node<T> i = rear;
                if (front == rear && front != null) {
                    counter++;
                    Cell cell = (Cell) i.value;
                    writer.print("(" + cell.getRowIndex() + "," + cell.getColumnIndex() + ")");
                } else {
                    while (i != null) {
                        Cell cell = (Cell) i.value;
                        writer.print("(" + cell.getRowIndex() + "," + cell.getColumnIndex() + ")");
                        counter++;
                        if (i.next != null) {
                            writer.print(",");
                        }
                        i = i.next;
                    }
                    writer.print(" <-f");
                    writer.print("Mouse moved " + counter + " times.");

                    writer.close();
                    System.out.println("Output written to " + filename + "Succesfully");


                }
            } catch (IOException e) {
                System.out.println("An error occured while writing the output to " + filename);
                e.printStackTrace();
            }

            }
        }


            public static class CircularDoublyLinkedList<T> {
        private Node<T> head, currentIndex, tail;

        /**
         * Constructor for objects of class DoublyLinkedList
         */
        public CircularDoublyLinkedList() {
            head = null;
            tail = null;
            currentIndex = null;
        }

        /**
         * Precondition: None
         * Postcondition: returns true if list is empty
         */
        public boolean isEmpty() {
            return (head == null);
        }


        /**
         * Precondition: None
         * Postcondition: Adds a new element at the beginning of the list
         */
        public void enqueue(T item) {
            // Create a newNode variable for the value
            Node<T> newNode = new Node<T>();
            newNode.value = item;

            if (isEmpty()) {  // If the list is empty
                // Both head and tail will refer to the newNode
                head = newNode;
                tail = newNode;
            } else {  // Otherwise
                newNode.prev = tail;  // The current head's previous node will be the new node
                tail.next = newNode;
                tail = newNode;  // Update the head variable to show the new first element of the list
            }
        }


        /**
         * Precondition: None
         * Postcondition: returns true if a given item is in the list; otherwise returns false
         */

        /**
         * Precondition: None
         * Postcondition: removes first occurence of an item from the list
         */
        public T dequeue()
        {
            if (isEmpty()) {
                System.out.println("Cannot pop from an empty stack!");
                return null;
            }

            T tmp = head.value;
            if (head == tail) // singleton
            {
                head = null;
                tail = null;
            }
            else {
                Node<T> newHead = head.next;
                newHead.prev = tail;
                tail.next = newHead;
                head = newHead;
                head.prev = tail;
            }
            return tmp;
        }

    }

    /**
     * Precondition: None
     * Postcondition: Returns wether or not the mouse(start) can get to the cheese(end) in a maze using DFS
     */
    public static void DepthFirstSearch(List<Cell> cellList, int col){
        LinkedStack DFS  = new LinkedStack();

        for (Cell cell : cellList) {
            if (cell.getType() == 'm') {
                DFS.push(cell);
            }
        }

        boolean moreToSearch = true;
        LinkedQueue trailQueue = new LinkedQueue<>();

        while (!DFS.isEmpty() && moreToSearch) {
            int space = 0;
            for (Cell cell : cellList) {
                System.out.print(cell.getType() + " ");
                space++;
                if (space % col == 0) {
                    System.out.println();
                }
            }
            System.out.println();

            Cell j = (Cell) DFS.pop();

            if (j.type != 'c'){
                j.type = '^';
            }
            trailQueue.enqueue(j);

            if (j.type == 'c') {
                moreToSearch = false;

            } else {
                int y = j.getRowIndex();
                int x = j.getColumnIndex();

                for (Cell cell : cellList) {
                    if (cell.getColumnIndex() == x && cell.getRowIndex() == y - 1) {
                        if (cell.getType() == '0' || cell.getType() == 'c') {
                            DFS.push(cell);
                        }
                    } else if (cell.getColumnIndex() == x + 1 && cell.getRowIndex() == y) {
                        if (cell.getType() == '0' || cell.getType() == 'c') {
                            DFS.push(cell);
                        }
                    } else if (cell.getColumnIndex() == x && cell.getRowIndex() == y + 1) {
                        if (cell.getType() == '0' || cell.getType() == 'c') {
                            DFS.push(cell);
                        }
                    } else if (cell.getColumnIndex() == x - 1 && cell.getRowIndex() == y) {
                        if (cell.getType() == '0' || cell.getType() == 'c') {
                            DFS.push(cell);
                        }
                    }
                }
            }
        }
        if (DFS.isEmpty() && moreToSearch){
            System.out.println("Cheese cannot be found.");
        }
        trailQueue.printQueue();
    }
    /**
     * Precondition: None
     * Postcondition: Returns wether or not the mouse(start) can get to the cheese(end) in a maze using BFS
     */
    public static void BreadthFirstSearch(List<Cell> cellList, int col){
        CircularDoublyLinkedList BFS  = new CircularDoublyLinkedList ();

        for (Cell cell : cellList) {
            if (cell.getType() == 'm') {
                BFS.enqueue(cell);
            }
        }

        boolean moreToSearch = true;
        LinkedQueue trailQueue = new LinkedQueue<>();

        while (!BFS.isEmpty() && moreToSearch) {
            int space = 0;
            for (Cell cell : cellList) {
                System.out.print(cell.getType() + " ");
                space++;
                if (space % col == 0) {
                    System.out.println();
                }
            }
            System.out.println();

            Cell j = (Cell) BFS.dequeue();

            if (j.type != 'c'){
                j.type = '^';
            }
            trailQueue.enqueue(j);

            if (j.type == 'c') {
                moreToSearch = false;

            } else {
                int y = j.getRowIndex();
                int x = j.getColumnIndex();

                for (Cell cell : cellList) {
                    if (cell.getColumnIndex() == x && cell.getRowIndex() == y - 1) {
                        if (cell.getType() == '0' || cell.getType() == 'c') {
                            BFS.enqueue(cell);
                        }
                    } else if (cell.getColumnIndex() == x + 1 && cell.getRowIndex() == y) {
                        if (cell.getType() == '0' || cell.getType() == 'c') {
                            BFS.enqueue(cell);
                        }
                    } else if (cell.getColumnIndex() == x && cell.getRowIndex() == y + 1) {
                        if (cell.getType() == '0' || cell.getType() == 'c') {
                            BFS.enqueue(cell);
                        }
                    } else if (cell.getColumnIndex() == x - 1 && cell.getRowIndex() == y) {
                        if (cell.getType() == '0' || cell.getType() == 'c') {
                            BFS.enqueue(cell);
                        }
                    }
                }
            }
        }
        if (BFS.isEmpty() && moreToSearch){
            System.out.println("Cheese cannot be found.");
        }
        trailQueue.printQueue();
    }

        public static void main(String[] args) {
            if (args.length < 1) {
                System.out.println("Please provide the file path as a command-line argument.");
                return;
            }

            String filePath = args[0];
            List<Cell> cellList = new ArrayList<>();

            try {
                File file = new File(filePath);
                Scanner scanner = new Scanner(file);

                int row = 0;
                int col = 0;
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    row++;
                    col = line.length();
                }

                char[][] maze = new char[row][col];
                scanner = new Scanner(file);

                int i = 0;
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    for (int j = 0; j < col; j++) {
                        maze[i][j] = line.charAt(j);
                        Cell cell = new Cell(i, j, maze[i][j]);
                        cellList.add(cell);
                    }
                    i++;
                }

                DepthFirstSearch(cellList, col);
                BreadthFirstSearch(cellList, col);

            } catch (FileNotFoundException e) {
                System.out.println("File not found: " + filePath);
                e.printStackTrace();
            }
        }
    }

