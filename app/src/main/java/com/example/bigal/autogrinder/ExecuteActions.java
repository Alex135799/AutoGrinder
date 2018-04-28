package com.example.bigal.autogrinder;

import android.os.AsyncTask;
import android.widget.TextView;

import com.example.bigal.autogrinder.action.Action;

import java.util.List;

public class ExecuteActions extends AsyncTask<List<Action>, String, Boolean> {

    TextView textView;

    public ExecuteActions(TextView textView) {
        this.textView = textView;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected Boolean doInBackground(List<Action>... asyncActionLists) {
        Action previousAction = null;
        int num = 0;
        publishProgress("Starting...");
        for (Action action : asyncActionLists[0]) {
            if(previousAction != null) {
                try {
                    long timeDiff = action.getEventTime() - previousAction.getEventTime();
                    Thread.sleep(timeDiff);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (previousAction.getDownTime() != action.getDownTime()) {
                    num++;
                }
            }
            action.performAction();
            publishProgress(String.format("Performing Action %s: \n%s\n", num, action.toString()));
            previousAction = action;
        }

        return true;
    }

    @Override
    protected void onProgressUpdate(String... message) {
        textView.setText(message[0]);
    }

    @Override
    protected void onPostExecute(Boolean result) {
        textView.setText("End");
    }

    @Override
    protected void onCancelled() {

    }

}
