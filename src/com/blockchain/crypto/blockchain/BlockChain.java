package com.blockchain.crypto.blockchain;

import com.blockchain.crypto.currenycy.TransactionOutput;

import java.util.*;

public class BlockChain {

    // This is a public ledger anyone can access it
    // Immutable ledger, we can't remove blocks
    public static List<Block> blockChain;
    public static Map<String, TransactionOutput> UTXOs;

    public BlockChain(){
        BlockChain.blockChain=new ArrayList<>();
        BlockChain.UTXOs=new HashMap<String, TransactionOutput>();
    }

    public void addBlock(Block block){
        BlockChain.blockChain.add(block);
    }

    public List<Block> getBlockChain(){
        return BlockChain.blockChain;
    }

    public int getSize(){
        return BlockChain.blockChain.size();
    }

    @Override
    public String toString() {
        StringBuilder builder=new StringBuilder();
        for(Block block: blockChain){
            builder.append(block.toString()+"\n");
        }
        return builder.toString();
    }
}
