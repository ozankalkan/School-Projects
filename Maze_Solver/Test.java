import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import ADTPackage.*;
import GraphPackage.*;


// !!! Note: The address required for reading text files is located on line 118. !!! 
public class Test {
	public static <T> void main(String[] args) throws FileNotFoundException {
		UndirectedGraph<String> graph = mazeToGraph();
		char[][] graphArray = mazeToArray();
		

		System.out.println("Adjacency Matrix :");
		graph.getAdjacencyMatrix(); // Prints the adjacency matrix to the screen and returns it.
		System.out.println();
		System.out.println("Adjacency List: "); 
		graph.getAdjacencyList(); // Prints the adjacency list to the screen and returns it.
		System.out.println();
		System.out.println("The number of edges found : " + graph.getNumberOfEdges());
		
		System.out.println();
		System.out.println("BreadthFirst");
		System.out.println("------------");
		//The method that takes the graph, start and end points as parameters, and prints the path and visited vertices.
		printBreadthFirstSearch(graph, "0 - 1" , (graphArray.length - 2) + " - " + (graphArray[graphArray.length - 2].length - 1));
		
		System.out.println();
		System.out.println("DepthFirst");
		System.out.println("----------");
		printDepthFirstSearch(graph, "0 - 1" , (graphArray.length - 2) + " - " + (graphArray[graphArray.length - 2].length - 1));

		System.out.println();
		System.out.println("ShortestPath");
		System.out.println("------------");
		printShortestPath(graph, "0 - 1" , (graphArray.length - 2) + " - " + (graphArray[graphArray.length - 2].length - 1));

		System.out.println();
		System.out.println("CheapestPath");
		System.out.println("------------");
		// The method that prints the cheapest path, visited vertices, and the weight of the edges.
		printCheapestPath("0 - 1" , (graphArray.length - 2) + " - " + (graphArray[graphArray.length - 2].length - 1));
		
	}
	
	// The method that converts the maze array into a unweighted graph.
	public static UndirectedGraph<String> mazeToGraph() {
		char[][] graphArray = mazeToArray();
		UndirectedGraph<String> graph = new UndirectedGraph<String>();
		for(int i = 0; i < graphArray.length ; i++) {
			for(int j = 0; j < graphArray[i].length; j++) {
				char newChar = graphArray[i][j];
				if(newChar == ' ') {
					graph.addVertex(i + " - " + j);
					
					if(i != 0) { // If the vertex is at the top, the above of the vertex is not checked
						if(graphArray[i - 1][j] == ' ') {
							int upper = i - 1;
							String upperVertex = upper + " - " + j;
							graph.addEdge(upperVertex, i + " - " + j);
						}
					}
					if(j != 0) { // If the vertex is on the far left, the left of the vertex is not checked
						if(graphArray[i][j - 1] == ' ') {
							int left = j - 1;
							String leftVertex = i + " - " + left;
							graph.addEdge(i + " - " + j,leftVertex);
						}
					}
					
					
				}
			}
		}
		return graph;
	}
	
	// // The method that converts the maze array into a weighted graph.
	public static UndirectedGraph<String> mazeToWeightedGraph() {
		char[][] graphArray = mazeToArray();
		UndirectedGraph<String> graph = new UndirectedGraph<String>();
		for(int i = 0; i < graphArray.length ; i++) {
			for(int j = 0; j < graphArray[i].length; j++) {
				char newChar = graphArray[i][j];
				if(newChar == ' ') {
					graph.addVertex(i + " - " + j);
					
					if(i != 0) {
						if(graphArray[i - 1][j] == ' ') {
							int upper = i - 1;
							String upperVertex = upper + " - " + j;
							int weight = (int)(Math.random()*4 + 1); // Generates random integers 1-4 for edge weights.
							graph.addEdge(upperVertex, i + " - " + j, weight);
						}
					}
					if(j != 0) {
						if(graphArray[i][j - 1] == ' ') {
							int left = j - 1;
							String leftVertex = i + " - " + left;
							int weight = (int)(Math.random() *4 + 1);
							graph.addEdge(i + " - " + j,leftVertex , weight);
						}
					}
					
					
				}
			}
		}
		return graph;
	}
	
	// Reads the txt file of the maze and converts it to an array.
	public static char[][] mazeToArray() {
		try 
		{
			File txtFile = new File ("C:\\Users\\Ozan\\Desktop\\mazes\\maze1.txt");
			Scanner createGraphArray = new Scanner(txtFile);
			Scanner createGraph = new Scanner(txtFile);
			int rowNumber = 0;
			String rowLine = null;
			while(createGraphArray.hasNext()) {
				rowLine = createGraphArray.nextLine();
				rowNumber++;
			}
			int columnNumber = rowLine.length();
			char[][] mazeArray = new char [rowNumber][columnNumber];
			
			String line;
			int i = 0;
			while(createGraph.hasNext()) {
				line = createGraph.nextLine();
				for(int j = 0; j < line.length(); j++) {
					mazeArray[i][j] = line.charAt(j);
				}
				i++;
			}
			createGraphArray.close();
			createGraph.close();
			return mazeArray;
		}
		
		catch(FileNotFoundException e) 
		{
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		      return null;
		    }
		
	}
	
