package com.example.pathfinder;// Author: Kevin Dunne
// Program: Programming Assignment 1 - Dijkstra’s Algorithm
// Date:
// ToDo: 
////////////////////////////////////////////////////////////////////////////////
import java.io.*;
import java.util.*;

public class Generate {

    public void CreateFile(int size) throws IOException {
        FileWriter file = new FileWriter("src\\NewFile.txt");
        file.write("S\n");
        file.write(size + "\n");

        // create a file that runs for the size multiplied by the size
        for (int i = 0; i < ((size * size) - size); i++) {
            // create new random instance every time
            Random rand = new Random();
            int randomNumber1 = rand.nextInt(size) + 1;
            int randomNumber2 = rand.nextInt(size) + 1;
            int randomNumber3 = rand.nextInt(size) + 1;

            file.write(randomNumber1 + ", " + randomNumber2 + ", " + randomNumber3);
            file.write("\n");
            file.flush();
        }
        file.close();
    }

    public void callGen() throws IOException {

        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter a Size for your File");
        int size = scan.nextInt();
        CreateFile(size);
    }
}
