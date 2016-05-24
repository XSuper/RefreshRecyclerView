package com.hileone.demo.new_refresh;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hileone.demo.BaseAdapter;
import com.hileone.recyclerview.RefreshRecyclerView;

public class NewRefreshActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RefreshRecyclerView recyclerView = new RefreshRecyclerView(this);
        setContentView(recyclerView);

        initListener(recyclerView, 60);
        recyclerView.setLayoutManager();
        recyclerView.setAllowRefresh(true);
        recyclerView.setAllowLoadMore(true);
        recyclerView.setAutoLoadMore(true);
        recyclerView.setFooterEdge(new NewFooterEdge(this));
        recyclerView.setHeaderEdge(new NewHeaderEdge(this));

        BaseAdapter adapter = new BaseAdapter();
        recyclerView.setAdapter(adapter);
        adapter.updateData(60);

    }

    private void initListener(final RefreshRecyclerView view, final int countSize) {
        view.setRefreshListener(new RefreshRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ((BaseAdapter) view.getAdapter()).updateData(countSize);
                        view.setRefreshDone(true);
                    }
                }, 2000);
            }
        });

        view.setLoadMoreListener(new RefreshRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ((BaseAdapter) view.getAdapter()).addDataAndNotify();
                        view.setLoadMoreDone(true);
                    }
                }, 2000);
            }
        });
    }
}
