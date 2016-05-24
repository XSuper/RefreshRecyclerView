package com.hileone.demo.all_condition;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.hileone.demo.BaseAdapter;
import com.hileone.demo.BasePagerAdapter;
import com.hileone.recyclerview.RefreshRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AllConditionActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ViewPager viewPager = new ViewPager(this);
        setContentView(viewPager);

        final List<View> viewList = new ArrayList<>();

        viewList.add(initOneView()); // no refresh、no loadmore, just pure recyclerview

        viewList.add(initTwoView()); // only refresh、no loadmore

        viewList.add(initThreeView()); // could refresh and loadmore

        viewList.add(initFourView()); // could refresh and loadmore, but list is too short

        viewPager.addOnPageChangeListener(this);
        viewPager.setAdapter(new BasePagerAdapter(viewList));
    }

    private View initOneView() {
        final RefreshRecyclerView recyclerView = new RefreshRecyclerView(AllConditionActivity.this);

        initListener(recyclerView, 60);
        recyclerView.setLayoutManager();
        recyclerView.setAllowRefresh(false); // no pull_to_loadmore
        recyclerView.setAllowLoadMore(false); // no drag_to_loadmore

        BaseAdapter adapter = new BaseAdapter();
        recyclerView.setAdapter(adapter);
        adapter.updateData(60);

        return recyclerView;
    }

    private View initTwoView() {
        final RefreshRecyclerView recyclerView = new RefreshRecyclerView(AllConditionActivity.this);

        initListener(recyclerView, 60);
        recyclerView.setLayoutManager();
        recyclerView.setAllowRefresh(true); // could pull_to_loadmore
        recyclerView.setAllowLoadMore(false); // no drag_to_loadmore

        BaseAdapter adapter = new BaseAdapter();
        recyclerView.setAdapter(adapter);
        adapter.updateData(60);

        return recyclerView;
    }

    private View initThreeView() {
        final RefreshRecyclerView recyclerView = new RefreshRecyclerView(AllConditionActivity.this);

        initListener(recyclerView, 6);
        recyclerView.setLayoutManager();
        recyclerView.setAllowRefresh(true); // no pull_to_loadmore
        recyclerView.setAllowLoadMore(true); // no drag_to_loadmore

        BaseAdapter adapter = new BaseAdapter();
        recyclerView.setAdapter(adapter);
        adapter.updateData(6); // list to short

        return recyclerView;
    }

    private View initFourView() {
        RefreshRecyclerView recyclerView = new RefreshRecyclerView(AllConditionActivity.this);

        initListener(recyclerView, 60);
        recyclerView.setLayoutManager();
        recyclerView.setAutoRefresh(false); // no auto refresh
        recyclerView.setAutoLoadMore(true); // could auto loadmore
        recyclerView.setAllowRefresh(true); // could pull_to_loadmore
        recyclerView.setAllowLoadMore(true); // could drag_to_loadmore

        BaseAdapter adapter = new BaseAdapter();
        recyclerView.setAdapter(adapter);
        adapter.updateData(60);

        return recyclerView;
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        String text = "";
        switch (position) {
            case 0:
                text = "no pull_to_refresh, no drag_to_loadmore";
                break;
            case 1:
                text = "only pull_to_refresh, no drag_to_loadmore";
                break;
            case 2:
                text = "has pull_to_refresh, has drag_to_loadmore";
                break;
            case 3:
                text = "has pull_to_refresh and drag_to_loadmore, list too short";
                break;
        }
        Toast.makeText(AllConditionActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
