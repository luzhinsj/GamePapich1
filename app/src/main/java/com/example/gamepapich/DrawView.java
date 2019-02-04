package com.example.gamepapich;

import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

public class DrawView extends SurfaceView implements SurfaceHolder.Callback {
    private DrawThread drawThread;
    public DrawView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread(getContext(),getHolder());
        drawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        drawThread.requestStop();
        boolean retry = true;
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {
                //
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        drawThread.setTowardPoint((int)event.getX(),(int)event.getY());
        return false;
    }
}
