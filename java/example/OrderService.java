package example;

public class OrderService {

    private AddressValidator addressValidator;
    private OrderRepository orderRepository;

    public void addAddressToOrder(String id, Address newAddress) {
        boolean isValidated = addressValidator.validate(newAddress);
        if (isValidated) {
            Order order = orderRepository.getOrder(id);
            if (order != null) {
                order.setAddress(newAddress);
                orderRepository.save(order);
            } else {
                throw new IllegalArgumentException("example.Order id " + id + " not found");
            }
        }
        else {
            throw new IllegalArgumentException("Invalid address provided");
        }
    }
}
