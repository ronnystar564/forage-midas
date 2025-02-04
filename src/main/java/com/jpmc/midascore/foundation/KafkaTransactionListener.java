package com.jpmc.midascore.foundation;

import com.jpmc.midascore.foundation.Transaction;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@EnableKafka
@Service
public class KafkaTransactionListener {

    @Value("${general.kafka-topic}") // Pull the topic name from application.yml
    private String topic;

    // Kafka Listener for incoming transaction messages
    @KafkaListener(topics = "${general.kafka-topic}", groupId = "midas-core-group")
    public void listenTransaction(Transaction transaction) {
        // Log the transaction or perform processing (for now, we're just logging)
        System.out.println("Received transaction: " + transaction);

        // Add any further logic here to process the transaction, if needed
    }
}
