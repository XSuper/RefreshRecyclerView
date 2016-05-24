package com.hileone.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hileone.demo.new_refresh.NewRefreshActivity;
import com.hileone.demo.all_condition.AllConditionActivity;
import com.hileone.demo.zrc_refresh.ZrcRefreshActivity;

/**
 * The creator is Leone && E-mail: butleone@163.com
 *
 * @author Leone
 * @date 5/12/16
 * @description Edit it! Change it! Beat it! Whatever, just do it!
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.one).setOnClickListener(this);
        findViewById(R.id.two).setOnClickListener(this);
        findViewById(R.id.three).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.one:
                startActivity(new Intent(MainActivity.this, AllConditionActivity.class));
                break;
            case R.id.two:
                startActivity(new Intent(MainActivity.this, ZrcRefreshActivity.class));
                break;
            case R.id.three:
                startActivity(new Intent(MainActivity.this, NewRefreshActivity.class));
                break;
        }
    }
}
