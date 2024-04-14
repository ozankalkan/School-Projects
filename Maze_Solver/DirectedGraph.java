package GraphPackage;
import java.util.Iterator;
import ADTPackage.*; // Classes that implement various ADTs
import GraphPackage.Vertex.Edge;
/**
 A class that implements the ADT directed graph.
 @author Frank M. Carrano
 @author Timothy M. Henry
 @version 5.1
 */
public class DirectedGraph<T> implements GraphInterface<T>
{
   private DictionaryInterface<T, VertexInterface<T>> vertices;
   private int edgeCount;
   
   public DirectedGraph()
   {
      vertices = new UnsortedLinkedDictionary<>();
      edgeCount = 0;
   } // end default constructor

   public boolean addVertex(T vertexLabel)
   {
      VertexInterface<T> addOutcome = vertices.add(vertexLabel, new Vertex<>(vertexLabel));
      return addOutcome == null; // Was addition to dictionary successful?
   } // end addVertex
   
   public boolean addEdge(T begin, T end, double edgeWeight)
   {
      boolean result = false;
      VertexInterface<T> beginVertex = vertices.getValue(begin);
      VertexInterface<T> endVertex = vertices.getValue(end);
      if ( (beginVertex != null) && (endVertex != null) )
         result = beginVertex.connect(endVertex, edgeWeight);
      if (result)
         edgeCount++;
      return result;
   } // end addEdge
   
   public boolean addEdge(T begin, T end)
   {
      return addEdge(begin, end, 0);
   } // end addEdge

   public boolean hasEdge(T begin, T end)
   {
      boolean found = false;
      VertexInterface<T> beginVertex = vertices.getValue(begin);
      VertexInterface<T> endVertex = vertices.getValue(end);
      if ( (beginVertex != null) && (endVertex != null) )
      {
         Iterator<VertexInterface<T>> neighbors = beginVertex.getNeighborIterator();
         while (!found && neighbors.hasNext())
         {
            VertexInterface<T> nextNeighbor = neighbors.next();
            if (endVertex.equals(nextNeighbor))
               found = true;
         } // end while
      } // end if
      
      return found;
   } // end hasEdge

	public boolean isEmpty()
	{
	  return vertices.isEmpty();
	} // end isEmpty

	public void clear()
	{
	  vertices.clear();
	  edgeCount = 0;
	} // end clear

	public int getNumberOfVertices()
	{
	  return vertices.getSize();
	} // end getNumberOfVertices

	public int getNumberOfEdges()
	{
	  return edgeCount;
	} // end getNumberOfEdges

	protected void resetVertices()
	{
	   Iterator<VertexInterface<T>> vertexIterator = vertices.getValueIterator();
	   while (vertexIterator.hasNext())
	   {
	      VertexInterface<T> nextVertex = vertexIterator.next();
	      nextVertex.unvisit();
	      nextVertex.setCost(0);
	      nextVertex.setPredecessor(null);
	   } // end while
	} // end resetVertices
	
	public StackInterface<T> getTopologicalOrder() 
	{
		resetVertices();

		StackInterface<T> vertexStack = new LinkedStack<>();
		int numberOfVertices = getNumberOfVertices();
		for (int counter = 1; counter <= numberOfVertices; counter++)
		{
			VertexInterface<T> nextVertex = findTerminal();
			nextVertex.visit();
			vertexStack.push(nextVertex.getLabel());
		} // end for
		
		return vertexStack;	
	} // end getTopologicalOrder
	
	
   //###########################################################################
   
