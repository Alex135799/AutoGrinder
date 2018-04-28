package com.example.bigal.autogrinder.action;

import android.hardware.input.InputManager;
import android.view.InputEvent;
import android.view.MotionEvent;

import com.example.bigal.autogrinder.exceptions.InputManagerException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public abstract class Action {

    @Getter
    @Setter
    private float startX = 0;

    @Getter
    @Setter
    private float endX = 0;

    @Getter
    @Setter
    private float startY = 0;

    @Getter
    @Setter
    private float endY = 0;

    @Getter
    @Setter
    private long downTime;

    @Getter
    @Setter
    private long eventTime;

    @Getter
    @Setter
    private int metaState;

    @Getter
    @Setter
    private int motionEventNum;

    private Method injectInputEventMethod;
    private InputManager inputManager;

    public Action() {
        initInputManager();
    };

    public Action(float startX, float endX, float startY, float endY,
                  long downTime, long eventTime, int metaState, int motionEventNum) {
        this.startX = startX;
        this.endX = endX;
        this.startY = startY;
        this.endY = endY;
        this.downTime = downTime;
        this.eventTime = eventTime;
        this.metaState = metaState;
        this.motionEventNum = motionEventNum;
        initInputManager();
    }

    public abstract MotionEvent buildEvent();

    public abstract void performAction();

    protected void invokeAction(MotionEvent event){
        try {
            injectInputEventMethod.invoke(inputManager, new Object[]{event, Integer.valueOf(0)});
        }
        catch(InvocationTargetException | IllegalAccessException ex) {
            throw new InputManagerException("Could not invoke injectInputEventMethod. " + ex.getMessage(), ex);
        }
    }

    private void initInputManager() {
        String methodName = "getInstance";
        Object[] objArr = new Object[0];
        try {
            inputManager = (InputManager) InputManager.class.getDeclaredMethod(methodName, new Class[0])
                    .invoke(null, objArr);

            //Make MotionEvent.obtain() method accessible
            methodName = "obtain";
            MotionEvent.class.getDeclaredMethod(methodName, new Class[0]).setAccessible(true);

            //Get the reference to injectInputEvent method
            methodName = "injectInputEvent";
            injectInputEventMethod = InputManager.class.getMethod(methodName, new Class[]{InputEvent.class, Integer.TYPE});
        }
        catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
            throw new InputManagerException("Could not initalize injectInputEventMethod. " + ex.getMessage(), ex);
        }
    }
}
