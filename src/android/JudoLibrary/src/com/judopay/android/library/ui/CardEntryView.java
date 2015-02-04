package com.judopay.android.library.ui;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.judopay.android.api.ValidationHelper;
import com.judopay.android.api.data.CardBase;
import com.judopay.android.api.exception.InvalidDataException;
import com.judopay.android.library.JudoSDKManager;
import com.judopay.android.library.R;
import com.judopay.android.library.utils.FakeR;
import com.judopay.android.library.utils.UiUtils;

/**
 * Class: com.example.TestProject.CardNumberTextView
 * Project: JudoPayments
 * Created Date: 18/06/13 10:35
 *
 * @author <a href="mailto:developersupport@judopayments.com <developersupport@judopayments.com>">Matthew Rollings</a>
 *         Copyright (c) Alternative Payments Ltd 2014. All rights reserved.
 */
public class CardEntryView extends LinearLayout
{

	private CardNumberTextView cardNumberTextView;
	private CardExpiryCV2TextView cardExpiryCV2TextView;
	private CardImageView cardImageView;
    private TextView hintTextView;
    private int currentCard = CardBase.CardType.UNKNOWN;
    EntryCompleteListener entryCompleteListener;

    public void reset() {
		cardNumberTextView.getEditText().setText("");
		cardExpiryCV2TextView.getEditText().setText("");
        currentCard = CardBase.CardType.UNKNOWN;
		cardImageView.setCardImage(JudoSDKManager.getCardResourceID(getContext(), currentCard, true), false);
        if (hintTextView!=null) hintTextView.setText(R.string.enter_card_no);

		setStage(STAGE_CC_NO);

		//show keyboard
		InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(cardNumberTextView.getWindowToken(), 0);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
	}

    public void setHintTextView(TextView hintTextView){
        this.hintTextView=hintTextView;
    }

	/**
	 * Interface to pass on updates to the current activities
	 */
	public interface EntryCompleteListener
	{
		public void onCreditCardEntered(String cardNo);

		public void onExpiryAndCV2Entered(String cardNo, String cv2);

	}

	public void setEntryCompleteListener(EntryCompleteListener entryCompleteListener) {
		this.entryCompleteListener = entryCompleteListener;
	}

	public EditText getCardNoEditText() {
		return cardNumberTextView.getEditText();
	}

	public EditText getCV2EditText() {
		return cardExpiryCV2TextView.getEditText();
	}

	/**
	 * Constructors
	 */
	public CardEntryView(Context context) {
		super(context);
		init();
	}