	public QueueInterface<T> getBreadthFirstSearch(T origin, T end) {
		resetVertices();
		int numberOfVisitedVertices = 0;
		QueueInterface<T> traversalOrder = new LinkedQueue<>();
		QueueInterface<VertexInterface<T>> vertexQueue = new LinkedQueue<>();
		VertexInterface<T> originVertex = vertices.getValue(origin);
		VertexInterface<T> endVertex = vertices.getValue(end);
		boolean done = false;
		originVertex.visit();
		numberOfVisitedVertices++;
		traversalOrder.enqueue(originVertex.getLabel());
		vertexQueue.enqueue(originVertex);
		while(!done && !vertexQueue.isEmpty()) { 
			VertexInterface<T> frontVertex = vertexQueue.dequeue();
			Iterator<VertexInterface<T>> vertexIterator = frontVertex.getNeighborIterator();
			while(vertexIterator.hasNext()) {
				VertexInterface<T> nextNeighbor = vertexIterator.next();
				if(!nextNeighbor.isVisited()){
					nextNeighbor.visit();
					numberOfVisitedVertices++;
					traversalOrder.enqueue(nextNeighbor.getLabel());
					vertexQueue.enqueue(nextNeighbor);
				}
				if(nextNeighbor.equals(endVertex)) {
					done = true;
				}
			}
		}
		System.out.println("Number Of Visited Vertices For BreadthFirstSearch : " +  numberOfVisitedVertices);
		if(!done) {
			return null;
		}
		else
			return traversalOrder;
	}

    //return the traversal order between origin vertex and end vertex
    

  
	
	
   //###########################################################################
   
	public QueueInterface<T> getDepthFirstSearch(T origin, T end) {
		resetVertices();
		int numberOfVisitedVertices = 0;
		QueueInterface<T> traversalOrder = new LinkedQueue<>();
		StackInterface<VertexInterface<T>> vertexStack = new LinkedStack<>();
		VertexInterface<T> originVertex = vertices.getValue(origin);
		VertexInterface<T> endVertex = vertices.getValue(end);
		boolean done = false;
		originVertex.visit();
		numberOfVisitedVertices++;
		traversalOrder.enqueue(originVertex.getLabel());
		vertexStack.push(originVertex);
		while(!done && !vertexStack.isEmpty()) {
			VertexInterface<T> topVertex = vertexStack.peek();
			
			VertexInterface<T> nextNeighbor = topVertex.getUnvisitedNeighbor();
			if(nextNeighbor != null) { 
				nextNeighbor.visit();
				numberOfVisitedVertices++;
				traversalOrder.enqueue(nextNeighbor.getLabel());
				vertexStack.push(nextNeighbor);
			}
			else {
				vertexStack.pop();
			}
			
			if(nextNeighbor != null) {
				if(nextNeighbor.equals(endVertex))
					done = true;
			}
			
			
		}
		System.out.println("Number Of Visited Vertices For DepthFirstSearch : " +  numberOfVisitedVertices);
		if(!done) {
			return null;
		}
		else
			return traversalOrder;
		
	}
   //return depth first search traversal order between origin vertex and end vertex
    
   //###########################################################################
		
	
	
	
	//###########################################################################

	public int getShortestPath(T begin, T end, StackInterface<T> path) {
		resetVertices();
		boolean done = false;
		int numberOfVisitedVertices = 0;
		QueueInterface<VertexInterface<T>> vertexQueue = new LinkedQueue<VertexInterface<T>>();
		VertexInterface<T> beginVertex = vertices.getValue(begin);
		VertexInterface<T> endVertex = vertices.getValue(end);
		beginVertex.visit();
		numberOfVisitedVertices++;
		vertexQueue.enqueue(beginVertex);
		
		
		while(!done && !vertexQueue.isEmpty()) {
			VertexInterface<T> frontVertex = vertexQueue.dequeue();
			Iterator<VertexInterface<T>> vertexIterator = frontVertex.getNeighborIterator();
			while(!done && vertexIterator.hasNext()) {
				VertexInterface<T> nextNeighbor = vertexIterator.next();
				if(!nextNeighbor.isVisited()) {
					nextNeighbor.visit();
					numberOfVisitedVertices++;
					nextNeighbor.setCost(frontVertex.getCost() + 1);
					nextNeighbor.setPredecessor(frontVertex);
					vertexQueue.enqueue(nextNeighbor);
				}
				if(nextNeighbor.equals(endVertex)) {
					done = true;
				}
			}
		}
		int pathLength = (int) endVertex.getCost();
		path.push(endVertex.getLabel());
		VertexInterface<T> vertex = endVertex;
		
		while(vertex.hasPredecessor()) {
			vertex = vertex.getPredecessor();
			path.push(vertex.getLabel());
		}
		System.out.println("Number Of Visited Vertices For ShortestPath : " +  numberOfVisitedVertices);
		return pathLength;
		
	}
	
	
	
