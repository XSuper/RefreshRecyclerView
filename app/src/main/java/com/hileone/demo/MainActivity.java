package com.hileone.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.hileone.recyclerview.RefreshRecyclerView;

import java.util.ArrayList;
import java.util.Random;

/**
 * The creator is Leone && E-mail: butleone@163.com
 *
 * @author Leone
 * @date 5/12/16
 * @description Edit it! Change it! Beat it! Whatever, just do it!
 */
public class MainActivity extends AppCompatActivity
        implements RefreshRecyclerView.OnRefreshListener, RefreshRecyclerView.OnLoadMoreListener {

    private RefreshRecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayUtils.init(this);

        recyclerView = new RefreshRecyclerView(this);
        setContentView(recyclerView);
        recyclerView.setRefreshListener(this);
        recyclerView.setLoadMoreListener(this);

        recyclerView.setLayoutManager();
        BaseAdapter adapter = new BaseAdapter();
        recyclerView.setAdapter(adapter);
        adapter.initData();
    }

    @Override
    public void onLoadMore() {
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                ((BaseAdapter) recyclerView.getAdapter()).addDataAndNotify();
                recyclerView.setLoadMoreDone(true);
            }
        }, 2000);
    }

    @Override
    public void onRefresh() {
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                ((BaseAdapter) recyclerView.getAdapter()).initData();
                recyclerView.setRefreshDone(true);
            }
        }, 2000);
    }

    private class BaseAdapter extends RecyclerView.Adapter<DemoViewHolder> {

        private ArrayList<String> data = new ArrayList<>();
        Random r = new Random();

        public void initData(){
            data.clear();
            for(int i = 0; i < 60; i++){
                data.add(r.nextInt(100) + "");
            }
            notifyDataSetChanged();
        }

        public void addDataAndNotify() {
            for (int i = 0; i < 6; i++) {
                data.add(r.nextInt(100) + "");
            }
            notifyDataSetChanged();
        }

        @Override
        public DemoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new DemoViewHolder(parent.getContext());
        }

        @Override
        public void onBindViewHolder(DemoViewHolder holder, int position) {
            holder.setText("Item" + position + "_" + data.get(position));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

}
