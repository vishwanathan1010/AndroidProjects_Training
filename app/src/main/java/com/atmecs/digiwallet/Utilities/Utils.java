package com.atmecs.digiwallet.Utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.atmecs.digiwallet.Activities.MainActivity;

public class Utils {
    View roorView;
    EditText phoneNumberEditText, passwordEditText;
    private Preferences preferences;
    //public ProgressDialog progress;

    //public static AppCompatActivity activity;

    /**
     * @param id
     * @param context to get string value as a part of context.
     * @return
     */
    public static String getStringValue(int id, Context context) {
        String value = context.getResources().getString(id);
        return value;
    }

    /**
     * to get alert dialog.
     *
     * @param context
     * @param positiveButtonText
     * @param negativeButtonText
     * @param alertDialogTitle
     */
    public void getAlertDialog(final Context context, String positiveButtonText, String negativeButtonText, String alertDialogTitle) {

        // phoneNumberEditText = rootView.findViewById(R.id.phone_number);
        // passwordEditText = rootView.findViewById(R.id.password);

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        preferences = new Preferences(context);
                        preferences.OnLogout();
                        Intent intent=new Intent(context, MainActivity.class);
                        context.startActivity(intent);

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(alertDialogTitle).setPositiveButton(positiveButtonText, dialogClickListener)
                .setNegativeButton(negativeButtonText, dialogClickListener).show();
    }

    public void getCheckAlertDialog(final Context context, String positiveButtonText, String alertDialogTitle) {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(alertDialogTitle).setPositiveButton(positiveButtonText, dialogClickListener).show();
    }

    public void getForgotPasswordDialog(final Context context, final FragmentManager fragmentManager,
                                        final Fragment fragment, final int replaceFrameId, String positiveButtonText,
                                        String alertDialogTitle) {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        fragmentManager.beginTransaction().replace(replaceFrameId, fragment).commit();
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(alertDialogTitle).setPositiveButton(positiveButtonText, dialogClickListener).show();
    }

    public void progressBar(Context context, String message) {
        final ProgressDialog progress;
        progress = new ProgressDialog(context);
        progress.setMessage(message);
        progress.setCancelable(true);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                progress.dismiss();
            }
        }).start();
    }
}