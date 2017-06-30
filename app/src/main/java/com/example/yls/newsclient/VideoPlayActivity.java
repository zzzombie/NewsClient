package com.example.yls.newsclient;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.VideoView;

/**
 * Created by yls on 2017/6/30.
 */

public class VideoPlayActivity extends BaseActivity{

    private VideoView videoView;
    private SeekBar sbCurrentPosition;
    private ProgressBar progressBar;
    @Override
    public int getLayoutRes() {
        return R.layout.activity_video_play;
    }

    @Override
    public void initData() {
        String videoUrl = getIntent().getStringExtra("video_url");

        // 设置视频播放路径
        videoView.setVideoPath(videoUrl);

        // 设置监听器，监听缓冲完成
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override   // 缓冲完成后回调
            public void onPrepared(MediaPlayer mp) {
                videoView.start();  // 缓冲完成后，开始播放视频
                progressBar.setVisibility(View.GONE);
            }
        });

        Uri videoUri = getIntent().getData();
        if (videoUri == null) { // 正常从当前应用的主界面跳转过来
             videoUrl = getIntent().getStringExtra("video_url");
            // 设置视频播放路径
            videoView.setVideoPath(videoUrl);
        } else {    // 从第三方应用跳转过来
            videoView.setVideoURI(videoUri);
        }
    }

    @Override
    public void initListener() {

// 设置监听器，监听缓冲完成
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override   // 缓冲完成后回调
            public void onPrepared(MediaPlayer mp) {
                videoView.start();  // 缓冲完成后，开始播放视频
                progressBar.setVisibility(View.GONE);

                sbCurrentPosition.setMax(videoView.getDuration());
                updateCurrentPosition();        // 更新当前播放进度
            }
        });

    }
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    updateSeekBarProgress();
                    break;
            }
        }
    };

    private void updateSeekBarProgress() {
        sbCurrentPosition.setProgress(videoView.getCurrentPosition());
        mHandler.sendEmptyMessageDelayed(0, 300);
    }

    private void updateCurrentPosition() {
        sbCurrentPosition.setProgress(videoView.getCurrentPosition());
        mHandler.sendEmptyMessageDelayed(0, 300);
    }


    @Override
    public void initView() {
        videoView = (VideoView) findViewById(R.id.video_view);
        progressBar  = (ProgressBar ) findViewById(R.id.pb_01);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 移除所有的消息，退出Handler死循环
        mHandler.removeCallbacksAndMessages(null);
    }
}
