package edu.unca.cs.csci343;
import java.net.UnknownHostException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;

import org.json.JSONObject;

import com.vesoft.nebula.driver.graph.data.Node;
import com.vesoft.nebula.driver.graph.data.ResultSet;
import com.vesoft.nebula.driver.graph.data.ValueWrapper;
import com.vesoft.nebula.driver.graph.exception.AuthFailedException;
import com.vesoft.nebula.driver.graph.exception.IOErrorException;
import com.vesoft.nebula.driver.graph.net.NebulaClient;

public class GraphAssignment2 {
    private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger("ConnectionExample");
    private static String cursor_node_id;
    
    private static Node min_dist(Map<Node, Double> dist, List<Node> Q) {
    	Node returnval = null;
    	double min = Double.POSITIVE_INFINITY;
    	for(Map.Entry<Node, Double> e: dist.entrySet()) {
    		if (e.getValue() < min && Q.contains(e.getKey())) {
    			min = e.getValue();
    			returnval = e.getKey();
    		}
    	}
    	return returnval;
    }
    
    
    private static List<Node> Dijkstra(Node startNode, Node endNode, double mpg, NebulaClient client) throws IOErrorException {
        Map<Node, Node> prev = new HashMap<Node, Node>(); // To store the previous node in the shortest path
        Map<Node, Double> dist = new HashMap<Node, Double>(); // To store the shortest distances (costs)
        List<Node> Q = new LinkedList<Node>(); // List of all nodes that are yet to be visited

        // Retrieve all nodes from the graph
        String allNodes = "MATCH (n) return n";
        ResultSet all = client.execute(allNodes);
        
        // Initialize distances
        while (all.hasNext()) {
            ResultSet.Record r = all.next();
            Node n = r.get(0).asNode();
            if (n.equals(startNode)) {
                dist.put(n, 0.0); // Distance from start node to itself is 0
            } else {
                dist.put(n, Double.POSITIVE_INFINITY); // Initialize all other nodes with infinity distance
            }
            Q.add(n); // Add all nodes to the list of unvisited nodes
        }

        // Dijkstra's algorithm loop
        while (!Q.isEmpty()) {
            Node u = min_dist(dist, Q); // Get the node with the smallest distance
            Q.remove(u); // Remove it from the unvisited list

            // If we've reached the end node, break out of the loop
            if (u.equals(endNode)) {
                break;
            }

            // Retrieve neighbors of the current node
            String neighbors = "MATCH ({name: \"" + u.getProperties().get("name").asString() + "\"})"
                    + "~[e]~(n) return n, e.dist, n.gasPrice";  // Fetch gasPrice of neighboring node
            ResultSet allNeighbors = client.execute(neighbors);

            // Update the distances for the neighbors, including gas price cost
            while (allNeighbors.hasNext()) {
                ResultSet.Record r = allNeighbors.next();
                Node v = r.get(0).asNode();
                
                double distance = r.get(1).asDouble();  // Distance of the edge
                double gasPrice = v.getProperties().get("gasPrice").asDouble();  // Gas price of the neighbor
                
                // Calculate the total cost, factoring in the gas price of the neighbor node
                double alt = dist.get(u) + (distance / mpg) * gasPrice;
                
                if (alt < dist.get(v)) { // If the alternative distance is smaller, update it
                    dist.put(v, alt);
                    prev.put(v, u);
                }
            }
        }

        // Reconstruct the path from startNode to endNode by tracing the previous nodes
        List<Node> path = new LinkedList<>();
        Node currentNode = endNode;
        while (currentNode != null) {
            path.add(0, currentNode); // Add to the front of the path list
            currentNode = prev.get(currentNode); // Trace back to the previous node
        }

        // Return the path (from startNode to endNode)
        return path;
    }
    

    private static Node getNodeByName(String name, NebulaClient client) throws IOErrorException {
        // Query to find a node by name
        String query = "MATCH (n {name: \"" + name + "\"}) RETURN n LIMIT 1";
        ResultSet resultSet = client.execute(query);

        if (resultSet.hasNext()) {
            ResultSet.Record record = resultSet.next();
            return record.get(0).asNode();  // Retrieve the node from the query result
            
        } else {
            return null;  // Return null if the node is not found
        }
    }
    
    
    /**
     * This is the "wrapper" method for getNdeByName and Dijkstra, where it takes a start 
     * city, end city, the MPG of your car (you choose) and the client
     * 
     * @param startCity
     * @param endCity
     * @param MPG
     * @param client
     * @throws IOErrorException
     */
    
