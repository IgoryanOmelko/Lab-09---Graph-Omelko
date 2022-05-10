package com.example.lab08_omelko;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;

import math.arr;//import our classes with helper functions
import math.interp;

public class MySurface extends SurfaceView {
    Paint p;//is a pen
    float xmin;//is a AABB (Axis Aligned Bounding Box)
    float xmax;
    float ymin;
    float ymax;
    float[] x;//x,y data points
    float[] y;
    int n;//number of points

    public void update() {//compute  min and max (aabb)
        xmin = arr.min(x, n);
        xmax = arr.max(x, n);
        ymin = arr.min(y, n);
        ymax = arr.max(y, n);
    }

    public MySurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        p = new Paint();// a pen to draw some line
        p.setColor(Color.RED);// line color is red
        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        int w = canvas.getWidth();//image dimensions w
        int h = canvas.getHeight();//image dimensions h
        float x0 = 0.0f, y0 = 0.0f;//Last point (need 2 point to draw a line)
        for (int i = 0; i < n; i++) {
            //transform xi, yi (in some units) from word space to screen space (in pixels)
            float x1 = interp.map(x[i], xmin, xmax, 0, w - 1);
            float y1 = interp.map(y[i], ymin, ymax, h - 1, 0);
            if (i > 0) {
                canvas.drawLine(x0, y0, x1, y1, p);
            }//can only draw after 1 iteration
            x0 = x1;//remember last point
            y0 = y1;
        }
        //super.onDraw(canvas);
    }
}
