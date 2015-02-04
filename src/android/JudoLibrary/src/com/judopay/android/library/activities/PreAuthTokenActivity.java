package com.judopay.android.library.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import com.judopay.android.api.JudoPaymentCallback;
import com.judopay.android.api.TransactionProcessingApiService;
import com.judopay.android.api.data.Receipt;
import com.judopay.android.api.data.Transaction;
import com.judopay.android.api.exception.InvalidDataException;
import com.judopay.android.library.JudoSDKManager;
import com.judopay.android.library.R;

/**
 * Class: MakePaymentActivity
 *
 * Project: com.judopay.android.library.activities JudoPayments
 * Created Date: 24/03/14 14:32
 *
 * @author <a href="mailto:developersupport@judopayments.com <developersupport@judopayments.com>">Matthew Rollings</a>
 *         Copyright (c) Alternative Payments Ltd 2014. All rights reserved.
 */
public class PreAuthTokenActivity extends PaymentTokenActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.title_pre_auth_token));
        ((Button)findViewById(R.id.payButton)).setText(getString(R.string.token_preauth));
    }


    public void makeTokenPayment() throws InvalidDataException {

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
        TransactionProcessingApiService.preAuth(this, transaction, new JudoPaymentCallback() {
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


}
