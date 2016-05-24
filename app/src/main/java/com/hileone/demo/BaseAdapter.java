package com.hileone.demo;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Random;

/**
 * The creator is Leone && E-mail: butleone@163.com
 *
 * @author Leone
 * @date 5/23/16
 * @description Edit it! Change it! Beat it! Whatever, just do it!
 */
public class BaseAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private ArrayList<String> data = new ArrayList<>();
    private Random r = new Random();

    /**
     * update data
     * @param size size
     */
    public void updateData(int size){
        data.clear();
        for(int i = 0; i < size; i++){
            data.add(r.nextInt(100) + "");
        }
        notifyDataSetChanged();
    }

    /**
     * loadmore to add data
     */
    public void addDataAndNotify() {
        for (int i = 0; i < 6; i++) {
            data.add(r.nextInt(100) + "");
        }
        notifyDataSetChanged();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder(parent.getContext());
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.setText("item at " + position + "th And value is " + data.get(position));
        holder.setOnClick();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
