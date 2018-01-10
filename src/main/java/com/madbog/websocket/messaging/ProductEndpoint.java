package com.madbog.websocket.messaging;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ProductEndpoint {

    @MessageMapping("/import")
    @SendTo("/topic/products")
    public String products(List<Product> products) throws Exception {
        Thread.sleep(1000); // simulated delay
        System.out.println(products);
        return "Submitted " + products.size() + " products";
    }
}
