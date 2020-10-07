package com.company;

import java.io.Serializable;

public class Human implements Serializable {
    private int age; //Значение поля должно быть больше 0
    public Human(int age){
        if (!(age < 0)) this.age= age;
        else throw new IllegalArgumentException("Значение age должно быть >0");
    }
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    @Override
    public String toString() {
        return "{" + '\n'+
                "<age>:" + age + '\n'+
                "}";
    }

}