	public CardEntryView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}


    public String getCardNumber() throws InvalidDataException {
        String cardNo = cardNumberTextView.getText().toString().replace(" ", "");
        if (!ValidationHelper.canProcess(cardNo)) throw new InvalidDataException("Credit card No");
        else return cardNo;
    }

    public String getCardCV2() throws InvalidDataException {
        return cardExpiryCV2TextView.getCardCV2();
    }

    public String getCardExpiry() throws InvalidDataException {
        return cardExpiryCV2TextView.getCardExpiry();
    }



	private void init() {
		super.removeAllViews();

		setOrientation(VERTICAL);

		Context c = getContext();
		cardImageView = new CardImageView(c);
        cardImageView.setCardImageWithoutAnimation(JudoSDKManager.getCardResourceID(getContext(), currentCard, true));

        LinearLayout layoutHolder = new LinearLayout(getContext());
		layoutHolder.setOrientation(HORIZONTAL);
		layoutHolder.setGravity(Gravity.CENTER_VERTICAL);

		LinearLayout cardFrame = new LinearLayout(getContext());
		cardFrame.setGravity(Gravity.CENTER_VERTICAL);
        //added  padding
		cardFrame.setPadding(UiUtils.toPixels(getContext(),8), 0, 0, 0);
		cardFrame.addView(cardImageView);

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;

        cardNumberTextView = new CardNumberTextView(getContext());
        cardNumberTextView.setLayoutParams(params);

        cardExpiryCV2TextView = new CardExpiryCV2TextView(getContext(), null, FakeR.getResourceID(c, "style/judo_payments_CardText"));
		cardExpiryCV2TextView.setLayoutParams(params);
		cardExpiryCV2TextView.setVisibility(View.INVISIBLE);

        layoutHolder.addView(cardFrame, LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutHolder.addView(cardNumberTextView);
        layoutHolder.addView(cardExpiryCV2TextView);

        addView(layoutHolder);

		cardNumberTextView.setEntryCompleteListener(new BackgroundHintTextView.EntryCompleteListener()
		{
            int lastPos = 0;
			@Override
			public void onEntryComplete(String cardNo) {
				if(entryCompleteListener != null) entryCompleteListener.onCreditCardEntered(cardNo);
				setStage(STAGE_CC_EXP);
			}

			@Override
			public void onProgress(int pos) {
				if(pos == 0) return;

				String cardNo = cardNumberTextView.getText().toString();
                currentCard = ValidationHelper.getCardType(cardNo);
				cardImageView.setCardImage(JudoSDKManager.getCardResourceID(getContext(), currentCard, true), false);

                if (currentCard==CardBase.CardType.AMEX){
                    cardNumberTextView.showInvalid("AmEx not accepted");
                }
                else if (currentCard==CardBase.CardType.MAESTRO){
                    cardNumberTextView.showInvalid("Maestro not accepted");
                }

                lastPos =pos;
			}
		});

		cardExpiryCV2TextView.setEntryCompleteListener(new BackgroundHintTextView.EntryCompleteListener()
		{
			@Override
			public void onEntryComplete(String expiryAndCV2) {
				Log.e(CardEntryView.this.toString(), "expiryAndCV2: " + expiryAndCV2);
				expiryAndCV2 = expiryAndCV2.replaceFirst(" ", "");
				Log.e(CardEntryView.this.toString(), "expiryAndCV2: " + expiryAndCV2);
				String temp[] = expiryAndCV2.split(" ");
				if(temp.length < 2){
					Log.e(CardEntryView.this.toString(), "Error: Invalid expiry and/or cv2");
					return;
				}

				String expiry = temp[0];
				String cv2 = temp[1];
				if(entryCompleteListener != null){
					entryCompleteListener.onExpiryAndCV2Entered(expiry, cv2);
				}
			}

			@Override
			public void onProgress(int pos) {
				cardExpiryCV2TextView.validatePartialInput();
				// If we've entered
				if(pos > 5){
					cardImageView.setCardImage(JudoSDKManager.getCardResourceID(getContext(), currentCard, false), true);
                    if (hintTextView!=null)hintTextView.setText(R.string.enter_card_cv2);

                    //validate the date
                    if (pos==6) {
                        try {
                            cardExpiryCV2TextView.validateExpiryDate(getCV2EditText().getText().toString());
                        } catch (Exception e) {
                            cardExpiryCV2TextView.showInvalid(e.getMessage());
                        }
                    }
				}
				else {
					cardImageView.setCardImage(JudoSDKManager.getCardResourceID(getContext(), currentCard, true), true);
                    if (hintTextView!=null)hintTextView.setText(R.string.enter_card_expiry);
				}
			}
		});

		cardExpiryCV2TextView.setBackKeyPressedListener(new CardExpiryCV2TextView.BackKeyPressedListener()
		{
			@Override
			public void onBackKeyPressed() {
				if(cardExpiryCV2TextView.getText().toString().length() == 1){
					cardExpiryCV2TextView.setText("");
					setStage(STAGE_CC_NO);
				}
			}
		});

        //TODO: add key listener for backspace key when on first char of expiry date


	}

	private static final int STAGE_CC_NO = 0;
	private static final int STAGE_CC_EXP = 1;
	private static final int STAGE_CC_CV2 = 2;

	private int currentStage = STAGE_CC_NO;

	public void setStage(int stage) {

		boolean animate = stage != currentStage;


		if(stage == STAGE_CC_EXP || stage == STAGE_CC_CV2){
            cardNumberTextView.setEnabled(false);
			cardExpiryCV2TextView.setEnabled(true);
			cardExpiryCV2TextView.setVisibility(View.VISIBLE);
			// This will last 4 digits of cc# visible on screen
			cardNumberTextView.setVisibility(View.GONE);
			cardExpiryCV2TextView.setText(" ");
            String cardNo = cardNumberTextView.getEditText().getText().toString();
            //cardExpiryCV2TextView.setLast4NumbersOfCard(cardNo.substring(cardNo.length()-4));

			cardExpiryCV2TextView.requestFocus();


            if (hintTextView!=null)hintTextView.setText(R.string.enter_card_expiry);

			if(animate){
				animateInRight(cardExpiryCV2TextView);
				animateOutLeft(cardNumberTextView);
			}

		}
		else {
			cardNumberTextView.setEnabled(true);
			cardExpiryCV2TextView.setEnabled(false);
			cardExpiryCV2TextView.setVisibility(View.GONE);
			cardNumberTextView.setVisibility(View.VISIBLE);
			cardNumberTextView.requestFocus();
			if(animate){
				animateInLeft(cardNumberTextView);
				animateOutRight(cardExpiryCV2TextView);
			}
            if (hintTextView!=null)hintTextView.setText(R.string.enter_card_no);
		}
		currentStage = stage;
	}

	private void animateOutLeft(View view) {
		animateAlphaTranslate(view, 1.0f, 0.0f, 0.0f, -1.0f, false);
	}

	private void animateOutRight(View view) {
		animateAlphaTranslate(view, 1.0f, 0.0f, 0.0f, 1.0f, false);
	}

	private void animateInRight(View view) {
		animateAlphaTranslate(view, 0.0f, 1.0f, 1.0f, 0.0f, true);
	}

	private void animateInLeft(View view) {
		animateAlphaTranslate(view, 0.0f, 1.0f, -1.0f, 0.0f, true);
	}

	private void animateAlphaTranslate(final View view, float alphaFrom, float alphaTo, float xFrom, float xTo,
									   boolean requestFocus) {
		AnimationSet animationSet = new AnimationSet(true);

		AlphaAnimation fade = new AlphaAnimation(alphaFrom, alphaTo);
		fade.setDuration(350);

		TranslateAnimation slide = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, xFrom,
				Animation.RELATIVE_TO_SELF, xTo,
				Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f
		);

		slide.setDuration(350);

		animationSet.addAnimation(fade);
		animationSet.addAnimation(slide);
		view.startAnimation(animationSet);
		if(requestFocus){
			animationSet.setAnimationListener(new Animation.AnimationListener()
			{
				@Override
				public void onAnimationStart(Animation animation) {

				}

				@Override
				public void onAnimationEnd(Animation animation) {
					view.requestFocus();
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
				}
			});
		}

	}


}