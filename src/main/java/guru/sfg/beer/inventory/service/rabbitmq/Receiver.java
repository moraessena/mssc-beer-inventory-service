package guru.sfg.beer.inventory.service.rabbitmq;

import guru.sfg.beer.inventory.service.configuration.RabbitMqConfiguration;
import guru.sfg.beer.inventory.service.domain.BeerInventory;
import guru.sfg.commons.events.NewInventoryEvent;
import guru.sfg.beer.inventory.service.repositories.BeerInventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class Receiver {

    private final BeerInventoryRepository beerInventoryRepository;

    @Autowired
    public Receiver(BeerInventoryRepository beerInventoryRepository) {
        this.beerInventoryRepository = beerInventoryRepository;
    }

    @RabbitListener(queues = RabbitMqConfiguration.NEW_INVENTORY_QUEUE)
    public void listToNewInventoryEvent(Message<NewInventoryEvent> newInventoryEvent, MessageHeaders headers) {
        log.info("Receiving new inventory event");
        Optional.ofNullable(newInventoryEvent.getPayload().getBeerDto()).ifPresent(dto -> {
            BeerInventory beerInventory = BeerInventory.builder()
                    .beerId(dto.getId())
                    .upc(dto.getUpc())
                    .quantityOnHand(dto.getStockQuantity())
                    .build();
            beerInventoryRepository.save(beerInventory);
        });
    }

}
