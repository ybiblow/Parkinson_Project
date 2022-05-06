package dev.jacob_ba.paintonscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.jtransforms.fft.DoubleFFT_1D;
import org.jtransforms.fft.FloatFFT_1D;

import java.util.ArrayList;
import java.util.Collections;

public class Graph extends AppCompatActivity {

    private LineChart mpLineChart;
    private ArrayList<ILineDataSet> dataSets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        mpLineChart = findViewById(R.id.line_chart);
        dataSets = new ArrayList<>();

        ArrayList<Entry> wave1 = xWave();
        LineDataSet lineDataSet1 = new LineDataSet(wave1, "xWave");
        lineDataSet1.setLineWidth(3);
        lineDataSet1.setColor(Color.RED);
        lineDataSet1.setDrawCircles(false);
        lineDataSet1.setValueTextSize(10);
        lineDataSet1.setValueTextColor(Color.RED);
        dataSets.add(lineDataSet1);

        ArrayList<Entry> wave2 = yWave();
        LineDataSet lineDataSet2 = new LineDataSet(wave2, "yWave");
        lineDataSet2.setLineWidth(3);
        lineDataSet2.setColor(Color.GREEN);
        lineDataSet2.setDrawCircles(false);
        lineDataSet2.setValueTextSize(10);
        lineDataSet2.setValueTextColor(Color.GREEN);
        dataSets.add(lineDataSet2);

        ArrayList<Entry> wave3 = rWave();
        LineDataSet lineDataSet3 = new LineDataSet(wave3, "rWave");
        lineDataSet3.setLineWidth(3);
        lineDataSet3.setColor(Color.BLUE);
        lineDataSet3.setDrawCircles(false);
        lineDataSet3.setValueTextSize(10);
        lineDataSet3.setValueTextColor(Color.BLUE);
        dataSets.add(lineDataSet3);

        ArrayList<Entry> wave4 = FFTWave();
        LineDataSet lineDataSet4 = new LineDataSet(wave4, "FFT-Wave");
        lineDataSet4.setLineWidth(3);
        lineDataSet4.setColor(Color.BLACK);
        lineDataSet4.setDrawCircles(false);
        lineDataSet4.setValueTextSize(10);
        lineDataSet4.setValueTextColor(Color.BLACK);
        dataSets.add(lineDataSet4);

