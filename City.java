package com.company;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class City implements  Comparable, Serializable {
    private String owner;
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Double area; //Значение поля должно быть больше 0, Поле не может быть null
    private int population; //Значение поля должно быть больше 0
    private Double metersAboveSeaLevel;
    private Float agglomeration;
    private Government government; //Поле не может быть null
    private StandardOfLiving standardOfLiving; //Поле может быть null
    private Human governor; //Поле может быть null
    public ArrayList<City> collection;

    public City(String name) {
        this.creationDate = LocalDateTime.now();
        this.name = name;

    }

    public City(String name, Coordinates coordinates, Double area, int population, Double metersAboveSeaLevel, Float agglomeration, Government government, StandardOfLiving standardOfLiving, Human governor) {
        this.creationDate = LocalDateTime.now();
        this.name = name;
        this.coordinates = coordinates;
        if (!(area < 0)) this.area = area;
        else throw new IllegalArgumentException("Значение должно быть >0");
        if (!(population < 0)) this.population = population;
        else throw new IllegalArgumentException("Значение  должно быть >0");
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.agglomeration = agglomeration;
        this.setGovernor(governor);
        this.government = government;
        this.standardOfLiving = standardOfLiving;


    }

    public City(String name, Coordinates coordinates, Double area, int population, Double metersAboveSeaLevel, Float agglomeration, Government government, Human governor) {
        this.creationDate = LocalDateTime.now();

        this.name = name;
        this.coordinates = coordinates;
        if (!(area < 0)) this.area = area;
        else throw new IllegalArgumentException("Значение должно быть >0");
        if (!(population < 0)) this.population = population;
        else throw new IllegalArgumentException("Значение должно быть >0");
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.agglomeration = agglomeration;
        this.setGovernor(governor);
        this.government = government;

    }

    public City(String name, Coordinates coordinates, Double area, int population, Double metersAboveSeaLevel, Float agglomeration, Government government) {
        this.creationDate = LocalDateTime.now();
        this.name = name;
        this.coordinates = coordinates;
        if (!(area < 0)) this.area = area;
        else throw new IllegalArgumentException("Значение должно быть >0");
        if (!(population < 0)) this.population = population;
        else throw new IllegalArgumentException("Значение должно быть >0");
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.agglomeration = agglomeration;
        this.government = government;

    }

    City(String name, Coordinates coordinates, Double area, int population, Double metersAboveSeaLevel, Float agglomeration, Government government, StandardOfLiving standardOfLiving) {
        this.creationDate = LocalDateTime.now();
        this.name = name;
        this.coordinates = coordinates;
        if (!(area < 0)) this.area = area;
        else throw new IllegalArgumentException("Значение должно быть >0");
        if (!(population < 0)) this.population = population;
        else throw new IllegalArgumentException("Значение должно быть >0");
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.agglomeration = agglomeration;
        this.government = government;
        this.standardOfLiving = standardOfLiving;

    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;

    }
    public Double getMetersAboveSeaLevel(){
        return metersAboveSeaLevel;
    }
    public Float getAgglomeration(){return agglomeration;}

    public void setName(String name) {
        this.name = name;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public void setMetersAboveSeaLevel(Double metersAboveSeaLevel) {
        this.metersAboveSeaLevel = metersAboveSeaLevel;
    }

    public void setAgglomeration(Float agglomeration) {
        this.agglomeration = agglomeration;
    }

    public Government getGovernment() {
        return government;
    }

    public void setStandardOfLiving(StandardOfLiving standardOfLiving) {
        this.standardOfLiving = standardOfLiving;
    }


    public StandardOfLiving getStandardOfLiving() {
        return standardOfLiving;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public int getId() {
        return id;
    }
    public String getName(){return name;}



    @Override
    public String toString() {
        return "{" +
                "'id':" + id + "," + '\n' +
                " 'name':" + name + "," + '\n' +
                " 'coordinates':" + coordinates + "," + '\n' +
                "'creationDate':" + creationDate + "," + '\n' +
                "'area':" + area + "," + '\n' +
                "'population':" + population + "," + '\n' +
                "'metersAboveSeaLevel':" + metersAboveSeaLevel + "," + '\n' +
                "'agglomeration':" + agglomeration + "," + '\n' +
                "'government':" + government + "," + '\n' +
                "'standardOfLiving':" + standardOfLiving + "," + '\n' +
                "'governor':" + governor + "," + '\n' +

                '}' + "\n";
    }

    public Human getGovernor() {
        return governor;
    }

    public void setGovernor(Human governor) {
        this.governor = governor;
    }



    @Override
    public int compareTo(Object o) {
        if (!(o instanceof City))
            throw new IllegalArgumentException("Объект класса City можно сравнить только с объектами этого класса ");
        else {
            int result = 0;
            City t = (City) o;
           result = (this.id > t.id) ? 1 : (this.id < t.id) ? -1 : 0;
            return result;
        }

    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public void setid(int id) {
        this.id = id;
    }

    public long getid() {
        return id;
    }

    public double getArea() {return area;
    }

    public int getPopulation() {return population;
    }
}