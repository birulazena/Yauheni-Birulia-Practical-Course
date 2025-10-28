package org.example.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.Phaser;

public class Factory implements Runnable{

    private final List<String> details = new ArrayList<>(List.of("head", "torso", "hand","feet" ));

    private final int days;

    private Phaser phaser;

    private BlockingDeque<String> finishedDetailsDeque;

    public Factory(Phaser phaser, int days) {
        finishedDetailsDeque = new LinkedBlockingDeque<>();
        this.days = days;
        this.phaser = phaser;
        phaser.register();
    }

    private void makingDetails()  {
        Random r = new Random();
        for(int i = 0; i < 10; i++) {
            finishedDetailsDeque.add(details.get(r.nextInt(4)));
        }
    }

    @Override
    public void run() {
        for(int i = 0; i < days; i++) {
            makingDetails();
            phaser.arriveAndAwaitAdvance();
        }
        phaser.arriveAndDeregister();
    }

    public List<String> getDetail() {
        if (finishedDetailsDeque.isEmpty()) {
            return null;
        }
        List<String> result = new ArrayList<>();
        for(int i = 0 ;i < 5; i++) {
            result.add(finishedDetailsDeque.poll());
        }
        return result;
    }
}
