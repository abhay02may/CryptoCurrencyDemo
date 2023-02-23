package com.blockchain.crypto.app;

import com.blockchain.crypto.blockchain.Block;
import com.blockchain.crypto.blockchain.BlockChain;
import com.blockchain.crypto.constants.Constants;
import com.blockchain.crypto.currenycy.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.KeyPair;
import java.security.Security;
import java.util.Base64;

public class MainApp {
    public static void main(String[] args) {

        Security.addProvider(new BouncyCastleProvider());
        KeyPair keyPair= CryptographyHelper.ellipticCurveCrypto();
//        System.out.println(Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded()));
//        System.out.println(Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()));

        Wallet userA=new Wallet();
        Wallet userB=new Wallet();
        Wallet lender=new Wallet();
        BlockChain blockChain=new BlockChain();
        Miner miner=new Miner();

        Transaction genesisTransaction=new Transaction(lender.getPublicKey(), userA.getPublicKey(), 500,null);
        genesisTransaction.generateSignature(lender.getPrivateKey());
        genesisTransaction.setTransactionId("0");
        genesisTransaction.outputs.add(
                new TransactionOutput(genesisTransaction.getReceiver(), genesisTransaction.getAmount(),
                genesisTransaction.getTransactionId()));
        BlockChain.UTXOs.put(genesisTransaction.outputs.get(0).getId(),genesisTransaction.outputs.get(0));
        System.out.println("Constructing the genesis block....");

        Block genesis=new Block(Constants.GENESIS_PREV_HASH);
        genesis.addTransactions(genesisTransaction);
        miner.mine(genesis,blockChain);

        Block block1=new Block(genesis.getHash());
        System.out.println("\n UsersA balance is : "+userA.calculateBalance());
        System.out.println("\n UsersA tries to send money to User B (120coin) ");
        block1.addTransactions(userA.transferMoney(userB.getPublicKey(), 120));
        miner.mine(block1,blockChain);
        System.out.println("\n UsersA balance is : "+userA.calculateBalance());
        System.out.println("\n UsersB balance is : "+userB.calculateBalance());

        Block block2 = new Block(block1.getHash());
        System.out.println("\nuserA sends more funds (600) than it has...");
        block2.addTransactions(userA.transferMoney(userB.getPublicKey(), 600));
        miner.mine(block2,blockChain);
        System.out.println("\nuserA's balance is: " + userA.calculateBalance());
        System.out.println("userB's balance is: " + userB.calculateBalance());

        Block block3 = new Block(block2.getHash());
        System.out.println("\nuserB is attempting to send funds (110) to userA...");
        block3.addTransactions(userB.transferMoney( userA.getPublicKey(), 110));
        System.out.println("\nuserA's balance is: " + userA.calculateBalance());
        System.out.println("userB's balance is: " + userB.calculateBalance());
        miner.mine(block3,blockChain);

        System.out.println("Miner's reward: "+miner.getReward());



    }
}
