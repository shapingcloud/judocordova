package com.judopay.android.library.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.widget.EditText;

/**
 * Class: com.judopay.android.library.NoMovingCursorEditText
 * Project: JudoPayments
 * Created Date: 18/06/13 16:30
 *
 * @author <a href="mailto:developersupport@judopayments.com <developersupport@judopayments.com>">Matthew Rollings</a>
 *         Copyright (c) Alternative Payments Ltd 2014. All rights reserved.
 */
abstract public class NoCursorMovingEditText extends EditText {

        public NoCursorMovingEditText(Context context) {
            super(context);
        }

        public NoCursorMovingEditText(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public NoCursorMovingEditText(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        @Override
        public void onSelectionChanged(int start, int end) {

            CharSequence text = getText();
            if (text != null) {
                if (start != text.length() || end != text.length()) {
                    setSelection(text.length(), text.length());
                    return;
                }
            }
            super.onSelectionChanged(start, end);
        }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        return new CustomInputConnection(super.onCreateInputConnection(outAttrs),
                true);
    }

    // Method to override to see if delete pressed on an empty textview
    abstract void onBackKeyPressed();


    // We create this class so that the backspace can be caught from the soft keyboard
    // on our edit text
    private class CustomInputConnection extends InputConnectionWrapper {

        public CustomInputConnection(InputConnection target, boolean mutable) {
            super(target, mutable);
        }

        @Override
        public boolean sendKeyEvent(KeyEvent event) {
            if (event.getAction() == KeyEvent.ACTION_DOWN
                    && event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                onBackKeyPressed();
            }
            return super.sendKeyEvent(event);
        }

        @Override
        public boolean deleteSurroundingText(int beforeLength, int afterLength) {
            // magic: in latest Android, deleteSurroundingText(1, 0) will be called for backspace
            if (beforeLength == 1 && afterLength == 0) {
                // backspace
                return sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL))
                        && sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DEL));
            }

            return super.deleteSurroundingText(beforeLength, afterLength);
        }

    }


}