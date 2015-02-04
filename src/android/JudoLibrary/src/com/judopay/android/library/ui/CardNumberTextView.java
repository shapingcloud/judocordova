package com.judopay.android.library.ui;


import android.content.Context;
import android.util.AttributeSet;
import com.judopay.android.api.ValidationHelper;
import com.judopay.android.library.R;

/**
 * Class: com.example.TestProject.CardNumberTextView
 * Project: JudoPayments
 * Created Date: 18/06/13 10:35
 *
 * @author <a href="mailto:developersupport@judopayments.com <developersupport@judopayments.com>">Matthew Rollings</a>
 *         Copyright (c) Alternative Payments Ltd 2014. All rights reserved.
 */

public class CardNumberTextView extends BackgroundHintTextView {



    public CardNumberTextView(Context context) {
        super(context);
        init();
    }

    public CardNumberTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CardNumberTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        // Set our hint text
        setHintText("0000 0000 0000 0000");
        setErrorText(getResources().getString(R.string.msg_check_number));
        // Set additional chars to skip
        setInputFilter("");
        setText(" ");

        //set courier font
        //Typeface type = Typeface.createFromAsset(getContext().getAssets(),FONT_PATH);
        //getEditText().setTypeface(type);
        //getHintText().setTypeface(type);

        getEditText().setSingleLine(true);
    }

    @Override
    void validateInput(String input) throws Exception {
        // We have finished entering the cc# let's validate it
        input = input.replace(" ","");
        if(!ValidationHelper.checkLuhn(input))
			throw new Exception("Card number is invalid");
    }

    @Override
    void backkeyPressed() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

}