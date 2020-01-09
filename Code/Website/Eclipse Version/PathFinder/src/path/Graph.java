package path;

import java.util.*;

public class Graph
{
    boolean[] visited;
    int[] distance;
    int[] previous;
    LinkedList<Node> adj[];
    int sourceVertice = 1;
    int graphSize;

    public Graph()
    {

    }

    // constructor
    @SuppressWarnings("unchecked")
	public Graph(int graphSize)
    {
        this.graphSize = graphSize;
        // setting up the adjacent linked list and passing in graph size
        adj = new LinkedList[graphSize + 1];
        visited = new boolean[graphSize + 1];
        distance = new int[graphSize + 1];
        previous = new int[graphSize + 1];

        // populating the 4 arrays
        for (int i = 1; i < graphSize + 1; i++)
        {
            adj[i] = new LinkedList<>();

        }
    }
    public void addEdge(int source, int destination, int weight)
    {
        Node edge = new Node(source, destination, weight);
        adj[source].addFirst(edge);
    }
}
