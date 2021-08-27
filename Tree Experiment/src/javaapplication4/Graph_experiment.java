package javaapplication4;

import java.util.Scanner;

class Node {

    int value;
    int level;
    int node_index;
    Node left, right;

    Node(int value, int node_index, int level) {
        this.level = level;
        this.value = value;
        this.node_index = node_index;
    }

    Node(int value, int level) {
        this.level = level;
        this.value = value;
    }
}

public class Graph_experiment {

    static Scanner scan = new Scanner(System.in);
    static int total_node, temp, count = 0, max_lebel = 0, level = 0, i = 0;

    static Node graph_input_BST(Node root, int value) {
        level++;
        if (level > max_lebel) {
            max_lebel = level;
        }
        if (root == null) {
            return new Node(value, level);
        } else {
            if (value <= root.value) {
                root.left = graph_input_BST(root.left, value);
            } else {
                root.right = graph_input_BST(root.right, value);
            }
        }
        return root;
    }

    static Node graph_input_manually(Node root, int level) {
        if (root.left == null && root.right == null) {
            System.out.println("Enter left and right clild for(" + root.value + ")---[Enter -1 if child doesn't have value]");
            System.out.print("Left - ");
            int left = scan.nextInt();
            System.out.print("Right - ");
            int right = scan.nextInt();
            if (left != -1) {
                root.left = new Node(left, count++, level);
            }
            if (right != -1) {
                root.right = new Node(right, count++, level);
            }
            return root;
        } else {
            if (count < total_node) {
                if (root.left != null) {
                    root.left = graph_input_manually(root.left, level);
                }
                if (root.right != null) {
                    root.right = graph_input_manually(root.right, level);
                }
            }
        }
        return root;
    }

    static void DFS(Node root) {
        System.out.print(root.value + "-");
        if (root.left != null) {
            DFS(root.left);
        }
        if (root.right != null) {
            DFS(root.right);
        }
    }

    static void search(Node root, int v, String str) {
        if (str.equals("value")) {
            if (v == root.value) {
                System.out.print(root.node_index);
            }
        }
        if (str.equals("index")) {
            if (v == root.node_index) {
                System.out.print(root.value);
            }
        }
        if (root.left != null) {
            search(root.left, v, str);
        }
        if (root.right != null) {
            search(root.right, v, str);
        }
    }

    static void BFS(Node root, int level) {
        if (root.level == level) {
            System.out.print(root.value + "-");
        }
        if (root.left != null) {
            BFS(root.left, level);
        }
        if (root.right != null) {
            BFS(root.right, level);
        }

    }

    static Node adding_node(Node root, int parrant, int val) {

        if (parrant == root.value) {
            if (root.left == null && root.right == null) {
                if (root.level == level) {
                    level++;
                }
                System.out.print("\nBoth clild node of (" + root.value + ") is empty!"
                        + "\nWhere you want to add? (type left or right)\nEnter -> ");
                String str = scan.next();
                if (str.equals("left")) {
                    root.left = new Node(val, count++, root.level + 1);
                    total_node++;
                    System.out.print("\nNew Node Added Successfully!\n");
                    return root;
                }
                if (str.equals("right")) {
                    root.right = new Node(val, count++, root.level + 1);
                    total_node++;
                    System.out.print("\nNew Node Added Successfully!\n");
                    return root;
                }
            } else if (root.left == null) {
                System.out.print("Right clild node of (" + root.value + ") is not empty!"
                        + "-Value will auto added in left clild\n");
                root.left = new Node(val, count++, root.level + 1);
                total_node++;
                System.out.print("\nNew Node Added Successfully!\n");
                return root;
            } else if (root.right == null) {
                System.out.print("Left clild node of (" + root.value + ") is not empty!"
                        + "-Value will auto added in right clild\n");
                root.right = new Node(val, count++, root.level + 1);
                total_node++;
                System.out.print("\nNew Node Added Successfully!\n");
                return root;
            } else {
                System.out.print("Ops!! Nither of child is empty in this node try another!");
                return root;
            }
        }

        if (root.left != null) {
            adding_node(root.left, parrant, val);
        }
        if (root.right != null) {
            adding_node(root.right, parrant, val);
        }
        return root;
    }

