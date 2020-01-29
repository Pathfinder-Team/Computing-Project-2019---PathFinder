package com.example.pathfinder;


public class MapNode
{
    int newNum;
    int oldNum;

    int from;
    int to;

    public MapNode()
    {

    }
    public MapNode(int newNum, int oldNum)
    {
        this.newNum = newNum;
        this.oldNum = oldNum;
    }
}

