package cnzjys.zhuimj.svideoplayer.svideoplayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.net.Uri;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.IOException;

import cnzjys.zhuimj.svideoplayer.R;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class SVideoPlayer extends FrameLayout implements ISVideoPlayer, View.OnTouchListener{
    private Context mContext;
    private FrameLayout mContainer;
    private TextureView mTextureView;
    private SurfaceTexture mSurfaceTexture;
    private IjkMediaPlayer mIjkPlayer;
    private SVideoPlayerController mController;
    private long clickTime = 0;
    private static final int DOUBLE_CLICK_TIME = 3000;
    private long[] mHits = new long[3];
    private GestureDetector mGestureDetector;
    private static final String TAG = "FQFQFQ";

    public SVideoPlayer(@NonNull Context context) {
        super(context);
    }

    public SVideoPlayer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        initView();
    }


    private void initView(){
        mContainer = new FrameLayout(mContext);

        mContainer.setKeepScreenOn(true); //屏幕常亮
        mContainer.setBackgroundColor(Color.BLACK);
        LayoutParams lp = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );

        addView(mContainer, lp);

        mController = new SVideoPlayerController(mContext);
        mController.setVideoPlayer(this);

        mGestureDetector = new GestureDetector(getContext(),new MGestureListener());

        initTextureView();
        initIjkPlayer();
        setOnTouchListener(this);
    }

    private void initIjkPlayer(){
        mIjkPlayer = new IjkMediaPlayer();
        try {
            mIjkPlayer.setDataSource(mContext, Uri.parse(
                    "http://txmov2.a.yximgs.com/bs2/newWatermark/NzMwNzEzODIyNA_zh_4.mp4"), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mIjkPlayer.prepareAsync();
    }

    private void initTextureView(){
        mController.removeView(mTextureView);
        if (mTextureView == null) {
            mTextureView = new TextureView(mContext);
            mTextureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
                @Override
                public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                    if (mSurfaceTexture != null){
                        mTextureView.setSurfaceTexture(mSurfaceTexture);
                    }else{
                        mSurfaceTexture = surface;
                        mIjkPlayer.setSurface(new Surface(surface));
                    }
                }

                @Override
                public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

                }

                @Override
                public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                    return mSurfaceTexture == null;
                }

                @Override
                public void onSurfaceTextureUpdated(SurfaceTexture surface) {

                }
            });

            LayoutParams lp = new LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            );
            mContainer.addView(mTextureView, 0, lp);
        }

        if (mController != null){
            LayoutParams lp = new LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT

            );
            mContainer.addView(mController, lp);
        }


    }


    @Override
    public void start() {
        mIjkPlayer.start();
    }

    @Override
    public void pause() {
        mIjkPlayer.pause();
    }

    public void release(){
        mIjkPlayer.release();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }


    private class MGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            if (mIjkPlayer.isPlaying())
                mIjkPlayer.pause();
            else
                mIjkPlayer.start();
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Toast.makeText(getContext().getApplicationContext(), "点赞了", Toast.LENGTH_SHORT).show();
            return true;
        }

    }

}
