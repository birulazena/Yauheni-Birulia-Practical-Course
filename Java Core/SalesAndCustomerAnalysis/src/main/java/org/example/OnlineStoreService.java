package org.example;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class OnlineStoreService {

    private List<Order> orders;

    public OnlineStoreService(List<Order> orders) {
        this.orders = orders;
    }

    public List<String> citiesWhereOrdersCameFrom() {
        return orders.stream()
                .filter(c -> c.getStatus() == OrderStatus.DELIVERED)
                .map(c -> c.getCustomer().getCity())
                .distinct()
                .toList();

    }

    public double totalIncomeAllCompletedOrders() {
        return orders.stream()
                .filter(c -> c.getStatus() == OrderStatus.DELIVERED)
                .mapToDouble(c -> c.getItems().stream().mapToDouble(i -> i.getPrice()).sum())
                .sum();

    }

    public String mostPopularProductBySales() {
        return orders.stream()
                .flatMap(i -> i.getItems()
                        .stream())
                .collect(Collectors.groupingBy(OrderItem::getProductName, Collectors.summingInt(OrderItem::getQuantity)))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .toString();
    }

}
