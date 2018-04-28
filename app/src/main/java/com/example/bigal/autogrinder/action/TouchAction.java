package com.example.bigal.autogrinder.action;

import android.os.SystemClock;
import android.support.v4.view.InputDeviceCompat;
import android.view.MotionEvent;

public class TouchAction extends Action {

    public TouchAction(float startX, float startY, long downTime, long eventTime,
                       int motionEventNum, int metaState) {
        super(startX, 0.0f, startY, 0.0f, downTime, eventTime, metaState, motionEventNum);
    }

    @Override
    public MotionEvent buildEvent() {
        long now = SystemClock.uptimeMillis();
        long timeDiff = getEventTime() - getDownTime();
        MotionEvent event =  MotionEvent.obtain(
                now,
                now + timeDiff,
                getMotionEventNum(),
                getStartX(),
                getStartY(),
                getMetaState()
        );
        event.setSource(InputDeviceCompat.SOURCE_TOUCHSCREEN);
        return event;
    }

    @Override
    public void performAction() {
        super.invokeAction(buildEvent());
    }
}
