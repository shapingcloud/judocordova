package com.judopay.android.library.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.judopay.android.api.Constants;
import com.judopay.android.api.JudoPaymentCallback;
import com.judopay.android.api.TransactionProcessingApiService;
import com.judopay.android.api.data.*;
import com.judopay.android.api.exception.InvalidDataException;
import com.judopay.android.library.JudoSDKManager;
import com.judopay.android.library.R;
import com.judopay.android.library.ui.AVSEntryView;
import com.judopay.android.library.ui.CardEntryView;
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
public class PaymentActivity extends BaseActivity {

    String judoPaymentRef;
    Float judoAmount;
    String judoId;
    String judoCurrency;
    Bundle judoMetaData;
    CardEntryView cardEntryView;
    Consumer judoConsumer;
    private AVSEntryView aVSEntryView;
    private HelpButton cv2ExpiryHelpInfoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);
        setTitle(getString(R.string.title_payment));
        cardEntryView = (CardEntryView) findViewById(R.id.cardEntryView);
        TextView hintTextView = (TextView) findViewById(R.id.hintTextView);
        cardEntryView.setHintTextView(hintTextView);
        aVSEntryView = (AVSEntryView) findViewById(R.id.avsEntryView);

        cv2ExpiryHelpInfoButton = (HelpButton) findViewById(R.id.infoButtonID);
        cv2ExpiryHelpInfoButton.setVisibility(View.GONE);

        setHelpText(R.string.help_info, R.string.help_card_text);
        setHelpText(R.string.help_postcode_title, R.string.help_postcode_text, R.id.postCodeHelpButton);

        // Get required intent extras
        judoPaymentRef = getIntent().getStringExtra(JudoSDKManager.JUDO_PAYMENT_REF);
        judoConsumer = getIntent().getParcelableExtra(JudoSDKManager.JUDO_CONSUMER);

        judoAmount = Float.valueOf(getIntent().getStringExtra(JudoSDKManager.JUDO_AMOUNT));
        judoId = getIntent().getStringExtra(JudoSDKManager.JUDO_ID);
        judoCurrency = getIntent().getStringExtra(JudoSDKManager.JUDO_CURRENCY);

        if (judoPaymentRef==null) throw new IllegalArgumentException("JUDO_PAYMENT_REF must be supplied");
        if (judoConsumer==null) throw new IllegalArgumentException("JUDO_CONSUMER must be supplied");
        if (judoAmount==null) throw new IllegalArgumentException("JUDO_AMOUNT must be supplied");
        if (judoId==null) throw new IllegalArgumentException("JUDO_ID must be supplied");
        if (judoCurrency==null) throw new IllegalArgumentException("JUDO_CURRENCY must be supplied");

        //optional meta data
        judoMetaData = getIntent().getBundleExtra(JudoSDKManager.JUDO_META_DATA);

        ((Button)findViewById(R.id.payButton)).setText(getString(R.string.payment));

        findViewById(R.id.payButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Perform payment
                try {
                    makeCardPayment();
                } catch (Exception e) {
                    Toast.makeText(PaymentActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(Constants.DEBUG_TAG, "Exception", e);
                }
            }
        });

        cardEntryView.setEntryCompleteListener(new CardEntryView.EntryCompleteListener() {
            @Override
            public void onCreditCardEntered(String cardNo) {
                cv2ExpiryHelpInfoButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onExpiryAndCV2Entered(String cardNo, String cv2) {
                if (JudoSDKManager.isAVSEnabled && aVSEntryView!=null){
                    aVSEntryView.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    @Override
    public void onBackPressed(){
        setResult(JudoSDKManager.JUDO_CANCELLED);
        super.onBackPressed();
    }

    public void makeCardPayment() throws InvalidDataException {
        String cardNo = cardEntryView.getCardNumber();
        String expiryDate = cardEntryView.getCardExpiry();
        String cv2 = cardEntryView.getCardCV2();

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
        TransactionProcessingApiService.pay(this, transaction, new JudoPaymentCallback() {
            @Override
            public void onPaymentSuccess(Receipt receipt) {
                Intent intent = new Intent();
                intent.putExtra(JudoSDKManager.JUDO_RECEIPT, receipt);
                setResult(JudoSDKManager.JUDO_SUCCESS, intent);
                finish();
                Log.d("com.judopay.android", "SUCCESS: "+receipt.toString());
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
