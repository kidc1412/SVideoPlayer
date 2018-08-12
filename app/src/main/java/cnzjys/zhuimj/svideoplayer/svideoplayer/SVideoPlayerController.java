package cnzjys.zhuimj.svideoplayer.svideoplayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import cnzjys.zhuimj.svideoplayer.R;

public class SVideoPlayerController extends FrameLayout{


    private View mView;
    private Button pauseBtn;
    private ISVideoPlayer mISVideoPlayer;
    private SVideoPlayer mVideoPlayer;


    public SVideoPlayerController(@NonNull Context context) {
        super(context);
        initView();
    }



    private void initView(){
        mView = LayoutInflater.from(getContext()).inflate(R.layout.svideo_controller, this, true);
        pauseBtn = mView.findViewById(R.id.pause_button);
        pauseBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mVideoPlayer.pause();
            }
        });
    }


    public void setVideoPlayer(SVideoPlayer mVideoPlayer){
        this.mVideoPlayer = mVideoPlayer;
    }



}
