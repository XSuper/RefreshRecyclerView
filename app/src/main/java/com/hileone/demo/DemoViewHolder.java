package com.hileone.demo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * The creator is Leone && E-mail: butleone@163.com
 *
 * @author Leone
 * @date 5/12/16
 * @description Edit it! Change it! Beat it! Whatever, just do it!
 */
public class DemoViewHolder extends RecyclerView.ViewHolder{
    Button button;
    public DemoViewHolder(Context context) {
        super(new Button(context));

        button = (Button) itemView;
        button.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));

    }

    public void setText(String text){
        button.setText(text);
    }
}
