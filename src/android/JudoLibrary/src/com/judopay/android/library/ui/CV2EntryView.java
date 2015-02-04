package com.judopay.android.library.ui;


import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.judopay.android.api.data.CardBase;
import com.judopay.android.api.data.CardToken;
import com.judopay.android.library.JudoSDKManager;
import com.judopay.android.library.R;
import com.judopay.android.library.utils.FakeR;
import com.judopay.android.library.utils.Typefaces;
import com.judopay.android.library.utils.UiUtils;

/**
 * Class: com.example.TestProject.CardNumberTextView
 * Project: JudoPayments
 * Created Date: 18/06/13 10:35
 *
 * @author <a href="mailto:developersupport@judopayments.com <developersupport@judopayments.com>">Matthew Rollings</a>
 *         Copyright (c) Alternative Payments Ltd 2014. All rights reserved.
 */

public class CV2EntryView extends LinearLayout
{

	private int currentCard = CardBase.CardType.UNKNOWN;

	private CV2TextView cv2TextView;
	private FrameLayout cardImageLayout;
	private EditText last4CCNosTextView;

	/**
	 * Interface to pass on updates to the current activities
	 */
	public interface EntryCompleteListener
	{
		public void onCreditCardEntered(String cardNo);

		public void onExpiryAndCV2Entered(String cardNo, String cv2);
	}

	EntryCompleteListener entryCompleteListener;

	public void setEntryCompleteListener(EntryCompleteListener entryCompleteListener) {
		this.entryCompleteListener = entryCompleteListener;
	}

	public EditText getCv2EditText() {
		return cv2TextView.getEditText();
	}

    public String getCv2() {
        return cv2TextView.getEditText().getText().toString();
    }

	public Editable getText(){
		return getCv2EditText().getText();
	}

	/**
	 * Constructors
	 */
	public CV2EntryView(Context context) {
		super(context);
		init();
	}

	public CV2EntryView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}


	private void init() {
		super.removeAllViews();

		setOrientation(HORIZONTAL);

		cardImageLayout = new FrameLayout(getContext());

        LayoutParams cardImageLayoutParams  = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        cardImageLayoutParams.gravity = Gravity.CENTER_VERTICAL;
		cardImageLayout.setLayoutParams(cardImageLayoutParams);

        cardImageLayout.setPadding(0,0, UiUtils.toPixels(getContext(), 8),0);

		setCardImageWithoutAnimation(FakeR.getResourceID(getContext(), "drawable/ic_card_cv2"));

		cv2TextView = new CV2TextView(getContext());

		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.weight = 1;
		params.gravity = Gravity.CENTER;

		cv2TextView.setLayoutParams(params);

		cv2TextView.setEntryCompleteListener(new BackgroundHintTextView.EntryCompleteListener()
		{
			@Override
			public void onEntryComplete(String cardNo) {
				if(entryCompleteListener != null) entryCompleteListener.onCreditCardEntered(cardNo);
			}

			@Override
			public void onProgress(int pos) {

			}
		});

		last4CCNosTextView = new EditText(getContext());
		last4CCNosTextView.setText("0000");

        final Typeface type = Typefaces.loadTypefaceFromRaw(getContext(), R.raw.courier);
        last4CCNosTextView.setTypeface(type);
		last4CCNosTextView.setFocusable(false);
		last4CCNosTextView.setEnabled(false);
		last4CCNosTextView.setBackgroundDrawable(null);

        addView(cardImageLayout);
		addView(last4CCNosTextView);
        addView(cv2TextView);

	}

    public void setCardDetails(CardToken cardToken){
        setLast4CCNosText(cardToken.getCardLastFour());
        setCardType(cardToken.getCardType());
    }

	public void setLast4CCNosText(String text) {
		last4CCNosTextView.setText(getResources().getString(R.string.card_no_obscured, text));
	}


	public void setCardType(int cardType){
        setCardImageWithoutAnimation(JudoSDKManager.getCardResourceID(getContext(), cardType, false));
	}

	public void setCardImageWithoutAnimation(int drawableId) {
		// Add view cardImageLayout
		final ImageView imageView = new ImageView(getContext());
		imageView.setImageResource(drawableId);
        cardImageLayout.removeAllViews();
		cardImageLayout.addView(imageView);
	}


	public void setCardImage(int drawableId, boolean vertical) {

		Context c = getContext();
		int objAnim = vertical
				? FakeR.getResourceID(c, "anim/flipping_out_vert")
				: FakeR.getResourceID(c, "anim/flipping_out");
		int bakAnim = FakeR.getResourceID(c, "anim/fade_out");
		CompatibilityAnimation compatibilityAnimationOut = new CompatibilityAnimation(getContext(), objAnim, bakAnim);

		if(cardImageLayout.getChildCount() > 0){
			final ImageView imageView = (ImageView) cardImageLayout.getChildAt(0);
			compatibilityAnimationOut.setDuration(350);
			compatibilityAnimationOut.setCompatabilityAnimationListener(
					new CompatibilityAnimation.CompatabilityAnimationListener()
					{
						@Override
						public void onAnimationStart() {

						}

						@Override
						public void onAnimationEnd() {
							getHandler().post(new Runnable()
							{
								@Override
								public void run() {
									cardImageLayout.removeView(imageView);
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
		cardImageLayout.addView(imageView2);

		objAnim = vertical
				? FakeR.getResourceID(c, "anim/flipping_in_vert")
				: FakeR.getResourceID(c, "anim/flipping_in");
		bakAnim = FakeR.getResourceID(c, "anim/fade_in");
		CompatibilityAnimation compatibilityAnimationIn = new CompatibilityAnimation(getContext(), objAnim, bakAnim);
		compatibilityAnimationIn.setDuration(350);
		compatibilityAnimationIn.setDelay(350);
		compatibilityAnimationIn.setCompatabilityAnimationListener(
				new CompatibilityAnimation.CompatabilityAnimationListener()
				{
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