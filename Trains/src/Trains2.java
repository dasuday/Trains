import org.jgraph.*;
import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.alg.BellmanFordShortestPath;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.alg.KruskalMinimumSpanningTree;
import org.jgrapht.alg.AllDirectedPaths;

import java.io.*;
import java.util.List;
import java.util.Vector;

public class Trains2 {

	private static SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> trainsGraph = null;
	private static AllDirectedPaths allPaths = null; 
	private static List<GraphPathImpl<String, DefaultWeightedEdge>> trainsList = null;
	private static DijkstraShortestPath<String, DefaultWeightedEdge> dPath = null;

	//Method to calculate and display the path cost given a path as a string (ex: "v1-v2-v3-..."; where v1, v2, etc. are vertices on the path). 
	//If no such path exists the method dsiplays "No such route!"
	public static void calcPath(String testpath){
		DefaultWeightedEdge testedge = null;
		int testLength = 0;
		String[] pathVertices;

		pathVertices = testpath.split("-");
		for(int i=0; i<pathVertices.length-1; i++){
			testedge = trainsGraph.getEdge(pathVertices[i], pathVertices[i+1]);
			if(testedge == null){
				System.out.println("No such route!");
				break;
			}
			testLength = testLength + (int)trainsGraph.getEdgeWeight(testedge);
		}
		if(testedge != null)
			System.out.println(testLength);
	}

	//Method to discover and display how many paths of a certain number of hops or less exist between the given source and target vertices. 
	//Displays 0 if there are no such routes.
	//Boolean "showPaths" controls whether the calculated paths are shown.
	public static void discoverRoutes(String source, String target, Boolean exactHops, int maxHops, Boolean showPaths, Boolean isHops){
		trainsList = allPaths.getAllPaths (source, target, false, maxHops);
		if(!exactHops && isHops){
			System.out.println(trainsList.size());
			if(showPaths){
				for(int i=0; i<trainsList.size(); i++){
					System.out.println(trainsList.get(i).toString());
				}
			}
		}
		else if (isHops){
			Vector paths = new Vector();
			for(int i=0; i<trainsList.size(); i++){
				if(trainsList.get(i).getEdgeList().size() == 4){
					paths.add(trainsList.get(i));
				}
			}
			System.out.println(paths.size());
			if(showPaths){
				for(int i=0; i<paths.size(); i++){
					System.out.println(paths.get(i).toString());
				}
			}
		}
		else{
			Vector pathIndexes = new Vector();
			Vector pathLengths = new Vector();
			List<DefaultWeightedEdge> pathsList = null;
			int pathLength;
			for(int i=0; i<trainsList.size(); i++){
				pathLength = 0;
				pathsList = trainsList.get(i).getEdgeList();
				for(int j=0; j<pathsList.size(); j++){
					pathLength = pathLength + (int)trainsGraph.getEdgeWeight(pathsList.get(j));
				}

				if(pathLength < maxHops){
					//System.out.println(pathLength);
					pathLengths.add(pathLength);
					pathIndexes.add(i);
				}
			}

			System.out.println(pathIndexes.size());

			if(showPaths)
				for(int i=0; i<pathIndexes.size(); i++)
					System.out.println(trainsList.get((int)pathIndexes.get(i)).toString());
		}
	}

