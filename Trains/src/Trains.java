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

public class Trains {



	public static void main(String[] args) throws Exception{

		//Ask user for filename		
		BufferedReader userIP = new BufferedReader(new InputStreamReader(System.in));
		//System.out.println("Please enter the input file name:");
		//String filename = userIP.readLine();

		//Create new empty graph
		SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> trainsGraph = new SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);

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

		//Housekeeping for problem solutions 1-5
		String testpath = null;
		DefaultWeightedEdge testedge = null;
		int testLength = 0;
		String[] pathVertices;

		//1. The distance of the route A-B-C.
		System.out.println("\nAnswer 1: \n");
		testpath = "A-B-C";
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
		testLength = 0;

		//2. The distance of the route A-D.
		System.out.println("\nAnswer 2: \n");
		testpath = "A-D";
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
		testLength = 0;
		
		//3. The distance of the route A-D-C.
		System.out.println("\nAnswer 3: \n");
		testpath = "A-D-C";
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
		testLength = 0;
		
		//4. The distance of the route A-E-B-C-D.
		System.out.println("\nAnswer 4: \n");
		testpath = "A-E-B-C-D";
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
		testLength = 0;
		
		//5. The distance of the route A-E-D.
		System.out.println("\nAnswer 5: \n");
		testpath = "A-B-D";
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
		testLength = 0;
		
		//Housekeeping for problem solutions 6, 7, 9 & 10 below
		AllDirectedPaths allPaths = new AllDirectedPaths(trainsGraph);
		List<GraphPathImpl<String, DefaultWeightedEdge>> trainsList = null;

		/*6. The number of trips starting at C and ending at C with a maximum of 3
 		stops. In the sample data below, there are two such trips: C-D-C (2
 		stops). and C-E-B-C (3 stops).*/
		System.out.println("\nAnswer 6: \n");
		trainsList = allPaths.getAllPaths("C", "C", false, 3);
		System.out.println(trainsList.size());
		for(int i=0; i<trainsList.size(); i++){
			System.out.println(trainsList.get(i).toString());
		}


		/* 7. The number of trips starting at A and ending at C with exactly 4 stops.
		 In the sample data below, there are three such trips: A to C (via B,C,D); A
		 to C (via D,C,D); and A to C (via D,E,B).*/
		System.out.println("\nAnswer 7: \n");
		trainsList = allPaths.getAllPaths("A", "C", false, 4);
		/*System.out.println(trainsList.size());
		for(int i=0; i<trainsList.size(); i++){
			System.out.println(trainsList.get(i).toString());
		}*/
		Vector paths = new Vector();
		for(int i=0; i<trainsList.size(); i++){
			if(trainsList.get(i).getEdgeList().size() == 4){
				paths.add(trainsList.get(i));
			}
		}
		System.out.println(paths.size());
		for(int i=0; i<paths.size(); i++){
			System.out.println(paths.get(i).toString());
		}

		//Housekeeping for problem solutions 8 & 9 below
		DijkstraShortestPath<String, DefaultWeightedEdge> dPath = null;

		/*8. The length of the shortest route (in terms of distance to travel) from A
		 to C.*/
		System.out.println("\nAnswer 8: \n");
		dPath = new DijkstraShortestPath(trainsGraph, "A", "C");
		System.out.println((int)dPath.getPathLength());

		//Housekeeping items for problem solutions 9 & 10 below
		Vector pathLengths = new Vector();
		List<DefaultWeightedEdge> pathsList = null;
		char source, target;
		int pathLength;

		/*
		 9. The length of the shortest route (in terms of distance to travel) from B
		 to B.*/
		System.out.println("\nAnswer 9: \n");
		trainsList = allPaths.getAllPaths("B", "B", false, trainsGraph.edgeSet().size());
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
		System.out.println(trainsList.get(minIndex).toString());

		/*Question 10: The number of different routes from C to C with a distance of less than 
		 30. In the sample data, the trips are: CDC, CEBC, CEBCDC, CDCEBC, CDEBC,
		 CEBCEBC, CEBCEBCEBC.*/
		System.out.println("\nAnswer 10: \n");
		trainsList = allPaths.getAllPaths("C", "C", false, 30);
		Vector pathIndexes = new Vector();
		for(int i=0; i<trainsList.size(); i++){
			pathLength = 0;
			pathsList = trainsList.get(i).getEdgeList();
			for(int j=0; j<pathsList.size(); j++){
				pathLength = pathLength + (int)trainsGraph.getEdgeWeight(pathsList.get(j));
			}

			if(pathLength < 30){
				//System.out.println(pathLength);
				pathLengths.add(pathLength);
				pathIndexes.add(i);
			}
		}

		System.out.println(pathIndexes.size());

		for(int i=0; i<pathIndexes.size(); i++)
			System.out.println(trainsList.get((int)pathIndexes.get(i)).toString());
	}

}
