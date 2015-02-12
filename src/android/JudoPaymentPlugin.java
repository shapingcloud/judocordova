package com.nudge.judopay;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import com.judopay.android.api.data.CardToken;
import com.judopay.android.api.data.Consumer;
import com.judopay.android.api.data.Receipt;
import com.judopay.android.library.JudoSDKManager;

/**
 * This class echoes a string called from JavaScript.
 */
public class JudoPaymentPlugin extends CordovaPlugin {
    private static final int PAYMENT_REQUEST_CODE = 910;
    public static final int ACTION_CARD_PAYMENT = 101;
    public static final int ACTION_TOKEN_PAYMENT = 102;
    public static final int ACTION_PREAUTH = 201;
    public static final int ACTION_TOKEN_PREAUTH = 202;
    private static final int ACTION_REGISTER_CARD = 301;


    private CallbackContext callbackContext;

    @Override
    public boolean execute(String action, JSONArray args,
            CallbackContext callbackContext) throws JSONException {

        //setting callback context
        this.callbackContext = callbackContext;

        if (action.equals("makeSimpleTransaction")){
                /*this.makePayment(this.cordova.getActivity().getApplicationContext(), args.getString(0), MY_JUDO_ID,
                    MY_API_TOKEN, MY_API_SECRET, "ref68798", "cref43434",
                    null, null, false,
                    SERVER);*/

            this.makeOauthPayment(this.cordova.getActivity().getApplicationContext(),
                                args.getString(0), // paymentAmount
                                args.getString(1), // currency
                                args.getString(2), // paymentRef
                                args.getString(3), // consumerRef
                                args.getString(4) // userReceipt
                                );
            return true;
        }

        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
        case ACTION_CARD_PAYMENT:
        case ACTION_TOKEN_PAYMENT:
            switch (resultCode){
                case JudoSDKManager.JUDO_SUCCESS:
                    // STUB: Handle successful payment
                    Receipt receipt = intent.getParcelableExtra(JudoSDKManager.JUDO_RECEIPT);
                    this.callbackContext.success(receipt.toJson());
                    break;
                case JudoSDKManager.JUDO_CANCELLED:
                    this.callbackContext.error("user_cancelled");
                    break;
                case JudoSDKManager.JUDO_ERROR:
                    // STUB: Handle Payment Cancelled
                    JudoSDKManager.JudoError judoError = JudoSDKManager.parseErrorIntent(intent);
                    this.callbackContext.error(judoError.toString());
                    break;
            }

            break;

        default:
            this.callbackContext.error("Invalid Response from request code :" + Integer.toString(requestCode));
            break;
        }
     }




    private void makeOauthPayment(Context ctx, String amount, String currency, String paymentRef, String customerRef, String userReceipt) {


        int judo_id_id = cordova.getActivity().getResources().getIdentifier("judo_id", "string", cordova.getActivity().getPackageName());
        String judo_id = cordova.getActivity().getString(judo_id_id);

        int judo_api_token_id = cordova.getActivity().getResources().getIdentifier("judo_api_token", "string", cordova.getActivity().getPackageName());
        String judo_api_token = cordova.getActivity().getString(judo_api_token_id);
        
        int judo_api_secret_id = cordova.getActivity().getResources().getIdentifier("judo_api_secret", "string", cordova.getActivity().getPackageName());
        String judo_api_secret = cordova.getActivity().getString(judo_api_secret_id);

        int judo_env_id = cordova.getActivity().getResources().getIdentifier("judo_env", "string", cordova.getActivity().getPackageName());
        String judo_env = cordova.getActivity().getString(judo_env_id);

        JudoSDKManager.setKeyAndSecret(ctx, judo_api_token, judo_api_secret);

        if(judo_env.equals("live")) {
        	JudoSDKManager.setProductionMode(ctx);
        }


        if (userReceipt.equals("")){
            Intent intent = JudoSDKManager.makeAPayment(ctx, judo_id, currency, amount, paymentRef, customerRef, null); 
            this.cordova.startActivityForResult((CordovaPlugin) this, intent, ACTION_CARD_PAYMENT);
        } else {

            Receipt receipt = Receipt.fromJson(userReceipt);

            // We get the card details and consumer information from the receipt
            CardToken cardToken = receipt.getCardToken();
            Consumer consumer = receipt.getConsumer();
            
            Intent intent = JudoSDKManager.makeATokenPayment(ctx, judo_id, currency, amount, paymentRef, cardToken, consumer, null);
            this.cordova.startActivityForResult((CordovaPlugin) this, intent, ACTION_TOKEN_PAYMENT);
        }

        
        
    }

}
