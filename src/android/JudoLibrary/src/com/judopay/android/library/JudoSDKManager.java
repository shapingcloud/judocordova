package com.judopay.android.library;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.judopay.android.api.JudoAPIServiceBase;
import com.judopay.android.api.TransactionProcessingApiService;
import com.judopay.android.api.ValidationHelper;
import com.judopay.android.api.data.CardBase;
import com.judopay.android.api.data.CardToken;
import com.judopay.android.api.data.Consumer;
import com.judopay.android.library.activities.*;
import com.judopay.android.library.utils.FakeR;

/**
 * Class: JudoAPIManager
 * <p/>
 * <p/>
 * <p/>
 * Project: com.judopay.android.api JudoPayments
 * Created Date: 24/03/14 08:39
 *
 * @author <a href="mailto:developersupport@judopayments.com <developersupport@judopayments.com>">Matthew Rollings</a>
 *         Copyright (c) Alternative Payments Ltd 2014. All rights reserved.
 */
public class JudoSDKManager {

    public static final int JUDO_SUCCESS = Activity.RESULT_OK;
    public static final int JUDO_CANCELLED = Activity.RESULT_CANCELED;
    public static final int JUDO_ERROR = Activity.RESULT_FIRST_USER+1;

    public static final String JUDO_ERROR_MESSAGE = "ERROR_MESSAGE";
    public static final String JUDO_ERROR_THROWABLE = "ERROR_THROWABLE";

    public static final String JUDO_PAYMENT_REF = "JudoPay-yourPaymentReference";
    public static final String JUDO_AMOUNT = "JudoPay-amount";
    public static final String JUDO_ID = "JudoPay-judoId";
    public static final String JUDO_CURRENCY = "JudoPay-currency";
    public static final String JUDO_META_DATA = "JudoPay-yourPaymentMetaData";

    public static final String JUDO_RECEIPT = "JudoPay-receipt";

    public static final String JUDO_CARD_DETAILS = "JudoPay-CardToken";
    public static final String JUDO_CONSUMER = "JudoPay-consumer";

    //this is a library only config
    public static boolean isAVSEnabled = false;

    public static Intent makeAPreAuth(Context context,
                                      String judoId,
                                      String currency,
                                      String amount,
                                      String yourPaymentRef,
                                      String consumerRef,
                                      Bundle metaData){

        Intent intent = new Intent(context, PreAuthActivity.class);
        intent.putExtra(JUDO_PAYMENT_REF, yourPaymentRef);
        intent.putExtra(JUDO_CONSUMER, new Consumer(consumerRef));
        intent.putExtra(JUDO_AMOUNT, amount);
        intent.putExtra(JUDO_ID, judoId);
        intent.putExtra(JUDO_CURRENCY, currency);

        //optional meta data
        intent.putExtra(JUDO_META_DATA, metaData);

        // Return our payment intent
        return intent;
    }

    public static Intent makeATokenPreAuth(Context context,
                                           String judoId,
                                           String currency,
                                           String amount,
                                           String yourPaymentRef,
                                           CardToken CardToken,
                                           Consumer consumer,
                                           Bundle metaData){

        Intent intent = new Intent(context, PreAuthTokenActivity.class);
        intent.putExtra(JUDO_PAYMENT_REF, yourPaymentRef);
        intent.putExtra(JUDO_AMOUNT, amount);
        intent.putExtra(JUDO_ID, judoId);
        intent.putExtra(JUDO_CURRENCY, currency);
        intent.putExtra(JUDO_CARD_DETAILS, CardToken);
        intent.putExtra(JUDO_CONSUMER, consumer);

        //optional meta data
        intent.putExtra(JUDO_META_DATA, metaData);

        // Return our payment intent
        return intent;

    }

    public static Intent makeAPayment(Context context,
                                    String judoId,
                                    String currency,
                                    String amount,
                                    String yourPaymentRef,
                                    String consumerRef,
                                    Bundle metaData){

        Intent intent = new Intent(context, PaymentActivity.class);
        intent.putExtra(JUDO_PAYMENT_REF, yourPaymentRef);
        intent.putExtra(JUDO_CONSUMER, new Consumer(consumerRef));
        intent.putExtra(JUDO_AMOUNT, amount);
        intent.putExtra(JUDO_ID, judoId);
        intent.putExtra(JUDO_CURRENCY, currency);

        //optional meta data
        intent.putExtra(JUDO_META_DATA, metaData);

        // Return our payment intent
        return intent;

    }

