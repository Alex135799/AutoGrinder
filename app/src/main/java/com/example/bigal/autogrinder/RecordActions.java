package com.example.bigal.autogrinder;

import android.view.MotionEvent;
import android.widget.TextView;

import com.example.bigal.autogrinder.action.Action;
import com.example.bigal.autogrinder.action.TouchAction;

import java.util.LinkedList;
import java.util.List;

public class RecordActions {

    private boolean isRecording = false;
    private boolean isExecuting = false;
    private TextView textView;
    private List<Action> actionList;

    public RecordActions(TextView textView) {
        actionList = new LinkedList<>();
        this.textView = textView;
    }

    public void handleAction(MotionEvent event) {
        if(isRecording && !isExecuting) {
            logAction(event);
        }
    }

    public void toggleRecording() {
        if (!isExecuting && isRecording) {
            isRecording = false;

            saveActions();
        }
        else if (!isExecuting && !isRecording){
            textView.setText("Recording...");
            isRecording = true;
        }
        else if (isExecuting) {
            isRecording = false;
        }
    }

    private void logAction(MotionEvent event) {
        Action action = new TouchAction(event.getX(), event.getY(),
                event.getDownTime(), event.getEventTime(), event.getAction(),
                event.getMetaState());
        actionList.add(action);
        textView.setText(action.toString());
    }

    private void saveActions() {
        cleanActionList();
        new ExecuteActions(textView).execute(actionList);
        actionList = new LinkedList<>();
    }

    private void cleanActionList() {
        List<Action> tmpList = new LinkedList<>();
        long lastDownTime = actionList.get(actionList.size()-1).getDownTime();
        for(Action action : actionList) {
            // Take out last action, as it was just to stop recording
            if(action.getDownTime() != lastDownTime) {
                tmpList.add(action);
            }
        }
        actionList = tmpList;
    }
}
