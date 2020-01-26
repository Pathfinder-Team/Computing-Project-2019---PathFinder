package com.example.pathfinder;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.*;
import java.util.*;


public class Setup
{
    ArrayList<Node> points_array = new ArrayList();
    ArrayList<Node> map_points_array = new ArrayList();

    public Setup()
    {

    }
    public void setUpEverythingArrays(SQLiteDatabase db)
    {
        Cursor c = db.rawQuery("select * from map_points",null);
        while (c.moveToNext()) {
            Node edge = new Node(c.getInt(1),
                    c.getString(2),
                    c.getInt(3));
            //Node edge = new Node(result.getInt("current_point_id"), result.getString("point_name"), result.getInt("maps_map_id"));
            map_points_array.add(edge);
        }
        Cursor cd = db.rawQuery("select * from special_points",null);
        while (cd.moveToNext()) {
            Node edge = new Node(cd.getInt(1),
                    cd.getInt(2),
                    cd.getInt(3),
                    cd.getInt(4),
                    cd.getString(5));
            points_array.add(edge);
        }
    }
    public void setupEdges(Graph graph) {
        for (int i = 0; i < map_points_array.size(); i++)
        {
            if(map_points_array.get(i).current_point_id == points_array.get(i).point_from_id)
            {
                graph.addEdge(points_array.get(i).point_from_id,points_array.get(i).point_id,points_array.get(i).point_weight);
            }
        }
    }
    public void setUpMap(String currentDest, String finalDest, SQLiteDatabase db) throws IOException {
        Graph graph = new Graph();
        setUpEverythingArrays(db);
        graph = new Graph(map_points_array.size());
        setupEdges(graph);
        graph.findShortestPaths();
    }
}
