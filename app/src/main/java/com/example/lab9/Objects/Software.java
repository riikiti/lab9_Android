package com.example.lab9.Objects;

import java.io.Serializable;

// Class for Software Object
public class Software implements Serializable {
    private int id;
    private String name;
    private String surname;
    private int course;

    private String developmentDate;
    private String category = "";
    private String subcategory = "";
    private String secondName;

    public Software(int id, String name, String description, String secondName, int cost, String developmentDate, String category, String subcategory) {
        this.id = id;
        this.name = name;
        this.surname = description;
        this.secondName = secondName;
        this.course = cost;

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getDate() {
        return course;
    }

    public void setDate(int date) {
        this.course = date;
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

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
}
