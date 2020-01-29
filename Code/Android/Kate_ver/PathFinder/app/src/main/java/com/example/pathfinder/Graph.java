package com.example.pathfinder;
// Author: Kevin Dunne
// Program: Programming Assignment 1 - Dijkstra’s Algorithm
// Date:
// ToDo:
////////////////////////////////////////////////////////////////////////////////

import java.util.*;

public class Graph
{


    Scanner scan = new Scanner(System.in);
    // instance variables
    boolean[] visited;
    int[] distance;
    int[] previous;
    LinkedList<Node> adj[];

    ArrayList<Node> directionsArray;

    ArrayList<Node> finalDirectionsArray = new ArrayList<>();
    // defaulting the source vertice to 1
    int sourceVertice = 1;
    int sourceDestination = 1;
    int graphSize;

    public Graph()
    {

    }

    // constructor
    public Graph(int graphSize)
    {
        System.out.println("GraphSize: "+graphSize);
        this.graphSize = graphSize;
        // setting up the adjacent linked list and passing in graph size
        adj = new LinkedList[graphSize + 1];
        directionsArray = new ArrayList<>();
        visited = new boolean[graphSize + 1];
        distance = new int[graphSize + 1];
        previous = new int[graphSize + 1];

        // populating the 4 arrays
        for (int i = 1; i < graphSize + 1; i++)
        {
            adj[i] = new LinkedList<>();
            visited[i] = false;
            distance[i] = 99999;
            previous[i] = -1;

        }
    }

    public void addEdge(Node MapPriority,
                        int point_from_id,
                        int point_to_id,
                        int point_weight,
                        String point_direction)
    {
        Node edge = new Node(point_from_id,
                point_to_id, point_weight,
                point_direction);
       // System.out.println("###########################");
        System.out.println("point_from_id: "+point_from_id+", point_to_id: "+point_to_id+", point_weight: "+point_weight);
       // System.out.println("###########################");


        adj[MapPriority.current_point_id].addFirst(edge);
    }

    public void getUserInput()
    {
        boolean pass = false;
        int verticeCheck = 0;
        int verticeCheckDestination = 0;
        int size = adj.length - 1;
        do
        {
            System.out.println("Select a source Vertice");
            System.out.println("Between 1 and " + size);
            System.out.println(" ");
            //verticeCheck = scan.nextInt();

            DisplayActivity da = new DisplayActivity();

            //String currentLocation = da.getCurrentLocationDetails.get(0).point_name;
            //String nextLocation = da.getNextLocationDetails.get(0).point_name;

            //System.out.println("1 Current Location "+currentLocation);
            //System.out.println("2 Next Location "+nextLocation);

            //System.out.println("3 Current Location "+da.current_selected_id);
            //System.out.println("4 Next Location "+da.selected_destination_id);

            verticeCheck = da.current_selected_id;
            verticeCheckDestination = da.selected_destination_id;

            if (verticeCheck != 0 && verticeCheck <= adj.length && verticeCheckDestination != 0 && verticeCheckDestination <= adj.length)
            {
                //System.out.println("current location: "+verticeCheck);
                //System.out.println("next location: "+verticeCheckDestination);
                sourceVertice = verticeCheck;
                sourceDestination = verticeCheckDestination;

                System.out.println("sourceVertice: "+sourceVertice);
                System.out.println("sourceDestination: "+sourceDestination);
                System.out.println(" ");
                pass = true;
            }
            else
            {
                System.out.println("Invalid Vertice " + verticeCheck + ", go again");
            }
        }
        while (pass != true);
    }
    public void checkOut()
    {
        System.out.println("###########################");
        //System.out.println("adj.length: "+adj.length);
        for(int i = 1; i < adj.length;i++)
        {
            //System.out.println("check");
            for(int j = 0; j < adj[i].size();j++)
            {
                //System.out.println("adj[i].size(): "+adj[i].size());
                System.out.println("Source: "

                        +adj[i].get(j).point_from_id+", Destination: "
                        +adj[i].get(j).point_to_id+", Weight: "
                        +adj[i].get(j).point_weight +" Direction: "
                        +adj[i].get(j).point_direction);
            }
        }
        System.out.println("###########################");
    }

