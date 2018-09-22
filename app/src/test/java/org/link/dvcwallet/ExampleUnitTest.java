package org.link.dvcwallet;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.link.dvcwallet.core.beans.GlobalConfig;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import rx.Subscription;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private Web3j web3j;

    @Before
    public void initWeb3j(){
        web3j = Web3j.build(new HttpService(GlobalConfig.peer_address));
    }

    @Test
    public void testSubscription(){
        Subscription subscription = web3j.blockObservable(false).subscribe(ethBlock -> {
           Log.i("TEST", String.valueOf(ethBlock.getBlock().getNumber()));
        });
        subscription.unsubscribe();
    }

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
}