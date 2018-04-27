package com.caohai.alipayhomepagedemo;

import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {
    private Toolbar mToolbar;
    private FrameLayout noFoldContent, foldContent;
    private LinearLayout noFoldMark, foldMark;
    private AppBarLayout appBar;
    private int mMaskColor;
    private NestedScrollView scrollView;
    private int offset;
    private int total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mToolbar.setTitle("");
        mToolbar.setSubtitle("");
        setSupportActionBar(mToolbar);
    }

    private void initView() {
        mMaskColor = getResources().getColor(R.color.colorPrimary);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        noFoldContent = (FrameLayout) findViewById(R.id.no_fold_content);
        noFoldMark = (LinearLayout) findViewById(R.id.no_fold_mark);
        foldContent = (FrameLayout) findViewById(R.id.fold_content);
        foldMark = (LinearLayout) findViewById(R.id.fold_mark);
        appBar = (AppBarLayout) findViewById(R.id.appbar);
        scrollView = (NestedScrollView) findViewById(R.id.scroll_view);
        appBar.addOnOffsetChangedListener(this);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    //appBar.scrollTo(0, 0);
                    if (offset > total / 2) {
                        Log.i("mytag", "false");
                        appBar.setExpanded(false, true);
                    } else {
                        Log.i("mytag", "true");
                        appBar.setExpanded(true, true);
                    }

                }
                return false;
            }
        });
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        offset = Math.abs(verticalOffset);
        total = appBarLayout.getTotalScrollRange();
        float ratio = offset / (float) total;
        Log.i("mytag", "ratio:" + ratio);
        if (ratio >= 0.5) {
            foldContent.setVisibility(View.VISIBLE);
            noFoldContent.setVisibility(View.GONE);
            float mRatio = (offset - total / 2) / (float) (total / 2);
            if (ratio == 0.5) {
                foldMark.setBackgroundColor(Color.argb((int) (255 * (1 - 0)), Color.red(mMaskColor), Color.green(mMaskColor), Color.blue(mMaskColor)));
            } else {
                foldMark.setBackgroundColor(Color.argb((int) (255 * (1 - mRatio)), Color.red(mMaskColor), Color.green(mMaskColor), Color.blue(mMaskColor)));
            }

        } else {
            foldContent.setVisibility(View.GONE);
            noFoldContent.setVisibility(View.VISIBLE);
            if (ratio == 0) {
                noFoldMark.setBackgroundColor(Color.argb((int) (255 * ratio), Color.red(mMaskColor), Color.green(mMaskColor), Color.blue(mMaskColor)));
            } else {
                noFoldMark.setBackgroundColor(Color.argb((int) (255 * (ratio + 0.5)), Color.red(mMaskColor), Color.green(mMaskColor), Color.blue(mMaskColor)));
            }

        }

        //noFoldMark.getBackground().setAlpha(255);
    }
}
