package com.judopay.android.library.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;
import com.judopay.android.api.Constants;
import com.judopay.android.api.JudoPaymentCallback;
import com.judopay.android.api.TransactionProcessingApiService;
import com.judopay.android.api.data.CardToken;
import com.judopay.android.api.data.Consumer;
import com.judopay.android.api.data.Receipt;
import com.judopay.android.api.data.Transaction;
import com.judopay.android.api.exception.InvalidDataException;
import com.judopay.android.library.JudoSDKManager;
import com.judopay.android.library.R;
import com.judopay.android.library.ui.CV2EntryView;

/**
 * Class: MakePaymentActivity
 *
 * Project: com.judopay.android.library.activities JudoPayments
 * Created Date: 24/03/14 14:32
 *
 * @author <a href="mailto:developersupport@judopayments.com <developersupport@judopayments.com>">Matthew Rollings</a>
 *         Copyright (c) Alternative Payments Ltd 2014. All rights reserved.
 */
public class PaymentTokenActivity extends BaseActivity {

    String judoPaymentRef;
    Float judoAmount;
    String judoId;
    String judoCurrency;
    Bundle judoMetaData;
    CardToken judoCardToken;
    Consumer judoConsumer;
    CV2EntryView cv2EntryView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.token_payment);
        setTitle(getString(R.string.title_payment_token));
        cv2EntryView = (CV2EntryView) findViewById(R.id.cv2EntryView);

        setHelpText(R.string.help_info, R.string.help_cv2_text);
        setHelpText(R.string.help_postcode_title, R.string.help_postcode_text, R.id.postCodeHelpButton);

        // Get required intent extras
        judoPaymentRef = getIntent().getStringExtra(JudoSDKManager.JUDO_PAYMENT_REF);

        judoAmount = Float.valueOf(getIntent().getStringExtra(JudoSDKManager.JUDO_AMOUNT));
        judoId = getIntent().getStringExtra(JudoSDKManager.JUDO_ID);
        judoCurrency = getIntent().getStringExtra(JudoSDKManager.JUDO_CURRENCY);
        judoCardToken = getIntent().getParcelableExtra(JudoSDKManager.JUDO_CARD_DETAILS);
        judoConsumer = getIntent().getParcelableExtra(JudoSDKManager.JUDO_CONSUMER);

        if (judoPaymentRef==null) throw new IllegalArgumentException("JUDO_PAYMENT_REF must be supplied");
        if (judoAmount==null) throw new IllegalArgumentException("JUDO_AMOUNT must be supplied");
        if (judoId==null) throw new IllegalArgumentException("JUDO_ID must be supplied");
        if (judoCurrency==null) throw new IllegalArgumentException("JUDO_CURRENCY must be supplied");
        if (judoCardToken==null) throw new IllegalArgumentException("JUDO_CARD_DETAILS must be supplied");
        if (judoConsumer==null) throw new IllegalArgumentException("JUDO_CONSUMER must be supplied");

        cv2EntryView.setCardDetails(judoCardToken);

        //optional meta data
        judoMetaData = getIntent().getBundleExtra(JudoSDKManager.JUDO_META_DATA);

        ((Button)findViewById(R.id.payButton)).setText(getString(R.string.token_payment));

        // Get intent data
        findViewById(R.id.payButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Perform payment
                try{
                    makeTokenPayment();
                } catch (Exception e) {
                    Toast.makeText(PaymentTokenActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(Constants.DEBUG_TAG, "Exception", e);
                }
            }
        });
    }

    @Override
    public void onBackPressed(){
        setResult(JudoSDKManager.JUDO_CANCELLED);
        super.onBackPressed();
    }

    public void makeTokenPayment() throws InvalidDataException{

        // Create transaction object
        Transaction transaction = Transaction.initWithAmount(
                this,
                judoId,
                judoCurrency,
                judoAmount,
                judoPaymentRef,
                judoConsumer,
                judoMetaData,
                judoCardToken
        );

        // Add CV2 to the card details as confirmation
        judoCardToken.setCV2(cv2EntryView.getCv2());

        showLoadingSpinner(true);

        // Perform request
        TransactionProcessingApiService.pay(this, transaction, new JudoPaymentCallback() {
            @Override
            public void onPaymentSuccess(Receipt receipt) {
                Intent intent = new Intent();
                intent.putExtra(JudoSDKManager.JUDO_RECEIPT, receipt);
                setResult(JudoSDKManager.JUDO_SUCCESS, intent);
                finish();
                Log.e("com.judopay.android", "SUCCESS");
            }

            @Override
            public void onPaymentError(String s, Throwable throwable) {
                showLoadingSpinner(false);
                Log.e("com.judopay.android", "ERROR: "+s);
                setResult(JudoSDKManager.JUDO_ERROR, JudoSDKManager.createErrorIntent(s, throwable));
                finish();
            }
        });

    }


    public void showLoadingSpinner(final boolean show){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(findViewById(R.id.payButton).getWindowToken(), 0);
                findViewById(R.id.loadingLayout).setVisibility(show?View.VISIBLE:View.GONE);
            }
        });
    }


}
