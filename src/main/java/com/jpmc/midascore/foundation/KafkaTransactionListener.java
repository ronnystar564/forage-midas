package com.jpmc.midascore.foundation;

import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.foundation.TransactionProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@EnableKafka
@Service
public class KafkaTransactionListener {

    private final TransactionProcessor transactionProcessor;  // Inject the transaction processor

    @Autowired
    public KafkaTransactionListener(TransactionProcessor transactionProcessor) {
        this.transactionProcessor = transactionProcessor;  // Assign it in the constructor
    }

    // Kafka Listener for incoming transaction messages
    @KafkaListener(topics = "${general.kafka-topic}", groupId = "midas-core-group")
    public void listenTransaction(Transaction transaction) {
        // Log the transaction received from Kafka
        System.out.println("Received transaction: " + transaction);

        // Delegate the processing of the transaction to the TransactionProcessor
        transactionProcessor.processTransaction(transaction);
    }
}
