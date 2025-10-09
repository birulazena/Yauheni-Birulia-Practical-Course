package org.example;

import org.example.factory.Factory;
import org.example.fractions.Wednesday;
import org.example.fractions.World;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {


        Factory factory = new Factory(10);

        Wednesday wednesday = new Wednesday(factory);

        World world = new World(factory);

        Thread factoryThread = new Thread(factory);

        Thread wednesdayThread = new Thread(wednesday);

        Thread worldThread = new Thread(world);

        factoryThread.start();
        wednesdayThread.start();
        worldThread.start();

        try {

            factoryThread.join();
            wednesdayThread.join();
            worldThread.join();
        } catch (InterruptedException e) {
            new RuntimeException(e);
        }

        System.out.println(wednesday.buildRobots());
        System.out.println(world.buildRobots());



//        Factory f = new Factory(finishedDetailsDeque);
//        var l = f.makingDetails();
//        f.makingDetails();
//        System.out.println(finishedDetailsDeque);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for(int i = 0 ; i < 20; i++) {
//                    System.out.println("lol");
//                    try {
//                        Thread.sleep(3000);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            }
//        }).start();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for(int i = 0; i < 20; i++) {
//                    System.out.println("pop");
//                    try {
//                        Thread.sleep(300);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            }
//        }).start();

    }
}