	//Method to find the shortestPath from a source vertex to a target vertex. 
	//If the source and target are different, this is simply a call to the JGraphT Dijkstra's algorithm implementation
	//Otherwise, we use the AllDirectedGraphs Class to extract all routes and calculate the shortest path. 
	//(JGraphT Dijkstra, Bellman-Ford and other Shortest Path Algorithms will return 0 when source and target are the same.)
	public static void shortestPath(String source, String target, Boolean showPaths){
		if (source.equals(target)){
		Vector pathLengths = new Vector();
		List<DefaultWeightedEdge> pathsList = null;
		int pathLength;
		trainsList = allPaths.getAllPaths(source, target, false, trainsGraph.edgeSet().size());
		//System.out.println(trainsList.size());
		for(int i=0; i<trainsList.size(); i++){
			pathLength = 0;
			pathsList = trainsList.get(i).getEdgeList();
			for(int j=0; j<pathsList.size(); j++){
				pathLength = pathLength + (int)trainsGraph.getEdgeWeight(pathsList.get(j));
			}
			//System.out.println(pathLength);
			pathLengths.add(pathLength);
		}

		int min = (int)pathLengths.get(0);
		int minIndex = 0;
		for(int i=1; i<trainsList.size(); i++){
			if(min > (int)pathLengths.get(i)){
				min = (int)pathLengths.get(i);
				minIndex = i;
			}
		}
		System.out.println(min);

		if(showPaths)
			System.out.println(trainsList.get(minIndex).toString());
		}
		else{
			dPath = new DijkstraShortestPath(trainsGraph, source, target);
			System.out.println((int)dPath.getPathLength());
			
			if(showPaths)
				System.out.println(dPath.getPath().toString());
		}
	}
	

	public static void main(String[] args) throws Exception{

		//Ask user for filename		
		BufferedReader userIP = new BufferedReader(new InputStreamReader(System.in));
		//System.out.println("Please enter the input file name:");
		//String filename = userIP.readLine();

		//Create new empty graph
		trainsGraph = new SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);

		//Read input file and load the input graph
		FileReader filereader = new FileReader("input.txt");
		BufferedReader fileread = new BufferedReader(filereader);

		String line = null;

		while((line = fileread.readLine()) != null){
			// Read line from file and split into origin vertex, incident vertex and edge weight
			String ipSplits[] = line.split(",");

			//Add new edge to graph; then add edge weight
			trainsGraph.addVertex(ipSplits[0]);
			trainsGraph.addVertex(ipSplits[1]);
			DefaultWeightedEdge e = trainsGraph.addEdge(ipSplits[0], ipSplits[1]);
			trainsGraph.setEdgeWeight(e, Double.valueOf(ipSplits[2]));
		}

		//Housekeeping for problem solutions 6, 7, 9 & 10 below
		allPaths = new AllDirectedPaths(trainsGraph);

		//1. The distance of the route A-B-C.
		System.out.println("\nAnswer 1: \n");
		calcPath("A-B-C");

		//1. The distance of the route A-B-C.
		System.out.println("\nAnswer 2: \n");
		calcPath("A-D");

		//3. The distance of the route A-D-C.
		System.out.println("\nAnswer 3: \n");
		calcPath("A-D-C");


		//4. The distance of the route A-E-B-C-D.
		System.out.println("\nAnswer 4: \n");
		calcPath("A-E-B-C-D");


		//5. The distance of the route A-E-D.
		System.out.println("\nAnswer 5: \n");
		calcPath("A-E-D");


		/*6. The number of trips starting at C and ending at C with a maximum of 3
 		stops. In the sample data below, there are two such trips: C-D-C (2
 		stops). and C-E-B-C (3 stops).*/
		System.out.println("\nAnswer 6: \n");
		discoverRoutes("C","C", false, 3, true, true);


		/* 7. The number of trips starting at A and ending at C with exactly 4 stops.
		 In the sample data below, there are three such trips: A to C (via B,C,D); A
		 to C (via D,C,D); and A to C (via D,E,B).*/
		System.out.println("\nAnswer 7: \n");
		discoverRoutes("A","C", true, 4, true, true);

		/*8. The length of the shortest route (in terms of distance to travel) from A
		 to C.*/
		System.out.println("\nAnswer 8: \n");
		shortestPath("A", "C", true);

		/*
		 9. The length of the shortest route (in terms of distance to travel) from B
		 to B.*/
		System.out.println("\nAnswer 9: \n");
		shortestPath("B","B", true);

		/*Question 10: The number of different routes from C to C with a distance of less than 
		 30. In the sample data, the trips are: CDC, CEBC, CEBCDC, CDCEBC, CDEBC,
		 CEBCEBC, CEBCEBCEBC.*/
		System.out.println("\nAnswer 10: \n");
		discoverRoutes("C","C",false, 30, true, false);
	}

}
