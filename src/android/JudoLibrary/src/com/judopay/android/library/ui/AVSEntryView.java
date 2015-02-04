package com.judopay.android.library.ui;


import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.judopay.android.api.exception.InvalidDataException;
import com.judopay.android.library.R;
import com.judopay.android.library.utils.Typefaces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class: com.example.TestProject.CardNumberTextView
 * Project: JudoPayments
 * Created Date: 18/06/13 10:35
 *
 * @author <a href="mailto:developersupport@judopayments.com <developersupport@judopayments.com>">Matthew Rollings</a>
 *         Copyright (c) Alternative Payments Ltd 2014. All rights reserved.
 */
public class AVSEntryView extends LinearLayout
{

    private Spinner countrySpinner;
    private EditText postCodeEditText;
    private TextView postCodeTitleText;
    private View postCodeContainer;

    /**
	 * Constructors
	 */
	public AVSEntryView(Context context) {
		super(context);
		init();
	}

	public AVSEntryView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}


    public String getCountry() throws InvalidDataException {
        return "";
    }

    public String getPostCode() throws InvalidDataException {
        return "";
    }


    String countries[];
    String postcodeText[];

	private void init() {
		super.removeAllViews();

		setOrientation(VERTICAL);

        View view = inflate(getContext(), R.layout.avs, null);
        addView(view);

        //get the arrays of values from Strings
        countries = getResources().getStringArray(R.array.avs_countries);
        postcodeText = getResources().getStringArray(R.array.avs_countries_postcode_label_text);

        countrySpinner = (Spinner) view.findViewById(R.id.countrySpinner);
        postCodeContainer =  view.findViewById(R.id.postCodeContainer);
        postCodeEditText = (EditText) view.findViewById(R.id.postCodeEditText);
        postCodeTitleText = (TextView) view.findViewById(R.id.postCodeTitleText);

        final Typeface type = Typefaces.loadTypefaceFromRaw(getContext(), R.raw.courier);
        postCodeEditText.setTypeface(type);


        List<String> list = new ArrayList<String>();
        Collections.addAll(list, countries);

        // Populate country spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, list){

            //so we can set the font of items in the list
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View row = super.getDropDownView(position, convertView, parent);
                if (row instanceof TextView){
                    ((TextView)row).setTypeface(type);
                    ((TextView)row).setTextColor(getResources().getColor(R.color.default_text));
                    ((TextView)row).setTextSize(18);
                }
                return row;
            }

            //so we can set the font of the selected item
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View row= super.getView(position, convertView, parent);
                if (row instanceof TextView){
                    ((TextView)row).setTypeface(type);
                    ((TextView)row).setTextColor(getResources().getColor(R.color.default_text));
                    ((TextView)row).setTextSize(18);
                }
                return row;
            }
        };
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(dataAdapter);

        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                postCodeTitleText.setText(postcodeText[position]);
                postCodeEditText.setHint(postcodeText[position]);
                if (position==3){
                    postCodeContainer.setVisibility(View.INVISIBLE);
                }
                else{
                    postCodeContainer.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
	}





}