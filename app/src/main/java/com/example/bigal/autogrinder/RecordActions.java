package com.example.bigal.autogrinder;

import android.view.MotionEvent;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

public class RecordActions {

    public static final String SAVE_ACTIONS_PATH = "/path/to/save";
    private boolean isRecording;
    private TextView textView;
    private List<MotionEvent> motionEventList;

    public RecordActions(TextView textView) {
        motionEventList = new LinkedList<>();
        this.textView = textView;
    }

    public boolean isRecording() {
        return isRecording;
    }

    public void handleAction(MotionEvent event) {
        if(isRecording) {
            logAction(event);
        }
    }

    public void startRecording() {
        isRecording = true;
    }

    public void stopRecording() {
        isRecording = false;

        saveActions();
    }

    private void logAction(MotionEvent event) {
        motionEventList.add(event);

        textView.setText(String.format("X: %f, Y: %f",event.getX(), event.getY()));
    }

    private void saveActions() {
        String retString = "";
        for (MotionEvent event : motionEventList) {
            retString += String.format("X: %f, Y: %f%n",event.getX(), event.getY());
        }
        textView.setText(retString);
    }
}
