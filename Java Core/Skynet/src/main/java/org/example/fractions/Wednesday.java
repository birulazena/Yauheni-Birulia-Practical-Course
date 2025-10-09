package org.example.fractions;

import org.example.factory.Factory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Wednesday implements IFraction, Runnable{

    private Factory factory;

    private List<String> details;

    public Wednesday(Factory factory) {
        this.factory = factory;
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
        try {
            List<String> temp = new ArrayList<>();
            while(true) {
                temp = factory.getDetail();
                if (temp == null) {
                    for (int i = 0; i < 10; i++) {
                        Thread.sleep(60);
                        temp = factory.getDetail();
                        if (temp != null) {
                            break;
                        }
                    }
                    if(temp == null) {
                        break;
                    }
                }
                details.addAll(temp);
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(details.size());
    }
}