	    //	return the shortest path between begin vertex and end vertex
	    
    //###########################################################################
  
   
	
   
    //###########################################################################
	
      public double getCheapestPath(T begin, T end, StackInterface<T> path) {
    	resetVertices();
    	boolean done = false;
		int numberOfVisitedVertices = 0;
    	VertexInterface<T> beginVertex = vertices.getValue(begin);
		VertexInterface<T> endVertex = vertices.getValue(end);
    	PriorityQueueInterface<EntryPQ> priorityQueue = new HeapPriorityQueue<EntryPQ>();
    	priorityQueue.add(new EntryPQ(beginVertex, 0 ,null));
    	while(!done && !priorityQueue.isEmpty()) {
    		EntryPQ frontEntry = priorityQueue.remove();
    		VertexInterface<T> frontVertex = frontEntry.getVertex();
    		if(!frontVertex.isVisited()) {
    			frontVertex.visit();
    			numberOfVisitedVertices ++;
    			frontVertex.setCost(frontEntry.getCost());
    			frontVertex.setPredecessor(frontEntry.getPredecessor());
    			if(frontVertex.equals(endVertex))
    				done = true;
    			else {
    				Iterator<VertexInterface<T>> vertexIterator = frontVertex.getNeighborIterator();
    				Iterator<Double> weightIterator = frontVertex.getWeightIterator();
    				while(vertexIterator.hasNext() && weightIterator.hasNext()) {
    					VertexInterface<T> nextNeighbor = vertexIterator.next();
    					double weightOfEdgeToNeighbor = weightIterator.next();
    							
    					if(!nextNeighbor.isVisited()) {
    						double nextCost = weightOfEdgeToNeighbor + frontVertex.getCost();
    						priorityQueue.add(new EntryPQ(nextNeighbor, nextCost, frontVertex));
    					}
    				}
    				
    				
    			}
    		}
    		
    	}
    	  
    	double pathCost = endVertex.getCost();
    	path.push(endVertex.getLabel());
    	VertexInterface<T> vertex = endVertex;
    	while(vertex.hasPredecessor()) {
    		vertex = vertex.getPredecessor();
    		path.push(vertex.getLabel());
    	}
    	System.out.println("Number Of Visited Vertices For CheapestPath : " +  numberOfVisitedVertices);
    	return pathCost;  
      }
      
     //		return the cost of the cheapest path
     
    //###########################################################################
      
      
      // Prints the adjacency list and returns it.
      public ListInterface<ListInterface<T>> getAdjacencyList() {
    	  Iterator<VertexInterface<T>> vertexIterator = vertices.getValueIterator();
    	  ListInterface<ListInterface<T>> allAdjacencyList= new LinkedListWithIterator<>();
    	  
    	  
  		while (vertexIterator.hasNext())
  		{
  			ListInterface<T> adjacencyList= new LinkedListWithIterator<>();
  			Vertex<T> vertex =(Vertex<T>) vertexIterator.next();
  			adjacencyList.add(vertex.getLabel());
  			Iterator<VertexInterface<T>> neighbors = vertex.getNeighborIterator();
  	        		
  			while (neighbors.hasNext())
  			{
  				Vertex<T> tempVertex = (Vertex<T>)neighbors.next();	
  				adjacencyList.add(tempVertex.getLabel());
  			}
  			
  			allAdjacencyList.add(adjacencyList);
  		}
  		for (int a = allAdjacencyList.getLength(); a >= 1 ; a --) {
  			ListInterface<T> entry = allAdjacencyList.getEntry(a);
  			for(int b = 1; b <= entry.getLength(); b++) {
  				if(b == 1) {
  					System.out.print(entry.getEntry(b) + " --> ");
  				}
  				else
  					System.out.print(entry.getEntry(b) + " / ");
  			}
  			System.out.println();
  		}
  		return allAdjacencyList; 
      }
      
