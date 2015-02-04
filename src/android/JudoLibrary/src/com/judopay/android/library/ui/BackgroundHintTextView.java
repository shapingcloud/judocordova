package com.judopay.android.library.ui;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.*;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.judopay.android.api.Constants;
import com.judopay.android.library.R;
import com.judopay.android.library.utils.FakeR;
import com.judopay.android.library.utils.Typefaces;
import com.judopay.android.library.utils.UiUtils;

import java.util.ArrayList;

/**
 * Class: com.example.TestProject.CardNumberTextView
 * Project: JudoPayments
 * Created Date: 18/06/13 10:35
 *
 * @author <a href="mailto:developersupport@judopayments.com <developersupport@judopayments.com>">Matthew Rollings</a>
 *         Copyright (c) Alternative Payments Ltd 2014. All rights reserved.
 */


public abstract class BackgroundHintTextView extends FrameLayout
{
	private EditText textTextView;
	private TextView hintTextView;

	ArrayList<Integer> skipCharsAtPositions = new ArrayList<Integer>();
	private String hintText = "";
	private int beforeTextSize;
	private String filterString = "";
    private TextView textErrorView;
    private String errorText;
    private boolean nextMustBeDelete = false;

    /**
	 * Interface to pass on updates to the current activities
	 */
	public interface EntryCompleteListener
	{
		public void onEntryComplete(String value);

		public void onProgress(int pos);
	}

	EntryCompleteListener entryCompleteListener;

	public EditText getEditText() {
		return textTextView;
	}

    protected TextView getHintText() {
        return hintTextView;
    }

	public Editable getText() {
		return textTextView.getText();
	}

	public final void setText(CharSequence text) {
		textTextView.setText(text);
	}

    public void setErrorText(String errorText) {
        this.errorText = errorText;
        textErrorView.setText(this.errorText);
    }

	public void setHintTextVisible(boolean visible) {
		hintTextView.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
	}

	public void setRealHintText(String hint) {
		textTextView.setHint(hint);
	}

	@Override
	public void setOnFocusChangeListener(OnFocusChangeListener l) {
		textTextView.setOnFocusChangeListener(l);
	}

	public void setEntryCompleteListener(EntryCompleteListener entryCompleteListener){
       this.entryCompleteListener=entryCompleteListener;
    }


    public BackgroundHintTextView(Context context) {
        super(context);
        init();
    }

