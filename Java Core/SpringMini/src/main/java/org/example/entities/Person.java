package org.example.entities;

import org.example.annotations.MyComponent;
import org.example.annotations.MyScope;
import org.example.lifecycle.InitializingBean;

@MyComponent
@MyScope("prototype")
public class Person implements InitializingBean {

    private String name = "hello";

    public Person() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("Все хорошо!");
    }
}
