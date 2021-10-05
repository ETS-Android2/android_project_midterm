package vn.edu.usth.coronatracker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import vn.edu.usth.coronatracker.model.CountryModel;
import vn.edu.usth.coronatracker.utils.ClaimsXAxisValueFormatter;
import vn.edu.usth.coronatracker.utils.MyMarkerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class CountryDetailActivity extends AppCompatActivity {
    private static final String TAG = "CORONA DETAIL ACTIVITY";
    private CountryModel resultCountry;

    private TextView cases, todayCases;
    private TextView active, todayActive;
    private TextView recovered, todayRecovered;
    private TextView deaths, todayDeaths;
    private TextView countryName;
    private CardView country;
    private PieChart pieChart;
    private List<String> sortedDays = new ArrayList<>();
    private List<Date> days = new ArrayList<>();
    private List<Integer> vaccineLists = new ArrayList<>();
    private LineChart lineChart;
    private LineData lineData;
    private LineDataSet lineDataSet;

    private int active_case;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_detail);
        initView();
        setupPieChart();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            resultCountry = (CountryModel) getIntent().getSerializableExtra("countryDetail");
            Log.i(TAG, resultCountry.toString());
        } else {
            Log.i(TAG, "Error somewhere");
        }


        cases.setText(resultCountry.getCases());
        active.setText(resultCountry.getActive());
        deaths.setText(resultCountry.getDeaths());
        recovered.setText(resultCountry.getRecovered());
        todayCases.setText("+ " + resultCountry.getTodayCases());
        todayRecovered.setText("+ " + resultCountry.getTodayRecovered());
        todayDeaths.setText("+ " + resultCountry.getTodayDeaths());
        countryName.setText(resultCountry.getCountry());
        active_case = (Integer.parseInt(resultCountry.getTodayCases()) - Integer.parseInt(resultCountry.getTodayRecovered()) - Integer.parseInt(resultCountry.getTodayDeaths()));
        if (active_case < 0) {
            todayActive.setText("- " + String.valueOf(active_case * -1));
        } else {
            todayActive.setText("+ " + String.valueOf(active_case));
        }
        loadPieChartData(resultCountry);
        fetchData(resultCountry);
    }

    private void initView() {
        cases = findViewById(R.id.cases);
        todayCases = findViewById(R.id.casesToday);
        active = findViewById(R.id.active);
        recovered = findViewById(R.id.recovered);
        todayRecovered = findViewById(R.id.recoveredToday);
        deaths = findViewById(R.id.deaths);
        todayDeaths = findViewById(R.id.deathsToday);
        todayActive = findViewById(R.id.active_new);
        country = findViewById(R.id.country_card);
        countryName = findViewById(R.id.country_name);
        pieChart = findViewById(R.id.piechart);
        lineChart = findViewById(R.id.line_chart);
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

    private void loadPieChartData(CountryModel result) {
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

    private void fetchData(CountryModel country) {
        RequestQueue queue = Volley.newRequestQueue(CountryDetailActivity.this);
        String country_name = country.getCountry();
        HashMap<String, Object> map = new HashMap<String, Object>();
        String url = String.format("https://disease.sh/v3/covid-19/vaccine/coverage/countries/%s", country_name);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject timeline = response.getJSONObject("timeline");
                            Log.i(TAG, timeline.toString());
                            for (Iterator<String> it = timeline.keys(); it.hasNext(); ) {
                                String key = it.next();
                                map.put(key, timeline.get(key));
                            }
                            for (String key : map.keySet()) {
                                Log.i(TAG, key + " Hello: " + map.get(key));
                                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
                                Date date = new Date();
                                try {
                                    date = sdf.parse(key);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                Log.i(TAG, date.toString());
                                days.add(date);
                                vaccineLists.add((Integer) map.get(key));
                            }

                            renderData();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CountryDetailActivity.this, "Fail to get data..", Toast.LENGTH_SHORT).show();

                    }
                });
        queue.add(jsonObjectRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void renderData() {
        Collections.sort(vaccineLists);
        List<Date> sorted = days.stream().sorted(Comparator.comparingLong(Date::getTime))
                .collect(Collectors.toList());

        for (int i = 1; i <= 6; i++) {
            SimpleDateFormat sm = new SimpleDateFormat("MM-dd-yy");
            String strDate = sm.format(sorted.get(i * 5 - 1));
            sortedDays.add(strDate);

        }

//        for (int i = 0; i < sortedDays.size(); i++) {
//            Log.i(TAG, sortedDays.get(i));
//        }

        List<Entry> lineEntries = new ArrayList<>();

        lineEntries.add(new Entry(0.0f, vaccineLists.get(0)));
        lineEntries.add(new Entry(1.0f, vaccineLists.get(5)));
        lineEntries.add(new Entry(2.0f, vaccineLists.get(10)));
        lineEntries.add(new Entry(3.0f, vaccineLists.get(15)));
        lineEntries.add(new Entry(4.0f, vaccineLists.get(20)));
        lineEntries.add(new Entry(5.0f, vaccineLists.get(25)));


        lineDataSet = new LineDataSet(lineEntries, "Number of vaccines");
        lineDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        lineDataSet.setCircleRadius(7f);

        lineData = new LineData(lineDataSet);
        lineData.setDrawValues(false);


        lineChart.animateXY(900, 900);
        lineChart.getXAxis().setEnabled(true);

        lineChart.getDescription().setEnabled(false);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.setExtraBottomOffset(40f);

        lineChart.setDrawGridBackground(false);
        lineChart.getLegend().setEnabled(false);
//        lineChart.setViewPortOffsets(0,0,0,0);
        lineChart.setTouchEnabled(true);

//        lineChart.setScaleEnabled(true);
//        lineChart.setHighlightPerTapEnabled(true);
//        lineChart.setPinchZoom(true);
//        lineChart.setHorizontalScrollBarEnabled(false);
//        lineChart.setVerticalScrollBarEnabled(false);

        MyMarkerView mv = new MyMarkerView(this, R.layout.marker_content);
        lineChart.setMarker(mv); // For bounds control
        lineChart.setDrawMarkers(true);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.isDrawLabelsEnabled();
        xAxis.setLabelRotationAngle(315f);
        xAxis.setLabelCount(lineEntries.size());
        xAxis.setTextSize(10f);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceMax(0.5f);
        xAxis.setSpaceMin(0.5f);
        xAxis.setValueFormatter(new ClaimsXAxisValueFormatter(sortedDays));

        //put final here
        lineChart.setData(lineData);
        lineChart.invalidate();
    }

}

