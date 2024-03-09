
package com.example.ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

public class StatisitcsBarChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statisitcs_bar_chart);

        BarChart barChart = findViewById(R.id.barchart_graph);

        ArrayList<BarEntry> products = new ArrayList<>();

        EcommerceDataBase dbobj = new EcommerceDataBase(getApplicationContext());
        Cursor c = dbobj.Stats();
        Hashtable<String , Integer> productname_quantity = new Hashtable<>();
        while(!c.isAfterLast()){
            int proid = Integer.parseInt(c.getString(0));
            String productname = dbobj.BarChartFunction(proid);
            int quantity =  Integer.parseInt(c.getString(1));
            if(productname_quantity.containsKey(productname) == true){
                productname_quantity.put(productname,productname_quantity.get(productname)+1);
            }
            else{
                productname_quantity.put(productname , quantity);
            }
            c.moveToNext();
        }


        ArrayList<String> labels = new ArrayList<>();
        int x = 1;
        Set<String> keys = productname_quantity.keySet();
        for (String product_name: keys) {
            products.add(new BarEntry(x , productname_quantity.get(product_name)));
            x++;
            labels.add(product_name);
            if(x == 6) break;
        }


        BarDataSet barDataSet = new BarDataSet(products , "products");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);



        BarData barData = new BarData(barDataSet);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("Bar Chart Products");
        barChart.animateY(2000);
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
    }
}
