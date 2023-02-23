package com.blockchain.crypto.currenycy;

import java.security.PublicKey;

public class TransactionOutput {

    // Identifier of the transaction Output (SHA-256)
    private String id;
    // transactionId of the parent
    private String parentTransactionId;
    // The new owner of the client
    private PublicKey receiver;
    // amount of coins
    private double amount;


    public TransactionOutput(PublicKey receiver, double amount,String parentTransactionId) {
        this.parentTransactionId = parentTransactionId;
        this.receiver = receiver;
        this.amount = amount;
        generateId();
    }

    private void generateId() {
        this.id=CryptographyHelper.generateHash(receiver.toString()+Double.toString(amount)+parentTransactionId.toString());
    }

    public boolean isMine(PublicKey publicKey){
        return receiver==publicKey;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentTransactionId() {
        return parentTransactionId;
    }

    public void setParentTransactionId(String parentTransactionId) {
        this.parentTransactionId = parentTransactionId;
    }

    public PublicKey getReceiver() {
        return receiver;
    }

    public void setReceiver(PublicKey receiver) {
        this.receiver = receiver;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
