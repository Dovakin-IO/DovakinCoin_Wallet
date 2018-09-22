package org.link.dvcwallet.core;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.link.dvcwallet.core.event.BlockMinedEvent;
import org.link.dvcwallet.facade.BlockChainService;
import org.link.dvcwallet.utils.GlobalCache;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.utils.Convert;

import java.io.IOException;

import rx.Subscription;

public class BlockChainServiceImpl extends BlockChainService {

    private Subscription subscription;

    public BlockChainServiceImpl(String address) {
        super(address);
    }

    @Override
    public void subscribBlock() {
        startSubscriptionForBlock();
    }

    public void startSubscriptionForBlock() {
        new Thread(() -> subscription = GlobalCache.web3j.blockObservable(false)
                .subscribe(ethBlock -> {
                    try {
                        EthGetBalance ethGetBalance
                                = GlobalCache.
                                web3j.ethGetBalance(address,
                                DefaultBlockParameter.valueOf("latest"))
                                .send();
                        String balanceETH = Convert.fromWei(ethGetBalance.getBalance().toString(),
                                Convert.Unit.ETHER).toPlainString();

                        BlockMinedEvent blockMinedEvent = new BlockMinedEvent();
                        blockMinedEvent.setBalance(balanceETH);
                        EventBus.getDefault().post(blockMinedEvent);

//                        List<EthBlock.TransactionResult> results
//                                = ethBlock.getBlock().getTransactions();
//                        for (EthBlock.TransactionResult result : results){
//                            result.get();
//                        }
                        Log.i("TEST", String.valueOf(ethBlock.getBlock().getNumber()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                })
        ).start();
    }
}
