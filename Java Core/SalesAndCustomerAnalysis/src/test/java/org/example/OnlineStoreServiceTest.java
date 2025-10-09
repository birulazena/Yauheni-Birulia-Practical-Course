package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class OnlineStoreServiceTest {

    private OnlineStoreService service;

    private List<Order> orders;

    @BeforeEach
    void init() {
        Customer c1 = new Customer("1",
                "Zenya",
                "zenya@mail.com",
                LocalDateTime.now(),
                18,
                "Minsk");

        Customer c2 = new Customer("2",
                "Vanya",
                "vanya@gmail.com",
                LocalDateTime.now(),
                19,
                "Minsk");

        Customer c3 = new Customer("3",
                "Yana",
                "yana@mail.com",
                LocalDateTime.now(),
                18,
                "Sochi");

        Customer c4 = new Customer("4",
                "Arjom",
                "arjom@mail.com",
                LocalDateTime.now(),
                20,
                "Brest");

        org.example.Order o1 = new org.example.Order("1",
                LocalDateTime.now(),
                c1,
                List.of(new OrderItem("Математика", 20, 30.5, Category.BOOKS),
                        new OrderItem("Физика", 24, 10.5, Category.BOOKS),
                        new OrderItem("Оперативка", 200, 78.6, Category.ELECTRONICS)
                ),
                OrderStatus.DELIVERED);

        org.example.Order o2 = new org.example.Order("2",
                LocalDateTime.now(),
                c2,
                List.of(new OrderItem("Процессор", 1, 90.4, Category.ELECTRONICS),
                        new OrderItem("Цветок", 3, 12.4, Category.HOME)
                ),
                OrderStatus.DELIVERED);

        org.example.Order o3 = new org.example.Order("3",
                LocalDateTime.now(),
                c2,
                List.of(new OrderItem("Крем", 300, 400, Category.BEAUTY),
                        new OrderItem("Пудра", 200, 23.5, Category.BEAUTY)),
                OrderStatus.PROCESSING);

        org.example.Order o4 = new org.example.Order("4",
                LocalDateTime.now(),
                c4,
                List.of(new OrderItem("сказки", 2, 34.6, Category.BOOKS)),
                OrderStatus.DELIVERED);

        org.example.Order o5 = new org.example.Order("5",
                LocalDateTime.now(),
                c4,
                List.of(new OrderItem("Крем", 2, 34.6, Category.BEAUTY)),
                OrderStatus.PROCESSING);

        org.example.Order o6 = new org.example.Order("6",
                LocalDateTime.now(),
                c4,
                List.of(new OrderItem("Комп", 1, 834.6, Category.ELECTRONICS)),
                OrderStatus.CANCELLED);

        org.example.Order o7 = new org.example.Order("7",
                LocalDateTime.now(),
                c4,
                List.of(new OrderItem("Ваза", 20, 345.6, Category.HOME)),
                OrderStatus.DELIVERED);

        org.example.Order o8 = new org.example.Order("8",
                LocalDateTime.now(),
                c4,
                List.of(new OrderItem("Телик", 1, 3499.6, Category.ELECTRONICS)),
                OrderStatus.PROCESSING);

        org.example.Order o9 = new org.example.Order("9",
                LocalDateTime.now(),
                c4,
                List.of(new OrderItem("Стиралка", 2, 734.6, Category.ELECTRONICS)),
                OrderStatus.DELIVERED);

        org.example.Order o10 = new org.example.Order("10",
                LocalDateTime.now(),
                c2,
                List.of(new OrderItem("сказки", 2, 34.6, Category.BOOKS)),
                OrderStatus.DELIVERED);

        orders = new LinkedList<>();

        orders.add(o1);
        orders.add(o2);
        orders.add(o3);
        orders.add(o4);
        orders.add(o5);
        orders.add(o6);
        orders.add(o7);
        orders.add(o8);
        orders.add(o9);
        orders.add(o10);

        service = new OnlineStoreService(orders);
    }

    @Test
    @DisplayName("Уникальные города, откукда поступали заказы")
    void citiesWhereOrdersCameFromTest() {
        assertLinesMatch(List.of("Minsk", "Brest"), service.citiesWhereOrdersCameFrom());
    }

    @Test
    @DisplayName("Общая сумма всех заказов")
    void totalIncomeAllCompletedOrdersTest() {
        assertEquals(25229.2, service.totalIncomeAllCompletedOrders());
    }

    @Test
    @DisplayName("Самый популярный товар по продажам")
    void mostPopularProductBySalesTest() {
        assertEquals("Крем", service.mostPopularProductBySales());
    }

    @Test
    @DisplayName("Средняя сумма по всем заказам")
    void averageCheckSuccessfullyDeliveredOrdersTest() {
        assertEquals(4204.866666666667, service.averageCheckSuccessfullyDeliveredOrders());
    }

    @Test
    @DisplayName("Клиенты, у которых более пяти заказов")
    void customersWhoHaveMoreThan5OrdersTest() {
        assertEquals("Arjom", service.customersWhoHaveMoreThan5Orders().getFirst().getName());
    }
}
