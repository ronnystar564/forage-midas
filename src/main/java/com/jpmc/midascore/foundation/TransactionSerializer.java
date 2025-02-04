package com.jpmc.midascore.foundation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class TransactionSerializer implements Serializer<Transaction> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // You can configure additional settings for serialization if needed
    }

    @Override
    public byte[] serialize(String topic, Transaction data) {
        try {
            if (data == null) {
                return null;
            }
            // Serialize the Transaction object into JSON
            return objectMapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing Transaction object", e);
        }
    }

    @Override
    public void close() {
        // No resources to close
    }
}
