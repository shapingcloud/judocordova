package com.nudge.judopay;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.judopay.android.api.action.JudoPayments;

/**
 * This class echoes a string called from JavaScript.
 */
public class JudoPaymentPlugin extends CordovaPlugin {
	private static final int PAYMENT_REQUEST_CODE = 910;

	public static final String MY_JUDO_ID = "";
	public static final String MY_API_TOKEN = "";
	public static final String MY_API_SECRET = "";
	public static final int SERVER = JudoPayments.LIVE;
	private CallbackContext callbackContext;

	@Override
	public boolean execute(String action, JSONArray args,
			CallbackContext callbackContext) throws JSONException {

		//setting callback context
		this.callbackContext = callbackContext;

		if (action.equals("echo")) {
			String message = args.getString(0);
			this.echo(message, callbackContext);
			return true;
		}

		if (action.equals("makeSimpleTransaction")){
 				/*this.makePayment(this.cordova.getActivity().getApplicationContext(), args.getString(0), MY_JUDO_ID,
					MY_API_TOKEN, MY_API_SECRET, "ref68798", "cref43434",
					null, null, false,
					SERVER);*/

			this.makeOauthPayment(this.cordova.getActivity().getApplicationContext(),
								args.getString(0),
								args.getString(1),
								args.getString(2),
								args.getString(3),
								args.getString(4),
								args.getString(5), //
								args.getString(6), //primary color
								args.getString(7), //secondary color
								null,
								null,
								false,
								SERVER);
			return true;
		}

		return false;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		switch (requestCode) {
		case PAYMENT_REQUEST_CODE:
			if (resultCode == Activity.RESULT_OK ){
				 this.callbackContext.success(intent.getStringExtra("receipt"));
	 		}else {
	 			try {
		 			this.callbackContext.error(intent.getStringExtra("error"));
				} catch (Exception e) {
					// TODO: handle exception
					this.callbackContext.error("user_cancelled");
				}
	 		}
			break;

		default:
			this.callbackContext.error("Invalid Response from request code :" + Integer.toString(requestCode));
			break;
		}
	 }

	private void echo(String message, CallbackContext callbackContext) {
		if (message != null && message.length() > 0) {
			callbackContext.success("Altered by Native : " + message);
		} else {
			callbackContext.error("Expected one non-empty string argument.");
		}
	}

	private void makePayment(Context ctx, String amount, String judoID,
			String apiToken, String apiSecret, String paymentRef,
			String customerRef, String Merchant, String merchantColor, String merchantSecColor,  String customerEmail, String customerPhone,
			boolean offerPinAccess, int serverID) {

		JudoPayments.init(ctx, apiToken, apiSecret, judoID, serverID);

		Intent i = new Intent(ctx, AddCardActivity.class);
		i.putExtra("Merchant_name", Merchant); //Added setting of Merchant Name.
		i.putExtra("Merchant_color", merchantColor); //Added setting for merchant color
		i.putExtra("Merchant_scolor", merchantSecColor);

		i.putExtra("JudoPay-amount", amount);
		i.putExtra("JudoPay-judoId", judoID);
		if (!offerPinAccess)
			i.putExtra("JudoPay-Mode", "basic");// this ensures they are not
												// offered a chance to save
												// their card details
		i.putExtra("JudoPay-yourPaymentReference", paymentRef);
		if (customerRef != null)// (if this parameter is not provided, customer
								// ref will be assigned the device id)
			i.putExtra("JudoPay-yourConsumerReference", customerRef);

		// optional (For email or SMS receipts)
		if (customerEmail != null)
			i.putExtra("JudoPay-emailAddress", customerEmail);// your customer's
																// email address
																// (Email
																// receipt)

		if (customerPhone != null)
			i.putExtra("JudoPay-mobileNumber", customerPhone);// your customer's
																// mobile number
																// (SMS receipt)

  		this.cordova.startActivityForResult((CordovaPlugin) this, i, PAYMENT_REQUEST_CODE);
 	}

	private void makeOauthPayment(Context ctx, String amount, String judoID,
			String apiOauthToken, String paymentRef, String customerRef, String Merchant,String merchantColor,String merchantSecColor,
			String customerEmail, String customerPhone, boolean offerPinAccess,
			int serverID) {

		JudoPayments.init(ctx, apiOauthToken, judoID, serverID);

		Intent i = new Intent(ctx, AddCardActivity.class);
		i.putExtra("Merchant_name", Merchant); //Added setting of Merchant Name.
		i.putExtra("Merchant_color", merchantColor);
		i.putExtra("Merchant_scolor", merchantSecColor);

		i.putExtra("JudoPay-amount", amount);
		i.putExtra("JudoPay-judoId", judoID);
		if (!offerPinAccess)
			i.putExtra("JudoPay-Mode", "basic");// this ensures they are not
												// offered a chance to save
												// their card details
		i.putExtra("JudoPay-yourPaymentReference", paymentRef);
		if (customerRef != null)// (if this parameter is not provided, customer
								// ref will be assigned the device id)
			i.putExtra("JudoPay-yourConsumerReference", customerRef);

		// optional (For email or SMS receipts)
		if (customerEmail != null)
			i.putExtra("JudoPay-emailAddress", customerEmail);// your customer's
																// email address
																// (Email
																// receipt)

		if (customerPhone != null)
			i.putExtra("JudoPay-mobileNumber", customerPhone);// your customer's
																// mobile number
																// (SMS receipt)

		this.cordova.startActivityForResult((CordovaPlugin) this, i, PAYMENT_REQUEST_CODE);
	}

}