    public static void Rapper(String startCity, String endCity, double MPG, NebulaClient client) throws IOErrorException{
    	Node start = getNodeByName(startCity, client);
    	Node end = getNodeByName(endCity, client);
    	
    	List<Node> path = Dijkstra(start, end, MPG, client);

    	// Output the path and its cost
    	System.out.println("With a MPG of "+MPG+", the shortest path is:");
    	for (Node node : path) {
    	    System.out.print(node.getProperties().get("name"));
    	    System.out.print("	Gas Price: "+ node.getProperties().get("gasPrice").asDouble());
    	    
    	    System.out.println();
    	}
    }

    
    
    public static void main(String[] args) throws AuthFailedException, IOErrorException, UnknownHostException {
    	String username = "ablazer1";
    	NebulaClient client = NebulaClient.builder("localhost:9669", username, "Nebula1234").build();

    	String schema_select = "SESSION SET SCHEMA `/" + username + "`";
    	client.execute(schema_select);


    	// ############ GQL Statements ##################################
    	String gql_string = "CREATE GRAPH TYPE IF NOT EXISTS HighwaysType AS {\n"
    			+ "NODE City ( {name STRING PRIMARY KEY, gasPrice FLOAT64} ),\n"
    			+ "EDGE Highway (City)~[:Highway {dist FLOAT64} ]~(City)\n"
    			+ "}\n"
    			+ ""; // put your query here
    	
    	client.execute(gql_string);

    	gql_string = "CREATE GRAPH IF NOT EXISTS Highways :: HighwaysType"; // put your query here
    	client.execute(gql_string);

    	gql_string = "SESSION SET GRAPH Highways\n";
    	client.execute(gql_string);

    	//########################

    	//Create graph
    	
    	gql_string = "INSERT OR REPLACE\n"
    			+ "(a@City {name: \"San Francisco\", gasPrice: 8.75}), (b@City {name: \"San Rafael\", gasPrice:\n"
    			+ "8.05}), (c@City {name: \"Richmond\", gasPrice: 7.99}), (d@City {name: \"Oakland\", gasPrice:\n"
    			+ "8.50}), (e@City {name: \"Hayward\", gasPrice: 6.96}), (f@City {name: \"San Mateo\", gasPrice:\n"
    			+ "8.01}), (g@City {name: \"Pacifica\", gasPrice: 9.99}), (h@City {name: \"Halfmoon Bay\",\n"
    			+ "gasPrice: 10.15}), (i@City {name: \"Palo Alto\", gasPrice: 11.82}), (j@City {name: \"Fremont\",\n"
    			+ "gasPrice: 8.01}), (k@City {name: \"San Jose\", gasPrice: 9.10}), (l@City {name: \"Santa Clara\n"
    			+ "\", gasPrice: 6.77}), (m@City {name: \"Scotts Valley\", gasPrice: 7.00}), (n@City {name: \"\n"
    			+ "Santa Cruz\", gasPrice: 6.50}), (o@City {name: \"Watsonville\", gasPrice: 6.09}),\n"
    			+ "(b)~[:Highway {dist: 15}]~(c), (b)~[:Highway {dist: 18}]~(a), (c)~[:Highway {dist: 15}]~(d), (a)\n"
    			+ "~[:Highway {dist: 12}]~(d), (a)~[:Highway {dist: 15}]~(g), (a)~[:Highway {dist: 20}]~(f), (d)\n"
    			+ "~[:Highway {dist: 20}]~(e), (f)~[:Highway {dist: 20}]~(e), (g)~[:Highway {dist: 15}]~(h), (f)~[:\n"
    			+ "Highway {dist: 25}]~(h), (f)~[:Highway {dist: 18}]~(i), (e)~[:Highway {dist: 14}]~(j), (i)~[:\n"
    			+ "Highway {dist: 15}]~(j), (i)~[:Highway {dist: 10}]~(l), (j)~[:Highway {dist: 20}]~(k), (l)~[:\n"
    			+ "Highway {dist: 15}]~(k), (h)~[:Highway {dist: 50}]~(n), (l)~[:Highway {dist: 35}]~(m), (m)~[:\n"
    			+ "Highway {dist: 10}]~(n), (k)~[:Highway {dist: 60}]~(o), (o)~[:Highway {dist: 70}]~(n)\n"
    			+ "";
    	
    	client.execute(gql_string); 	
    	
    	
    	/*
        Assignment: Modify the program above to create a gas saver application. Take as input two cities,
one to start at and one to end at, and find the path between them with minimum gas cost. Assume
you have to buy all of the gas it took to reach a city at the price the city is giving once you get there
(assume some reasonable gas mileage for your car).

        */
    	
        Rapper("San Francisco","Hayward",14.5,client);

        client.close();
    	
    }

}
        
	


