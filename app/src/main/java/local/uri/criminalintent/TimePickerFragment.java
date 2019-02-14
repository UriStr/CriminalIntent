package local.uri.criminalintent;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

public class TimePickerFragment extends DialogFragment {

    private TimePicker mTimePicker;

    private static final String ARG_DATE = "time";
    private static String argumentNameForDataFromDatePicker;


    public static TimePickerFragment newInstance(Date date, String argumentNameForData) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);

        argumentNameForDataFromDatePicker = argumentNameForData;

        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Date date = (Date) getArguments().getSerializable(ARG_DATE);
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_time, null);

        mTimePicker = v.findViewById(R.id.dialog_time_time_picker);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            mTimePicker.setHour(hours);
            mTimePicker.setMinute(minutes);
        } else {
            mTimePicker.setCurrentHour(hours);
            mTimePicker.setCurrentMinute(minutes);
        }

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.choose_time)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int hours, minutes;
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                            hours = mTimePicker.getCurrentHour();
                            minutes = mTimePicker.getCurrentMinute();
                        } else {
                            hours = mTimePicker.getHour();
                            minutes = mTimePicker.getMinute();
                        }

                        calendar.set(Calendar.HOUR_OF_DAY, hours);
                        calendar.set(Calendar.MINUTE, minutes);
                        date.setTime(calendar.getTimeInMillis());

                        sendResult(Activity.RESULT_OK, date);
                    }
                })
                .create();
    }

    private void sendResult(int resultCode, Date date) {
        if (getTargetFragment() == null) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(argumentNameForDataFromDatePicker, date);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}