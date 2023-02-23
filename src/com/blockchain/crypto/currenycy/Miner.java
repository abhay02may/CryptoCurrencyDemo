package com.blockchain.crypto.currenycy;

import com.blockchain.crypto.blockchain.Block;
import com.blockchain.crypto.blockchain.BlockChain;
import com.blockchain.crypto.constants.Constants;

public class Miner {

    public double reward;

    public void mine(Block block, BlockChain blockChain){
        // It takes some time to find the valid has
        //
        while(!isGoldenHash(block)){
            block.incrementNonce();
            block.generateHash();
        }
        System.out.println(block+" has just mined ....");
        System.out.println(" Hash is : "+block.getHash());
        blockChain.addBlock(block);
        reward+= Constants.REWARD;
    }

    private boolean isGoldenHash(Block block) {
        String leadingZeros=new String(new char[Constants.DIFFICULTY]).replace('\0','0');
        return block.getHash().substring(0,Constants.DIFFICULTY).equals(leadingZeros);
    }


    public double getReward() {
        return reward;
    }
}