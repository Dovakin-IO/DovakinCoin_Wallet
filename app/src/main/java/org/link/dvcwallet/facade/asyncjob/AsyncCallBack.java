package org.link.dvcwallet.facade.asyncjob;

/**
 * Created by liuhuanchao on 2018/9/20.
 */

public interface AsyncCallBack {

    public void onSuccess(String msg);

    public void onFaild(String msg);
}
