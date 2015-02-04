package com.judopay.android.library.ui;


import android.content.Context;
import android.graphics.Typeface;
import android.text.*;
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

public class PriceTextView extends NoCursorMovingEditText{

    public PriceTextView(Context context) {
        super(context);
        init();
    }

    public PriceTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PriceTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private String currencySymbol = "\u00a3";

    public void setCurrencySymbol(String currency){
        currencySymbol=currency;
    }


    @Override
    void onBackKeyPressed() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private void init() {
        setHint(getResources().getString(R.string.payment_hint_enter_amt_with_currency, currencySymbol));
        //setCursorVisible(false);
        setInputType(InputType.TYPE_CLASS_NUMBER);
        setTypeface(Typeface.DEFAULT);

        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned spanned, int i3, int i4) {
                for (int i = start; i < end; i++) {
//                    if (!Character.isDigit(source.charAt(i))) {
//                        return "";
//                    }
                }
                return null;
            }
        };
        setFilters(new InputFilter[] {new InputFilter.LengthFilter(10), filter});


        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void afterTextChanged(Editable editable) {

                int l = editable.toString().length();

                if (editable.toString().equals("")) return;

                if (editable.toString().indexOf(".")!=l-3){
                    // Convert to number
                    String numberStr = editable.toString().replaceAll("[^0-9]", "");

                    int pence = (int) (Long.parseLong(numberStr)%100);
                    long pounds = (Long.parseLong(numberStr)-pence)/100;

                    setTypeface(Typeface.MONOSPACE);

                    String resultStr = currencySymbol+pounds+"."+String.format("%02d",pence);
                    PriceTextView.this.setText(resultStr);

                    return;
                }
            }
        });

    }

}