package dev.jacob_ba.paintonscreen;

import static dev.jacob_ba.paintonscreen.MainActivity.paint_brush;
import static dev.jacob_ba.paintonscreen.MainActivity.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Display extends View {

    public static ArrayList<Path> pathList = new ArrayList<>();
    public static ArrayList<Integer> colorList = new ArrayList<>();
    public static ArrayList<Float> y_values = new ArrayList<>();
    public static ArrayList<Float> x_values = new ArrayList<>();
    public static ArrayList<Float> t_values = new ArrayList<>();
    public ViewGroup.LayoutParams params;
    public static int current_brush = Color.BLACK;
    public static boolean isFirstTime = true;
    private float firstTime;

    public Display(Context context) {
        super(context);
        init(context);
    }

    public Display(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Display(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        paint_brush.setAntiAlias(true);
        paint_brush.setColor(Color.BLACK);
        paint_brush.setStyle(Paint.Style.STROKE);
        paint_brush.setStrokeCap(Paint.Cap.ROUND);
        paint_brush.setStrokeJoin(Paint.Join.ROUND);
        paint_brush.setStrokeWidth(10f);

        params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float time = event.getEventTime();
        float x = (float) event.getX();
        float y = (float) event.getY();
        x_values.add(x);
        y_values.add(y);
        if (isFirstTime) {
            firstTime = time;
            isFirstTime = false;
        }
        time -= firstTime;
        t_values.add(time);
        Log.i("info", "Time: " + time);
        //Log.i("info", "x_values size: " + x_values.toString());
        //Log.i("info", "x_values size: " + x_values.size());
        //Log.i("info", "y_values size: " + y_values.toString());
        //Log.i("info", "y_values size: " + y_values.size());
        //Log.i("info", "x = " + x + ", y = " + y);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x, y);
                invalidate();
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x, y);
                pathList.add(path);
                colorList.add(current_brush);
                invalidate();
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < pathList.size(); i++) {
            paint_brush.setColor(colorList.get(i));
            canvas.drawPath(pathList.get(i), paint_brush);
            invalidate();
        }
    }
}
