package org.link.dvcwallet.facade;

import android.content.Context;

import org.web3j.protocol.Web3j;

/**
 * Created by liuhuanchao on 2018/9/20.
 */

public abstract class DvcService<T> {

    protected Context mContext;

    private DvcService(){}

    public DvcService(Context context){
        this.mContext = context;
    }

    /**
     * 连接以太坊节点
     * @return
     */
    public abstract Web3j connectPeer(String address);

    /**
     * 创建钱包
     * @param password
     * @return
     */
    public abstract T createWallet(String password);

    /**
     * 加载钱包
     * @param password
     * @param walletFilePath
     * @return
     */
    public abstract T loadWallet(String password, String walletFilePath);

    /**
     * 通过助记词导入钱包
     * @param mnemonic
     * @return
     */
    public abstract T loadWalletByMnemonic(String password, String mnemonic);

    /**
     * 导出钱包
     * @param password
     * @return
     */
    public abstract T exportWallet(String password);
}
