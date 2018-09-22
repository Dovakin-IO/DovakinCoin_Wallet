package org.link.dvcwallet;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.link.dvcwallet.core.event.BlockMinedEvent;

public class WalletActivity extends AppCompatActivity {

    private String ethBalance;
    private TextView tv_eth_balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        tv_eth_balance = findViewById(R.id.eth_balance);
    }

    @SuppressLint("StringFormatInvalid")
    @Subscribe(threadMode =  ThreadMode.MAIN)
    public void onBlockMinedEvent(BlockMinedEvent event){
        if(event != null){
            ethBalance = event.getBalance();
            Log.i("balance", ethBalance);
            Toast.makeText(WalletActivity.this, "balance: " + ethBalance, Toast.LENGTH_LONG)
                    .show();
            tv_eth_balance.setText(ethBalance);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
