package com.example.nationinfo;

import android.graphics.Bitmap;

public class Nation {
    private String name;
    private String national_flag; // quốc kỳ
    private String population; // dân số
    private String area; // diện tích

    public Nation(){}

    public Nation(String name, String national_flag, String population, String area) {
        this.name = name;
        this.national_flag = national_flag;
        this.population = population;
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNational_flag() {
        return national_flag;
    }

    public void setNational_flag(String national_flag) {
        this.national_flag = national_flag;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
