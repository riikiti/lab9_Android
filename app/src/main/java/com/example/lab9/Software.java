package com.example.lab9;

import java.util.Date;

// Class for Software Object
public class Software {
    private String name;
    private String description;
    private int cost;
    private int version;
    private Date developmentDate;

    public Software(String name, String description, int cost, int version, Date developmentDate) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.version = version;
        this.developmentDate = developmentDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Date getDevelopmentDate() {
        return developmentDate;
    }

    public void setDevelopmentDate(Date developmentDate) {
        this.developmentDate = developmentDate;
    }
}
