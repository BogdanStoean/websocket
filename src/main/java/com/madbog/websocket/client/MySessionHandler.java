package com.madbog.websocket.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.madbog.websocket.messaging.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Slf4j
public class MySessionHandler extends StompSessionHandlerAdapter {
    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        session.subscribe("/topic/products", this);

        List<Product> products = Collections.singletonList(Product
          .builder()
          .name("product")
          .price(BigDecimal.TEN)
          .build());

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            session.send("/app/import", objectMapper.writeValueAsString(products).getBytes());
            session.send("/app/import", objectMapper.writeValueAsString(products).getBytes());
            session.send("/app/import", objectMapper.writeValueAsString(products).getBytes());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        log.info("New session: {}", session.getSessionId());
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        exception.printStackTrace();
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return String.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        log.info("Received: {}", payload);
    }
}
