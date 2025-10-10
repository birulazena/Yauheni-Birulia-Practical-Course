package org.example;

import org.example.factory.Factory;
import org.example.fractions.Wednesday;
import org.example.fractions.World;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class WednesdayTest {

    @Test
    @DisplayName("Создание роботов Wednesday")
    void BuildRobotsWednesdayTest() {
        Factory factory = new Factory(1);
        Wednesday wednesday = new Wednesday(factory);

        // вручную подложим детали
        var detailsField = getPrivateDetails(wednesday);
        detailsField.addAll(List.of("head", "torso", "hand", "hand", "feet", "feet"));

        assertEquals(1, wednesday.buildRobots());
    }

    @Test
    @DisplayName("Создание роботов World")
    void BuildRobotsWorldTest() {
        Factory factory = new Factory(1);
        World world = new World(factory);

        // вручную подложим детали
        var detailsField = getPrivateDetails(world);
        detailsField.addAll(List.of("head", "torso", "hand", "hand", "feet", "feet"));

        assertEquals(1, world.buildRobots());
    }

    @Test
    @DisplayName("Проверка работы двух фракций одновременно")
    void twoFractionRunTogetherTest() throws InterruptedException {

        Factory factory = new Factory(10);
        Wednesday wednesday = new Wednesday(factory);
        World world = new World(factory);

        Thread tf = new Thread(factory);
        Thread tw = new Thread(wednesday);
        Thread twr = new Thread(world);

        tf.start();
        tw.start();
        twr.start();

        tf.join();
        tw.join();
        twr.join();

        assertTrue(getPrivateDetails(wednesday).size() == 50);
        assertTrue(getPrivateDetails(world).size() == 50);

    }




    private List<String> getPrivateDetails(Object fraction) {
        try {
            var field = fraction.getClass().getDeclaredField("details");
            field.setAccessible(true);
            return (List<String>) field.get(fraction);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
