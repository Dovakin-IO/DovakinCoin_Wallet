package org.link.dvcwallet.core.event;

public class BlockMinedEvent {

    private String balance;

    public BlockMinedEvent() {}

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