	public static void printBreadthFirstSearch(UndirectedGraph<String> graph, String begin, String end) {
		QueueInterface<String> traversalOrder = new LinkedQueue<>();
		traversalOrder = graph.getBreadthFirstSearch(begin, end);
		char[][] tempGraphArr = mazeToArray();
		while(traversalOrder != null && !traversalOrder.isEmpty()) { // Converts the vertices in the traversalOrder to points in the array
			String index = traversalOrder.dequeue();
			String[] indexes = index.split(" ");
			int i = Integer. parseInt(indexes[0]);
			int j = Integer. parseInt(indexes[2]);
			tempGraphArr[i][j] = '.';
		}
		if(traversalOrder == null) {
			System.out.println("There is no exit from the maze !");
		}
		else {
			System.out.println("BFS output between the starting and end points:");
			for(int a = 0; a< tempGraphArr.length; a++) {
				for(int b = 0; b < tempGraphArr[a].length; b++) {
					System.out.print(tempGraphArr[a][b]);
				}
				System.out.println();
			}
		}
	}
	
	public static void printDepthFirstSearch(UndirectedGraph<String> graph, String begin, String end) {
		QueueInterface<String> traversalOrder = new LinkedQueue<>();
		traversalOrder = graph.getDepthFirstSearch(begin, end);
		char[][] tempGraphArr = mazeToArray();
		while(traversalOrder != null && !traversalOrder.isEmpty()) { //Converts the vertices in the traversalOrder to points in the array
			String index = traversalOrder.dequeue();
			String[] indexes = index.split(" ");
			int i = Integer. parseInt(indexes[0]);
			int j = Integer. parseInt(indexes[2]);
			tempGraphArr[i][j] = '.';
		}
		if(traversalOrder == null) {
			System.out.println("There is no exit from the maze !");
		}
		else {
			System.out.println("DFS output between the starting and end points:");
			for(int a = 0; a< tempGraphArr.length; a++) {
				for(int b = 0; b < tempGraphArr[a].length; b++) {
					System.out.print(tempGraphArr[a][b]);
				}
				System.out.println();
			}
		}
	}
	
	public static void printShortestPath(UndirectedGraph<String> graph, String begin, String end) {
		StackInterface<String> path = new LinkedStack<>();
		graph.getShortestPath(begin, end, path);
		char[][] tempGraphArr = mazeToArray();
		while(path != null && !path.isEmpty()) { //Converts the vertices in the path to points in the array
			String index = path.pop();
			String[] indexes = index.split(" ");
			int i = Integer. parseInt(indexes[0]);
			int j = Integer. parseInt(indexes[2]);
			tempGraphArr[i][j] = '.';
		}
		if(path == null) {
			System.out.println("There is no exit from the maze !");
		}
		else {
			System.out.println("ShortestPath output between the starting and end points:");
			for(int a = 0; a< tempGraphArr.length; a++) {
				for(int b = 0; b < tempGraphArr[a].length; b++) {
					System.out.print(tempGraphArr[a][b]);
				}
				System.out.println();
			}
		}
		
	}
	
	public static void printCheapestPath(String begin, String end) {
		StackInterface<String> path = new LinkedStack<>();
		StackInterface<String> pathForWeights = new LinkedStack<>();
		StackInterface<String> tempPath = new LinkedStack<>();
		UndirectedGraph<String> graph = mazeToWeightedGraph();
		double costOfCheapestPath = graph.getCheapestPath(begin, end, path);
		char[][] tempGraphArr = mazeToArray();
		while(!path.isEmpty()) {
			tempPath.push(path.peek());
			path.pop();
		}
		while(!tempPath.isEmpty()){
			path.push(tempPath.peek());
			pathForWeights.push(tempPath.peek());
			tempPath.pop();
		}
		
		
		while(path != null && !path.isEmpty()) { // //Converts the vertices in the path to points in the array
			String index = path.pop();
			String[] indexes = index.split(" ");
			int i = Integer. parseInt(indexes[0]);
			int j = Integer. parseInt(indexes[2]);
			tempGraphArr[i][j] = '.';
		}
		if(path == null) {
			System.out.println("There is no exit from the maze !");
		}
		else {
			System.out.println("CheapestPath output between the starting and end points:");
			for(int a = 0; a< tempGraphArr.length; a++) {
				for(int b = 0; b < tempGraphArr[a].length; b++) {
					System.out.print(tempGraphArr[a][b]);
				}
				System.out.println();
			}
		}
		System.out.println("The cost of the cheapest path : " + costOfCheapestPath);
		graph.getWeightsOfTraversal(pathForWeights); // a method that prints the weights of all the edges passed for the cheapest path
		System.out.println("");
		System.out.println(" ");
		graph.getWeightOfEdges(); // a method that prints the weights of all the edges
	}

	

}

