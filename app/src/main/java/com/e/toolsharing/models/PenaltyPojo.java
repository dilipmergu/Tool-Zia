package com.e.toolsharing.models;

public class PenaltyPojo {

    private String image;

    public PenaltyPojo(String image, String name, String tool_owner, String booked_by, String pid, String damage_penalty, String penalty_reason) {
        this.image = image;
        this.name = name;
        this.tool_owner = tool_owner;
        this.booked_by = booked_by;
        this.pid = pid;
        this.damage_penalty = damage_penalty;
        this.penalty_reason = penalty_reason;
    }

    private String name;
    private String tool_owner;
    private String booked_by;
    private String pid;
    private String damage_penalty;
    private String penalty_reason;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTool_owner() {
        return tool_owner;
    }

    public void setTool_owner(String tool_owner) {
        this.tool_owner = tool_owner;
    }

    public String getBooked_by() {
        return booked_by;
    }

    public void setBooked_by(String booked_by) {
        this.booked_by = booked_by;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDamage_penalty() {
        return damage_penalty;
    }

    public void setDamage_penalty(String damage_penalty) {
        this.damage_penalty = damage_penalty;
    }

    public String getPenalty_reason() {
        return penalty_reason;
    }

    public void setPenalty_reason(String penalty_reason) {
        this.penalty_reason = penalty_reason;
    }







    public PenaltyPojo()
    {

    }

}
