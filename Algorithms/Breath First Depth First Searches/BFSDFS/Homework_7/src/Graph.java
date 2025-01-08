
import java.util.LinkedList;
import java.util.Queue;

public class Graph {
    // Private data fields
    private int n; // Number of nodes in the graph
    private int time; // Used in DFS for timestamps
    private boolean[][] edges; // Adjacency matrix
    private Node[] nodes; // Array of Node objects

    // Constructor
    public Graph(boolean[][] edges) {
        this.edges = edges;
        this.n = edges.length; // Number of nodes is the length of the edges array
        this.time = 0; // Initialize time to 0

        // Initialize the nodes array
        this.nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(i); // Each node's index is its name
        }
    }

    // Public method for Depth-First Search
    public void depthFirstSearch() {
        // Reinitialize all nodes for DFS
        for (Node node : nodes) {
            node.setColor(Node.WHITE);
            node.setParent(-1);
            node.setDTime(0);
            node.setFTime(0);
        }
        time = 0; // Reset time

        // Perform DFS for each component
        for (Node node : nodes) {
            if (node.getColor() == Node.WHITE) {
                dfsVisit(node);
            }
        }
    }

    // Private helper method for DFS
    private void dfsVisit(Node u) {
        time++;
        u.setDTime(time); // Set discovery time
        u.setColor(Node.GRAY); // Mark node as visited

        // Explore each neighbor
        int uIndex = u.getName();
        for (int vIndex = 0; vIndex < n; vIndex++) {
            if (edges[uIndex][vIndex]) { // Check if there's an edge
                Node v = nodes[vIndex];
                if (v.getColor() == Node.WHITE) {
                    v.setParent(uIndex);
                    dfsVisit(v); // Recursively visit
                }
            }
        }

        u.setColor(Node.BLACK); // Mark node as fully explored
        time++;
        u.setFTime(time); // Set finish time
    }

    // Public method for Breadth-First Search
    public void breadthFirstSearch(int source) {
        // Reinitialize all nodes for BFS
        for (Node node : nodes) {
            node.setColor(Node.WHITE);
            node.setDist(Integer.MIN_VALUE);
            node.setParent(-1);
        }

        // Initialize the source node
        Node s = nodes[source];
        s.setColor(Node.GRAY);
        s.setDist(0);
        s.setParent(-1);

        // Use a queue for BFS
        Queue<Node> queue = new LinkedList<>();
        queue.add(s);

        while (!queue.isEmpty()) {
            Node u = queue.poll();
            int uIndex = u.getName();

            // Explore each neighbor
            for (int vIndex = 0; vIndex < n; vIndex++) {
                if (edges[uIndex][vIndex]) { // Check if there's an edge
                    Node v = nodes[vIndex];
                    if (v.getColor() == Node.WHITE) {
                        v.setColor(Node.GRAY);
                        v.setDist(u.getDist() + 1);
                        v.setParent(uIndex);
                        queue.add(v);
                    }
                }
            }

            u.setColor(Node.BLACK); // Mark node as fully explored
        }
    }

    // toString method to describe the graph
    @Override
    public String toString() {
    	String end = "Adjacency Matrix: \n";
        for (boolean[] row : edges) {
            for (boolean edge : row) {
                end += (edge ? "1 " : "0 ");
            }
            end+= "\n";
        }

        end += "\nNode Information:\n";
        for (Node node : nodes) {
            end += node.toString();
            end += "\n";
        }

        return end;
    }
}
