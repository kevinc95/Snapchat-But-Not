package com.example.kevin.snapchatbutnot;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DrawActivity extends AppCompatActivity implements View.OnClickListener {

    MyCanvas myCanvas;
    TouchHandler touchHandler;
    MotionController motionController;

    Button red;
    Button blue;
    Button green;
    Button undo;
    Button clear;
    Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        myCanvas = (MyCanvas) findViewById(R.id.myCanvas);
        touchHandler = new TouchHandler(this);
        motionController = new MotionController(this);
        motionController.register();
        myCanvas.setOnTouchListener(touchHandler);

        Intent intent = getIntent();
        Bitmap thumbnail = intent.getParcelableExtra(MainActivity.THUMBNAIL_TAKEN);

        myCanvas.setBackground(new BitmapDrawable(getResources(), thumbnail));

        red = (Button) findViewById(R.id.red);
        blue = (Button) findViewById(R.id.blue);
        green = (Button) findViewById(R.id.green);
        undo = (Button) findViewById(R.id.undo);
        clear = (Button) findViewById(R.id.clear);
        done = (Button) findViewById(R.id.done);

        red.setOnClickListener(this);
        blue.setOnClickListener(this);
        green.setOnClickListener(this);
        undo.setOnClickListener(this);
        clear.setOnClickListener(this);
        done.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        motionController.unregister();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == red.getId()) {
            myCanvas.setPaintColor(Color.RED);
        }
        if (view.getId() == blue.getId()) {
            myCanvas.setPaintColor(Color.BLUE);
        }
        if (view.getId() == green.getId()) {
            myCanvas.setPaintColor(Color.GREEN);
        }
        if (view.getId() == undo.getId()) {
            myCanvas.undoPath();
        }
        if (view.getId() == clear.getId()) {
            myCanvas.clearPath();
        }
        if (view.getId() == done.getId()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void longPress(float x, float y) {
        myCanvas.longPress(x, y);
    }

    public void doubleTap(float x, float y) {
        myCanvas.doubleTap(x, y);
    }

    public void undoIcon() {
        myCanvas.undoIcon();
    }

    public void clearIcons() {
        myCanvas.clearIcons();
    }

    public void addPath(int id, float x, float y) {
        myCanvas.addPath(id, x, y);
    }

    public void updatePath(int id, float x, float y) {
        myCanvas.updatePath(id, x, y);
    }

    public void removePath(int id) {
        myCanvas.removePath(id);
    }
}
