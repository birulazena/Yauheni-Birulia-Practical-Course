package org.example.entities;

import org.example.annotations.MyComponent;
import org.example.annotations.MyScope;

@MyComponent
@MyScope("prototype")
public class Person {

    private String name = "hello";

    public Person() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
