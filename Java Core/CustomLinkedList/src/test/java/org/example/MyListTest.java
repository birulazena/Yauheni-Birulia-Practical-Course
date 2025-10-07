package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MyListTest {

    private MyList<Integer> myList;

    @BeforeEach
    void init() {
        myList = new MyList<>();
    }

    @Test
    @DisplayName("Пустой список")
    void testEmptyList() {
        assertEquals(0, myList.size());
        assertThrows(RuntimeException.class, () -> myList.getFirst());
        assertThrows(RuntimeException.class, () -> myList.getLast());
    }

    @Test
    @DisplayName("Добавление в начало")
    void addFirstTest() {
        myList.addFirst(12);
        myList.addFirst(21);
        assertEquals(2, myList.size());
        assertEquals(21, myList.get(0));
        assertEquals(12, myList.get(1));
    }

    @Test
    @DisplayName("Добавление в конец")
    void addLastTest() {
        myList.addLast(22);
        myList.addLast(33);
        assertEquals(2, myList.size());
        assertEquals(22, myList.get(0));
        assertEquals(33, myList.get(1));
    }

    @Test
    @DisplayName("Добавление по индексу")
    void addIndexTest() {
        myList.add(0, 11);
        myList.add(1, 22);
        myList.add(2, 33);
        assertEquals(3, myList.size());
        assertEquals(11, myList.get(0));
        assertEquals(22, myList.get(1));
        assertEquals(33, myList.get(2));
    }

    @Test
    @DisplayName("Получение первого")
    void getFirstTest() {
        myList.addFirst(11);
        myList.addFirst(22);
        myList.addFirst(33);
        assertEquals(33, myList.getFirst());
        myList.remoteFirst();
        assertEquals(22, myList.getFirst());
        myList.remoteFirst();
        assertEquals(11, myList.getFirst());
    }

    @Test
    @DisplayName("Получение последнего")
    void getLastTest() {
        myList.addFirst(11);
        myList.addFirst(22);
        myList.addFirst(33);
        assertEquals(11, myList.getLast());
        myList.remoteLast();
        assertEquals(22, myList.getLast());
        myList.remoteLast();
        assertEquals(33, myList.getLast());
    }

    @Test
    @DisplayName("Получение по индексу")
    void getIndexTest() {
        myList.addFirst(11);
        myList.addFirst(22);
        myList.addFirst(33);
        assertEquals(33, myList.get(0));
        assertEquals(11, myList.get(2));
        assertEquals(22, myList.get(1));
        assertThrows(RuntimeException.class, () -> myList.get(5));
    }

    @Test
    @DisplayName("Удаление первых")
    void remoteFirstTest() {
        myList.addFirst(11);
        myList.addFirst(22);
        myList.addFirst(33);
        assertEquals(3, myList.size());
        assertEquals(33, myList.remoteFirst());
        assertEquals(2, myList.size());
        assertEquals(22, myList.remoteFirst());
        assertEquals(1, myList.size());
        assertEquals(11, myList.remoteFirst());
        assertEquals(0, myList.size());
        assertThrows(RuntimeException.class, () -> myList.remoteFirst());
    }

    @Test
    @DisplayName("Удаление последних")
    void remoteLastTest() {
        myList.addFirst(11);
        myList.addFirst(22);
        myList.addFirst(33);
        assertEquals(3, myList.size());
        assertEquals(11, myList.remoteLast());
        assertEquals(2, myList.size());
        assertEquals(22, myList.remoteLast());
        assertEquals(1, myList.size());
        assertEquals(33, myList.remoteLast());
        assertEquals(0, myList.size());
        assertThrows(RuntimeException.class, () -> myList.remoteLast());
    }

    @Test
    @DisplayName("Удаление по индексу")
    void remoteIndexTest() {
        myList.addFirst(11);
        myList.addFirst(22);
        myList.addFirst(33);
        assertEquals(3, myList.size());
        assertEquals(11, myList.remote(2));
        assertEquals(2, myList.size());
        assertEquals(33, myList.remote(0));
        assertEquals(1, myList.size());
        assertEquals(22, myList.remote(0));
        assertEquals(0, myList.size());
        assertThrows(RuntimeException.class, () -> myList.remote(0));
    }
}
