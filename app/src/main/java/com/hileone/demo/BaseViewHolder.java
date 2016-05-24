package com.hileone.demo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * The creator is Leone && E-mail: butleone@163.com
 *
 * @author Leone
 * @date 5/12/16
 * @description Edit it! Change it! Beat it! Whatever, just do it!
 */
public class BaseViewHolder extends RecyclerView.ViewHolder{
    private Button mButton;

    /**
     * BaseViewHolder
     * @param context context
     */
    public BaseViewHolder(Context context) {
        super(new Button(context));

        mButton = (Button) itemView;
        mButton.setAllCaps(false);
        mButton.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
    }

    /**
     * setOnClick
     */
    public void setOnClick() {
        if (mButton != null) {
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), mButton.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /**
     * setText
     * @param text text
     */
    public void setText(String text){
        if (mButton != null && !TextUtils.isEmpty(text)) {
            mButton.setText(text);
        }
    }
}
