package vn.edu.usth.coronatracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.coronatracker.adapter.SliderAdapter;
import vn.edu.usth.coronatracker.model.CoronaModel;
import vn.edu.usth.coronatracker.model.SymptomModel;
import vn.edu.usth.coronatracker.services.RetrofitClient;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "CORONA MAIN ACTIVITY";
    CoronaModel result;

    private TextView cases, todayCases;
    private TextView active;
    private TextView recovered, todayRecovered;
    private TextView deaths, todayDeaths;
    private CardView country;
    private PieChart pieChart;
    private SliderAdapter sliderAdapter;
    private SliderView sliderView;
    int[] images = {R.drawable.cough, R.drawable.pain, R.drawable.fever};

    private List<SymptomModel> symptomLists = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addSymptoms();
        initView();
        fetchData();
        setupPieChart();
//        loadPieChartData();

        country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CountryActivity.class);
                startActivity(intent);
            }
        });

        SliderAdapter sliderAdapter = new SliderAdapter(getApplicationContext(), symptomLists);

        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();

    }

    private void fetchData() {
        Call<CoronaModel> call = RetrofitClient.getInstance().getMyApi().getWorldCorona();
        call.enqueue(new Callback<CoronaModel>() {
            @Override
            public void onResponse(Call<CoronaModel> call, Response<CoronaModel> response) {
                if (!response.isSuccessful()) {
                    Log.i(TAG, "Error");
                } else {
                    if (response.body() != null) {
                        result = response.body();
                        cases.setText(result.getCases() + " cases");
                        active.setText(result.getActive() + " cases");
                        deaths.setText(result.getDeaths() + " cases");
                        recovered.setText(result.getRecovered() + " cases");
                        todayCases.setText("+ " + result.getTodayCases() + " cases");
                        todayRecovered.setText("+ " + result.getTodayRecovered() + " cases");
                        todayDeaths.setText("+ " + result.getTodayDeaths() + " cases");
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

//        Legend l = pieChart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//        l.setOrientation(Legend.LegendOrientation.VERTICAL);


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
        entries.add(new PieEntry(Float.parseFloat(result.getDeaths()), "Deaths"));
        entries.add(new PieEntry(Float.parseFloat(result.getCases()), "Cases"));
        entries.add(new PieEntry(Float.parseFloat(result.getRecovered()), "Recovered"));
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
        recovered = findViewById(R.id.recovered);
        todayRecovered = findViewById(R.id.recoveredToday);
        deaths = findViewById(R.id.deaths);
        todayDeaths = findViewById(R.id.deathsToday);
        country = findViewById(R.id.country_card);
        pieChart = findViewById(R.id.piechart);
        sliderView = findViewById(R.id.image_slider);
    }

    private void addSymptoms() {
        symptomLists.add(new SymptomModel("Dry cough", "Lorem Ipsum is simply dummy text of the printing and typesetting industry", images[0]));
        symptomLists.add(new SymptomModel("Pain", "Lorem Ipsum is simply dummy text of the printing and typesetting industry", images[1]));
        symptomLists.add(new SymptomModel("Fever", "Lorem Ipsum is simply dummy text of the printing and typesetting industry", images[2]));
    }
}