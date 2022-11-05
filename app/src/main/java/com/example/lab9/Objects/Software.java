package com.example.lab9.Objects;

// Class for Software Object
public class Software {
    private int id;
    private String name;
    private String description;
    private int cost;
    private int version;
    private String developmentDate;
    private String category = "";
    private String subcategory = "";

    public Software(int id, String name, String description, int cost, int version, String developmentDate, String category, String subcategory) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.version = version;
        this.developmentDate = developmentDate;
        this.category = category;
        this.subcategory = subcategory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDevelopmentDate() {
        return developmentDate;
    }

    public void setDevelopmentDate(String developmentDate) {
        this.developmentDate = developmentDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }
}
