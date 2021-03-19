package ru.rrbagautdinov.rabbitmq.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;


public class ExchangeSenderApp {
    private static final String EXCHANGE_NAME = "directExchanger";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

            Scanner in = new Scanner(System.in);
            System.out.print("Input the title of article and info: ");
            String message = in.nextLine();
            String[] arr = message.split(" ",2);
            String firstWord = arr[0];
            String theRest = arr[1];
            System.out.println(firstWord);
            System.out.println(theRest);

            channel.basicPublish(EXCHANGE_NAME, firstWord, null, theRest.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}