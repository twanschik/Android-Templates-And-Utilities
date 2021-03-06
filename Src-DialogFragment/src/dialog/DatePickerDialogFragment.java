package com.example.dialog;

import java.util.Calendar;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;


public class DatePickerDialogFragment extends DialogFragment implements FixedDatePickerDialog.OnDateSetListener
{
	public static final String EXTRA_DATE = "date";
	
	private long mDate = -1l;
	private DatePickerDialogListener mListener;
	
	
	public interface DatePickerDialogListener
	{
		public void onDialogPositiveClick(DialogFragment dialog, int year, int month, int day);
	}
	
	
	public static DatePickerDialogFragment newInstance(Calendar calendar)
	{
		DatePickerDialogFragment fragment = new DatePickerDialogFragment();
		
		// arguments
		Bundle args = new Bundle();
		args.putLong(EXTRA_DATE, calendar.getTimeInMillis());
		fragment.setArguments(args);
		
		return fragment;
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setCancelable(true);
		
		// handle intent extras
		if(getArguments() != null)
		{
			handleExtras(getArguments());
		}
		
		// set callback listener
		try
		{
			mListener = (DatePickerDialogListener) getTargetFragment();
		}
		catch(ClassCastException e)
		{
			throw new ClassCastException(getTargetFragment().toString() + " must implement DatePickerDialogListener");
		}
	}
	
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		Calendar calendar = Calendar.getInstance();
		if(mDate!=-1l)
		{
			calendar.setTimeInMillis(mDate);
		}
		
		FixedDatePickerDialog dialog = new FixedDatePickerDialog(calendar, getActivity(), this);
		return dialog;
	}
	
	
	@Override
	public void onDateSet(DatePicker view, int year, int month, int day)
	{
		mListener.onDialogPositiveClick(DatePickerDialogFragment.this, year, month, day);
	}
	
	
	private void handleExtras(Bundle extras)
	{
		if(extras.containsKey(EXTRA_DATE))
		{
			mDate = (Long) extras.get(EXTRA_DATE);
		}
	}
}