        LineData data = new LineData(dataSets);
        mpLineChart.setData(data);
        mpLineChart.invalidate();
    }

    private ArrayList<Entry> FFTWave() {
        // Polar coordinates
        ArrayList<Entry> waveEntries = new ArrayList<>();

        // last element of t_values + 1 will be the size of the wave array
        int size_of_wave = Display.t_values.get(Display.t_values.size() - 1).intValue() + 1;
        // array of size "size_of_wave" and all elements are '0'
        ArrayList<Float> wave = new ArrayList<Float>(Collections.nCopies(size_of_wave, 0f));

        // input values of r into wave array in the proper index
        for (int i = 0; i < Display.t_values.size(); i++) {
            float x = Display.x_values.get(i);
            float y = Display.y_values.get(i);
            float r = (float) Math.sqrt((float) Math.pow(x, 2) + (float) Math.pow(y, 2));
            wave.set(Display.t_values.get(i).intValue(), r);
        }
        // sample and hold
        for (int i = 1; i < wave.size(); i++) {
            if (wave.get(i) == 0) {
                wave.set(i, wave.get(i - 1));
            }
        }
        // input values of wave array into the waveEntries
        for (int i = 0; i < wave.size(); i++) {
            Entry e = new Entry(i, wave.get(i));
            waveEntries.add(e);
        }

        float a[] = new float[wave.size()];
        for (int i = 0; i < wave.size(); i++) {
            a[i] = wave.get(i);
        }

//        Log.i("info", "" + array[0]);

        FloatFFT_1D fft = new FloatFFT_1D(size_of_wave);
        fft.realForward(a);

        int n = wave.size();

        float[] magnitude = new float[n / 2 + 1];

        ArrayList<Entry> waveEntries1 = new ArrayList<>();

        boolean isOdd = ((n % 2) == 1);
        if (isOdd) {
            // odd case
            magnitude[0] = Math.abs(a[0]);
            for (int i = 1; i < (n - 1) / 2; i++) {
                magnitude[i] = (float) Math.sqrt(a[2 * i] * a[2 * i] + a[2 * i + 1] * a[2 * i + 1]);
            }
            magnitude[(n - 1) / 2] = (float) Math.sqrt(a[n - 1] * a[n - 1] + a[1] * a[1]);
        } else {
            // even case
            magnitude[0] = Math.abs(a[0]);
            for (int i = 1; i < n / 2; i++) {
                magnitude[i] = (float) Math.sqrt(a[2 * i] * a[2 * i] + a[2 * i + 1] * a[2 * i + 1]);
            }
            magnitude[n / 2] = Math.abs(a[1]);
        }
        for (int i = 0; i < n / 2 + 1; i++) {
            Entry tmp = new Entry(i, magnitude[i]);
            waveEntries1.add(tmp);
        }
        Log.i("info", "Magnitudes length: " + magnitude.length);
        Log.i("Info", "Printing Magnitudes");
        for (int i = 0; i < 10; i++) {
            Log.i("Info", "Magnitude: " + magnitude[i]);
        }

        return waveEntries1;
    }

    private ArrayList<Entry> xWave() {
        ArrayList<Entry> waveEntries = new ArrayList<>();

        // last element of t_values + 1 will be the size of the wave array
        int size_of_wave = Display.t_values.get(Display.t_values.size() - 1).intValue() + 1;
        // array of size "size_of_wave" and all elements are '0'
        ArrayList<Integer> wave = new ArrayList<Integer>(Collections.nCopies(size_of_wave, 0));

        // input values of x_values into wave array in the proper index
        for (int i = 0; i < Display.t_values.size(); i++) {
            wave.set(Display.t_values.get(i).intValue(), Display.x_values.get(i).intValue());
        }
        // sample and hold
        for (int i = 1; i < wave.size(); i++) {
            if (wave.get(i) == 0) {
                wave.set(i, wave.get(i - 1));
            }
        }
        // input values of wave array into the waveEntries
        for (int i = 0; i < wave.size(); i++) {
            Entry e = new Entry(i, wave.get(i));
            waveEntries.add(e);
        }
        return waveEntries;
    }

    private ArrayList<Entry> yWave() {
        ArrayList<Entry> waveEntries = new ArrayList<>();

        // last element of t_values + 1 will be the size of the wave array
        int size_of_wave = Display.t_values.get(Display.t_values.size() - 1).intValue() + 1;
        // array of size "size_of_wave" and all elements are '0'
        ArrayList<Integer> wave = new ArrayList<Integer>(Collections.nCopies(size_of_wave, 0));

        // input values of y_values into wave array in the proper index
        for (int i = 0; i < Display.t_values.size(); i++) {
            wave.set(Display.t_values.get(i).intValue(), Display.y_values.get(i).intValue());
        }
        // sample and hold
        for (int i = 1; i < wave.size(); i++) {
            if (wave.get(i) == 0) {
                wave.set(i, wave.get(i - 1));
            }
        }
        // input values of wave array into the waveEntries
        for (int i = 0; i < wave.size(); i++) {
            Entry e = new Entry(i, wave.get(i));
            waveEntries.add(e);
        }
        return waveEntries;
    }

    private ArrayList<Entry> rWave() {
        // Polar coordinates
        ArrayList<Entry> waveEntries = new ArrayList<>();

        // last element of t_values + 1 will be the size of the wave array
        int size_of_wave = Display.t_values.get(Display.t_values.size() - 1).intValue() + 1;
        // array of size "size_of_wave" and all elements are '0'
        ArrayList<Float> wave = new ArrayList<Float>(Collections.nCopies(size_of_wave, 0f));

        // input values of r into wave array in the proper index
        for (int i = 0; i < Display.t_values.size(); i++) {
            float x = Display.x_values.get(i);
            float y = Display.y_values.get(i);
            float r = (float) Math.sqrt((float) Math.pow(x, 2) + (float) Math.pow(y, 2));
            wave.set(Display.t_values.get(i).intValue(), r);
        }
        // sample and hold
        for (int i = 1; i < wave.size(); i++) {
            if (wave.get(i) == 0) {
                wave.set(i, wave.get(i - 1));
            }
        }
        // input values of wave array into the waveEntries
        for (int i = 0; i < wave.size(); i++) {
            Entry e = new Entry(i, wave.get(i));
            waveEntries.add(e);
        }

        float array[] = new float[wave.size()];
        for (int i = 0; i < wave.size(); i++) {
            array[i] = wave.get(i);
        }
        return waveEntries;
    }
}