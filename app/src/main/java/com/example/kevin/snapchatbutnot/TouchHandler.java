package com.example.kevin.snapchatbutnot;

import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Kevin on 6/23/2017.
 */

public class TouchHandler implements View.OnTouchListener {

    private DrawActivity drawActivity;
    GestureDetectorCompat gestureDetectorCompat;

    public TouchHandler(DrawActivity drawActivity) {
        this.drawActivity = drawActivity;
        gestureDetectorCompat = new GestureDetectorCompat(this.drawActivity, new MyGestureListener());
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        gestureDetectorCompat.onTouchEvent(motionEvent);

        int maskAction = motionEvent.getActionMasked();
        switch (maskAction) {
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_DOWN:
                for (int i = 0, size = motionEvent.getPointerCount(); i < size; i++) {
                    int id = motionEvent.getPointerId(i);
                    drawActivity.addPath(id, motionEvent.getX(i), motionEvent.getY(i));
                }
                break;
            case MotionEvent.ACTION_MOVE:
                for (int i = 0, size = motionEvent.getPointerCount(); i < size; i++) {
                    int id = motionEvent.getPointerId(i);
                    drawActivity.updatePath(id, motionEvent.getX(i), motionEvent.getY(i));
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                for (int i = 0, size = motionEvent.getPointerCount(); i < size; i++) {
                    int id = motionEvent.getPointerId(i);
                    drawActivity.removePath(id);
                }
                break;
        }

        return true;
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);

            float xCoord = e.getX();
            float yCoord = e.getY();

            drawActivity.longPress(xCoord, yCoord);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            float xCoord = e.getX();
            float yCoord = e.getY();

            drawActivity.doubleTap(xCoord, yCoord);

            return super.onDoubleTap(e);
        }
    }
}
