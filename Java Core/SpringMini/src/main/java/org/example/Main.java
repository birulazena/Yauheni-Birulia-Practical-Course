package org.example;

import org.example.context.MiniApplicationContext;
import org.example.entities.Check;
import org.example.entities.Conf;
import org.example.entities.Person;

import java.lang.reflect.InvocationTargetException;


public class Main {


    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        MiniApplicationContext context = new MiniApplicationContext(Conf.class);

        Person person = context.getBean(Person.class);
        person.setName("Yana");

        Person person2 = context.getBean(Person.class);
        person2.setName("Zenya");

        Check c = context.getBean(Check.class);

        System.out.println(person.getName());
        System.out.println(person2.getName());

        c.getP();

    }
}