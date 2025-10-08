package org.example;

import java.util.List;

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

}
