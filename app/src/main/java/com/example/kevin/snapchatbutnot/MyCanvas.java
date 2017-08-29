package com.example.kevin.snapchatbutnot;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.HashMap;
import java.util.Stack;

/**
 * Created by Kevin on 6/22/2017.
 */

public class MyCanvas extends View {

    HashMap<Integer, Path> activePaths;

    Stack<Path> inactivePaths;
    Stack<Paint> inactivePathsPaint;

    Stack<Bitmap> icons;
    Stack<Float> xCoords;
    Stack<Float> yCoords;

    Paint paintChosen;

    Paint pathPaintRed;
    Paint pathPaintBlue;
    Paint pathPaintGreen;

    public MyCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        activePaths = new HashMap<>();

        inactivePaths = new Stack<>();
        inactivePathsPaint = new Stack<>();

        icons = new Stack<>();
        xCoords = new Stack<>();
        yCoords = new Stack<>();

        pathPaintRed = new Paint(Paint.ANTI_ALIAS_FLAG);
        pathPaintRed.setColor(Color.RED);
        pathPaintRed.setStyle(Paint.Style.STROKE);
        pathPaintRed.setStrokeWidth(40);

        pathPaintBlue = new Paint(Paint.ANTI_ALIAS_FLAG);
        pathPaintBlue.setColor(Color.BLUE);
        pathPaintBlue.setStyle(Paint.Style.STROKE);
        pathPaintBlue.setStrokeWidth(40);

        pathPaintGreen = new Paint(Paint.ANTI_ALIAS_FLAG);
        pathPaintGreen.setColor(Color.GREEN);
        pathPaintGreen.setStyle(Paint.Style.STROKE);
        pathPaintGreen.setStrokeWidth(40);

        paintChosen = pathPaintRed;
    }

    public void setPaintColor(int color) {

        if (color == Color.RED) {
            paintChosen = pathPaintRed;
        }
        if (color == Color.BLUE) {
            paintChosen = pathPaintBlue;
        }
        if (color == Color.GREEN) {
            paintChosen = pathPaintGreen;
        }
    }

    public void addPath(int id, float x, float y) {
        Path path = new Path();
        path.moveTo(x, y);
        activePaths.put(id, path);
        invalidate();
    }

    public void updatePath(int id, float x, float y) {
        Path path = activePaths.get(id);
        if (path != null) {
            path.lineTo(x, y);
        }
        invalidate();
    }

    public void removePath(int id) {
        if (activePaths.containsKey(id)) {
            Path path = activePaths.remove(id);
            inactivePaths.push(path);
            inactivePathsPaint.push(paintChosen);
        }
        invalidate();
    }

    public void undoPath() {
        if (!inactivePaths.empty() && !inactivePathsPaint.empty()) {
            inactivePaths.pop();
            inactivePathsPaint.pop();
        }
        invalidate();
    }

    public void clearPath() {
        while (!inactivePaths.empty() && !inactivePathsPaint.empty()) {
            inactivePaths.pop();
            inactivePathsPaint.pop();
        }
        invalidate();
    }

    public void longPress(float x, float y) {
        Bitmap ironMan = BitmapFactory.decodeResource(getResources(), R.drawable.iron_man_chest_piece);
        Bitmap resizedIronMan = Bitmap.createScaledBitmap(ironMan, 330, 330, false);
        icons.push(resizedIronMan);
        xCoords.push(x - (resizedIronMan.getWidth() / 2));
        yCoords.push(y - (resizedIronMan.getHeight() / 2));
        invalidate();
    }

    public void doubleTap(float x, float y) {
        Bitmap captainAmerica = BitmapFactory.decodeResource(getResources(), R.drawable.captain_america_shield);
        Bitmap resizedCaptainAmerica = Bitmap.createScaledBitmap(captainAmerica, 330, 330, false);
        icons.push(resizedCaptainAmerica);
        xCoords.push(x - (resizedCaptainAmerica.getWidth() / 2));
        yCoords.push(y - (resizedCaptainAmerica.getHeight() / 2));
        invalidate();
    }

    public void undoIcon() {
        if (!icons.empty() && !xCoords.empty() && !yCoords.empty()) {
            icons.pop();
            xCoords.pop();
            yCoords.pop();
        }
        invalidate();
    }

    public void clearIcons() {
        while (!icons.empty() && !xCoords.empty() && !yCoords.empty()) {
            icons.pop();
            xCoords.pop();
            yCoords.pop();
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Path path: activePaths.values()) {
            canvas.drawPath(path, paintChosen);
        }
        for (int i = 0; i < inactivePaths.size(); i++) {
            canvas.drawPath(inactivePaths.get(i), inactivePathsPaint.get(i));
        }

        for (int j = 0; j < icons.size(); j++) {
            canvas.drawBitmap(icons.get(j), xCoords.get(j), yCoords.get(j), null);
        }
    }
}
