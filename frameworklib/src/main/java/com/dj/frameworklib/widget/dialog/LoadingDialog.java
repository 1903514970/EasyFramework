package com.dj.frameworklib.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Window;
import android.widget.TextView;

import com.dj.frameworklib.R;


/**
 * Created by dengjun on 2019/3/28.
 */

public class LoadingDialog extends Dialog {

    private boolean isSetWindow = false;

    private TextView mHintTv;

    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.loading_dialog);
        setContentView(R.layout.dialog_loading);
        mHintTv = findViewById(R.id.dialog_loading_tv);
    }

    @Override
    public void show() {
        if(!isSetWindow){
            Window window = getWindow();
            if(window != null){
                window.setBackgroundDrawableResource(android.R.color.transparent);
                isSetWindow = true;
            }
        }
        super.show();
    }


    public void show(CharSequence hintText){
        if(hintText != null){
            mHintTv.setText(hintText);
        }
        show();
    }
}
