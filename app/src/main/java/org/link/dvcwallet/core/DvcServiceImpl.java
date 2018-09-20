package org.link.dvcwallet.core;

import android.content.Context;

import org.link.dvcwallet.core.beans.DvcReceipt;
import org.link.dvcwallet.core.beans.GlobalConfig;
import org.link.dvcwallet.facade.DvcService;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.WalletUtils;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

import io.github.novacrypto.bip39.MnemonicGenerator;
import io.github.novacrypto.bip39.SeedCalculator;
import io.github.novacrypto.bip39.Words;
import io.github.novacrypto.bip39.wordlists.English;
import io.github.novacrypto.hashing.Sha256;

/**
 * Created by liuhuanchao on 2018/9/20.
 */

public class DvcServiceImpl extends DvcService<DvcReceipt> {

    public DvcServiceImpl(Context context) {
        super(context);
    }

    @Override
    public DvcReceipt createWallet(String password) {
        DvcReceipt dvcReceipt = new DvcReceipt();
        generateMnemonic(dvcReceipt);
        ECKeyPair keyPair = generateECKeyPairByMnemonic(dvcReceipt, dvcReceipt.getMnemonic(), password);

        File file = new File(GlobalConfig.walletFilePath);
        if (!file.exists()){
            file.mkdirs();
        }

        String fileName = null;
        try {
            fileName = WalletUtils.generateWalletFile(password, keyPair, file, false);
        } catch (CipherException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (fileName == null) return new DvcReceipt().setStatus(false);
        dvcReceipt.setWalletPath(GlobalConfig.walletFilePath + File.separator + fileName);
        return dvcReceipt;
    }

    @Override
    public DvcReceipt loadWallet(String password, String walletFilePath) {
        DvcReceipt dvcReceipt = new DvcReceipt();
        File file = new File(walletFilePath);
        Credentials credentials = null;
        try {
            credentials = WalletUtils.loadCredentials(password, file);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        }
        dvcReceipt.setWalletPath(credentials.getAddress());
        dvcReceipt.setPublicKey(credentials.getEcKeyPair().getPublicKey().toString(16));
        dvcReceipt.setPrivateKey(credentials.getEcKeyPair().getPrivateKey().toString(16));
        return dvcReceipt;
    }

    @Override
    public DvcReceipt loadWalletByMnemonic(String password, String mnemonic) {
        DvcReceipt dvcReceipt = new DvcReceipt();
        ECKeyPair keyPair = generateECKeyPairByMnemonic(dvcReceipt, mnemonic, password);

        makeWalletFile(dvcReceipt, password, keyPair);

        return dvcReceipt;
    }

    @Override
    public DvcReceipt exportWallet(String password) {
        return null;
    }

    private void generateMnemonic(DvcReceipt dvcReceipt) {
        StringBuilder sb = new StringBuilder();
        byte[] entropy = new byte[Words.TWELVE.byteLength()];
        new SecureRandom().nextBytes(entropy);
        new MnemonicGenerator(English.INSTANCE)
                .createMnemonic(entropy, sb::append);
        dvcReceipt.setMnemonic(sb.toString());
    }

    private ECKeyPair generateECKeyPairByMnemonic(DvcReceipt dvcReceipt, String mnemonic, String password){
        List mnemonicList = Arrays.asList(mnemonic.split(" "));
        byte[] seed = new SeedCalculator()
                .withWordsFromWordList(English.INSTANCE)
                .calculateSeed(mnemonicList, password);
        ECKeyPair ecKeyPair = ECKeyPair.create(Sha256.sha256(seed));
        String privateKey = ecKeyPair.getPrivateKey().toString(16);
        String publicKey = ecKeyPair.getPublicKey().toString(16);
        String address = "0x" + Keys.getAddress(publicKey);
        dvcReceipt.setPrivateKey(privateKey);
        dvcReceipt.setPublicKey(publicKey);
        dvcReceipt.setAddress(address);
        return ecKeyPair;
    }

    private void makeWalletFile(DvcReceipt dvcReceipt, String password, ECKeyPair keyPair){
        File file = new File(GlobalConfig.walletFilePath);
        if (!file.exists()){
            file.mkdirs();
        }

        String fileName = null;
        try {
            fileName = WalletUtils.generateWalletFile(password, keyPair, file, false);
        } catch (CipherException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (fileName == null) {dvcReceipt.setStatus(false); return;}
        dvcReceipt.setWalletPath(GlobalConfig.walletFilePath + File.separator + fileName);
    }
}
