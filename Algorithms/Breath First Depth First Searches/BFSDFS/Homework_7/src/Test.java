
public class Test {
    public static void main(String[] args) {
        // Define three graphs for BFS
        boolean[][] bfsGraph1 = {
            {false, true, true, false, false},
            {true, false, true, true, false},
            {true, true, false, false, true},
            {false, true, false, false, true},
            {false, false, true, true, false}
        };

        boolean[][] bfsGraph2 = {
            {false, true, true, false, false, false},
            {true, false, false, true, false, false},
            {true, false, false, false, true, false},
            {false, true, false, false, true, false},
            {false, false, true, true, false, true},
            {false, false, false, false, true, false}
        };

        boolean[][] bfsGraph3 = {
            {false, true, false, false, false, true},
            {true, false, true, false, false, false},
            {false, true, false, true, false, false},
            {false, false, true, false, true, false},
            {false, false, false, true, false, true},
            {true, false, false, false, true, false}
        };

        // Perform BFS on each graph
        System.out.println("BFS on Graph 1:");
        Graph graph1 = new Graph(bfsGraph1);
        graph1.breadthFirstSearch(0);
        System.out.println(graph1);

        System.out.println("BFS on Graph 2:");
        Graph graph2 = new Graph(bfsGraph2);
        graph2.breadthFirstSearch(0);
        System.out.println(graph2);

        System.out.println("BFS on Graph 3:");
        Graph graph3 = new Graph(bfsGraph3);
        graph3.breadthFirstSearch(0);
        System.out.println(graph3);

        // Define three graphs for DFS
        boolean[][] dfsGraph1 = {
            {false, true, false, false, true, false},
            {true, false, true, false, false, false},
            {false, true, false, true, false, false},
            {false, false, true, false, true, false},
            {true, false, false, true, false, true},
            {false, false, false, false, true, false}
        };

        boolean[][] dfsGraph2 = {
            {false, true, true, false, false, false},
            {true, false, false, true, false, false},
            {true, false, false, false, true, false},
            {false, true, false, false, true, false},
            {false, false, true, true, false, true},
            {false, false, false, false, true, false}
        };

        boolean[][] dfsGraph3 = {
        	    {false, true,  true,  true,  true,  false, false, false}, // Node 1
        	    {false, false, true,  false, false, true,  false, false}, // Node 2
        	    {false, false, false, false, false, false, true,  false}, // Node 3
        	    {false, false, true,  false, false, false, true,  false}, // Node 4
        	    {false, false, false, true,  false, false, false, true }, // Node 5
        	    {false, false, true,  false, false, false, true,  true }, // Node 6
        	    {false, false, false, false, false, false, false, false}, // Node 7
        	    {false, false, false, false, false, false, true,  false}  // Node 8
        	};

        // Perform DFS on each graph
        System.out.println("DFS on Graph 1:");
        Graph graph4 = new Graph(dfsGraph1);
        graph4.depthFirstSearch();
        System.out.println(graph4);

        System.out.println("DFS on Graph 2:");
        Graph graph5 = new Graph(dfsGraph2);
        graph5.depthFirstSearch();
        System.out.println(graph5);

        System.out.println("DFS on Graph 3:");
        Graph graph6 = new Graph(dfsGraph3);
        graph6.depthFirstSearch();
        System.out.println(graph6);
    }
}
