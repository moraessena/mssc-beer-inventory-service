package guru.sfg.beer.inventory.service.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.sfg.beer.inventory.service.rabbitmq.Receiver;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfiguration {

    public static final String BREWRY_BINDING = "brewry_binding";
    public static final String FANOUT_BREWRY_EXCHANGE_NAME = "brewry_exchange";
    public static final String BREW_BEER_QUEUE = "brew_beer_queue";

    public static final String NEW_INVENTORY_BINDING = "new_inventory_binding";
    public static final String FANOUT_INVENTORY_EXCHANGE_NAME = "inventory_exchange";
    public static final String NEW_INVENTORY_QUEUE = "new_inventory_queue";

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory,
                                         final Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(final ObjectMapper objectMapper) {
        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter(objectMapper);
        return jackson2JsonMessageConverter;
    }

}
