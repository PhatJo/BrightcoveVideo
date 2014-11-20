package com.example.test.video;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.brightcove.player.event.Event;
import com.brightcove.player.event.EventListener;
import com.brightcove.player.event.EventType;
import com.brightcove.player.media.Catalog;
import com.brightcove.player.media.VideoListener;
import com.brightcove.player.model.Video;
import com.brightcove.player.view.BrightcoveVideoView;

public class VideoActivity extends Activity {
    BrightcoveVideoView bcVideoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_activity);

        bcVideoView = (BrightcoveVideoView) findViewById(R.id.bc_video_view);
        final Catalog catalog = new Catalog("09MQKMo8MdLanB7dqUAJWoeMXY2FDrbYhfnEpx91paZbR7-GxK2nBQ..");

        catalog.findVideoByID("3838977250001", new VideoListener() {
            @Override
            public void onVideo(Video video) {
                bcVideoView.add(video);
                bcVideoView.start();
            }

            @Override
            public void onError(String error) {
                throw new RuntimeException(error);
            }
        });

        bcVideoView.getEventEmitter().on(EventType.DID_PLAY, new EventListener() {
            @Override
            public void processEvent(Event event) {
                setResult(Activity.RESULT_OK);
                bcVideoView.stopPlayback();
                finish();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            Log.d("VideoActivity", "onDestroy");
            bcVideoView.getEventEmitter().off();
            bcVideoView = null;
        }
    }

}