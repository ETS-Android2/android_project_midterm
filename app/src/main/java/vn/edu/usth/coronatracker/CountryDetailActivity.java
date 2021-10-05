package vn.edu.usth.coronatracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
import vn.edu.usth.coronatracker.model.Country;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class CountryDetailActivity extends AppCompatActivity {
    private static final String TAG = "CORONA DETAIL ACTIVITY";
    private Country resultCountry;

    private TextView cases, todayCases;
    private TextView active;
    private TextView recovered, todayRecovered;
    private TextView deaths, todayDeaths;
    private TextView countryName;
    private CardView country;
    private PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_detail);
        initView();
        setupPieChart();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            resultCountry = (Country) getIntent().getSerializableExtra("countryDetail");
            Log.i(TAG, resultCountry.toString());
        } else {
            Log.i(TAG, "Error somewhere");
        }


        cases.setText(withLargeIntegers(Double.parseDouble(resultCountry.getCases())));
        active.setText(withLargeIntegers(Double.parseDouble(resultCountry.getActive())));
        deaths.setText(withLargeIntegers(Double.parseDouble(resultCountry.getDeaths())));
        recovered.setText(withLargeIntegers(Double.parseDouble(resultCountry.getRecovered())));
        todayCases.setText("+ " + withLargeIntegers(Double.parseDouble(resultCountry.getTodayCases())) + " cases");
        todayRecovered.setText("+ " + withLargeIntegers(Double.parseDouble(resultCountry.getTodayRecovered())) + " cases");
        todayDeaths.setText("+ " + withLargeIntegers(Double.parseDouble(resultCountry.getTodayDeaths())) + " cases");
        countryName.setText(resultCountry.getCountry());
        loadPieChartData(resultCountry);
    }

    public static String withLargeIntegers(double value) {
        DecimalFormat df = new DecimalFormat("###,###,###");
        return df.format(value);
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
        countryName = findViewById(R.id.country_name);
        pieChart = findViewById(R.id.piechart);
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

        Legend l = pieChart.getLegend();
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setTextSize(12);
        l.setFormSize(20);
        l.setFormToTextSpace(2);
        l.setDrawInside(false);
        l.setEnabled(true);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

    }

    private void loadPieChartData(Country result) {
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

//    private void fetchData(Country country){
//        String country_name = country.getCountry();
//        String url = "https://disease.sh/v3/covid-19/vaccine/coverage/countries/{country_name}";
//        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//
//            }
//        });
//    }

}