    static Node delete_node_with_child(Node root, int val) {
        if (root.value == val) {
            if (root.left != null || root.right != null) {
                System.out.print("These all Nodes[");
                DFS(root);
                System.out.println("]will also remove after removing the node(" + root.value + ")");

                System.out.print("Do you want to proceed?(Type yes or no)\n->");
                if (scan.next().equals("yes")) {
                    System.out.print("All Nodes removes successfully");
                    return null;
                } else {
                    System.out.print("Node removed Unsuccessful");
                    return root;
                }
            } else {
                System.out.print("Node removed successfully");
                return null;
            }

        }
        if (root.left != null) {
            root.left = delete_node_with_child(root.left, val);
        }
        if (root.right != null) {
            root.right = delete_node_with_child(root.right, val);
        }
        return root;
    }
    static Node delete_node_without_child(Node root, int val) {
        if (root.value == val) {
            if(root.right!=null){
                root.value = root.right.value;
                val = root.value;
            }
            else if(root.left!=null){
                root.value = root.left.value;
                val = root.value;
            }
            else{
                return null;
            }
        }
        if (root.left != null) {
            root.left = delete_node_without_child(root.left, val);
        }
        if (root.right != null) {
            root.right = delete_node_without_child(root.right, val);
        }
        return root;
    }

    static void functions(Node root) {
        System.out.println("What would you like to do further?");
        System.out.print("1.DFS\n2.BFS\n3.BFS by level\n4.Search node "
                + "number by value\n5.Search Value by node number\n"
                + "6.Enter A new Node\n7.Delete a node with child\n"
                + "8.Delete a node without child\n");
        while (true) {
            System.out.print("\nEnter between 1 to 7 or type stop to terminet-> ");
            String str = scan.next();
            if (str.equals("stop")) {
                break;
            }
            switch (Integer.parseInt(str)) {
                case 0:
                    System.out.println();
                case 1:
                    System.out.print("\n DFS travers!\n-> ");
                    DFS(root);
                    break;
                case 2:
                    System.out.print("\n BFS travers!\n-> ");
                    while (i <= max_lebel) {
                        BFS(root, i);
                        i++;
                    }
                    i = 0;
                    break;
                case 3:
                    System.out.println("\nShowing every lebel!");
                    while (i <= max_lebel) {
                        System.out.print("lebel[" + i + "]-> ");
                        BFS(root, i);
                        System.out.println();
                        i++;
                    }
                    i = 0;
                    break;
                case 4:
                    System.out.print("Enter value -> ");
                    temp = scan.nextInt();
                    System.out.print("Node Number Found -> ");
                    search(root, temp, "value");
                    break;
                case 5:
                    System.out.print("Enter index -> ");
                    temp = scan.nextInt();
                    System.out.print("Value Found -> ");
                    search(root, temp, "index");
                    break;
                case 6:
                    System.out.print("\n<--[Note] This is still under development,search by node will not work properly!-->\n\n");
                    System.out.print("Enter A value -> ");
                    temp = scan.nextInt();
                    System.out.print("Enter Parant Node value -> ");
                    int parrant = scan.nextInt();
                    root = adding_node(root, parrant, temp);
                    break;
                case 7:
                    System.out.print("Enter A Node value -> ");
                    temp = scan.nextInt();
                    root = delete_node_with_child(root, temp);
                    break;
                case 8:
                    System.out.print("Enter A Node value -> ");
                    temp = scan.nextInt();
                    root = delete_node_without_child(root, temp);
                    break;
                default:
                    System.out.print("Enter Between 1 to 7!");
            }
        }
    }

    public static void main(String[] args) {
        Node root = null;
        System.out.println("<---------WELCOME--------->\n");
        System.out.println("How Would you like to input the graph?");
        System.out.println("Enter 1 for using BST\nEnter 2 for manually input\n");
        System.out.print("Enter-> ");
        switch (scan.nextInt()) {

            case 1:
                System.out.print("\nNumber of Nodes-> ");
                total_node = scan.nextInt();
                System.out.println("Enter Nodes");
                while (count++ < total_node) {
                    level = -1;
                    System.out.print(count + "-> ");
                    root = graph_input_BST(root, scan.nextInt());
                }
                functions(root);
                break;
            case 2:
                System.out.print("\nNumber of Nodes-> ");
                total_node = scan.nextInt();
                System.out.print("\nEnter root-> ");
                root = new Node(scan.nextInt(), count++, level);
                while (count < total_node) {
                    level++;
                    if (level > max_lebel) {
                        max_lebel = level;
                    }
                    root = graph_input_manually(root, level);
                }
                System.out.println("\n<-----Graph Input Complete----->\n");
                functions(root);

            default:
                System.out.println("Enter between 1 or 2!");
        }
    }
}
