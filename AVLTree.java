
import java.io.IOException;
import java.util.Random;

class Example {

    public static int rotations;
}

// Java program for insertion in AVL Tree 
class Node {

    int height;
    String key;
    Node left, right;
    AVLTree avl;

    Node(String d) {
        key = d;
        height = 1;
        avl = new AVLTree();
    }
}

class AVLTree {

    Node root;

    Node insertnum(Node node, int key) {

        /* 1.  Perform the normal BST insertion */
        if (node == null) {
            return (new Node(String.valueOf(key)));
        }

        if (key < Integer.parseInt(node.key)) { // key < node.key
            node.left = insert(node.left, String.valueOf(key));
        } else if (key > Integer.parseInt(node.key)) { // key > node.key
            node.right = insert(node.right, String.valueOf(key));
        } else // Duplicate keys not allowed 
        {
            return node;
        }

        /* 2. Update height of this ancestor node */
        node.height = 1 + max(height(node.left),
                height(node.right));

        /* 3. Get the balance factor of this ancestor 
              node to check whether this node became 
              unbalanced */
        int balance = getBalance(node);

        // If this node becomes unbalanced, then there 
        // are 4 cases Left Left Case 
        if (balance > 1 && key < Integer.parseInt(node.left.key)) { // key < node.left.key
            return rightRotate(node);
        }

        // Right Right Case 
        if (balance < -1 && key > Integer.parseInt(node.right.key)) { // key > node.right.key
            return leftRotate(node);
        }

        // Left Right Case 
        if (balance > 1 && key > Integer.parseInt(node.left.key)) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case 
        if (balance < -1 && key < Integer.parseInt(node.right.key)) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        /* return the (unchanged) node pointer */
        return node;
    }

    // A utility function to get the height of the tree 
    int height(Node N) {
        if (N == null) {
            return 0;
        }

        return N.height;
    }

    // A utility function to get maximum of two integers 
    int max(int a, int b) {
        return (a > b) ? a : b;
    }

    // A utility function to right rotate subtree rooted with y 
    // See the diagram given above. 
    Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // Perform rotation 
        x.right = y;
        y.left = T2;

        // Update heights 
        y.height = max(height(y.left), height(y.right)) + 1;
        x.height = max(height(x.left), height(x.right)) + 1;

