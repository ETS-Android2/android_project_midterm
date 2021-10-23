package vn.edu.usth.coronatracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
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

import java.text.DecimalFormat;
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

public class CountryDetailFragment extends Fragment {
    public static final String TAG = "COUNTRY DETAIL FRAGMENT";
    private CountryModel resultCountry;

    private TextView cases, todayCases;
    private TextView active, todayActive;
    private TextView recovered, todayRecovered;
    private TextView deaths, todayDeaths;
    private TextView countryName;
    private RelativeLayout country;
    private PieChart pieChart;
    private List<String> sortedDays = new ArrayList<>();
    private List<Date> days = new ArrayList<>();
    private List<Integer> vaccineLists = new ArrayList<>();
    private LineChart lineChart;
    private LineData lineData;
    private LineDataSet lineDataSet;

    private int active_case;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_country_detail, container, false);
        initView(view);
        setupPieChart();
//        Bundle extras = getArguments();
//        if (extras != null) {
//            resultCountry = (CountryModel) extras.getSerializable("countryDetail");
//            Log.i(TAG, resultCountry.toString());
//        } else {
//            Log.i(TAG, "Error somewhere");
//        }

        if(getArguments()!=null){
            CountryDetailFragmentArgs args=CountryDetailFragmentArgs.fromBundle(getArguments());
            resultCountry=args.getCountryArg();
            Log.i(TAG, resultCountry.toString());
        }


        cases.setText(withLargeIntegers(Double.parseDouble(resultCountry.getCases())));
        active.setText(withLargeIntegers(Double.parseDouble(resultCountry.getActive())));
        deaths.setText(withLargeIntegers(Double.parseDouble(resultCountry.getDeaths())));
        recovered.setText(withLargeIntegers(Double.parseDouble(resultCountry.getRecovered())));
        todayCases.setText("+ " + withLargeIntegers(Double.parseDouble(resultCountry.getTodayCases())) + " cases");
        todayRecovered.setText("+ " + withLargeIntegers(Double.parseDouble(resultCountry.getTodayRecovered())) + " cases");
        todayDeaths.setText("+ " + withLargeIntegers(Double.parseDouble(resultCountry.getTodayDeaths())) + " cases");
        countryName.setText(resultCountry.getCountry());
        active_case = (Integer.parseInt(resultCountry.getTodayCases()) - Integer.parseInt(resultCountry.getTodayRecovered()) - Integer.parseInt(resultCountry.getTodayDeaths()));
        if (active_case < 0) {
            todayActive.setText("- " + withLargeIntegers(active_case * -1));
        } else {
            todayActive.setText("+ " + withLargeIntegers(active_case));
        }
        loadPieChartData(resultCountry);
        fetchData(resultCountry);
        return view;
    }

    public static String withLargeIntegers(double value) {
        DecimalFormat df = new DecimalFormat("###,###,###");
        return df.format(value);
    }

    private void initView(View view) {
        cases = view.findViewById(R.id.cases);
        todayCases = view.findViewById(R.id.casesToday);
        active = view.findViewById(R.id.active);
        recovered = view.findViewById(R.id.recovered);
        todayRecovered = view.findViewById(R.id.recoveredToday);
        deaths = view.findViewById(R.id.deaths);
        todayDeaths = view.findViewById(R.id.deathsToday);
        todayActive = view.findViewById(R.id.active_new);
        country = view.findViewById(R.id.country_card);
        countryName = view.findViewById(R.id.country_name);
        pieChart = view.findViewById(R.id.piechart);
        lineChart = view.findViewById(R.id.line_chart);
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

    private void fetchData(CountryModel country) {
        RequestQueue queue = Volley.newRequestQueue(getContext());
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

                    }
                });
        queue.add(jsonObjectRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void renderData() {
        Collections.sort(vaccineLists);
        // I dont know why, but the days are not sorted as in fetch api
        Log.i(TAG, days.toString());

        //sort the date in days list
        //take from stack overflow, I have not found the optimal way yet
        List<Date> sorted = days.stream().sorted(Comparator.comparingLong(Date::getTime))
                .collect(Collectors.toList());

        //Take 6 days out of 30 days
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


        lineDataSet = new LineDataSet(lineEntries, "");
        lineDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        lineDataSet.setCircleRadius(7f);

        lineData = new LineData(lineDataSet);
        lineData.setDrawValues(false);


        lineChart.animateXY(900, 900);
        lineChart.getXAxis().setEnabled(true);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.setExtraBottomOffset(40f);

        lineChart.setDrawGridBackground(false);
        lineChart.getLegend().setEnabled(false);
//        lineChart.setViewPortOffsets(0,0,0,0);
        lineChart.setTouchEnabled(true);
        lineChart.getDescription().setEnabled(false);

//        lineChart.setScaleEnabled(true);
//        lineChart.setHighlightPerTapEnabled(true);
//        lineChart.setPinchZoom(true);
//        lineChart.setHorizontalScrollBarEnabled(false);
//        lineChart.setVerticalScrollBarEnabled(false);

        MyMarkerView mv = new MyMarkerView(getContext(), R.layout.marker_content);
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

