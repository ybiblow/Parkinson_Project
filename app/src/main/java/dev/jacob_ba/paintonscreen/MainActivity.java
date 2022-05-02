package dev.jacob_ba.paintonscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static Path path = new Path();
    public static Paint paint_brush = new Paint();
    Button button_pencil;
    Button button_eraser;
    Button button_black;
    Button button_green;
    Button button_blue;
    Button button_yellow;
    Button button_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button_pencil = findViewById(R.id.button_pencil);
        button_eraser = findViewById(R.id.button_eraser);
        button_black = findViewById(R.id.button_black);
        button_green = findViewById(R.id.button_green);
        button_blue = findViewById(R.id.button_blue);
        button_yellow = findViewById(R.id.button_yellow);
        button_check = findViewById(R.id.button_check);
        setOnClicks();

    }

    private void setOnClicks() {

        button_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("info", "Checked Clicked! size of x array is: " + Display.x_values.size());
                Log.i("info", "Size of x array is: " + Display.x_values.size());
                Log.i("info", "Size of y array is: " + Display.y_values.size());
                Log.i("info", "Size of t array is: " + Display.t_values.size());
                Log.i("info", "last time is: " + Display.t_values.get(Display.t_values.size() - 1));
                Intent intent = new Intent(getApplicationContext(), Graph.class);
                startActivity(intent);
            }
        });

        button_pencil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("info", "Pencil clicked!");
                paint_brush.setColor(Color.BLACK);
                setBrushCurrentColor(paint_brush.getColor());
            }
        });

        button_eraser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("info", "Eraser clicked!");
                Display.isFirstTime = true;
                Display.pathList.clear();
                Display.colorList.clear();
                Display.x_values.clear();
                Display.y_values.clear();
                Display.t_values.clear();
                path.reset();
            }
        });

        button_black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("info", "Black clicked!");
                paint_brush.setColor(Color.BLACK);
                setBrushCurrentColor(paint_brush.getColor());
            }
        });

        button_green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("info", "Green clicked!");
                int color = getColor(R.color.green_800);
                paint_brush.setColor(color);
                setBrushCurrentColor(paint_brush.getColor());
            }
        });

        button_blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("info", "Blue clicked!");
                int color = getColor(R.color.blue_800);
                paint_brush.setColor(color);
                setBrushCurrentColor(paint_brush.getColor());
            }
        });

        button_yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("info", "Yellow clicked!");
                int color = getColor(R.color.yellow_800);
                paint_brush.setColor(color);
                setBrushCurrentColor(paint_brush.getColor());
            }
        });
    }

    public void setBrushCurrentColor(int color) {
        Display.current_brush = color;
        path = new Path();
    }
}