   // Prints the adjacency matrix and returns it.
      public String[][] getAdjacencyMatrix() {
    	  int size = getNumberOfVertices() + 1;
    	  String [][] adjacencyMatrixForPrint = new String [size][size];
    	  String [][] adjacencyMatrix = new String [size][size];
    	  adjacencyMatrixForPrint[0][0] = "        |";
    	  adjacencyMatrix[0][0] = " ";
    	  Iterator<VertexInterface<T>> vertexIterator = vertices.getValueIterator();
    	  int upperSize = size - 1;
    	  while (vertexIterator.hasNext()) {
    		  Vertex<T> vertex =(Vertex<T>) vertexIterator.next();
    		  adjacencyMatrixForPrint[0][upperSize] = " "+ vertex.getLabel() +" |";
    		  adjacencyMatrix[0][upperSize] = (String)vertex.getLabel();
    		  upperSize --;
    	  }
    	  
    	  Iterator<VertexInterface<T>> allVertexIterator = vertices.getValueIterator();
    	  StackInterface<VertexInterface<T>> vertexStack = new LinkedStack<>();
    	  while (allVertexIterator.hasNext()) {
    		  Vertex<T> vertex =(Vertex<T>) allVertexIterator.next();
    		  vertexStack.push(vertex);
    	  }
    	  int i = 1;
    	  
    	  while (!vertexStack.isEmpty()) {
    		  Vertex<T> vertex =(Vertex<T>) vertexStack.pop();
    		  int j = 0;
    		  adjacencyMatrixForPrint[i][j] = "| " + vertex.getLabel() + " |";
    		  adjacencyMatrix[i][j] = (String) vertex.getLabel();
    		  j = j + 1;
    		  Iterator<VertexInterface<T>> innerVertexIterator = vertices.getValueIterator();
    		  StackInterface<VertexInterface<T>> innerVertexStack = new LinkedStack<>();
        	  while (innerVertexIterator.hasNext()) {
        		  Vertex<T> innerVertex =(Vertex<T>) innerVertexIterator.next();
        		  innerVertexStack.push(innerVertex);
        	  }
    		  
    		  while(!innerVertexStack.isEmpty()) {
    			  Vertex<T> innerVertex =(Vertex<T>) innerVertexStack.pop();
    			  if(hasEdge(vertex.getLabel(), innerVertex.getLabel())) {
    				  adjacencyMatrixForPrint[i][j] = "   1   |";
    				  adjacencyMatrix[i][j] = "1";
    			  }
    			  else {
    				  adjacencyMatrixForPrint[i][j] = "   0   |";
    				  adjacencyMatrix[i][j] = "0";
    			  }
    				  
    			  j++;
    		  }
    		  i++;
    		  
    		  
    	  }
    	  for(int a = 0 ; a < adjacencyMatrixForPrint.length ; a++) {
    		  for(int b = 0; b < adjacencyMatrixForPrint[a].length ; b++) {
    			  System.out.print(adjacencyMatrixForPrint[a][b]);
    		  }
    		  System.out.println();
    	  }
    	  return adjacencyMatrix;
      }

      // Prints the weights of all edges in the graph.
      public void getWeightOfEdges() {
    	Iterator<VertexInterface<T>> vertexIterator = vertices.getValueIterator();
        System.out.println("Random weights for all edges :"); 		
  		while(vertexIterator.hasNext()) {
  			boolean print = false;
  			Vertex<T> vertex =(Vertex<T>) vertexIterator.next(); 
  			Iterator<VertexInterface<T>> neighbors = vertex.getNeighborIterator();
  	  		Iterator<Double> weightIterator = vertex.getWeightIterator();
  	  		if(neighbors.hasNext()) {
  	  			print = true;
  	  		}
  			while (neighbors.hasNext())
  	  		{
  	  			Vertex<T> neighborVertex = (Vertex<T>)neighbors.next();	
  	  			System.out.print("("+ vertex.getLabel() + ")--" + weightIterator.next() + "--("+ neighborVertex.getLabel() + ")  ");
  	  		}
  			if(print == true)
  				System.out.println();
  		}
      }
      
