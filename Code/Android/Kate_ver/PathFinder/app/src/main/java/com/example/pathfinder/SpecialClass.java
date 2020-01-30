package com.example.pathfinder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class SpecialClass {


    public SpecialClass()
    {

    }
    public void CrateAllTables(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                "org_details(" +
                "organisation_name varchar ," +
                "organisation_address varchar ," +
                "organisation_email varchar," +
                "organisation_mobile varchar," +
                "organisation_building_name varchar);");

        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                "map_details(" +
                "map_id int ," +
                "org_name varchar," +
                "org_building varchar," +
                "map_name varchar," +
                "map_comments varchar," +
                "map_image varchar);");
        //

        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                "building_details(" +
                "building_name varchar);");

        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                "map_information(" +
                "map_image varchar);");


        //
        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                "map_points(" +
                "current_point_id int ," +
                "point_name varchar," +
                "maps_map_id int);");

        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                "special_points(" +
                "point_id int ," +
                "point_from_id int," +
                "point_to_id int," +
                "point_weight int," +
                "point_direction varchar);");
    }

    public void Wipe(SQLiteDatabase db)
    {
        db.execSQL("DROP TABLE IF EXISTS building_details");
        db.execSQL("DROP TABLE IF EXISTS map_information");

        // main table
        db.execSQL("DROP TABLE IF EXISTS map_points");
        db.execSQL("DROP TABLE IF EXISTS special_points");

        // secondary table
        db.execSQL("DROP TABLE IF EXISTS org_details");
        db.execSQL("DROP TABLE IF EXISTS map_details");

        //
        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                "org_details(" +
                "organisation_name varchar ," +
                "organisation_address varchar ," +
                "organisation_email varchar," +
                "organisation_mobile varchar," +
                "organisation_building_name varchar);");

        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                "map_details(" +
                "map_id int ," +
                "org_name varchar," +
                "org_building varchar," +
                "map_name varchar," +
                "map_comments varchar," +
                "map_image varchar);");
        //

        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                "building_details(" +
                "building_name varchar);");

        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                "map_information(" +
                "map_image varchar);");


        //
        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                "map_points(" +
                "current_point_id int ," +
                "point_name varchar," +
                "maps_map_id int);");

        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                "special_points(" +
                "point_id int ," +
                "point_from_id int," +
                "point_to_id int," +
                "point_weight int," +
                "point_direction varchar);");
    }
}
