package fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.yls.newsclient.NewsDetailActivity;
import com.example.yls.newsclient.R;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.Serializable;
import java.util.List;

import adapter.NewsAdapter;
import base.URLManager;
import bean.NewsEntity;

/**
 * Created by yls on 2017/6/28.
 */

public class NewsFragment extends BaseFragment {
    /**
     * 新闻类别id
     */
    private String channelId;
    private ListView listView;

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_news;
    }

    @Override
    public void initView() {
//        TextView textView = (TextView) mRootView.findViewById(R.id.tv_01);
         listView = (ListView) mRootView.findViewById(R.id.list_view);
//        textView.setText(channelId);    // 显示新闻类别id，以作区分

    }

    @Override
    public void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 用户点击的新闻
                NewsEntity.ResultBean newsBean = (NewsEntity.ResultBean)
                        parent.getItemAtPosition(position);

                Intent intent = new Intent(mActivity, NewsDetailActivity.class);
                intent.putExtra("news", (Serializable) newsBean);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {
        getDataFromServer();
    }

    public void getDataFromServer() {
        String url = URLManager.getUrl(channelId);

        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String json = responseInfo.result;
                System.out.println("----服务器返回的json数据:" + json);

                json = json.replace(channelId, "result");
                Gson gson = new Gson();
                NewsEntity newsDatas = gson.fromJson(json, NewsEntity.class);
                System.out.println("----解析json:" + newsDatas.getResult().size());

                // 显示数据到列表中
                showDatas(newsDatas);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                error.printStackTrace();
            }
        });

    }

    private void showDatas(NewsEntity newsDatas) {
        if (newsDatas == null
                || newsDatas.getResult() == null
                || newsDatas.getResult().size() == 0) {
            System.out.println("----没有获取到服务器的新闻数据");
            return;
        }

        // （1）显示轮播图
        List<NewsEntity.ResultBean.AdsBean> ads
                = newsDatas.getResult().get(0).getAds();

        // 有轮播图广告
        if (ads != null && ads.size() > 0) {
            View headerView = View.inflate(mActivity, R.layout.list_header, null);
            SliderLayout sliderLayout = (SliderLayout)
                    headerView.findViewById(R.id.slider_layout);

            for (int i = 0; i < ads.size(); i++) {
                // 一则广告数据
                NewsEntity.ResultBean.AdsBean adBean = ads.get(i);

                TextSliderView sliderView = new TextSliderView(mActivity);
                sliderView.description(adBean.getTitle()).image(adBean.getImgsrc());
                // 添加子界面
                sliderLayout.addSlider(sliderView);
                // 设置点击事件
                sliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                    @Override
                    public void onSliderClick(BaseSliderView slider) {
                        showToast(slider.getDescription());
                    }
                });
            }
            // 添加列表头部布局
            listView.addHeaderView(headerView);
        }


        // （2）显示新闻列表
        NewsAdapter newsAdapter = new NewsAdapter(
                mActivity, newsDatas.getResult());
        listView.setAdapter(newsAdapter);
    }
}
