package vn.edu.usth.coronatracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;


import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.coronatracker.model.CoronaModel;
import vn.edu.usth.coronatracker.services.CoronaApi;
import vn.edu.usth.coronatracker.services.RetrofitClient;

public class GlobalActivity extends AppCompatActivity {
    private static final String TAG = "CORONA MAIN ACTIVITY";
    CoronaModel result;

    private TextView cases, todayCases;
    private TextView active, todayActive;
    private TextView recovered, todayRecovered;
    private TextView deaths, todayDeaths;
//    private ImageView imageMap;
    private RelativeLayout country;
    private PieChart pieChart;
    private int active_case;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        fetchData();
//        setDateTextView();
        setupPieChart();
//        loadPieChartData();


        country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GlobalActivity.this, CountryActivity.class);
                startActivity(intent);
            }
        });

//        imageMap.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
//                startActivity(intent);
//            }
//        });




    }

    public static String withLargeIntegers(double value) {
        DecimalFormat df = new DecimalFormat("###,###,###");
        return df.format(value);
    }

    private void fetchData() {
        CoronaApi api = RetrofitClient.getClient().create(CoronaApi.class);
//        Call<CoronaModel> call = RetrofitClient.getInstance().getMyApi().getWorldCorona();
        Call<CoronaModel> call = api.getWorldCorona();
        call.enqueue(new Callback<CoronaModel>() {
            @Override
            public void onResponse(Call<CoronaModel> call, Response<CoronaModel> response) {
                if (!response.isSuccessful()) {
                    Log.i(TAG, "Error");
                } else {
                    if (response.body() != null) {
                        result = response.body();
                        cases.setText(withLargeIntegers(Double.parseDouble(result.getCases())));
                        active.setText(withLargeIntegers(Double.parseDouble(result.getActive())));
                        deaths.setText(withLargeIntegers(Double.parseDouble(result.getDeaths())));
                        recovered.setText(withLargeIntegers(Double.parseDouble(result.getRecovered())));
                        todayCases.setText("+ " + withLargeIntegers(Double.parseDouble(result.getTodayCases())) + " cases");
                        todayRecovered.setText("+ " + withLargeIntegers(Double.parseDouble(result.getTodayRecovered())) + " cases");
                        todayDeaths.setText("+ " + withLargeIntegers(Double.parseDouble(result.getTodayDeaths())) + " cases");
                        active_case = (Integer.parseInt(result.getTodayCases()) - Integer.parseInt(result.getTodayRecovered()) - Integer.parseInt(result.getTodayDeaths()));
                        if (active_case < 0) {
                            todayActive.setText("- " + withLargeIntegers(active_case * -1));
                        } else {
                            todayActive.setText("+ " + withLargeIntegers(active_case));
                        }

                        loadPieChartData(result);
                        Log.i(TAG, result.toString());

                    } else {
                        Log.i(TAG, "NULL");
                    }
                }

            }

            @Override
            public void onFailure(Call<CoronaModel> call, Throwable t) {
                Log.d(TAG, "onFailure: " + call.toString() + t.getMessage());
            }

        });


    }

//    private void setDateTextView() {
//        TextView textView = findViewById(R.id.date);
//        SimpleDateFormat sdf = new SimpleDateFormat("'As of' HH'h'mm, dd/MM/yyyy");
//        String currentDateandTime = sdf.format(new Date());
//        textView.setText(currentDateandTime);
//    }

    private void setupPieChart() {
        pieChart.setTransparentCircleRadius(45);
        pieChart.setCenterText("Covid cases chart \n ");
        pieChart.setTouchEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);
        pieChart.setDrawEntryLabels(false);
        pieChart.setExtraOffsets(25.f, 0.f, 25.f, 0.f);
        pieChart.setContentDescription("");
        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);
        pieChart.setEntryLabelTextSize(10);
        pieChart.setHoleRadius(75);
        pieChart.animateXY(1700, 1700);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.getDescription().setEnabled(false);

        Legend l = pieChart.getLegend();
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setTextSize(12);
        l.setFormSize(20);
        l.setFormToTextSpace(2);
        l.setDrawInside(false);
        l.setEnabled(true);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);


    }

    private void loadPieChartData(CoronaModel result) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(Float.parseFloat(result.getRecovered()), "Recovered"));
        entries.add(new PieEntry(Float.parseFloat(result.getCases()), "Cases"));
        entries.add(new PieEntry(Float.parseFloat(result.getDeaths()), "Deaths"));
        entries.add(new PieEntry(Float.parseFloat(result.getActive()), "Active"));

        ArrayList<Integer> colors = new ArrayList<>();
        for (int c : ColorTemplate.MATERIAL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        PieDataSet dataset = new PieDataSet(entries, "");
        dataset.setColors(colors);
        dataset.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataset.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataset.setValueTextColor(Color.BLACK);
        dataset.setSliceSpace(3f);
        dataset.setSelectionShift(2f);
        dataset.setValueLinePart1Length(0.2f); /** When valuePosition is OutsideSlice, indicates length of first half of the line */
        dataset.setValueLinePart2Length(0.2f); /** When valuePosition is OutsideSlice, indicates length of second half of the line */
        dataset.setValueLinePart1OffsetPercentage(100f); //starting of the line from center of the chart offset
        PieData data = new PieData(dataset);
        data.setDrawValues(true);
        data.setValueTextSize(10f);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextColor(Color.BLACK);
        pieChart.setData(data);
        pieChart.setUsePercentValues(true);
        pieChart.invalidate();


    }

    private void initView() {
        cases = findViewById(R.id.cases);
        todayCases = findViewById(R.id.casesToday);
        active = findViewById(R.id.active);
        todayActive = findViewById(R.id.active_new);
        recovered = findViewById(R.id.recovered);
        todayRecovered = findViewById(R.id.recoveredToday);
        deaths = findViewById(R.id.deaths);
        todayDeaths = findViewById(R.id.deathsToday);
        country = findViewById(R.id.country_card);
        pieChart = findViewById(R.id.piechart);
    }

}