package com.judopay.android.library.activities;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.judopay.android.api.data.BaseData;
import com.judopay.android.library.BuildConfig;
import com.judopay.android.library.JudoSDKManager;
import com.judopay.android.library.R;
import com.judopay.android.library.ui.HelpButton;

/**
 * Class: MakePaymentActivity
 *
 * Project: com.judopay.android.library.activities JudoPayments
 * Created Date: 24/03/14 14:32
 *
 * @author <a href="mailto:developersupport@judopayments.com <developersupport@judopayments.com>">Matthew Rollings</a>
 *         Copyright (c) Alternative Payments Ltd 2014. All rights reserved.
 */
public class BaseActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //remove title bar for non action bar supporting
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }else{
            setTheme(R.style.Theme_Judo_payments);

            if (!BuildConfig.DEBUG) {
                //prevents screen shot
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
            }
        }

    }

    public void setHelpText(int titleId, int messageId, int helpButtonResId){
        String title = getString(titleId);
        String message = getString(messageId);
        setHelpText(title, message, helpButtonResId);
    }


    public void setHelpText(int titleId, int messageId){
        setHelpText(titleId, messageId, R.id.infoButtonID);
    }

    public void setHelpText(final String title, final String message){
        setHelpText(title, message, R.id.infoButtonID);
    }

    public void setHelpText(final String title, final String message, int helpButtonResId){
        HelpButton infoButton = (HelpButton) findViewById(helpButtonResId);
        if (infoButton==null){
            return;
        }

        infoButton.setHelpClickListener(new HelpButton.HelpClickListener()
        {
            @Override
            public void onClick(boolean isHelp) {
                showMessage(title, message);
            }
        });
    }


    final protected void showMessage(final String msg){
        showMessage(null, msg);
    }

    final protected void showMessage(final String title, final String msg){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showConfirmation(title, msg);
            }
        });
    }

    final public void showConfirmation(String title, String msg){
        showConfirmation(title, msg, true, null, null);
    }

    public void showConfirmation(String title, String msg, boolean cancelable, String buttonLabel, final Runnable buttonAction){
        //Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(!BaseData.isBlank(title))
            builder.setTitle(title);
        builder.setMessage(msg);
        builder.setCancelable(cancelable);
        if(buttonLabel == null) {
            builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
        }
        else{
            builder.setPositiveButton(buttonLabel, new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int id) {
                    if(buttonAction != null)
                        new Thread(buttonAction).start();
                    dialog.dismiss();
                }
            });
            if(cancelable)
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        }
        builder.show();
    }

    @Override
    protected void onPostCreate(Bundle bundle){
        super.onPostCreate(bundle);

        View cancelButton = findViewById(R.id.cancelButton);
        // If we have a cancel button register it's listener to close the activity
        if (cancelButton!=null) {
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setResult(JudoSDKManager.JUDO_CANCELLED);
                    finish();
                }
            });
        }

    }

}
