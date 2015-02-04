package com.judopay.android.library.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import com.judopay.android.api.JudoPaymentCallback;
import com.judopay.android.api.TransactionProcessingApiService;
import com.judopay.android.api.data.Card;
import com.judopay.android.api.data.CardAddress;
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
public class PreAuthActivity extends PaymentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.title_pre_auth));
        ((Button)findViewById(R.id.payButton)).setText(getString(R.string.preauth));
    }

    @Override
    public void onBackPressed(){
        setResult(JudoSDKManager.JUDO_CANCELLED);
        super.onBackPressed();
    }

    public void makeCardPayment() throws InvalidDataException {
       String cv2 = cardEntryView.getCardCV2();
       String expiryDate = cardEntryView.getCardExpiry();
       String cardNo = cardEntryView.getCardNumber();

        // Address for the card
        CardAddress cardAddress = new CardAddress();
//        cardAddress.setLine1("32 Edward Street");
//        cardAddress.setLine2("Watson");
//        cardAddress.setLine3("Camborne");
//        cardAddress.setTown("Cornwall");
//        cardAddress.setPostCode("TR14 8PA");

        Card card = Card.initCardFromDetails(cardNo, expiryDate, cv2, cardAddress);

        // Create transaction object
        Transaction transaction = Transaction.initWithAmount(
                this,
                judoId,
                judoCurrency,
                judoAmount,
                judoPaymentRef,
                judoConsumer,
                judoMetaData,
                card
        );

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
