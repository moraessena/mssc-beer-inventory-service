package guru.sfg.beer.inventory.service.rabbitmq;

import org.springframework.stereotype.Component;

@Component
public class Receiver {
    public void receiveMessage(String message) {
        System.out.println(message);
    }
}
