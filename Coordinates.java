package com.company;

import java.io.Serializable;

public class Coordinates implements  Serializable{
    private long x;
    private int y;
    public Coordinates(long x, int y) {
        this.x = x;
        this.y = y;

    }

    public void setX(long x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    public long getX() { return x; }
    public int getY() { return y;}

    @Override
    public String toString() {
        return "{" + "\n" +
                "<x:>" + x + ","+ '\n'+
        "<y:>" + y + '\n'+
        "}";
    }

}
