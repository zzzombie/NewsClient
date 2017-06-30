package com.example.yls.newsclient;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import fragment.MainFragment01;
import fragment.MainFragment02;
import fragment.MainFragment03;
import fragment.MainFragment04;
import fragment.MainFragment05;

public class MainActivity extends BaseActivity {
    private RadioGroup radioGroup;
    private ViewPager viewPager;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        radioGroup = (RadioGroup) findViewById(R.id.rg_01);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        initViewPager();
        initToolbar();
//        initNavigationView();
        initActionBarDrawerToggle();
    }

//    private void initNavigationView() {
//        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
//        // 侧滑菜单点击监听
//        navigationView.setNavigationItemSelectedListener(
//                new NavigationView.OnNavigationItemSelectedListener() {
//
//                    @Override
//                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                        // 点击侧滑菜单item时，通过DrawerLayout关闭侧滑菜单
//                        showToast("" + item.getTitle());
//                        drawerLayout.closeDrawers();
//
//                        return false;
//                    }
//                });
//    }

    private void initActionBarDrawerToggle() {
        toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, 0, 0);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();     // 同步drawerLayout和toolbar的状态
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("广交院实训");

//        toolbar.setSubtitle("这是子标题");
        toolbar.setTitleTextColor(Color.RED);
//        toolbar.setSubtitleTextColor(Color.YELLOW);

        // 导航栏图标显示
        toolbar.setNavigationIcon(R.drawable.biz_video_play);

        // 点击toolbar导航栏左上角的图标后，退出当前界面
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    // Toolbar标题栏右侧点击菜单:创建
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // res/memu/main_option.xml
        // 定义可点击的菜单项
        getMenuInflater().inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    // Toolbar标题栏右侧点击菜单：点击
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_01) {
            showToast("menu_02");
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        final List<Fragment> fragments  = new ArrayList<>();
        fragments.add(new MainFragment01());
        fragments.add(new MainFragment02());
        fragments.add(new MainFragment03());
        fragments.add(new MainFragment04());
        fragments.add(new MainFragment05());

        viewPager.setAdapter(new FragmentPagerAdapter(
                getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
    }

    @Override
    public void initListener() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override   // 点击单选时，调用此方法
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch(checkedId) {
                    case R.id.rb_01:    // 点击单选时，切换ViewPager子界面
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.rb_02:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.rb_03:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.rb_04:
                        viewPager.setCurrentItem(3);
                        break;
                    case R.id.rb_05:
                        viewPager.setCurrentItem(4);
                        break;
                }
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch(position) {  // 当ViewPager子界面切换后，选中某个RadioButton
                    case 0:
                        radioGroup.check(R.id.rb_01);
                        break;
                    case 1:
                        radioGroup.check(R.id.rb_02);
                        break;
                    case 2:
                        radioGroup.check(R.id.rb_03);
                        break;
                    case 3:
                        radioGroup.check(R.id.rb_04);
                        break;
                    case 4:
                        radioGroup.check(R.id.rb_05);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void initData() {
    }


}
