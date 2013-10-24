package example;

public interface OrderRepository {
    Order getOrder(String id);

    void save(Order order);
}
