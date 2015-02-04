package com.judopay.android.library.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.judopay.android.library.utils.FakeR;

/**
 * ALternately shows an "i" icon or an x to delete the contents of the current text field
 * <p/>
 * Class: com.judopay.android.library.ui.HelpButton
 * Project: JudoPayments
 * Created Date: 20/06/2013 17:28
 *
 * @author <a href="mailto:developersupport@judopayments.com <developersupport@judopayments.com>">Elliot Long</a>
 *         Copyright (c) Alternative Payments Ltd 2014. All rights reserved.
 */

public class HelpButton extends LinearLayout
{
	ImageView img;
	public HelpButton(Context context) {
		this(context, null);
	}

	public HelpButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		setGravity(Gravity.CENTER);
		
		img = new ImageView(context);
		img.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        int src_resource = attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "src", 0);
        //img.setImageResource(FakeR.getResourceID(context, "drawable/ic_info"));
        img.setImageResource(src_resource);
		
		setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				if(clickListener != null)
					clickListener.onClick(isHelp);
			}
		});
		addView(img);
	}

	boolean isHelp = true;
	
	public void setHelp(boolean isHelp){
		this.isHelp = isHelp;

        if (isHelp) img.setImageResource(FakeR.getResourceID(getContext(), "drawable/ic_info"));
        else img.setImageResource(FakeR.getResourceID(getContext(), "drawable/ic_x"));
    }

	HelpClickListener clickListener;
	public interface HelpClickListener 
	{
		public void onClick(boolean isHelp);
	}

	public void setHelpClickListener(HelpClickListener clickListener) {
		this.clickListener = clickListener;
	}
}
