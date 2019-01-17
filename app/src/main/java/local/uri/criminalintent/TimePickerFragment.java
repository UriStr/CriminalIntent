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

    private static final String ARG_TIME = "time";
    public static final String EXTRA_TIME = "local.uri.criminalinent.time";


    public static TimePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TIME, date);

        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Date date = (Date) getArguments().getSerializable(ARG_TIME);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_time, null);

        mTimePicker = v.findViewById(R.id.dialog_time_time_picker);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mTimePicker.setCurrentHour(hours);
            mTimePicker.setCurrentMinute(minutes);
        } else {
            mTimePicker.setHour(hours);
            mTimePicker.setMinute(minutes);
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

                        int[] time = {hours, minutes};

                        sendResult(Activity.RESULT_OK, time);

                    }
                })
                .create();
    }

    private void sendResult(int resultCode, int[] time) {
        if (getTargetFragment() == null) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_TIME, time);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
