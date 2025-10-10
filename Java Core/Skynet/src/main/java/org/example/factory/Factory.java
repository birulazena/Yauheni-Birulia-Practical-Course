package org.example.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class Factory implements Runnable{

    private final List<String> details = new ArrayList<>(List.of("head", "torso", "hand","feet" ));

    private final int days;

    private volatile boolean isOpen;

    private BlockingDeque<String> finishedDetailsDeque;

    public Factory(int days) {
        finishedDetailsDeque = new LinkedBlockingDeque<>();
        this.days = days;
        this.isOpen = false;
    }

    private void makingDetails()  {
        Random r = new Random();
        for(int i = 0; i < 10; i++) {
            finishedDetailsDeque.add(details.get(r.nextInt(4)));
//            finishedDetailsDeque.add(details.get((i + 1) % 4));
        }
    }

    @Override
    public void run() {
        for(int i = 0; i < days; i++)
            try {
                isOpen = false;
                makingDetails();
                Thread.sleep(30);
                isOpen = true;
                Thread.sleep(30);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
        }
    }

    public List<String> getDetail() {
        if (!isOpen || finishedDetailsDeque.isEmpty()) {
            return null;
        }
        List<String> result = new ArrayList<>();
        for(int i = 0 ;i < 5; i++) {
            result.add(finishedDetailsDeque.poll());
        }
        return result;
    }

}