    public static Intent makeATokenPayment(Context context,
                                    String judoId,
                                    String currency,
                                    String amount,
                                    String yourPaymentRef,
                                    CardToken CardToken,
                                    Consumer consumer,
                                    Bundle metaData){

        Intent intent = new Intent(context, PaymentTokenActivity.class);
        intent.putExtra(JUDO_PAYMENT_REF, yourPaymentRef);
        intent.putExtra(JUDO_AMOUNT, amount);
        intent.putExtra(JUDO_ID, judoId);
        intent.putExtra(JUDO_CURRENCY, currency);
        intent.putExtra(JUDO_CARD_DETAILS, CardToken);
        intent.putExtra(JUDO_CONSUMER, consumer);

        //optional meta data
        intent.putExtra(JUDO_META_DATA, metaData);

        // Return our payment intent
        return intent;
    }

    public static Intent registerCard(Context context,
                              Consumer consumer){

        Intent intent = new Intent(context, RegisterCardActivity.class);
        intent.putExtra(JUDO_CONSUMER, consumer);

        // Return our register intent
        return intent;
    }

    /**
     *
     * @param context app context
     * @param key provided by Judo
     * @param secret provided by Judo
     */
    public static void setKeyAndSecret(Context context, String key, String secret){
        TransactionProcessingApiService.setKeyAndSecret(context, key, secret);
        init(context);
    }

    /**
     *
     * @param context app context
     * @param accessToken oauth token
     */
    public static void setOAuthAccessToken(Context context, String accessToken){
        TransactionProcessingApiService.setOAuthAccessToken(context, accessToken);
        init(context);
    }

    /**
     * Initialises anything that needs setting up before we can use this library
     *
     * @param context the Activity or application context
     */
    public static void init(Context context){
        // Set the Secure3D loading screen layout
        JudoAPIServiceBase.setSecure3DIntersitialLayoutId(context, R.layout.secure3d);
    }


    /**
     * Sets ProductionMode to true. DEFAULT: false (i.e sandbox)
     * @param context app context
     */
    public static void setProductionMode(Context context){
        TransactionProcessingApiService.setProductionMode(context);
    }

    /**
     * Enabled 3D secure. DEFAULT: false
     * @param enabled
     */
    public static void set3DsecureEnabled(boolean enabled){
        TransactionProcessingApiService.set3DsecureEnabled(enabled);
    }


    /**
     * Enabled AVS. DEFAULT: false
     * @param isAVSEnabled
     */
    public static void setAVSEnabled(boolean isAVSEnabled) {
        JudoSDKManager.isAVSEnabled = isAVSEnabled;
    }

    public static int getCardResourceID(Context context, String cardNo, boolean showFront){
        int cardType = ValidationHelper.getCardType(cardNo);
        return getCardResourceID(context, cardType, showFront);
    }

    public static int getCardResourceID(Context context, int cardType, boolean showFront){
        if (showFront){
            switch (cardType){
                case CardBase.CardType.VISA:
                    return FakeR.getResourceID(context, "drawable/ic_card_visa");
                case CardBase.CardType.MASTERCARD:
                    return FakeR.getResourceID(context, "drawable/ic_card_mastercard");
                case CardBase.CardType.AMEX:
                    return FakeR.getResourceID(context, "drawable/ic_card_amex");
                case CardBase.CardType.MAESTRO:
                    return FakeR.getResourceID(context, "drawable/ic_card_maestro");
                case CardBase.CardType.UNKNOWN:
                default:
                    return FakeR.getResourceID(context, "drawable/ic_card_unknown");
            }
        }
        else{
            switch (cardType){
                case CardBase.CardType.AMEX:
                    return FakeR.getResourceID(context, "drawable/ic_card_cv2_amex");
                default:
                    return FakeR.getResourceID(context, "drawable/ic_card_cv2");
            }
        }
    }

    public static Intent createErrorIntent(String message, Throwable throwable){
        Intent intent = new Intent();
        intent.putExtra(JUDO_ERROR_MESSAGE, message);
        intent.putExtra(JUDO_ERROR_THROWABLE, throwable);
        return intent;
    }

    public static class JudoError{
        public String message;
        public Throwable throwable;
    }

    public static JudoError parseErrorIntent(Intent intent){
        JudoError judoError = new JudoError();
        judoError.message = intent.getStringExtra(JUDO_ERROR_MESSAGE);
        judoError.throwable = (Throwable) intent.getSerializableExtra(JUDO_ERROR_THROWABLE);
        return judoError;
    }

}
