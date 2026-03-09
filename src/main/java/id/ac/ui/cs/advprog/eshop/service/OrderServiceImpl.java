package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order createOrder(Order order) {
        if (order.getId() == null) {
            Order newOrder = new Order(UUID.randomUUID().toString(),
                    order.getProducts(), order.getOrderTime(), order.getAuthor(), order.getStatus());
            return orderRepository.save(newOrder);
        }
        return orderRepository.save(order);
    }

    @Override
    public Order updateStatus(String orderId, String status) {
        Order order = orderRepository.findById(orderId);
        if (order != null) {
            Order newOrder = new Order(order.getId(), order.getProducts(),
                    order.getOrderTime(), order.getAuthor(), status);
            orderRepository.save(newOrder);
            return newOrder;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Order findById(String orderId) {
        return orderRepository.findById(orderId);
    }

    @Override
    public List<Order> findAllByAuthor(String author) {
        return orderRepository.findAllByAuthor(author);
    }
}