        // Return new root 
        Example.rotations++;
        return x;
    }

    // A utility function to left rotate subtree rooted with x 
    // See the diagram given above. 
    Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // Perform rotation 
        y.left = x;
        x.right = T2;

        //  Update heights 
        x.height = max(height(x.left), height(x.right)) + 1;
        y.height = max(height(y.left), height(y.right)) + 1;

        // Return new root 
        Example.rotations++;
        return y;
    }

    // Get Balance factor of node N 
    int getBalance(Node N) {
        if (N == null) {
            return 0;
        }

        return height(N.left) - height(N.right);
    }

    Node insert(Node node, String key) {

        /* 1.  Perform the normal BST insertion */
        if (node == null) {
            return (new Node(key));
        }

        if (key.compareTo(node.key) < 0) { // key < node.key
            node.left = insert(node.left, key);
        } else if (key.compareTo(node.key) > 0) { // key > node.key
            node.right = insert(node.right, key);
        } else // Duplicate keys not allowed 
        {
            return node;
        }

        /* 2. Update height of this ancestor node */
        node.height = 1 + max(height(node.left),
                height(node.right));

        /* 3. Get the balance factor of this ancestor 
              node to check whether this node became 
              unbalanced */
        int balance = getBalance(node);

        // If this node becomes unbalanced, then there 
        // are 4 cases Left Left Case 
        if (balance > 1 && key.compareTo(node.left.key) < 0) { // key < node.left.key
            return rightRotate(node);
        }

        // Right Right Case 
        if (balance < -1 && key.compareTo(node.right.key) > 0) { // key > node.right.key
            return leftRotate(node);
        }

        // Left Right Case 
        if (balance > 1 && key.compareTo(node.left.key) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case 
        if (balance < -1 && key.compareTo(node.right.key) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        /* return the (unchanged) node pointer */
        return node;
    }

    void printHelper(Node currPtr, String indent, boolean last) {
        // print the tree structure on the screen
        if (currPtr != null) {
            System.out.print(indent);
            if (last) {
                System.out.print("R----");
                indent += "     ";
            } else {
                System.out.print("L----");
                indent += "|    ";
            }

            System.out.println(currPtr.key);

            printHelper(currPtr.left, indent, false);
            printHelper(currPtr.right, indent, true);
        }
    }

    String preOrder(Node node) {
        if (node != null) {
            String str = node.key;
            str += " " + preOrder(node.left);
            str += " " + preOrder(node.right);
            return str;
        }
        return "";
    }

    static String readLn(int maxLg) {
        {
            byte lin[] = new byte[maxLg];
            int lg = 0, car = -1;
            String line = "";
            try {
                while (lg < maxLg) {
                    car = System.in.read();
                    if ((car < 0) || (car == '\n')) {
                        break;
                    }
                    lin[lg++] += car;
                }
            } catch (IOException e) {
                return (null);
            }
            if ((car < 0) && (lg == 0)) {
                return (null);
            }
            return (new String(lin, 0, lg));
        }

    }

    public static void main(String[] args) {
        Example.rotations = 0;
        AVLTree tree = new AVLTree();
        String[] stats = new String[10000];
        int numStats = 0;
        int numArray = 0;
        String[] array = new String[15000];
        long time = 0;
        int numLines = 0;
        while (true) {
            String comands;
            System.out.println("Waiting for a new comand");
            comands = readLn(10000);
            String[] action = comands.split(" ");

            if (action[0].equals("TEXTO")) {

                while (true) {
                    String line = readLn(5000);
                    String[] l = line.split(" |\\(|\\)|\\,|\\;|\\.");

                    if (line.equals("FIM.")) {
                        stats[numStats] = "GUARDADO.";
                        numStats++;
                        break;
                    }

                    for (String l1 : l) {
                        array[numArray] = l1;
                        numArray++;
                        Node node = tree.root;
                        int aux = 0;
                        l1 = l1.toLowerCase();

                        long start = System.nanoTime();

                        while (node != null) {

                            if (node.key.equals(l1.toLowerCase())) {
                                aux = 1;
                                break;
                            }

                            if (node != null && (l1.toLowerCase()).compareTo(node.key) > 0) {
                                node = node.right;
                            }

                            if (node != null && (l1.toLowerCase()).compareTo(node.key) < 0) {
                                node = node.left;
                            }
                        }

                        if (aux == 0) {
                            tree.root = tree.insert(tree.root, l1);

                            Node auxNode = tree.root;
                            while (auxNode != null) {

                                if (auxNode.key.equals(l1.toLowerCase())) {
                                    break;
                                }

                                if (auxNode != null && (l1.toLowerCase()).compareTo(auxNode.key) > 0) {
                                    auxNode = auxNode.right;
                                }

                                if (auxNode != null && (l1.toLowerCase()).compareTo(auxNode.key) < 0) {
                                    auxNode = auxNode.left;
                                }
                            }

                            auxNode.avl.root = auxNode.avl.insertnum(auxNode.avl.root, numLines);

                        } else {
                            node.avl.root = node.avl.insertnum(node.avl.root, numLines);
                        }

                        long end = System.nanoTime();
                        time += (end - start);
                    }
                    numLines++;
                }

            }

            System.out.println("TIME: " + (time));

            if (action[0].equals("LINHAS")) {
                String[] arraySec = new String[10];

                for (int i = 0; i < 10; i++) {
                    Random rand = new Random();
                    int index = rand.nextInt(numArray);
                    arraySec[i] = array[index];
                }
                long startTimeLines = System.nanoTime();
                for (int i = 0; i < 500; i++) {
                    Node node = tree.root;
                    Random rand = new Random();

                    //int index = rand.nextInt(numArray);
                    //action[1] = array[index];
                    int index = rand.nextInt(10);
                    action[1] = arraySec[index];
                    action[1] = action[1].toLowerCase();
                    int aux = 0;
                    while (node != null) {
                        if (node.key.equals(action[1])) {
                            aux = 1;
                            break;
                        }

                        if (node != null && (action[1]).compareTo(node.key) > 0) {
                            node = node.right;
                        }

                        if (node != null && (action[1]).compareTo(node.key) < 0) {
                            node = node.left;
                        }
                    }

                    if (aux == 1) {
                        stats[numStats] = node.avl.preOrder(node.avl.root);
                        numStats++;

                    } else {
                        stats[numStats] = "-1";
                        numStats++;
                    }
                }

                long endTimeLines = System.nanoTime();
                long timeLines = (endTimeLines - startTimeLines);
                System.out.println("Time Lines: " + timeLines);
            }
            if (action[0].equals("ASSOC")) {
                System.out.println("entrou no assco");
                long startTimeAssoc = System.nanoTime();
                for (int i = 0; i < 50; i++) {
                    Node node = tree.root;
                    Random rand = new Random();
                    int index = rand.nextInt(numArray);

                    action[1] = array[index];
                    action[1] = action[1].toLowerCase();
                    action[2] = String.valueOf(rand.nextInt(numLines));

                    int aux = 0;
                    while (node != null) {
                        if (node.key.equals(action[1])) {
                            aux = 1;
                            break;
                        }

                        if (node != null && (action[1]).compareTo(node.key) > 0) {
                            node = node.right;
                        }

                        if (node != null && (action[1]).compareTo(node.key) < 0) {
                            node = node.left;
                        }
                    }

                    if (aux == 1) {
                        Node auxNode = node.avl.root;
                        int flag = 0;
                        while (node != null) {
                            if (auxNode.key.equals(action[2])) {
                                stats[numStats] = "ENCONTRADA";
                                numStats++;
                                flag = 1;
                                break;
                            }

                            if (Integer.parseInt(action[2]) > Integer.parseInt(auxNode.key)) {
                                node = node.right;
                            }

                            if (Integer.parseInt(action[2]) < Integer.parseInt(auxNode.key)) {
                                node = node.left;
                            }
                        }
                        if (flag == 0) {
                            stats[numStats] = "NAO ENCONTRADA";
                            numStats++;
                        }

                    } else {
                        stats[numStats] = "NAO ENCONTRADA";
                        numStats++;
                    }
                }
                long endTimeAssoc = System.nanoTime();
                long timeAssoc = (endTimeAssoc - startTimeAssoc);
                System.out.println("Time Assoc: " + timeAssoc);
                //end of for

            }

            if (action[0].equals("TCHAU")) {
                for (int i = 0; i < numStats; i++) {
                    System.out.println(stats[i]);
                }
                System.out.println("NUMBER OF ROTATIONS: " + Example.rotations);
                return;
            }

        }
    }
}
