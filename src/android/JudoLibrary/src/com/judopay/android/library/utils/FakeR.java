package com.judopay.android.library.utils;

import android.content.Context;
import android.util.Log;
import com.judopay.android.api.Constants;

/**
 * Utility for getting resource IDs by name - needed in order to use library project files in PhoneGap without modification
 * <p/>
 * Class: com.judopay.android.library.utils.FakeR
 * Project: JudoPayments
 * Created Date: 07/01/2014 10:44
 *
 * @author <a href="mailto:developersupport@judopayments.com <developersupport@judopayments.com>">Elliot Long</a>
 *         Copyright (c) Alternative Payments Ltd 2014. All rights reserved.
 */

public class FakeR
{
	/**
	 * Used for searching for resources so we don't need to hard-code the link to the R.java file 
	 * This was added in order to support phone gap.
	 *
	 * @param identifier - should be something like "string/payment_auth" or "layout/add_card"
	 * @return the integer id or 0 if not found
	 */
	public static int getResourceID(Context ctx, String identifier){
		int result = ctx.getResources().getIdentifier(identifier, null, ctx.getPackageName());
		if(result == 0)
			Log.w(Constants.DEBUG_TAG, "Oops, couldn't find resource with this identifier: "+identifier);
		return result;
	}
}
