package org.example;
import org.example.factory.Factory;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FactoryTest {

    @Test
    @DisplayName("Производство и выдача деталей")
    void getDetailsTest() throws InterruptedException {
        Factory factory = new Factory(1);
        Thread t = new Thread(factory);
        t.start();
        t.join();

        List<String> details = factory.getDetail();
        assertNotNull(details);
        assertTrue(details.size() == 5);
    }

    @Test
    @DisplayName("Проверка когда деталей нету")
    void getDetailsNullTest() {
        Factory factory = new Factory(0); // фабрика не работает
        assertNull(factory.getDetail());
    }

}
