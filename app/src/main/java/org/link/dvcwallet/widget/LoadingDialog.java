package org.link.dvcwallet.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.link.dvcwallet.R;
import org.link.dvcwallet.facade.asyncjob.AsyncLoadingView;

/**
 * Created by liuhuanchao on 2018/9/20.
 */

public class LoadingDialog extends Dialog implements AsyncLoadingView {

    private TextView txLoadingInfo;

    public LoadingDialog(@NonNull Context context) {
        super(context);
    }

    public LoadingDialog(@NonNull Context context, String info) {
        super(context);
    }

    public LoadingDialog setLoadingInfo(String info){
        txLoadingInfo = findViewById(R.id.tv);
        txLoadingInfo.setText(info);
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** style config **/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        setContentView(R.layout.widget_loading);
        LinearLayout linearLayout = this.findViewById(R.id.LinearLayout);
        linearLayout.getBackground().setAlpha(210);
    }


    @Override
    public void init() {
    }
}
