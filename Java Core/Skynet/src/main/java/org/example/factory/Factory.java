package org.example.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class Factory {

    private final List<String> details = new ArrayList<>(List.of("head", "torso", "hand","feet" ));

    private BlockingDeque<String> finishedDetailsDeque = new LinkedBlockingDeque<String>();

    public BlockingDeque<String> makingDetails()  {
        Random r = new Random();
        for(int i = 0; i < 10; i++) {
            finishedDetailsDeque.add(details.get(r.nextInt(4)));
        }
        return finishedDetailsDeque;
    }


}
