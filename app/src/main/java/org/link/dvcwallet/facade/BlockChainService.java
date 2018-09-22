package org.link.dvcwallet.facade;

public abstract class BlockChainService {

    protected String address;

    public BlockChainService(String address) {
        this.address = address;
    }


    public abstract void subscribBlock();

}
