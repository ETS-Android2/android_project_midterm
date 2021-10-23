package vn.edu.usth.coronatracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class GlobalFragment extends Fragment {
    public static final String TAG = "GLOBAL FRAGMENT";
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_global, container, false);
        initView(view);
        fetchData();
//        setDateTextView();
        setupPieChart();
//        loadPieChartData();


        country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Fragment fragment = new CountryFragment();
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.setCustomAnimations(
//                        R.anim.slide_in_right,
//                        R.anim.slide_out_left,
//                        R.anim.slide_in_left,
//                        R.anim.slide_out_right
//                );
////                fragmentTransaction.add(R.id.framelayout, fragment).addToBackStack(null).commit();
//                fragmentTransaction.replace(R.id.framelayout, fragment).commit();
                MyApplication.addToBackStack(R.id.navigation_country);
                Navigation.findNavController(view).navigate(R.id.navigation_country);
            }
        });
        return view;
    }

    private static String withLargeIntegers(double value) {
        DecimalFormat df = new DecimalFormat("###,###,###");
        return df.format(value);
    }

    private void fetchData() {
        CoronaApi api = RetrofitClient.getClient().create(CoronaApi.class);
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
                            todayActive.setText("- " + withLargeIntegers(active_case * -1) + " cases");
                        } else {
                            todayActive.setText("+ " + withLargeIntegers(active_case) + " cases");
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

    private void initView(View view) {
        cases = view.findViewById(R.id.cases);
        todayCases = view.findViewById(R.id.casesToday);
        active = view.findViewById(R.id.active);
        todayActive = view.findViewById(R.id.active_new);
        recovered = view.findViewById(R.id.recovered);
        todayRecovered = view.findViewById(R.id.recoveredToday);
        deaths = view.findViewById(R.id.deaths);
        todayDeaths = view.findViewById(R.id.deathsToday);
        country = view.findViewById(R.id.country_card);
        pieChart = view.findViewById(R.id.piechart);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("Tag", "Fragment Global.onDestroyView() has been called.");
    }

}