package org.example.fractions;

import org.example.factory.Factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Phaser;
import java.util.stream.Collectors;

public class World implements Runnable, IFraction{

    private List<String> details;

    private Factory factory;

    private Phaser phaser;

    public World(Factory factory, Phaser phaser) {
        this.factory = factory;
        this.phaser = phaser;
        phaser.register();
        details = new ArrayList<>();
    }

    @Override
    public long buildRobots() {

        Map<String, Long> map = details.stream()
                .collect(Collectors.groupingBy(d -> d, Collectors.counting()));
        return List.of(map.getOrDefault("head", 0L),
                        map.getOrDefault("torso", 0L),
                        map.getOrDefault("hand",0L) / 2,
                        map.getOrDefault("feet", 0L) / 2)
                .stream()
                .mapToLong(i -> i)
                .min().orElse(0L);
    }

    @Override
    public void run() {
        List<String> temp;
        while(true) {
            phaser.arriveAndAwaitAdvance();
            temp = factory.getDetail();
            if (temp == null) {
                break;
            }
            details.addAll(temp);
        }
    }
}
