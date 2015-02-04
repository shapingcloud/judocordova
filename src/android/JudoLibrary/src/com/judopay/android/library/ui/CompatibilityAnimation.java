package com.judopay.android.library.ui;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Class: com.judopay.android.library.ui.CompatibilityAnimation
 * Project: JudoPayments
 * Created Date: 21/06/13 12:04
 *
 * @author <a href="mailto:developersupport@judopayments.com <developersupport@judopayments.com>">Matthew Rollings</a>
 *         Copyright (c) Alternative Payments Ltd 2014. All rights reserved.
 */

public class CompatibilityAnimation
{

    Object anim;

    /**
     * Interface to pass on updates to the current activities
     */
    public interface CompatabilityAnimationListener
    {
        public void onAnimationStart();
        public void onAnimationEnd();
    }

    CompatabilityAnimationListener compatabilityAnimationListener;

    public void setCompatabilityAnimationListener(CompatabilityAnimationListener compatabilityAnimationListener){
        this.compatabilityAnimationListener=compatabilityAnimationListener;
    }


    CompatibilityAnimation(Context c, int objectAnimation, int backupAnimation){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH){
            // Load object animation
            anim = AnimatorInflater.loadAnimator(c, objectAnimation);
            ((Animator)anim).addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    if (compatabilityAnimationListener!=null) compatabilityAnimationListener.onAnimationStart();
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    if (compatabilityAnimationListener!=null) compatabilityAnimationListener.onAnimationEnd();
                }

                @Override
                public void onAnimationCancel(Animator animator) {
                    //To change body of implemented methods use File | Settings | File Templates.
                }

                @Override
                public void onAnimationRepeat(Animator animator) {
                    //To change body of implemented methods use File | Settings | File Templates.
                }
            });
        }
        else{
           // Load backup animation
            anim = AnimationUtils.loadAnimation(c, backupAnimation);
            ((Animation)anim).setFillBefore(true);
            ((Animation)anim).setFillAfter(true);
            ((Animation)anim).setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    if (compatabilityAnimationListener != null) compatabilityAnimationListener.onAnimationStart();
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (compatabilityAnimationListener != null) compatabilityAnimationListener.onAnimationEnd();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    //To change body of implemented methods use File | Settings | File Templates.
                }
            });
        }

    }

    public void setDuration(int millis){
        if (anim instanceof Animation){
            ((Animation)anim).setDuration(millis);
        }
        else if (anim instanceof Animator){
            ((Animator)anim).setDuration(millis);
        }
        else{
            Log.e("CompatibilityAnimation", "Animation is neither of type Animator nor Animation");
        }
    }


    public void setDelay(int millis){
        if (anim instanceof Animation){
            ((Animation)anim).setStartOffset(millis);
        }
        else if (anim instanceof Animator){
            ((Animator)anim).setStartDelay(millis);
        }
        else{
            Log.e("CompatibilityAnimation", "Animation is neither of type Animator nor Animation");
        }
    }

    public void startAnimation(View v){
        if (anim instanceof Animation){
            v.startAnimation((Animation) anim);
        }
        else if (anim instanceof Animator){
            ((Animator) anim).setTarget(v);
            ((Animator) anim).start();
        }
        else{
            Log.e("CompatibilityAnimation", "Animation is neither of type Animator nor Animation");
        }
    }

}