    public void findShortestPaths()
    {
        // getting the users input to select a source vertex
        getUserInput();
        //checkOut();
        // setting the first index of distance and previous to 0 because i dont use these
        distance[sourceVertice] = 0;
        previous[sourceVertice] = 0;
        // setting the minValue to have a super large number to check against the smallest distance
        int minValue = 999999;
        // the root node is the sourcevertice of which the user has chosen
        int root = sourceVertice;
        int nextNode = 0;
        // running n times depending on adjency list size
        for (int i = 1; i < adj.length; i++)
        {
            // if the adj index contains a value greater then 0 then it contains values and we can procede
            if (adj[root].size() > 0)
            {
                // for that iterates though the embdedded array
                for (int j = 0; j < adj[root].size(); j++)
                {
                    // variables to hold the value at adjency array index at index j and getting the destination and weight
                    // values respectively
                    int getAdjIndexValueDest = adj[root].get(j).point_to_id;
                    int getAdjIndexValueWeight = adj[root].get(j).point_weight;
                    // if the minValue is over 9999999
                    // i did this to do this part of the if statement once only
                    if (minValue == 999999)
                    {
                        // if the index value in visited hasnt been visited which means if it isnt set to true
                        if (visited[root] != true)
                        {
                            // this handled the the root index, the previous and weight should be both 0 since they
                            // are the start locations and therefore dont have a previous vertex or a distance to weight
                            distance[getAdjIndexValueDest] = getAdjIndexValueWeight;
                            previous[adj[root].get(j).point_to_id] = root;
                        }
                    }
                    else
                    {
                        // if the value at the visited index is not true then do this if
                        if (visited[root] != true)
                        {
                            // holds the current and next weights, these will be compared later
                            int holdDestWeightAdjWeight = distance[root] + getAdjIndexValueWeight;
                            // holds the next weight, the point of this is to check if the current weight is less then
                            // the next weight because if so then that is the shortest path to that specfic vertex
                            int nextWeight = 0;

                                //System.out.println("distance "+distance.length);

                                //System.out.println("getAdjIndexValueDest "+getAdjIndexValueDest);

                                nextWeight = distance[getAdjIndexValueDest];

                            //System.out.println(" ");
                            //System.out.println("current weight "+holdDestWeightAdjWeight);
                            //System.out.println("next weight "+nextHold);
                            //System.out.println("");

                            // compare the current weight and next weight
                            if (holdDestWeightAdjWeight < nextWeight)
                            {
                                // pass in the current weight to the distance array at the intended dest
                                // and pass in the root to the previous array so you know where you have just come from
                                int intendedDest = adj[root].get(j).point_to_id;
                                distance[intendedDest] = holdDestWeightAdjWeight;
                                previous[intendedDest] = root;
                            }
                        }
                    }
                }
            }
            // processed the node at root so set the value at that index to true so we never go near it again
            visited[root] = true;
            // resetting minValue
            minValue = 999999;
            for (int d = 1; d < distance.length; d++)
            {
                // if the value at d in distanxce is greater then 0 and if that index in the visited array is not true which means visited
                if (distance[d] > 0 && visited[d] != true)
                {
                    // if the value in distance[d] is less than minValue which is 999999
                    if (distance[d] < minValue)
                    {
                        minValue = distance[d];
                        nextNode = d;
                    }
                }
            }
            // set the root to hold the value of the next node so we know to process that node next
            root = nextNode;
        }
        // output the path
        displayPath(sourceVertice, sourceDestination);
    }

