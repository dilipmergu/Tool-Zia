package com.e.toolsharing.adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

public final class JSSEProvider extends AsyncTask {

    private ProgressDialog statusDialog;
    private Activity sendMailActivity;

    public JSSEProvider(Activity activity) {
        sendMailActivity = activity;

    }

    protected void onPreExecute() {
        statusDialog = new ProgressDialog(sendMailActivity);
        statusDialog.setMessage("Getting ready...");
        statusDialog.setIndeterminate(false);
        statusDialog.setCancelable(false);
        statusDialog.show();
    }

    @Override
    protected Object doInBackground(Object... args) {
        try {
            publishProgress("Processing input....");
            GMailSender androidEmail = new GMailSender(args[0].toString(),
                    args[1].toString(), (List) args[2], args[3].toString(),
                    args[4].toString());
            publishProgress("Preparing mail message....");
            androidEmail.createEmailMessage();
            publishProgress("Sending email....");
            androidEmail.sendEmail();
            publishProgress("Email Sent.");
            statusDialog.setMessage("Email Sent");
            statusDialog.show();
            statusDialog.cancel();
        } catch (Exception e) {
            publishProgress(e.getMessage());
            Log.e("SendMailTask", e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void onProgressUpdate(Object... values) {
        statusDialog.setMessage(values[0].toString());

    }

    @Override
    public void onPostExecute(Object result) {
        statusDialog.dismiss();
    }

}