    public BackgroundHintTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BackgroundHintTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }



    public void setHintText(String hintText){
        this.hintText =hintText;
        hintTextView.setText(hintText);
    }

    public void setInputFilter(final String s){
        filterString=" "+s;
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned spanned, int i3, int i4) {
                for (int i = start; i < end; i++) {
                    if (!Character.isDigit(source.charAt(i)) && !isCharInFilter(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        };
        textTextView.setFilters(new InputFilter[] {new InputFilter.LengthFilter(hintText.length()), filter});

        for (int i=0;i<hintText.length();i++){
            if (isCharInFilter(hintText.charAt(i))){
                skipCharsAtPositions.add(i);
            }
        }
    }

    public boolean isCharInFilter(Character c){
        return filterString.contains(""+c);
    }

    private void init() {
        super.removeAllViews(); // kill old containers

        hintTextView = new EditText(getContext());
        textTextView = new NoCursorMovingEditText(getContext()) {
            @Override
            void onBackKeyPressed() {
                backkeyPressed();
            }
        };

		textTextView.setTextAppearance(getContext(), FakeR.getResourceID(getContext(), "style/judo_payments_CardText"));
		hintTextView.setTextAppearance(getContext(), FakeR.getResourceID(getContext(), "style/judo_payments_HintText"));

		
        hintTextView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        textTextView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        hintTextView.setEnabled(false);
        hintTextView.setFocusable(false);

        textTextView.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        hintTextView.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

        hintTextView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
		textTextView.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        textErrorView = new EditText(getContext());
        FrameLayout.LayoutParams errorLP = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        int margin = UiUtils.toPixels(getContext(), 8);
        errorLP.setMargins(margin,0,margin,0);

        textErrorView.setLayoutParams(errorLP);
        textErrorView.setEnabled(false);
        textErrorView.setFocusable(false);
        textErrorView.setVisibility(View.GONE);
        textErrorView.setBackgroundColor(Color.RED);
        textErrorView.setText(errorText);
        textErrorView.setSingleLine(true);
        textErrorView.setGravity(Gravity.CENTER);
        textErrorView.setTextAppearance(getContext(), FakeR.getResourceID(getContext(), "style/judo_payments_ErrorText"));

        //set courier font
        final Typeface type = Typefaces.loadTypefaceFromRaw(getContext(), R.raw.courier);
        hintTextView.setTypeface(type);
        textTextView.setTypeface(type);
        textErrorView.setTypeface(type);

        addView(hintTextView);
        addView(textTextView);
        addView(textErrorView);

        textTextView.addTextChangedListener(new TextWatcher() {

            public CharSequence previousString;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                previousString = charSequence;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                beforeTextSize=i2;
            }

            @Override
            public void afterTextChanged(Editable editable) {
                int l = editable.toString().length();
                boolean deleting = beforeTextSize==1;

                //textTextView.setTypeface(l==0?Typeface.DEFAULT:Typeface.MONOSPACE);

                if (deleting){
                    nextMustBeDelete=false;
                }
                else if (nextMustBeDelete && l>4){
                    textTextView.setText(l==0?"":editable.subSequence(0,l-1));
                    return;
                }

                // If we are deleting (we've just removed a space char, so delete another char please:
                // Or if we've pressed space don't allow it!
                if ((deleting && skipCharsAtPositions.contains(l)) || (l>0 && isCharInFilter(editable.charAt(l-1)) && !skipCharsAtPositions.contains(l-1))){
                    // If our char is at position 0 use an empty string and set selection to 0
                    textTextView.setText(l==0?"":editable.subSequence(0,l-1));
                    textTextView.setSelection(l==0?0:l-1);
                    return;
                }

                // Adds a non numeric char at positions needed
                for (int i: skipCharsAtPositions){
                    // We rescan all letters recursively to catch when a users pastes into the edittext
                    if (l>i){
                        if (hintText.charAt(i) != editable.toString().charAt(i)){
                            textTextView.setText(editable.subSequence(0,i)+""+hintText.charAt(i)+editable.subSequence(i, editable.length()));
                            return;
                        }
                    }
                }

                // Update the hint text
                String hintText="";
                for (int i=0; i< BackgroundHintTextView.this.hintText.length(); i++){
                    if (i<l) hintText+=" ";
                    else hintText+= BackgroundHintTextView.this.hintText.substring(i, i + 1);
                }
                hintTextView.setText(hintText);

                // We've got all the chars we need, fire off our listener
                if(l==hintText.length()){
                    // Remove spaces to give just the cc #
                    // If it's a valid number send onEntryComplete
					try {
						validateInput(editable.toString());
						if (entryCompleteListener!=null) entryCompleteListener.onEntryComplete(editable.toString());
						return;
					}
					catch(Exception e) {
						Log.e(Constants.DEBUG_TAG, e.getMessage(), e);
					}
					showInvalid();
                }
                else if (entryCompleteListener!=null){
                    entryCompleteListener.onProgress(l);
                }
            }
        });
    }


    public void showInvalid(){
        showInvalid(errorText);
    }

    public void showInvalid(String errorMessage){
        nextMustBeDelete = true;
        textErrorView.setText(errorMessage);
        textErrorView.setVisibility(View.VISIBLE);

        AnimationSet animationSet = new AnimationSet(true);

        AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setDuration(300);

        AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
        fadeOut.setStartOffset(300+300);
        fadeOut.setDuration(300);

        animationSet.addAnimation(fadeIn);
        animationSet.addAnimation(fadeOut);

        animationSet.setFillAfter(true);
        animationSet.setFillBefore(true);
        textErrorView.startAnimation(animationSet);
    }

	public void addTextChangedListener(TextWatcher watcher) {
		textTextView.addTextChangedListener(watcher);
	}



	abstract void validateInput(String input) throws Exception;


    abstract void backkeyPressed();

}