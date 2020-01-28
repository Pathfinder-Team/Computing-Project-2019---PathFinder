package com.example.pathfinder;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.*;
import java.util.*;


public class Setup
{
    ArrayList<Node> points_array = new ArrayList();
    ArrayList<Node> map_points_array = new ArrayList();
    public static ArrayList<Node> finalDirectionsArray = new ArrayList();

    public Setup()
    {

    }
    public void setUpEverythingArrays(SQLiteDatabase db)
    {
        Cursor c = db.rawQuery("select * from map_points",null);
        while (c.moveToNext()) {
            Node edge = new Node(c.getInt(0),
                    c.getString(1),
                    c.getInt(2));
            //Node edge = new Node(result.getInt("current_point_id"), result.getString("point_name"), result.getInt("maps_map_id"));
            map_points_array.add(edge);
        }
        Cursor cd = db.rawQuery("select * from special_points",null);
        while (cd.moveToNext()) {

            /*
            System.out.println("point_id: "+cd.getInt(0));
            System.out.println("point_from_id: "+cd.getInt(1));
            System.out.println("point_to_id: "+cd.getInt(2));
            System.out.println("point_weight: "+cd.getInt(3));
            System.out.println("point_direction: "+cd.getString(4));
            */
            Node edge = new Node(cd.getInt(0),
                    cd.getInt(1),
                    cd.getInt(2),
                    cd.getInt(3),
                    cd.getString(4));
            points_array.add(edge);
        }
        db.close();

        /*
        for(int i = 0; i < points_array.size(); i++)
        {
            System.out.println("point_id"+points_array.get(i).point_id);
            System.out.println("point_from_id"+points_array.get(i).point_from_id);
            System.out.println("point_to_id"+points_array.get(i).point_to_id);
            System.out.println("point_weight"+points_array.get(i).point_weight);
            System.out.println("point_direction"+points_array.get(i).point_direction);
        }

         */
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
    public void setUpMap(String currentDest, String finalDest, SQLiteDatabase db) throws IOException {
        Graph graph = new Graph();
        setUpEverythingArrays(db);
        graph = new Graph(map_points_array.size());
        setupEdges(graph);
        graph.findShortestPaths();
        finalDirectionsArray = graph.getCurrentDirections();
    }


}
