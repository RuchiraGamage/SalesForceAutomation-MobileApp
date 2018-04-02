package com.example.salinda.salseforseautomation.Other;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

public class Animation {

    public void onClickAnimation(final View v){
        ValueAnimator animator = ValueAnimator.ofInt(0,255);
        animator.setDuration(30000);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                v.setAlpha((Integer) valueAnimator.getAnimatedValue());
            }
        });
        animator.start();

    }

    public void onLongClickAnimation(final View v){
        ValueAnimator animator = ValueAnimator.ofInt(0,255);
        animator.setDuration(50000);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                v.setAlpha((Integer) valueAnimator.getAnimatedValue());
            }
        });
        animator.start();

    }
}
