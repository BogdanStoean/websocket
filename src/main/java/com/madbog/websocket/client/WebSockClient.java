package com.madbog.websocket.client;

import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WebSockClient {
    public static void main(String[] args) {

        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        WebSocketClient transport = new SockJsClient(transports);
        WebSocketStompClient stompClient = new WebSocketStompClient(transport);
        //        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        stompClient.setMessageConverter(new StringMessageConverter());
        stompClient.setTaskScheduler(new ConcurrentTaskScheduler());

        String url = "ws://127.0.0.1:8080/ws";
        StompSessionHandler sessionHandler = new MySessionHandler();
        stompClient.connect(url, sessionHandler);

        new Scanner(System.in).nextLine();
    }
}
