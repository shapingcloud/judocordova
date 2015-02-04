package com.judopay.android.library.ui;


import android.content.Context;
import android.util.AttributeSet;

import com.judopay.android.library.R;

/**
 * Class: com.example.TestProject.CardNumberTextView
 * Project: JudoPayments
 * Created Date: 18/06/13 10:35
 *
 * @author <a href="mailto:developersupport@judopayments.com <developersupport@judopayments.com>">Matthew Rollings</a>
 *         Copyright (c) Alternative Payments Ltd 2014. All rights reserved.
 */

public class CV2TextView extends BackgroundHintTextView {

    /**
     * Interface to pass on updates to the current activities
     */
    public interface BackKeyPressedListener
    {
        public void onBackKeyPressed();
    }

    BackKeyPressedListener backKeyPressedListener;

    public void setBackKeyPressedListener(BackKeyPressedListener backKeyPressedListener){
        this.backKeyPressedListener=backKeyPressedListener;
    }

    public CV2TextView(Context context) {
        super(context);
        init();
    }

    public CV2TextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CV2TextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

	@Override
	void validateInput(String input) throws Exception {
		//do nothing
	}

	@Override
    void backkeyPressed() {
        if (this.backKeyPressedListener!=null) backKeyPressedListener.onBackKeyPressed();
    }

    private void init() {
        // Set our hint text
        setHintText(getResources().getString(R.string.payment_hint_cv2));
        // Set additional chars to skip
        setInputFilter("");

    }

}