      // Prints the weights of the edges that on the cheapest path.
      public void getWeightsOfTraversal(StackInterface<T> path) {
    	  Iterator<VertexInterface<T>> vertexIterator = vertices.getValueIterator();
    	  StackInterface<T> pathForWeights = new LinkedStack<>();
    	  StackInterface<String> pathStrings = new LinkedStack<>();
    	  System.out.println("Weights for CheapestPath: ");
    	  while(!path.isEmpty()) {
    		  pathForWeights.push(path.pop());
    	  }
    	  
    	  double weight = 0;
    	  while(vertexIterator.hasNext() && !pathForWeights.isEmpty()) {
    		  Vertex<T> vertex =(Vertex<T>) vertexIterator.next(); 
    		  Iterator<VertexInterface<T>> neighbors = vertex.getNeighborIterator();
    		  Iterator<Double> weightIterator = vertex.getWeightIterator();
    		  
    		  if(!pathForWeights.isEmpty() && pathForWeights.peek().equals(vertex.getLabel())) {
    			  T vertexLabel = pathForWeights.pop();
    			  String pathString = "("+ vertexLabel + ")";
    			  pathStrings.push(pathString);
    			  Vertex<T> neighborVertex = (Vertex<T>)neighbors.next();
    			  weight = weightIterator.next();
    			  while(!pathForWeights.isEmpty() && !(pathForWeights.peek().equals(neighborVertex.getLabel()))) {
    				  neighborVertex = (Vertex<T>)neighbors.next();
    				  weight = weightIterator.next();
    			  }
    			  if(!pathForWeights.isEmpty()) {
    				  pathString = "--" + weight + "--";
    				  pathStrings.push(pathString);
    			  }
    				  
    		  }
    		  
    	  }
    	  while(!pathStrings.isEmpty()) {
    		  System.out.print(pathStrings.pop());
    	  }
    	  
      }
      
	
	protected VertexInterface<T> findTerminal()
	{
		boolean found = false;
		VertexInterface<T> result = null;

		Iterator<VertexInterface<T>> vertexIterator = vertices.getValueIterator();

		while (!found && vertexIterator.hasNext())
		{
			VertexInterface<T> nextVertex = vertexIterator.next();
			
			// If nextVertex is unvisited AND has only visited neighbors)
			if (!nextVertex.isVisited())
			{ 
				if (nextVertex.getUnvisitedNeighbor() == null )
				{ 
					found = true;
					result = nextVertex;
				} // end if
			} // end if
		} // end while

		return result;
	} // end findTerminal

	// Used for testing
	public void displayEdges()
	{
		System.out.println("\nEdges exist from the first vertex in each line to the other vertices in the line.");
		System.out.println("(Edge weights are given; weights are zero for unweighted graphs):\n");
		Iterator<VertexInterface<T>> vertexIterator = vertices.getValueIterator();
		while (vertexIterator.hasNext())
		{
			((Vertex<T>)(vertexIterator.next())).display();
		} // end while
	} // end displayEdges 
	
	private class EntryPQ implements Comparable<EntryPQ>
	{
		private VertexInterface<T> vertex; 	
		private VertexInterface<T> previousVertex; 
		private double cost; // cost to nextVertex
		
		private EntryPQ(VertexInterface<T> vertex, double cost, VertexInterface<T> previousVertex)
		{
			this.vertex = vertex;
			this.previousVertex = previousVertex;
			this.cost = cost;
		} // end constructor
		
		public VertexInterface<T> getVertex()
		{
			return vertex;
		} // end getVertex
		
		public VertexInterface<T> getPredecessor()
		{
			return previousVertex;
		} // end getPredecessor

		public double getCost()
		{
			return cost;
		} // end getCost
		
		public int compareTo(EntryPQ otherEntry)
		{
			// Using opposite of reality since our priority queue uses a maxHeap;
			// could revise using a minheap
			return (int)Math.signum(otherEntry.cost - cost);
		} // end compareTo
		
		public String toString()
		{
			return vertex.toString() + " " + cost;
		} // end toString 
	} // end EntryPQ
} // end DirectedGraph
