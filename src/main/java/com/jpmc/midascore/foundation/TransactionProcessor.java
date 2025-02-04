package com.jpmc.midascore.foundation;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.TransactionRecord;
import com.jpmc.midascore.repository.UserRepository;
import com.jpmc.midascore.repository.TransactionRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Component
public class TransactionProcessor {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRecordRepository transactionRecordRepository;

    @Transactional
    public void processTransaction(Transaction transaction) {
        // Fetch sender and recipient from the database using the transaction ID
        Optional<UserRecord> senderOptional = Optional.ofNullable(userRepository.findById(transaction.getSenderId()));
        Optional<UserRecord> recipientOptional = Optional.ofNullable(userRepository.findById(transaction.getRecipientId()));

        if (senderOptional.isPresent() && recipientOptional.isPresent()) {
            UserRecord sender = senderOptional.get();
            UserRecord recipient = recipientOptional.get();

            // Check if sender has enough balance
            if (sender.getBalance() >= transaction.getAmount()) {
                // Proceed with the transaction
                sender.setBalance(sender.getBalance() - transaction.getAmount());
                recipient.setBalance(recipient.getBalance() + transaction.getAmount());

                // Save the updated sender and recipient
                userRepository.save(sender);
                userRepository.save(recipient);

                // Save the transaction record
                TransactionRecord transactionRecord = new TransactionRecord(sender, recipient, transaction.getAmount(), new Date());
                transactionRecordRepository.save(transactionRecord);

                System.out.println("Transaction processed successfully!");
            } else {
                System.out.println("Transaction failed: Sender does not have enough balance.");
            }
        } else {
            System.out.println("Transaction failed: Sender or recipient not found.");
        }
    }
}
