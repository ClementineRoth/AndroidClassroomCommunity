package com.valzaris.classroomcommunity.class_source;

/**
 * Created by valzaris on 15/01/19.
 */

public class District {
    private int DistrictId;
    private String name;
    private String description;
    private float longitude;
    private float latitude;
    private int districtnb;

    public District(int DistId, String name,int nb){
        this.DistrictId=DistId;
        this.name=name;
        this.districtnb=nb;
    }

    public int getDistrictId(){
        return this.DistrictId;
    }

    public void setDistrictId(int districtId){
        this.DistrictId=districtId;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name=name;
    }

    public int getDistrictnb(){
        return this.districtnb;
    }
    public void setDistrictnb(int districtnb){
        this.districtnb=districtnb;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
