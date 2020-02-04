package com.example.pathfinder;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.*;
import java.util.*;


public class Setup
{
    ArrayList<Node> points_array = new ArrayList();
    ArrayList<Node> map_points_array = new ArrayList();

    ArrayList<MapNode> sortMapPoints = new ArrayList<>();
    ArrayList<MapNode> sortSpecialPoints = new ArrayList<>();

    public static ArrayList<Node> finalDirectionsArray = new ArrayList();

    public Setup()
    {

    }
    // setting up the arrays that will needed to be passed to the graph class in a specific orders
    public void setUpEverythingArrays(SQLiteDatabase db)
    {
        int counter = 1;
        Cursor c = db.rawQuery("select * from map_points",null);
        while (c.moveToNext()) {
            Node edge = new Node(c.getInt(0),
                    c.getString(1),
                    c.getInt(2));
            //counter++;
            MapNode mp = new MapNode(counter++,c.getInt(0));
            //sortMapPoints.add(mapPointsArray);
            map_points_array.add(edge);
        }

        Cursor cd = db.rawQuery("select * from special_points",null);
        while (cd.moveToNext()) {
            Node edge = new Node(cd.getInt(0),
                    cd.getInt(1),
                    cd.getInt(2),
                    cd.getInt(3),
                    cd.getString(4));
            points_array.add(edge);
        }
        db.close();
    }
    public void setupEdges(Graph graph) {
        for (int i = 0; i < map_points_array.size(); i++)
        {

            Node MapPriority = new Node(map_points_array.get(i).current_point_id,
                    map_points_array.get(i).point_name,
                    map_points_array.get(i).maps_map_id);
            for(int j = 0; j < points_array.size();j++)
            {
                if (map_points_array.get(i).current_point_id == points_array.get(j).point_from_id) {

                    //System.out.println("Source: "+points_array.get(j).point_from_id+",Destination: "+points_array.get(j).point_to_id+", Weight: "+points_array.get(j).point_weight);
                    //graph.addEdge(points_array.get(j).point_from_id, points_array.get(j).point_to_id, points_array.get(j).point_weight);
                    graph.addEdge(MapPriority,
                            points_array.get(j).point_from_id,
                            points_array.get(j).point_to_id,
                            points_array.get(j).point_weight,
                            points_array.get(j).point_direction);
                    //System.out.println("");
                }
            }
        }
    }

    public static ArrayList<Node> getDirect()
    {
        return finalDirectionsArray;
    }
    public void setUpMap(SQLiteDatabase db) {
        Graph graph = new Graph();
        setUpEverythingArrays(db);
        graph = new Graph(map_points_array.size());
        setupEdges(graph);
        graph.findShortestPaths();
        // returning the point names and directions from the shortest path
        finalDirectionsArray = graph.getCurrentDirections();
    }


}
