package cnzjys.zhuimj.svideoplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cnzjys.zhuimj.svideoplayer.svideoplayer.SVideoPlayer;

public class MainActivity extends AppCompatActivity {

    private SVideoPlayer videoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoPlayer = findViewById(R.id.video_player);
    }


    @Override
    protected void onPause() {
        super.onPause();
        videoPlayer.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoPlayer.release();
    }
}
