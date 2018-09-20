package org.link.dvcwallet.core.beans;

import org.web3j.crypto.ECKeyPair;

/**
 * Created by liuhuanchao on 2018/9/20.
 */

public class DvcReceipt {

    private Boolean status;
    private String address;
    private String walletPath;
    private String privateKey;
    private String publicKey;
    private String mnemonic;

    public Boolean getStatus() {
        return status;
    }

    public DvcReceipt setStatus(Boolean status) {
        this.status = status;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWalletPath() {
        return walletPath;
    }

    public void setWalletPath(String walletPath) {
        this.walletPath = walletPath;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getMnemonic() {
        return mnemonic;
    }

    public void setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
    }
}
