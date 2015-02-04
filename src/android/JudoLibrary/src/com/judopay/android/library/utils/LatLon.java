package com.judopay.android.library.utils;

import com.judopay.android.api.data.BaseData;

/**
 * Class: com.judopay.android.library.utils.LatLon
 * Project: JudoPayments
 * Created Date: 19/06/2013 13:56
 *
 * @author <a href="mailto:developersupport@judopayments.com <developersupport@judopayments.com>">Elliot Long</a>
 *         Copyright (c) Alternative Payments Ltd 2014. All rights reserved.
 */

public class LatLon
{
	double latitude;
	double longitude;

	public LatLon(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public LatLon(CharSequence latitude, CharSequence longitude) {
		this.latitude = BaseData.makeDouble(latitude);
		this.longitude = BaseData.makeDouble(longitude);
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}
}
