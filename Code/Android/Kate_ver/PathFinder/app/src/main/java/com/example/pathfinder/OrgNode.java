package com.example.pathfinder;

import android.graphics.Bitmap;

import java.sql.Blob;

public class OrgNode
{

    String organisation_name;
    String organisation_address;
    String organisation_email;
    String organisation_mobile;
    String organisation_building_name;

    int map_id;
    String org_name;
    String org_building;
    String map_name;
    String map_comments;
    Bitmap map_image;


    public OrgNode()
    {

    }
    public OrgNode(String organisation_name, String organisation_address, String organisation_email, String organisation_mobile,String organisation_building_name)
    {
        this.organisation_name=organisation_name;
        this.organisation_address=organisation_address;
        this.organisation_email=organisation_email;
        this.organisation_mobile=organisation_mobile;
        this.organisation_building_name=organisation_building_name;
    }

    public OrgNode(int map_id, String org_name, String org_building, String map_name,String map_comments, Bitmap map_image)
    {
        this.map_id=map_id;
        this.org_name=org_name;
        this.org_building=org_building;
        this.map_name=map_name;
        this.map_comments=map_comments;
        this.map_image=map_image;
    }
}
