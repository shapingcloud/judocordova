package com.judopay.android.library.ui;


import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;

import com.judopay.android.api.exception.InvalidDataException;
import com.judopay.android.library.R;

import java.util.Calendar;

/**
 * Class: com.example.TestProject.CardNumberTextView
 * Project: JudoPayments
 * Created Date: 18/06/13 10:35
 *
 * @author <a href="mailto:developersupport@judopayments.com <developersupport@judopayments.com>">Matthew Rollings</a>
 *         Copyright (c) Alternative Payments Ltd 2014. All rights reserved.
 */
public class CardExpiryCV2TextView extends BackgroundHintTextView {

    private String last4NumbersOfCard;

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

    public CardExpiryCV2TextView(Context context) {
        super(context);
        init();
    }

    public CardExpiryCV2TextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CardExpiryCV2TextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    void validateExpiryDate(String expiry) throws Exception {
        // Remove leading space and card no
        expiry = stripLast4NumbersOfCard(expiry);
        expiry = expiry.replaceFirst(" ", "");

        if(!TextUtils.isEmpty(expiry)) {
            String month = expiry.substring(0, 2);

            //validate month
            try{
            if (Integer.valueOf(month) > 12) {
                setErrorText("Invalid Month");
                throw new Exception(getResources().getString(R.string.msg_check_exp_date));
            }}catch (NumberFormatException e){
                setErrorText("Invalid Month");
                throw e;
            }

            //validate not in the past.
            String year = expiry.substring(3, 5);
            Calendar todayCal = Calendar.getInstance();
            todayCal.setTimeInMillis(System.currentTimeMillis());

            Calendar expDateCal = Calendar.getInstance();
            expDateCal.setTimeInMillis(System.currentTimeMillis());
            expDateCal.set(Calendar.MONTH, Integer.valueOf(month));
            //plus 2000 as we only get a 2 digit year
            expDateCal.set(Calendar.YEAR, Integer.valueOf(year)+2000);

            if (expDateCal.before(todayCal)){
                throw new Exception(getResources().getString(R.string.msg_check_exp_date));
            }

            //validate date not more than 10 years in future
            Calendar future = Calendar.getInstance();
            future.setTimeInMillis(System.currentTimeMillis());
            future.add(Calendar.YEAR, 10);

            if (expDateCal.after(future)){
                throw new Exception(getResources().getString(R.string.msg_check_exp_date));
            }
        }
    }

    public void setLast4NumbersOfCard(String last4NumbersOfCard){
        if(!TextUtils.isEmpty(last4NumbersOfCard) && last4NumbersOfCard.length()==4){
            this.last4NumbersOfCard = last4NumbersOfCard;
            String input = getEditText().getText().toString();
            getEditText().setText(" " + last4NumbersOfCard + input);
            getEditText().setSelection(5);
        }else{
            //error
        }
    }

    private String stripLast4NumbersOfCard(String input){
        if(!TextUtils.isEmpty(last4NumbersOfCard)){
            return input.substring(5);
        }else{
            return input;
        }
    }


    /**
     * Parses the input text and returns the part that's the CV2
     * @return 3 digit CV2 number
     * @throws InvalidDataException
     */
    public String getCardCV2() throws InvalidDataException {
        String cardnumbnerStripped = stripLast4NumbersOfCard( getEditText().getText().toString());
        String expiryAndCV2 = cardnumbnerStripped.replaceFirst(" ", "");
        Log.e(CardExpiryCV2TextView.this.toString(), "expiryAndCV2: " + expiryAndCV2);
        String temp[] = expiryAndCV2.split(" ");
        if(temp.length < 2){
            Log.e(CardExpiryCV2TextView.this.toString(), "Error: Invalid expiry and/or cv2");
            throw new InvalidDataException("Expiry date and/or cv2");
        }

        String expiry = temp[0];
        String cv2 = temp[1];

        return cv2;
    }

    /**
     * Parses the input text and returns the part that's the Expiry date
     * @return expiry date MM/YY
     * @throws InvalidDataException
     */
    public String getCardExpiry() throws InvalidDataException {
        String cardnumbnerStripped = stripLast4NumbersOfCard( getEditText().getText().toString());
        String expiryAndCV2 =cardnumbnerStripped.replaceFirst(" ", "");
        Log.e(CardExpiryCV2TextView.this.toString(), "expiryAndCV2: " + expiryAndCV2);
        String temp[] = expiryAndCV2.split(" ");
        if(temp.length < 2){
            Log.e(CardExpiryCV2TextView.this.toString(), "Error: Invalid expiry and/or cv2");
            throw new InvalidDataException("Expiry date and/or cv2");
        }

        String expiry = temp[0];
        String cv2 = temp[1];

        return expiry;
    }

	@Override
	void validateInput(String input) throws Exception {
        // Remove leading space
        input = stripLast4NumbersOfCard(input);
        input = input.replaceFirst(" ", "");

        String temp[] = input.split(" ");
        if (temp.length<2){
            setErrorText("Invalid Expiry / CV2");
			throw new Exception("Invalid expiry and/or cv2");
        }

        String expiry = temp[0];

        validateExpiryDate(expiry);
    }

    @Override
    void backkeyPressed() {
        if (this.backKeyPressedListener!=null) backKeyPressedListener.onBackKeyPressed();
    }

    private void init() {
        // Set our hint text
        setHintText(" MM/YY CV2");
        // Set error text
        setErrorText("Invalid CV2");
        // Set additional chars to skip
        setInputFilter("/");


    }

    public void validatePartialInput() {
        String input = getText().toString();

        if (input.length()>=2){
            if (!input.substring(1,2).matches("[0-1]")){
                setText("");
            }
        }
        if (input.length()>=3){
			int value = Integer.valueOf(input.substring(1,3));
            if (value > 12){
                setText(input.substring(0,2));
            }
			else if(value == 0){
				//setErrorText("Invalid Date");
				setText("0");
			}
        }
    }

}