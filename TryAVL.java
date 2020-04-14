import java.io.IOException;

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
            System.out.print(node.key + " ");
            String str = node.key;
            str +=" "+preOrder(node.left); 
            str +=" "+preOrder(node.right); 
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
        AVLTree tree = new AVLTree();

        String[] stats = new String[10000];
        int numStats = 0;

        while (true) {
            String comands;
            System.out.println("Waiting for a new comand");
            comands = readLn(10000);
            String[] action = comands.split(" ");
            
            if (action[0].equals("TEXTO")) {
                int numLines = 0;
                while (true) {
                    String line = readLn(5000);
                    String[] l = line.split(" |\\(|\\)|\\,|\\;|\\.");

                    if (line.equals("FIM.")) {
                        stats[numStats] = "GUARDADO.";
                        numStats++;
                        break;
                    }

                    for (String l1 : l) {
                        Node node = tree.root;
                        int aux = 0;
                        l1 = l1.toLowerCase();
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

                        System.out.println("out of while");
                        if (aux == 0) {
                            System.out.println("arrive if");
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

                            auxNode.avl.root = auxNode.avl.insert(auxNode.avl.root, String.valueOf(numLines));
                            auxNode.avl.printHelper(auxNode.avl.root, "", true);

                        } else {
                            System.out.println("arrive else");
                            node.avl.root = node.avl.insert(node.avl.root, String.valueOf(numLines));
                            node.avl.printHelper(node.avl.root, "", true);
                        }
                    }
                    numLines++;
                }

            }
            if (action[0].equals("LINHAS")) {
                System.out.println("in linhas ");
                Node node = tree.root;
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

            if (action[0].equals("ASSOC")) {
                System.out.println("entrou no assco");
                Node node = tree.root;
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
                    Node auxNode = node.avl.root;
                    int flag = 0;
                    while (node != null) {
                        if (auxNode.key.equals(action[2])) {
                            stats[numStats] = "ENCONTRADA";
                            numStats++;
                            flag = 1;
                            break;
                        }

                        if (auxNode != null && (action[2]).compareTo(node.key) > 0) {
                            node = node.right;
                        }

                        if (auxNode != null && (action[2]).compareTo(node.key) < 0) {
                            node = node.left;
                        }
                    }
                    if (flag == 0) {
                        stats[numStats] = "NAO ENCONTRADA";
                        numStats++;
                    }

                }
                else{
                    System.out.println("nao encontrada");
                }
            }

            if (action[0].equals("TCHAU")) {
                for (int i = 0; i < numStats; i++) {
                    System.out.println(stats[i]);
                }
                return;
            }

           tree.printHelper(tree.root, "", true);

        }
    }
}
