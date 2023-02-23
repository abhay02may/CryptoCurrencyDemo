package com.blockchain.crypto.currenycy;

import com.blockchain.crypto.blockchain.BlockChain;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Wallet {

    private PrivateKey privateKey;
    private PublicKey publicKey;

    public Wallet(){
        KeyPair keyPair=CryptographyHelper.ellipticCurveCrypto();
        this.privateKey= keyPair.getPrivate();
        this.publicKey= keyPair.getPublic();
    }

    // There is no balance associated with the user
    // UTXOs and consider all the transactions done in the past
    public double calculateBalance(){
        double balance=0;
        for (Map.Entry<String,TransactionOutput> item: BlockChain.UTXOs.entrySet()){
            TransactionOutput transactionOutput= item.getValue();
            if(transactionOutput.isMine(publicKey)){
                balance+= transactionOutput.getAmount();
            }
        }
        return balance;
    }

    // We are able to transfer money and miners of the blockchain will put this transaction into blockchain
    public Transaction transferMoney(PublicKey receiver, double amount){
        if(calculateBalance()<amount){
            System.out.println("Invalid transaction because of not enough money....");
            return null;
        }
        // storing the inputs for the transaction
        List<TransactionInput> inputs=new ArrayList<>();
        // lets find un-spend transactions from UTXOs
        for (Map.Entry<String,TransactionOutput> item: BlockChain.UTXOs.entrySet()){
            TransactionOutput UTXO= item.getValue();
                if(UTXO.isMine(this.publicKey)){
                    inputs.add(new TransactionInput(UTXO.getId()));
                }
        }
        // lets create the new transaction
        Transaction newTransaction=new Transaction(publicKey,receiver,amount,inputs);
        newTransaction.generateSignature(privateKey);

        return newTransaction;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }
}
