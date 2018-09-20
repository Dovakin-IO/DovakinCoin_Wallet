package org.link.dvcwallet.utils;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import org.link.dvcwallet.facade.asyncjob.AsyncCallBack;
import org.link.dvcwallet.facade.asyncjob.AsyncExcutor;
import org.link.dvcwallet.facade.asyncjob.AsyncJob;
import org.link.dvcwallet.widget.LoadingDialog;

/**
 * Created by liuhuanchao on 2018/9/20.
 */

public class AsyncExecutorImpl implements AsyncExcutor {

    private Context mContext;
    private AsyncJob job = null;
    private AsyncCallBack callBack = null;
    private AsyncExecutorImpl instance = null;
    private LoadingDialog loading;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            String result = bundle.getString("result");
            loading.dismiss();
            callBack.onSuccess(result);
        }
    };

    private AsyncExecutorImpl(){}

    public AsyncExecutorImpl(Context context, AsyncJob func1, AsyncCallBack func2){
        this.mContext = context;
        this.job = func1;
        this.callBack = func2;

        loading = new LoadingDialog(mContext);
        loading.setCanceledOnTouchOutside(false);
        loading.setCancelable(false);
    }

    public AsyncExecutorImpl getInstance(Context context, AsyncJob func1, AsyncCallBack func2){
        if(instance == null) {
            return new AsyncExecutorImpl(context, func1, func2);
        } else {
            job = func1;
            callBack = func2;
            return instance;
        }
    }

    @Override
    public void excute() {
        Runnable runnable  = () -> {
            String result = job.work();
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putString("result", result);
            message.setData(bundle);
        };
        loading.setLoadingInfo("加载中...")
                .show();
        new Thread(runnable).start();
    }
}
