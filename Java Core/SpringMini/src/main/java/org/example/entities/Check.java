package org.example.entities;

import org.example.annotations.MyAutowired;
import org.example.annotations.MyComponent;

@MyComponent
public class Check {

    @MyAutowired
    private Person p;

    public void getP() {
        System.out.println(p.getName());
    }


}
