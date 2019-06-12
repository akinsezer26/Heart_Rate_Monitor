package com.example.ozcan.heartrate;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    DS_Katmanı ds;
    ListView lss;
    List<Pulse_Deger> ps;

    DataPoint[] values,values_min,values_max;
    BarGraphSeries<DataPoint> series1;
    LineGraphSeries<DataPoint> series;
    LineGraphSeries<DataPoint> series2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grafik_layout);






        lss=(ListView)findViewById(R.id.lstgraph);

        ds=new DS_Katmanı(this);
        ds.open();

//        ds.addPulse(new Pulse_Deger("2018-02-05 22:45:00",45));
//        ds.addPulse(new Pulse_Deger("2018-02-06 22:45:00",90));
//        ds.addPulse(new Pulse_Deger("2018-02-07 22:45:00",55));
//        ds.addPulse(new Pulse_Deger("2018-02-08 22:45:00",33));
//        ds.addPulse(new Pulse_Deger("2018-02-10 22:45:00",45));
//        ds.addPulse(new Pulse_Deger("2018-02-11 22:45:00",69));
//        ds.addPulse(new Pulse_Deger("2018-02-12 22:45:00",45));
//        ds.addPulse(new Pulse_Deger("2018-02-13 22:45:00",70));
//        ds.addPulse(new Pulse_Deger("2018-02-14 22:45:00",33));
//        ds.addPulse(new Pulse_Deger("2018-02-15 22:45:00",22));
//        ds.addPulse(new Pulse_Deger("2018-02-16 22:45:00",10));
//        ds.addPulse(new Pulse_Deger("2018-02-17 22:45:00",90));
//        ds.addPulse(new Pulse_Deger("2018-02-18 22:45:00",50));

       // ds.addPulse(new Pulse_Deger("2018-05-18 22:45:00",120));

        ps=ds.listele("2018-02-18");
        generateDataForGraph();

        SatirAdaptor sa=new SatirAdaptor(this,ps);
        lss.setAdapter(sa);
        ds.close();

    }


    private void generateDataForGraph() {

        GraphView graph = (GraphView) findViewById(R.id.graph);
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);// It will remove the background grids
        graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);// remove horizontal x labels and line
        graph.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(120);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getGridLabelRenderer().setGridColor(Color.GREEN);
        graph.getGridLabelRenderer().setVerticalLabelsColor(Color.GREEN);
        graph.getGridLabelRenderer().setVerticalLabelsVisible(true);
        graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.GREEN);
//set static labels of y axis

        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    // show normal x values
                    return super.formatLabel(value, isValueX);
                } else {
                    if(value==120){return "max";}
                    else if(value==0){return "min";}
                    else if(value==60){return "middle";}
                    // show currency for y values
                    else return "   ";
                }
            }
        });






        int size = ps.size();
        values = new DataPoint[size];
        for (int i = 0; i < size; i++) {
            Integer xi;
            Integer yi;
            if (i == 0) {
                xi = i;
                yi = 0;
            } else {
                xi = i;
                yi = ps.get(i).getNabiz_deger();
            }

            DataPoint v = new DataPoint(xi, yi);
            values[i] = v;
        }
        series1 = new BarGraphSeries<>(values);
        graph.addSeries(series1);
        series1.setColor(Color.parseColor("#FA1A74"));
        series1.setSpacing(10);



        values_max = new DataPoint[size];
        for (int i = 0; i < size; i++) {
            Integer xi = i;
            Integer yi = 90;
                DataPoint v = new DataPoint(xi, yi);
            values_max[i] = v;
            }
        series=new LineGraphSeries<>(values_max);
        series.setColor(Color.parseColor("#66C5F5"));


        graph.addSeries(series);


        values_min = new DataPoint[size];
        for (int i = 0; i < size; i++) {
            Integer xi = i;
            Integer yi = 60;
            DataPoint v = new DataPoint(xi, yi);
            values_min[i] = v;
        }
        series2=new LineGraphSeries<>(values_min);
        series2.setColor(Color.parseColor("#66C5F5"));
        graph.addSeries(series2);

    }




}
