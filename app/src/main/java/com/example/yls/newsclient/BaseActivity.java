package com.example.yls.newsclient;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by yls on 2017/6/27.
 */

public abstract class BaseActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());

        initView();
        initListener();
        initData();
    }
    /** 返回一个Fragment要显示的布局界面 */
    public abstract int getLayoutRes();
    /** 初始化数据 */
    public abstract void initData() ;
    /** 设置控件的监听器 */
    public abstract void initListener() ;
    /** 查找布局中的子控件 */
    public abstract void initView() ;

    private Toast mToast;

    public void showToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(this, "xxx", Toast.LENGTH_SHORT);
        }
        mToast.setText(msg);
        mToast.show();
    }
}
