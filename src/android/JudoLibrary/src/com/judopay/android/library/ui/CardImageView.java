package com.judopay.android.library.ui;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.judopay.android.library.JudoSDKManager;
import com.judopay.android.library.utils.FakeR;

/**
 * Class: com.example.TestProject.CardNumberTextView
 * Project: JudoPayments
 * Created Date: 18/06/13 10:35
 *
 * @author <a href="mailto:developersupport@judopayments.com <developersupport@judopayments.com>">Matthew Rollings</a>
 *         Copyright (c) Alternative Payments Ltd 2014. All rights reserved.
 */

public class CardImageView extends FrameLayout {

    public CardImageView(Context context) {
        super(context);
    }

    public CardImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CardImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setCardType(int cardType){
        setCardImageWithoutAnimation(JudoSDKManager.getCardResourceID(getContext(), cardType, true));
    }


    public void setCardImageWithoutAnimation(int drawableId){
        // Add view cardImageLayout
        currentDrawableId=drawableId;
        final ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(drawableId);
        addView(imageView);
    }


    private int currentDrawableId=-1;

    public void setCardImage(int drawableId, boolean vertical){

        // If this is already our card image, don't reanimate
        if (drawableId==currentDrawableId) return;
        currentDrawableId=drawableId;

		Context c = getContext();
		int objAnim = vertical 
				? FakeR.getResourceID(c, "anim/flipping_out_vert") 
				: FakeR.getResourceID(c, "anim/flipping_out");
		int bakAnim = FakeR.getResourceID(c, "anim/fade_out");
        CompatibilityAnimation compatibilityAnimationOut = new CompatibilityAnimation(getContext(), objAnim, bakAnim);

        if (getChildCount()>0){
            final ImageView imageView = (ImageView) getChildAt(0);
            compatibilityAnimationOut.setDuration(350);
            compatibilityAnimationOut.setCompatabilityAnimationListener(new CompatibilityAnimation.CompatabilityAnimationListener() {
                @Override
                public void onAnimationStart() {

                }

                @Override
                public void onAnimationEnd() {
                    getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            removeView(imageView);
                        }
                    });
                }
            });
            compatibilityAnimationOut.startAnimation(imageView);
        }

        // Add view cardImageLayout
        final ImageView imageView2 = new ImageView(getContext());
        imageView2.setImageResource(drawableId);
        imageView2.setVisibility(View.INVISIBLE);
        addView(imageView2);

		objAnim = vertical
				? FakeR.getResourceID(c, "anim/flipping_in_vert")
				: FakeR.getResourceID(c, "anim/flipping_in");
		bakAnim = FakeR.getResourceID(c, "anim/fade_in");
		CompatibilityAnimation compatibilityAnimationIn = new CompatibilityAnimation(getContext(), objAnim, bakAnim);
        compatibilityAnimationIn.setDuration(350);
        compatibilityAnimationIn.setDelay(350);
        compatibilityAnimationIn.setCompatabilityAnimationListener(new CompatibilityAnimation.CompatabilityAnimationListener() {
            @Override
            public void onAnimationStart() {
                imageView2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd() {

            }
        });
        compatibilityAnimationIn.startAnimation(imageView2);
    }



}