package com.blockchain.crypto.blockchain;

import com.blockchain.crypto.constants.Constants;
import com.blockchain.crypto.currenycy.CryptographyHelper;
import com.blockchain.crypto.currenycy.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Block {

    private int id;
    private int nonce;
    private long timeStamp;
    private String hash;
    private String previousHash;
    public List<Transaction> transactions;

    public Block() {
    }

    public Block(String previousHash ) {
        this.transactions = new ArrayList<>();
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        generateHash();
    }



    public void generateHash() {
        String dataToHash=Integer.toString(id)+previousHash+Long.toString(timeStamp)+
                transactions.toString()+Integer.toString(nonce);
        this.hash= CryptographyHelper.generateHash(dataToHash);
    }

    public void incrementNonce(){
        this.nonce++;
    }

    public boolean addTransactions(Transaction transaction){
        if(transaction==null){
            return false;
        }
        // if the block is the genesis block we do not process
        if(!previousHash.equals(Constants.GENESIS_PREV_HASH)){
            if(!transaction.verifyTransaction()){
                System.out.println("Transaction is not valid !!!");
                return false;
            }
        }
        transactions.add(transaction);
        System.out.println("Transaction is valid and it is added to the block ...."+this);
        return true;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNonce() {
        return nonce;
    }

    public void setNonce(int nonce) {
        this.nonce = nonce;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "Block{" +
                "id=" + id +
                ", hash='" + hash + '\'' +
                ", previousHash='" + previousHash + '\'' +
                ", transaction='" + transactions + '\'' +
                '}';
    }
}