    public void displayPath(int root, int destination)
    {
        System.out.println("############################################################");
        System.out.println("############################################################");
        System.out.println(" ");
        Stack<Integer> fromid = new Stack();
        Stack<Integer> toid = new Stack();
        Stack<Integer> distanceTo = new Stack();

        // build our stack from end position to start position
        //System.out.println("Destination is " + destination);
        do {
            //System.out.println("save off details");
            fromid.push((Integer)previous[destination]);
            toid.push((Integer)destination);
            distanceTo.push((Integer)distance[destination]);

            // loop control
            //System.out.println("now load up detials for " + previous[destination]);
            destination = previous[destination];

        } while(distance[destination] != 0);

        // add in start position
        fromid.push((Integer)0);
        toid.push((Integer)destination);
        distanceTo.push((Integer)distance[destination]);

        // dump the stack out to see what we have
        int stackHere;
        int stackThere;
        int stackDistance;

        do {
            stackHere = fromid.pop();
            stackThere = toid.pop();
            stackDistance = distanceTo.pop();

            Node addDirections = new Node(stackHere, stackThere);
            directionsArray.add(addDirections);

            if(stackHere != 0) {
                System.out.println("from " + stackHere + " to " + stackThere + " for distance of " + stackDistance);
            }
        } while (!fromid.empty());

        for (int source = 1; source < distance.length; source++)
        {
            if (distance[source] == 99999)
            {
                System.out.print("This index is UNREACHABLE: " + source);
                System.out.println("");
            }
            else
            {
                // just 2 diffrent ways to look at the information if you want to read it as vertex going to desintation uncomment the second out seconds
                // other if you want to it as vertex coming from previous keep these uncommented.

               // System.out.print("" + source + " Came From " + previous[source]);
                //System.out.print(", Costing Weight of " + distance[source]);
               // System.out.println(" ");
            }
        }

        System.out.println("###########################");
        //System.out.println("adj.length: "+adj.length);

        /*
        for(int i = 1; i < adj.length;i++)
        {
            //System.out.println("check");
            for(int j = 0; j < adj[i].size();j++)
            {
                if(directionsArray.get(i).source != null) {
                    if (directionsArray.get(i).source == adj[i].get(j).current_point_id) {
                        System.out.println("Direction: " + adj[i].get(j).point_direction);
                    }
                }
            }
        }

         */

        for(int i = 1; i < adj.length;i++)
        {
                for (int j = 0; j < adj[i].size(); j++)
                {
                    //System.out.println("source: "+directionsArray.get(j).source);
                    for(int k = 0; k < directionsArray.size();k++) {
                        if (adj[i].get(j).point_from_id == directionsArray.get(k).source && adj[i].get(j).point_to_id == directionsArray.get(k).destination) {

                            Node addEdge = new Node(adj[i].get(j).point_from_id,adj[i].get(j).point_to_id,adj[i].get(j).point_direction);
                            finalDirectionsArray.add(addEdge);
                            //System.out.println("adj[i].get(j).fromPointId: "+finalDirectionsArray.get(j).fromPointId);
                            //System.out.println("adj[i].get(j).toPointId: "+finalDirectionsArray.get(j).toPointId);
                            //System.out.println("adj[i].get(j).pointDirection: "+finalDirectionsArray.get(j).pointDirection);
                            //System.out.println("Directions: " + adj[i].get(j).point_direction);

                        }
                    }
                }
            }

        System.out.println(" ");
        System.out.println("###########################");

        //finalDirectionsArray
        //System.out.println("Source: " + directionsArray.get(i).source + ", Destination: " + directionsArray.get(i).destination);
                /* if (directionsArray.get(i).source == adj[i].get(i).current_point_id &&  directionsArray.get(i).destination == adj[i].get(j).point_to_id)
                {
                    System.out.println("I: "+i);
                }

                 */
    }
    public ArrayList<Node> getCurrentDirections()
    {
        return finalDirectionsArray;
    }



}
