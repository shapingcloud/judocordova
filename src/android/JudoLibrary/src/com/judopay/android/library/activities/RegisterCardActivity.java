package com.judopay.android.library.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.judopay.android.api.Constants;
import com.judopay.android.api.JudoPaymentCallback;
import com.judopay.android.api.TransactionProcessingApiService;
import com.judopay.android.api.data.Card;
import com.judopay.android.api.data.CardAddress;
import com.judopay.android.api.data.Consumer;
import com.judopay.android.api.data.Receipt;
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
public class RegisterCardActivity extends BaseActivity {

    Bundle judoMetaData;
    CardEntryView cardEntryView;
    Consumer judoConsumer;
    private EditText addressLine1;
    private EditText addressLine2;
    private EditText addressLine3;
    private EditText addressTown;
    private EditText addressPostCode;
    private AVSEntryView aVSEntryView;
    private HelpButton cv2ExpiryHelpInfoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_card);
        setTitle(getString(R.string.title_register_card));

        cardEntryView = (CardEntryView) findViewById(R.id.cardEntryView);
        TextView hintTextView = (TextView) findViewById(R.id.hintTextView);
        cardEntryView.setHintTextView(hintTextView);
        aVSEntryView = (AVSEntryView) findViewById(R.id.avsEntryView);
        cv2ExpiryHelpInfoButton = (HelpButton) findViewById(R.id.infoButtonID);

        addressLine1 = (EditText) findViewById(R.id.addressLine1);
        addressLine2 = (EditText) findViewById(R.id.addressLine2);
        addressLine3 = (EditText) findViewById(R.id.addressLine3);
        addressTown = (EditText) findViewById(R.id.addressTown);
        addressPostCode = (EditText) findViewById(R.id.addressPostCode);

        setHelpText(R.string.help_info, R.string.help_card_text);
        setHelpText(R.string.help_postcode_title, R.string.help_postcode_text, R.id.postCodeHelpButton);

        // Get required intent extras
        judoConsumer = getIntent().getParcelableExtra(JudoSDKManager.JUDO_CONSUMER);

        if (judoConsumer==null) throw new IllegalArgumentException("JUDO_CONSUMER must be supplied");

        //optional meta data
        judoMetaData = getIntent().getBundleExtra(JudoSDKManager.JUDO_META_DATA);

        ((Button)findViewById(R.id.payButton)).setText(getString(R.string.register_card));

        findViewById(R.id.payButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Perform payment
                try {
                    makeCardPayment();
                }
                catch (Exception e){
                    Log.e(Constants.DEBUG_TAG, ""+e.getMessage(), e);
                    Toast.makeText(RegisterCardActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
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
        cardAddress.setLine1(addressLine1.getText().toString());
        cardAddress.setLine2(addressLine2.getText().toString());
        cardAddress.setLine3(addressLine3.getText().toString());
        cardAddress.setTown(addressTown.getText().toString());
        cardAddress.setPostCode(addressPostCode.getText().toString());

        Card card = Card.initCardFromDetails(cardNo, expiryDate, cv2, cardAddress);

        showLoadingSpinner(true);

        // Perform request
        TransactionProcessingApiService.registerCard(this, card, cardAddress, judoConsumer, new JudoPaymentCallback() {
            @Override
            public void onPaymentSuccess(Receipt receipt) {
                Intent intent = new Intent();
                intent.putExtra(JudoSDKManager.JUDO_RECEIPT, receipt);
                setResult(JudoSDKManager.JUDO_SUCCESS, intent);
                Log.d("com.judopay.android", "SUCCESS: " + receipt.toString());
                finish();
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
