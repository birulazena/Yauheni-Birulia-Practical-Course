package org.example;

import org.example.factory.Factory;
import org.example.fractions.Wednesday;
import org.example.fractions.World;

import java.util.concurrent.Phaser;


public class Main {
    public static void main(String[] args) throws InterruptedException {

        Phaser phaser = new Phaser();

        Factory factory = new Factory(phaser, 100);

        World world = new World(factory, phaser);

        Wednesday wednesday = new Wednesday(factory, phaser);

        Thread t1 = new Thread(world);
        Thread t2 = new Thread(wednesday);
        Thread t3 = new Thread(factory);


        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();



        System.out.println(world.buildRobots());
        System.out.println(wednesday.buildRobots());

    }
}